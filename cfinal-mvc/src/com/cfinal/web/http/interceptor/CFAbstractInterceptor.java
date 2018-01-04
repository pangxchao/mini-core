/**
 * Created the com.cfinal.web.interceptor.CFAbstractInterceptor.java
 * @created 2016年10月13日 上午10:57:39
 * @version 1.0.0
 */
package com.cfinal.web.http.interceptor;

import com.cfinal.web.http.CFActionInvoke;

/**
 * com.cfinal.web.interceptor.CFAbstractInterceptor.java
 * @author XChao
 */
public abstract class CFAbstractInterceptor extends CFInterceptor {

	/**
	 * 前置拦截器
	 * @param invocation
	 * @return The invoking next nterceptor or invoking action return true, else return false.
	 */
	protected boolean before(CFActionInvoke invocation) {
		return true;
	}

	@Override
	public final String intercept(CFActionInvoke invocation) throws Exception {
		String result = null;
		if(this.before(invocation)) {
			result = invocation.invoke();
			this.after(invocation);
		}
		return result;
	}

	/**
	 * 后置拦截器, 执行Action 返回时, 执行的代码
	 * @param invocation
	 */
	protected void after(CFActionInvoke invocation) {

	}
}
