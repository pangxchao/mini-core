package com.mini.inject;

import com.google.inject.Binder;
import com.mini.util.MiniProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件加载 Module
 * @author xchao
 */
public class PropertiesModule extends MiniModule {
    private static final String PRO_FILE_FORMAT = "application-%s.properties";
    private static final String PRO_FILE_NAME = "application.properties";
    private static final String PRO_KEY_NAME = "mini.active.file";
    private MiniProperties prop = new MiniProperties();

    /**
     * 获取默认的配置文件
     * @param loader 类加载器
     * @return 配置文件流
     */
    private InputStream getInputStream(ClassLoader loader) {
        return loader.getResourceAsStream(PRO_FILE_NAME);
    }

    /**
     * 递归加载配置文件信息
     * @param loader 类加载器
     * @param stream 配置文件流
     */
    private void loadProperties(ClassLoader loader, InputStream stream) throws IOException {
        if (stream == null) return;
        setProperty(new MiniProperties().miniLoad(stream));
        String activeFile = removeProperties(PRO_KEY_NAME);
        String name = String.format(PRO_FILE_FORMAT, activeFile);
        this.loadProperties(loader, loader.getResourceAsStream(name));
    }

    /** 自定义绑定 */
    @Override
    protected final void configures(Binder binder) throws Exception, Error {
        ClassLoader classLoader = this.getClass().getClassLoader();
        loadProperties(classLoader, getInputStream(classLoader));
        this.configures(binder, classLoader);
        this.bindProperties(prop);
    }

    protected void configures(Binder binder, ClassLoader loader) throws Exception, Error {}

    /**
     * 绑定 Properties 配置文件信息
     * @param properties Properties文件
     */
    protected void setProperty(Properties properties) {
        this.prop.setProperty(properties);
    }

    /**
     * 绑定 Properties 配置文件信息
     * @param properties Properties文件
     */
    protected void setPropertyIfAbsent(Properties properties) {
        this.prop.setPropertyIfAbsent(properties);
    }

    /**
     * 获取属性文件内容
     * @return 属性文件内容
     */
    protected Properties getProperty() {
        return this.prop;
    }

    private String removeProperties(String name) {
        String value = prop.getProperty(name);
        prop.remove(name);
        return value;
    }
}
