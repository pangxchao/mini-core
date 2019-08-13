package com.mini.web.model.factory;

import com.mini.web.model.*;
import com.mini.web.view.IView;

import javax.annotation.Nonnull;

public enum ModelType {
    MAP {
        @Nonnull
        @SuppressWarnings("unchecked")
        public MapModel getModel(IView view, String viewPath) {
            return new MapModel();
        }
    },
    LIST {
        @Nonnull
        @SuppressWarnings("unchecked")
        public ListModel getModel(IView view, String viewPath) {
            return new ListModel();
        }
    },
    PAGE {
        @Nonnull
        @SuppressWarnings("unchecked")
        public PageModel getModel(IView view, String viewPath) {
            PageModel model = new PageModel(view);
            model.setViewPath(viewPath);
            return model;
        }
    },
    STREAM {
        @Nonnull
        @SuppressWarnings("unchecked")
        public StreamModel getModel(IView view, String viewPath) {
            return new StreamModel();
        }
    },
    STRING {
        @Nonnull
        @SuppressWarnings("unchecked")
        public StringModel getModel(IView view, String viewPath) {
            return new StringModel();
        }
    };

    @Nonnull
    @SuppressWarnings("unchecked")
    public <T extends IModel<T>> T getModel(IView view, String viewPath) {
        PageModel model = new PageModel(view);
        model.setViewPath(viewPath);
        return (T) model;
    }
}
