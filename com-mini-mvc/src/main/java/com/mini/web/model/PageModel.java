package com.mini.web.model;

import com.mini.util.StringUtil;
import com.mini.util.map.MiniHashMap;
import com.mini.validate.ValidateUtil;
import com.mini.web.util.WebUtil;
import com.mini.web.view.IView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.web.util.WebUtil.forward;
import static com.mini.web.util.WebUtil.sendRedirect;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * Page Model 类实现
 * @author xchao
 */
public final class PageModel extends MiniHashMap<String> implements IModel<PageModel> {
    private static final long serialVersionUID = -1731063292578685253L;
    private int status = HttpServletResponse.SC_OK;
    private String contentType = "text/html";
    private final IView view;
    private String viewPath;
    private String message;

    public PageModel(IView view) {
        this.view = view;
    }

    @Override
    public PageModel toChild() {
        return this;
    }

    @Override
    public PageModel sendError(int status) {
        this.status = status;
        return toChild();
    }

    @Override
    public PageModel sendError(int status, String message) {
        this.message = message;
        this.status  = status;
        return toChild();
    }

    @Override
    public PageModel setContentType(String contentType) {
        this.contentType = contentType;
        return toChild();
    }

    /**
     * 添加数据
     * @param name  数据键名称
     * @param value 数据值
     * @return {@Code #this}
     */
    public PageModel addData(String name, Object value) {
        super.put(name, value);
        return toChild();
    }

    /**
     * Sets the value of viewPath.
     * @param viewPath The value of viewPath
     * @return {@Code #this}
     */
    public PageModel setViewPath(String viewPath) {
        this.viewPath = viewPath;
        return toChild();
    }

    @Override
    public void submit(HttpServletRequest request, HttpServletResponse response) throws Exception, Error {
        ValidateUtil.isNotBlank(viewPath, SC_INTERNAL_SERVER_ERROR, "View path can not be null");
        // 错误码处理和返回数据格式处理
        WebUtil.setContentType(contentType, response);
        if (WebUtil.sendError(status, message, response)) {
            return;
        }

        // 请求转发处理
        if (StringUtil.startsWith(viewPath = viewPath.trim(), "f:")) {
            forward(viewPath.substring(2), request, response);
            return;
        }

        // 重定向处理
        if (StringUtil.startsWith(viewPath = viewPath.trim(), "r:")) {
            sendRedirect(viewPath.substring(2), request, response);
            return;
        }
        // 生成页面
        put("request", request);
        put("response", response);
        put("session", request.getSession());
        put("context", request.getServletContext());
        view.generator(this, viewPath, request, response);
    }


}
