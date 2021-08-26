package com.mini.core.jdbc;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.mini.core.jdbc.mybatis.MiniMetaObjectHandler;
import com.mini.core.jdbc.mybatis.MiniMybatisIdGenerator;
import com.mini.core.jdbc.mybatis.injector.MiniSqlInjector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static com.baomidou.mybatisplus.annotation.DbType.MYSQL;

@Configuration(proxyBeanMethods = false)
@SuppressWarnings({"SpringFacetCodeInspection", "RedundantSuppression"})
public class MiniJdbcConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = MiniJdbcTemplate.class)
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public MiniJdbcTemplate miniJdbcTemplate(DataSource dataSource) {
        return new DefaultMiniJdbcTemplate(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean(value = ConfigurationCustomizer.class)
    public ConfigurationCustomizer configurationCustomizer() {
        final var handlerClass = MybatisEnumTypeHandler.class;
        return cfg -> cfg.setDefaultEnumTypeHandler(handlerClass);
    }

    @Bean
    @ConditionalOnMissingBean(value = MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        final MybatisPlusInterceptor it = new MybatisPlusInterceptor();
        it.addInnerInterceptor(new PaginationInnerInterceptor(MYSQL));
        return it;
    }

    @Bean
    @ConditionalOnMissingBean(value = MiniMybatisIdGenerator.class)
    public MiniMybatisIdGenerator miniMybatisIdGenerator() {
        return new MiniMybatisIdGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(value = MiniMetaObjectHandler.class)
    public MiniMetaObjectHandler miniMetaObjectHandler() {
        return new MiniMetaObjectHandler();
    }

    @Bean
    @ConditionalOnMissingBean(value = MiniSqlInjector.class)
    public MiniSqlInjector miniSqlInjector() {
        return new MiniSqlInjector();
    }

}
