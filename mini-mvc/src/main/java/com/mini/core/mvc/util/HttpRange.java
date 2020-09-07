package com.mini.core.mvc.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.max;

public abstract class HttpRange {
    private static final String BYTE_RANGE_PREFIX = "bytes=";
    private static final int MAX_RANGES = 100;

    public abstract long getStart(Long length);

    public abstract long getEnd(Long length);

    public static List<HttpRange> parseRanges(@Nullable String ranges) {
        if (!StringUtils.hasLength(ranges)) {
            return Collections.emptyList();
        }
        if (!ranges.startsWith(BYTE_RANGE_PREFIX)) {
            throw new IllegalArgumentException("Range '" + ranges + "' does not start with 'bytes='");
        }
        ranges = ranges.substring(BYTE_RANGE_PREFIX.length());

        String[] tokens = StringUtils.tokenizeToStringArray(ranges, ",");
        if (tokens.length > MAX_RANGES) {
            throw new IllegalArgumentException("Too many ranges: " + tokens.length);
        }
        List<HttpRange> result = new ArrayList<>(tokens.length);
        for (String token : tokens) {
            result.add(parseRange(token));
        }
        return result;
    }

    private static HttpRange parseRange(String range) {
        Assert.hasLength(range, "Range String must not be empty");
        int dashIdx = range.indexOf('-');
        if (dashIdx > 0) {
            long firstPos = Long.parseLong(range.substring(0, dashIdx));
            if (dashIdx < range.length() - 1) {
                Long lastPos = Long.parseLong(range.substring(dashIdx + 1));
                return new ByteRange(firstPos, lastPos);
            } else {
                return new ByteRange(firstPos, null);
            }
        } else if (dashIdx == 0) {
            long suffixLength = Long.parseLong(range.substring(1));
            return new SuffixByteRange(suffixLength);
        } else {
            throw new IllegalArgumentException("Range '" + range + "' does not contain \"-\"");
        }
    }

    private static class ByteRange extends HttpRange {
        private final long firstPos;
        @Nullable
        private final Long lastPos;

        public ByteRange(long firstPos, @Nullable Long lastPos) {
            assertPositions(firstPos, lastPos);
            this.firstPos = firstPos;
            this.lastPos = lastPos;
        }

        private void assertPositions(long firstBytePos, @Nullable Long lastBytePos) {
            if (firstBytePos < 0) {
                throw new IllegalArgumentException("Invalid first byte position: " + firstBytePos);
            }
            if (lastBytePos != null && lastBytePos < firstBytePos) {
                throw new IllegalArgumentException("firstBytePosition=" + firstBytePos +
                        " should be less then or equal to lastBytePosition=" + lastBytePos);
            }
        }

        @Override
        public long getStart(Long length) {
            return this.firstPos;
        }

        @Override
        public final long getEnd(@NotNull Long length) {
            if (lastPos != null && lastPos < length) {
                return lastPos;
            }
            return length - 1;
        }


        @Override
        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ByteRange)) {
                return false;
            }
            ByteRange otherRange = (ByteRange) other;
            return (this.firstPos == otherRange.firstPos &&
                    ObjectUtils.nullSafeEquals(this.lastPos, otherRange.lastPos));
        }

        @Override
        public int hashCode() {
            return (ObjectUtils.nullSafeHashCode(this.firstPos) * 31 +
                    ObjectUtils.nullSafeHashCode(this.lastPos));
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(this.firstPos);
            builder.append('-');
            if (this.lastPos != null) {
                builder.append(this.lastPos);
            }
            return builder.toString();
        }
    }

    private static class SuffixByteRange extends HttpRange {

        private final long suffixLength;

        public SuffixByteRange(long suffixLength) {
            if (suffixLength < 0) {
                throw new IllegalArgumentException("Invalid suffix length: " + suffixLength);
            }
            this.suffixLength = suffixLength;
        }

        @Override
        public final long getStart(Long length) {
            return max(0, length - suffixLength);
        }

        @Override
        public final long getEnd(Long length) {
            return length - 1;
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SuffixByteRange)) {
                return false;
            }
            SuffixByteRange otherRange = (SuffixByteRange) other;
            return (this.suffixLength == otherRange.suffixLength);
        }

        @Override
        public int hashCode() {
            return Long.hashCode(this.suffixLength);
        }

        @Override
        public String toString() {
            return "-" + suffixLength;
        }
    }
}
