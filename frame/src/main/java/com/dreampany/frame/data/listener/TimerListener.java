package com.dreampany.frame.data.listener;

/**
 * Created by air on 5/4/17.
 */

public interface TimerListener {
    void onTimerElapsed();
    void onTimerTick(long currentMillis);
}
