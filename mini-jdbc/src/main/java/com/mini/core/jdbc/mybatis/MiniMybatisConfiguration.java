package com.mini.core.jdbc.mybatis;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.mini.core.jdbc.mybatis.injector.MiniSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@SuppressWarnings({"SpringFacetCodeInspection", "RedundantSuppression"})
public class MiniMybatisConfiguration {

    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor();
    }

    @Bean
    public MiniMybatisIdGenerator miniMybatisIdGenerator() {
        return new MiniMybatisIdGenerator();
    }

    @Bean
    public MiniMetaObjectHandler miniMetaObjectHandler() {
        return new MiniMetaObjectHandler();
    }

    @Bean
    public MiniSqlInjector miniSqlInjector() {
        return new MiniSqlInjector();
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        final var handlerClass = MybatisEnumTypeHandler.class;
        return cfg -> cfg.setDefaultEnumTypeHandler(handlerClass);
    }
}
