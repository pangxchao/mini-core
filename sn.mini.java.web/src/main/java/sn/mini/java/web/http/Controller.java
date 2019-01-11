/**
 * Created the sn.mini.java.web.http.Controller.java
 *
 * @created 2017年10月9日 下午5:45:10
 * @version 1.0.0
 */
package sn.mini.java.web.http;

import sn.mini.java.jdbc.DaoManager;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.util.lang.StringUtil;
import sn.mini.java.util.logger.Log;
import sn.mini.java.web.SNInitializer;
import sn.mini.java.web.editor.EditorBind;
import sn.mini.java.web.http.rander.Json;
import sn.mini.java.web.model.IModel;
import sn.mini.java.web.model.ModelFactory;
import sn.mini.java.web.util.IUser;
import sn.mini.java.web.util.WebUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map.Entry;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * sn.mini.java.web.http.Controller.java
 *
 * @author XChao
 */
public abstract class Controller {

    public void doDeleteBefore(SNHttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doGetBefore(SNHttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doHeadBefore(SNHttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doOptionsBefore(SNHttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doPostBefore(SNHttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doPutBefore(SNHttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doTraceBefore(SNHttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public final void doService(ActionProxy proxy, SNHttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            IModel model = ModelFactory.createDefaultModel(request); // 创建model对象
            String viewPath = this.loginInterceptor(proxy, model, request, response);
            proxy.getRender().render(model, proxy.getView(), viewPath, request, response);
        } catch (Throwable throwable) {
            Log.error("Service response error.", throwable);
            // 如果response 未提交时， 才写入错误状态码
            if (!response.isCommitted()) {
                response.sendError(SC_INTERNAL_SERVER_ERROR, throwable.getMessage());
            }
        }
    }

    /**
     * 登录验证, 异常处理, 并调用参数绑定方法
     *
     * @param proxy    ActionProxy 对象
     * @param model    数据模型对象
     * @param request  DefaultRequest 对象
     * @param response DefaultResponse 对象
     * @return
     * @throws Exception
     */
    protected final String loginInterceptor(ActionProxy proxy, IModel model, SNHttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            IUser user = WebUtil.getAttribute(request.getSession(), IUser.USER_KEY, IUser.class);
            if (proxy.getAction().login() && user == null) {
                if (Json.class.isAssignableFrom(proxy.getAction().value())) {
                    model.setError(500).setMessage("Not login error.");
                    return null;
                }
                String loginUrl, queryString = request.getQueryString(), backUrlEncode;
                queryString = StringUtil.defaultIfEmpty(queryString, "");
                if (StringUtil.isNotBlank(loginUrl = SNInitializer.getLoginUrl())) {
                    backUrlEncode = StringUtil.urlEncode(StringUtil.join(WebUtil.getDoMain(request), proxy.getName(), "?", queryString), SNInitializer.getEncoding());
                    if (loginUrl.toLowerCase().startsWith("http://") || loginUrl.toLowerCase().startsWith("https://")) {
                        // 如果登录地址是完整的http或者https协议的地址时，将其切换成访问的协议
                        loginUrl = request.getScheme() + loginUrl.substring(5);
                        return StringUtil.join("r:", loginUrl, "?uri=", backUrlEncode);
                    }
                    return StringUtil.join("r:", loginUrl, "?uri=", backUrlEncode);
                }
                throw new ServletException("Not login error.");
            }
            return paramInterceptor(proxy, model, request, response);
        } catch (Throwable throwable) {
            if (Json.class.isAssignableFrom(proxy.getAction().value())) {
                // 如果model.error == 0 时,说明没有设置错误信息,强制设置错误码为500, 错误信息为异常信息
                if (model.getError() == 0) {
                    model.setError(500).setMessage(throwable.getMessage());
                    Log.error("Service response error. ", throwable);
                }
                return null;
            }
            model.setMessage(model.getError() != 0 ? model.getMessage() : throwable.getMessage());
            throw new ServletException(StringUtil.defaultIfEmpty(model.getMessage(), "Service response error."), throwable);
        }
    }

    /**
     * 绑定参数, 创建数据库连接, 并调用Action调用方法
     *
     * @param proxy    ActionProxy 对象
     * @param model    数据模型对象
     * @param request  DefaultRequest 对象
     * @param response DefaultResponse 对象
     * @return
     * @throws Exception
     */
    protected final String paramInterceptor(ActionProxy proxy, IModel model, SNHttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            for (int i = 0, j = proxy.getParameters().length; i < j; i++) {
                proxy.setParameterValue(i, EditorBind.getEditor(proxy.getParameter(i).getType()).value(proxy.getParameter(i).getName(), proxy.getParameter(i).getType(), request, response));
            }
            ActionInvoke invoke = new ActionInvoke(proxy, request, response, model);
            return invoke.invoke();
        } finally {
            for (Entry<String, IDao> entry : DaoManager.getCurrentDao().entrySet()) {
                try (IDao dao = entry.getValue()) {
                } catch (Exception e) {
                    Log.error("Close Dao fail. ", e);
                }
            }
        }
    }
}
