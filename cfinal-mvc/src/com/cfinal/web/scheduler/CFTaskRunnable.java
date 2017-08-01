/**
 * Created the com.cfinal.web.scheduler.CFTaskRunnable.java
 * @created 2016年10月8日 上午10:10:02
 * @version 1.0.0
 */
package com.cfinal.web.scheduler;

import com.cfinal.util.logger.CFLogger;

/**
 * com.cfinal.web.scheduler.CFTaskRunnable.java
 * @author XChao
 */
public class CFTaskRunnable extends Thread {

	private CFTaskPorxy xTaskPorxy;
	private CFTaskPattern pattern;
	private boolean running = false;
	private long millis;

	public CFTaskRunnable(CFTaskPorxy xTaskPorxy, CFTaskPattern pattern) {
		this.xTaskPorxy = xTaskPorxy;
		this.pattern = pattern;
		millis = System.currentTimeMillis();
	}

	public boolean match() {
		millis += 60000;
		return this.pattern.match(millis);
	}

	@Override
	public void run() {
		if (this.running == false && this.match()) {
			try {
				running = true;
				this.xTaskPorxy.getMethod().invoke(this.xTaskPorxy.getInstence());
			} catch (Throwable e) {
				CFLogger.severe(" Execute task " + this.xTaskPorxy.getInstence().getClass().getName() + " error. ", e);
			} finally {
				running = false;
			}
		}
	}

}
