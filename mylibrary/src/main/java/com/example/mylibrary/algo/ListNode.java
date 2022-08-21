package com.example.mylibrary.algo;

/**
 * 和牛客/leetcode 定义保持一致便于代码提交验证。
 */
public class ListNode implements Cloneable{
    public int val;
    public ListNode next;

    public ListNode(int data, ListNode next) {
        this.val = data;
        this.next = next;
    }

    public ListNode(int data) {
        this.val = data;
        this.next = null;
    }

//        public int getData() {
//            return data;
//        }
    @Override
    protected ListNode clone() throws CloneNotSupportedException {
        return (ListNode) super.clone();
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + val +
                ", next=" + next +
                '}';
    }
}
