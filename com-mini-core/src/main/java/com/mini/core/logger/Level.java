package com.mini.core.logger;

/**
 * 日志级别
 * @author xchao
 */
public enum Level {
    ALL(0), TRACE(1), DEBUG(2), INFO(3), WARN(4), ERROR(5), FATAL(6), OFF(7);
    private int value;

    Level(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
