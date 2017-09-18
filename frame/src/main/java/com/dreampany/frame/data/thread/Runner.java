package com.dreampany.frame.data.thread;

public abstract class Runner implements Runnable {

    protected long defaultWait = 1000L;
    protected long superWait = 3 * defaultWait;
    protected long periodWait = 1000L / 100;
    protected long wait = defaultWait;

    private Thread thread;
    private boolean running;
    private final Object guard = new Object();
    private volatile boolean guarded;

    public void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public void stop() {
        if (!running) {
            return;
        }
        running = false;
        thread.interrupt();
        notifyRunner();
    }

    public void waitRunner(long timeoutMs) {
        if (guarded) {
            //return;
        }
        guarded = true;
        synchronized (guard) {
            try {
                if (timeoutMs > 0L) {
                    guard.wait(timeoutMs);
                } else {
                    guard.wait();
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public void notifyRunner() {
        if (!guarded) {
            //return;
        }
        guarded = false;
        synchronized (guard) {
            guard.notify();
        }
    }

    protected void silentStop() {
        if (running) {
            running = false;
        }
    }

    public boolean isRunning() {
        return running;
    }

    private void waitFor(long timeout) throws InterruptedException {
        Thread.sleep(timeout);
    }

    protected abstract boolean looping() throws InterruptedException;

    @Override
    public void run() {
        try {
            while (running) {
                boolean looped = looping();
                if (!looped) {
                    break;
                }
            }
        } catch (InterruptedException interrupt) {
        }
        running = false;
    }
}