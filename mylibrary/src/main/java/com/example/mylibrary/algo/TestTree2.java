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
        System.out.println("root = " + root);

        // Integer[] nodeArray = {6, 5, 12, 4, 8, 11, 13, null, null, 7, 9}; // 结果不是 二叉搜索树
        //  违反了 二叉搜索树性质：
        // 若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值； 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值； 它的左、右子树也分别为二叉排序树。
        // 原因是 5 的 右节点 8 大于根节点6。 性质规定： 左子树上所有结点的值均小于它的根结点的值
        System.out.println("isValidBST = "+isValidBST(root));

        TestTree2 testTree = new TestTree2();
        TreeNode node2 = TestTree.preOrderTraversalSearch(root,7);
        System.out.println("node2:"+node2);
        TreeNode node8 = TestTree.preOrderTraversalSearch(root,9);
        System.out.println("node8:"+node8);
//        System.out.println("lowestCommonAncestor:");
//        System.out.println(testTree.lowestCommonAncestor(root, node2, node8));
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
