package com.mini.web.config;

import com.mini.web.view.IView;

import javax.inject.Singleton;
import java.io.Serializable;

@Singleton
public final class ViewConfigure implements Serializable {
    private static final long serialVersionUID = 770591597110272211L;
    private IView view ;

    /**
     * Gets the value of view.
     * @return The value of view
     */
    public IView getView() {
        return view;
    }

    /**
     * Sets the value of view.
     * @param view The value of view
     */
    public void setView(IView view) {
        this.view = view;
    }
}
