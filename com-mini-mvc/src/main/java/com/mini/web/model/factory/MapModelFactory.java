package com.mini.web.model.factory;

import com.mini.web.model.IModel;
import com.mini.web.model.MapModel;
import com.mini.web.view.IView;

import javax.inject.Singleton;

@Singleton
public class MapModelFactory implements IModelFactory<MapModel> {
    @Override
    public IModel<MapModel> getModel(IView view, String viewPath) {
        MapModel model = new MapModel();
        model.setViewPath(viewPath);
        return model;
    }
}
