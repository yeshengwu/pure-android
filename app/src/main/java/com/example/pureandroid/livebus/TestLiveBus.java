package com.example.pureandroid.livebus;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

public class TestLiveBus extends Fragment {

    public void initEvent() {
        LiveDataBus dataBus = LiveDataBus.get();
//        dataBus.observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer t) {
//
//            }
//        });
//
//        dataBus.observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String t) {
//
//            }
//        });

        dataBus.with("event", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
        dataBus.with("event", Integer.class).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });

        LiveDataBus.get().with("event").observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {

            }
        });

    }

    public void send(String data) {
        LiveDataBus.get().with("event").setValue("aaa");
    }
}
