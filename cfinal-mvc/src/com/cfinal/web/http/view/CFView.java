/**
 * Created the com.xcc.web.view.CFView.CFView.java
 * @created 2017年2月11日 上午11:56:46
 * @version 1.0.0
 */
package com.cfinal.web.http.view;

import com.cfinal.util.logger.CFLog;
import com.cfinal.web.CFService;
import com.cfinal.web.CFServletContext;
import com.cfinal.web.http.CFHttpServletRequest;
import com.cfinal.web.http.CFHttpServletResponse;
import com.cfinal.web.model.CFModel;

/**
 * com.xcc.web.view.CFView.CFView.java
 * @author XChao
 */
public abstract class CFView implements CFService {

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
			context.setViewClazz(this);
			CFLog.debug("Scanner CFView： " + this.getClass().getName());
		} catch (Throwable throwable) {
			CFLog.error(this.getClass().getName() + ".onStartup error. ", throwable);
		}
	}

	/**
	 * 生成页面内容，并写入response
	 * @param model
	 * @param viewPath
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public abstract void generator(CFModel model, String viewPath, //
		CFHttpServletRequest request, CFHttpServletResponse response) throws Exception;
}
