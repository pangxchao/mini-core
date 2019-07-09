package com.mini.web.config;

import com.mini.web.view.IView;

import javax.inject.Singleton;
import java.io.Serializable;

@Singleton
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
     * The value of viewClass
     * @param viewClass The value of viewClass
     * @return {@Code this}
     */
    public ViewConfigure setViewClass(Class<? extends IView> viewClass) {
        this.viewClass = viewClass;
        return this;
    }
}
