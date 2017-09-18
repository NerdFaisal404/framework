package com.dreampany.frame.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.dreampany.frame.data.enums.Type;

/**
 * Created by air on 5/11/17.
 */

public class Point extends BaseCloud {

    private long points;
    private Type type;

    public Point() {
    }

    protected Point(Parcel in) {
        super(in);
        points = in.readLong();
        type = in.readParcelable(Type.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(points);
        dest.writeParcelable(type, flags);
    }

    public static final Parcelable.Creator<Point> CREATOR = new Parcelable.Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel in) {
            return new Point(in);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };

    public void setPoints(long points) {
        this.points = points;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getPoints() {
        return points;
    }

    public Type getType() {
        return type;
    }
}
