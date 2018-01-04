/**
 * Created the com.cfinal.web.http.CFHttpSessionIdListener.java
 * @created 2017年8月24日 下午2:53:50
 * @version 1.0.0
 */
package com.cfinal.web.http;

import javax.servlet.http.HttpSessionIdListener;

import com.cfinal.util.logger.CFLog;
import com.cfinal.web.CFService;
import com.cfinal.web.CFServletContext;

/**
 * com.cfinal.web.http.CFHttpSessionIdListener.java
 * @author XChao
 */
public abstract class CFHttpSessionIdListener implements CFService, //
	HttpSessionIdListener {

	public final void onStartup(CFServletContext context) throws Exception {
		try {
			context.addBean(this);
			context.addListener(this);
			CFLog.debug("Scanner CFHttpSessionIdListener： " + this.getClass().getName());
		} catch (Throwable throwable) {
			CFLog.error(this.getClass().getName() + ".onStartup error. ", throwable);
		}
	}

}
