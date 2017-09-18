package com.dreampany.frame.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.dreampany.frame.data.enums.Type;
import com.dreampany.frame.data.enums.SQLiteQueryType;
import com.dreampany.frame.data.enums.SQLiteSelectionType;

import java.lang.reflect.Field;

/**
 * Created by nuc on 12/3/2016.
 */

public class SQLiteTask<T extends Base, X extends Type> extends Task<T, X> {

    private long hash;
    private int count;
    private SQLiteQueryType queryType;
    private SQLiteSelectionType selectionType;

    public SQLiteTask() {
    }

    public SQLiteTask(T item) {
        super(item);
    }

    protected SQLiteTask(Parcel in) {
        super(in);
        hash = in.readLong();
        count = in.readInt();
        queryType = in.readParcelable(SQLiteQueryType.class.getClassLoader());
        selectionType = in.readParcelable(SQLiteSelectionType.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(hash);
        dest.writeInt(count);
        dest.writeParcelable(queryType, flags);
        dest.writeParcelable(selectionType, flags);
    }

    public static final Parcelable.Creator<SQLiteTask> CREATOR = new Parcelable.Creator<SQLiteTask>() {
        @Override
        public SQLiteTask createFromParcel(Parcel in) {
            return new SQLiteTask(in);
        }

        @Override
        public SQLiteTask[] newArray(int size) {
            return new SQLiteTask[size];
        }
    };

    public void setHash(long hash) {
        this.hash = hash;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setQueryType(SQLiteQueryType queryType) {
        this.queryType = queryType;
    }

    public void setSelectionType(SQLiteSelectionType selectionType) {
        this.selectionType = selectionType;
    }

    public long getHash() {
        return hash;
    }

    public long getCount() {
        return count;
    }

    public SQLiteQueryType getQueryType() {
        return queryType;
    }

    public SQLiteSelectionType getSelectionType() {
        return selectionType;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append(this.getClass().getName());
        result.append(" Object {");
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for (Field field : fields) {
            result.append("  ");
            try {
                result.append(field.getName());
                result.append(": ");
                //requires access to private field:
                result.append(field.get(this));
            } catch (IllegalAccessException ex) {
                //System.out.println(ex);
                ex.printStackTrace();
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }
}
