package com.mini.plugin.util;

import com.mini.plugin.config.ColumnInfo;
import com.mini.plugin.config.Settings;

import java.io.Serializable;
import java.util.EventListener;
import java.util.Optional;


public final class ColumnUtil implements EventListener, Serializable {
	@SuppressWarnings("unchecked")
	public static Class<?> getColumnType(ColumnInfo column) {
		return Optional.of(Settings.getInstance())
			.map(Settings::getCurrentGroupMapper)
			.map(group -> group.get(column.getDbType()))
			.map(m -> column.isNotNull() ? m.getJavaType() : m.getNullJavaType())
			.map(ClassUtil::forName)
			.orElse((Class) Object.class);
	}
}
