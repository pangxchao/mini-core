/**
 * Created the sn.mini.dao.Paging.java
 * @created 2017年2月23日 下午12:46:22
 * @version 1.0.0
 */
package sn.mini.java.jdbc;

/**
 * sn.mini.dao.Paging.java
 * @author XChao
 */
public class Paging {

	// 当前页码数 ((page - 1) * rows = start)
	protected int page = 1;
	// 每页条数 类似limit ?, rows 是的rows数
	protected int rows = 10;
	// 总条数
	protected int total = 0;
	// SQL 查询的开始条数： 类似limit start, ? 是的start数
	protected int start = 0;
	// 总页数 最大页码数
	protected int maxpage = 0;

	/**
	 * @param page 当前页码
	 * @param rows 每页条数
	 */
	public Paging(int page, int rows) {
		if(page < 1) {
			page = 1;
		}
		if(rows < 1) {
			rows = 1;
		}
		this.page = page;
		this.rows = rows;
		this.start = (this.page - 1) * rows;
	}

	/**
	 * 获取当前页码数
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 获取每面条数
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * 获取最大条数
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 设置 最大条数
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
		int totalPages = this.total / this.rows;
		if(this.total % this.rows > 0) {
			totalPages = totalPages + 1;
		}
		this.maxpage = totalPages;
		if(this.maxpage > 0 && this.page > this.maxpage) {
			this.page = this.maxpage;
		}
		this.start = (this.page - 1) * this.rows;
	}

	/**
	 * 获取 limit ?, 10中?代表的值
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * 最大页码数
	 * @return the maxpage
	 */
	public int getMaxpage() {
		return maxpage;
	}
}
