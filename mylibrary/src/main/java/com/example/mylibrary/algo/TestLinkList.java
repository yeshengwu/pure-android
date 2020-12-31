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


        public  ListNode ReverseList(ListNode head) {
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


    public  ListNode findTheLastKNode(ListNode head,int k) {
        if (head == null) return null;
        // 快慢指针
        ListNode slow = head;
        ListNode fast = head;
        for (int i = 0;i<k;i++) {
            fast = fast.next;
        }
        while (fast != null){
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    public static void main(String[] args){

        TestLinkList linkList = new TestLinkList();
        TestLinkList.ListNode node = new TestLinkList.ListNode(1);
        TestLinkList.ListNode node2 = new TestLinkList.ListNode(2);
        TestLinkList.ListNode node3 = new TestLinkList.ListNode(3);
        TestLinkList.ListNode node4 = new TestLinkList.ListNode(4);
        TestLinkList.ListNode node5 = new TestLinkList.ListNode(5);
        node.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        ListNode theLast4Node = linkList.findTheLastKNode(node,8);
        System.out.println("theLast4Node = "+theLast4Node);
        ListNode head = linkList.ReverseList(node);
        System.out.println("ReverseList = "+head);

    }




}
