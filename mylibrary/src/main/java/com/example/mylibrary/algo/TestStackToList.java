package com.example.mylibrary.algo;

import java.util.Stack;

public class TestStackToList {

    public static void main(String args[]) {
        /**
         * 手动模拟操作如下：
         * + 号表示进队 -表示 出队
         * ()号表示 操作序号。
         *
         * 1     2    3    4
         * +(1)  +(2) +(3) +(6)
         * -(4)  -(5)      -(7)
         *
         * 说明： +(1)  +(2) +(3) 表示 将 数字 1 2 3 进入队列，
         * -(4) 表示 将 数字 1 出队列，
         * -(5) 表示 将 数字 2 出队列，
         * +(6) 表示 将 数字 4 进入队列，
         */

        TestStackToList list = new TestStackToList();
        list.add(1);
        list.add(2);
        list.add(3);

        int a = list.pop();
        System.out.println("pop = "+a);
        int a2 = list.pop();
        System.out.println("pop = "+a2);
        list.add(4);
        int a3 = list.pop();
        System.out.println("pop = "+a3);
        System.out.println("pop = "+list.pop());

    }

    private Stack<Integer> firstIn = new Stack<>();
    private Stack<Integer> secOut = new Stack<>();

    public void add(int a){
        firstIn.add(a);
    }

    public int pop() {
        // 关键就在这，如果 secOut本身残留，直接pop。
        // 手动 模拟 进出操作发现。
        if (!secOut.isEmpty()) {
            return secOut.pop();
        }
        //

        // copy
        while (!firstIn.isEmpty()){
            secOut.add(firstIn.pop());
        }
        if (!secOut.isEmpty()) {
            return secOut.pop();
        }
        return -1;
    }

}
