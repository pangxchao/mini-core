package com.mini.web.config;

import com.mini.web.model.IModel;
import com.mini.web.model.factory.ModelFactory;

import javax.inject.Singleton;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Singleton
public final class ModelFactoryConfigure implements Serializable {
    private static final long serialVersionUID = 1348664728363424911L;
    private final Map<Class<? extends IModel<?>>, ModelFactory<?>> modelFactory = new HashMap<>();


    /**
     * 添加一个监听器
     * @param model 参数解析器
     * @return {@Code this}
     */
    public <T> ModelFactoryConfigure addRenderer(Class<? extends IModel<?>> clazz, ModelFactory<?> model) {
        modelFactory.putIfAbsent(clazz, model);
        return this;
    }

    /**
     * Gets the value of modelMap.
     * @return The value of modelMap
     */
    public Map<Class<? extends IModel<?>>, ModelFactory<?>> getModelFactory() {
        return modelFactory;
    }
}
