package com.example.mylibrary.algo;

import java.util.ArrayList;
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

        TestTree2 testTree = new TestTree2();
        TreeNode node2 = TestTree.preOrderTraversalSearch(root,2);
        System.out.println("node2:"+node2);
        TreeNode node8 = TestTree.preOrderTraversalSearch(root,8);
        System.out.println("node8:"+node8);
        System.out.println("lowestCommonAncestor:");
        System.out.println(testTree.lowestCommonAncestor(root, node2, node8));
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


}
