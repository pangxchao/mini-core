/**
 * Created the sn.mini.java.web.http.view.JspView.java
 * @created 2017年10月25日 下午4:15:37
 * @version 1.0.0
 */
package sn.mini.java.web.http.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sn.mini.java.web.model.IModel;

/**
 * sn.mini.java.web.http.view.JspView.java
 * @author XChao
 */
public class JspView implements IView {
	@Override
	public void generator(IModel model, String viewPath, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("error", model.getError());
		request.setAttribute("message", model.getMessage());
		for (String attrName : model.keySet()) {
			request.setAttribute(attrName, model.getData(attrName));
		}
		request.getRequestDispatcher("/WEB-INF/" + viewPath + ".jsp").forward(request, response);
	}
}
