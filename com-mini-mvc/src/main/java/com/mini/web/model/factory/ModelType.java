package com.mini.web.model.factory;

import com.mini.web.model.IModel;
import com.mini.web.model.JsonModel;
import com.mini.web.model.PageModel;
import com.mini.web.model.StreamModel;
import com.mini.web.view.PageViewResolver;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Objects;

public enum ModelType implements ModelFactory {
    PAGE {
        @Nonnull
        @Override
        public PageModel getModel(PageViewResolver view, String viewPath) {
            PageModel model = new PageModel(view);
            model.setViewPath(viewPath);
            return model;
        }
    },
    JSON {
        @Nonnull
        @Override
        public JsonModel getModel(PageViewResolver view, String viewPath) {
            JsonModel model = new JsonModel();
            model.setViewPath(viewPath);
            return model;
        }
    },
    STREAM {
        @Nonnull
        @Override
        public StreamModel getModel(PageViewResolver view, String viewPath) {
            StreamModel model = new StreamModel();
            model.setViewPath(viewPath);
            return model;
        }
    },
    EXT {
        @Nonnull
        @Override
        public IModel<?> getModel(PageViewResolver view, String viewPath) {
            String message = "ModelFactory can not be null";
            Objects.requireNonNull(factory, message);
            return factory.getModel(view, viewPath);
        }
    };

    @Inject
    private static ModelFactory factory;
}
