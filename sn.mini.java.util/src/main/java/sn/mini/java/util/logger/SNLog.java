/**
 * Created the sn.mini.util.logger.MiniLog.java
 * @created 2016年9月20日 上午11:06:45
 * @version 1.0.0
 */
package sn.mini.java.util.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义日志实现
 * @author XChao
 */
public class SNLog implements ILog {
	private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	// 输出日志级别
	private static Level level = null, def = Level.DEBUG;

	private static int getLevels() {
		return level == null ? def.getOuts() : level.getOuts();
	}

	/**
	 * 设置输入日志级别
	 * @param level
	 */
	public static void setLevel(Level level) {
		SNLog.level = level;
	}

	@Override
	public boolean isFatalEnabled() {
		return (getLevels() & Level.FATAL.getValue()) == Level.FATAL.getValue();
	}

	@Override
	public boolean isErrorEnabled() {
		return (getLevels() & Level.ERROR.getValue()) == Level.ERROR.getValue();
	}

	@Override
	public boolean isWarnEnabled() {
		return (getLevels() & Level.WARN.getValue()) == Level.WARN.getValue();
	}

	@Override
	public boolean isInfoEnabled() {
		return (getLevels() & Level.INFO.getValue()) == Level.INFO.getValue();
	}

	@Override
	public boolean isDebugEnabled() {
		return (getLevels() & Level.DEBUG.getValue()) == Level.DEBUG.getValue();
	}

	@Override
	public boolean isTraceEnabled() {
		return (getLevels() & Level.TRACE.getValue()) == Level.TRACE.getValue();
	}

	@Override
	public void doFatal(String message) {
		this.log(Level.FATAL, message);

	}

	@Override
	public void doFatal(String message, Throwable throwable) {
		this.log(Level.FATAL, message, throwable);

	}

	@Override
	public void doError(String message) {
		this.log(Level.ERROR, message);
	}

	@Override
	public void doError(String message, Throwable throwable) {
		this.log(Level.ERROR, message, throwable);
	}

	@Override
	public void doWarn(String message) {
		this.log(Level.WARN, message);
	}

	@Override
	public void doWarn(String message, Throwable throwable) {
		this.log(Level.WARN, message, throwable);
	}

	@Override
	public void doInfo(String message) {
		this.log(Level.INFO, message);

	}

	@Override
	public void doInfo(String message, Throwable throwable) {
		this.log(Level.INFO, message, throwable);
	}

	@Override
	public void doDebug(String message) {
		this.log(Level.DEBUG, message);
	}

	@Override
	public void doDebug(String message, Throwable throwable) {
		this.log(Level.DEBUG, message, throwable);
	}

	@Override
	public void doTrace(String message) {
		this.log(Level.TRACE, message);
	}

	@Override
	public void doTrace(String message, Throwable throwable) {
		this.log(Level.TRACE, message, throwable);
	}

	private void log(Level level, String message) {
		if((getLevels() & level.getValue()) == level.getValue()) {
			System.out.println(String.format("%s - %s [%s] \r\n%s", format.format(//
				new Date()), this.getClass().getName(), level.name(), message));
		}
	}

	private void log(Level level, String message, Throwable throwable) {
		if((getLevels() & level.getValue()) == level.getValue()) {
			System.out.println(String.format("%s - %s [%s] \r\n%s", format.format(//
				new Date()), this.getClass().getName(), level.name(), message));
			throwable.printStackTrace(System.out);
		}
	}
}
