/**
 * Created the sn.mini.java.web.http.rander.Page.java
 * @created 2017年10月25日 下午4:51:20
 * @version 1.0.0
 */
package sn.mini.java.web.http.rander;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sn.mini.java.util.lang.StringUtil;
import sn.mini.java.web.SNInitializer;
import sn.mini.java.web.http.view.IView;
import sn.mini.java.web.model.IModel;

/**
 * sn.mini.java.web.http.rander.Page.java
 * @author XChao
 */
public final class Page implements IRender {

	@Override
	public void render(IModel model, IView view, String viewPath, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		response.setContentType("text/html"); // 返回数据格式处理
		if(StringUtil.isNotBlank(model.getContentType())) {
			response.setContentType(model.getContentType());
		}

		if(viewPath.trim().startsWith("f:")) { // 请求转发处理
			viewPath = StringUtil.join("/", viewPath.trim().substring(2).trim());
			request.getRequestDispatcher(viewPath).forward(request, response);
			return;
		}
		// 重定向处理
		if(viewPath.trim().startsWith("r:")) {
			viewPath = viewPath.trim().substring(2).trim();
			if(Pattern.compile("([a-zA-Z]{2,}://\\S+)+").matcher(viewPath).matches()) {
				response.sendRedirect(viewPath);
				return;
			}
			response.sendRedirect(StringUtil.join(SNInitializer.getWebRootPath(), viewPath));
			return;
		}
		// 生成页面
		view.generator(model, viewPath, request, response);
	}

}
