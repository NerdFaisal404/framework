package com.dreampany.frame.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.dreampany.frame.data.util.DataUtil;

/**
 * Created by nuc on 5/6/2017.
 */

public class Session extends BaseCloud {

    private long beginTime;
    private long endTime;
    private long datetime;

    public Session() {
    }

    protected Session(Parcel in) {
        super(in);
        beginTime = in.readLong();
        endTime = in.readLong();
        datetime = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(beginTime);
        dest.writeLong(endTime);
        dest.writeLong(datetime);
    }

    public static final Parcelable.Creator<Session> CREATOR = new Parcelable.Creator<Session>() {
        @Override
        public Session createFromParcel(Parcel in) {
            return new Session(in);
        }

        @Override
        public Session[] newArray(int size) {
            return new Session[size];
        }
    };

    @Override
    public boolean equals(Object inObject) {
        return inObject instanceof Session
                && beginTime == ((Session) inObject).beginTime
                && datetime == ((Session) inObject).datetime;
    }

    @Exclude
    @Override
    public String toString() {
        return toString(this);
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getDatetime() {
        return datetime;
    }

    public long getSessionTime() {
        return endTime - beginTime;
    }

    public void createHash() {
        String joint = Long.toString(beginTime) + Long.toString(datetime);
        super.setHash(DataUtil.getSha256(joint));
    }
}
