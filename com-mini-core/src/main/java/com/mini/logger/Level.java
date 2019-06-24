package com.mini.logger;

/**
 * 日志级别
 * @author xchao
 */
public enum Level {
    VERBOSE(1),
    DEBUG(2),
    INFO(3),
    WARN(4),
    ERROR(5);

    private int value;

    Level(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Level valueOf(int value) {
        if (value == 1) return VERBOSE;
        if (value == 2) return DEBUG;
        if (value == 3) return INFO;
        if (value == 4) return WARN;
        return ERROR;
    }
}
