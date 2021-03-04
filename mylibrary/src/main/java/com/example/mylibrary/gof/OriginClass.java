package com.example.mylibrary.gof;

public class OriginClass implements IOrigin {
    private int data;

    @Override
    public void setData(int data) {
        this.data = data;
    }

    @Override
    public int getData() {
        return this.data;
    }
}
