package com.mini.core.jdbc;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.boot.convert.ApplicationConversionService.getSharedInstance;

/**
 * Mini JDBC 操作默认实现
 *
 * @author pangchao
 */
@Component
public class DefaultMiniJdbcTemplate extends JdbcTemplate implements MiniJdbcTemplate {
    private ConversionService conversionService = getSharedInstance();

    public DefaultMiniJdbcTemplate(DataSource dataSource, boolean lazyInit) {
        super(dataSource, lazyInit);
    }

    public DefaultMiniJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 设置数据转换服务
     *
     * @param conversionService 数据转换服务
     */
    @Autowired
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Nonnull
    @Override
    public <T> List<T> queryList(String sql, Class<T> type, Object... params) {
        return query(sql, params, getBeanPropertyRowMapper(type));
    }

    @Nonnull
    @Override
    public List<Map<String, Object>> queryMapList(String sql, Object[] params) {
        return query(sql, params, getColumnMapRowMapper());
    }

    @Nonnull
    @Override
    public <T> List<T> querySingleList(String sql, Class<T> type, Object... params) {
        return query(sql, params, getSingleColumnRowMapper(type));
    }

    @Nonnull
    @Override
    public <T> Optional<T> queryOne(String sql, Class<T> type, Object... params) {
        return queryOne(sql, getBeanPropertyRowMapper(type), params);
    }

    @NotNull
    @Override
    public Optional<Map<String, Object>> queryMapOne(String sql, Object... params) {
        return queryOne(sql, getColumnMapRowMapper(), params);
    }

    @NotNull
    @Override
    public <T> Optional<T> querySingleOne(String sql, Class<T> type, Object... params) {
        return queryOne(sql, getSingleColumnRowMapper(type), params);
    }

    @Nonnull
    protected <T> RowMapper<T> getBeanPropertyRowMapper(Class<T> requiredType) {
        var mapper = BeanPropertyRowMapper.newInstance(requiredType);
        mapper.setPrimitivesDefaultedForNullValue(true);
        mapper.setConversionService(conversionService);
        return mapper;
    }
}
