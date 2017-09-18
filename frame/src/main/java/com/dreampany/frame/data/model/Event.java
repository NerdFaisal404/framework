package com.dreampany.frame.data.model;


import com.dreampany.frame.data.enums.Type;

/**
 * Created by nuc on 5/20/2017.
 */

public abstract class Event extends BaseSerial {
    public enum Result {
        SUCCESS, FAILED
    }

    public Type type;
    public Result result;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!Event.class.isInstance(obj)) {
            return false;
        }

        Event event = (Event) obj;
        return event.type.equals(type);
    }
}
