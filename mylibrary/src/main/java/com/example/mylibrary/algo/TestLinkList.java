package com.example.mylibrary.algo;

public class TestLinkList {

    public static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }


    public ListNode ReverseList(ListNode head) {
        // my wrong
//            if(head == null ) {
//                return head;
//            }
//            ListNode next = head.next;
//            next.next= head;
//            head = next;
//            head.next = null;
//            return  ReverseList(head);

        // right
        if (head == null || head.next == null)
            return head;
        ListNode next = head.next;
        head.next = null;
        ListNode newHead = ReverseList(next);
        next.next = head;
        return newHead;
    }


//    public ListNode ReverseList(ListNode head) {
//        ListNode newList = new ListNode(-1);
//        while (head != null) {
//            ListNode next = head.next;
//            head.next = newList.next;
//            newList.next = head;
//            head = next;
//        }
//        return newList.next;
//    }


    public ListNode findTheLastKNode(ListNode head, int k) {
        if (head == null) return null;
        // 快慢指针
        ListNode slow = head;
        ListNode fast = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    public static void main(String[] args) {

        TestLinkList linkList = new TestLinkList();
        TestLinkList.ListNode node = new TestLinkList.ListNode(1);
        TestLinkList.ListNode node2 = new TestLinkList.ListNode(2);
        TestLinkList.ListNode node3 = new TestLinkList.ListNode(3);
        TestLinkList.ListNode node4 = new TestLinkList.ListNode(4);
        TestLinkList.ListNode node5 = new TestLinkList.ListNode(5);
        node.next = node2;
        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;

//        ListNode theLast4Node = linkList.findTheLastKNode(node,5);
//        System.out.println("theLast4Node = "+theLast4Node);
        ListNode head = null;

//        head = linkList.ReverseListEvan(node);
        head = linkList.ReverseListRecursiveEvan(node);
        System.out.println("ReverseList = " + head);

    }

    public ListNode ReverseListEvan(ListNode head) {
        // 虚拟头节点，头插法
        ListNode dummyHead = new ListNode(-1);

        //  head 头节点就断了
        /*while (head != null) {
            head.next = dummyHead.next;
            dummyHead.next = head;

            head = head.next; // 这样 head 头节点就断了，只剩 这次的 head.next值。下一次循环就直接退出了。
        }
        return dummyHead.next;*/

        // 正解： 先保留 next 节点，不要断链。
        while (head != null) {
            ListNode next = head.next;

            head.next = dummyHead.next;
            dummyHead.next = head;

            head = next;
        }
        return dummyHead.next;
    }

    public ListNode ReverseListRecursiveEvan(ListNode head) {
//        if (head.next == null) {
//            return head;
//        }
//        ListNode next = ReverseListRecursiveEvan(head.next);
//        next.next = head;
//        return next;

        if (head == null || head.next == null) return head;
        // 从下一个节点开始递归
        ListNode reverse = ReverseListRecursiveEvan(head.next);
        head.next.next = head; // 设置下一个节点的 next 为当前节点
        head.next = null; // 把当前节点的 next 赋值为 null，避免循环引用
        return reverse;
    }


}
