/**
 * Created the com.cfinal.util.lang.CFThrowable.java
 * @created 2017年5月24日 下午6:14:58
 * @version 1.0.0
 */
package sn.mini.java.util.lang;

import java.util.Optional;

/**
 * 异常对象工具类
 * @author XChao
 */
public class ThrowableUtil {

	/**
	 * 获取异常的最后一个cause
	 * @param throwable
	 * @return Throwable
	 */
	public static Throwable getLastCause(Throwable throwable) {
		return Optional.ofNullable(throwable.getCause()).map(v -> getLastCause(v)).orElse(throwable);
	}

	/**
	 * 获取异常的上一个cause, 通常在反射调用的时候获取上一个cause的异常消息
	 * @param throwable
	 * @return
	 */
	public static Throwable getPrevCause(Throwable throwable) {
		return Optional.ofNullable(throwable.getCause()).map(v -> v).orElse(throwable);
	}
}
