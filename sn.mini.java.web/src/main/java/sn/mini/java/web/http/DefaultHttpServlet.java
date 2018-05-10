/**
 * Created the sn.mini.java.web.http.DefaultHttpServlet.java
 *
 * @created 2018年2月5日 上午11:44:32
 * @version 1.0.0
 */
package sn.mini.java.web.http;

import sn.mini.java.web.SNInitializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

/**
 * sn.mini.java.web.http.DefaultHttpServlet.java
 *
 * @author XChao
 */
public final class DefaultHttpServlet extends HttpServlet {
    private static final long serialVersionUID = -6021307577546368668L;

    private ActionProxy getActionProxy(HttpServletRequest req, HttpServletResponse response) throws IOException {
        SNHttpServletRequest request = new SNHttpServletRequest(req);
        ActionProxy proxy = SNInitializer.getActionProxy(request.getRequestURI());
        if (proxy == null) {
            for (String name : SNInitializer.getActionProxys().keySet()) {
                if (request.getRequestURI().matches(name)) {
                    proxy = SNInitializer.getActionProxy(name);
                    break;
                }
            }
        }
        if (proxy == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found Page.");
        }
        return proxy;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        SNHttpServletRequest request = new SNHttpServletRequest(req);
        ActionProxy actionProxy = getActionProxy(req, response);
        if (actionProxy == null) {
            response.sendError(SC_NOT_FOUND, "Not Found Page.");
            return;
        }
        actionProxy.getController().doDeleteBefore(request, response);
        actionProxy.getController().doService(actionProxy, request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        SNHttpServletRequest request = new SNHttpServletRequest(req);
        ActionProxy actionProxy = getActionProxy(req, response);
        if (actionProxy != null) {
            actionProxy.getController().doDeleteBefore(request, response);
            actionProxy.getController().doService(actionProxy, request, response);
        }
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        SNHttpServletRequest request = new SNHttpServletRequest(req);
        ActionProxy actionProxy = getActionProxy(req, response);
        if (actionProxy != null) {
            actionProxy.getController().doDeleteBefore(request, response);
            actionProxy.getController().doService(actionProxy, request, response);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        SNHttpServletRequest request = new SNHttpServletRequest(req);
        ActionProxy actionProxy = getActionProxy(req, response);
        if (actionProxy != null) {
            actionProxy.getController().doDeleteBefore(request, response);
            actionProxy.getController().doService(actionProxy, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        SNHttpServletRequest request = new SNHttpServletRequest(req);
        ActionProxy actionProxy = getActionProxy(req, response);
        if (actionProxy != null) {
            actionProxy.getController().doDeleteBefore(request, response);
            actionProxy.getController().doService(actionProxy, request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        SNHttpServletRequest request = new SNHttpServletRequest(req);
        ActionProxy actionProxy = getActionProxy(req, response);
        if (actionProxy != null) {
            actionProxy.getController().doDeleteBefore(request, response);
            actionProxy.getController().doService(actionProxy, request, response);
        }
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        SNHttpServletRequest request = new SNHttpServletRequest(req);
        ActionProxy actionProxy = getActionProxy(req, response);
        if (actionProxy != null) {
            actionProxy.getController().doDeleteBefore(request, response);
            actionProxy.getController().doService(actionProxy, request, response);
        }
    }
}
