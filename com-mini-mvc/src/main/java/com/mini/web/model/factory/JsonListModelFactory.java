package com.mini.web.model.factory;

import com.mini.web.model.JsonListModel;
import com.mini.web.view.IView;

import javax.inject.Singleton;

@Singleton
public final class JsonListModelFactory implements IModelFactory<JsonListModel> {

    @Override
    public JsonListModel getModel(IView view, String viewPath ) {
        return new JsonListModel();
    }
}
