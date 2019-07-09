package com.mini.web.config;

import com.mini.web.model.IModel;
import com.mini.web.model.factory.ModelFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Named("modelFactoryConfigure")
public  class ModelFactoryConfigure implements Serializable {
    private static final long serialVersionUID = 1348664728363424911L;
    private final Map<Class<? extends IModel<?>>, Class<? extends ModelFactory<?>>> modelFactory = new HashMap<>();


    /**
     * 添加一个监听器
     * @param factory 参数解析器
     * @return {@Code this}
     */
    public <T> ModelFactoryConfigure addModelFactory(Class<? extends IModel<?>> clazz, Class<? extends ModelFactory<?>> factory) {
        modelFactory.putIfAbsent(clazz, factory);
        return this;
    }

    /**
     * Gets the value of modelMap.
     * @return The value of modelMap
     */
    public Map<Class<? extends IModel<?>>, Class<? extends ModelFactory<?>>> getModelFactory() {
        return modelFactory;
    }

    /**
     * 根据 ModelClass 获取 Model 工厂
     * @param clazz ModelClass
     * @return Model工厂
     */
    public final Class<? extends ModelFactory<?>> getModelFactory(Class<? extends IModel<?>> clazz) {
        return modelFactory.get(clazz);
    }
}
