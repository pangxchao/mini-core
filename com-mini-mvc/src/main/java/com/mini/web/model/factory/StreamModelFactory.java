package com.mini.web.model.factory;

import com.mini.web.model.StreamModel;
import com.mini.web.view.IView;

import javax.inject.Singleton;

@Singleton
public final class StreamModelFactory implements IModelFactory<StreamModel> {

    @Override
    public StreamModel getModel(IView view, String viewPath ) {
        return new StreamModel();
    }
}
