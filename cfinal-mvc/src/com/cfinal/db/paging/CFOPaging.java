/**
 * Created the com.cfinal.db.paging.CFOPaging.java
 * @created 2017年2月23日 下午12:53:47
 * @version 1.0.0
 */
package com.cfinal.db.paging;

/**
 * oracle 分页实现
 * @author XChao
 */
public final class CFOPaging extends CFPaging {

	public CFOPaging(int page, int rows) {
		super(page, rows);
	}

	@Override
	public String paging(String sql) {
		// select * from (select rownum rm, * from tableName where rownum<(startRow+rowNum)) where rm >= startRow;
		StringBuilder builder = new StringBuilder("select * from ( ");
		builder.append("select oracle_max_count_rownum.*, rownum oracle_max_count_rownum from ( ");
		builder.append(sql).append(") oracle_max_count_sql_table where rownum <= ").append(this.getStart() + this.getRows());
		builder.append(" ").append(") pages_t1 where oracle_max_count_rownum > ");
		return builder.append(this.getStart()).toString();
	}

}
