package com.mini.web.model.factory;

import com.mini.web.model.*;
import com.mini.web.view.IView;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Objects;

public enum ModelType implements ModelFactory {
    LIST {
        @Nonnull
        @Override
        public ListModel getModel(IView view, String viewPath) {
            ListModel model = new ListModel();
            model.setViewPath(viewPath);
            return model;
        }
    },
    MAP {
        @Nonnull
        @Override
        public MapModel getModel(IView view, String viewPath) {
            MapModel model = new MapModel();
            model.setViewPath(viewPath);
            return model;
        }
    },
    PAGE {
        @Nonnull
        @Override
        public PageModel getModel(IView view, String viewPath) {
            PageModel model = new PageModel(view);
            model.setViewPath(viewPath);
            return model;
        }
    },
    STREAM {
        @Nonnull
        @Override
        public StreamModel getModel(IView view, String viewPath) {
            StreamModel model = new StreamModel();
            model.setViewPath(viewPath);
            return model;
        }
    },
    STRING {
        @Nonnull
        @Override
        public StringModel getModel(IView view, String viewPath) {
            StringModel model = new StringModel();
            model.setViewPath(viewPath);
            return model;
        }
    },
    EXT {
        @Nonnull
        @Override
        public IModel<?> getModel(IView view, String viewPath) {
            String message = "ModelFactory can not be null";
            Objects.requireNonNull(factory, message);
            return factory.getModel(view, viewPath);
        }
    };

    @Inject
    private static ModelFactory factory;
}
