package com.example.mylibrary.gof;

public class DecorOrigin implements IOrigin{

    private OriginClass origin;

    public DecorOrigin(OriginClass origin) {
        this.origin = origin;
    }

    public void addWaterMark(){
        int originData = origin.getData();

    }

    @Override
    public int getData() {
        return 0;
    }

    @Override
    public void setData(int data) {

    }
}
