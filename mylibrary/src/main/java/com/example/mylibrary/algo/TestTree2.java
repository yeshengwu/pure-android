package com.example.mylibrary.algo;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree/solution/er-cha-sou-suo-shu-de-zui-jin-gong-gong-zu-xian-26/
 * 235.二叉搜索树的最近公共祖先
 *
 * 专门研究该问题的几个解法
 */
public class TestTree2 {
    public static void main(String args[]) {
        // 二叉搜索树
        Integer[] nodeArray = {6, 2, 8, 0, 4, 7, 9, null, null, 3, 5};
        TreeNode root = TestTree.generateTreeNode(nodeArray);
        System.out.println("root = " + TestTree.PrintFromTopToBottom(root));
        System.out.println("root levelOrder = " + TestTree.levelOrder(root));

        // Integer[] nodeArray = {6, 5, 12, 4, 8, 11, 13, null, null, 7, 9}; // 结果不是 二叉搜索树
        //  违反了 二叉搜索树性质：
        // 若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值； 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值； 它的左、右子树也分别为二叉排序树。
        // 原因是 5 的 右节点 8 大于根节点6。 性质规定： 左子树上所有结点的值均小于它的根结点的值
        System.out.println("isValidBST = "+isValidBST(root));

        TreeNode node2 = TestTree.preOrderTraversalSearch(root,2);
        System.out.println("node2 levelOrder = " + TestTree.levelOrder(node2));
        TreeNode node4 = TestTree.preOrderTraversalSearch(root,4);
        System.out.println("node4 levelOrder = " + TestTree.levelOrder(node4));
        TreeNode node8 = TestTree.preOrderTraversalSearch(root,8);
        System.out.println("node8 levelOrder = " + TestTree.levelOrder(node8));

        TreeNode lowest8 = TestTree2.lowestCommonAncestor(root, node2, node8);
        System.out.println("lowest levelOrder = " + TestTree.levelOrder(lowest8));

        TreeNode lowest4 = TestTree2.lowestCommonAncestor_2(root, node2, node4);
        System.out.println("lowest4 levelOrder = " + TestTree.levelOrder(lowest4));

        System.out.println("before insert:" + TestTree.levelOrder(root));
        BTreePrinter.printNode(root);
        TreeNode nodeInsert = new TreeNode(1);
//        TreeNode insertResult = TestTree2.insertIntoBST(root, nodeInsert);
        TreeNode insertResult = TestTree2.insertIntoBST2(root, nodeInsert);
        // 关键日志：
        //IN right: inserted = 1 root = 0
        //IN left: inserted = 0 root = 2
        //IN left: inserted = 2 root = 6
        System.out.println("after insert:" + TestTree.levelOrder(insertResult));
        BTreePrinter.printNode(insertResult);
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> path_p = getPath(root, p);
        List<TreeNode> path_q = getPath(root, q);
        TreeNode ancestor = null;
        for (int i = 0; i < path_p.size() && i < path_q.size(); ++i) {
            if (path_p.get(i) == path_q.get(i)) {
                ancestor = path_p.get(i);
            } else {
                break;
            }
        }
        return ancestor;
    }

    public static List<TreeNode> getPath(TreeNode root, TreeNode target) {
        List<TreeNode> path = new ArrayList<TreeNode>();
        TreeNode node = root;
        while (node != target) {
            path.add(node);
            if (target.val < node.val) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        path.add(node);
        return path;
    }


    public static TreeNode lowestCommonAncestor_2(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode ancestor = root;
        while (true){
            if (ancestor.val>p.val && ancestor.val>q.val) {
                ancestor = ancestor.left;
            } else if (ancestor.val<p.val && ancestor.val<q.val){
                ancestor = ancestor.right;
            } else {
                break;
            }
        }
        return ancestor;
    }

    /**
     * futu2 interview
     *
     * @param root
     * @param p
     * @return
     */
    public static TreeNode insertIntoBSTEvan(TreeNode root, TreeNode p) {
        // my wrong
        if (root == null) {
            return null;
        }
        if (p.val > root.val) {
            // 右边插
            insertIntoBSTEvan(root.right, p);
            TreeNode temp = root;
            root.right = p;
            p.right = temp;
        } else {
            // 左边插
            insertIntoBSTEvan(root.left, p);
            TreeNode temp = root;
            root.left = p;
            p.left = temp;
        }
        return root;
    }

    public static TreeNode insertIntoBSTEvan_2(TreeNode root, TreeNode p) {
        if (root == null) {
            return new TreeNode(p.val);
        }
        if (p.val > root.val) {
            // 右边插
            TreeNode temp = insertIntoBSTEvan_2(root.right, p);
            root.right = p;
            p.right = temp;
//            root.right = temp;
        } else {
            // 左边插
            TreeNode temp = insertIntoBSTEvan_2(root.left, p);
            root.left = p;
            p.left = temp;
//            root.left = temp;
        }
        return root;
    }

    public static TreeNode insertIntoBST(TreeNode root, TreeNode p) {
        if (root == null) {
            return new TreeNode(p.val);
        }
        if (p.val > root.val) { // 右边插
            root.right = insertIntoBST(root.right, p);
        } else { // 左边插
            root.left = insertIntoBST(root.left, p);
        }
        return root;
    }

    /**
     *  evan 手写实现
     *
     * @param root
     * @param p
     * @return
     */
    public static TreeNode insertIntoBST2(TreeNode root, TreeNode p) {
        if (root == null) {
            return new TreeNode(p.val);
        }

        if (root.val < p.val) {
            TreeNode inserted = insertIntoBST2(root.right,p); // 这里是关键，不要跟上面一样
            System.out.println("IN right: inserted = "+inserted.val+ " root = "+root.val);
            // 简化成   root.right = insertIntoBST2(root.right, p); 就懵逼了。
            root.right = inserted;
        } else if (root.val > p.val) {
            TreeNode inserted = insertIntoBST2(root.left,p);
            System.out.println("IN left: inserted = "+inserted.val+ " root = "+root.val);
            root.left = inserted;
        }

        return root;
    }

    /**
     * https://leetcode-cn.com/problems/validate-binary-search-tree/solution/yan-zheng-er-cha-sou-suo-shu-by-leetcode-solution/
     * 98. 验证二叉搜索树
     * @param root
     * @return
     */
    public static boolean isValidBST(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        double inorder = -Double.MAX_VALUE;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // 如果中序遍历得到的节点的值小于等于前一个 inorder，说明不是二叉搜索树
            if (root.val <= inorder) {
                return false;
            }
            inorder = root.val;
            root = root.right;
        }
        return true;
    }


}
