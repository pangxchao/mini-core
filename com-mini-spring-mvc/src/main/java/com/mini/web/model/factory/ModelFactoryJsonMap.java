package com.mini.web.model.factory;

import com.mini.web.model.ModelJsonMap;
import com.mini.web.view.IView;

import org.springframework.stereotype.Component;

@Component
public final class ModelFactoryJsonMap implements ModelFactory<ModelJsonMap> {

    @Override
    public ModelJsonMap getModel(IView view, String viewPath ) {
        return new ModelJsonMap();
    }
}
