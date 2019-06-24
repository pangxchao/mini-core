package com.mini.web.model.factory;

import com.mini.web.model.ModelJsonMap;
import com.mini.web.view.IView;

import javax.inject.Singleton;

@Singleton
public final class ModelFactoryJsonMap implements ModelFactory<ModelJsonMap> {

    @Override
    public ModelJsonMap getModel(IView view, String viewPath ) {
        return new ModelJsonMap();
    }
}
