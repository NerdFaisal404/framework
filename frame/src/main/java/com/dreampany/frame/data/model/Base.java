package com.dreampany.frame.data.model;

import android.os.Parcel;

import com.google.common.primitives.Longs;
import com.google.firebase.database.Exclude;
import com.dreampany.frame.data.util.DataUtil;

import java.lang.reflect.Field;

/**
 * Created by nuc on 12/6/2015.
 */
public abstract class Base extends BaseParcel {

    private long id;
    private long time;
    private long hash;

    protected Base() {
    }

    protected Base(Parcel in) {
        id = in.readLong();
        time = in.readLong();
        hash = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(time);
        dest.writeLong(hash);
    }

    @Override
    public boolean equals(Object inObject) {

        if (this == inObject) {
            return true;
        }

        if (!Base.class.isInstance(inObject)) {
            return false;
        }

        Base base = (Base) inObject;
        return hash == base.hash;
    }

    @Override
    public int hashCode() {
        int hashCode = Longs.hashCode(hash);
        return hashCode;
    }

    @Exclude
    public long getId() {
        return this.id;
    }

    @Exclude
    public long getTime() {
        return this.time;
    }

    public long getHash() {
        return hash;
    }

    public Base setId(long id) {
        this.id = id;
        return this;
    }

    public Base setTime(long time) {
        this.time = time;
        return this;
    }

    public Base setHash(long hash) {
        this.hash = hash;
        return this;
    }

    protected String toString(Object object) {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append(object.getClass().getName());
        result.append(" Object {");
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = object.getClass().getDeclaredFields();

        //print field names paired with their values
        for (Field field : fields) {
            result.append("  ");
            try {
                result.append(field.getName());
                result.append(": ");
                //requires access to private field:
                result.append(field.get(object));
            } catch (IllegalAccessException ex) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }

    public void createHash() {
        hash = DataUtil.getSha256();
    }

}
