package com.shinhoandroid.mvp.model.utils;


import org.greenrobot.eventbus.EventBus;

/**
 * @author Created by gsd on 2016/12/7.
 */
public class EventBusUtils {

    public static EventBus getDefault() {
        return EventBus.getDefault();
    }


    public static void post(Object event) {
        getDefault().post(event);
    }

    public static void postSticky(Object stickyEvent) {
        getDefault().postSticky(stickyEvent);
    }

    public static void register(Object obj) {
        if (!getDefault().isRegistered(obj)) {
            getDefault().register(obj);
        }
    }

    public static void unRegister(Object obj) {
        if (getDefault().isRegistered(obj)) {
            getDefault().unregister(obj);
        }
    }
}
