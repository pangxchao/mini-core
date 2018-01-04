/**
 * Created the com.cfinal.web.scheduler.CFTimmertaskListener.java
 * @created 2017年8月21日 下午2:07:25
 * @version 1.0.0
 */
package com.cfinal.web.timmer;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import javax.servlet.ServletContextEvent;

import com.cfinal.util.logger.CFLog;
import com.cfinal.web.CFServletContextListener;

/**
 * com.cfinal.web.scheduler.CFTimmertaskListener.java
 * @author XChao
 */
public final class CFTimmerScheduledThreadPool extends CFServletContextListener {
	private final List<ScheduledFuture<?>> futures = new ArrayList<>();
	private ScheduledExecutorService scheduler;

	public final void contextInitialized(ServletContextEvent event) {
		try {
			Map<Class<? extends CFTimmer>, String> timmers = getContext().getTimmers();
			scheduler = Executors.newScheduledThreadPool(timmers.size());
			for (Entry<Class<? extends CFTimmer>, String> entry : timmers.entrySet()) {
				CFTimmer timmertask = getContext().getBean(entry.getKey());
				timmertask.setPattern(new CFTimmerPattern(entry.getValue()));
				futures.add(scheduler.scheduleAtFixedRate(timmertask, 1, 1, MINUTES));
			}
		} catch (Throwable throwable) {
			CFLog.error("Timmer start error.", throwable);
		}
	}

	public final void contextDestroyed(ServletContextEvent event) {
		for (ScheduledFuture<?> scheduledFuture : futures) {
			if(scheduledFuture != null && !scheduledFuture.isCancelled()) {
				scheduledFuture.cancel(true);
			}
		}
		if(scheduler != null && !scheduler.isShutdown()) {
			scheduler.shutdown();
		}
	}
}
