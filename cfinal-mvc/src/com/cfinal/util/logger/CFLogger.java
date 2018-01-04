/**
 * Created the com.cfinal.util.logger.CFLogger.java
 * @created 2016年9月20日 上午11:06:45
 * @version 1.0.0
 */
package com.cfinal.util.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义日志实现
 * @author XChao
 */
public class CFLogger extends CFLog {
	private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	// 输出日志级别
	private static int levels = 0;

	private static int getLevels() {
		if(levels == 0) {
			synchronized (CFLogger.class) {
				if(levels == 0) {
					levels = CFLogLevel.DEBUG.getOuts();
				}
			}
		}
		return levels;
	}

	/**
	 * 设置输入日志级别
	 * @param level
	 */
	public static void setLevel(CFLogLevel level) {
		levels = level.getOuts();
	}

	@Override
	protected boolean isFatalEnabled() {
		return (getLevels() & CFLogLevel.FATAL.getValue()) == CFLogLevel.FATAL.getValue();
	}

	@Override
	protected boolean isErrorEnabled() {
		return (getLevels() & CFLogLevel.ERROR.getValue()) == CFLogLevel.ERROR.getValue();
	}

	@Override
	protected boolean isWarnEnabled() {
		return (getLevels() & CFLogLevel.WARN.getValue()) == CFLogLevel.WARN.getValue();
	}

	@Override
	protected boolean isInfoEnabled() {
		return (getLevels() & CFLogLevel.INFO.getValue()) == CFLogLevel.INFO.getValue();
	}

	@Override
	protected boolean isDebugEnabled() {
		return (getLevels() & CFLogLevel.DEBUG.getValue()) == CFLogLevel.DEBUG.getValue();
	}

	@Override
	protected boolean isTraceEnabled() {
		return (getLevels() & CFLogLevel.TRACE.getValue()) == CFLogLevel.TRACE.getValue();
	}

	@Override
	protected void doFatal(String message) {
		this.log(CFLogLevel.FATAL, message);

	}

	@Override
	protected void doFatal(String message, Throwable throwable) {
		this.log(CFLogLevel.FATAL, message, throwable);

	}

	@Override
	protected void doError(String message) {
		this.log(CFLogLevel.ERROR, message);
	}

	@Override
	protected void doError(String message, Throwable throwable) {
		this.log(CFLogLevel.ERROR, message, throwable);
	}

	@Override
	protected void doWarn(String message) {
		this.log(CFLogLevel.WARN, message);
	}

	@Override
	protected void doWarn(String message, Throwable throwable) {
		this.log(CFLogLevel.WARN, message, throwable);
	}

	@Override
	protected void doInfo(String message) {
		this.log(CFLogLevel.INFO, message);

	}

	@Override
	protected void doInfo(String message, Throwable throwable) {
		this.log(CFLogLevel.INFO, message, throwable);
	}

	@Override
	protected void doDebug(String message) {
		this.log(CFLogLevel.DEBUG, message);
	}

	@Override
	protected void doDebug(String message, Throwable throwable) {
		this.log(CFLogLevel.DEBUG, message, throwable);
	}

	@Override
	protected void doTrace(String message) {
		this.log(CFLogLevel.TRACE, message);
	}

	@Override
	protected void doTrace(String message, Throwable throwable) {
		this.log(CFLogLevel.TRACE, message, throwable);
	}

	private void log(CFLogLevel level, String message) {
		if((getLevels() & level.getValue()) == level.getValue()) {
			System.out.println(String.format("%s - %s [%s] \r\n%s", format.format(//
				new Date()), this.getClass().getName(), level.name(), message));
		}
	}

	private void log(CFLogLevel level, String message, Throwable throwable) {
		if((getLevels() & level.getValue()) == level.getValue()) {
			System.out.println(String.format("%s - %s [%s] \r\n%s", format.format(//
				new Date()), this.getClass().getName(), level.name(), message));
			throwable.printStackTrace(System.out);
		}
	}
}
