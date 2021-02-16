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

        // solution 1
//        while (fast != null && k-- > 0) {
//            fast = fast.next;
//        }
//        if (k > 0) {
//            return null;
//        }

        // solution 2
        for (int i = 0; i < k; i++) {
            if (fast.next != null) {
                fast = fast.next;
            } else {
                return null;
            }
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

        int lastK = 2;
        ListNode theLastKNode = linkList.findTheLastKNode(node,lastK);
        System.out.println("lastK = "+lastK+" theLastKNode = "+theLastKNode);

        ListNode head = null;
        head = linkList.deleteLastKth(node,lastK);
        System.out.println("lastK = "+lastK+" deleteLastKth = "+head);

//        head = linkList.ReverseListEvan(node);
//        head = linkList.ReverseListRecursiveEvan(node);
//        System.out.println("ReverseList = " + head);

    }

    // 删除倒数第K个结点
    public  ListNode deleteLastKth(ListNode head, int k) {

        //        if (head == null) return null;
//        // 快慢指针
//        ListNode slow = head;
//        ListNode fast = head;
//
//        // solution 1
//        while (fast != null && k-- > 0) {
//            fast = fast.next;
//        }
//        if (k > 0) {
//            return null;
//        }


        ListNode fast = head;
        int i = 1;
        while (fast != null && i < k) {
            fast = fast.next;
            ++i;
        }

        if (fast == null) return head;

        ListNode slow = head;
        ListNode prev = null;
        while (fast.next != null) {
            fast = fast.next;
            prev = slow;
            slow = slow.next;
        }

        if (prev == null) {
            head = head.next;
        } else {
            prev.next = prev.next.next;
        }
        return head;
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

    /**
     *  1->2->3
     *   递归示意图：
     *      h                   h             h
     *   |-----|            |-----|        |-----|
     *   |  1   | -------> |  2   | -------> |  3   |
     *   |  .(回归点)  | <------- |  .(回归点)  | <-------  | return reverse = 3  |
     *   |  后续逻辑  |    |  后续逻辑  |
     *
     *   后续逻辑 是当前 h 入参 下一个节点往回指 head.next.next = head;  head.next = null;
     *
     * @param head
     * @return
     */
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
