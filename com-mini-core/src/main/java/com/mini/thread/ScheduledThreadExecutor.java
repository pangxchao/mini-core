package com.mini.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
        executor.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 在后台线程延时执行一个可取消的任务
     * @param id       定时器ID
     * @param runnable 任务内容
     * @param delay    延时时间
     */
    public static void schedule(String id, Runnable runnable, long delay) {
        futures.put(id, executor.schedule(runnable, delay, TimeUnit.MILLISECONDS));
    }

    /**
     * 开启一个定时任务，该任务不受任务的执行时间影响
     * @param runnable 任务内容
     * @param delay    上次开始到下次开始的时间间隔
     */
    public static void scheduleAtFixedRate(Runnable runnable, long delay) {
        executor.scheduleAtFixedRate(runnable, 0, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 开启一个定时任务，该任务不受任务的执行时间影响
     * @param runnable     任务内容
     * @param initialDelay 第一次延迟的时间
     * @param delay        上次开始到下次开始的时间间隔
     */
    public static void scheduleAtFixedRate(Runnable runnable, long initialDelay, long delay) {
        executor.scheduleAtFixedRate(runnable, initialDelay, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 开启一个可以取消定时任务，该任务不受任务的执行时间影响
     * @param runnable     任务内容
     * @param initialDelay 第一次延迟的时间
     * @param delay        上次开始到下次开始的时间间隔
     */
    public static void scheduleAtFixedRate(String id, Runnable runnable, long initialDelay, long delay) {
        futures.put(id, executor.scheduleAtFixedRate(runnable, initialDelay, delay, TimeUnit.MILLISECONDS));
    }

    /**
     * 开启一个定时任务，该任务延时会受任务执行时间的影响
     * @param runnable 任务内容
     * @param delay    上次任务结束到下次任务开始的时间间隔
     */
    public static void scheduleWithFixedDelay(Runnable runnable, long delay) {
        executor.scheduleWithFixedDelay(runnable, 0, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 开启一个定时任务，该任务延时会受任务执行时间的影响
     * @param runnable     任务内容
     * @param initialDelay 第一次延迟的时间
     * @param delay        上次任务结束到下次任务开始的时间间隔
     */
    public static void scheduleWithFixedDelay(Runnable runnable, long initialDelay, long delay) {
        executor.scheduleWithFixedDelay(runnable, initialDelay, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 开启一个定时任务，该任务延时会受任务执行时间的影响
     * @param runnable     任务内容
     * @param initialDelay 第一次延迟的时间
     * @param delay        上次任务结束到下次任务开始的时间间隔
     */
    public static void scheduleWithFixedDelay(String id, Runnable runnable, long initialDelay, long delay) {
        futures.put(id, executor.scheduleWithFixedDelay(runnable, initialDelay, delay, TimeUnit.MILLISECONDS));
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
