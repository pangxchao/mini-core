package com.mini.web.model.factory;

import com.mini.web.model.IModel;
import com.mini.web.model.PageModel;
import com.mini.web.view.IView;

import javax.inject.Singleton;

@Singleton
public class PageModelFactory implements IModelFactory<PageModel> {
    @Override
    public IModel<PageModel> getModel(IView view, String viewPath) {
        PageModel model = new PageModel(view);
        model.setViewPath(viewPath);
        return model;
    }
}