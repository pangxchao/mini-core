/**
 * Created the com.cfinal.web.http.view.CFJspView.java
 * @created 2017年2月11日 下午12:40:02
 * @version 1.0.0
 */
package com.cfinal.web.http.view;

import com.cfinal.web.http.CFHttpServletRequest;
import com.cfinal.web.http.CFHttpServletResponse;
import com.cfinal.web.model.CFModel;
import com.cfinal.web.model.CFUser;

/**
 * com.cfinal.web.http.view.CFJspView.java
 * @author XChao
 */
public class CFJspView extends CFView {
	private static final String FILE_SUFFIX = ".jsp";

	@Override
	public void generator(CFModel model, String viewPath, CFHttpServletRequest request, //
		CFHttpServletResponse response) throws Exception {
		request.setAttribute("error", model.getError());
		request.setAttribute("message", model.getMessage());
		for (String key : model.keySet()) {
			request.setAttribute(key, model.getData(key));
		}
		request.setAttribute("sysBasePath", getContext().getWebRootPath());
		request.setAttribute("iuser", request.getSession().getAttribute(CFUser.USER_KEY));
		request.getRequestDispatcher("/WEB-INF/" + viewPath + FILE_SUFFIX).forward(request, response);
	}

}
