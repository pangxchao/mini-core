package com.mini.util.http.upload;

import java.io.File;
import java.io.IOException;

import com.mini.util.PKGenerator;
import com.mini.util.http.Converter;
import com.mini.util.http.HttpUtil;
import com.mini.util.http.builder.PartBuilder;
import com.mini.util.http.call.HttpCall;
import com.mini.util.lang.FileUtil;
import com.mini.util.lang.Function;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class UploadInfo<T> {
    public static final int STATUS_WAIT = 0; // 队列等待中
    public static final int STATUS_PAUSE = 1; // 暂停
    public static final int STATUS_UPLOADING = 2; // 上传中
    public static final int STATUS_FINISH = 3; // 上传完成
    public static final int STATUS_FAIL = 4; // 上传失败

    private File file; // 需要上传的文件
    private int status; // 上传状态
    private String url; // 上传文件的URL
    private String field; // 上传文件的键名
    private final long id; // 唯一标题
    private long sliceSize; // 分片大小
    private HttpCall<T> call; // 调用器
    private long totalLength; // 文件总大小
    private boolean callPause; // 是否调用过暂停
    private long uploadLength; // 已上传大小
    private boolean sliceUploading; // 是否正在上传（写数据）
    private Function.F1<Long> onPause; // 请求暂停时回调
    private Function.F1<Long> onStart; // 请求开始时的回调
    private final Converter<T> converter; // 数据转换对象
    private Function.F3<Long, HttpCall<T>, T> onSuccess; // 成功返回时回调
    private Function.F3<Long, Long, Long> onUpload; // 上传进度回调
    private Function.F3<Long, HttpCall<T>, IOException> onFail; // 请求失败时的回调
    private Function.F4<PartBuilder, Long, Long, T> handler; // 单片上传完成时的回调处理

    public UploadInfo(String url, String field, File file, Converter<T> converter) {
        totalLength = file.length();
        this.converter = converter;
        id = PKGenerator.key();
        this.field = field;
        this.file = file;
        this.url = url;
    }

    /**
     * 暂停
     */
    private void onPause() {
        status = STATUS_PAUSE;
        if (onPause != null) onPause.apply(id);
    }

    /**
     * 开始
     */
    private void onStart() {
        status = STATUS_UPLOADING;
        if (onStart != null) onStart.apply(id);
    }

    /**
     * 成功
     *
     * @param call 调用器
     * @param t    分片返回信息
     */
    private void onSuccess(HttpCall<T> call, T t) {
        status = STATUS_FINISH;
        if (onSuccess != null) {
            onSuccess.apply(id, call, t);
        }

    }

    /**
     * 上传进度
     *
     * @param total        需要上传总大小
     * @param uploadLength 已上传大小
     */
    private void onUpload(long total, long uploadLength) {
        if (onUpload != null) onUpload.apply(id, total, uploadLength);
    }

    /**
     * 失败回调
     *
     * @param call 调用器
     * @param e    异常信息
     */
    private void onFail(HttpCall<T> call, IOException e) {
        status = STATUS_FAIL;
        if (onFail != null) {
            onFail.apply(id, call, e);
        }
    }

    /**
     * 分片成功回调
     *
     * @param call 调用器
     * @param t    分片返回信息
     */
    private void onSliceSuccess(Call call, T t) {
        try {
            // 退出下在写数据的状态
            sliceUploading = false;
            // 已上传的长度与总长度相等，表示上传完成
            if (uploadLength == totalLength) {
                onSuccess(this.call, t);
                return;
            }
            // 如果调用过暂停方法， 需要暂停
            if (callPause) {
                callPause = false;
                onPause();
                return;
            }
            // 分片大小与分片回调都满足分片，则分片
            if (handler != null && sliceSize > 0) {
                start(t);
                return;
            }
            // 如果不满足上面的条件，说明上传过程出现问题
            onFail(this.call, new IOException("ERROR!!!"));
        } catch (Exception e) {
            onFail(this.call, new IOException(e));
        }
    }

    public UploadInfo<T> setSliceSize(long sliceSize) {
        this.sliceSize = sliceSize;
        return this;
    }

    public UploadInfo<T> setUploadLength(long uploadLength) {
        this.uploadLength = uploadLength;
        return this;
    }

    public UploadInfo<T> setOnPause(Function.F1<Long> onPause) {
        this.onPause = onPause;
        return this;
    }

    public UploadInfo<T> setOnStart(Function.F1<Long> onStart) {
        this.onStart = onStart;
        return this;
    }

    public UploadInfo<T> setOnSuccess(Function.F3<Long, HttpCall<T>, T> onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }

    public UploadInfo<T> setOnUpload(Function.F3<Long, Long, Long> onUpload) {
        this.onUpload = onUpload;
        return this;
    }

    public UploadInfo<T> setOnFail(Function.F3<Long, HttpCall<T>, IOException> onFail) {
        this.onFail = onFail;
        return this;
    }

    public UploadInfo<T> setHandler(Function.F4<PartBuilder, Long, Long, T> handler) {
        this.handler = handler;
        return this;
    }

    /**
     * 获取上传唯一标识
     *
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * 获取上传状态
     *
     * @return 状态
     */
    public int getStatus() {
        return status;
    }

    /**
     * 获取上传总大小
     *
     * @return 需要上传总大小
     */
    public long getTotalLength() {
        return totalLength;
    }

    /**
     * 获取已上传大小
     *
     * @return 已上传大小
     */
    public long getUploadLength() {
        return uploadLength;
    }

    // 开始上传
    private void start(T t) {
        try {
            // 上传完成或者正在写数据时，直接返回
            if (status == STATUS_FINISH || sliceUploading) {
                return;
            }
            sliceUploading = true; // 正在写数据状态
            PartBuilder builder = HttpUtil.newClient().newRequest().url(url).newPartBuilder();
            if (field != null && file != null && handler != null && sliceSize > 0) {
                handler.apply(builder, totalLength, uploadLength, t); // 添加回调参数
                final long sendSize = Math.min(sliceSize, totalLength - uploadLength);
                builder.addPart(MultipartBody.Part.createFormData(field, file.getName(), new RequestBody() {
                    public void writeTo(BufferedSink sink) throws IOException {
                        try (BufferedSource source = Okio.buffer(Okio.source(file))) {
                            long mySenSize = sendSize; // 剩于可以读取的字节数长度
                            int length, size = 2048; // 读取字节的buffer长度
                            byte[] buf = new byte[size]; // 读取字节的buffer数组
                            source.skip(uploadLength); // 跳过已上传长度
                            for (; mySenSize > 0; mySenSize -= length) {
                                size = Math.min(size, (int) mySenSize);
                                length = source.read(buf, 0, size);
                                if (length <= 0) break; // 数据完成
                                sink.write(buf, 0, length); // 写数据
                                uploadLength = uploadLength + length;
                                onUpload(totalLength, uploadLength);
                            }
                        }
                    }

                    public MediaType contentType() {
                        return MediaType.parse(FileUtil.getMiniType(file));
                    }

                    public long contentLength() {
                        return sendSize;
                    }
                }));
            }
            call = builder.post(converter).setOnSuccess(this::onSliceSuccess).setOnPause(this::onPause);
            call.setOnStart(this::onStart).setOnFail((call, e) -> onFail(this.call, e)).enqueue();
        } catch (Exception e) {
            onFail(this.call, new IOException(e));
        }
    }

    /**
     * 开始下载
     */
    public void start() {
        start(null);
    }

    /**
     * 暂停
     */
    public void pause() {
        if (status == STATUS_FINISH || status == STATUS_FAIL) return;
        if (status != STATUS_PAUSE) callPause = true; // 需要暂停
    }
}
