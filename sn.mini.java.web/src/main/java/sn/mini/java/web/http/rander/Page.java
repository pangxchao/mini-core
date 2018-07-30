/**
 * Created the sn.mini.java.web.http.rander.Page.java
 *
 * @created 2017年10月25日 下午4:51:20
 * @version 1.0.0
 */
package sn.mini.java.web.http.rander;

import sn.mini.java.util.lang.StringUtil;
import sn.mini.java.web.SNInitializer;
import sn.mini.java.web.http.view.IView;
import sn.mini.java.web.model.IModel;
import sn.mini.java.web.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class Page implements IRender {

    @Override
    public void render(IModel model, IView view, String viewPath, HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        response.setContentType("text/html"); // 返回数据格式处理
        if (StringUtil.isNotBlank(model.getContentType())) {
            response.setContentType(model.getContentType());
        }
        if ((viewPath = viewPath.trim()).startsWith("f:")) { // 请求转发处理
            viewPath = viewPath.substring(2);
            if (!viewPath.startsWith("/")) {
                viewPath = "/" + viewPath;
            }
            request.getRequestDispatcher(viewPath).forward(request, response);
            return;
        }
        if ((viewPath = viewPath.trim()).startsWith("r:")) {  // 重定向处理
            viewPath = viewPath.substring(2);
            if (viewPath.toLowerCase().startsWith("http://") || //
                    viewPath.toLowerCase().startsWith("https://")) {
                response.sendRedirect(viewPath);
                return;
            }
            if (!viewPath.trim().startsWith("/")) {
                viewPath = SNInitializer.getWebRootPath() + viewPath;
            }
            response.sendRedirect(WebUtil.getDoMain(request) + viewPath);
            return;
        }
        // 生成页面
        view.generator(model, viewPath, request, response);
    }

}
