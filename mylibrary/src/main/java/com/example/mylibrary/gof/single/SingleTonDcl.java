package com.example.mylibrary.gof.single;

/**
 * https://github.com/allentofight/easy-cs/blob/main/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F/%E6%88%91%E7%94%A8DCL%E5%86%99%E5%87%BA%E4%BA%86%E5%8D%95%E4%BE%8B%E6%A8%A1%E5%BC%8F%EF%BC%8C%E7%BB%93%E6%9E%9C%E9%98%BF%E9%87%8C%E9%9D%A2%E8%AF%95%E5%AE%98%E4%B8%8D%E6%BB%A1%E6%84%8F%EF%BC%81.md
 * 2021-3-12 今天是他妈的彻底搞清楚 DCL的时候了. 是否要 volatile. 为啥要 dcl?
 *
 * 这之前一直人云亦云，看别人分析，自己不动手实战分析底层，别人怎么说就怎么背，跟着别人后面吃屎，
 * 关键问题是 别人也没完全搞清楚，完了，吃一半吃不下了，下次被问还是不知道。
 * 这样下去技术怎么证明是自己说上话是自己掌握了呢？不能吧！
 */
public class SingleTonDcl {
    private SingleTonDcl() {
    }

    private static SingleTonDcl instance = null;

    public static SingleTonDcl getInstance() {
        if (null == instance) {
            synchronized (SingleTonDcl.class) {
                if (null == instance) {
                    instance = new SingleTonDcl();
                }
            }
        }
        return instance;
    }

    // 这样可以吗？ 可以，是 懒汉式-线程安全
    /*public static SingleTonDcl getInstance() {
        synchronized (SingleTonDcl.class) {
            if (null == instance) {
                instance = new SingleTonDcl();
            }
        }
        return instance;
    }*/
}
