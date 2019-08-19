package com.mini.web.model.factory;

import com.mini.web.model.*;
import com.mini.web.view.IView;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import static com.mini.web.model.IModel.MODEL_KEY;

public enum ModelType {
    MAP {
        @Nonnull
        @SuppressWarnings("unchecked")
        public IModel<MapModel> getModel(IView view, String viewPath, HttpServletRequest r) {
            MapModel model = new MapModel();
            r.setAttribute(MODEL_KEY, model);
            return model;
        }
    },
    LIST {
        @Nonnull
        @SuppressWarnings("unchecked")
        public ListModel getModel(IView view, String viewPath, HttpServletRequest r) {
            ListModel model = new ListModel();
            r.setAttribute(MODEL_KEY, model);
            return model;
        }
    },
    PAGE {
        @Nonnull
        @SuppressWarnings("unchecked")
        public PageModel getModel(IView view, String viewPath, HttpServletRequest r) {
            PageModel model = new PageModel(view);
            r.setAttribute(MODEL_KEY, model);
            model.setViewPath(viewPath);
            return model;
        }
    },
    STREAM {
        @Nonnull
        @SuppressWarnings("unchecked")
        public StreamModel getModel(IView view, String viewPath, HttpServletRequest r) {
            StreamModel model = new StreamModel();
            r.setAttribute(MODEL_KEY, model);
            return model;
        }
    },
    STRING {
        @Nonnull
        @SuppressWarnings("unchecked")
        public StringModel getModel(IView view, String viewPath, HttpServletRequest r) {
            StringModel model = new StringModel();
            r.setAttribute(MODEL_KEY, model);
            return model;
        }
    };

    @Nonnull
    @SuppressWarnings("unchecked")
    public <T extends IModel<T>> T getModel(IView view, String viewPath, HttpServletRequest r) {
        IModel<PageModel> model = new PageModel(view);
        r.setAttribute(MODEL_KEY, model);
        model.setViewPath(viewPath);
        return (T) model;
    }

}
