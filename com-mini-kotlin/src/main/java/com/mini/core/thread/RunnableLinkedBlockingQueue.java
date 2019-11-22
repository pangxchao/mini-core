package com.mini.core.thread;

import com.mini.core.logger.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.mini.core.logger.LoggerFactory.getLogger;

public final class RunnableLinkedBlockingQueue {
    private static final Logger logger = getLogger(RunnableLinkedBlockingQueue.class);
    private static final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private static boolean flag = false;

    // 队列监听启动
    protected synchronized static void start() {
        // 如果线程正在运行则不处理
        if (RunnableLinkedBlockingQueue.flag) {
            return;
        }
        // 标记线程下在运行中
        RunnableLinkedBlockingQueue.flag = true;
        // 开启一个线程来处理队列任务
        ScheduledThreadExecutor.thread(() -> {
            while (RunnableLinkedBlockingQueue.flag) {
                try {
                    // 使用阻塞模式获取队列消息
                    Runnable runnable = queue.take();
                    // 将获取消息交由线程池处理
                    ScheduledThreadExecutor.execute(runnable);
                } catch (Exception | Error exception) {
                    logger.error(exception);
                }
            }
        });
    }

    /**
     * 添加一个任务到队列
     * @param runnable 任务执行过程
     */
    public synchronized static void put(Runnable runnable) {
        try {
            RunnableLinkedBlockingQueue.queue.put(runnable);
            RunnableLinkedBlockingQueue.start();
        } catch (Exception | Error exception) {
            logger.error(exception);
        }
    }
}
