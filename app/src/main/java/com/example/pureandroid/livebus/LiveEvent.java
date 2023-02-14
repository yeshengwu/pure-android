package com.example.pureandroid.livebus;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public abstract class LiveEvent<T> {

    public Lifecycle.State observerActiveLevel() {
        return Lifecycle.State.CREATED;
    }

    public void observe(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer<T> observer) {

    }

    public void observeForever(@NonNull Observer<T> observer) {

    }

    public void observeSticky(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer<T> observer) {

    }

    public void observeStickyForever(@NonNull Observer<T> observer) {

    }

    public void postValue(T t) {

    }

    public void removeObserver(@NonNull Observer<T> observer) {

    }

    public void removeObservers(@NonNull LifecycleOwner owner) {

    }

    public void setValue(T t) {

    }
}
