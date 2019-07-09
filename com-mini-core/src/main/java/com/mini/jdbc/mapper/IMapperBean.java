package com.mini.jdbc.mapper;

import com.alibaba.fastjson.JSONObject;
import com.mini.jdbc.util.SQLValue;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * BeanMapper.java
 * @author xchao
 */
public final class IMapperBean<T> implements IMapper<T> {
    private final Class<T> clazz;

    private IMapperBean(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T get(ResultSet rs, int number) throws SQLException {
        SQLValue value = IMapperMap.INSTANCE.get(rs, number);
        return new JSONObject(value).toJavaObject(clazz);
    }
}
