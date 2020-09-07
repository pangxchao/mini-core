package com.mini.core.mvc.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mini.core.mvc.support.config.Configures;
import com.mini.core.mvc.util.ResponseCode;
import com.mini.core.mvc.view.PageViewResolver;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;


/**
 * Page Model 类实现
 *
 * @author xchao
 */
@Component
@SuppressWarnings("UnusedReturnValue")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PageModel extends IModel<PageModel> implements Serializable, ResponseCode {
    private static final String URL_REGEX = "http(s)?://([\\s\\S])+";
    private static final Logger log = getLogger(PageModel.class);
    private final Map<String, Object> data = new HashMap<>();
    private static final String TYPE = "text/html";
    private String viewPath;

    @Autowired
    public PageModel(Configures configures) {
        super(configures, TYPE);
    }

    @Override
    protected PageModel model() {
        return this;
    }

    /**
     * 设置返回视图路径/可以重定向和转发
     *
     * @param viewPath 返回视图
     * @return @this
     */
    public PageModel setViewPath(@NotNull String viewPath) {
        this.viewPath = viewPath;
        return model();
    }

    /**
     * 获取所有数据
     *
     * @return 所有数据
     */
    public final Map<String, Object> getData() {
        return data;
    }

    /**
     * 添加数据
     *
     * @param name  数据键名称
     * @param value 数据值
     * @return {@link PageModel}
     */
    public final PageModel addData(String name, Object value) {
        data.put(name, value);
        return model();
    }

    /**
     * 添加数据
     *
     * @param name  数据键名称
     * @param value 数据值
     * @return {@link PageModel}
     */
    public final PageModel put(String name, Object value) {
        return addData(name, value);
    }

    /**
     * 添加所有数据
     *
     * @param map Map数据
     * @return {@link PageModel}
     */
    public final PageModel addDataAll(@Nonnull Map<? extends String, ?> map) {
        data.putAll(map);
        return model();
    }

    /**
     * 添加所有数据
     *
     * @param map Map数据
     * @return {@link PageModel}
     */
    public final PageModel putAll(@Nonnull Map<? extends String, ?> map) {
        return addDataAll(map);
    }

    /**
     * 设置实体数据
     *
     * @param data 实体数据
     * @return {@link PageModel}
     */
    public PageModel addDataAll(Object data) {
        Optional.ofNullable(data).map(JSON::toJSON)
                .filter(it -> it instanceof JSONObject)
                .map(it -> (JSONObject) it)
                .ifPresent(it -> it.forEach(this::addData));
        return model();
    }

    /**
     * 设置实体数据
     *
     * @param data 实体数据
     * @return {@link PageModel}
     */
    public PageModel putAll(Object data) {
        return addDataAll(data);
    }

    @Override
    protected void onError(HttpServletRequest request, HttpServletResponse response) throws Exception, Error {
        response.sendError(INTERNAL_SERVER_ERROR, format("%d(%s)", getStatus(), getMessage()));
    }

    @Override
    protected void doSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception, Error {
        try {
            // 请求转发处理
            if (viewPath != null && startsWithIgnoreCase(viewPath, "f:")) {
                viewPath = viewPath.substring(2);
                // 如果不是以“/”开头，则添加
                if (!viewPath.startsWith("/")) {
                    viewPath = "/" + viewPath;
                }
                // 转发请求
                request.getRequestDispatcher(viewPath).forward(request, response);
                return;
            }
            // 重定向处理
            if (viewPath != null && startsWithIgnoreCase(viewPath, "r:")) {
                viewPath = viewPath.substring(2);
                // http:// 或者 https:// 开头的绝对地址
                if (viewPath.toLowerCase().matches(URL_REGEX)) {
                    response.sendRedirect(viewPath);
                    return;
                }

                // “/” 开头的绝对地址
                if (startsWithIgnoreCase(viewPath, "/")) {
                    response.sendRedirect(viewPath);
                    return;
                }

                // 构建绝对地址
                String contextPath = request.getContextPath();
                viewPath = contextPath + "/" + viewPath;
                response.sendRedirect(viewPath);
                return;
            }
            PageViewResolver view = getConfigures().getPageViewResolver();
            view.generator(data, viewPath, request, response);
        } catch (IOException | Error e) {
            response.setStatus(INTERNAL_SERVER_ERROR);
            log.error(e.getMessage());
        }
    }
}
