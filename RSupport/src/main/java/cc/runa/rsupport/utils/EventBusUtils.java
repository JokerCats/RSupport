package cc.runa.rsupport.utils;

import org.greenrobot.eventbus.EventBus;

import cc.runa.rsupport.bean.DefaultEvent;

/**
 * @author JokerCats on 2020.08.03
 */
public class EventBusUtils {

    /**
     * 注册事件
     */
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    /**
     * 解除事件
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发送普通事件
     */
    public static void sendEvent(DefaultEvent event) {
        EventBus.getDefault().post(event);
    }

}