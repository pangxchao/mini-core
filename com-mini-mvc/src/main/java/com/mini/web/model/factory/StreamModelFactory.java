package com.mini.web.model.factory;

import javax.inject.Singleton;

import com.mini.web.model.IModel;
import com.mini.web.model.StreamModel;
import com.mini.web.view.IView;

@Singleton
public class StreamModelFactory implements IModelFactory<StreamModel> {
    @Override
    public IModel<StreamModel> getModel(IView view, String viewPath) {
        StreamModel model = new StreamModel();
        model.setViewPath(viewPath);
        return model;
    }
}
