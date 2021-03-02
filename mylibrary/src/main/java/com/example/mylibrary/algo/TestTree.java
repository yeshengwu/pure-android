package com.example.mylibrary.algo;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 树的一些基本功能：生成，遍历，查找
 * TestTree2 TestTree3可以从这里调用基本功能。
 */
public class TestTree {

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

    private void preOrderXX(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val + " ");// 当前节点
        preOrderXX(root.right);
        preOrderXX(root.left);

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
//        System.out.println(PrintFromTopToBottom2(root));
        System.out.println("");
        System.out.println("getRightView:");
        System.out.println(getRightSideView(root));
        System.out.println("");
        System.out.println("rightSideView:");
        System.out.println(rightSideView(root));
        System.out.println("rightSideViewDFS:");
        System.out.println(testTree.rightSideViewDFS(root));
        System.out.println("invertTree:");
        testTree.invertTree(root);
        System.out.println(PrintFromTopToBottom(root));
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

    /**
     * 头 增长
     * 层序遍历，BFS
     * 关键注意点：每层遍历不空的节点才放入，然后 遍历时将 当前层的最后一个节点放入结果列表。
     * https://leetcode-cn.com/problems/binary-tree-right-side-view/comments/
     *
     * @param node
     * @return
     */
    public static List<Integer> getRightSideView(TreeNode node) {
        List<Integer> result = new ArrayList<>();
        if (node == null) {
            return new ArrayList<>();
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            int count = queue.size();
            for (int i = 0; i < count; i++) {
                TreeNode item = queue.poll();

                if (item == null) {
                    continue;
                }

                if (item.left != null) {
                    queue.offer(item.left);
                }
                if (item.right != null) {
                    queue.offer(item.right);
                }
                if (i == count - 1) {  //将当前层的最后一个节点放入结果列表
                    result.add(item.val);
                }
            }
        }
        return result;
    }

    public static List<Integer> rightSideView(TreeNode root) {
        Map<Integer, Integer> rightmostValueAtDepth = new HashMap<Integer, Integer>();
        int max_depth = -1;

        Queue<TreeNode> nodeQueue = new LinkedList<TreeNode>();
        Queue<Integer> depthQueue = new LinkedList<Integer>();
        nodeQueue.add(root);
        depthQueue.add(0);

        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();
            int depth = depthQueue.remove();

            if (node != null) {
                // 维护二叉树的最大深度
                max_depth = Math.max(max_depth, depth);

                // 由于每一层最后一个访问到的节点才是我们要的答案，因此不断更新对应深度的信息即可
                rightmostValueAtDepth.put(depth, node.val);

                nodeQueue.add(node.left);
                nodeQueue.add(node.right);
                depthQueue.add(depth + 1);
                depthQueue.add(depth + 1);
            }
        }

        List<Integer> rightView = new ArrayList<Integer>();
        for (int depth = 0; depth <= max_depth; depth++) {
            rightView.add(rightmostValueAtDepth.get(depth));
        }

        return rightView;
    }

    List<Integer> res = new ArrayList<>();

    /**
     * DFS
     * https://leetcode-cn.com/problems/binary-tree-right-side-view/comments/
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideViewDFS(TreeNode root) {
        dfs(root, 0); // 从根节点开始访问，根节点深度是0
        return res;
    }

    private void dfs(TreeNode root, int depth) {
        if (root == null) {
            return;
        }
        // 先访问 当前节点，再递归地访问 右子树 和 左子树。
        if (depth == res.size()) {   // 如果当前节点所在深度还没有出现在res里，说明在该深度下当前节点是第一个被访问的节点，因此将当前节点加入res中。
            res.add(root.val);
        }
        depth++;
        dfs(root.right, depth);
        dfs(root.left, depth);
    }

    /**
     * 226. 翻转二叉树
     * https://leetcode-cn.com/problems/invert-binary-tree/comments/
     * @param root
     * @return
     */
    public static TreeNode invertTree(TreeNode root){
        if (root == null) {
            return null;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    /**
     * 前序遍历查询
     *
     * @param node  根节点
     * @param value
     * @return
     */
    public static TreeNode preOrderTraversalSearch(TreeNode node, int value) {
        //判断当前节点是不是为空，若不为空
        if (node != null) {
            //则判断需要查询的排名，与当前节点的排名是否相等
            if (node.val == value) {
                return node;
            }
        }
        TreeNode resultNode = null;
        //向左遍历，如果左子节点不为空
        if (node.left != null) {
            resultNode = preOrderTraversalSearch(node.left, value);
            if (resultNode != null) {
                return resultNode;
            }
        }
        //向右遍历，如果左子节点不为空
        if (node.right != null) {
            resultNode = preOrderTraversalSearch(node.right, value);
            if (resultNode != null) {
                return resultNode;
            }
        }
        return resultNode;
    }

}
