/**
 * Created the com.cfinal.web.render.CFRender.java
 * @created 2016年9月24日 下午7:44:26
 * @version 1.0.0
 */
package com.cfinal.web.http.render;

import java.io.IOException;

import com.cfinal.util.logger.CFLog;
import com.cfinal.web.CFService;
import com.cfinal.web.CFServletContext;
import com.cfinal.web.http.CFHttpServletRequest;
import com.cfinal.web.http.CFHttpServletResponse;
import com.cfinal.web.http.view.CFView;
import com.cfinal.web.model.CFModel;

/**
 * 页面渲染器，用于渲染action的数据到页面。 用户自定义的页面渲染器必须实现改接口
 * @author XChao
 */
public abstract class CFRender implements CFService {

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
			CFLog.debug("Scanner CFRender： " + this.getClass().getName());
		} catch (Throwable throwable) {
			CFLog.error(this.getClass().getName() + ".onStartup error. ", throwable);
		}
	}

	/**
	 * 页面渲染方法
	 * @param model 视图和数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public abstract void render(CFModel model, CFView view, String viewPath, CFHttpServletRequest request, //
		CFHttpServletResponse response) throws Exception;
}
