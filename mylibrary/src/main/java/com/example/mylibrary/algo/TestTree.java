package com.example.mylibrary.algo;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class TestTree {
    public static class TreeNode {
        int val;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    /**
     * 关键字：   leetcode 二叉树 生成
     * <p>
     * 数组层序遍历方式生成二叉树
     * https://blog.csdn.net/baoxin1100/article/details/108025393
     * LeetCode(力扣)题目中二叉树的如何生成？根据给定顺序列表生成二叉树（python）
     * https://blog.csdn.net/u012957549/article/details/105501036/  Leetcode 数组生成二叉树的工具
     * <p>
     *
     * @param nums Integer[] nodeArray = [3,9,20,null,null,15,7];
     * @return
     */
    public static TreeNode generateTreeNode(Integer[] nums) {
        if (nums == null || nums.length == 0)
            return null;
        int len = nums.length;
        int index = 0;
        TreeNode head = new TreeNode(nums[index]);
        Deque<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.offer(head);
        TreeNode cur;
        while (index < len) {
            index++;
            if (index >= len) return head;
            cur = nodeQueue.poll();
            Integer left = nums[index];
            if (left != null) {
                cur.left = new TreeNode(left);
                nodeQueue.offer(cur.left);
            }

            index++;
            if (index >= len) return head;
            Integer right = nums[index];
            if (right != null) {
                cur.right = new TreeNode(right);
                nodeQueue.offer(cur.right);
            }
        }
        return head;
    }

    /**
     * https://labuladong.gitee.io/algo/%E7%AE%97%E6%B3%95%E6%80%9D%E7%BB%B4%E7%B3%BB%E5%88%97/BFS%E6%A1%86%E6%9E%B6.html
     * BFS 算法框架套路详解
     * 二、二叉树的最小高度
     *
     * @param root
     * @return
     */
    int minDepth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        // root 本身就是一层，depth 初始化为 1
        int depth = 1;

        while (!q.isEmpty()) {
            int sz = q.size();
            /* 将当前队列中的所有节点向四周扩散 */
            for (int i = 0; i < sz; i++) {
                TreeNode cur = q.poll();
                /* 判断是否到达终点 */
                if (cur.left == null && cur.right == null)
                    return depth;
                /* 将 cur 的相邻节点加入队列 */
                if (cur.left != null)
                    q.offer(cur.left);
                if (cur.right != null)
                    q.offer(cur.right);
            }
            /* 这里增加步数 */
            depth++;
        }
        return depth;
    }

    /**
     * 前序遍历：根结点 ---> 左子树 ---> 右子树
     * 根 左 右
     * https://www.cnblogs.com/bigsai/p/11393609.html 二叉树前序遍历
     *
     * @param root
     */
    private void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val + " ");// 当前节点
        preOrder(root.left);
        preOrder(root.right);

        /*if (root != null) {
            System.out.print(root.val + " ");// 当前节点
            preOrder(root.left);
            preOrder(root.right);
        }*/
    }

    /**
     * 中序遍历：
     * 左 根 右
     *
     * @param root
     */
    private void midOrder(TreeNode root) {
        if (root != null) {
            // my wrong
           /* System.out.print(root.left.val + " ");// 当前节点
            preOrder(root);
            preOrder(root.right);*/

            midOrder(root.left);
            System.out.print(root.val + " ");// 当前节点
            midOrder(root.right);
        }
    }

    /**
     * 后序遍历：
     * 左 右 根
     *
     * @param root
     */
    private void postOrder(TreeNode root) {
        if (root != null) {
            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.val + " ");// 当前节点
        }
    }

    public static void main(String args[]) {
        TestTree testTree = new TestTree();
        /**
         *     3
         *    / \
         *   9  20
         *      / \
         *     15  7
         */
//        Integer[] nodeArray = [3,9,20,null,null,15,7];
//        Integer[] nodeArray = {3, 9, 20, null, null, 15, 7};

        /**
         *       3
         *    /    \
         *   9       8
         *  / \     /  \
         *  1  13   7  12
         *  \  /\      /
         *  2 4  5    15
         *             \
         *             18
         */
        Integer[] nodeArray = {3, 9, 8, 1, 13, 7, 12, null, 2, 4, 5, null, null, 15, null
                , null, null, null, null, null, null, null, 18}; // 18 前面 7个 null 是 2 4 5 15 下面的，像 2
        // 这个节点的兄弟下面不需要填充 null， 因为 2的兄弟都不在填个jj
        TreeNode root = TestTree.generateTreeNode(nodeArray);
        System.out.println("root = " + root);

        /*TreeNode root = new TreeNode(3);
        TreeNode node9 = new TreeNode(9);
        TreeNode node20 = new TreeNode(20);
        root.left = node9;
        root.right = node20;

        TreeNode node15 = new TreeNode(15);
        TreeNode node7 = new TreeNode(7);
        node20.left = node15;
        node20.right = node7;*/

        int miniDepth = testTree.minDepth(root);
        System.out.println("miniDepth = " + miniDepth);

        System.out.println("preOrder:");
        testTree.preOrder(root);
        System.out.println("");
        System.out.println("midOrder:");
        testTree.midOrder(root);
        System.out.println("");
        System.out.println("postOrder:");
        testTree.postOrder(root);

        System.out.println("");
        System.out.println("PrintFromTopToBottom:");
        System.out.println(PrintFromTopToBottom(root));
        System.out.println(PrintFromTopToBottom2(root));

    }

    /**
     * http://www.cyc2018.xyz/%E7%AE%97%E6%B3%95/%E5%89%91%E6%8C%87%20Offer%20%E9%A2%98%E8%A7%A3/32.1%20%E4%BB%8E%E4%B8%8A%E5%BE%80%E4%B8%8B%E6%89%93%E5%8D%B0%E4%BA%8C%E5%8F%89%E6%A0%91.html#%E9%A2%98%E7%9B%AE%E6%8F%8F%E8%BF%B0
     * 32.1 从上往下打印二叉树
     *
     * @param root
     * @return
     */
    public static ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        ArrayList<Integer> ret = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int cnt = queue.size();
            while (cnt-- > 0) {
                TreeNode t = queue.poll();
                if (t == null)
                    continue;
                ret.add(t.val);
                queue.add(t.left);
                queue.add(t.right);
            }
        }
        return ret;
    }

    public static ArrayList<Integer> PrintFromTopToBottom2(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();

        queue.add(root);
        while (!queue.isEmpty()) {
            int count = queue.size();
            for (int i = 0; i < count; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    continue;
                }
                result.add(node.val);
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        return result;
    }
}
