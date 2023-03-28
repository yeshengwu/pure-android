package com.example.pureandroid.testaidl;

public class NotInitializedExecption extends RuntimeException {
    public NotInitializedExecption() {
        super("Not init!!!");
    }
}
