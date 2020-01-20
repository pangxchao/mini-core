package com.sjhy.plugin.tool;

import com.intellij.database.psi.DbTable;

import java.util.List;

/**
 * 缓存数据工具类
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
public class CacheDataUtils {
	private volatile static CacheDataUtils cacheDataUtils;

	/**
	 * 单例模式
	 */
	public static CacheDataUtils getInstance() {
		if (cacheDataUtils == null) {
			synchronized (CacheDataUtils.class) {
				if (cacheDataUtils == null) {
					cacheDataUtils = new CacheDataUtils();
				}
			}
		}
		return cacheDataUtils;
	}

	private CacheDataUtils() {
	}

	/**
	 * 当前选中的表
	 */
	private DbTable selectDbTable;
	/**
	 * 所有选中的表
	 */
	private List<DbTable> dbTableList;

	public static CacheDataUtils getCacheDataUtils() {
		return cacheDataUtils;
	}

	public static void setCacheDataUtils(CacheDataUtils cacheDataUtils) {
		CacheDataUtils.cacheDataUtils = cacheDataUtils;
	}

	public DbTable getSelectDbTable() {
		return selectDbTable;
	}

	public void setSelectDbTable(DbTable selectDbTable) {
		this.selectDbTable = selectDbTable;
	}

	public List<DbTable> getDbTableList() {
		return dbTableList;
	}

	public void setDbTableList(List<DbTable> dbTableList) {
		this.dbTableList = dbTableList;
	}
}
