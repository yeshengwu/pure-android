package com.example.mylibrary.algo;

/**
 * Created by shidu on 19/4/5.
 * https://github.com/wangzheng0822/algo/blob/master/java/07_linkedlist/LinkedListAlgo.java
 */

/**
 * 1) 单链表反转
 * 2) 链表中环的检测
 * 3) 两个有序的链表合并
 * 4) 删除链表倒数第n个结点
 * 5) 求链表的中间结点
 * <p>
 * Author: Zheng
 */
public class LinkedListAlgo {

    // 单链表反转  不带头节点操作反转
    public static ListNode reverse(ListNode list) {
        ListNode curr = list, pre = null;
        while (curr != null) {
            ListNode next = curr.next;  //防止链表断裂，先存next节点，下面curr好节点更新
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;

        // error :  curr.next = pre;  使得链表断裂
//        Node curr = list, pre = null;
//        while (curr != null) {
//            curr.next = pre;
//            pre = curr;
//            curr = curr.next;
//        }
//        return pre;

        // error : next.next = newList.next; 使得链表断裂
//        Node newList = new Node(-1,null);
//        while (list != null) {
//            Node next = list.next;
//            if (next == null) break;
//            next.next = newList.next;
//            newList.next = next;
//            list = next;
//        }
//        return newList.next;

        //带头节点来操作反转
//        Node newList = new Node(-1,null);
//        while (list != null) {
//            Node next = list.next;
//            list.next = newList.next; // 让当前头节点指向 新头节点的下一个节点
//            newList.next = list;
//            list = next;
//        }
//        return newList.next;
    }

    public static ListNode ReverseList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode next = head.next;
        head.next = null;
        ListNode newHead = ReverseList(next);
        next.next = head;
        return newHead;
    }

    // 检测环
    public static boolean checkCircle(ListNode list) {
        if (list == null) return false;

        ListNode fast = list.next;
        ListNode slow = list;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (slow == fast) return true;
        }

        return false;
    }

    // 有序链表合并
    public static ListNode mergeSortedLists(ListNode la, ListNode lb) {
        if (la == null) return lb;
        if (lb == null) return la;

        ListNode p = la;
        ListNode q = lb;
        ListNode head;
        if (p.val < q.val) {
            head = p;
            p = p.next;
        } else {
            head = q;
            q = q.next;
        }
        ListNode r = head;

        while (p != null && q != null) {
            if (p.val < q.val) {
                r.next = p;
                p = p.next;
            } else {
                r.next = q;
                q = q.next;
            }
            r = r.next;
        }

        if (p != null) {
            r.next = p;
        } else {
            r.next = q;
        }

        return head;
    }

    /**
     * 思考过程怎么转换成代码？
     * 代码只是思考后的成品而已，应该要知道思考过程，
     * 为什么这么思考，目的是解决什么问题，所以思考过程
     * 一定要写下来。有了想法就好办，否则光记代码几天又忘记了
     * 目标是：一看思考过程和思考思路马上就能知道代码怎么写。2021-2-21
     *
     * https://blog.csdn.net/u013486414/article/details/104889368/  算法题做到崩溃？刷了几千道算法题，关于如何刷题有些话我想对你说
     *
     */

    /**
     * 跟上面 mergeSortedLists 一样，还少定义了 p,q
     * https://www.youtube.com/watch?v=yuMEpwt-YB4
     * @param list1
     * @param list2
     * @return
     */
    public static ListNode MergeEvan(ListNode list1, ListNode list2) {

        if (list1 == null) return list2;
        if (list2 == null) return list1;

        ListNode head3 = null;
        if (list1.val < list2.val) {
            head3 = list1;
            list1 = list1.next;
        } else {
            head3 = list2;
            list2 = list2.next;
        }

        ListNode cur = head3;
        System.out.println("cur = "+cur);
        System.out.println("head3 = "+head3);
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                cur.next = list1;
                list1 = list1.next;
            } else {
                cur.next = list2;
                list2 = list2.next;
            }

            cur = cur.next;
        }

        if (list1 == null) {
            cur.next = list2;
        } else {
            cur.next = list1;
        }

        return head3;
    }

    /**
     * 动画演示 #21 合并两个有序链表  看清怎么串起来的过程
     * https://leetcode-cn.com/problems/merge-two-sorted-lists/solution/dong-hua-yan-shi-21-he-bing-liang-ge-you-blxu/793057
     * @param list1
     * @param list2
     * @return
     */
    public static ListNode Merge(ListNode list1, ListNode list2) {
        ListNode head = new ListNode(-1, null);
        ListNode cur = head;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                cur.next = list1;
                list1 = list1.next;
            } else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        if (list1 != null)
            cur.next = list1;
        if (list2 != null)
            cur.next = list2;
        return head.next;
    }

    // 删除倒数第K个结点
    public static ListNode deleteLastKth(ListNode list, int k) {
        ListNode fast = list;
        int i = 1;
        while (fast != null && i < k) {
            fast = fast.next;
            ++i;
        }

        if (fast == null) return list;

        ListNode slow = list;
        ListNode prev = null;
        while (fast.next != null) {
            fast = fast.next;
            prev = slow;
            slow = slow.next;
        }

        if (prev == null) {
            list = list.next;
        } else {
            prev.next = prev.next.next;
        }
        return list;
    }

    // 求中间结点
    public static ListNode findMiddleNode(ListNode list) {
        if (list == null) return null;

        ListNode fast = list;
        ListNode slow = list;

        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    public static void printAll(ListNode list) {
        ListNode p = list;
        while (p != null) {
            System.out.print(p.val + " ");
            p = p.next;
        }
        System.out.println();
    }

    public static ListNode createNode(int value) {
        return new ListNode(value, null);
    }

    public static void main(String[] args) {
        LinkedListAlgo testInsert = new LinkedListAlgo();
//        Node head =  LinkedListAlgo.createNode(-1);

        ListNode node1 = LinkedListAlgo.createNode(1);
        ListNode node2 = LinkedListAlgo.createNode(2);
        ListNode node3 = LinkedListAlgo.createNode(3);
        ListNode node4 = LinkedListAlgo.createNode(4);
        ListNode node5 = LinkedListAlgo.createNode(5);
        ListNode node6 = LinkedListAlgo.createNode(6);
        ListNode node7 = LinkedListAlgo.createNode(7);

//        head.next = node1;
        node1.next = node3;
//        node3.next = node5;

        LinkedListAlgo.printAll(node1);

        node2.next = node4;
//        node4.next = node6;
//        node6.next = node7;
        LinkedListAlgo.printAll(node2);

        /**
         * 结论：nodeXX 持有 node1 引用后，修改 nodeXX 会导致 node1 一样被更改。log:
         *
         * nodeXX start:
         * 1 3 5
         * nodeXX set next. next is:
         * 2 4 6 7
         * nodeXX after set next. nodeXX is:
         * 1 2 4 6 7
         * nodeXX after set next. node1 is:
         * 1 2 4 6 7
         */
        /*Node nodeXX = node1;
        System.out.println("nodeXX start:");
        LinkedListAlgo.printAll(nodeXX);
        nodeXX.next = node2;
        System.out.println("nodeXX set next. next is:");
        LinkedListAlgo.printAll(node2);
        System.out.println("nodeXX after set next. nodeXX is:");
        LinkedListAlgo.printAll(nodeXX);
        System.out.println("nodeXX after set next. node1 is:");
        LinkedListAlgo.printAll(node1);*/

//        Node merged = LinkedListAlgo.mergeSortedLists(node1,node2);
//        Node merged = LinkedListAlgo.Merge(node1,node2);

        /**
         * 测试数据好理解一点，不要太长容易晕。
         * 1 3
         * 2 4
         */
        ListNode merged = LinkedListAlgo.MergeEvan(node1,node2);
        LinkedListAlgo.printAll(merged);

//        Node middleNode = LinkedListAlgo.findMiddleNode(node1);
//        System.out.println("middleNode from:");
//        LinkedListAlgo.printAll(middleNode);

//        Node reverseHead = LinkedListAlgo.reverse(node1);
//        Node reverseHead = LinkedListAlgo.reverse(node1);
        ListNode reverseHead = LinkedListAlgo.ReverseList(node1);
        LinkedListAlgo.printAll(reverseHead);

    }
}
