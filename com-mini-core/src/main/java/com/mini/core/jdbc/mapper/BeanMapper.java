package com.mini.core.jdbc.mapper;

import com.mini.core.holder.ClassHolder;
import com.mini.core.holder.FieldHolder;
import com.mini.core.util.ThrowsUtil;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.mini.core.jdbc.util.JdbcUtil.getObject;
import static com.mini.core.jdbc.util.JdbcUtil.lookupColumnName;
import static java.lang.Class.forName;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.Validate.isTrue;

/**
 * BeanMapper.java
 * @author xchao
 */
@Singleton
public final class BeanMapper<T> implements Mapper<T>, EventListener, Serializable {
	private static final Map<Class<?>, Mapper<?>> MAP = new ConcurrentHashMap<>();
	private static final String $MAPPER$ = "_$$$MAPPER$$$";
	private final ClassHolder<T> holder;
	
	private BeanMapper(@Nonnull Class<T> type) {
		holder = ClassHolder.create(type);
		isTrue(holder != null && holder.hasTable());
	}
	
	@Nonnull
	@Override
	public T get(ResultSet rs, int number) throws SQLException {
		var result = holder.createInstance();
		ResultSetMetaData metaData = rs.getMetaData();
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			var name = lookupColumnName(metaData, i);
			try {
				if (!holder.hasColumn(name)) continue;
				FieldHolder<T> h = holder.getColumn(name);
				var o = getObject(rs, i, h.getFieldType());
				h.setValue(result, o);
			} catch (Exception ignored) {}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Mapper<T> create(Class<T> type) {
		return (Mapper<T>) MAP.computeIfAbsent(type, key -> {
			Class<?> mType;
			try {
				mType = forName(type.getCanonicalName() + BeanMapper.$MAPPER$);
				ofNullable(mType).filter(Mapper.class::isAssignableFrom)
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
}
