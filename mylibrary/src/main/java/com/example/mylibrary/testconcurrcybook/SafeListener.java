package com.example.mylibrary.testconcurrcybook;

import android.view.View;

public class SafeListener {
    private final View.OnClickListener listener;

    private SafeListener() {
        this.listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("SafeListeners registerListener onClick");
            }
        };
    }

    public static SafeListener newInstance(Father father){
        SafeListener safeListener = new SafeListener();
        father.registerListener(safeListener.listener);
        return safeListener;
    }

}
