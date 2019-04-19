package com.mini.spring.model;

import com.mini.util.lang.StringUtil;
import com.mini.util.lang.TypeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_NOT_ACCEPTABLE;
import static javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT;

public final class StreamModel implements View {

    private String message;
    private String fileName;
    private HttpStatus status;
    private String contentType;
    private long contentLength;
    private InputStream inputStream;

    public String getMessage() {
        return message;
    }

    public String getFileName() {
        return fileName;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    public long getContentLength() {
        return contentLength;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public StreamModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public StreamModel setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public StreamModel setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public StreamModel setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public StreamModel setContentLength(long contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public StreamModel setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try (OutputStream outputStream = response.getOutputStream();
             InputStream inputStream = this.getInputStream()) {
            try {
                // 设置文件下载的头信息
                if (StringUtil.isNotBlank(this.fileName)) {
                    String fileName = new String(this.fileName.getBytes(), "ISO8859-1");
                    response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                }
            } catch (Exception ignored) {}
            // 如果输入流为空，则表示文件下载时，已经在控制器处理过了，不需要在这里处理
            if (inputStream == null) {
                return;
            }

            // 文件总长度
            final long fileLength = contentLength;

            // bufferSize：读取文件缓冲区最大长度
            final int bufferSize = 1024;

            // buffer：缓冲区数据池
            byte[] buffer = new byte[bufferSize];

            /* startPos: 开始下载位置，endPos：结束下载位置，contentLength：下载内容大小<br/>
             *	比如bytes=27000-39000时，startPos=27000，endPos=39000，contentLength=39000-27000 */
            long startPos, endPos = fileLength - 1, contentLength = fileLength;
            // 设置文件下载大小，文件字节长度，如果设置该参数，表示可以 多线程连接下载
            if (contentLength > 0) {
                // 告诉客户端允许断点续传,响应的格式是:Accept-Ranges: bytes
                response.setHeader("Accept-Ranges", "bytes");
                // 客户端请求的下载的文件块的开始字节
                if (null != request.getHeader("Range")) {
                    // 若客户端传来Range，说明之前下载了一部分，设置206状态(SC_PARTIAL_CONTENT)
                    response.setStatus(SC_PARTIAL_CONTENT);
                    // 请求Header中 Range 字段格式为 “bytes=27000-” 或者 “bytes=27000-3900”
                    String rangeBytes = request.getHeader("Range").replaceAll("bytes=", "");

                    // 从某字节开始 的下载(bytes=27000-)
                    if (rangeBytes.endsWith("-")) { // 读取已经读取的字节数
                        startPos = TypeUtil.castToLongValue(rangeBytes.replaceAll("-", "").trim());
                    } else { // // 读取已经读取的字节和读取结束时的字节数
                        String[] rangeBytesArr = rangeBytes.split("-");
                        startPos = TypeUtil.castToLongValue(rangeBytesArr[0].trim());
                        endPos   = TypeUtil.castToLongValue(rangeBytesArr[1].trim());
                    }
                    // 只有设置了文件下载大小时， 才支持断点续传
                    contentLength = endPos - startPos + 1;
                    // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
                    response.setHeader("Content-Range", StringUtil.join("bytes ", startPos, "-", endPos, "/", fileLength));

                    // 读取时，跳过 startLength 字节
                    if (inputStream.skip(startPos) < startPos) {
                        response.sendError(SC_NOT_ACCEPTABLE);
                        return;
                    }
                }
                // 只有设置了文件下载大小时， 才支持断点续传。
                response.setHeader("Content-Length", String.valueOf(contentLength));
            }

            // length: 读取文件缓冲区实际长度
            // readLength: 已读取的总长度
            int length, readLength = 0;
            if (endPos > 0) {
                while (contentLength - readLength >= bufferSize) {
                    length = inputStream.read(buffer, 0, bufferSize);
                    outputStream.write(buffer, 0, length);
                    readLength += bufferSize;
                }
                // 余下的不足 1024 个字节在这里读取
                if (readLength < contentLength) {
                    length = ((int) (contentLength - readLength));
                    length = inputStream.read(buffer, 0, length);
                    outputStream.write(buffer, 0, length);
                }
            } else {
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
            }
            outputStream.flush();
        }
    }

}
