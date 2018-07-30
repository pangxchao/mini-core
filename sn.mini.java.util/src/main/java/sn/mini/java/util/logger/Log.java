/**
 * Created the sn.mini.util.logger.Log.java
 *
 * @created 2017年8月15日 上午11:03:54
 * @version 1.0.0
 */
package sn.mini.java.util.logger;

import sn.mini.java.util.lang.ClassUtil;

/**
 * 日志工具类
 *
 * @author XChao
 */
public abstract class Log {
	private static ILog instance = null, def = new SNLog();

	/**
	 * 获取当前类的实例
	 *
	 * @return
	 */
	private static ILog getInstance() {
		return instance == null ? def : instance;
	}

	/**
	 * 设置输出日志实现类
	 *
	 * @param logger
	 */
	public static void setInstance(Class<? extends ILog> logger) {
		Log.instance = ClassUtil.newInstance(logger);
	}

	/**
	 * 输出日志
	 *
	 * @param message
	 */
	public static void fatal(String message) {
		if (getInstance().isFatalEnabled()) {
			StackTraceElement element = getStackTraceElement(new Throwable());
			getInstance().doFatal(getMessageByElement(element, message));
		}
	}

	/**
	 * 输出日志
	 *
	 * @param message
	 * @param throwable
	 */
	public static void fatal(String message, Throwable throwable) {
		if (getInstance().isFatalEnabled()) {
			StackTraceElement element = getStackTraceElement(new Throwable());
			getInstance().doFatal(getMessageByElement(element, message), throwable);
		}
	}

	/**
	 * 输出日志
	 *
	 * @param message
	 */
	public static void error(String message) {
		if (getInstance().isErrorEnabled()) {
			StackTraceElement element = getStackTraceElement(new Throwable());
			getInstance().doError(getMessageByElement(element, message));
		}
	}

	/**
	 * 输出日志
	 *
	 * @param message
	 * @param throwable
	 */
	public static void error(String message, Throwable throwable) {
		if (getInstance().isErrorEnabled()) {
			StackTraceElement element = getStackTraceElement(new Throwable());
			getInstance().doError(getMessageByElement(element, message), throwable);
		}
	}

	/**
	 * 输出日志
	 *
	 * @param message
	 */
	public static void warn(String message) {
		if (getInstance().isWarnEnabled()) {
			StackTraceElement element = getStackTraceElement(new Throwable());
			getInstance().doWarn(getMessageByElement(element, message));
		}
	}

	/**
	 * 输出日志
	 *
	 * @param message
	 * @param throwable
	 */
	public static void warn(String message, Throwable throwable) {
		if (getInstance().isWarnEnabled()) {
			StackTraceElement element = getStackTraceElement(new Throwable());
			getInstance().doWarn(getMessageByElement(element, message), throwable);
		}
	}

	/**
	 * 输出日志
	 *
	 * @param message
	 */
	public static void info(String message) {
		if (getInstance().isInfoEnabled()) {
			StackTraceElement element = getStackTraceElement(new Throwable());
			getInstance().doInfo(getMessageByElement(element, message));
		}
	}

	/**
	 * 输出日志
	 *
	 * @param message
	 * @param throwable
	 */
	public static void info(String message, Throwable throwable) {
		if (getInstance().isInfoEnabled()) {
			StackTraceElement element = getStackTraceElement(new Throwable());
			getInstance().doInfo(getMessageByElement(element, message), throwable);
		}
	}

	/**
	 * 输出日志
	 *
	 * @param message
	 */
	public static void debug(String message) {
		if (getInstance().isDebugEnabled()) {
			StackTraceElement element = getStackTraceElement(new Throwable());
			getInstance().doDebug(getMessageByElement(element, message));
		}
	}

	/**
	 * 输出日志
	 *
	 * @param message
	 * @param throwable
	 */
	public static void debug(String message, Throwable throwable) {
		if (getInstance().isDebugEnabled()) {
			StackTraceElement element = getStackTraceElement(new Throwable());
			getInstance().doDebug(getMessageByElement(element, message), throwable);
		}
	}

	/**
	 * 输出日志
	 *
	 * @param message
	 */
	public static void trace(String message) {
		if (getInstance().isTraceEnabled()) {
			StackTraceElement element = getStackTraceElement(new Throwable());
			getInstance().doTrace(getMessageByElement(element, message));
		}
	}

	/**
	 * 输出日志
	 *
	 * @param message
	 * @param throwable
	 */
	public static void trace(String message, Throwable throwable) {
		if (getInstance().isTraceEnabled()) {
			StackTraceElement element = getStackTraceElement(new Throwable());
			getInstance().doTrace(getMessageByElement(element, message), throwable);
		}
	}

	private static StackTraceElement getStackTraceElement(Throwable throwable) {
		StackTraceElement[] elements = throwable.getStackTrace();
		return elements.length > 0 ? elements[1] : elements[0];
	}

	private static String getMessageByElement(StackTraceElement element, String message) {
		StringBuilder build = new StringBuilder().append(element.getClassName()).append(".");
		build.append(element.getMethodName()).append("[Line:").append(element.getLineNumber());
		return build.append("] - ").append(message).toString();
	}
}
