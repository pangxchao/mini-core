package com.mini.web.model.factory;

import com.mini.web.model.ModelStream;
import com.mini.web.view.IView;

import org.springframework.stereotype.Component;

@Component
public final class ModelFactoryStream implements ModelFactory<ModelStream> {

    @Override
    public ModelStream getModel(IView view, String viewPath ) {
        return new ModelStream();
    }
}
