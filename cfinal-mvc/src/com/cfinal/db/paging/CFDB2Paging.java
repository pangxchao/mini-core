/**
 * Created the com.cfinal.db.paging.CFDB2Paging.java
 * @created 2017年2月23日 下午1:09:26
 * @version 1.0.0
 */
package com.cfinal.db.paging;

/**
 * db2 分页实现
 * @author XChao
 */
public final class CFDB2Paging extends CFPaging {
	public CFDB2Paging(int page, int rows) {
		super(page, rows);
	}

	@Override
	public String paging(String sql) {
		return new StringBuilder(sql).append(" limit ").append(this.getStart()).append(", ").append(this.getRows())
			.toString();
	}
}
