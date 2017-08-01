/**
 * Created the com.cfinal.db.model.CFDBModel.java
 * @created 2017年4月26日 下午4:31:38
 * @version 1.0.0
 */
package com.cfinal.db.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cfinal.db.CFDB;
import com.cfinal.db.CFSql;
import com.cfinal.db.model.mapping.CFDBMapping;
import com.cfinal.db.model.mapping.CFDBTables;
import com.cfinal.db.paging.CFPaging;

/**
 * com.cfinal.db.model.CFDBModel.java
 * @author XChao
 */
public interface CFDBModel<T extends CFDBModel<T>> extends Serializable {

	/**
	 * 保存当前实体信息到数据库，并返回影响条数
	 * @param db
	 * @return
	 */
	default int insert(CFDB db) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		Set<String> columns = tables.currentColumnKeySet();
		return db.insert(tables.getTableName(), StringUtils.join(columns, ", "),
			this.getParamsByColumns(tables, columns).toArray());
	}

	/**
	 * 保存当前实体指定字段信息到数据库，并返回影响条数
	 * @param db
	 * @param columns
	 * @return
	 */
	default int insert(CFDB db, String... columns) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		return db.insert(tables.getTableName(), StringUtils.join(columns, ", "),
			this.getParamsByColumns(tables, columns).toArray());
	}

	/**
	 * 保存当前实体信息到数据库， 并返回数据库自增长ID
	 * @param db
	 * @return
	 */
	default long insertGen(CFDB db) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		Set<String> columns = tables.currentColumnKeySet();
		return db.insertGen(tables.getTableName(), StringUtils.join(columns, ", "),
			this.getParamsByColumns(tables, columns).toArray());
	}

	/**
	 * 保存当前实体信息指定字段到数据库，并返回数据库自增长ID值
	 * @param db
	 * @param columns
	 * @return
	 */
	default long insertGen(CFDB db, String... columns) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		return db.insertGen(tables.getTableName(), StringUtils.join(columns, ", "),
			this.getParamsByColumns(tables, columns).toArray());
	}

	/**
	 * 保存当前实体信息到数据库，并返回影响条数<br/>
	 * 如果记录重复时，则覆盖之前的记录
	 * @param db
	 * @return
	 */
	default int replace(CFDB db) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		Set<String> columns = tables.currentColumnKeySet();
		return db.replace(tables.getTableName(), StringUtils.join(columns, ", "),
			this.getParamsByColumns(tables, columns).toArray());
	}

	/**
	 * 保存当前实体指定字段信息到数据库，并返回影响条数<br/>
	 * 如果记录重复时，则覆盖之前的记录
	 * @param db
	 * @param columns
	 * @return
	 */
	default int replace(CFDB db, String... columns) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		return db.replace(tables.getTableName(), StringUtils.join(columns, ", "),
			this.getParamsByColumns(tables, columns).toArray());
	}

	/**
	 * 保存当前实体信息到数据库， 并返回数据库自增长ID<br/>
	 * 如果记录重复时，则覆盖之前的记录
	 * @param db
	 * @return
	 */
	default long replaceGen(CFDB db) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		Set<String> columns = tables.currentColumnKeySet();
		return db.replaceGen(tables.getTableName(), StringUtils.join(columns, ", "),
			this.getParamsByColumns(tables, columns).toArray());
	}

	/**
	 * 保存当前实体信息指定字段到数据库，并返回数据库自增长ID值<br/>
	 * 如果记录重复时，则覆盖之前的记录
	 * @param db
	 * @param columns
	 * @return
	 */
	default long replaceGen(CFDB db, String... columns) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		return db.replaceGen(tables.getTableName(), StringUtils.join(columns, ", "),
			this.getParamsByColumns(tables, columns).toArray());
	}

	/**
	 * 根据ID，将当前记录同步到数据库<br/>
	 * 同步时，条件字段不会被修改
	 * @param db
	 * @return
	 */
	default int update(CFDB db) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		List<String> columns = tables.getCurrentColumnExcludePk();
		Set<String> requires = tables.primaryKeySet();
		List<Object> params = new ArrayList<>();
		params.addAll(this.getParamsByColumns(tables, columns));
		params.addAll(this.getParamsByColumns(tables, requires));
		return db.update(tables.getTableName(), StringUtils.join(columns, ", "), //
			StringUtils.join(requires, ", "), params.toArray());
	}

	/**
	 * 根据ID，将当前记录的指定字段同步到数据库
	 * @param db
	 * @param columns
	 * @return
	 */
	default int update(CFDB db, String... columns) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		Set<String> requires = tables.primaryKeySet();
		List<Object> params = new ArrayList<>();
		params.addAll(this.getParamsByColumns(tables, columns));
		params.addAll(this.getParamsByColumns(tables, requires));
		return db.update(tables.getTableName(), StringUtils.join(columns, ", "), //
			StringUtils.join(requires, ", "), params.toArray());
	}

	/**
	 * 根据ID删除数据库当前实体记录
	 * @param db
	 * @return
	 */
	default int delete(CFDB db) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		Set<String> requires = tables.primaryKeySet();
		return db.delete(tables.getTableName(), StringUtils.join(requires, ", "), //
			this.getParamsByColumns(tables, requires).toArray());
	}

	/**
	 * 根据指定条件字段，删除数据库当前实体记录
	 * @param db
	 * @param requires
	 * @return
	 */
	default int delete(CFDB db, String... requires) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		return db.delete(tables.getTableName(), StringUtils.join(requires, ", "), //
			this.getParamsByColumns(tables, requires).toArray());
	}

	/**
	 * 根据ID查询当前实体信息
	 * @param db
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default T queryById(CFDB db) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		CFSql sql = getBasiscQuerySql(tables);
		for (String columnName : tables.primaryKeySet()) {
			sql.append(" and ").append(columnName);
			sql.append(" = ? ", tables.getValue(columnName, this));
		}
		return (T) db.queryOne(this.getClass(), sql);
	}

	/**
	 * 根据指定字段条件查询当前实体信息
	 * @param db
	 * @param requires
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default T queryOne(CFDB db) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		CFSql sql = getBasiscQuerySql(tables);
		return (T) db.queryOne(this.getClass(), sql);
	}

	/**
	 * 根据指定字段条件查询当前实体信息
	 * @param db
	 * @param requires
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default T queryOne(CFDB db, String... requires) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		CFSql sql = getBasiscQuerySql(tables);
		for (String columnName : requires) {
			sql.append(" and ").append(columnName);
			sql.append(" = ? ", tables.getValue(columnName, this));
		}
		return (T) db.queryOne(this.getClass(), sql);
	}

	/**
	 * 根据Sql中添加的条件查询实体信息
	 * @param db
	 * @param requires 在sql中添加查询条件 如： sql.append(" and s_id = ?", sid);
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default T queryOne(CFDB db, CfDBRequires requires) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		CFSql sql = getBasiscQuerySql(tables);
		requires.requires(sql);
		return (T) db.queryOne(this.getClass(), sql);
	}

	/**
	 * 根据指定条件字段查询当前实体列表
	 * @param db
	 * @param requires
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default List<T> query(CFDB db) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		CFSql sql = getBasiscQuerySql(tables);
		return (List<T>) db.query(this.getClass(), sql);
	}

	/**
	 * 根据指定条件字段查询当前实体列表
	 * @param db
	 * @param requires
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default List<T> query(CFDB db, String... requires) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		CFSql sql = getBasiscQuerySql(tables);
		for (String columnName : requires) {
			sql.append(" and ").append(columnName);
			sql.append(" = ? ", tables.getValue(columnName, this));
		}
		return (List<T>) db.query(this.getClass(), sql);
	}

	/**
	 * 根据Sql添加的条件查询实体列表
	 * @param db
	 * @param requires 在sql中添加查询条件 如： sql.append(" and s_id = ?", sid);
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default List<T> query(CFDB db, CfDBRequires requires) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		CFSql sql = getBasiscQuerySql(tables);
		requires.requires(sql);
		return (List<T>) db.query(this.getClass(), sql);
	}

	/**
	 * 根据指定条件字段分页查询当前实体列表
	 * @param paging
	 * @param db
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default List<T> query(CFPaging paging, CFDB db) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		CFSql sql = getBasiscQuerySql(tables);
		return (List<T>) db.query(paging, this.getClass(), sql);
	}

	/**
	 * 根据指定条件字段分页查询当前实体列表
	 * @param paging
	 * @param db
	 * @param requires
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default List<T> query(CFPaging paging, CFDB db, String... requires) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		CFSql sql = getBasiscQuerySql(tables);
		for (String columnName : requires) {
			sql.append(" and ").append(columnName);
			sql.append(" = ? ", tables.getValue(columnName, this));
		}
		return (List<T>) db.query(paging, this.getClass(), sql);
	}

	/**
	 * 根据Sql添加的条件分页查询实体列表
	 * @param paging
	 * @param db
	 * @param requires 在sql中添加查询条件 如： sql.append(" and s_id = ?", sid);
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default List<T> query(CFPaging paging, CFDB db, CfDBRequires requires) {
		CFDBTables tables = CFDBMapping.getTables(db, this.getClass());
		CFSql sql = getBasiscQuerySql(tables);
		requires.requires(sql);
		return (List<T>) db.query(paging, this.getClass(), sql);
	}

	/**
	 * 根据字段集合，获取这些字段的所有对应该实体的属性值
	 * @param columns
	 * @return
	 */
	default List<Object> getParamsByColumns(CFDBTables tables, String... columns) {
		List<Object> params = new ArrayList<>();
		for (String columnName : columns) {
			params.add(tables.getValue(columnName, this));
		}
		return params;
	}

	/**
	 * 根据字段集合，获取这些字段的所有对应该实体的属性值
	 * @param columns
	 * @return
	 */
	default List<Object> getParamsByColumns(CFDBTables tables, Set<String> columns) {
		List<Object> params = new ArrayList<>();
		for (String columnName : columns) {
			params.add(tables.getValue(columnName, this));
		}
		return params;
	}

	/**
	 * 根据字段集合，获取这些字段的所有对应该实体的属性值
	 * @param columns
	 * @return
	 */
	default List<Object> getParamsByColumns(CFDBTables tables, List<String> columns) {
		List<Object> params = new ArrayList<>();
		for (String columnName : columns) {
			params.add(tables.getValue(columnName, this));
		}
		return params;
	}

	/**
	 * 获取基础查询sql
	 * @param tables
	 * @return
	 */
	default CFSql getBasiscQuerySql(CFDBTables tables) {
		Set<String> currentColumns = tables.currentColumnKeySet();
		String[] columnNameList = new String[currentColumns.size()];
		int index = 0;
		for (String columnName : tables.currentColumnKeySet()) {
			StringBuilder builder = new StringBuilder(columnName);
			builder.append(" as `").append(tables.getLable(columnName));
			columnNameList[index] = builder.append("`").toString();
			index++;
		}
		return CFSql.createQuery(tables.getTableName(), columnNameList).appendWhereTrue();
	}
}
