package com.dreampany.frame.data.model;

import android.content.Context;
import android.os.Parcel;

import com.google.common.primitives.Longs;
import com.dreampany.frame.data.enums.Type;
import com.dreampany.frame.data.enums.Processor;

/**
 * Created by nuc on 11/3/2016.
 */

public abstract class Task<T extends BaseParcel, X extends Type> extends BaseParcel {

    protected long id;
    protected T item;
    protected X itemType;
    protected X itemSource;
    protected X taskType;
    protected X density;
    protected X priority;
    protected X status;
    protected Processor processor;
    protected Context context;

    protected Task() {
    }

    protected Task(T item) {
        this.item = item;
    }

    protected Task(X taskType) {
        this.taskType = taskType;
    }

    protected Task(Parcel in) {
        super(in);
        id = in.readLong();
        if (in.readByte() == 0) {
            item = null;
        } else {
            Class<?> itemClazz = (Class<?>) in.readSerializable();
            item = in.readParcelable(itemClazz.getClassLoader());
        }
        itemType = in.readParcelable(Type.class.getClassLoader());
        itemSource = in.readParcelable(Type.class.getClassLoader());
        taskType = in.readParcelable(Type.class.getClassLoader());
        density = in.readParcelable(Type.class.getClassLoader());
        priority = in.readParcelable(Type.class.getClassLoader());
        status = in.readParcelable(Type.class.getClassLoader());
        processor = in.readParcelable(Processor.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(id);
        if (item == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            Class<?> itemClazz = item.getClass();
            dest.writeSerializable(itemClazz);
            dest.writeParcelable(item, flags);
        }
        dest.writeParcelable(itemType, flags);
        dest.writeParcelable(itemSource, flags);
        dest.writeParcelable(taskType, flags);
        dest.writeParcelable(density, flags);
        dest.writeParcelable(priority, flags);
        dest.writeParcelable(status, flags);
        dest.writeParcelable(processor, flags);
    }

    @Override
    public boolean equals(Object inObject) {
        if (this == inObject) {
            return true;
        }

        if (!Task.class.isInstance(inObject)) {
            return false;
        }

        Task item = (Task) inObject;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Longs.hashCode(id);
    }

    @Override
    protected Task<T, X> clone() throws CloneNotSupportedException {
        return (Task<T, X>) super.clone();
    }

    public Task<T, X> setId(long id) {
        this.id = id;
        return this;
    }

    public Task<T, X> setItem(T item) {
        this.item = item;
        return this;
    }

    public Task<T, X> setItemType(X itemType) {
        this.itemType = itemType;
        return this;
    }

    public Task<T, X> setItemSource(X itemSource) {
        this.itemSource = itemSource;
        return this;
    }

    public Task<T, X> setTaskType(X taskType) {
        this.taskType = taskType;
        return this;
    }

    public Task<T, X> setDensity(X density) {
        this.density = density;
        return this;
    }

    public Task<T, X> setPriority(X priority) {
        this.priority = priority;
        return this;
    }

    public Task<T, X> setStatus(X status) {
        this.status = status;
        return this;
    }

    public Task<T, X> setProcessor(Processor processor) {
        this.processor = processor;
        return this;
    }

    public Task<T, X> setContext(Context context) {
        this.context = context.getApplicationContext();
        return this;
    }

    public long getId() {
        return id;
    }

    public T getItem() {
        return item;
    }

    public X getItemType() {
        return itemType;
    }

    public X getItemSource() {
        return itemSource;
    }

    public X getTaskType() {
        return taskType;
    }

    public X getDensity() {
        return density;
    }

    public X getPriority() {
        return priority;
    }

    public X getStatus() {
        return status;
    }

    public Processor getProcessor() {
        return processor;
    }

    public Context getContext() {
        return context;
    }

    public void process() {
        processor.process(this);
    }
}
