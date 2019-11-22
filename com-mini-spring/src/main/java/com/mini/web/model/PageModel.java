package com.mini.web.model;

import com.mini.validate.ValidateUtil;
import com.mini.web.view.IView;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * Page Model 类实现
 * @author xchao
 */
public class PageModel extends IModel<PageModel> implements Serializable {
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
    public final Map<String, Object> getData() {
        return data;
    }

    /**
     * 添加数据
     * @param name  数据键名称
     * @param value 数据值
     * @return @this
     */
    public final PageModel addData(String name, Object value) {
        data.put(name, value);
        return model();
    }

    /**
     * 添加所有数据
     * @param map Map数据
     * @return @this
     */
    public final PageModel addDataAll(@Nonnull Map<? extends String, ?> map) {
        data.putAll(map);
        return model();
    }

    @Override
    protected void onError(HttpServletRequest request, HttpServletResponse response) throws Exception, Error {
        response.sendError(getStatus(), getMessage());
    }

    @Override
    protected void onSubmit(HttpServletRequest request, HttpServletResponse response, String viewPath) throws Exception, Error {
        ValidateUtil.isNotBlank(viewPath, INTERNAL_SERVER_ERROR, "View path can not be null");
        // 生成页面
        data.put("request", request);
        data.put("response", response);
        data.put("session", request.getSession());
        data.put("context", request.getServletContext());
        view.generator(data, viewPath, request, response);
    }
}
