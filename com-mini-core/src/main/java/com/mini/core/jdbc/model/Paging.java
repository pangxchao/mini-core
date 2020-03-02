package com.mini.core.jdbc.model;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.util.Collections.addAll;

public final class Paging<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private final List<T> rows = new ArrayList<>();
	private int page, limit, total;

	/**
	 * 指定页码数和每页条数初始化
	 * @param page  页码数
	 * @param limit 每页条数
	 */
	public Paging(int page, int limit) {
		this.limit = limit;
		this.page = page;
	}

	/**
	 * 设置总条数
	 * @param total 总条数
	 * @return 当前对象
	 */
	public final Paging setTotal(int total) {
		this.total = total;
		return this;
	}

	/**
	 * 获取页码数
	 * @return 页码数
	 */
	public final int getPage() {
		return max(1, page);
	}

	/**
	 * 获取每页条数
	 * @return 每页条数
	 */
	public final int getLimit() {
		return max(1, limit);
	}

	/**
	 * 获取总条数
	 * @return 总条数
	 */
	public final int getTotal() {
		return max(0, total);
	}

	/**
	 * 获取跳过的条数
	 * @return 跳过的条数
	 */
	public final int getStart() {
		int p = getPage() - 1;
		return p * limit;
	}

	/**
	 * 设置分页数据
	 * @param rows 分页数据
	 */
	public final void addRows(List<T> rows) {
		this.rows.addAll(rows);
	}

	/**
	 * 设置分页数据
	 * @param rows 分页数据
	 */
	@SafeVarargs
	public final void addRows(T... rows) {
		addAll(this.rows, rows);
	}

	/**
	 * 清空所有数据
	 * @see List#clear()
	 */
	public final void clearRows() {
		this.rows.clear();
	}

	/**
	 * 获取分页数据
	 * @return 数据列表
	 */
	@Nonnull
	public final List<T> getRows() {
		return this.rows;
	}
}
