package com.mini.core.thread;

import com.google.common.eventbus.EventBus;

import java.io.Serializable;

import static com.mini.core.util.threed.ScheduledThreadExecutor.schedule;

@SuppressWarnings("UnstableApiUsage")
public final class EventExecutor implements Serializable {
    private static final EventBus EVENT = new EventBus();

    /**
     * 注册通知事件
     *
     * @param object 通知对象
     */
    public static void register(Object object) {
        EVENT.register(object);
    }

    /**
     * 取消通知事件注册
     *
     * @param object 通知对象
     */
    public static void unregister(Object object) {
        EVENT.unregister(object);
    }

    /**
     * 发送通知
     *
     * @param event 事件对象
     */
    public static void post(Object event) {
        EVENT.post(event);
    }

    /**
     * 延时发送通知
     *
     * @param event 事件对象
     */
    public static void post(Object event, long delay) {
        schedule(() -> post(event), delay);
    }
}
