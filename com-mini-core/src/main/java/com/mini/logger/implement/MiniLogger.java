package com.mini.logger.implement;

import com.mini.logger.Level;
import com.mini.logger.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.mini.util.ObjectUtil.defIfNull;

/**
 * 自定义日志实现
 * @author XChao
 */
public final class MiniLogger implements Logger {
    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private Level level;

    public MiniLogger(Level level) {
        this.level = level;
    }

    public MiniLogger setLevel(Level level) {
        this.level = level;
        return this;
    }

    @Override
    public Level getLevel() {
        if (level != null) {
            return level;
        }
        return Level.DEBUG;
    }

    @Override
    public boolean isVerboseEnabled() {
        return this.getLevel().getValue() >= Level.VERBOSE.getValue();
    }

    @Override
    public boolean isDebugEnabled() {
        return this.getLevel().getValue() >= Level.DEBUG.getValue();
    }

    @Override
    public boolean isInfoEnabled() {
        return this.getLevel().getValue() >= Level.INFO.getValue();
    }

    @Override
    public boolean isWarnEnabled() {
        return this.getLevel().getValue() >= Level.WARN.getValue();
    }

    @Override
    public boolean isErrorEnabled() {
        return this.getLevel().getValue() >= Level.ERROR.getValue();
    }

    @Override
    public void verbose(Object message) {
        this.verbose(message, null);
    }

    @Override
    public void verbose(Throwable throwable) {
        this.verbose(null, throwable);
    }

    @Override
    public void verbose(Object message, Throwable throwable) {
        log(Level.VERBOSE, message, throwable);
    }

    @Override
    public void debug(Object message) {
        this.debug(message, null);
    }

    @Override
    public void debug(Throwable throwable) {
        this.debug(null, throwable);
    }

    @Override
    public void debug(Object message, Throwable throwable) {
        log(Level.DEBUG, message, throwable);
    }

    @Override
    public void info(Object message) {
        this.info(message, null);
    }

    @Override
    public void info(Throwable throwable) {
        this.info(null, throwable);
    }

    @Override
    public void info(Object message, Throwable throwable) {
        log(Level.INFO, message, throwable);
    }

    @Override
    public void warn(Object message) {
        this.warn(message, null);
    }

    @Override
    public void warn(Throwable throwable) {
        this.warn(null, throwable);
    }

    @Override
    public void warn(Object message, Throwable throwable) {
        log(Level.WARN, message, throwable);
    }

    @Override
    public void error(Object message) {
        this.error(message, null);
    }

    @Override
    public void error(Throwable throwable) {
        this.error(null, throwable);
    }

    @Override
    public void error(Object message, Throwable throwable) {
        log(Level.ERROR, message, throwable);
    }

    private void log(Level level, Object message, Throwable e) {
        if (level.getValue() >= getLevel().getValue()) {
            StringBuilder builder = new StringBuilder();
            // 日志时间和日志级别
            builder.append(format.format(new Date()));
            builder.append(" ").append(level.name()).append(" ");

            // 日志堆栈位置
            for (StackTraceElement element : new Throwable().getStackTrace()) {
                if (!element.getClassName().equals(MiniLogger.class.getName())) {
                    builder.append("[");
                    builder.append(element.getClassName());
                    builder.append(".");
                    builder.append(element.getMethodName());
                    builder.append(" line:");
                    builder.append(element.getLineNumber());
                    builder.append("]");
                    break;
                }
            }
            // 消息内容
            message = defIfNull(message, "");
            builder.append(" ").append(message);
            System.out.println(builder.toString());
            if (e != null) e.printStackTrace(System.out);
        }
    }

}




















