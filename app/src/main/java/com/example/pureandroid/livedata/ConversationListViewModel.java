package com.example.pureandroid.livedata;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConversationListViewModel extends ViewModel {

    private MutableLiveData<Integer> connectionStatusLiveData ;

    public MutableLiveData<Integer> connectionStatusLiveData() {
        if (connectionStatusLiveData == null) {
            connectionStatusLiveData = new MutableLiveData<>();
        }

        return connectionStatusLiveData;
    }

    public void setStatusValue(int status){
        connectionStatusLiveData.setValue(status);
    }
    public void postStatus(int status){
        connectionStatusLiveData.postValue(status);
    }

    @Override
    protected void onCleared() {
        Log.e("evan","ConversationListViewModel onCleared");
    }
}
