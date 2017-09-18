package com.dreampany.frame.data.manager;

import android.content.Context;

import com.dreampany.frame.data.enums.Type;
import com.dreampany.frame.data.model.Point;
import com.dreampany.frame.data.model.Session;
import com.dreampany.frame.data.provider.pref.FramePref;
import com.dreampany.frame.data.provider.sqlite.FrameSQLite;
import com.dreampany.frame.data.util.AndroidUtil;
import com.dreampany.frame.data.util.DataUtil;

/**
 * Created by air on 5/11/17.
 */

public final class PointManager {

    public static final long defaultThreshold = 100;
    public static final long defaultPoints = 1000L;
    private static final String usedPoints = "used_points";

    private static PointManager manager;
    private long threshold = defaultThreshold;

    private PointManager() {

    }

    public static synchronized PointManager onInstance() {
        if (manager == null) {
            manager = new PointManager();
        }
        return manager;
    }


    public long toPoints(long credits) {
        long points = credits / threshold;
        return points < 0L ? 0L : points;
    }

    public void trackPoints(Context context, Session session, Type pointType) {
        long credits = session.getSessionTime() / 1000; // seconds
        long points = toPoints(credits);

        Point point = new Point();
        point.setHash(session.getHash());
        point.setPoints(points);
        point.setType(pointType);

        FrameSQLite.onInstance(context).write(point);
    }

    public void trackPoints(Context context, long hash, long points, Type pointType) {
        Point point = new Point();
        point.setHash(hash);
        point.setPoints(points);
        point.setType(pointType);

        FrameSQLite.onInstance(context).write(point);
    }

    public long getAvailablePoints(Context context) {
        long totalPoints = FrameSQLite.onInstance(context).getPoints();
        long usedPoints = FramePref.onInstance(context).getLong(PointManager.usedPoints);
        return totalPoints - usedPoints;
    }

    public long getPoints(Context context, Type... types) {
        long points = 0L;
        for (Type type : types) {
            points += FrameSQLite.onInstance(context).getPoints(type);
        }
        return points;
    }

    public void dragDefaultPoints(Context context, Type pointType) {
        if (FrameSQLite.onInstance(context).isEmpty(pointType)) {
            String appId = AndroidUtil.getApplicationId(context);
            long hash = DataUtil.getSha256(appId);
            Point point = new Point();
            point.setHash(hash);
            point.setPoints(defaultPoints);
            point.setType(pointType);
            FrameSQLite.onInstance(context).write(point);
        }
    }
}
