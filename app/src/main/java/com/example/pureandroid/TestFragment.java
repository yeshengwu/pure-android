package com.example.pureandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TestFragment extends Fragment {
    private static final String TAG = "test_fragment";

    private List<View> mCachedView;

    public static Fragment create() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCachedView = new ArrayList<>();
        Log.d(TAG, "onAttach");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView.");
        View rootView = inflater.inflate(R.layout.fragment_test, container, false);
        TextView textView = rootView.findViewById(R.id.fragment_test_text);
        Log.d(TAG, "onCreateView. textView = " + textView);

        if (mCachedView.contains(textView)) {
            Log.d(TAG, "onCreateView. mCachedView contains textView");
        } else {
            Log.d(TAG, "onCreateView. mCachedView not contains textView");
            mCachedView.add(textView);
        }
        for (View item : mCachedView) {
            Log.d(TAG, "onCreateView. item = " + item);
        }

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate. savedInstanceState = " + savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // test throw memory leak.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
        // test end

        Log.d(TAG, "onActivityCreated. savedInstanceState = " + savedInstanceState);
        View view = getView();
        if (view != null) {
            TextView textView = view.findViewById(R.id.fragment_test_text);
            Log.d(TAG, "onActivityCreated. textView = " + textView);
        } else {
            Log.d(TAG, "onActivityCreated. getView = null");
        }
        Log.d(TAG, "onActivityCreated. mCachedView = " + System.identityHashCode(mCachedView));
        for (View item : mCachedView) {
            Log.d(TAG, "onActivityCreated. item = " + item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume. mCachedView = " + System.identityHashCode(mCachedView));
        for (View item : mCachedView) {
            Log.d(TAG, "onResume. item = " + item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView. mCachedView = " + System.identityHashCode(mCachedView));
        for (View item : mCachedView) {
            Log.d(TAG, "onDestroyView. item = " + item);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach. mCachedView = " + System.identityHashCode(mCachedView));
        for (View item : mCachedView) {
            Log.d(TAG, "onDetach. item = " + item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy.");
    }
}
