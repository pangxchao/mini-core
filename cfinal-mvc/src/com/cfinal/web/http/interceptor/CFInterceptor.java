/**
 * Created the com.cfinal.web.interceptor.CFInterceptor.java
 * @created 2016年9月24日 下午7:58:12
 * @version 1.0.0
 */
package com.cfinal.web.http.interceptor;

import com.cfinal.util.logger.CFLog;
import com.cfinal.web.CFService;
import com.cfinal.web.CFServletContext;
import com.cfinal.web.http.CFActionInvoke;

/**
 * 过虑器(拦截器)接口
 * @author XChao
 */
public abstract class CFInterceptor implements CFService {

	/**
	 * 该方法会在 CFServletContextListener.contextInitialized方法之前执行
	 * @param context
	 * @throws Exception
	 */
	protected void initialized(CFServletContext context) throws Exception {
	}

	public final void onStartup(CFServletContext context) throws Exception {
		try {
			context.addBean(this);
			this.initialized(context);
			CFLog.debug("Scanner CFInterceptor： " + this.getClass().getName());
		} catch (Throwable throwable) {
			CFLog.error(this.getClass().getName() + ".onStartup error. ", throwable);
		}
	}

	/**
	 * 过虑器执行方法
	 * @param invocation
	 * @throws Exception
	 */
	public abstract String intercept(CFActionInvoke invocation) throws Exception;

}
