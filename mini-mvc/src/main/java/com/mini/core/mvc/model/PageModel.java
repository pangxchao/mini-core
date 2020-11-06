package com.mini.core.mvc.model;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * Page Model 类实现
 *
 * @author xchao
 */
@SuppressWarnings("UnusedReturnValue")
public class PageModel extends IModel<ModelAndView, PageModel> {
    private final ModelAndView mv = new ModelAndView();

    public PageModel(HttpServletRequest request, HttpServletResponse response) {
        super(request, response, MediaType.TEXT_HTML);
    }

    @Override
    protected final PageModel getThis() {
        return this;
    }

    public final ModelAndView getModelAndView() {
        return mv;
    }

    @Override
    public final PageModel setStatus(@NotNull HttpStatus status) {
        PageModel.this.mv.setStatus(status);
        return super.setStatus(status);
    }

    @Override
    public final PageModel setMessage(String message) {
        this.mv.addObject("message", message);
        return super.setMessage(message);
    }

    @Override
    public final PageModel setCode(Integer code) {
        this.mv.addObject("code", code);
        return super.setCode(code);
    }

    /**
     * 添加数据-Map类型的数据
     *
     * @param name  数据键名称
     * @param value 数据值
     * @return {this}
     */
    public final PageModel put(String name, Object value) {
        this.mv.addObject(name, value);
        return getThis();
    }

    /**
     * 添加所有数据-Map结构数据
     *
     * @param map Map数据
     * @return {this}
     */
    public final PageModel putAll(@NotNull Map<String, ?> map) {
        this.mv.addAllObjects(map);
        return getThis();
    }

    /**
     * 设置实体数据
     *
     * @param object 实体数据
     * @return {@link PageModel}
     */
    public final PageModel putObject(Object object) {
        this.mv.addObject(object);
        return getThis();
    }

    /**
     * 设置自定义分页数据结构
     *
     * @param page 自定义分页数据结构
     * @return {this}
     */
    public PageModel putPage(Object page) {
        return this.putObject(page);
    }

    /**
     * 设置返回视图路径/可以重定向和转发
     *
     * @param viewName 返回视图
     * @return @this
     */
    public final PageModel setViewName(@Nullable String viewName) {
        this.mv.setViewName(viewName);
        return getThis();
    }

    /**
     * 设置重定向路径
     *
     * @param viewName 返回视图
     * @return @this
     */
    public final PageModel setRedirectViewName(@NotNull String viewName) {
        return setViewName("redirect:" + viewName);
    }

    /**
     * 设置转发路径
     *
     * @param viewName 返回视图
     * @return @this
     */
    public final PageModel setForwardViewName(@NotNull String viewName) {
        return setViewName("forward:" + viewName);
    }

    /**
     * 设置返回视图路径/可以重定向和转发
     *
     * @param view 返回视图
     * @return @this
     */
    public final PageModel setView(@Nullable View view) {
        this.mv.setView(view);
        return getThis();
    }

    @Override
    public final ModelAndView build() {
        return mv;
    }

    @Override
    protected final String getDispatcherPath() {
        return "/h/page";
    }
}
