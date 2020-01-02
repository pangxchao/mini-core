package com.mini.core.logger.implement;

import com.mini.core.logger.Level;
import com.mini.core.logger.Logger;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.EventListener;

import static com.mini.core.logger.Level.*;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.time.DateFormatUtils.format;

/**
 * 自定义日志实现
 * @author XChao
 */
public final class MiniLogger implements Logger, EventListener {
	private Level level;

	public MiniLogger(@Nonnull Level level) {
		this.level = level;
	}

	@Override
	public final void setLevel(@Nonnull Level level) {
		this.level = level;
	}

	@Nonnull
	@Override
	public final Level getLevel() {
		return level;
	}

	private boolean isLevelEnabled(Level lev) {
		return level.value() <= lev.value();
	}

	@Override
	public boolean isTraceEnabled() {
		return isLevelEnabled(TRACE);
	}

	@Override
	public boolean isDebugEnabled() {
		return isLevelEnabled(DEBUG);
	}

	@Override
	public boolean isInfoEnabled() {
		return isLevelEnabled(INFO);
	}

	@Override
	public boolean isWarnEnabled() {
		return isLevelEnabled(WARN);
	}

	@Override
	public boolean isErrorEnabled() {
		return isLevelEnabled(ERROR);
	}

	@Override
	public boolean isFatalEnabled() {
		return isLevelEnabled(ERROR);
	}

	@Override
	public void trace(Object message) {
		this.trace(message, null);
	}

	@Override
	public void trace(Throwable throwable) {
		this.trace(null, throwable);
	}

	@Override
	public void trace(Object message, Throwable throwable) {
		log(TRACE, message, throwable);
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
		log(DEBUG, message, throwable);
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
		log(INFO, message, throwable);
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
		log(WARN, message, throwable);
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
		log(ERROR, message, throwable);
	}

	@Override
	public void fatal(Object message) {
		this.fatal(message, null);
	}

	@Override
	public void fatal(Throwable throwable) {
		this.fatal(null, throwable);
	}

	@Override
	public void fatal(Object message, Throwable throwable) {
		log(FATAL, message, throwable);
	}

	private void log(Level level, Object message, Throwable e) {
		if (!isLevelEnabled(level)) return;
		// 生成日志信息
		StringBuilder builder = new StringBuilder();
		// 日志时间和日志级别
		String FORMAT_STR = "yyyy-MM-dd HH:mm:ss.SSS";
		builder.append(format(new Date(), FORMAT_STR));
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
		message = defaultIfNull(message, "");
		builder.append(" ").append(message);
		System.out.println(builder.toString());
		if (e != null) e.printStackTrace(System.out);
	}
}
