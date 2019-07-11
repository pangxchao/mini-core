package com.mini.web.model;

import com.mini.util.TypeUtil;
import com.mini.util.map.MiniMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Stream Model类实现
 * @author xchao
 */
public final class StreamModel extends LinkedHashMap<String, Object> implements MiniMap<String>, IModel<StreamModel> {
    private static final String REGEX = "(?:(bytes=)(?<NS>\\d)+(-)(?<NN>\\d)*)";
    private static final long serialVersionUID = -1731063292578685253L;
    private String contentType = "application/octet-stream";
    private int status = HttpServletResponse.SC_OK;
    private InputStream inputStream;
    private long contentLength;
    private String message;


    @Override
    public StreamModel toChild() {
        return this;
    }

    @Override
    public StreamModel sendError(int status) {
        this.status = status;
        return toChild();
    }

    @Override
    public StreamModel sendError(int status, String message) {
        this.message = message;
        this.status  = status;
        return toChild();
    }

    @Override
    public StreamModel setContentType(String contentType) {
        this.contentType = contentType;
        return toChild();
    }


    /**
     * Sets the value of inputStream.
     * @param inputStream The value of inputStream
     * @return {@Code #this}
     */
    public StreamModel setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return toChild();
    }

    /**
     * Sets the value of contentLength.
     * @param contentLength The value of contentLength
     * @return {@Code #this}
     */
    public StreamModel setContentLength(long contentLength) {
        this.contentLength = contentLength;
        return toChild();
    }

    /**
     * Sets the value of fileName.
     * @param fileName The value of fileName
     * @return {@Code #this}
     */
    public StreamModel setFileName(HttpServletResponse response, String fileName) {
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        return toChild();
    }

    @Override
    public void submit(HttpServletRequest request, HttpServletResponse response) throws Exception, Error {
        // 错误码处理和返回数据格式处理
        response.setContentType(contentType);
        if (status != HttpServletResponse.SC_OK) {
            response.sendError(status, message);
            return;
        }

        // 如果输入流为空，不需要处理
        if (inputStream == null) {
            return;
        }

        // 开始读取数据
        try (OutputStream output = response.getOutputStream();
             InputStream input = this.inputStream) {

            // 设置文件下载大小，文件字节长度，如果设置该参数，表示可以 多线程连接下载
            // 告诉客户端允许断点续传，响应的格式是:Accept-Ranges: bytes
            response.setHeader("Accept-Ranges", "bytes");

            // [0]-下载开始位置,[1]-下载结位置, [2]-客户端请求的字节总量
            long[] position = new long[]{0L, contentLength - 1, contentLength};
            // 客户端请求的下载的文件块的开始字节，格式：bytes=123-或者bytes=123-456
            Optional.ofNullable(request.getHeader("Range")).ifPresent(range -> {
                try {
                    // 若客户端传来 Range，下载了一部分，设置206状态(SC_PARTIAL_CONTENT)
                    response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                    Pattern pattern = Pattern.compile(REGEX, CASE_INSENSITIVE);
                    for (Matcher matcher = pattern.matcher(range); matcher.find(); ) {
                        position[0] = TypeUtil.castToLongVal(matcher.group("NS"));
                        position[1] = TypeUtil.castToLongVal(matcher.group("NN"));
                    }
                    position[0] = input.skip(position[0]);
                    if (position[1] <= position[0]) {
                        position[1] = contentLength;
                    }
                    position[2] = position[1] - position[0] + 1;
                    // Content-Range: bytes [文件块的开始字节]-[文件块结束字节]/[文件的总大小]
                    response.setHeader("Content-Range", format("bytes %d-%d/%d", position[0], position[1], contentLength));
                } catch (Exception ignored) { }
            });
            // 只有设置了文件下载大小时， 才支持断点续传。
            response.setHeader("Content-Length", String.valueOf(position[2]));
            try {
                int length, size = 2048;
                byte[] buf = new byte[size];
                long sendLength = position[2];
                for (; sendLength > 0; sendLength -= length) {
                    size   = (int) Math.min(size, sendLength);
                    length = input.read(buf, 0, size);
                    if (length <= 0) break;
                    output.write(buf, 0, length);
                }
                output.flush();
            } catch (IOException ignored) {}
        }
    }
}
