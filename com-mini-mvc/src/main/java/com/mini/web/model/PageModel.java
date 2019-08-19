package com.mini.web.model;

import com.mini.util.map.MiniHashMap;
import com.mini.validate.ValidateUtil;
import com.mini.web.view.IView;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * Page Model 类实现
 * @author xchao
 */
public final class PageModel extends IModel<PageModel> implements Serializable {
    private static final long serialVersionUID = -1731063292578685253L;
    private final MiniHashMap<String> data = new MiniHashMap<>();
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

    @Nonnull
    public Set<String> keySet() {
        return data.keySet();
    }

    @Nonnull
    public Collection<Object> values() {
        return data.values();
    }

    @Nonnull
    public Set<Map.Entry<String, Object>> entrySet() {
        return data.entrySet();
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
    protected void sendError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(getStatus(), getMessage());
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
