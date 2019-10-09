package com.mini.web.model.factory;

import javax.inject.Singleton;

import com.mini.web.model.IModel;
import com.mini.web.model.PageModel;
import com.mini.web.view.IView;

@Singleton
public class PageModelFactory implements IModelFactory<PageModel> {
    @Override
    public IModel<PageModel> getModel(IView view, String viewPath) {
        PageModel model = new PageModel(view);
        model.setViewPath(viewPath);
        return model;
    }
}
