package com.example.mylibrary.gof;

public class ProxyOrigin implements IOrigin{
    private OriginClass origin;

    public ProxyOrigin(OriginClass origin) {
        this.origin = origin;
    }

    @Override
    public int getData() {
        System.out.println("proxy before getData");
        int data = this.origin.getData();
        System.out.println("proxy after getData");
        return data;
    }

    @Override
    public void setData(int data) {
        System.out.println("proxy before setData");
        this.origin.setData(data);
        System.out.println("proxy after setData");
    }
}
