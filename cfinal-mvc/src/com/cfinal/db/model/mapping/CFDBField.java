/**
 * Created the com.cfinal.db.model.mapping.CFDBField.java
 * @created 2017年6月16日 下午5:29:43
 * @version 1.0.0
 */
package com.cfinal.db.model.mapping;

import java.lang.reflect.Method;

/**
 * com.cfinal.db.model.mapping.CFDBFields.java
 * @author XChao
 */
public class CFDBField {
	private String labName;
	private String dbName;
	private Method getter;
	private Method setter;
	private Class<?> types;

	/**
	 * @return the labName
	 */
	public String getLabName() {
		return labName;
	}

	/**
	 * @param labName the labName to set
	 */
	public void setLabName(String labName) {
		this.labName = labName;
	}

	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * @param dbName the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * @return the getter
	 */
	public Method getGetter() {
		return getter;
	}

	/**
	 * @param getter the getter to set
	 */
	public void setGetter(Method getter) {
		this.getter = getter;
	}

	/**
	 * @return the setter
	 */
	public Method getSetter() {
		return setter;
	}

	/**
	 * @param setter the setter to set
	 */
	public void setSetter(Method setter) {
		this.setter = setter;
	}

	/**
	 * @return the types
	 */
	public Class<?> getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(Class<?> types) {
		this.types = types;
	}

	/**
	 * 获取实体字段的值
	 * @param instence
	 * @return
	 */
	public Object getValue(Object instence) {
		try {
			if(this.getter != null) {
				return this.getter.invoke(instence);
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 设置实体的字段值
	 * @param instence
	 * @param value
	 */
	public void setValue(Object instence, Object value) {
		try {
			if(this.setter != null) {
				this.setter.invoke(instence, value);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
