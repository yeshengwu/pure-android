package com.example.mylibrary;

public class TestSingleton {

    private static class SingletonHolder {
        private static TestSingleton instance = new TestSingleton();
    }

    public static TestSingleton getSingleton() {
        return SingletonHolder.instance;
    }

}
