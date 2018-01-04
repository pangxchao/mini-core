/**
 * Created the com.cfinal.web.core.CFHttpSessionListener.java
 * @created 2017年8月21日 下午2:22:55
 * @version 1.0.0
 */
package com.cfinal.web.http;

import javax.servlet.http.HttpSessionListener;

import com.cfinal.util.logger.CFLog;
import com.cfinal.web.CFService;
import com.cfinal.web.CFServletContext;

/**
 * com.cfinal.web.core.CFHttpSessionListener.java
 * @author XChao
 */
public abstract class CFHttpSessionListener implements CFService, //
	 HttpSessionListener {

	public final void onStartup(CFServletContext context) throws Exception {
		try {
			context.addBean(this);
			context.addListener(this);
			CFLog.debug("Scanner CFHttpSessionListener： " + this.getClass().getName());
		} catch (Throwable throwable) {
			CFLog.error(this.getClass().getName() + ".onStartup error. ", throwable);
		}
	}
	
}
