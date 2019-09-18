package com.mini.http.builder;

import com.mini.util.StringUtil;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
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
import java.util.function.LongConsumer;
import java.util.function.ObjLongConsumer;
import java.util.stream.Stream;

import static com.mini.util.FileUtil.getMiniType;
import static com.mini.util.ObjectUtil.defIfNull;
import static java.lang.String.valueOf;
import static okhttp3.MediaType.parse;

public final class PartBuilder<T> extends AbstractBuilder<T> {
    private final Builder builder = new Builder();

    // 下载进度回调
    private ObjLongConsumer<Long> onUpload;
    // 请求暂停时回调
    private LongConsumer onCancel;


    @Override
    public RequestBody getRequestBody() {
        return builder.build();
    }

    /** 设置上传进度回调 */
    public PartBuilder<T> setOnUpload(ObjLongConsumer<Long> onUpload) {
        this.onUpload = onUpload;
        return this;
    }

    /** 设置取消回调 */
    public PartBuilder<T> setOnCancel(LongConsumer onCancel) {
        this.onCancel = onCancel;
        return this;
    }

    public PartBuilder<T> setType(MediaType type) {
        builder.setType(type);
        return this;
    }

    public PartBuilder<T> addFormDataPart(String name, Object value) {
        builder.addFormDataPart(name, valueOf(defIfNull(value, "")));
        return this;
    }

    public <E> PartBuilder<T> addFormDataPartStream(String name, Stream<E> stream) {
        stream.forEach(e -> addFormDataPart(name, e));
        return this;
    }

    public PartBuilder<T> addFormDataPart(String name, @Nullable String fileName, RequestBody body) {
        builder.addFormDataPart(name, fileName, body);
        return this;
    }

    public PartBuilder<T> addPart(RequestBody body) {
        builder.addPart(body);
        return this;
    }

    public PartBuilder<T> addPart(Headers headers, RequestBody body) {
        builder.addPart(headers, body);
        return this;
    }

    public PartBuilder<T> addPart(MultipartBody.Part part) {
        builder.addPart(part);
        return this;
    }

    public PartBuilder<T> addPart(String name, String fileName, String contentType, byte[] content) {
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

    public PartBuilder<T> addPart(String name, String fileName, String contentType, long contentLength, InputStream content) {
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

    public PartBuilder<T> addPart(String name, File file, long offset, long contentLength) {
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

    public PartBuilder<T> addPart(String name, File file) {
        return addPart(name, file, 0, file.length());
    }

    // 取消上传
    private void onCancel(long uploadLength) {
        if (onCancel == null) return;
        this.onCancel.accept(uploadLength);
    }

    // 上传进度回调
    private void onUpload(long totalLength, long downloadLength) {
        if (PartBuilder.this.onUpload == null) return;
        onUpload.accept(totalLength, downloadLength);
    }
}
