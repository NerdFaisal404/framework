package com.dreampany.frame.data.manager;

import com.dreampany.frame.data.enums.Type;
import com.dreampany.frame.data.model.BaseParcel;
import com.dreampany.frame.data.model.Task;
import com.dreampany.frame.data.structure.SmartQueue;
import com.dreampany.frame.data.util.AndroidUtil;
import com.dreampany.frame.data.util.LogKit;

/**
 * Created by nuc on 12/24/2016.
 */

public abstract class TaskManager<T extends BaseParcel, X extends Type> extends Manager {

    private TaskManager parent;

    public void setParent(TaskManager parent) {
        this.parent = parent;
    }

    public TaskManager getParent() {
        return parent;
    }

    public void resolveTask(Task<T, X> task) throws InterruptedException {

    }

    protected final SmartQueue<Task<T, X>> taskQueue;
    volatile private Thread taskThread;

    public TaskManager() {
        taskQueue = new SmartQueue<>();
    }

    public void start() {
    }

    public void stop() {
    }

    protected void insertTask(Task<T, X> task) {
    }

    protected Task<T, X> get() {
        return taskQueue.pollFirst();
    }

    public void remove(Task<T, X> task) {
        taskQueue.remove(task);
    }

    private void clearQueue() {
        taskQueue.clear();
    }

    public int getCount() {
        return taskQueue.size();
    }

    public void postTask(Task<T, X> task) {
        postTask(null, task);
    }

    public void postTask(TaskManager parent, Task<T, X> task) {
        this.parent = parent;
        if (exists(task)) {
            return;
        }
        insertTask(task);
        resolveThread();
    }

    public void postTaskInFirst(Task<T, X> task) {
        if (exists(task)) {
            return;
        }
        taskQueue.insertFirst(task);
        resolveThread();
    }

    public void moveToFirst(Task<T, X> task) {
        remove(task);
        postTaskInFirst(task);
    }

    private boolean exists(Task<T, X> task) {
        for (Task<T, X> tTask : taskQueue) {
            if (tTask.equals(task)) {
                return true;
            }
        }
        return false;
    }

    private void resolveThread() {
        if (AndroidUtil.needThread(taskThread)) {
            taskThread = AndroidUtil.createThread(threadRunnable);
            taskThread.start();
        }
    }

    private final Runnable threadRunnable = new Runnable() {
        @Override
        public void run() {
            keepRunning();
        }
    };

    private void keepRunning() {

        while (true) {

            Task<T, X> nextTask = get();
            if (nextTask == null) break;

            try {
                resolveTask(nextTask);
            } catch (InterruptedException e) {
                LogKit.error(toString() + " " + e.toString());
            }

        }
    }

   /* public void postCallback(final Task<T, X> task) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Task.Callback callback = task.getCallback();
                if (callback != null) {
                    callback.onTask(task);
                }
            }
        };

        AndroidUtil.post(runnable);
    }

    public void postCallback(final Task<I, T, S, X, P> task, final List<I> items) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Task.Callback callback = task.getCallback();
                if (callback != null) {
                    callback.onData(task, items);
                }
            }
        };

        AndroidUtil.post(runnable);
    }*/
}

