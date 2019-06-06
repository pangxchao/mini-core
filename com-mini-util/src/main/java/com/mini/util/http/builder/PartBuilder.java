package com.mini.util.http.builder;

import com.mini.util.lang.Function;
import com.mini.util.lang.StringUtil;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import static com.mini.util.lang.FileUtil.getMiniType;
import static okhttp3.MediaType.parse;

public final class PartBuilder<V> extends AbstractBuilder<PartBuilder<V>, V> {
    private final MultipartBody.Builder builder = new MultipartBody.Builder();

    // 请求暂停时回调
    private Function.F1<Long> onCancel;
    // 下载进度回调
    private Function.F2<Long, Long> onUpload;

    /** 设置取消回调 */
    public PartBuilder<V> setOnCancel(Function.F1<Long> onCancel) {
        this.onCancel = onCancel;
        return this;
    }

    /** 设置上传进度回调 */
    public PartBuilder<V> setOnUpload(Function.F2<Long, Long> onUpload) {
        this.onUpload = onUpload;
        return this;
    }

    @Override
    public RequestBody getRequestBody() {
        return builder.build();
    }

    @Override
    protected PartBuilder<V> getSelf() {
        return this;
    }

    public PartBuilder<V> setType(MediaType type) {
        builder.setType(type); return getSelf();
    }

    public PartBuilder<V> addFormDataPart(String name, Object value) {
        Object v = value == null ? "" : value;
        builder.addFormDataPart(name, String.valueOf(v));
        return getSelf();
    }

    public PartBuilder<V> addFormDataPart(String name, @Nullable String fileName, RequestBody body) {
        builder.addFormDataPart(name, fileName, body);
        return getSelf();
    }

    public PartBuilder<V> addPart(RequestBody body) {
        builder.addPart(body);
        return getSelf();
    }

    public PartBuilder<V> addPart(Headers headers, RequestBody body) {
        builder.addPart(headers, body);
        return getSelf();
    }

    public PartBuilder<V> addPart(MultipartBody.Part part) {
        builder.addPart(part);
        return getSelf();
    }

    public <T> PartBuilder<V> addFormDataPartIterable(String name, Iterable<T> array) {
        for (T t : array) addFormDataPart(name, t);
        return getSelf();
    }

    public <T> PartBuilder<V> addFormDataPartIterator(String name, Iterator<T> array) {
        for (; array.hasNext(); ) addFormDataPart(name, array.next());
        return getSelf();
    }

    public <T> PartBuilder<V> addFormDataPartArray(String name, T[] array) {
        for (T t : array) addFormDataPart(name, t);
        return getSelf();
    }

    public PartBuilder<V> addFormDataPartArray(String name, long[] array) {
        for (long t : array) addFormDataPart(name, t);
        return getSelf();
    }

    public PartBuilder<V> addFormDataPartArray(String name, int[] array) {
        for (int t : array) addFormDataPart(name, t);
        return getSelf();
    }

    public PartBuilder<V> addFormDataPartArray(String name, short[] array) {
        for (short t : array) addFormDataPart(name, t);
        return getSelf();
    }

    public PartBuilder<V> addFormDataPartArray(String name, byte[] array) {
        for (byte t : array) addFormDataPart(name, t);
        return getSelf();
    }

    public PartBuilder<V> addFormDataPartArray(String name, double[] array) {
        for (double t : array) addFormDataPart(name, t);
        return getSelf();
    }

    public PartBuilder<V> addFormDataPartArray(String name, float[] array) {
        for (float t : array) addFormDataPart(name, t);
        return getSelf();
    }

    public PartBuilder<V> addFormDataPartArray(String name, boolean[] array) {
        for (boolean t : array) addFormDataPart(name, t);
        return getSelf();
    }

    public PartBuilder<V> addFormDataPartArray(String name, char[] array) {
        for (char t : array) addFormDataPart(name, t);
        return getSelf();
    }


    public PartBuilder<V> addPart(String name, String fileName, String contentType, byte[] content) {
        return addPart(MultipartBody.Part.createFormData(name, fileName, new RequestBody() {
            public MediaType contentType() {
                if (!StringUtil.isBlank(contentType)) {
                    return parse(contentType);
                } return parse("application/octet-stream");
            }

            public long contentLength() {
                return content.length;
            }

            public void writeTo(@Nonnull BufferedSink sink) throws IOException {
                sink.write(content);
            }
        }));
    }

    public PartBuilder<V> addPart(String name, String fileName, String contentType, long contentLength, InputStream content) {
        return addPart(MultipartBody.Part.createFormData(name, fileName, new RequestBody() {
            public MediaType contentType() {
                if (!StringUtil.isBlank(contentType)) {
                    return parse(contentType);
                } return parse("application/octet-stream");
            }

            public long contentLength() {
                return contentLength;
            }

            public void writeTo(@Nonnull BufferedSink sink) throws IOException {
                try (Source source = Okio.source(content)) {
                    sink.writeAll(source);
                }
            }
        }));
    }

    public PartBuilder<V> addPart(String name, File file, long offset, long contentLength) {
        return addPart(MultipartBody.Part.createFormData(name, file.getName(), new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return parse(getMiniType(file));
            }

            public long contentLength() {
                return contentLength;
            }

            @Override
            public void writeTo(@Nonnull BufferedSink sink) throws IOException {
                if (offset + contentLength > file.length()) {
                    throw new IOException("Out-of-range");
                }
                // 剩于可以读取的字节数长度
                long sendLength = contentLength;
                long uploadLength = 0;
                try (BufferedSource source = Okio.buffer(Okio.source(file))) {
                    // 读取字节的buffer长度
                    int length, size = 2048;
                    // 读取字节的buffer数组
                    byte[] buf = new byte[size];
                    // 跳过已上传长度
                    source.skip(offset);
                    for (; sendLength > 0; sendLength -= length) {
                        size = Math.min(size, (int) sendLength);
                        // 读取数据，并返回实际读取长度
                        length = source.read(buf, 0, size);
                        if (length <= 0) break;
                        // 写数据
                        sink.write(buf, 0, length);
                        // 计算并回调上传的进度
                        uploadLength = uploadLength + length;
                        onUpload(contentLength, uploadLength);
                    }
                } catch (IOException exception) {
                    if (PartBuilder.this.isCanceled()) {
                        onCancel(uploadLength);
                    }
                    throw exception;
                }
            }
        }));
    }

    public PartBuilder<V> addPart(String name, File file) {
        return addPart(name, file, 0, file.length());
    }

    // 取消上传
    private void onCancel(long uploadLength) {
        if (onCancel == null) return;
        this.onCancel.apply(uploadLength);
    }

    // 上传进度回调
    private void onUpload(long totalLength, long downloadLength) {
        if (PartBuilder.this.onUpload == null) return;
        onUpload.apply(totalLength, downloadLength);
    }
}
