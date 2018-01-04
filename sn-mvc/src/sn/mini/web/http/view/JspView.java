/**
 * Created the sn.mini.web.http.view.JspView.java
 * @created 2017年10月25日 下午4:15:37
 * @version 1.0.0
 */
package sn.mini.web.http.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sn.mini.web.model.IModel;

/**
 * sn.mini.web.http.view.JspView.java
 * @author XChao
 */
public class JspView implements IView {
	@Override
	public void generator(IModel model, String viewPath, HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		request.setAttribute("error", model.getError());
		request.setAttribute("message", model.getMessage());
		for (String key : model.keySet()) {
			request.setAttribute(key, model.getData(key));
		}
		request.getRequestDispatcher("/WEB-INF/" + viewPath + ".jsp").forward(request, response);
	}
}
