package com.example.mylibrary.testlaunage;

/**
 * ai问题： Java 常量引用 GCRoots，举个代码例子
 *
 * Java 中的常量引用是一种 GC Roots，这意味着它们可以防止被引用对象被垃圾回收器清理。以下是一个示例代码：
 * 在该示例中，MY_CONSTANT 是一个常量字符串，它被声明为 public static final，因此它是一个静态变量并且无法更改。
 * 在 main() 方法中，我们使用它来构建一个新的字符串（通过将其附加到一个 StringBuilder 实例中）。
 * 由于 MY_CONSTANT 是一个 GC Roots，它保留了对它所包含的对象（也就是该字符串）的引用，所以即使在我们尝试调用 System.gc() 请求垃圾回收时，该字符串也不会被回收丢弃。
 * [for Interview]
 */
public class ConstantsExample {
    public static final String MY_CONSTANT = "Hello, world!";

    public static void main(String[] args) {
//        String myString = new StringBuilder().append(MY_CONSTANT).toString();
//        System.gc(); // 在这里尝试触发垃圾回收
//        System.out.println(myString);

//        String myString = ", world!";
        String myString = new StringBuilder().append("cccc").toString();
        System.gc();
        System.out.println(myString);
    }
}
