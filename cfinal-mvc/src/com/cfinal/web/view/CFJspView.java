/**
 * Created the com.cfinal.web.view.CFJspView.java
 * @created 2017年2月11日 下午12:40:02
 * @version 1.0.0
 */
package com.cfinal.web.view;

import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;
import com.cfinal.web.central.CFContext;
import com.cfinal.web.central.CFInitialize;
import com.cfinal.web.entity.CFUser;
import com.cfinal.web.model.CFModel;

/**
 * com.cfinal.web.view.CFJspView.java
 * @author XChao
 */
public class CFJspView implements CFView {
	private CFContext context = CFInitialize.getContext();

	@Override
	public void generator(CFModel model, String viewPath, CFRequest request, CFResponse response) throws Exception {
		request.setAttribute("error", model.getError());
		request.setAttribute("message", model.getMessage());
		for (String key : model.keySet()) {
			request.setAttribute(key, model.getData(key));
		}
		request.setAttribute("sysBasePath", context.getWebRootPath());
		request.setAttribute("iuser", request.getSession().getAttribute(CFUser.USER_KEY));
		request.getRequestDispatcher("/WEB-INF/" + viewPath).forward(request, response);
	}

}
