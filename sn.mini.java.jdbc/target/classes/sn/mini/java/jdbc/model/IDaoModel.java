/**
 * Created the sn.mini.dao.model.IDaoModel.java
 * @created 2017年11月2日 下午3:42:41
 * @version 1.0.0
 */
package sn.mini.java.jdbc.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sn.mini.java.jdbc.DBException;
import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.Sql;

/**
 * sn.mini.dao.model.IDaoModel.java
 * @author XChao
 */
public interface IDaoModel<T extends IDaoModel<T>> extends Serializable {

	@SuppressWarnings("unchecked")
	default DaoTable getDaoTable() {
		return ModelMapping.getDaoTable((Class<? extends IDaoModel<?>>) this.getClass());
	}

	default IDaoModel<T> set(String name, Object value) {
		try {
			this.getDaoTable().setValue(name, this, value);
			return this;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	default Object get(String name) {
		try {
			return this.getDaoTable().getValue(name, this);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 保存当前实体信息到数据库，并返回影响条数
	 * @param db
	 * @return @
	 */
	default int insert(IDao dao) {
		return this.insert(dao, getDaoTable().currentEntrySet().stream()//
			.map(v -> v.getValue()).toArray(v -> new String[v]));
	}

	/**
	 * 保存当前实体指定字段信息到数据库，并返回影响条数
	 * @param db
	 * @param columns
	 * @return @
	 */
	default int insert(IDao dao, String... columns) {
		return dao.insert(this.getDaoTable().getDbName(), columns, this.getParamsByColumns(columns).toArray());
	}

	/**
	 * 保存当前实体信息到数据库， 并返回数据库自增长ID
	 * @param db
	 * @return @
	 */
	default long insertGen(IDao dao) {
		return this.insertGen(dao, getDaoTable().currentEntrySet().stream()//
			.map(v -> v.getValue()).toArray(v -> new String[v]));
	}

	/**
	 * 保存当前实体信息指定字段到数据库，并返回数据库自增长ID值
	 * @param db
	 * @param columns
	 * @return @
	 */
	default long insertGen(IDao dao, String... columns) {
		return dao.insertGen(this.getDaoTable().getDbName(), columns, this.getParamsByColumns(columns).toArray());
	}

	/**
	 * 保存当前实体信息到数据库，并返回影响条数<br/>
	 * 如果记录重复时，则覆盖之前的记录
	 * @param db
	 * @return @
	 */
	default int replace(IDao dao) {
		return this.replace(dao, getDaoTable().currentEntrySet().stream()//
			.map(v -> v.getValue()).toArray(v -> new String[v]));
	}

	/**
	 * 保存当前实体指定字段信息到数据库，并返回影响条数<br/>
	 * 如果记录重复时，则覆盖之前的记录
	 * @param db
	 * @param columns
	 * @return @
	 */
	default int replace(IDao dao, String... columns) {
		return dao.replace(this.getDaoTable().getDbName(), columns, this.getParamsByColumns(columns).toArray());
	}

	/**
	 * 保存当前实体信息到数据库， 并返回数据库自增长ID<br/>
	 * 如果记录重复时，则覆盖之前的记录
	 * @param db
	 * @return @
	 */
	default long replaceGen(IDao dao) {
		return this.replaceGen(dao, getDaoTable().currentEntrySet().stream()//
			.map(v -> v.getValue()).toArray(v -> new String[v]));
	}

	/**
	 * 保存当前实体信息指定字段到数据库，并返回数据库自增长ID值<br/>
	 * 如果记录重复时，则覆盖之前的记录
	 * @param db
	 * @param columns
	 * @return @
	 */
	default long replaceGen(IDao dao, String... columns) {
		return dao.replaceGen(this.getDaoTable().getDbName(), columns, this.getParamsByColumns(columns).toArray());
	}

	/**
	 * 根据ID，将当前记录同步到数据库<br/>
	 * 同步时，条件字段不会被修改
	 * @param db
	 * @return @
	 */
	default int update(IDao dao) {
		return update(dao, this.getDaoTable().currentEntrySet().stream()//
			.map(v -> v.getValue()).toArray(v -> new String[v]));
	}

	/**
	 * 根据ID，将当前记录的指定字段同步到数据库
	 * @param db
	 * @param columns
	 * @return @
	 */
	default int update(IDao dao, String... columns) {
		String[] stream = this.getDaoTable().primaryEntrySet().stream().map(v -> v.getValue())
			.toArray(v -> new String[v]);
		List<Object> params = new ArrayList<>(this.getParamsByColumns(columns));
		params.addAll(this.getParamsByColumns(stream));
		return dao.update(this.getDaoTable().getDbName(), columns, stream, params.toArray());
	}

	/**
	 * 根据ID删除数据库当前实体记录
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	default int delete(IDao dao) {
		return this.delete(dao, this.getDaoTable().primaryEntrySet().stream() //
			.map(v -> v.getValue()).toArray(v -> new String[v]));
	}

	/**
	 * 根据指定条件字段，删除数据库当前实体记录
	 * @param db
	 * @param requires
	 * @return @
	 */
	default int delete(IDao dao, String... requires) {
		return dao.delete(this.getDaoTable().getDbName(), requires, this.getParamsByColumns(requires).toArray());
	}

	/**
	 * 根据ID查询当前实体信息
	 * @param db
	 * @return @
	 */
	default T queryById(IDao dao) {
		return this.queryOne(dao, this.getDaoTable().primaryEntrySet().stream() //
			.map(v -> v.getValue()).toArray(v -> new String[v]));
	}

	/**
	 * 根据指定字段条件查询当前实体信息
	 * @param db
	 * @param requires
	 * @return @
	 */
	@SuppressWarnings("unchecked")
	default T queryOne(IDao dao, String... requires) {
		Sql sql = this.getBasiscQuerySql().whereTrue();
		for (String dbName : requires) {
			sql.andEq(dbName).params(this.get(dbName));
		}
		return (T) dao.queryOne(this.getClass(), sql);
	}

	/**
	 * 根据指定条件字段查询当前实体列表
	 * @param db
	 * @param requires
	 * @return @
	 */
	@SuppressWarnings("unchecked")
	default List<T> query(IDao dao) {
		return (List<T>) dao.query(this.getClass(), this.getBasiscQuerySql());
	}

	/**
	 * 根据指定条件字段查询当前实体列表
	 * @param db
	 * @param requires
	 * @return @
	 */
	@SuppressWarnings("unchecked")
	default List<T> query(IDao dao, String... requires) {
		Sql sql = this.getBasiscQuerySql().whereTrue();
		for (String dbName : requires) {
			sql.andEq(dbName).params(this.get(dbName));
		}
		return (List<T>) dao.query(this.getClass(), sql);
	}

	/**
	 * 根据指定条件字段分页查询当前实体列表
	 * @param paging
	 * @param db
	 * @return @
	 */
	@SuppressWarnings("unchecked")
	default List<T> query(Paging paging, IDao dao) {
		return (List<T>) dao.query(paging, this.getClass(), this.getBasiscQuerySql());
	}

	/**
	 * 根据指定条件字段分页查询当前实体列表
	 * @param paging
	 * @param db
	 * @param requires
	 * @return @
	 */
	@SuppressWarnings("unchecked")
	default List<T> query(Paging paging, IDao dao, String... requires) {
		Sql sql = this.getBasiscQuerySql().whereTrue();
		for (String dbName : requires) {
			sql.andEq(dbName).params(this.get(dbName));
		}
		return (List<T>) dao.query(paging, this.getClass(), sql);
	}

	/**
	 * 根据字段集合，获取这些字段的所有对应该实体的属性值
	 * @param columns
	 * @return @
	 */
	default List<Object> getParamsByColumns(String... columns) {
		try {
			List<Object> params = new ArrayList<>();
			for (String columnName : columns) {
				params.add(this.getDaoTable().getValue(columnName, this));
			}
			return params;
		} catch (Exception e) {
			throw new DBException(e.getMessage(), e);
		}
	}

	/**
	 * 获取基础查询sql
	 * @param table
	 * @return
	 */
	default Sql getBasiscQuerySql() {
		return Sql.createSelect(this.getDaoTable().getDbName(), this.getDaoTable().currentEntrySet() //
			.stream().map(v -> v.getValue()).toArray(v -> new String[v]));
	}
}
