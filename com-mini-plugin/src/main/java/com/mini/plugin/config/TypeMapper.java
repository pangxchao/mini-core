package com.mini.plugin.config;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import static com.intellij.openapi.util.text.StringUtil.defaultIfEmpty;

public final class TypeMapper implements Serializable {
	private String databaseType, javaType;
	private String nullJavaType;
	
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
	
	public void setNullJavaType(String nullJavaType) {
		this.nullJavaType = nullJavaType;
	}
	
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	
	@NotNull
	public synchronized final String getNullJavaType() {
		return defaultIfEmpty(nullJavaType, //
			getJavaType());
	}
	
	@NotNull
	public synchronized final String getDatabaseType() {
		return defaultIfEmpty(databaseType, "");
	}
	
	@NotNull
	public synchronized final String getJavaType() {
		return defaultIfEmpty(javaType, "");
	}
	
	public static Builder builder() {
		return new Builder(new TypeMapper());
	}
	
	public static Builder builder(TypeMapper copy) {
		return new Builder(new TypeMapper())
			.databaseType(copy.getDatabaseType())
			.javaType(copy.getJavaType());
	}
	
	public static class Builder implements Serializable {
		private final TypeMapper mapper;
		
		protected Builder(TypeMapper m) {
			this.mapper = m;
		}
		
		public Builder databaseType(String databaseType) {
			mapper.setDatabaseType(databaseType);
			return this;
		}
		
		public Builder nullJavaType(String nullJavaType) {
			mapper.setNullJavaType(nullJavaType);
			return this;
		}
		
		public Builder javaType(String javaType) {
			mapper.setJavaType(javaType);
			return this;
		}
		
		public TypeMapper build() {
			return mapper;
		}
	}
}
