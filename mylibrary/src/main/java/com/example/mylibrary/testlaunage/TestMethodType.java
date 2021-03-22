package com.example.mylibrary.testlaunage;

import android.os.Build;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import androidx.annotation.RequiresApi;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * 《深入理解jvm》 p268
 * 2021-3-20
 */
public class TestMethodType {

    class GrandFather {
        void thinking() {
            System.out.println("i am GrandFather");
        }
    }

    class Father extends GrandFather {
        void thinking() {
            System.out.println("i am father");
        }
    }

    class Son extends Father {
        @RequiresApi(api = Build.VERSION_CODES.O)
        void thinking() {
            try {
                MethodType mt = MethodType.methodType(void.class);
                MethodHandle mh = lookup().findSpecial(GrandFather.class, "thinking", mt, getClass());
                mh.invoke(this);

                System.out.println(getClass());

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        (new TestMethodType().new Son()).thinking(); // output: i am father
        // 书本不对，参考网上最新 RFX 大神。 已经不能在Son里调用GrandFather的thinking()方法
        // https://www.zhihu.com/question/40427344/answer/86545388
    }

}
