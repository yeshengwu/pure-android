package com.example.pureandroid.message;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageSnapshot implements Parcelable {
    private final int id;

    protected MessageSnapshot(Parcel in) {
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessageSnapshot> CREATOR = new Creator<MessageSnapshot>() {
        @Override
        public MessageSnapshot createFromParcel(Parcel in) {
            return new MessageSnapshot(in);
        }

        @Override
        public MessageSnapshot[] newArray(int size) {
            return new MessageSnapshot[size];
        }
    };
}
