package com.mini.web.model.factory;

import com.mini.web.model.IModel;
import com.mini.web.view.IView;

import javax.annotation.Nonnull;

public interface ModelFactory {
    @Nonnull
    IModel<?> getModel(IView view, String viewPath);
}
