/**
 * Created the com.cfinal.web.interceptor.CFAbstractInterceptor.java
 * @created 2016年10月13日 上午10:57:39
 * @version 1.0.0
 */
package com.cfinal.web.interceptor;

import com.cfinal.web.central.CFInvocation;

/**
 * com.cfinal.web.interceptor.CFAbstractInterceptor.java
 * @author XChao
 */
public abstract class CFAbstractInterceptor implements CFInterceptor {

	/**
	 * 前置拦截器
	 * @param invocation
	 * @return The invoking next nterceptor or invoking action return true, else return false.
	 */
	public abstract boolean before(CFInvocation invocation);

	@Override
	public String intercept(CFInvocation invocation) throws Exception {
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
	public abstract void after(CFInvocation invocation);
}
