package com.mini.web.model.factory;

import com.mini.web.model.ListModel;
import com.mini.web.view.IView;

import javax.inject.Singleton;

@Singleton
public final class ListModelFactory implements IModelFactory<ListModel> {

    @Override
    public ListModel getModel(IView view, String viewPath ) {
        return new ListModel();
    }
}
