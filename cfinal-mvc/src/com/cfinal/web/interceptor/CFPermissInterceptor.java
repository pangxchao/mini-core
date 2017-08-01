/**
 * Created the com.cfinal.web.interceptor.CFPermissInterceptor.java
 * @created 2017年2月3日 上午10:00:53
 * @version 1.0.0
 */
package com.cfinal.web.interceptor;

import com.cfinal.web.annotaion.CFAction;
import com.cfinal.web.central.CFInvocation;
import com.cfinal.web.entity.CFUser;
import com.cfinal.web.model.CFModel;
import com.cfinal.web.preprocessor.CFMenuPreprocessor;

/**
 * com.cfinal.web.interceptor.CFPermissInterceptor.java
 * @author XChao
 */
public abstract class CFPermissInterceptor implements CFInterceptor {

	protected CFMenuPreprocessor getInstance() {
		return (CFMenuPreprocessor) (getContext().getPreprocessorPorxy().getPreprocessor());
	}

	@Override
	public String intercept(CFInvocation invocation) throws Exception {
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

	protected abstract boolean validate(CFInvocation invocation, CFModel model, CFUser user, CFAction action);
}
