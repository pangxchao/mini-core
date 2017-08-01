/**
 * Created the com.cfinal.db.paging.CFMPaging.java
 * @created 2017年2月23日 下午12:49:46
 * @version 1.0.0
 */
package com.cfinal.db.paging;

/**
 * Mysql 分页实现
 * @author XChao
 */
public final class CFMPaging extends CFPaging {

	public CFMPaging(int page, int rows) {
		super(page, rows);
	}

	@Override
	public String paging(String sql) {
		return new StringBuilder(sql).append(" limit ").append(this.getStart()).append(", ").append(this.getRows()).toString();
	}

}
