/**
 * Created the com.cfinal.web.preprocessor.CFDBPreprocessor.java
 * @created 2016年10月13日 上午11:25:00
 * @version 1.0.0
 */
package com.cfinal.web.http.listener;

import javax.servlet.ServletContextEvent;

import com.cfinal.db.CFDB;
import com.cfinal.db.CFDBFactory;
import com.cfinal.web.CFServletContextListener;

/**
 * 带有默认数据库连接的预处理执行器
 * @author XChao
 */
public abstract class CFDBServletContextListener extends CFServletContextListener {

	protected CFDB getDB(String name) {
		return CFDBFactory.create(name);
	}

	public abstract void dbContextInitialized(ServletContextEvent event);

	@Override
	public final void contextInitialized(ServletContextEvent event) {
		try {
			this.dbContextInitialized(event);
		} finally {
			for (String name : CFDBFactory.getThreadDB().keySet()) {
				CFDBFactory.close(CFDBFactory.getThreadDB().get(name));
			}
		}
	}

	public void contextDestroyed(ServletContextEvent arg0) {

	}

}
