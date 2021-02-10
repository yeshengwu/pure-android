package com.example.pureandroid;

import android.view.View;
import android.view.ViewGroup;

public interface IBookRecommendManager {

    View getView(ViewGroup parent);

    void destroy();

}
