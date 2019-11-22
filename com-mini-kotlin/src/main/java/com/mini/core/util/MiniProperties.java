package com.mini.core.util;

import com.mini.core.inject.annotation.PropertySource;
import com.mini.core.inject.annotation.PropertySources;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;

public class MiniProperties extends Properties {
    private static final long serialVersionUID = 6389833449837854599L;

    /**
     * 加载属性文件
     * @param reader Reader
     * @return {# this}
     */

    public synchronized MiniProperties miniLoad(Reader reader) {
        try {
            super.load(reader);
            return MiniProperties.this;
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载属性文件
     * @param inStream 输入流
     * @return {# this}
     */
    @SuppressWarnings("WeakerAccess")
    public synchronized MiniProperties miniLoad(InputStream inStream) {
        try {
            super.load(inStream);
            return MiniProperties.this;
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置属性，如果存在时跳过
     * @param key   属性key
     * @param value 属性值
     * @return {# this}
     */
    @SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
    public synchronized MiniProperties setPropertyIfAbsent(String key, String value) {
        this.putIfAbsent(key, value);
        return this;
    }

    /**
     * 设置子属性文件的所有内容，子文件中存在的键会覆盖当前键
     * @param properties 属性文件内容
     * @return {# this}
     */
    public synchronized MiniProperties setProperty(Properties properties) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String value = String.valueOf(entry.getValue());
            String key = String.valueOf(entry.getKey());
            setProperty(key, value);
        }
        return this;
    }

    /**
     * 设置子属性文件的所有内容，当前文件中Key存在时跳过
     * @param properties 属性文件内容
     * @return {# this}
     */

    public synchronized MiniProperties setPropertyIfAbsent(Properties properties) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String value = String.valueOf(entry.getValue());
            String key = String.valueOf(entry.getKey());
            setPropertyIfAbsent(key, value);
        }
        return this;
    }

    /**
     * 根据注解信息加载属性文件
     * @param sources 注解信息
     * @return 属性文件信息
     */
    @Nonnull
    public static MiniProperties createProperties(PropertySources sources) {
        if (sources == null) return new MiniProperties();
        MiniProperties properties = new MiniProperties();
        for (PropertySource source : sources.value()) {
            properties.putAll(createProperties(source));
        }
        return properties;
    }

    /**
     * 根据注解信息加载属性文件
     * @param source 注解信息
     * @return 属性文件信息
     */
    @Nonnull
    public static MiniProperties createProperties(PropertySource source) {
        if (source == null) return new MiniProperties();
        return createProperties(source.value());
    }

    /**
     * 根据注解信息加载属性文件
     * @param fileName 文件路径
     * @return 属性文件信息
     */
    @Nonnull
    public static MiniProperties createProperties(String fileName) {
        if (StringUtil.isEmpty(fileName)) return new MiniProperties();
        ClassLoader loader = MiniProperties.class.getClassLoader();
        InputStream stream = loader.getResourceAsStream(fileName);
        return new MiniProperties().miniLoad(stream);
    }
}
