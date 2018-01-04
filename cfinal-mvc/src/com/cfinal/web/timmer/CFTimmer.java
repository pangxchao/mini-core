/**
 * Created the com.cfinal.web.scheduler.CFTimmertask.java
 * @created 2017年8月21日 上午10:21:50
 * @version 1.0.0
 */
package com.cfinal.web.timmer;

import com.cfinal.util.logger.CFLog;
import com.cfinal.web.CFService;
import com.cfinal.web.CFServletContext;

/**
 * com.cfinal.web.scheduler.CFTimmertask.java
 * @author XChao
 */
public abstract class CFTimmer implements CFService, Runnable {
	private long millis = 0;
	private boolean running = false;
	private CFTimmerPattern pattern;

	public final void onStartup(CFServletContext context) throws Exception {
		context.addBean(this);
		millis = System.currentTimeMillis();
		CFLog.debug("Scanner CFTimmer： " + this.getClass().getName());
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(CFTimmerPattern pattern) {
		this.pattern = pattern;
	}

	private final boolean match() {
		millis += 60000;
		return this.pattern.match(millis);
	}

	public final void run() {
		if(this.running == false && this.match()) {
			try {
				this.running = true;
				this.execute(getContext());
			} catch (Throwable e) {
				CFLog.error(" Execute task " + this.getClass().getName() + " error. ", e);
			} finally {
				running = false;
			}
		}
	}

	protected abstract void execute(CFServletContext context);
}
