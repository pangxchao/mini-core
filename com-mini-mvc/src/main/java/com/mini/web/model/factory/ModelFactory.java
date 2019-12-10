package com.mini.web.model.factory;

import com.mini.web.model.IModel;
import com.mini.web.view.PageViewResolver;

import javax.annotation.Nonnull;

public interface ModelFactory {
    @Nonnull
    IModel<?> getModel(PageViewResolver view, String viewPath);
}
