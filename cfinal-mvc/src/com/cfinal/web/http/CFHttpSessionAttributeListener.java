/**
 * Created the com.cfinal.web.http.CFHttpSessionAttributeListener.java
 * @created 2017年8月24日 下午3:00:19
 * @version 1.0.0
 */
package com.cfinal.web.http;

import javax.servlet.http.HttpSessionAttributeListener;

import com.cfinal.util.logger.CFLog;
import com.cfinal.web.CFService;
import com.cfinal.web.CFServletContext;

/**
 * com.cfinal.web.http.CFHttpSessionAttributeListener.java
 * @author XChao
 */
public abstract class CFHttpSessionAttributeListener implements CFService, //
	HttpSessionAttributeListener {

	@Override
	public void onStartup(CFServletContext context) throws Exception {
		try {
			context.addBean(this);
			context.addListener(this);
			CFLog.debug("Scanner CFHttpSessionAttributeListener： " + this.getClass().getName());
		} catch (Throwable throwable) {
			CFLog.error(this.getClass().getName() + ".onStartup error. ", throwable);
		}
	}
}
