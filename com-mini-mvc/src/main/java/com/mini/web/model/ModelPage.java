package com.mini.web.model;

import com.mini.util.StringUtil;
import com.mini.util.map.MiniMap;
import com.mini.web.view.IView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Page Model 类实现
 * @author xchao
 */
public final class ModelPage extends LinkedHashMap<String, Object> implements MiniMap<String>, IModel<ModelPage> {
    private static final long serialVersionUID = -1731063292578685253L;
    private int status = HttpServletResponse.SC_OK;
    private String contentType = "text/html";
    private final IView view;
    private String viewPath;
    private String message;

    public ModelPage(IView view) {
        this.view = view;
    }

    @Override
    public ModelPage toChild() {
        return this;
    }

    @Override
    public ModelPage sendError(int status) {
        this.status = status;
        return toChild();
    }

    @Override
    public ModelPage sendError(int status, String message) {
        this.message = message;
        this.status  = status;
        return toChild();
    }

    @Override
    public ModelPage setContentType(String contentType) {
        this.contentType = contentType;
        return toChild();
    }

    /**
     * 添加数据
     * @param name  数据键名称
     * @param value 数据值
     * @return {@Code #this}
     */
    public ModelPage addData(String name, Object value) {
        super.put(name, value);
        return toChild();
    }

    /**
     * Sets the value of viewPath.
     * @param viewPath The value of viewPath
     * @return {@Code #this}
     */
    public ModelPage setViewPath(String viewPath) {
        this.viewPath = viewPath;
        return toChild();
    }


    @Override
    public void submit(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        // 错误码处理和返回数据格式处理
        response.setContentType(contentType);
        if (status != HttpServletResponse.SC_OK) {
            response.sendError(status, message);
            return;
        }
        // 视图或者视图的路径为空时，不处理数据
        if (StringUtil.isBlank(viewPath)) {
            return;
        }

        // 请求转发处理
        if ((viewPath = viewPath.trim()).startsWith("f:")) {
            viewPath = viewPath.substring(2);
            if (!viewPath.startsWith("/")) {
                viewPath = "/" + viewPath;
            }
            forward(request, response);
            return;
        }

        // 重定向处理
        if ((viewPath = viewPath.trim()).startsWith("r:")) {
            viewPath = StringUtil.substring(viewPath, 2);
            if (isAbsolutePath(viewPath.toLowerCase())) {
                response.sendRedirect(viewPath);
                return;
            }
            String contextPath = request.getContextPath();
            if (!StringUtil.startsWith(viewPath, "/")) {
                viewPath = contextPath + "/" + viewPath;
            }
            response.sendRedirect(viewPath);
            return;
        }
        // 生成页面
        put("request", request);
        put("response", response);
        put("session", request.getSession());
        put("context", request.getServletContext());
        view.generator(this, viewPath, request, response);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(viewPath).forward(request, response);
    }

    private boolean isAbsolutePath(String lower) throws Error {
        return lower.matches("http(s)?://([\\s\\S])+");
    }
}
