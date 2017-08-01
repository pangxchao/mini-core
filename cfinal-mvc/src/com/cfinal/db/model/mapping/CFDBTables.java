/**
 * Created the com.cfinal.db.model.mapping.CFDBTables.java
 * @created 2017年4月27日 上午8:50:47
 * @version 1.0.0
 */
package com.cfinal.db.model.mapping;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.cfinal.db.CFDBException;
import com.cfinal.db.CFDBFactory;
import com.cfinal.db.CFDB;

/**
 * com.cfinal.db.model.mapping.CFDBTables.java
 * @author XChao
 */
public class CFDBTables {

	private String tableName;
	private Class<?> clazzInfo;

	// 当前实体的所有字段，包括联合表查询结果的所有字段，单表操作时，这些字段可以在数据库表中不存在
	private final Map<String, CFDBField> allColumns = new HashMap<>();
	// 当前实体的字段，这些字段为当前实体对应数据库表必须存在的字段
	private final Map<String, Boolean> currentColumns = new HashMap<>();
	// 当前实体的所有主键字段，这些字段为当前实体对应数据库表必须存在的字段
	private final Map<String, Boolean> primaryColumns = new HashMap<>();

	/**
	 * 获取当前实体对应的数据库表名
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 设置当前实体对应的数据库表名
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 获取当前实体的Class信息
	 * @return the clazzInfo
	 */
	public Class<?> getClazzInfo() {
		return clazzInfo;
	}

	/**
	 * 设置当前实体Class信息
	 * @param clazzInfo the clazzInfo to set
	 */
	public void setClazzInfo(Class<?> clazzInfo) {
		this.clazzInfo = clazzInfo;
	}

	/**
	 * 获取当前实体所有字段的Set集合
	 * @return
	 */
	public Set<String> currentColumnKeySet() {
		return this.currentColumns.keySet();
	}

	/**
	 * 获取所有非主键字段
	 * @return
	 */
	public List<String> getCurrentColumnExcludePk() {
		List<String> result = new ArrayList<>();
		for (String column : currentColumnKeySet()) {
			result.add(column);
		}
		return result;
	}

	/**
	 * 获取当前实体对应表的所有字段与属性的Set集合
	 * @return
	 */
	public Set<String> allColumnKeySet() {
		return allColumns.keySet();
	}

	/**
	 * 获取当前实体对应表的所有主键的Set集合
	 * @return
	 */
	public Set<String> primaryKeySet() {
		return this.primaryColumns.keySet();
	}

	/**
	 * 添加一个当前表的字段
	 * @param columnName
	 * @return
	 */
	public Boolean addCurrentColumn(String columnName) {
		return this.currentColumns.put(columnName, true);
	}

	/**
	 * 给当前实体添加一个字段与属性的映射
	 * @param columnName 字段名称
	 * @param dbFields 属性信息
	 * @return
	 */
	public CFDBField addColumnFields(CFDBField dbFields) {
		return this.allColumns.put(dbFields.getDbName(), dbFields);
	}

	/**
	 * 获取当前实体设置一个主键字段
	 * @param columnName
	 * @return
	 */
	public Boolean setPrimarys(String columnName) {
		return this.primaryColumns.put(columnName, true);
	}

	/**
	 * 根据字段名称， 获取当前字段对应实体的字段信息
	 * @param columnName 字段名称
	 * @return
	 */
	public CFDBField getDBFields(String columnName) {
		return this.allColumns.get(columnName);
	}

	/**
	 * 获取数据库字段查询时的别名
	 * @param columnName
	 * @return
	 */
	public String getLable(String columnName) {
		return this.getDBFields(columnName).getLabName();
	}

	/**
	 * 根据字段名获取属性的getter方法
	 * @param columnName
	 * @return
	 */
	public Method getGetter(String columnName) {
		return this.getDBFields(columnName).getGetter();
	}

	/**
	 * 根据字段名，获取属性的setter方法
	 * @param columnName
	 * @return
	 */
	public Method getSetter(String columnName) {
		return this.getDBFields(columnName).getSetter();
	}

	/**
	 * 根据字段名，获取属性的类型
	 * @param columnName
	 * @return
	 */
	public Class<?> getTypes(String columnName) {
		return this.getDBFields(columnName).getTypes();
	}

	/**
	 * 根据字段名、实体信息，获取该实体该属性的值
	 * @param columnName
	 * @param instence
	 * @return
	 */
	public Object getValue(String columnName, Object instence) {
		return this.getDBFields(columnName).getValue(instence);
	}

	/**
	 * 根据字段名、实体信息，设置该实体该属性的值
	 * @param columnName
	 * @param instence
	 * @param value
	 * @return
	 */
	public void setValue(String columnName, Object instence, Object value) {
		this.getDBFields(columnName).setValue(instence, value);
	}

	/**
	 * 判断当前字段是否为该实体对应数据库表的的主键字段
	 * @param columnName 字段名称
	 * @return
	 */
	public boolean isPrimarys(String columnName) {
		return this.primaryColumns.get(columnName) != null //
			&& this.primaryColumns.get(columnName);
	}

	/**
	 * 获取指定表的创建表的SQL语句
	 * @param db
	 * @param tableName
	 * @return
	 */
	public static String getCreateTable(CFDB db, String tableName) {
		ResultSet rs = null;
		try {
			rs = db.executeQuery("show create table " + tableName);
			if(rs.next()) {
				return rs.getString(2);
			}
		} catch (SQLException e) {
			throw new CFDBException(e);
		} finally {
			CFDBFactory.close(rs);
		}
		return "";
	}

	/**
	 * 获取指定表所有的字段
	 * @param db
	 * @param tableName
	 * @return
	 */
	public static JSONArray getColumns(CFDB db, String tableName) {
		try {
			return db.analysis(db.getMetaData().getColumns(null, null, tableName, null));
		} catch (SQLException e) {
			throw new CFDBException(e);
		}
	}

	/**
	 * 获取指定表所有的主键字段
	 * @param db
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getPrimaryKey(CFDB db, String tableName) {
		try {
			return db.analysis(db.getMetaData().getPrimaryKeys(null, null, tableName));
		} catch (SQLException e) {
			throw new CFDBException(e);
		}
	}

	/**
	 * 获取指定表所有的外键， 其它表指向该表的所有外键
	 * @param db
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getExportedKeys(CFDB db, String tableName) {
		try {
			return db.analysis(db.getMetaData().getExportedKeys(null, null, tableName));
		} catch (SQLException e) {
			throw new CFDBException(e);
		}
	}

	/**
	 * 获取所有表指定的外键，该表指向其它表的所有外键
	 * @param db
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getImportedKeys(CFDB db, String tableName) {
		try {
			return db.analysis(db.getMetaData().getImportedKeys(null, null, tableName));
		} catch (SQLException e) {
			throw new CFDBException(e);
		}
	}

	/**
	 * 获取指定表所有的索引
	 * @param db
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getIndexInfo(CFDB db, String tableName) {
		try {
			return db.analysis(db.getMetaData().getIndexInfo(null, null, tableName, false, false));
		} catch (SQLException e) {
			throw new CFDBException(e);
		}
	}
}
