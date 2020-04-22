package com.mini.core.jdbc.builder;

import com.mini.core.jdbc.annotation.*;
import com.mini.core.util.ThrowsUtil;
import com.mini.core.util.holder.ClassHolder;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.io.Serializable;
import java.util.Date;
import java.util.EventListener;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Class.forName;
import static java.lang.System.currentTimeMillis;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.of;

@Singleton
public final class SQLInterfaceDef implements SQLInterface, EventListener, Serializable {
	private static final Map<Class<?>, SQLInterface> INTER_MAP = new ConcurrentHashMap<>();
	private static final String $SQL$ = "_$$$SQL$$$";
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> void createReplace(SQLBuilder builder, @Nonnull T instance) {
		Class<? extends T> type = (Class<T>) instance.getClass();
		ClassHolder<? extends T> table = ClassHolder.create(type);
		Table aTable = table.getAnnotation(Table.class);
		Objects.requireNonNull(aTable);
		
		builder.replaceInto(aTable.value());
		table.fields().forEach(h -> {
			// 带Auto注解的字段不处理
			if (nonNull(h.getAnnotation(Auto.class))) {
				return;
			}
			// 获取表字段信息
			Column column = h.getAnnotation(Column.class);
			if (isNull(column)) return;
			
			// 带Lock注解的字段设置值为当前时间
			if (nonNull(h.getAnnotation(Lock.class))) {
				builder.values(column.value());
				builder.args(currentTimeMillis());
				return;
			}
			
			// 带CreateAt注解的字段设置值为当前时间
			if (nonNull(h.getAnnotation(CreateAt.class))) {
				builder.values(column.value());
				builder.args(new Date());
				return;
			}
			
			// 带UpdateAt注解的字段设置值为当前时间
			if (nonNull(h.getAnnotation(UpdateAt.class))) {
				builder.values(column.value());
				builder.args(new Date());
				return;
			}
			
			// 其它字段
			builder.values(column.value());
			builder.args(h.getValue(instance));
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> void createInsert(SQLBuilder builder, @Nonnull T instance) {
		Class<? extends T> type = (Class<T>) instance.getClass();
		ClassHolder<? extends T> table = ClassHolder.create(type);
		Table aTable = table.getAnnotation(Table.class);
		Objects.requireNonNull(aTable);
		
		builder.insertInto(aTable.value());
		table.fields().forEach(h -> {
			// 带Auto注解的字段不处理
			if (nonNull(h.getAnnotation(Auto.class))) {
				return;
			}
			// 获取表字段信息
			Column column = h.getAnnotation(Column.class);
			if (isNull(column)) return;
			
			// 带Lock注解的字段设置值为当前时间
			if (nonNull(h.getAnnotation(Lock.class))) {
				builder.values(column.value());
				builder.args(currentTimeMillis());
				return;
			}
			
			// 带CreateAt注解的字段设置值为当前时间
			if (nonNull(h.getAnnotation(CreateAt.class))) {
				builder.values(column.value());
				builder.args(new Date());
				return;
			}
			
			// 带UpdateAt注解的字段设置值为当前时间
			if (nonNull(h.getAnnotation(UpdateAt.class))) {
				builder.values(column.value());
				builder.args(new Date());
				return;
			}
			
			// 其它字段
			builder.values(column.value());
			builder.args(h.getValue(instance));
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> void createDelete(SQLBuilder builder, @Nonnull T instance) {
		Class<? extends T> type = (Class<T>) instance.getClass();
		ClassHolder<? extends T> table = ClassHolder.create(type);
		Table aTable = table.getAnnotation(Table.class);
		Objects.requireNonNull(aTable);
		
		if (table.fields().stream().anyMatch(h -> {
			Del del = h.getAnnotation(Del.class);
			return nonNull(del);
		})) {
			// 有字段代表删除状态时，修改该字段为删除状态
			builder.update(aTable.value());
			table.fields().forEach(h -> {
				// 获取表字段信息
				Column column = h.getAnnotation(Column.class);
				if (isNull(column)) return;
				
				// 修改字段的删除状态
				Del del = h.getAnnotation(Del.class);
				if (nonNull(del)) {
					builder.set("%s = ?", column.value());
					builder.args(del.value());
				}
				
				// 修改字段的锁数据
				if (nonNull(h.getAnnotation(Lock.class))) {
					builder.set("%s = ?", column.value());
					builder.args(currentTimeMillis());
				}
			});
		} else builder.delete(aTable.value()).from(aTable.value());
		
		// 添加修改或者删除的条件
		table.fields().forEach(h -> {
			// 获取表字段信息
			Column column = h.getAnnotation(Column.class);
			if (column == null) return;
			
			// 添加字段ID条件
			if (nonNull(h.getAnnotation(Id.class))) {
				builder.where("%s = ?", column.value());
				builder.args(h.getValue(instance));
			}
			
			// 添加字段锁条件
			if (nonNull(h.getAnnotation(Lock.class))) {
				builder.where("%s = ?", column.value());
				builder.args(h.getValue(instance));
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> void createUpdate(SQLBuilder builder, @Nonnull T instance) {
		Class<? extends T> type = (Class<T>) instance.getClass();
		ClassHolder<? extends T> table = ClassHolder.create(type);
		Table aTable = table.getAnnotation(Table.class);
		Objects.requireNonNull(aTable);
		
		builder.update(aTable.value());
		table.fields().forEach(h -> {
			// 带ID注解的字段不修改
			if (nonNull(h.getAnnotation(Id.class))) {
				return;
			}
			
			// 带CreateAt注解的字段不修改
			if (nonNull(h.getAnnotation(CreateAt.class))) {
				return;
			}
			
			// 获取表字段信息
			Column column = h.getAnnotation(Column.class);
			if (column == null) return;
			
			// 还UpdateAt注解的字段设置字段值为当前时间
			if (nonNull(h.getAnnotation(UpdateAt.class))) {
				builder.set("%s = ?", column.value());
				builder.args(new Date());
				return;
			}
			
			// 带Lock注解的字段修改为当前时间戳值
			if (nonNull(h.getAnnotation(Lock.class))) {
				builder.set("%s = ?", column.value());
				builder.args(currentTimeMillis());
				return;
			}
			
			// 其它所有的字段
			builder.set("%s = ?", column.value());
			builder.args(h.getValue(instance));
		});
		
		table.fields().forEach(h -> {
			// 获取表字段信息
			Column column = h.getAnnotation(Column.class);
			if (column == null) return;
			
			// 添加字段ID条件
			if (nonNull(h.getAnnotation(Id.class))) {
				builder.where("%s = ?", column.value());
				builder.args(h.getValue(instance));
				return;
			}
			
			// 带Lock注解的字段设置修改值和条件
			if (nonNull(h.getAnnotation(Lock.class))) {
				builder.where("%s = ?", column.value());
				builder.args(h.getValue(instance));
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> void createInsertOnUpdate(SQLBuilder builder, T instance) {
		Class<? extends T> type = (Class<T>) instance.getClass();
		ClassHolder<? extends T> table = ClassHolder.create(type);
		Table aTable = table.getAnnotation(Table.class);
		Objects.requireNonNull(aTable);
		
		builder.insertInto(aTable.value());
		table.fields().forEach(h -> {
			// 带Auto注解的字段不处理
			if (nonNull(h.getAnnotation(Auto.class))) {
				return;
			}
			// 获取表字段信息
			Column column = h.getAnnotation(Column.class);
			if (isNull(column)) return;
			
			// 带Lock注解的字段设置值为当前时间
			if (nonNull(h.getAnnotation(Lock.class))) {
				builder.values(column.value());
				builder.args(currentTimeMillis());
				return;
			}
			
			// 带CreateAt注解的字段设置值为当前时间
			if (nonNull(h.getAnnotation(CreateAt.class))) {
				builder.values(column.value());
				builder.args(new Date());
				return;
			}
			
			// 带UpdateAt注解的字段设置值为当前时间
			if (nonNull(h.getAnnotation(UpdateAt.class))) {
				builder.values(column.value());
				builder.args(new Date());
				return;
			}
			
			// 其它字段
			builder.values(column.value());
			builder.args(h.getValue(instance));
		});
		
		table.fields().forEach(h -> {
			// 带ID注解的字段不修改
			if (nonNull(h.getAnnotation(Id.class))) {
				return;
			}
			
			// 带CreateAt注解的字段不修改
			if (nonNull(h.getAnnotation(CreateAt.class))) {
				return;
			}
			
			// 获取表字段信息
			Column column = h.getAnnotation(Column.class);
			if (column == null) return;
			
			// 还UpdateAt注解的字段设置字段值为当前时间
			if (nonNull(h.getAnnotation(UpdateAt.class))) {
				builder.onDuplicateKeyUpdate("%s = ?", column.value());
				builder.args(new Date());
				return;
			}
			
			// 带Lock注解的字段修改为当前时间戳值
			if (nonNull(h.getAnnotation(Lock.class))) {
				builder.onDuplicateKeyUpdate("%s = ?", column.value());
				builder.args(currentTimeMillis());
				return;
			}
			
			// 其它所有的字段
			builder.onDuplicateKeyUpdate("%s = VALUES(%s)", column.value(), column.value());
		});
	}
	
	@Override
	public final <T> void createSelect(SQLBuilder builder, @Nonnull Class<T> type) {
		ClassHolder<? extends T> table = ClassHolder.create(type);
		Table aTable = table.getAnnotation(Table.class);
		Objects.requireNonNull(aTable);
		
		// 查询字段信息
		table.fields().forEach(h -> {
			// 获取表字段信息
			Column column = h.getAnnotation(Column.class);
			if (column == null) return;
			
			// 添加查询字段
			builder.select(column.value());
		});
		// from 表名
		builder.from(aTable.value());
		
		// 处理关联表信息
		for (Join join : table.getAnnotationsByType(Join.class)) {
			join.type().execute(builder, join.value(), join.args());
		}
		
		// 查询字段信息
		table.fields().forEach(h -> {
			// 获取表字段信息
			Column column = h.getAnnotation(Column.class);
			if (column == null) return;
			
			// 排除删除的数据
			Del del = h.getAnnotation(Del.class);
			if (nonNull(del)) {
				builder.where("%s <> ?", column.value());
				builder.args(del.value());
			}
		});
	}
	
	// 获取SQL创建的实现类
	public static <T> SQLInterface getSQLInterface(Class<T> type) {
		return SQLInterfaceDef.INTER_MAP.computeIfAbsent(type, key -> {
			Class<?> mType;
			try {
				mType = forName(type.getCanonicalName() + SQLInterfaceDef.$SQL$);
				of(mType).filter(SQLInterface.class::isAssignableFrom)
						.orElseThrow(NoClassDefFoundError::new);
			} catch (ReflectiveOperationException | NoClassDefFoundError e) {
				mType = SQLInterfaceDef.class;
			}
			try {
				var mClass = mType.asSubclass(SQLInterface.class);
				return mClass.getDeclaredConstructor().newInstance();
			} catch (ReflectiveOperationException | NoClassDefFoundError e) {
				throw ThrowsUtil.hidden(e);
			}
		});
	}
}
