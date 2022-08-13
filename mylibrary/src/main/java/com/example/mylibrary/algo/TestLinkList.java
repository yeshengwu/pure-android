package com.example.mylibrary.algo;

/**
 * 自己实现测试手写。技巧方法：
 * 一题多解，来加强理解：比如反转链表方法： 递归，虚拟节点头插法，当前指针加上个指针法。
 */
public class TestLinkList {

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
        ListNode theLastKNode = linkList.findTheLastKNode(node, lastK);
        System.out.println("lastK = " + lastK + " find theLastKNode = " + theLastKNode);

        ListNode head = null;

        head = linkList.ReverseListEvan(node);
//        head = linkList.ReverseListRecursiveEvan(node);
        System.out.println("ReverseList = " + head);

        head = linkList.ReverseListRecursiveEvan(head);
        System.out.println("ReverseList reverse = " + head);

        head = linkList.deleteLastKth(node, lastK);
        System.out.println("deleteLastKth. lastK = " + lastK + " result list  = " + head);
    }

    // 删除倒数第K个结点
    public ListNode deleteLastKth(ListNode head, int k) {

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

            head = head.next; // 这样 head 头节点就断了，只剩 第一次循环赋值 head.next = dummyHead.next 它的值为 null.。下一次循环就直接退出了。
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
     * 1->2->3
     * 递归示意图：
     * h                   h             h
     * |-----|            |-----|        |-----|
     * |  1   | -------> |  2   | -------> |  3   |
     * |  .(回归点)  | <------- |  .(回归点)  | <-------  | return reverse = 3  |
     * |  后续逻辑  |    |  后续逻辑  |
     * <p>
     * 后续逻辑 是当前 h 入参 下一个节点往回指 head.next.next = head;  head.next = null;
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

        // 递归理解技巧： 先用 1个节点来套用下面解法
        // 再换成 2个节点 1->2->null 来测试
        // 最终换成 3个节点 1->2->3-null 来测试。
        // 配合阶乘理解： 递归开始做为起点，然后 往右上方画一个台阶，不断右上画台阶，再回归就明白递归的了。

        // 具体下面递归时，可以把每层变量用 h1 n1, h2 n2 来表示，
        // 比如 这里第一层时所有循环用的变量记录并画在纸上，比如 h1 就是 head,n1 就是 next节点，到了下一层就是 h2,n2,然后得到结果返回到h1层
        // 继续执行第一层逻辑。这样方法内变量就不会乱掉。


        // right
        if (head == null || head.next == null)  // 这里这么写是为了处理只有1个节点的情况
            return head;
        ListNode next = head.next;
        head.next = null;
        ListNode newHead = ReverseList(next);
        next.next = head;
        return newHead;
    }

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

}
