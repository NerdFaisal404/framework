package com.dreampany.frame.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.dreampany.frame.data.enums.Type;

/**
 * Created by nuc on 11/16/2016.
 */

public class CloudTask<T extends Base, X extends Type> extends Task<T, X> {

    public T item;
    public boolean autoSync;
    public boolean persistent;

    public CloudTask() {}

    protected CloudTask(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<CloudTask> CREATOR = new Parcelable.Creator<CloudTask>() {
        @Override
        public CloudTask createFromParcel(Parcel in) {
            return new CloudTask(in);
        }

        @Override
        public CloudTask[] newArray(int size) {
            return new CloudTask[size];
        }
    };


    @Override
    protected CloudTask clone() throws CloneNotSupportedException {
        return (CloudTask) super.clone();
    }

    public CloudTask copy() {
        try {
            CloudTask copiedTask = clone();
            return copiedTask;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
