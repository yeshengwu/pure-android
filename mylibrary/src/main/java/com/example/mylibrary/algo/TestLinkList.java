package com.example.mylibrary.algo;

/**
 * 自己实现测试手写。技巧方法：
 * 一题多解，来加强理解：比如反转链表方法： 递归，虚拟节点头插法，当前指针加上个指针法。
 */
public class TestLinkList {

    public static void main(String[] args) throws CloneNotSupportedException {
        TestLinkList linkList = new TestLinkList();
        ListNode node = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(6);
        ListNode node7 = new ListNode(7);
        ListNode node8 = new ListNode(8);
        node.next = node2;
        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;

        int lastK = 2;
        ListNode theLastKNode = linkList.findTheLastKNode(node, lastK);
        System.out.println("lastK = " + lastK + " find theLastKNode = " + theLastKNode);

        ListNode head = null;

        head = linkList.ReverseList(node);
//        head = linkList.ReverseListRecursiveEvan(node);
        System.out.println("ReverseList = " + head);

        head = linkList.ReverseListRecursiveEvan(head);
        System.out.println("ReverseList reverse = " + head);

//     head = linkList.deleteLastKth(node, lastK);
//       System.out.println("deleteLastKth. lastK = " + lastK + " result list  = " + head);
//
        int k = 2;
        System.out.println("init k= " + k);
        while (k-->0) {
            System.out.println("IN while k= " + k);
        }

        while (k>0) {
            System.out.println("IN while k= " + k);
            k--;
        }

      /*  ListNode list1 = null;
        ListNode list2;*/

       /* ListNode list1;
        list1 = node;
        ListNode list2 = node2;
//        // 1 3 5
        node.next = node3;
        node3.next = node5;
        // 2 4 6
        node2.next = node4;
        node4.next = node6;*/

        ListNode list1 = node;
        ListNode node3Clone = (ListNode) node3.clone();
        ListNode node4Clone = (ListNode) node4.clone();
        ListNode node3Clone2 = (ListNode) node3.clone();
        ListNode node3Clone3 = (ListNode) node3.clone();
        ListNode node5Clone = (ListNode) node5.clone();
        ListNode node6Clone = (ListNode) node6.clone();
        ListNode list2 = node4Clone;
       /* // 1 2 4
        node.next = node2;
        node2.next = node4;
        // 1 3 4
        ListNode node3Clone = (ListNode) node3.clone();
        ListNode node4Clone = (ListNode) node4.clone();
        node1Clone.next = node3Clone;
        node3Clone.next = node4Clone;*/

        // 1 2 3
        node.next = node2;
        node2.next = node3;
        System.out.println("node1.hash = " + node.hashCode() + " clone1 = " + node.clone().hashCode() + " node1 clone again =" + node.clone().hashCode());
        // 3 3 3

        // 4 5 6
        node4Clone.next = node5Clone;
        node5Clone.next = node6Clone;

        ListNode headMerge = MergeEvan2(list1, list2);
        System.out.println("MergeEvan = " + headMerge);
//        ListNode headMerge2 = LinkedListAlgo.mergeSortedLists(list1, list2);
//        ListNode headMerge2 = LinkedListAlgo.Merge(list1, list2);
//        ListNode headMerge2 = LinkedListAlgo.MergeEvan(list1, list2);
//        System.out.println("MergeStandard = " + headMerge2);
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

        // 错误做法2： 死循环
        // ListNode dummyHead = new ListNode(-1);
        //        while ( head.next!= null) { // 这里为啥不是 head != null
        //            ListNode next = head.next;
        //            dummyHead.next = next;
        //            next.next = head;
        //            head = next;
        //        }
        //        return dummyHead.next;

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
        System.out.println("newHead = " + newHead + " param next = " + next);
        next.next = head;
        return newHead;

        // log: 得出结论：先找到最末尾节点（单个节点），然后递归的归这个阶段 将末尾这个节点往回指形成链表
        // newHead = ListNode{val=3, next=null} param next = ListNode{val=3, next=null}
        //newHead = ListNode{val=3, next=ListNode{val=2, next=null}} param next = ListNode{val=2, next=null}
        //ReverseList = ListNode{val=3, next=ListNode{val=2, next=ListNode{val=1, next=null}}}
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

    /**
     * 通俗解释：
     * 合并有序链表:像海盗登船游戏。 root相当于大本营，head头节点相当于
     * 那个上2条船的海盗，2条船哪边值小就上哪个 来回跳跃，然后2条船头节点像船长，一直后退。
     * root会随着head更新不断更新最新，这里很关键，想想为什么？ 用 1 3 5  ！2 4 6来挂断点观察 root head 变化
     * 原因：ListNode root = head 这句就是 大本营保证了 登船的锁链接到了船上，至于后面怎么连是 head节点的事情。
     */

    /**
     * 牛客网 提交代码发现有 16组testcase，想要通过,要考虑的场景case还是非常多的，不容易。
     * 技巧：用这个 1 2 3 | 4 5 6 来挂断点看 head root head.next的值 data next的变化情况。
     * <p>
     * https://www.nowcoder.com/questionTerminal/d8b6b4358f774294a89de2a6ac4d9337
     *
     * @param list1
     * @param list2
     * @return
     */
    public static ListNode MergeEvan2(ListNode list1, ListNode list2) {
        // my wrong: 通不过测试case： 1 2 3 | 3 4 5  and 1 2 3 | 3 3 3 and 1 2 3 | 4 5 6
        /*if (list1 == null) return list2;
        if (list2 == null) return list1;

        ListNode resultHead;
        ListNode h1n1;
        ListNode h2n1;
        if (list1.val <= list2.val) {
            resultHead = list1;
        } else {
            resultHead = list2;
        }

        while ((list1 != null) && (list2 != null)) {
            if (list1.val <= list2.val) {
                h1n1 = list1.next;
                list1.next = list2;
                list1 = h1n1;
            } else {
                h2n1 = list2.next;
                list2.next = list1;
                list2 = h2n1;
            }
        }
        return resultHead;*/

        //新建一个头节点，用来存合并的链表。
        ListNode head = new ListNode(-1);
        head.next = null;
        ListNode root = head;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                head.next = list1;
                head = list1;
                list1 = list1.next;
            } else {
                head.next = list2;
                head = list2;
                list2 = list2.next;
            }
        }
        //把未结束的链表连接到合并后的链表尾部
        if (list1 != null) {
            head.next = list1;
        }
        if (list2 != null) {
            head.next = list2;
        }
        return root.next;
    }

}
