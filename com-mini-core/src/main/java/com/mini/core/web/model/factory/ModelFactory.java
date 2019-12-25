package com.mini.core.web.model.factory;

import com.mini.core.web.view.PageViewResolver;
import com.mini.core.web.model.IModel;
import com.mini.core.web.view.PageViewResolver;

import javax.annotation.Nonnull;

public interface ModelFactory {
    @Nonnull
    IModel<?> getModel(PageViewResolver view, String viewPath);
}
