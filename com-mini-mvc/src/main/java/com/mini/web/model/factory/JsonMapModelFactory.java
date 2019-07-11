package com.mini.web.model.factory;

import com.mini.web.model.JsonMapModel;
import com.mini.web.view.IView;

import javax.inject.Singleton;

@Singleton
public final class JsonMapModelFactory implements IModelFactory<JsonMapModel> {

    @Override
    public JsonMapModel getModel(IView view, String viewPath ) {
        return new JsonMapModel();
    }
}
