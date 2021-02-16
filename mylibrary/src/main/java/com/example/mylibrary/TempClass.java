package com.example.mylibrary;

import java.util.Objects;

public class TempClass {

    private String name;
    private int age;

    public TempClass(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setData(String data) {
        System.out.println("IN TempClass. setData");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TempClass tempClass = (TempClass) o;
        return age == tempClass.age &&
                name.equals(tempClass.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }



}
