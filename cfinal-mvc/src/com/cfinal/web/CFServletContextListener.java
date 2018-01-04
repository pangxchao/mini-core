/**
 * Created the com.cfinal.web.core.CFServletContextListener.java
 * @created 2017年8月21日 下午1:54:33
 * @version 1.0.0
 */
package com.cfinal.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cfinal.util.logger.CFLog;

/**
 * com.cfinal.web.core.CFServletContextListener.java
 * @author XChao
 */
public abstract class CFServletContextListener implements CFService, //
	ServletContextListener {

	public final void onStartup(CFServletContext context) throws Exception {
		try {
			context.addBean(this);
			context.addListener(this);
			CFLog.debug("Scanner CFServletContextListener： " + this.getClass().getName());
		} catch (Throwable throwable) {
			CFLog.error(this.getClass().getName() + ".onStartup error. ", throwable);
		}
	}

	@Override
	public abstract void contextInitialized(ServletContextEvent event);
	
	@Override
	public abstract void contextDestroyed(ServletContextEvent event);

	
	
}
