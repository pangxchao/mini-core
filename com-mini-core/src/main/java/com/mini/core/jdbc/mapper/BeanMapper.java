package com.mini.core.jdbc.mapper;

import com.mini.core.jdbc.annotation.Column;
import com.mini.core.jdbc.annotation.Table;
import com.mini.core.jdbc.util.JdbcUtil;
import com.mini.core.util.ThrowsUtil;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import javax.annotation.Nonnull;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.mini.core.jdbc.util.JdbcUtil.getObject;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * BeanMapper.java
 * @author xchao
 */

public final class BeanMapper<T> implements Mapper<T>, EventListener, Serializable {
	private static Map<Class<?>, BeanMapper<?>> MAP = new ConcurrentHashMap<>();
	private final Map<String, Field> FIELD_MAP = new HashMap<>();
	private final Class<T> type;

	private BeanMapper(Class<T> type) {
		this.type = type;
	}

	@Nonnull
	@Override
	public T get(ResultSet rs, int number) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		try {
			T value = ConstructorUtils.invokeConstructor(type);
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				try {
					// 获取字段名称
					String column = JdbcUtil.lookupColumnName(metaData, i);

					// 属性字段为空时，不处理
					Field field = FIELD_MAP.get(column);
					if (field == null) continue;

					// 获取属性信息
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), type);
					Method method = pd.getWriteMethod();
					if (method == null) continue;


					// 设置字段的值
					method.invoke(value, getObject(rs, column, field.getType()));
				} catch (IntrospectionException ignored) {}
			}
			return value;
		} catch (ReflectiveOperationException e) {
			throw ThrowsUtil.hidden(e);
		}
	}

	private void addField(String fieldName, Field field) {
		FIELD_MAP.putIfAbsent(fieldName, field);
	}

	@SuppressWarnings("unchecked")
	public static <T> BeanMapper<T> create(Class<T> type) {
		return (BeanMapper<T>) MAP.computeIfAbsent(type, key -> {
			Table table = type.getAnnotation(Table.class);
			Class<? super T> supers = type.getSuperclass();
			while (table == null && supers != null) {
				table = supers.getAnnotation(Table.class);
			}
			// 验证@Table是否为空，为空时提示错误信息
			notNull(table, "%s is not find @Table", type.getName());

			// 创建 Mapper 对象
			BeanMapper<T> mapper = new BeanMapper<>(type);
			for (supers = type; supers != null; supers = supers.getSuperclass()) {
				for (Field field : supers.getDeclaredFields()) {
					Optional.ofNullable(field.getAnnotation(Column.class))
						.ifPresent(column -> mapper.addField(column.value(), field));
				}
			}
			return mapper;
		});
	}
}
