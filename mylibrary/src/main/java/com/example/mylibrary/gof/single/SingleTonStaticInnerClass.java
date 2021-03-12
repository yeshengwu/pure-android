package com.example.mylibrary.gof.single;

/**
 * 手写时容易忘记的 记忆点。
 * 丢三落四，不够严谨，也说明没有彻底理解。
 * 这样就容易被人抓住小99，没意义，一步到位就过了。
 */
public class SingleTonStaticInnerClass {

    private SingleTonStaticInnerClass() {
        //  逻辑记忆点3: 禁止外部实例化
    }

    private static class SingletonHolder { //逻辑记忆点2： 为了声明 sInstance 为 static，内部类需要静态内部类 static，否则编译不过
        public static SingleTonStaticInnerClass sInstance = new SingleTonStaticInnerClass();
    }

    public SingleTonStaticInnerClass getsInstance() { // 逻辑记忆点1： 返回 holder中构造的外部单例
        return SingletonHolder.sInstance;
    }
}
