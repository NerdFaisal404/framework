package com.dreampany.frame.data.manager;

import com.dreampany.frame.data.model.Event;
import com.dreampany.frame.data.structure.SmartQueue;

import org.greenrobot.eventbus.EventBus;

public abstract class EventManager extends Manager {

    protected SmartQueue<Event> queue;
    protected EventManager() {
        queue = new SmartQueue<>();
    }


    protected void putEvent(Event event) {
        if (exists(event)) {
            return;
        }

        queue.insertLast(event);
        start();
    }

    private boolean exists(Event event) {
        for (Event e : queue) {
            if (e.equals(event)) {
                return true;
            }
        }
        return false;
    }

    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }
}
