package com.mini.core.util.thread;

import org.slf4j.Logger;

import java.io.Serializable;
import java.util.EventListener;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.mini.core.util.thread.ThreadExecutor.post;
import static org.slf4j.LoggerFactory.getLogger;

public final class RunnableQueue implements EventListener, Serializable {
    private static final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private static final Logger log = getLogger(RunnableQueue.class);
    private static boolean flag = false;

    // 队列监听启动
    private synchronized static void start() {
        if (!RunnableQueue.flag) {
            ThreadExecutor.thread(() -> {
                try {
                    flag = true;
                    for (Runnable runnable; flag; ) {
                        runnable = queue.take();
                        post(runnable);
                    }
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                } finally {
                    flag = false;
                }
            });
        }
    }

    /**
     * 添加一个任务到队列
     *
     * @param runnable 任务执行过程
     */
    public synchronized static void put(Runnable runnable) {
        try {
            RunnableQueue.queue.put(runnable);
            RunnableQueue.start();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}