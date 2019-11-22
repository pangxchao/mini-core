package com.mini.core.jdbc.mapper;

import com.alibaba.fastjson.JSONObject;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static com.mini.core.jdbc.mapper.IMapperMap.INSTANCE;

/**
 * BeanMapper.java
 * @author xchao
 */

public final class IMapperBean<T> implements IMapper<T> {
    private final Class<T> clazz;

    private IMapperBean(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Nonnull
    @Override
    public T get(ResultSet rs, int number) throws SQLException {
        Map<String, Object> value = INSTANCE.get(rs, number);
        return new JSONObject(value).toJavaObject(clazz);
    }
}
