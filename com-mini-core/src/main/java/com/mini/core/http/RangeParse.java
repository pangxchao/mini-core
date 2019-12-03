package com.mini.core.http;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;
import static java.lang.Math.max;
import static java.lang.Math.min;

public final class RangeParse {

    // 下载分片数据
    public static final class Range {
        private long length, start, end;

        Range(long start, long end) {
            this.start = start;
            this.end   = end;
        }

        public final long getLength() {
            return length;
        }

        public final long getStart() {
            return start;
        }

        public final long getEnd() {
            return min(end, length - 1);
        }

        // 判断数据合法性
        public final boolean validate(long contentLength) {
            length = contentLength;
            end    = min(max(length, 0), length - 1);
            return start >= 0 & start <= end && length > 0;
        }
    }

    private final StringReader reader;

    private RangeParse(StringReader reader) {
        this.reader = reader;
    }

    private boolean skipConstant(String constant) throws IOException {
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

    private long readLong() throws IOException {
        String number = this.readNumber();
        if (number.length() <= 0) {
            return -1;
        }
        return parseLong(number);
    }

    @Nonnull
    private String readNumber() throws IOException {
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

    private void skipBlankCharacter() throws IOException {
        this.reader.mark(1);
        int c = reader.read(); // 32, 9, 10, 13
        while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
            this.reader.mark(1);
            c = reader.read();
        }
        reader.reset();
    }

    // 解析Range数据
    public static List<Range> parseRange(@Nonnull String rangeText) throws IOException {
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

            // 创建Range并添加到列表中
            Range range = new Range(start, end);
            result.add(range);

            // 跳过空白字符
            parse.skipBlankCharacter();
        } while (parse.skipConstant(","));
        return result;
    }
}
