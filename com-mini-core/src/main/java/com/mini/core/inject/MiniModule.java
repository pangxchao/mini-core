package com.mini.core.inject;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.mini.core.inject.annotation.ComponentScan;
import com.mini.core.inject.annotation.PropertySource;
import com.mini.core.inject.annotation.PropertySources;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.Properties;

import static com.mini.core.util.MiniProperties.createProperties;

public abstract class MiniModule implements Module {

    @Override
    public final void configure(Binder binder) {
        PropertySources sources = this.getPropertySources();
        Properties properties = createProperties(sources);
        PropertySource source = this.getPropertySource();
        properties.putAll(createProperties(source));
        Names.bindProperties(binder, properties);
        binder.requestInjection(this);
        this.onStartup(binder);
    }

    protected abstract void onStartup(Binder binder);

    // 获取当前类指定注解信息
    public final <T extends Annotation> T getAnnotation(Class<T> clazz) {
        return this.getClass().getAnnotation(clazz);
    }

    // 获取当前类ComponentScan注解信息
    public final ComponentScan getComponentScan() {
        return getAnnotation(ComponentScan.class);
    }

    /**
     * 获取当前类ComponentScan注解信息的值
     * @return ComponentScan注解信息的值
     */
    @Nonnull
    public final String[] getComponentScanValue() {
        ComponentScan scan = getComponentScan();
        if (scan == null) return new String[0];
        return scan.value();
    }

    // 获取当前类PropertySources注解信息
    public final PropertySources getPropertySources() {
        return getAnnotation(PropertySources.class);
    }

    // 获取当前类PropertySource注解信息
    public final PropertySource getPropertySource() {
        return getAnnotation(PropertySource.class);
    }

}
