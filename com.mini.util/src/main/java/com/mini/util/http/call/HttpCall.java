package com.mini.util.http.call;


import com.mini.util.http.Converter;
import com.mini.util.http.builder.BaseBuilder;
import com.mini.util.lang.Function;
import okhttp3.*;
import okio.*;

import java.io.IOException;

public abstract class HttpCall<T> implements Callback {

    private Call call;
    protected Function.F0 onPause; // 请求暂停时回调
    protected Function.F0 onStart; // 请求开始时的回调
    protected final Converter<T> converter; // 数据转换器
    protected Function.F2<Call, T> onSuccess; // 成功返回时回调
    protected Function.F2<Call, IOException> onFail; // 请求失败时的回调
    protected Function.F2<Long, Long> onUpload; // 上传进度回调
    protected Function.F2<Long, Long> onDownload; // 下载进度回调

    public HttpCall(BaseBuilder builder, Converter<T> converter) {
        this.call = builder.getClient().newCall(getRequest(builder));
        this.converter = converter;
    }

    /**
     * 请求是否执行
     *
     * @return 是否下在执行请求
     */
    public boolean isExecuted() {
        return call != null && call.isExecuted();
    }

    /**
     * 请求是否取消
     *
     * @return 是否取消
     */
    public boolean isCanceled() {
        return call != null && call.isCanceled();
    }

    /**
     * 设置暂停加高方法
     *
     * @param onPause 暂停回调
     * @return 调用器
     */
    public HttpCall<T> setOnPause(Function.F0 onPause) {
        this.onPause = onPause;
        return this;
    }

    /**
     * 设置开始回调方法
     *
     * @param onStart 开始回调
     * @return 调用器
     */
    public HttpCall<T> setOnStart(Function.F0 onStart) {
        this.onStart = onStart;
        return this;
    }

    /**
     * 设置成功回调方法
     *
     * @param onSuccess 成功回调
     * @return 调用器
     */
    public HttpCall<T> setOnSuccess(Function.F2<Call, T> onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }

    /**
     * 设置失败回调方法
     *
     * @param onFail 失败回调
     * @return 调用器
     */
    public HttpCall<T> setOnFail(Function.F2<Call, IOException> onFail) {
        this.onFail = onFail;
        return this;
    }

    /**
     * 设置上传进度回调方法
     *
     * @param onUpload 提交进度回调
     * @return 调用器
     */
    public HttpCall<T> setOnUpload(Function.F2<Long, Long> onUpload) {
        this.onUpload = onUpload;
        return this;
    }

    /**
     * 设置下载进度回调方法
     *
     * @param onDownload 下载进度回调
     * @return 调用器
     */
    public HttpCall<T> setOnDownload(Function.F2<Long, Long> onDownload) {
        this.onDownload = onDownload;
        return this;
    }

    protected abstract Request getRequest(BaseBuilder builder);

    /**
     * 取消请求(暂停)
     *
     * @return 调用器
     */
    public final HttpCall<T> cancel() {
        try {
            if (call != null) call.cancel();
            if (onPause != null) onPause.apply();
            return this;
        } catch (Throwable e) {
            onFailure(call, new IOException(e));
        }
        return null;
    }

    /**
     * 同步执行
     *
     * @return 结果
     */
    public final T execute() {
        try {
            if (converter == null) return null;
            T instance = converter.apply(call, call.execute());
            if (onSuccess != null) onSuccess.apply(call, instance);
            return instance;
        } catch (Throwable e) {
            onFailure(call, new IOException(e));
        }
        return null;
    }

    /**
     * 异步执行
     *
     * @see Call
     */
    public final void enqueue() {
        call.enqueue(this);
        if (onStart != null) {
            onStart.apply();
        }
    }


    /**
     * 异步请求成功的回调方法
     *
     * @param call     调用器
     * @param response 结果
     */
    public final void onResponse(Call call, Response response) {
        try {
            if (onSuccess == null || converter == null) return;
            onSuccess.apply(call, converter.apply(call, response));
        } catch (Throwable e) {
            onFailure(call, new IOException(e));
        }
    }

    /**
     * 异步请求失败时的回调方法
     *
     * @param call 调用器
     * @param e    异常信息
     */
    public final void onFailure(Call call, IOException e) {
        if (onFail != null) onFail.apply(call, e);
    }

    /**
     * 上传进度处理器
     *
     * @author xchao
     */
    protected class ProgressRequestBody extends RequestBody {
        private RequestBody body;
        private BufferedSink buffer;

        public ProgressRequestBody(RequestBody body) {
            this.body = body;
        }

        public MediaType contentType() {
            return body.contentType();
        }

        public long contentLength() throws IOException {
            return body.contentLength();
        }

        public void writeTo(BufferedSink sink) throws IOException {
            buffer = buffer == null ? Okio.buffer(new ForwardingSink(sink) {
                protected long contentLength = body.contentLength();
                protected long total = 0L;

                public void write(Buffer source, long byteCount) throws IOException {
                    total = total + byteCount;
                    super.write(source, byteCount);
                    if (onUpload != null) onUpload.apply(total, contentLength);
                }
            }) : buffer;
            body.writeTo(buffer); //写入
            buffer.flush();  //刷新
        }
    }

    /**
     * 下载进度处理器
     *
     * @author xchao
     */
    protected class ProgressResponseBody extends ResponseBody {
        private ResponseBody body;
        private BufferedSource source;

        public ProgressResponseBody(ResponseBody body) {
            this.body = body;
        }

        @Override
        public MediaType contentType() {
            return body.contentType();
        }

        @Override
        public long contentLength() {
            return body.contentLength();
        }

        @Override
        public BufferedSource source() {
            return source == null ? source = Okio.buffer(new ForwardingSource(body.source()) {
                protected long total = 0;

                public long read(Buffer sink, long byteCount) throws IOException {
                    long length = super.read(sink, byteCount);
                    total = total + (length != -1 ? length : 0);
                    if (onDownload != null) onDownload.apply(total, contentLength());
                    return length;
                }
            }) : source;
        }
    }


}
