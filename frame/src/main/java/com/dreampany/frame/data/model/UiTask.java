package com.dreampany.frame.data.model;

import android.os.Parcel;

import com.dreampany.frame.data.enums.Type;

/**
 * Created by nuc on 12/21/2016.
 */

public class UiTask<T extends Base> extends Task<T, Type> {

    public UiTask() {
    }

    public UiTask(T item) {
        super(item);
    }

    public UiTask(T item, Type itemType) {
        super(item);
        setItemType(itemType);
    }

    private UiTask(Parcel in) {
        super(in);
    }

    public static final Creator<UiTask> CREATOR = new Creator<UiTask>() {
        @Override
        public UiTask createFromParcel(Parcel in) {
            return new UiTask(in);
        }

        @Override
        public UiTask[] newArray(int size) {
            return new UiTask[size];
        }
    };
}
