package com.example.mylibrary;

class Parent {
    static {
        System.out.println("Parent static");
    }

    {
        System.out.println("Parent");
    }

    Parent() {
        System.out.println("Parent constructor");
    }
}

class Child extends Parent {
    static {
        System.out.println("Child static");
    }

    {
        System.out.println("Child");
    }

    Child() {
        System.out.println("Child constructor");
    }
}

public class CodeBlockTest {
    public static void main(String[] args) {
        Parent parent = new Child();
    }
}