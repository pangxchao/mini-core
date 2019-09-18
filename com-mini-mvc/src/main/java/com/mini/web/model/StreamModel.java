package com.mini.web.model;

import com.mini.util.ObjectUtil;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.mini.util.ObjectUtil.require;
import static java.lang.Long.parseLong;
import static java.lang.Math.min;
import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE;

/**
 * Stream Model类实现
 * @author xchao
 */
public final class StreamModel extends IModel<StreamModel> implements Serializable {
    private static final String MULTIPART_BOUNDARY = "MULTIPART_BOUNDARY";
    private static final long serialVersionUID = -1731063292578685253L;
    private static final String TYPE = "application/octet-stream";
    // curl -i --range 0-9 http://www.baidu.com/img/bdlogo.gif
    private static final int BUFFER_SIZE = 2048;
    private boolean acceptRangesSupport = true;
    private WriteCallback writeCallback;
    private InputStream inputStream;
    private long contentLength;
    private String fileName;


    public StreamModel() {
        super(TYPE);
    }

    @Override
    protected StreamModel model() {
        return this;
    }

    public StreamModel setFileName(String fileName) {
        this.fileName = fileName;
        return model();
    }

    public StreamModel setContentLength(long contentLength) {
        this.contentLength = contentLength;
        return model();
    }

    public StreamModel setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return model();
    }

    public StreamModel setWriteCallback(WriteCallback writeCallback) {
        this.writeCallback = writeCallback;
        return model();
    }

    public StreamModel setAcceptRangesSupport(boolean acceptRangesSupport) {
        this.acceptRangesSupport = acceptRangesSupport;
        return model();
    }

    protected long getEnd() {
        return contentLength - 1;
    }

    @Override
    protected void submit(HttpServletRequest request, HttpServletResponse response, String viewPath) {
        try (ServletOutputStream output = response.getOutputStream()) {
            if (!StringUtil.isBlank(StreamModel.this.fileName)) {
                response.addHeader("Content-Disposition",  //
                        "attachment; filename=" + fileName);
            }

            // 不支持断点续传
            if (!this.acceptRangesSupport) {
                copy(output, 0, getEnd());
                return;
            }

            // 读取客户端上传的请求数据范围数据,并告诉客户端允许断点续传
            String rangeText = request.getHeader("Range");
            response.setHeader("Accept-Ranges", "bytes");

            // 如果请求数据范围不存在，则客户端不需要断点续传，直接返回
            if (rangeText == null || StringUtil.isBlank(rangeText)) {
                this.copy(output, 0, getEnd());
                return;
            }

            // 解析客户端提交的请求数据范围数据
            List<Range> rangeList = this.parseRange(rangeText);
            if (rangeList == null || rangeList.size() <= 0) {
                response.addHeader("Content-Range", "bytes */" + contentLength);
                response.sendError(SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                return;
            }

            // 验证客户端提交的请求头是否合法
            for (StreamModel.Range range : rangeList) {
                if (range.validate()) continue;

                // 如果Range对象不合法，返回错误信息
                response.addHeader("Content-Range", "bytes */" + contentLength);
                response.sendError(SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                return;
            }

            // 客户端只传入一个数据范围
            if (rangeList.size() == 1 && rangeList.get(0).validate()) {
                // 设置返回的数据范围
                StreamModel.Range range = rangeList.get(0);
                response.addHeader("Content-Range", format("bytes %d-%d/%d", range.getStart(), range.getEnd(), range.getLength()));

                // 设置传回内容在大小和Buffer大小
                long length = range.getEnd() - range.getStart() + 1;
                response.setContentLengthLong(length);
                response.setBufferSize(BUFFER_SIZE);

                // 写数据
                copy(output, range.getStart(), range.getEnd());
                return;
            }

            // 客户端传入了多个数据范围
            response.setContentType("multipart/byteranges; boundary=" + MULTIPART_BOUNDARY);
            for (StreamModel.Range range : rangeList) {
                // 输出每片数据的分隔符
                output.println();
                output.println("--" + MULTIPART_BOUNDARY);

                // 文件类型和设置返回的数据范围
                output.println("Content-Type: " + getContentType());
                output.println(format("Content-Range: bytes %d-%d/%d", range.getStart(), range.getEnd(), range.getLength()));

                // 输出一个空行,再定入数据
                output.println();
                copy(output, range.getStart(), range.getEnd());
            }

            // 输出结尾符
            output.println();
            output.print("--" + MULTIPART_BOUNDARY + "--");
        } catch (Exception | Error exception) {
            response.setStatus(500);
        }
    }

    // 解析Range数据
    private synchronized List<Range> parseRange(@Nonnull String rangeText) throws IOException {
        // 读取器
        StringReader reader = new StringReader(rangeText);
        RangeParse parse = new RangeParse(reader);

        // Range固定格式以"bytes="开头
        parse.skipBlankCharacter();
        if (!parse.skipConstant("bytes=")) {
            return null;
        }

        // 数据容器
        List<Range> result = new ArrayList<>();
        do {
            // 读取范围的开始字节
            parse.skipBlankCharacter();
            long start = parse.readLong();
            if (start == -1) start = 0;

            // 读取开始与结束之间的“-”
            parse.skipBlankCharacter();
            if (!parse.skipConstant("-")) {
                return null;
            }

            // 读取范围的结束字节
            parse.skipBlankCharacter();
            long end = parse.readLong();
            if (end == -1) end = getEnd();

            // 创建Range并添加到列表中
            Range range = new Range(contentLength, start, end);
            result.add(range);

            // 跳过空白字符
            parse.skipBlankCharacter();
        } while (parse.skipConstant(","));
        return result;
    }

    // 写入数据
    private void copy(OutputStream out, long start, long end) throws Exception {
        getWriteCallback().copy(out, start, end);
    }

    @Nonnull
    private synchronized WriteCallback getWriteCallback() {
        return ObjectUtil.defIfNull(writeCallback, new WriteCallback() {
            public void copy(OutputStream out, long start, long end) throws IOException {
                if (StreamModel.this.inputStream == null) return;
                try (InputStream source = StreamModel.this.inputStream) {
                    long sendLength = end - start + 1, skip = source.skip(start);
                    require(skip >= start, format("Skip fail. [%d, %d]", skip, start));
                    if (end >= 0) transferTo(out, source, sendLength);
                    else source.transferTo(out);
                }
            }

            private void transferTo(OutputStream out, InputStream source, long sendLength) throws IOException {
                int length, size = BUFFER_SIZE;
                byte[] buffer = new byte[size];
                for (; sendLength > 0; sendLength -= length) {
                    size   = min(size, (int) sendLength);
                    length = source.read(buffer, 0, size);

                    if (length <= 0) break;
                    out.write(buffer, 0, length);
                }
            }
        });
    }

    // 下载分片数据
    protected static final class Range {
        private long length, start, end;

        public Range(long length, long start, long end) {
            this.length = length;
            this.start  = start;
            this.end    = end;
        }

        protected final long getLength() {
            return length;
        }

        protected final long getStart() {
            return start;
        }

        protected final long getEnd() {
            return min(end, length - 1);
        }

        // 判断数据合法性
        public final boolean validate() {
            end = min(end, length - 1);
            boolean re = start >= 0;
            re = re && start <= end;
            return re && length > 0;
        }
    }

    protected static final class RangeParse {
        private final StringReader reader;

        public RangeParse(StringReader reader) {
            this.reader = reader;
        }

        public boolean skipConstant(String constant) throws IOException {
            int length = constant.length();
            this.reader.mark(length);
            for (int i = 0; i < length; i++) {
                int c = reader.read();
                if (c == -1 || c != constant.charAt(i)) {
                    reader.reset();
                    return false;
                }
            }
            return true;
        }

        public long readLong() throws IOException {
            String number = this.readNumber();
            if (number.length() <= 0) {
                return -1;
            }
            return parseLong(number);
        }

        @Nonnull
        public String readNumber() throws IOException {
            StringBuilder result = new StringBuilder();
            this.reader.mark(1);
            int c = reader.read();
            while (c >= '0' && c <= '9') {
                result.append((char) c);
                this.reader.mark(1);
                c = reader.read();
            }
            reader.reset();
            return result.toString();
        }

        public void skipBlankCharacter() throws IOException {
            this.reader.mark(1);
            int c = reader.read(); // 32, 9, 10, 13
            while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                this.reader.mark(1);
                c = reader.read();
            }
            reader.reset();
        }
    }

    @FunctionalInterface
    public interface WriteCallback {
        void copy(OutputStream out, long start, long end) throws Exception;
    }
}
