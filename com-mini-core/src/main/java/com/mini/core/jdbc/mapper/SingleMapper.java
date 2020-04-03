package com.mini.core.jdbc.mapper;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.mini.core.jdbc.util.JdbcUtil.getObject;
import static org.apache.commons.lang3.Validate.isTrue;

public final class SingleMapper<T> implements Mapper<T>, EventListener, Serializable {
	private static final Map<Class<?>, SingleMapper<?>> MAP = new ConcurrentHashMap<>();
	private final Class<T> type;
	
	private SingleMapper(Class<T> type) {
		this.type = type;
	}
	
	@Nonnull
	@Override
	public T get(ResultSet rs, int number) throws SQLException {
		isTrue(rs.getMetaData().getColumnCount() == 1);
		return type.cast(getObject(rs, 1, type));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Mapper<T> create(Class<T> type) {
		return (Mapper<T>) MAP.computeIfAbsent(type, k -> {
			return new SingleMapper<>(type); //
		});
	}
}
