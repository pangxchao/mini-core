package com.mini.web.model.factory;

import com.mini.web.model.MapModel;
import com.mini.web.view.IView;

import javax.inject.Singleton;

@Singleton
public final class MapModelFactory implements IModelFactory<MapModel> {

    @Override
    public MapModel getModel(IView view, String viewPath ) {
        return new MapModel();
    }
}
