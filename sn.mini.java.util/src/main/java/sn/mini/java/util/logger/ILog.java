/**   
 * Created the sn.mini.util.logger.ILog.java
 * @created 2017年11月22日 上午10:41:47 
 * @version 1.0.0 
 */
package sn.mini.java.util.logger;

/**   
 * sn.mini.util.logger.ILog.java 
 * @author XChao  
 */
public interface ILog {
	public abstract boolean isFatalEnabled();

	public abstract boolean isErrorEnabled();

	public abstract boolean isWarnEnabled();

	public abstract boolean isInfoEnabled();

	public abstract boolean isDebugEnabled();

	public abstract boolean isTraceEnabled();

	public abstract void doFatal(String message);

	public abstract void doFatal(String message, Throwable throwable);

	public abstract void doError(String message);

	public abstract void doError(String message, Throwable throwable);

	public abstract void doWarn(String message);

	public abstract void doWarn(String message, Throwable throwable);

	public abstract void doInfo(String message);

	public abstract void doInfo(String message, Throwable throwable);

	public abstract void doDebug(String message);

	public abstract void doDebug(String message, Throwable throwable);

	public abstract void doTrace(String message);

	public abstract void doTrace(String message, Throwable throwable);
}
