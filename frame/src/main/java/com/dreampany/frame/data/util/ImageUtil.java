package com.dreampany.frame.data.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

/**
 * Created by nuc on 1/1/2017.
 */

public final class ImageUtil {
    private ImageUtil() {
    }

    public static Drawable getAppIcon(Context context, String packageName) {
        try {
            Drawable icon = context.getPackageManager().getApplicationIcon(packageName);
            return icon;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Drawable getAppIconByUri(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageArchiveInfo(uri, 0);
        if (pi == null) {
            return null;
        }
        ApplicationInfo appInfo = pi.applicationInfo;

        appInfo.sourceDir = uri;
        appInfo.publicSourceDir = uri;

        return appInfo.loadIcon(pm);
    }

    public static Drawable getVideoIcon(Context context, String thumbUri) {
        /*try {
            Drawable icon = context.getPackageManager().getApplicationIcon(packageName);
            return icon;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }*/

        return null;
    }
}
