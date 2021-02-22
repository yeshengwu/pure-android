package com.example.pureandroid;

import android.graphics.Bitmap;
import android.os.Binder;

/**
 * https://blog.csdn.net/ylyg050518/article/details/97671874
 * Android跨进程传输超大bitmap的实现
 */
public class BitmapBinder extends Binder {

    private Bitmap bitmap;

    public BitmapBinder(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
