/**
 * Created the com.cfinal.web.scheduler.thread.CFDBTask.java
 * @created 2016年10月13日 上午11:05:47
 * @version 1.0.0
 */
package com.cfinal.web.scheduler.thread;

import com.cfinal.db.CFDBFactory;
import com.cfinal.db.CFDB;
import com.cfinal.util.logger.CFLogger;

/**
 * 带有默认数据库连接的定时任务执行器
 * @author XChao
 */
public abstract class CFDBTask implements CFTask {

	protected CFDB getDB(String name) {
		return CFDBFactory.create(name);
	}

	protected void logger(Throwable e) {
		CFLogger.severe("定时任务[" + this.getClass().getName() + "]执行失败", e);
	}

	@Override
	public void execute() {
		try {
			this.dbExecute();
		} finally {
			for (String name : CFDBFactory.getThreadDB().keySet()) {
				CFDBFactory.close(CFDBFactory.getThreadDB().get(name));
			}
		}
	}

	public abstract void dbExecute();

}
