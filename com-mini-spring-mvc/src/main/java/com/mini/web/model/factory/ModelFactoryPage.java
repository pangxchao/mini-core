package com.mini.web.model.factory;

import com.mini.web.model.ModelPage;
import com.mini.web.view.IView;

import org.springframework.stereotype.Component;

@Component
public final class ModelFactoryPage implements ModelFactory<ModelPage> {

    @Override
    public ModelPage getModel(IView view, String viewPath) {
        ModelPage model = new ModelPage(view);
        model.setViewPath(viewPath);
        return model;
    }
}
