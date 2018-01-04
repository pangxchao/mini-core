/**
 * Created the com.cfinal.web.CFServletRequestAttributeListener.java
 * @created 2017年8月24日 下午2:48:23
 * @version 1.0.0
 */
package com.cfinal.web;

import javax.servlet.ServletRequestAttributeListener;

import com.cfinal.util.logger.CFLog;

/**
 * com.cfinal.web.CFServletRequestAttributeListener.java
 * @author XChao
 */
public abstract class CFServletRequestAttributeListener implements CFService, //
	ServletRequestAttributeListener {

	public final void onStartup(CFServletContext context) throws Exception {
		try {
			context.addBean(this);
			context.addListener(this);
			CFLog.debug("Scanner CFServletRequestAttributeListener： " + this.getClass().getName());
		} catch (Throwable throwable) {
			CFLog.error(this.getClass().getName() + ".onStartup error. ", throwable);
		}
	}
}
