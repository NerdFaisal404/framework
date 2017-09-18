package com.dreampany.frame.data.enums;

import android.os.Parcelable;

import com.dreampany.frame.data.model.Task;

public interface Processor extends Parcelable {
    void process(Task task);
}