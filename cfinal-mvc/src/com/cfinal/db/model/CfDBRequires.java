/**
 * Created the com.cfinal.db.model.CfDBRequires.java
 * @created 2017年4月26日 下午5:22:44
 * @version 1.0.0
 */
package com.cfinal.db.model;

import com.cfinal.db.CFSql;

/**
 * com.cfinal.db.model.CfDBRequires.java
 * @author XChao
 */
@FunctionalInterface
public interface CfDBRequires {
	/**
	 * 在sql中添加查询条件 如： sql.append(" and s_id = ?", sid);
	 * @param sql
	 */
	public void requires(CFSql sql);
}
