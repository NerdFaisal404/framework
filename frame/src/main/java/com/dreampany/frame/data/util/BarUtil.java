package com.dreampany.frame.data.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.dreampany.frame.data.model.Color;
import com.dreampany.frame.ui.activity.BaseActivity;

public class BarUtil {

    public static void hide(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        //hideToolbar(activity);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusColor(Activity activity, Color color) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ColorUtil.getColor(activity, color.getColorPrimaryDarkId()));
        }
        // window.set(activity.getResources().getColor(R.color.example_color));
    }

    public static void setStatusColor(BaseActivity activity, Color color) {
        setStatusColor((Activity) activity, color);
    }


    private static int backupStatusColor;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void backupStatusColor(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            backupStatusColor = window.getStatusBarColor();
        }
    }


    public static void backupStatusColor(BaseActivity activity) {
        backupStatusColor((Activity) activity);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void restoreStatusColor(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(backupStatusColor);
        }
    }

    public static void setActionBarColor(Toolbar toolbar, Color color) {
        if (toolbar != null) {
            toolbar.setBackgroundColor(ColorUtil.getColor(toolbar.getContext(), color.getColorPrimaryId()));
        }
    }


    public static void showToolbar(Activity activity) {
        if (BaseActivity.class.isInstance(activity)) {
           // showToolbar((BaseActivity) activity);
        }
    }

    public static void showToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        }
        //  mFabButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).startUi();
    }

    public static void hideToolbar(Activity activity) {
        if (BaseActivity.class.isInstance(activity)) {
           // hideToolbar((BaseActivity) activity);
        }
    }

    public static void hideToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        }
        //mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

        // FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
        // int fabBottomMargin = lp.bottomMargin;
        // mFabButton.animate().translationY(mFabButton.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).startUi();
    }
}
