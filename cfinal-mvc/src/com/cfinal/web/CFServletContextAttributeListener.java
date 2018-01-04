/**
 * Created the com.cfinal.web.CFServletContextAttributeListener.java
 * @created 2017年8月24日 下午2:57:00
 * @version 1.0.0
 */
package com.cfinal.web;

import javax.servlet.ServletContextAttributeListener;

import com.cfinal.util.logger.CFLog;

/**
 * com.cfinal.web.CFServletContextAttributeListener.java
 * @author XChao
 */
public abstract class CFServletContextAttributeListener implements CFService, //
	ServletContextAttributeListener {

	public void onStartup(CFServletContext context) throws Exception {
		try {
			context.addBean(this);
			context.addListener(this);
			CFLog.debug("Scanner CFServletContextAttributeListener： " + this.getClass().getName());
		} catch (Throwable throwable) {
			CFLog.error(this.getClass().getName() + ".onStartup error. ", throwable);
		}
	}

}
