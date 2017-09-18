package com.dreampany.frame.data.manager;

import com.dreampany.frame.data.thread.Runner;

/**
 * Created by nuc on 5/20/2017.
 */

public abstract class Manager extends Runner {

    protected volatile boolean started;

    public void start() {
        super.start();
    }

    public void stop() {
        super.stop();
    }

    @Override
    public void run() {
        super.run();
    }
}
