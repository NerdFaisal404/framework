package com.dreampany.frame.application;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.Indexable;
import com.dreampany.frame.data.util.AndroidUtil;

import java.lang.ref.WeakReference;

/**
 * Created by nuc on 11/29/2016.
 */

public class BaseApplication extends MultiDexApplication implements android.app.Application.ActivityLifecycleCallbacks {

    protected Context context;
    protected WeakReference<Activity> activityReference;
    protected Action action;
    protected Indexable indexable;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        registerActivityLifecycleCallbacks(this);
        // Realm.init(this);
        AndroidUtil.setDebug(context);

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        activityReference = new WeakReference<>(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityReference = null;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public void trackMe(String screenName) {

    }

    public void appIndex(String name, String description, String url) {

    }

    public void startIndexingMe() {
    }

    public void stopIndexingMe() {

    }

    protected Action getAction(String description, String uri) {
        return new Action.Builder(Action.Builder.VIEW_ACTION)
                .setObject(description, uri)
                .build();
    }

    public void jumpIfNeeded(Activity activity) {
        if (needForceJump() && getJumpingClass() != null) {
            //Intent intent = new Intent(activity, getJumpingClass());
            //startActivity(intent);
            AndroidUtil.openDestroyActivity(activity, getJumpingClass());
            //activity.finish();
        }
    }

    protected boolean needForceJump() {
        return false;
    }

    protected Class getJumpingClass() {
        return null;
    }

}
