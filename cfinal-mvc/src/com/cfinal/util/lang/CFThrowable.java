/**
 * Created the com.cfinal.util.lang.CFThrowable.java
 * @created 2017年5月24日 下午6:14:58
 * @version 1.0.0
 */
package com.cfinal.util.lang;

/**
 * 异常对象工具类
 * @author XChao
 */
public class CFThrowable {

	/**
	 * 获取异常的最后一个cause
	 * @param throwable
	 * @return Throwable
	 */
	public static Throwable getLastCause(Throwable throwable) {
		Throwable throwable1;
		do {
			throwable1 = throwable;
			throwable = throwable.getCause();
		} while (throwable != null);
		return throwable1;
	}
}
