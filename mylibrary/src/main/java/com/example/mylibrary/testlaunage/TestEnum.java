package com.example.mylibrary.testlaunage;


public class TestEnum {

    public static void main(String[] args) {
        System.out.println("DESTROYED.ordinal = " + State.DESTROYED.ordinal() + " INITIALIZED.ordinal = " +
                State.INITIALIZED.ordinal());

        System.out.println("DESTROYED compareTo INITIALIZED. value = " +State.DESTROYED.compareTo(State.INITIALIZED));

        // 结果：DESTROYED.ordinal = 0 INITIALIZED.ordinal = 1
        //      DESTROYED compareTo INITIALIZED. value = -1
        // Lifecycle 这边 State 枚举 是严格有序的，DESTROYED 放在第一个位置就是 0，
        // 那么做 compareTo 就是依赖 枚举 顺序的，如果枚举顺序调整比较的值也不一样。
        // enum ordinal() 初始值是0
    }

    public enum State {
        /**
         * Destroyed state for a LifecycleOwner. After this event, this Lifecycle will not dispatch
         * any more events. For instance, for an {@link android.app.Activity}, this state is reached
         * <b>right before</b> Activity's {@link android.app.Activity#onDestroy() onDestroy} call.
         */
        DESTROYED,

        /**
         * Initialized state for a LifecycleOwner. For an {@link android.app.Activity}, this is
         * the state when it is constructed but has not received
         * {@link android.app.Activity#onCreate(android.os.Bundle) onCreate} yet.
         */
        INITIALIZED,

        /**
         * Created state for a LifecycleOwner. For an {@link android.app.Activity}, this state
         * is reached in two cases:
         * <ul>
         *     <li>after {@link android.app.Activity#onCreate(android.os.Bundle) onCreate} call;
         *     <li><b>right before</b> {@link android.app.Activity#onStop() onStop} call.
         * </ul>
         */
        CREATED,

        /**
         * Started state for a LifecycleOwner. For an {@link android.app.Activity}, this state
         * is reached in two cases:
         * <ul>
         *     <li>after {@link android.app.Activity#onStart() onStart} call;
         *     <li><b>right before</b> {@link android.app.Activity#onPause() onPause} call.
         * </ul>
         */
        STARTED,

        /**
         * Resumed state for a LifecycleOwner. For an {@link android.app.Activity}, this state
         * is reached after {@link android.app.Activity#onResume() onResume} is called.
         */
        RESUMED;

    }
}
