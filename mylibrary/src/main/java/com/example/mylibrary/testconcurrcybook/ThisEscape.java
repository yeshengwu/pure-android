package com.example.mylibrary.testconcurrcybook;

import android.view.View;

public class ThisEscape {

    public ThisEscape(Father father) {
        // ng, this ref are escaped.
        father.registerListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ThisEscape registerListener onClick");
            }
        });

        // right way
        SafeListener.newInstance(father);
    }
}
