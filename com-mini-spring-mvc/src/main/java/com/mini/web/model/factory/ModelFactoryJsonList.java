package com.mini.web.model.factory;

import com.mini.web.model.ModelJsonList;
import com.mini.web.view.IView;

import org.springframework.stereotype.Component;

@Component
public final class ModelFactoryJsonList implements ModelFactory<ModelJsonList> {

    @Override
    public ModelJsonList getModel(IView view, String viewPath ) {
        return new ModelJsonList();
    }
}
