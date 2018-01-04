/**
 * Created the com.cfinal.web.CFServletRequestListener.java
 * @created 2017年8月24日 下午2:50:32
 * @version 1.0.0
 */
package com.cfinal.web;

import javax.servlet.ServletRequestListener;

import com.cfinal.util.logger.CFLog;

/**
 * com.cfinal.web.CFServletRequestListener.java
 * @author XChao
 */
public abstract class CFServletRequestListener implements CFService, //
	ServletRequestListener {
	public final void onStartup(CFServletContext context) throws Exception {
		try {
			context.addBean(this);
			context.addListener(this);
			CFLog.debug("Scanner CFServletRequestListener： " + this.getClass().getName());
		} catch (Throwable throwable) {
			CFLog.error(this.getClass().getName() + ".onStartup error. ", throwable);
		}
	}
}
