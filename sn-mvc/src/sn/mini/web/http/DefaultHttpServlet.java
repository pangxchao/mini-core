/**
 * Created the sn.mini.web.http.DefaultHttpServlet.java
 * @created 2018年2月5日 上午11:44:32
 * @version 1.0.0
 */
package sn.mini.web.http;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static sn.mini.web.SNInitializer.getActionProxy;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sn.mini.util.logger.Log;

/**
 * sn.mini.web.http.DefaultHttpServlet.java
 * @author XChao
 */
@WebServlet(asyncSupported = true, name = "sn.mini.default.servlet", value = "/*", description = "defaultServlet")
public final class DefaultHttpServlet extends HttpServlet {
	private static final long serialVersionUID = -6021307577546368668L;

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse response) //
		throws ServletException, IOException {
		try {
			SNHttpServletRequest request = new SNHttpServletRequest(req);
			ActionProxy proxy = getActionProxy(request.getRequestURI());

			// 验证控制器是否存在，如果不存在时，则返回 404 未找到页面的错误信息
			if(Objects.isNull(proxy)) {
				response.sendError(SC_NOT_FOUND, "Not Found Page.");
				return;
			}

			Controller controller = proxy.getController();
			controller.doDeleteBefore(request, response);

			this.doService(request, response);
		} catch (Throwable throwable) {
			Log.error("Internal Server Error Page.", throwable);
			if(response.isCommitted()) {
				return;
			}
			response.sendError(SC_INTERNAL_SERVER_ERROR, "Internal Server Error Page.");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) //
		throws ServletException, IOException {

		SNHttpServletRequest request = new SNHttpServletRequest(req);
		this.doService(request, response);
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse response) //
		throws ServletException, IOException {

		SNHttpServletRequest request = new SNHttpServletRequest(req);
		this.doService(request, response);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse response) //
		throws ServletException, IOException {

		SNHttpServletRequest request = new SNHttpServletRequest(req);
		this.doService(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response) //
		throws ServletException, IOException {
		SNHttpServletRequest request = new SNHttpServletRequest(req);
		this.doService(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse response) //
		throws ServletException, IOException {

		SNHttpServletRequest request = new SNHttpServletRequest(req);
		this.doService(request, response);
	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse response) //
		throws ServletException, IOException {

		SNHttpServletRequest request = new SNHttpServletRequest(req);
		this.doService(request, response);
	}

	protected void doService(SNHttpServletRequest request, HttpServletResponse response) //
		throws ServletException, IOException {

	}

}
