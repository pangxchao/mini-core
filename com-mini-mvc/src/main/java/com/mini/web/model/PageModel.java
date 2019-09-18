package com.mini.web.model;

import com.mini.util.ObjectUtil;
import com.mini.validate.ValidateUtil;
import com.mini.web.view.IView;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * Page Model 类实现
 * @author xchao
 */
public final class PageModel extends IModel<PageModel> implements Serializable {
    private static final long serialVersionUID = -1731063292578685253L;
    private final Map<String, Object> data = new HashMap<>();
    private static final String TYPE = "text/html";
    private final IView view;


    public PageModel(IView view) {
        setContentType(TYPE);
        this.view = view;
    }

    @Override
    protected PageModel model() {
        return this;
    }

    /**
     * 获取所有数据
     * @return 所有数据
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * 添加数据
     * @param name  数据键名称
     * @param value 数据值
     * @return {@Code #this}
     */
    public PageModel addData(String name, Object value) {
        data.put(name, value);
        return model();
    }

    /**
     * 添加所有数据
     * @param map Map数据
     * @return {@Code #this}
     */
    public PageModel addDataAll(@Nonnull Map<? extends String, ?> map) {
        data.putAll(map);
        return model();
    }

    @Override
    protected void submit(HttpServletRequest request, HttpServletResponse response, String viewPath) throws Exception, Error {
        ValidateUtil.isNotBlank(viewPath, SC_INTERNAL_SERVER_ERROR, "View path can not be null");
        // 生成页面
        data.put("request", request);
        data.put("response", response);
        data.put("session", request.getSession());
        data.put("context", request.getServletContext());
        view.generator(data, viewPath, request, response);
    }
}
