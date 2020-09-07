package com.mini.core.mvc.model;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.http.*;
import org.springframework.http.ContentDisposition.Builder;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStream;
import java.util.List;

import static com.mini.core.util.ThrowableKt.hidden;
import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.ResponseEntity.status;

public final class StreamModel {
    private static final MediaType MEDIA_TYPE = parseMediaType("application/octet-stream");
    private static final String MULTIPART_BOUNDARY = "MULTIPART_BOUNDARY";
    private static final long serialVersionUID = -1731063292578685253L;
    private static final Logger log = getLogger(StreamModel.class);
    private static final String CD = "Content-Disposition";
    private static final String A = "attachment";
    private static final int BUFFER_SIZE = 2048;
    private List<HttpRange> httpRangeList;
    private WriteCallback writeCallback;
    private long fileLength;
    private String fileName;

    private final HttpHeaders headers = new HttpHeaders();

    public StreamModel() {
        headers.setContentType(MEDIA_TYPE);
    }

    @NotNull
    public final HttpHeaders getHeaders() {
        return headers;
    }

    public StreamModel fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public StreamModel fileLength(long fileLength) {
        this.fileLength = fileLength;
        return this;
    }

    public StreamModel httpRangeList(List<HttpRange> httpRangeList) {
        this.httpRangeList = httpRangeList;
        return this;
    }

    public StreamModel writeCallback(WriteCallback writeCallback) {
        this.writeCallback = writeCallback;
        return this;
    }

    public final ResponseEntity<StreamingResponseBody> error(@NotNull HttpStatus status, @NotNull String message) {
        return status(status).headers(headers).body(output -> output.write(message.getBytes()));
    }

    public final ResponseEntity<StreamingResponseBody> error(@NotNull HttpStatus status) {
        return error(status, "Service Error!");
    }

    public final ResponseEntity<StreamingResponseBody> error(@NotNull String message) {
        return error(BAD_REQUEST, message);
    }

    public final ResponseEntity<StreamingResponseBody> error() {
        return error(BAD_REQUEST, "Service Error!");
    }

    public final ResponseEntity<StreamingResponseBody> ok() {
        // 设置文件附加信息
        ContentDisposition cd = headers.getContentDisposition();
        String type = cd.getType().isBlank() ? A : cd.getType();
        Builder cdBuilder = ContentDisposition.builder(type)
                .filename(cd.getFilename()).name(cd.getName());
        if (fileName != null && !fileName.isBlank()) {
            cdBuilder.filename(fileName);
        }
        headers.setContentDisposition(cdBuilder.build());
        // 不支持断点续传
        if (StreamModel.this.httpRangeList == null) {
            return status(OK).headers(headers).body(output -> {
                copy(output, 0, fileLength - 1); //
            });
        }
        // 告诉客户端允许断点续传
        this.headers.add("Accept-Ranges", "bytes");
        // 如果请求数据范围不存在，则客户端不需要断点续传，直接返回
        if (StreamModel.this.httpRangeList.isEmpty()) {
            return status(OK).headers(headers).body(output -> {
                copy(output, 0, fileLength - 1); //
            });
        }
        // 客户端只传入一个数据范围
        if (httpRangeList.size() == 1) {
            // 设置返回的数据范围
            HttpRange range = httpRangeList.get(0);
            long start = range.getRangeStart(fileLength), end = range.getRangeEnd(fileLength);
            headers.add("Content-Range", format("bytes %d-%d/%d", start, end, fileLength));
            // 设置传回内容在大小和Buffer大小
            long length = end - start + 1;
            headers.setContentLength(length);
            // 写数据
            return status(PARTIAL_CONTENT).headers(headers).body(output -> {
                copy(output, 0, fileLength - 1); //
            });
        }
        // 客户端传入了多个数据范围
        return status(PARTIAL_CONTENT).headers(headers).body(output -> {
            //noinspection SpellCheckingInspection
            headers.setContentType(parseMediaType("multipart/byteranges; boundary=" + MULTIPART_BOUNDARY));
            for (HttpRange range : httpRangeList) {
                long start = range.getRangeStart(fileLength), end = range.getRangeEnd(fileLength);
                // 输出每片数据的分隔符
                output.write("\r\n".getBytes());
                output.write(("--" + MULTIPART_BOUNDARY).getBytes());

                // 文件类型和设置返回的数据范围
                output.write("Content-Type: application/octet-stream".getBytes());
                output.write(format("Content-Range: bytes %d-%d/%d", start, end, end - start + 1).getBytes());

                // 输出一个空行,再定入数据
                output.write("\r\n".getBytes());
                this.copy(output, start, end);
            }
            // 输出结尾符
            output.write("\r\n".getBytes());
            output.write(("--" + MULTIPART_BOUNDARY + "--").getBytes());
        });
    }

    // 写入数据
    private void copy(OutputStream out, long start, long end) {
        try {
            if (StreamModel.this.writeCallback != null) {
                writeCallback.copy(out, start, end);
            }
        } catch (Exception e) {
            throw hidden(e);
        }
    }

    @FunctionalInterface
    public interface WriteCallback {
        void copy(OutputStream out, long start, long end) throws Exception;
    }
}
