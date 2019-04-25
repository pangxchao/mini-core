package sn.mini.java.web.http.interceptor;

import sn.mini.java.web.http.ActionInvoke;

/**
 * sn.mini.java.web.http.interceptor.PermissInterceptor.java
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
