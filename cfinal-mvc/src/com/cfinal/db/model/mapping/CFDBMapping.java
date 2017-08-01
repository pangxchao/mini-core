/**
 * Created the com.cfinal.db.model.mapping.CFDBMapping.java
 * @created 2017年4月26日 下午4:32:21
 * @version 1.0.0
 */
package com.cfinal.db.model.mapping;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cfinal.db.CFDB;
import com.cfinal.util.lang.CFString;

/**
 * com.cfinal.db.model.mapping.CFDBMapping.java
 * @author XChao
 */
public class CFDBMapping {
	private static final Map<Class<?>, CFDBTables> DB_MAPPING = new HashMap<>();

	/**
	 * 根据java类， 获取该类对应数据库的表信息与字段信息
	 * @param clazz
	 * @return
	 */
	public static CFDBTables getTables(CFDB db, Class<?> clazz) {
		try {
			CFDBTables tables = DB_MAPPING.get(clazz);
			if(tables == null) {
				// 创建数据库与实体的映射信息
				tables = new CFDBTables();
				tables.setClazzInfo(clazz);
				// 设置java类信息并扫描java类信息
				scanningClazz(tables, clazz);
				// 扫描数据库字段信息
				scanningField(db, tables, clazz);
				// 读取所有数据库的主键字段
				getPrimaryKeys(db, tables);
				// 将数据库映射信息添加到全局缓存
				DB_MAPPING.put(clazz, tables);
			}
			return tables;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 扫描类信息与类注解信息， 关键数据库表
	 * @param tables
	 * @param clazz
	 */
	private static void scanningClazz(CFDBTables tables, Class<?> clazz) {
		CFDBName dbName = clazz.getAnnotation(CFDBName.class);
		String tableName = dbName == null ? null : dbName.value();
		if(StringUtils.isBlank(tableName)) {
			tableName = CFString.toDBName(clazz.getSimpleName());
		}
		tables.setTableName(tableName);
	}

	/**
	 * 扫描字段信息， 关与表关联
	 * @param tables
	 * @param clazz
	 * @throws IntrospectionException
	 */
	private static void scanningField(CFDB db, CFDBTables tables, Class<?> clazz) throws Exception {
		// 首先从数据库获取所有字段， 放在Map里面
		JSONArray arrays = CFDBTables.getColumns(db, tables.getTableName());
		for (int i = 0, len = arrays.size(); i < len; i++) {
			JSONObject objects = arrays.getJSONObject(i);
			tables.addCurrentColumn(objects.getString("COLUMN_NAME"));
		}
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
			// 创建一个数据库字段/java属性对象，并设置DBFields的所有信息
			CFDBField dbFields = new CFDBField();
			// 数据库字段/java属性对象 数据库字段名称
			dbFields.setDbName(CFString.toDBName(property.getName()));
			// 数据库字段/java属性对象 的java属性名称
			dbFields.setLabName(property.getName());
			// 数据库字段/java属性对象 的java setter 方法对象
			dbFields.setSetter(property.getWriteMethod());
			// 数据库字段/java属性对象 的java getter 方法对象
			dbFields.setGetter(property.getReadMethod());
			// 数据库字段/java属性对象 的java 属性类型 Class<?>对象
			dbFields.setTypes(property.getPropertyType());
			// 如果 java 对象的 setter 方法不为空时
			if(dbFields.getSetter() != null) {
				// 获取该方法的 @DBName 注解信息
				CFDBName dbName = dbFields.getSetter().getAnnotation(CFDBName.class);
				// 如果注解信息不能空，并且注解信息的value值不为空时，则设置数据库字段名称为 注解的value值
				if(dbName != null && StringUtils.isNotBlank(dbName.value())) {
					dbFields.setDbName(dbName.value());
				}
			}
			// 将DBFields信息添加的DBMapping映射中
			tables.addColumnFields(dbFields);
		}
	}

	/**
	 * 读取所有数据库的主键字段
	 * @param db
	 * @param tables
	 */
	private static void getPrimaryKeys(CFDB db, CFDBTables tables) {
		JSONArray arrays = CFDBTables.getPrimaryKey(db, tables.getTableName());
		for (int i = 0, len = arrays.size(); i < len; i++) {
			JSONObject objects = arrays.getJSONObject(i);
			tables.setPrimarys(objects.getString("COLUMN_NAME"));
		}
	}
}
