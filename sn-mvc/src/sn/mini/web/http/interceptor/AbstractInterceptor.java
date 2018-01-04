/**
 * Created the sn.mini.web.http.interceptor.AbstractInterceptor.java
 * @created 2017年11月17日 下午3:10:00
 * @version 1.0.0
 */
package sn.mini.web.http.interceptor;

import sn.mini.web.http.ActionInvoke;

/**
 * sn.mini.web.http.interceptor.AbstractInterceptor.java
 * @author XChao
 */
public abstract class AbstractInterceptor implements Interceptor {
	/**
	 * 前置拦截器
	 * @param invocation
	 * @return The invoking next interceptor or invoking action return true, else return false.
	 */
	protected boolean before(ActionInvoke invoke) {
		return true;
	}

	@Override
	public final String interceptor(ActionInvoke invoke) throws Exception {
		if(this.before(invoke)) {
			String result = invoke.invoke();
			this.after(invoke);
			return result;
		}
		return null;
	}

	/**
	 * 后置拦截器, 执行Action 返回时, 执行的代码
	 * @param invocation
	 */
	protected void after(ActionInvoke invoke) {
	}
}
