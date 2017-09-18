package com.dreampany.frame.data.provider.pref;

import android.content.Context;

/**
 * Created by nuc on 5/1/2017.
 */

public final class FramePref extends BasePref {

    private static final String tour = "tour";
    private static final String register = "register";

    private static FramePref pref;

    private FramePref(Context context) {
        super(context);
    }

    synchronized public static FramePref onInstance(Context context) {
        if (pref == null) {
            pref = new FramePref(context);
        }
        return pref;
    }

    @Override
    protected String getPrefName() {
        return getClass().getSimpleName();
    }

    public void setValue(String key, long value) {
        setPrivateValue(key, value);
    }

    public long getLong(String key) {
        return getPrivateLong(key, 0L);
    }

    public boolean isToured() {
        boolean toured = getPublicBoolean(tour, false);
        return toured;
    }

    public boolean isRegistered() {
        boolean registered = getPublicBoolean(register, false);
        return registered;
    }

    public void tourCompleted() {
        setPublicValue(tour, true);
    }

    public void registerCompleted() {
        setPublicValue(register, true);
    }
}
