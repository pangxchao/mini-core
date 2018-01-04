/**
 * Created the sn.mini.web.http.interceptor.PermissInterceptor.java
 * @created 2017年11月17日 下午3:14:09
 * @version 1.0.0
 */
package sn.mini.web.http.interceptor;

import sn.mini.web.http.ActionInvoke;

/**
 * sn.mini.web.http.interceptor.PermissInterceptor.java
 * @author XChao
 */
public abstract class PermissInterceptor implements Interceptor {

	@Override
	public String interceptor(ActionInvoke invoke) throws Exception {
		invoke.getModel().validate(this.validate(invoke), 505, "对不起! 您没有该功能的操作权限.");
		return invoke.invoke();
	}

	protected abstract boolean validate(ActionInvoke invoke);
}
