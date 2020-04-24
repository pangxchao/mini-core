package com.mini.core.jdbc.mapper;

import com.mini.core.jdbc.annotation.Column;
import com.mini.core.jdbc.util.JdbcUtil;
import com.mini.core.util.ThrowsUtil;
import com.mini.core.util.holder.ClassHolder;
import com.mini.core.util.holder.FieldHolder;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.mini.core.jdbc.util.JdbcUtil.lookupColumnName;
import static java.lang.Class.forName;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

/**
 * BeanMapper.java
 * @author xchao
 */
@Singleton
public final class BeanMapper<T> implements Mapper<T>, EventListener, Serializable {
	private static final Map<Class<?>, Mapper<?>> MAP = new ConcurrentHashMap<>();
	private static final Map<Class<?>, ResultSetCall> RES = new HashMap<>() {{
		this.put(String.class, JdbcUtil::getString);
		this.put(Long.class, JdbcUtil::getOLong);
		this.put(long.class, JdbcUtil::getLong);
		this.put(Integer.class, JdbcUtil::getInteger);
		this.put(int.class, JdbcUtil::getInt);
		this.put(Short.class, JdbcUtil::getOShort);
		this.put(short.class, JdbcUtil::getShort);
		this.put(Byte.class, JdbcUtil::getOByte);
		this.put(byte.class, JdbcUtil::getByte);
		this.put(Double.class, JdbcUtil::getODouble);
		this.put(double.class, JdbcUtil::getDouble);
		this.put(Float.class, JdbcUtil::getOFloat);
		this.put(float.class, JdbcUtil::getFloat);
		this.put(Boolean.class, JdbcUtil::getOBoolean);
		this.put(boolean.class, JdbcUtil::getBoolean);
		this.put(byte[].class, JdbcUtil::getBytes);
		this.put(Date.class, JdbcUtil::getTimestamp);
		this.put(java.sql.Timestamp.class, JdbcUtil::getTimestamp);
		this.put(java.sql.Date.class, JdbcUtil::getDate);
		this.put(java.sql.Time.class, JdbcUtil::getTime);
		this.put(LocalDateTime.class, (rs, columnIndex) -> {
			var value = JdbcUtil.getTimestamp(rs, columnIndex);
			return value == null ? null : value.toLocalDateTime();
		});
		this.put(LocalDate.class, (rs, columnIndex) -> {
			var value = JdbcUtil.getDate(rs, columnIndex);
			return value == null ? null : value.toLocalDate();
		});
		this.put(LocalTime.class, (rs, columnIndex) -> {
			var value = JdbcUtil.getTime(rs, columnIndex);
			return value == null ? null : value.toLocalTime();
		});
	}};
	private final Map<String, FieldHolder<T>> columns = new HashMap<>();
	private static final ResultSetCall DEF = JdbcUtil::getObject;
	private static final String $MAPPER$ = "_$$$MAPPER$$$";
	private final ClassHolder<T> holder;
	
	private BeanMapper(@Nonnull Class<T> type) {
		this.holder = requireNonNull(ClassHolder.create(type));
		BeanMapper.this.holder.fields().forEach(field -> {
			Column c = field.getAnnotation(Column.class);
			if (Objects.isNull(c)) return;
			// 获取字段名和别名
			var name = defaultIfBlank(c.alias(), c.value());
			columns.put(name, field);
		});
	}
	
	@Nonnull
	@Override
	public T get(ResultSet rs, int number) throws SQLException {
		var result = holder.createInstance();
		ResultSetMetaData metaData = rs.getMetaData();
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			try {
				var name = lookupColumnName(metaData, i);
				FieldHolder<T> h = columns.get(name);
				if (h != null) {
					var res = RES.get(h.getType());
					res = res == null ? DEF : res;
					var o = res.apply(rs, i);
					h.setValue(result, o);
				}
			} catch (Exception ignored) {
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Mapper<T> create(Class<T> type) {
		return (Mapper<T>) MAP.computeIfAbsent(type, key -> {
			Class<?> mType;
			try {
				mType = forName(type.getCanonicalName() + BeanMapper.$MAPPER$);
				Optional.of(mType).filter(Mapper.class::isAssignableFrom)
						.orElseThrow(NoClassDefFoundError::new);
			} catch (ReflectiveOperationException | NoClassDefFoundError e) {
				mType = BeanMapper.class;
			}
			try {
				Class<? extends Mapper> mClass = mType.asSubclass(Mapper.class);
				return mClass.getDeclaredConstructor(Class.class).newInstance(type);
			} catch (ReflectiveOperationException | NoClassDefFoundError e) {
				throw ThrowsUtil.hidden(e);
			}
		});
	}
	
	private interface ResultSetCall {
		Object apply(ResultSet rs, int columnIndex) throws SQLException;
	}
}
