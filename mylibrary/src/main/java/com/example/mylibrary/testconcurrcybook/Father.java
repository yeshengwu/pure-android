package com.example.mylibrary.testconcurrcybook;

import android.view.View;

import java.util.Arrays;

public class Father {

    private final String[] states = new String[]{
            "a", "b"
    };

    public String[] getStates() {
        return states;
    }

    @Override
    public String toString() {
        return "Father{" +
                "states=" + Arrays.toString(states) +
                '}';
    }

    public void registerListener(View.OnClickListener clickListener) {
        clickListener.getClass().getSuperclass();
        //  通过这个  Field field = Inner.class.getDeclaredField("this$0");
        // 拿到匿名内部类的外部类引用
    }

}
