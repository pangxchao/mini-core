/**
 * Created the com.cfinal.web.scheduler.CFDBTimmertask.java
 * @created 2017年8月21日 下午5:06:00
 * @version 1.0.0
 */
package com.cfinal.web.timmer;

import com.cfinal.db.CFDB;
import com.cfinal.db.CFDBFactory;
import com.cfinal.util.logger.CFLog;
import com.cfinal.web.CFServletContext;

/**
 * com.cfinal.web.scheduler.CFDBTimmertask.java
 * @author XChao
 */
public abstract class CFDBTimmer extends CFTimmer {

	protected final CFDB getDB(String name) {
		return CFDBFactory.create(name);
	}

	protected final void logger(Throwable e) {
		CFLog.error("定时任务[" + this.getClass().getName() + "]执行失败", e);
	}

	public final void execute(CFServletContext context) {
		try {
			this.dbExecute(context);
		} finally {
			for (String name : CFDBFactory.getThreadDB().keySet()) {
				CFDBFactory.close(CFDBFactory.getThreadDB().get(name));
			}
		}
	}

	public abstract void dbExecute(CFServletContext context);
}
