package com.mini.plugin.config;

import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * 数据类型映射表格信息
 * @author xchao
 */
public final class DataType extends AbstractClone<DataType> implements Serializable {
	private String databaseType;
	private String nullJavaType;
	private String javaType;
	
	public DataType setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
		return this;
	}
	
	public DataType setNullJavaType(String nullJavaType) {
		this.nullJavaType = nullJavaType;
		return this;
	}
	
	public DataType setJavaType(String javaType) {
		this.javaType = javaType;
		return this;
	}
	
	@NotNull
	public synchronized String getDatabaseType() {
		if (StringUtil.isEmpty(databaseType)) {
			databaseType = "VARCHAR";
		}
		return databaseType.toUpperCase();
	}
	
	@NotNull
	public synchronized String getNullJavaType() {
		if (StringUtil.isEmpty(nullJavaType)) {
			nullJavaType = "String";
		}
		return nullJavaType;
	}
	
	@NotNull
	public synchronized String getJavaType() {
		if (StringUtil.isEmpty(javaType)) {
			javaType = "String";
		}
		return javaType;
	}
	
	@NotNull
	@Override
	public final synchronized DataType clone() {
		final DataType info = new DataType();
		info.setDatabaseType(databaseType);
		info.setNullJavaType(nullJavaType);
		info.setJavaType(javaType);
		return info;
	}
}
