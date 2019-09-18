package com.mini.web.model.factory;

import com.mini.web.model.IModel;
import com.mini.web.model.StringModel;
import com.mini.web.view.IView;

import javax.inject.Singleton;

@Singleton
public class StringModelFactory implements IModelFactory<StringModel> {
    @Override
    public IModel<StringModel> getModel(IView view, String viewPath) {
        StringModel model = new StringModel();
        model.setViewPath(viewPath);
        return model;
    }
}
