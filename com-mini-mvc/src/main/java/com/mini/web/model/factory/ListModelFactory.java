package com.mini.web.model.factory;

import javax.inject.Singleton;

import com.mini.web.model.IModel;
import com.mini.web.model.ListModel;
import com.mini.web.view.IView;

@Singleton
public class ListModelFactory implements IModelFactory<ListModel> {
    @Override
    public IModel<ListModel> getModel(IView view, String viewPath) {
        ListModel model = new ListModel();
        model.setViewPath(viewPath);
        return model;
    }
}
