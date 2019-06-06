package com.mini.util.ioc;

public interface ApplicationContext {
    <T> T getBean(String name, Class<T> clazz);
}
