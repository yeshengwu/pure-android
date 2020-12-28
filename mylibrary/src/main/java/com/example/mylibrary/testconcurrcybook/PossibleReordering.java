package com.example.mylibrary.testconcurrcybook;

import android.os.Parcel;
import android.os.Parcelable;

public class PossibleReordering implements Parcelable {

    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                a = 1;
                x = b;
            }
        });

        Thread other = new Thread(new Runnable() {
            @Override
            public void run() {
                b = 1;
                y = a;
            }
        });

        one.start();
        other.start();
        one.join();
        other.join();
        System.out.println("x = " + x + " y = " + y);
    }

    protected PossibleReordering(Parcel in) {
    }

    public static final Creator<PossibleReordering> CREATOR = new Creator<PossibleReordering>() {
        @Override
        public PossibleReordering createFromParcel(Parcel in) {
            return new PossibleReordering(in);
        }

        @Override
        public PossibleReordering[] newArray(int size) {
            return new PossibleReordering[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
