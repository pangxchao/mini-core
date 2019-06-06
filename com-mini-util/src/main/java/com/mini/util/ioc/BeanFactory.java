package com.mini.util.ioc;

public interface BeanFactory {

    /**
     * 获取Bean实例类型
     * @return Bean实例类型
     */
    Class<?> getBeanType();

    /**
     * 获取Bean实体名称
     * @return Bean实体名称
     */
    String getBeanName();

    /**
     * 获取Bean实例
     * @return Bean实例
     */
    Object getBean() throws Exception;
}
