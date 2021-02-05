package com.example.pureandroid;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tangdongwei on 2019/10/24.
 */
public interface IBookRecommendManager {

    View getView(ViewGroup parent);

    void destroy();

}
