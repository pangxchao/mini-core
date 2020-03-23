package com.mini.core.jdbc.builder;

import com.mini.core.holder.ClassHolder;
import com.mini.core.holder.FieldHolder;
import com.mini.core.util.ThrowsUtil;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.io.Serializable;
import java.util.Date;
import java.util.EventListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Class.forName;
import static java.lang.System.currentTimeMillis;
import static java.util.Optional.ofNullable;

@Singleton
public final class SQLInterfaceDef implements SQLInterface, EventListener, Serializable {
	private static final Map<Class<?>, SQLInterface> INTER_MAP = new ConcurrentHashMap<>();
	private static final String $SQL$ = "_$$$SQL$$$";
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> void createReplace(SQLBuilder builder, @Nonnull T instance) {
		Class<? extends T> type = (Class<T>) instance.getClass();
		var table = ClassHolder.create(type).verifyTable();
		// 添加除自增长的所有字段
		table.replace(builder).columns().stream().filter(h -> {
			return !h.hasAuto(); //
		}).forEach(holder -> holder.values(builder, h -> {
			var o = h.hasUpdate() || h.hasCreate() ? //
					new Date() : h.getValue(instance);
			builder.params(o);
		}));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> void createInsert(SQLBuilder builder, @Nonnull T instance) {
		Class<? extends T> type = (Class<T>) instance.getClass();
		var table = ClassHolder.create(type).verifyTable();
		// 添加除自增长的所有字段
		table.insert(builder).columns().stream().filter(h -> {
			return !h.hasAuto(); //
		}).forEach(holder -> holder.values(builder, h -> {
			var o = h.hasUpdate() || h.hasCreate() ? //
					new Date() : h.getValue(instance);
			builder.params(o);
		}));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> void createDelete(SQLBuilder builder, @Nonnull T instance) {
		Class<? extends T> type = (Class<T>) instance.getClass();
		var table = ClassHolder.create(type).verifyTable();
		// 有标识删除状态的字段时，逻辑删除
		if (table.hasColumnsAnyMatchDel()) {
			table.update(builder).columns().stream().filter(FieldHolder::hasDel)
					.forEach(holder -> holder.setDel(builder));
			table.columns().stream().filter(FieldHolder::hasLock).forEach(holder -> { //
				holder.set(builder, h -> builder.params(currentTimeMillis()));
			});
		}
		// 否则直接物理删除该数据
		else table.delete(builder).from(builder);
		
		// 添加ID条件
		table.columns().stream().filter(FieldHolder::hasId).forEach(holder -> { //
			holder.whereId(builder, h -> builder.params(h.getValue(instance)));
		});
		
		// 添加 Lock 条件
		table.columns().stream().filter(FieldHolder::hasLock).forEach(holder -> { //
			holder.whereLock(builder, h -> builder.params(h.getValue(instance)));
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> void createUpdate(SQLBuilder builder, @Nonnull T instance) {
		Class<? extends T> type = (Class<T>) instance.getClass();
		var table = ClassHolder.create(type).verifyTable();
		
		// 添加除ID、CreateAt和Lock标注的所有字段
		table.update(builder).columns().stream().filter(h -> {
			return !h.hasId() && !h.hasCreate() && !h.hasLock(); //
		}).forEach(holder -> holder.set(builder, h -> {
			var o = h.hasUpdate() ? new Date() //
					: h.getValue(instance);
			builder.params(o);
		}));
		// 添加 Lock 字段修改值
		table.columns().stream().filter(FieldHolder::hasLock).forEach(holder -> { //
			holder.set(builder, h -> builder.params(System.currentTimeMillis()));
		});
		
		// 添加ID条件
		table.columns().stream().filter(FieldHolder::hasId).forEach(holder -> holder.whereId(builder, h -> {
			builder.params(h.getValue(instance));
		}));
		
		// 添加 Lock 条件
		table.columns().stream().filter(FieldHolder::hasLock).forEach(holder -> holder.whereLock(builder, h -> {
			builder.params(h.getValue(instance));
		}));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> void createInsertOnUpdate(SQLBuilder builder, T instance) {
		Class<? extends T> type = (Class<T>) instance.getClass();
		var table = ClassHolder.create(type).verifyTable();
		// 添加除自增长的所有字段
		table.insert(builder).columns().stream().filter(h -> {
			return !h.hasAuto(); //
		}).forEach(holder -> holder.values(builder, h -> {
			var o = h.hasUpdate() || h.hasCreate() ? //
					new Date() : h.getValue(instance);
			builder.params(o);
		}));
		// 修改除ID和创建时间以外的所有字段
		table.columns().stream().filter(h -> {
			return !h.hasId() && !h.hasCreate(); //
		}).forEach(h -> h.onDuplicateKeyUpdate(builder));
	}
	
	@Override
	public final <T> void createSelect(SQLBuilder builder, @Nonnull Class<T> type) {
		var table = ClassHolder.create(type).verifyTable();
		// 验证@Table并生成查询语句
		table.select(builder).from(builder).join(builder);
		// 排除标记删除的数据
		table.columns().stream().filter(FieldHolder::hasDel)
				.forEach(h -> h.whereDel(builder));
	}
	
	// 获取SQL创建的实现类
	public static <T> SQLInterface getSQLInterface(Class<T> type) {
		return SQLInterfaceDef.INTER_MAP.computeIfAbsent(type, key -> {
			Class<?> mType;
			try {
				mType = forName(type.getCanonicalName() + SQLInterfaceDef.$SQL$);
				ofNullable(mType).filter(SQLInterface.class::isAssignableFrom)
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
