/**
 * Created the com.cfinal.db.CFSql.java
 * @created 2016年9月22日 下午3:28:42
 * @version 1.0.0
 */
package com.cfinal.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cfinal.db.mapper.cell.CFCell;

/**
 * SQL 拼装类
 * @author PangChao
 */
public class CFSql {
	// insert SQL 与replace SQL 传入字段时, 将其转换成value位置中参数的对应关系
	private static final Map<String, String> insert_values = new HashMap<>();
	// update SQL 传入字段时, 将其转换成 set 位置中参数的对应关系
	private static final Map<String, String> update_values = new HashMap<>();
	// update SQL 与 delete SQL 传入字段时,将其生成 where 位置中的参数对应关系, 默认用 and连接
	private static final Map<String, String> wheres_values = new HashMap<>();

	/**
	 * 根据字段，获取insert/replace语句的 values(?)语句中? 部分sql
	 * @param keys
	 * @return
	 */
	public static String getInsertParam(String keys) {
		String result = insert_values.get(keys);
		if(StringUtils.isBlank(result)) {
			result = keys.replaceAll("[^,^ ]+", "?");
			insert_values.put(keys, result);
		}
		return result;
	}

	/**
	 * 根据字段,获取update kye = ? 部分Sql
	 * @param keys
	 * @return
	 */
	public static String getUpdateParam(String keys) {
		String result = update_values.get(keys);
		if(StringUtils.isBlank(result)) {
			result = keys.replaceAll("[, ]+", " = ?, ") + " = ? ";
			update_values.put(keys, result);
		}
		return result;
	}

	/**
	 * 根据字段，获取where key = ? 中 key = ? 部分代码
	 * @param keys
	 * @return
	 */
	public static String getWheresParam(String keys) {
		String result = wheres_values.get(keys);
		if(StringUtils.isBlank(result)) {
			result = keys.replaceAll("[, ]+", " = ? and ") + " = ? ";
			wheres_values.put(keys, result);
		}
		return result;
	}

	private StringBuilder sqls = new StringBuilder();
	private List<Object> params = new ArrayList<>();
	private Map<String, CFCell<?>> cells = new HashMap<String, CFCell<?>>();

	public CFSql() {
	}

	/**
	 * 创建一个 select 语句
	 * @param keys 查询结果集字段
	 * @return
	 */
	public static CFSql createQuery(String tableName, String... keys) {
		if(keys == null || keys.length == 0) {
			throw new CFDBException("The Sql createQuery keys can not empty. ");
		}
		return new CFSql("select ").append(StringUtils.join(keys, ", ")).append(" from ").append(tableName).append(" ");
	}

	/**
	 * 创建一个insert into语句
	 * @param tableName
	 * @param keys
	 * @return
	 */
	public static CFSql createInsert(String tableName, String... keys) {
		String keysStr = StringUtils.join(keys, ", ");
		return new CFSql("insert into ").append(tableName).append("(").append(keysStr).append(") values(")
			.append(getInsertParam(keysStr)).append(")");
	}

	/**
	 * 创建一个replace into语句
	 * @param tableName
	 * @param keys
	 * @return
	 */
	public static CFSql createReplace(String tableName, String... keys) {
		String keysStr = StringUtils.join(keys, ", ");
		return new CFSql("replace into ").append(tableName).append("(").append(keysStr).append(") values(")
			.append(getInsertParam(keysStr)).append(")");
	}

	/**
	 * 创建一个 update 语句<br/>
	 * 该方法创建语句时，如果需要添加where条件时，请选调用appendWhereTrue方法
	 * @param tableName
	 * @param keys 如果keys为空时， 返回 update tableName set
	 * @return
	 */
	public static CFSql createUpdate(String tableName, String... keys) {
		if(keys == null || keys.length == 0) {
			return new CFSql("update ").append(tableName).append(" set ");
		}
		String keysStr = StringUtils.join(keys, ", ");
		return new CFSql("update ").append(tableName).append(" set ") //
			.append(getUpdateParam(keysStr)).append(" ");
	}

	/**
	 * 创建一个delete 语句<br/>
	 * 该方法创建语句时，如果需要添加where条件时，请选调用appendWhereTrue方法
	 * @param tableName
	 * @return
	 */
	public static CFSql createDelete(String tableName) {
		return new CFSql("delete from ").append(tableName).append(" ");
	}

	/**
	 * 创建sql语句
	 * @param sql sql语句字符串
	 * @param params sql 语句中带?号参数的值列表
	 */
	public CFSql(String sql, Object... params) {
		this.append(sql, params);
	}

	/**
	 * 获取该sql的完整sql字符串形式
	 */
	public String getSQL() {
		return this.sqls.toString();
	}

	/**
	 * 获取该sql中所有带?号参数的值列表数组
	 */
	public Object[] getParams() {
		return this.params.toArray();
	}

	public Map<String, CFCell<?>> getCells() {
		return this.cells;
	}

	public CFSql append(String sql) {
		this.sqls.append(sql);
		return this;
	}

	/**
	 * 追加sql 语句
	 * @param sql sql语句字符串
	 * @param params 语句中带?号参数的值列表
	 */
	public CFSql append(String sql, Object... params) {
		this.sqls.append(sql);
		if(params != null) {
			for (Object param : params) {
				this.params.add(param);
			}
		}
		return this;
	}

	/**
	 * 向Sql中追加参数
	 * @param params
	 * @return
	 */
	public CFSql appendParams(Object... params) {
		if(params != null) {
			for (Object param : params) {
				this.params.add(param);
			}
		}
		return this;
	}

	/**
	 * 追加join联合表语句，<br/>
	 * 该方法不能同时追加参数
	 * @param tableName 表名
	 * @param column1 连接表前表字段
	 * @param column2 连接表当前表字段
	 * @return
	 */
	public CFSql appendJoin(String tableName, String column1, String column2) {
		this.sqls.append(" join ").append(tableName).append(" on ");
		this.sqls.append(column1).append(" = ").append(column2).append(" ");
		return this;
	}

	/**
	 * 追加left join联合表语句，<br/>
	 * 该方法不能同时追加参数
	 * @param tableName 表名
	 * @param column1 连接表前表字段
	 * @param column2 连接表当前表字段
	 * @return
	 */
	public CFSql appendLeftJoin(String tableName, String column1, String column2) {
		this.sqls.append(" left join ").append(tableName).append(" on ");
		this.sqls.append(column1).append(" = ").append(column2).append(" ");
		return this;
	}

	/**
	 * 追加right join联合表语句，<br/>
	 * 该方法不能同时追加参数
	 * @param tableName 表名
	 * @param column1 连接表前表字段
	 * @param column2 连接表当前表字段
	 * @return
	 */
	public CFSql appendRigthtJoin(String tableName, String column1, String column2) {
		this.sqls.append(" right join ").append(tableName).append(" on ");
		this.sqls.append(column1).append(" = ").append(column2).append(" ");
		return this;
	}

	/**
	 * 追加cross join联合表语句，<br/>
	 * 该方法不能同时追加参数
	 * @param tableName 表名
	 * @param column1 连接表前表字段
	 * @param column2 连接表当前表字段
	 * @return
	 */
	public CFSql appendCrossJoin(String tableName, String column1, String column2) {
		this.sqls.append(" cross join ").append(tableName).append(" ");
		return this;
	}

	/**
	 * 向Sql中追加 where 1 = 1 语句块
	 * @return
	 */
	public CFSql appendWhereTrue() {
		return this.append(" where 1 = 1 ");
	}

	/**
	 * 向Sql中追加 where 1 = 2 语句块
	 * @return
	 */
	public CFSql appendWhereFalse() {
		return this.append(" where 1 = 2 ");
	}

	/**
	 * 追加Sql where 等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereEqInAnd(String key, String param) {
		return this.append(" and ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 and 连接 <br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereEqInAnd(String key, String param) {
		if(StringUtils.isBlank(param)) {
			return this;
		}
		return this.append(" and ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereEqInAnd(String key, int param) {
		return this.append(" and ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 and 连接 <br/>
	 * 如果传入参数 小于0 时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereEqInAnd(String key, int param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereEqInAnd(String key, long param) {
		return this.append(" and ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 and 连接 <br/>
	 * 如果传入参数 小于0 时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereEqInAnd(String key, long param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereEqInAnd(String key, float param) {
		return this.append(" and ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 and 连接 <br/>
	 * 如果传入参数 小于0 时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereEqInAnd(String key, float param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereEqInAnd(String key, double param) {
		return this.append(" and ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 and 连接 <br/>
	 * 如果传入参数 小于0 时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereEqInAnd(String key, double param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereEqInAnd(String key, Date param) {
		return this.append(" and ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 and 连接 <br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereEqInAnd(String key, Date param) {
		if(param == null) {
			return this;
		}
		return this.append(" and ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereEqInOr(String key, String param) {
		return this.append(" or ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 or 连接 <br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereEqInOr(String key, String param) {
		if(StringUtils.isBlank(param)) {
			return this;
		}
		return this.append(" or ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereEqInOr(String key, int param) {
		return this.append(" or ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 or 连接 <br/>
	 * 如果传入参数小于0 时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereEqInOr(String key, int param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereEqInOr(String key, long param) {
		return this.append(" or ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 or 连接 <br/>
	 * 如果传入参数小于0 时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereEqInOr(String key, long param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereEqInOr(String key, float param) {
		return this.append(" or ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 or 连接 <br/>
	 * 如果传入参数小于0 时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereEqInOr(String key, float param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereEqInOr(String key, double param) {
		return this.append(" or ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 or 连接 <br/>
	 * 如果传入参数小于0 时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereEqInOr(String key, double param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereEqInOr(String key, Date param) {
		return this.append(" or ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 等于 条件， 用 or 连接 <br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereEqInOr(String key, Date param) {
		if(param == null) {
			return this;
		}
		return this.append(" or ").append(key).append(" = ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtInAnd(String key, String param) {
		return this.append(" and ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 and 连接<br />
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtInAnd(String key, String param) {
		if(StringUtils.isBlank(param)) {
			return this;
		}
		return this.append(" and ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtInAnd(String key, int param) {
		return this.append(" and ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 and 连接<br />
	 * 如果传入参数小于0时，不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtInAnd(String key, int param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtInAnd(String key, long param) {
		return this.append(" and ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 and 连接<br />
	 * 如果传入参数小于0时，不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtInAnd(String key, long param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtInAnd(String key, float param) {
		return this.append(" and ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 and 连接<br />
	 * 如果传入参数小于0时，不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtInAnd(String key, float param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtInAnd(String key, double param) {
		return this.append(" and ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 and 连接<br />
	 * 如果传入参数小于0时，不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtInAnd(String key, double param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtInAnd(String key, Date param) {
		return this.append(" and ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 and 连接<br />
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtInAnd(String key, Date param) {
		if(param == null) {
			return this;
		}
		return this.append(" and ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtInOr(String key, String param) {
		return this.append(" or ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtInOr(String key, String param) {
		if(StringUtils.isBlank(param)) {
			return null;
		}
		return this.append(" or ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtInOr(String key, int param) {
		return this.append(" or ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtInOr(String key, int param) {
		if(param <= 0) {
			return null;
		}
		return this.append(" or ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtInOr(String key, long param) {
		return this.append(" or ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtInOr(String key, long param) {
		if(param <= 0) {
			return null;
		}
		return this.append(" or ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtInOr(String key, float param) {
		return this.append(" or ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtInOr(String key, float param) {
		if(param <= 0) {
			return null;
		}
		return this.append(" or ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtInOr(String key, double param) {
		return this.append(" or ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtInOr(String key, double param) {
		if(param <= 0) {
			return null;
		}
		return this.append(" or ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtInOr(String key, Date param) {
		return this.append(" or ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtInOr(String key, Date param) {
		if(param == null) {
			return null;
		}
		return this.append(" or ").append(key).append(" > ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtEqInAnd(String key, String param) {
		return this.append(" and ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtEqInAnd(String key, String param) {
		if(StringUtils.isBlank(param)) {
			return null;
		}
		return this.append(" and ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtEqInAnd(String key, int param) {
		return this.append(" and ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtEqInAnd(String key, int param) {
		if(param <= 0) {
			return null;
		}
		return this.append(" and ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtEqInAnd(String key, long param) {
		return this.append(" and ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtEqInAnd(String key, long param) {
		if(param <= 0) {
			return null;
		}
		return this.append(" and ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtEqInAnd(String key, float param) {
		return this.append(" and ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtEqInAnd(String key, float param) {
		if(param <= 0) {
			return null;
		}
		return this.append(" and ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtEqInAnd(String key, double param) {
		return this.append(" and ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtEqInAnd(String key, double param) {
		if(param <= 0) {
			return null;
		}
		return this.append(" and ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtEqInAnd(String key, Date param) {
		return this.append(" and ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于 条件， 用 or 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtEqInAnd(String key, Date param) {
		if(param == null) {
			return null;
		}
		return this.append(" and ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtEqInOr(String key, String param) {
		return this.append(" or ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 or 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtEqInOr(String key, String param) {
		if(StringUtils.isBlank(param)) {
			return this;
		}
		return this.append(" or ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtEqInOr(String key, int param) {
		return this.append(" or ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtEqInOr(String key, int param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtEqInOr(String key, long param) {
		return this.append(" or ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtEqInOr(String key, long param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtEqInOr(String key, float param) {
		return this.append(" or ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtEqInOr(String key, float param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtEqInOr(String key, double param) {
		return this.append(" or ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtEqInOr(String key, double param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereGtEqInOr(String key, Date param) {
		return this.append(" or ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 大于等于 条件， 用 or 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereGtEqInOr(String key, Date param) {
		if(param == null) {
			return this;
		}
		return this.append(" or ").append(key).append(" >= ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtInAnd(String key, String param) {
		return this.append(" and ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 and 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtInAnd(String key, String param) {
		if(StringUtils.isBlank(param)) {
			return this;
		}
		return this.append(" and ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtInAnd(String key, int param) {
		return this.append(" and ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 and 连接<br/>
	 * 如梦传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtInAnd(String key, int param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtInAnd(String key, long param) {
		return this.append(" and ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 and 连接<br/>
	 * 如梦传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtInAnd(String key, long param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtInAnd(String key, float param) {
		return this.append(" and ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 and 连接<br/>
	 * 如梦传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtInAnd(String key, float param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtInAnd(String key, double param) {
		return this.append(" and ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 and 连接<br/>
	 * 如梦传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtInAnd(String key, double param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtInAnd(String key, Date param) {
		return this.append(" and ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 and 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtInAnd(String key, Date param) {
		if(param == null) {
			return this;
		}
		return this.append(" and ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtInOr(String key, String param) {
		return this.append(" or ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 or 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtInOr(String key, String param) {
		if(StringUtils.isBlank(param)) {
			return this;
		}
		return this.append(" or ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtInOr(String key, int param) {
		return this.append(" or ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtInOr(String key, int param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtInOr(String key, long param) {
		return this.append(" or ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtInOr(String key, long param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtInOr(String key, float param) {
		return this.append(" or ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtInOr(String key, float param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtInOr(String key, double param) {
		return this.append(" or ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtInOr(String key, double param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtInOr(String key, Date param) {
		return this.append(" or ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于 条件， 用 or 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtInOr(String key, Date param) {
		if(param == null) {
			return this;
		}
		return this.append(" or ").append(key).append(" < ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtEqInAnd(String key, String param) {
		return this.append(" and ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 and 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtEqInAnd(String key, String param) {
		if(StringUtils.isBlank(param)) {
			return this;
		}
		return this.append(" and ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtEqInAnd(String key, int param) {
		return this.append(" and ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 and 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtEqInAnd(String key, int param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtEqInAnd(String key, long param) {
		return this.append(" and ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 and 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtEqInAnd(String key, long param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtEqInAnd(String key, float param) {
		return this.append(" and ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 and 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtEqInAnd(String key, float param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtEqInAnd(String key, double param) {
		return this.append(" and ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 and 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtEqInAnd(String key, double param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" and ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtEqInAnd(String key, Date param) {
		return this.append(" and ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 and 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtEqInAnd(String key, Date param) {
		if(param == null) {
			return this;
		}
		return this.append(" and ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtEqInOr(String key, String param) {
		return this.append(" or ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 or 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtEqInOr(String key, String param) {
		if(StringUtils.isBlank(param)) {
			return this;
		}
		return this.append(" or ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtEqInOr(String key, int param) {
		return this.append(" or ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtEqInOr(String key, int param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtEqInOr(String key, long param) {
		return this.append(" or ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtEqInOr(String key, long param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtEqInOr(String key, float param) {
		return this.append(" or ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtEqInOr(String key, float param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtEqInOr(String key, double param) {
		return this.append(" or ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 or 连接<br/>
	 * 如果传入参数小于0时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtEqInOr(String key, double param) {
		if(param <= 0) {
			return this;
		}
		return this.append(" or ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLtEqInOr(String key, Date param) {
		return this.append(" or ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 and 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLtEqInOr(String key, Date param) {
		if(param == null) {
			return this;
		}
		return this.append(" or ").append(key).append(" <= ? ", param);
	}

	/**
	 * 追加Sql where like 条件， 用 and 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLikeInAnd(String key, String param) {
		return this.append(" and ").append(key).append(" like ? ", param);
	}

	/**
	 * 追加Sql where like 条件， 用 and 连接, 默认后%匹配
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLikeInAnd(String key, String param) {
		if(StringUtils.isBlank(param)) {
			return this;
		}
		return this.append(" and ").append(key).append(" like ? ", param + "%");
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 or 连接
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereLikeInOr(String key, String param) {
		return this.append(" or ").append(key).append(" like ? ", param);
	}

	/**
	 * 追加Sql where like 条件， 用 or 连接<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereLikeInOr(String key, String param) {
		if(StringUtils.isBlank(param)) {
			return this;
		}
		return this.append(" or ").append(key).append(" like ? ", param + "%");
	}

	/**
	 * 追加Sql where Match 条件， 用 and 连接<br/>
	 * 全文索引搜索条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereMatchInAnd(String keys, String param) {
		return this.append(" and MATCH(").append(keys).append(") AGAINST(? IN BOOLEAN MODE) ", param);
	}

	/**
	 * 追加Sql where Match 条件， 用 and 连接<br/>
	 * 全文索引搜索条件<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereMatchInAnd(String keys, String param) {
		if(StringUtils.isBlank(param)) {
			return this;
		}
		return this.append(" and MATCH(").append(keys).append(") AGAINST(? IN BOOLEAN MODE) ", param);
	}

	/**
	 * 追加Sql where 小于等于 条件， 用 or 连接<br/>
	 * 全文索引搜索条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql appendWhereMatchInOr(String keys, String param) {
		return this.append(" or MATCH(").append(keys).append(") AGAINST(? IN BOOLEAN MODE) ", param);
	}

	/**
	 * 追加Sql where Match 条件， 用 or 连接<br/>
	 * 全文索引搜索条件<br/>
	 * 如果传入参数为空时， 不添加sql条件
	 * @param key
	 * @param params
	 * @return
	 */
	public CFSql valiAppendWhereMatchInOr(String keys, String param) {
		if(StringUtils.isBlank(param)) {
			return this;
		}
		return this.append(" or MATCH(").append(keys).append(") AGAINST(? IN BOOLEAN MODE) ", param);
	}

	/**
	 * 向SQL中追加group by语句
	 * @param keys
	 * @return
	 */
	public CFSql appendGroupBy(String... keys) {
		if(keys != null) {
			this.sqls.append(" group by ").append(StringUtils.join(keys, ", "));
		}
		return this;
	}

	/**
	 * 向SQL中追加group by语句
	 * @param keys
	 * @return
	 */
	public CFSql appendOrderBy(String... keys) {
		if(keys != null) {
			this.sqls.append(" order by ").append(StringUtils.join(keys, ", "));
		}
		return this;
	}

	/**
	 * 添加一个cell实现
	 * @param key
	 * @param cell
	 * @return
	 */
	public CFSql addCell(String key, CFCell<?> cell) {
		this.cells.put(key, cell);
		return this;
	}

	/**
	 * 将传入字段用逗号连接起来
	 * @param kyes
	 * @return
	 */
	public static String join(String... kyes) {
		return StringUtils.join(kyes, ", ");
	}
}
