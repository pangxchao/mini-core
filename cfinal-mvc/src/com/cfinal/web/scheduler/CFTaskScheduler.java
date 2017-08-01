/**
 * Created the com.cfinal.web.scheduler.CFTaskScheduler.java
 * @created 2016年10月8日 上午11:00:47
 * @version 1.0.0
 */
package com.cfinal.web.scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * com.cfinal.web.scheduler.CFTaskScheduler.java
 * @author XChao
 */
public class CFTaskScheduler {
	private static Object lockObject = new Object();
	private static ScheduledExecutorService service;
	private static Map<String, ScheduledFuture<?>> futures = new HashMap<>();

	/**
	 * 初始化定时任务线程
	 * @param corePoolSize
	 */
	public static void initTask(int corePoolSize) {
		synchronized (lockObject) {
			if(service == null) {
				service = Executors.newScheduledThreadPool(corePoolSize);
			}
		}
	}

	/**
	 * 添加一个工作线程, 调用添加之前需要调用初始化
	 * @param taskPorxy
	 */
	public static void addTask(CFTaskPorxy taskPorxy) {
		synchronized (lockObject) {
			if(service == null) {
				throw new RuntimeException("Calls to initTask before calling to addTask. ");
			}
			CFTaskPattern pattern = new CFTaskPattern(taskPorxy.getRule());
			CFTaskRunnable runnable = new CFTaskRunnable(taskPorxy, pattern);
			futures.put(taskPorxy.getName(), service.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.MINUTES));
		}
	}

	/**
	 * 删除一个工作线程
	 * @param taskPorxy
	 */
	public static void removeTask(CFTaskPorxy taskPorxy) {
		synchronized (lockObject) {
			ScheduledFuture<?> scheduledFuture = futures.get(taskPorxy.getName());
			if(scheduledFuture != null && !scheduledFuture.isCancelled()) {
				scheduledFuture.cancel(true);
			}
			scheduledFuture = null;
		}
	}

	public static void stopTask() {
		synchronized (lockObject) {
			for (String name : futures.keySet()) {
				ScheduledFuture<?> scheduledFuture = futures.get(name);
				if(scheduledFuture != null && !scheduledFuture.isCancelled()) {
					scheduledFuture.cancel(true);
				}
				scheduledFuture = null;
			}
			if(service != null && !service.isShutdown()) {
				service.shutdown();
			}
			service = null;
		}
	}
}
