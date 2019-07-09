package com.mini.web.config;

import com.mini.web.view.IView;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public final class ViewConfigure implements Serializable {
    private static final long serialVersionUID = 770591597110272211L;
    private Class<? extends IView> viewClass;

    public Class<? extends IView> getViewClass() {
        return viewClass;
    }

    public void setViewClass(Class<? extends IView> viewClass) {
        this.viewClass = viewClass;
    }
}
