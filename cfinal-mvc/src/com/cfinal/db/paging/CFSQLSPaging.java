/**
 * Created the com.cfinal.db.paging.CFSQLSPaging.java
 * @created 2017年2月23日 下午1:26:34
 * @version 1.0.0
 */
package com.cfinal.db.paging;

/**
 * com.cfinal.db.paging.CFSQLSPaging.java
 * @author XChao
 */
public final class CFSQLSPaging extends CFPaging {

	public CFSQLSPaging(int page, int rows) {
		super(page, rows);
	}

	@Override
	public String paging(String sql) {
		// select top (startRow + rowNmu) from tableName ;
//		return new StringBuilder(sql).append(" limit ")
		// .append(this.getStart()).append(", ").append(this.getPage()).toString();
		return null;
	}
}
