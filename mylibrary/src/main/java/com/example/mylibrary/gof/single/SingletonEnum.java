package com.example.mylibrary.gof.single;

public enum SingletonEnum {
    Instance("BDSDK", 1);

    private String name;
    private int index;

    private SingletonEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "SingletonEnum{" +
                "name='" + name + '\'' +
                ", index=" + index +
                '}';
    }

    public static SingletonEnum getInstance() {
        return Instance;
    }
}
