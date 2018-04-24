/**
 * Created the sn.mini.java.web.http.DefaultHttpServlet.java
 * @created 2018年2月5日 上午11:44:32
 * @version 1.0.0
 */
package sn.mini.java.web.http;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static sn.mini.java.web.SNInitializer.getActionProxy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * sn.mini.java.web.http.DefaultHttpServlet.java
 * @author XChao
 */
public final class DefaultHttpServlet extends HttpServlet {
	private static final long serialVersionUID = -6021307577546368668L;

	private Controller getController(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		SNHttpServletRequest request = new SNHttpServletRequest(req);
		ActionProxy proxy = getActionProxy(request.getRequestURI());
		if (proxy != null && proxy.getController() != null) { return proxy.getController(); }
		response.sendError(SC_NOT_FOUND, "Not Found Page.");
		return null;
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		SNHttpServletRequest request = new SNHttpServletRequest(req);
		Controller controller = getController(req, response);
		if (controller == null) { return; }
		controller.doDeleteBefore(request, response);
		controller.doService(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		SNHttpServletRequest request = new SNHttpServletRequest(req);
		Controller controller = getController(req, response);
		if (controller == null) { return; }
		controller.doDeleteBefore(request, response);
		controller.doService(request, response);
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		SNHttpServletRequest request = new SNHttpServletRequest(req);
		Controller controller = getController(req, response);
		if (controller == null) { return; }
		controller.doDeleteBefore(request, response);
		controller.doService(request, response);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		SNHttpServletRequest request = new SNHttpServletRequest(req);
		Controller controller = getController(req, response);
		if (controller == null) { return; }
		controller.doDeleteBefore(request, response);
		controller.doService(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		SNHttpServletRequest request = new SNHttpServletRequest(req);
		Controller controller = getController(req, response);
		if (controller == null) { return; }
		controller.doDeleteBefore(request, response);
		controller.doService(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		SNHttpServletRequest request = new SNHttpServletRequest(req);
		Controller controller = getController(req, response);
		if (controller == null) { return; }
		controller.doDeleteBefore(request, response);
		controller.doService(request, response);
	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		SNHttpServletRequest request = new SNHttpServletRequest(req);
		Controller controller = getController(req, response);
		if (controller == null) { return; }
		controller.doDeleteBefore(request, response);
		controller.doService(request, response);
	}

}
