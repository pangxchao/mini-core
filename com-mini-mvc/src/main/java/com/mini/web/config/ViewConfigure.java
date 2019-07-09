package com.mini.web.config;

import com.mini.web.view.IView;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.Serializable;

@Singleton
@Named("viewConfigure")
public final class ViewConfigure implements Serializable {
    private static final long serialVersionUID = 770591597110272211L;
    private Class<? extends IView> viewClass;

    /**
     * Gets the value of viewClass.
     * @return The value of viewClass
     */
    public Class<? extends IView> getViewClass() {
        return viewClass;
    }

    /**
     * 获取视图实现类的全名
     * @return 视图实现类的全名
     */
    public String getClassName() {
        return viewClass.getName();
    }

    /**
     * The value of viewClass
     * @param viewClass The value of viewClass
     * @return {@Code this}
     */
    public ViewConfigure setViewClass(Class<? extends IView> viewClass) {
        this.viewClass = viewClass;
        return this;
    }
}
