package com.dreampany.frame.data.provider.pref;

import android.content.Context;

import com.github.pwittchen.prefser.library.rx2.Prefser;

/**
 * Created by air on 9/18/17.
 */

public class PrefManager extends Prefser {
    private static PrefManager instance;


    private PrefManager(Context context) {
        super(context.getApplicationContext());
    }

    synchronized public static PrefManager onInstance(Context context) {
        if (instance == null) {
            instance = new PrefManager(context);
        }
        return instance;
    }

}
