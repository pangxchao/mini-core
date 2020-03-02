package com.mini.core.thread;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;
import static java.util.Calendar.*;

/**
 * - 开启后台执行任务和定时任务
 * @author xchao
 */
public final class ScheduledThreadExecutor {
	private static final Map<String, ScheduledFuture<?>> futures = new HashMap<>();
	private static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(//
			2 * Runtime.getRuntime().availableProcessors());

	/**
	 * 在后台线程执行一个任务
	 * @param runnable 任务内容
	 */
	public static void execute(Runnable runnable) {
		executor.execute(runnable);
	}

	/**
	 * 在后台线程延时执行一个任务
	 * @param runnable 任务内容
	 * @param delay    延时时间
	 */

	public static void schedule(Runnable runnable, long delay) {
		schedule(runnable, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * 在后台线程延时执行一个任务
	 * @param runnable 任务内容
	 * @param delay    延时时间
	 * @param unit     时间单位
	 */
	public static void schedule(Runnable runnable, long delay, TimeUnit unit) {
		executor.schedule(runnable, delay, unit);
	}

	/**
	 * 在后台线程延时执行一个可取消的任务
	 * @param id       定时器ID
	 * @param runnable 任务内容
	 * @param delay    延时时间
	 */

	public static void schedule(String id, Runnable runnable, long delay) {
		schedule(id, runnable, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * 在后台线程延时执行一个可取消的任务
	 * @param id       定时器ID
	 * @param runnable 任务内容
	 * @param delay    延时时间
	 * @param unit     时间单位
	 */
	public static void schedule(String id, Runnable runnable, long delay, TimeUnit unit) {
		futures.put(id, executor.schedule(runnable, delay, unit));
	}

	/**
	 * 开启一个定时任务，该任务不受任务的执行时间影响
	 * @param runnable 任务内容
	 * @param delay    上次开始到下次开始的时间间隔
	 * @param unit     时间单位
	 */

	public static void scheduleAtFixedRate(Runnable runnable, long delay, TimeUnit unit) {
		executor.scheduleAtFixedRate(runnable, 0, delay, unit);
	}

	/**
	 * 开启一个定时任务，该任务不受任务的执行时间影响
	 * @param runnable     任务内容
	 * @param initialDelay 第一次延迟的时间
	 * @param delay        上次开始到下次开始的时间间隔
	 */

	public static void scheduleAtFixedRate(Runnable runnable, long initialDelay, long delay) {
		scheduleAtFixedRate(runnable, initialDelay, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * 开启一个定时任务，该任务不受任务的执行时间影响
	 * @param runnable     任务内容
	 * @param initialDelay 第一次延迟的时间
	 * @param delay        上次开始到下次开始的时间间隔
	 * @param unit         时间单位
	 */
	public static void scheduleAtFixedRate(Runnable runnable, long initialDelay, long delay, TimeUnit unit) {
		executor.scheduleAtFixedRate(runnable, initialDelay, delay, unit);
	}

	/**
	 * 开启一个可以取消定时任务，该任务不受任务的执行时间影响
	 * @param runnable     任务内容
	 * @param initialDelay 第一次延迟的时间
	 * @param delay        上次开始到下次开始的时间间隔
	 */
	public static void scheduleAtFixedRate(String id, Runnable runnable, long initialDelay, long delay) {
		scheduleAtFixedRate(id, runnable, initialDelay, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * 开启一个可以取消定时任务，该任务不受任务的执行时间影响
	 * @param runnable     任务内容
	 * @param initialDelay 第一次延迟的时间
	 * @param delay        上次开始到下次开始的时间间隔
	 * @param unit         时间单位
	 */
	public static void scheduleAtFixedRate(String id, Runnable runnable, long initialDelay, long delay,
			TimeUnit unit) {
		futures.put(id, executor.scheduleAtFixedRate(runnable, initialDelay, delay, unit));
	}

	/**
	 * 开启一个定时任务，该任务延时会受任务执行时间的影响
	 * @param runnable 任务内容
	 * @param delay    上次任务结束到下次任务开始的时间间隔
	 */

	public static void scheduleWithFixedDelay(Runnable runnable, long delay) {
		scheduleWithFixedDelay(runnable, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * 开启一个定时任务，该任务延时会受任务执行时间的影响
	 * @param runnable 任务内容
	 * @param delay    上次任务结束到下次任务开始的时间间隔
	 * @param unit     时间单位
	 */
	public static void scheduleWithFixedDelay(Runnable runnable, long delay, TimeUnit unit) {
		executor.scheduleWithFixedDelay(runnable, 0, delay, unit);
	}

	/**
	 * 开启一个定时任务，该任务延时会受任务执行时间的影响
	 * @param runnable     任务内容
	 * @param initialDelay 第一次延迟的时间
	 * @param delay        上次任务结束到下次任务开始的时间间隔
	 */

	public static void scheduleWithFixedDelay(Runnable runnable, long initialDelay, long delay) {
		scheduleWithFixedDelay(runnable, initialDelay, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * 开启一个定时任务，该任务延时会受任务执行时间的影响
	 * @param runnable     任务内容
	 * @param initialDelay 第一次延迟的时间
	 * @param delay        上次任务结束到下次任务开始的时间间隔
	 * @param unit         时间单位
	 */
	public static void scheduleWithFixedDelay(Runnable runnable, long initialDelay, long delay, TimeUnit unit) {
		executor.scheduleWithFixedDelay(runnable, initialDelay, delay, unit);
	}

	/**
	 * 开启一个定时任务，该任务延时会受任务执行时间的影响
	 * @param id           定时任务唯一识别码
	 * @param runnable     任务内容
	 * @param initialDelay 第一次延迟的时间
	 * @param delay        上次任务结束到下次任务开始的时间间隔
	 */

	public static void scheduleWithFixedDelay(String id, Runnable runnable, long initialDelay, long delay) {
		scheduleWithFixedDelay(id, runnable, initialDelay, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * 开启一个定时任务，该任务延时会受任务执行时间的影响
	 * @param id           定时任务唯一识别码
	 * @param runnable     任务内容
	 * @param initialDelay 第一次延迟的时间
	 * @param delay        上次任务结束到下次任务开始的时间间隔
	 * @param unit         时间单位
	 */
	public static void scheduleWithFixedDelay(String id, Runnable runnable, long initialDelay, long delay,
			TimeUnit unit) {
		futures.put(id, executor.scheduleWithFixedDelay(runnable, initialDelay, delay, unit));
	}

	/**
	 * 根据时间规则开启一个定时任务
	 * @param id       定时任务唯一识别码
	 * @param runnable 任务内容
	 * @param rule     时间规则
	 */
	public static void scheduleAtTimeRule(String id, Runnable runnable, TimeRule rule) {
		final long delay = 1000 - currentTimeMillis() % 1000;
		ScheduledThreadExecutor.scheduleAtFixedRate(id, () -> {
			final Calendar c = Calendar.getInstance();
			if (!rule.secondMatch(c.get(SECOND))) {
				return; // 秒
			}
			if (!rule.minuteMatch(c.get(MINUTE))) {
				return; // 分
			}
			if (!rule.hourMatch(c.get(HOUR_OF_DAY))) {
				return; // 时
			}
			if (!rule.dayMatch(c.get(DAY_OF_MONTH))) {
				return; // 日
			}
			if (!rule.monthMatch(c.get(MONTH))) {
				return; // 月
			}
			if (!rule.weekMatch(c.get(DAY_OF_WEEK))) {
				return; // 周
			}
			if (!rule.yearMatch(c.get(YEAR))) {
				return; // 年
			}
			runnable.run();
		}, delay, 1000);
	}

	/**
	 * 强制开启一个立即执行的线程<br/> 该线程无法取消，不受线程池管理约束
	 * @param runnable 任务内容
	 */
	public static void thread(Runnable runnable) {
		new Thread(runnable).start();
	}

	/**
	 * 取消未执行的延时任务
	 * @param id 定时器ID
	 */
	public static void cancel(String id) {
		ScheduledFuture<?> future = futures.get(id);
		if (future instanceof RunnableScheduledFuture) {
			executor.remove((Runnable) future);
			future.cancel(false);
		}
	}
}
