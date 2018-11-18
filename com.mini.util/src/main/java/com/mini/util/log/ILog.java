package com.mini.util.log;

/**   
 * sn.mini.util.logger.ILog.java 
 * @author XChao  
 */
public interface ILog {
	boolean isFatalEnabled();

	boolean isErrorEnabled();

	boolean isWarnEnabled();

	boolean isInfoEnabled();

	boolean isDebugEnabled();

	boolean isTraceEnabled();

	void doFatal(String message);

	void doFatal(String message, Throwable throwable);

	void doError(String message);

	void doError(String message, Throwable throwable);

	void doWarn(String message);

	void doWarn(String message, Throwable throwable);

	void doInfo(String message);

	void doInfo(String message, Throwable throwable);

	void doDebug(String message);

	void doDebug(String message, Throwable throwable);

	void doTrace(String message);

	void doTrace(String message, Throwable throwable);
}
