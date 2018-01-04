/**
 * Created the com.cfinal.web.interceptor.CFPermissInterceptor.java
 * @created 2017年2月3日 上午10:00:53
 * @version 1.0.0
 */
package com.cfinal.web.http.interceptor;

import com.cfinal.web.annotaion.CFAction;
import com.cfinal.web.http.CFActionInvoke;
import com.cfinal.web.model.CFModel;
import com.cfinal.web.model.CFUser;

/**
 * com.cfinal.web.interceptor.CFPermissInterceptor.java
 * @author XChao
 */
public abstract class CFPermissInterceptor extends CFInterceptor {

	@Override
	public String intercept(CFActionInvoke invocation) throws Exception {
		CFAction action = invocation.getAction();
		if(action != null && action.permiss() > 0) {
			CFUser user = (CFUser) invocation.getUser();
			if(user == null) {
				throw new RuntimeException("对不起! 您没有该功能的操作权限.");
			}
			if(action.menuId() > 0) {
				if(this.validate(invocation, invocation.getModel(), user, action) == false) {
					throw new RuntimeException("对不起! 您没有该功能的操作权限.");
				}
			}
		}
		return invocation.invoke();
	}

	protected abstract boolean validate(CFActionInvoke invocation, CFModel model, CFUser user, CFAction action);
}
