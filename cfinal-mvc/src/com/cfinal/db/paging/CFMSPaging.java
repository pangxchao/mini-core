/**
 * Created the com.cfinal.db.paging.CFMSPaging.java
 * @created 2017年2月23日 下午1:28:09
 * @version 1.0.0
 */
package com.cfinal.db.paging;

/**
 * com.cfinal.db.paging.MSPaging.java
 * @author XChao
 */
public final class CFMSPaging extends CFPaging {

	public CFMSPaging(int page, int rows) {
		super(page, rows);
	}

	@Override
	public String paging(String sql) {
//		//
//		return new StringBuilder(sql).append(" limit ").append(this.getStart())
//				.append(", ").append(this.getPage()).toString();
		return null;
	}
}
