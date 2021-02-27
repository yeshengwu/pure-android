package com.example.pureandroid.livedata;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.pureandroid.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class TestLiveDataAc extends FragmentActivity {

    private ConversationListViewModel conversationListViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_livedata);
        findViewById(R.id.test_tv_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversationListViewModel.setStatusValue(2);
            }
        });

        findViewById(R.id.test_tv_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversationListViewModel.postStatus(3);
            }
        });

//        conversationListViewModel = ViewModelProviders
//                .of(this)
//                .get(ConversationListViewModel.class);

        ViewModelProvider vp = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication()));
        conversationListViewModel = vp.get(ConversationListViewModel.class);

        conversationListViewModel.connectionStatusLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.e("evan","onChanged integer = "+integer);
            }
        });
    }
}
