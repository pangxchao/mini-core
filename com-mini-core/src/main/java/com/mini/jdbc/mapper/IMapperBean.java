package com.mini.jdbc.mapper;

import static com.mini.jdbc.mapper.IMapperMap.INSTANCE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

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
		Map<String, Object> value = INSTANCE.get(rs, number);
		return new JSONObject(value).toJavaObject(clazz);
	}
}
