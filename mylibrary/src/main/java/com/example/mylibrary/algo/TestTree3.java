package com.example.mylibrary.algo;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * 236. 二叉树的最近公共祖先
 *
 * 专门研究该问题的几个解法
 */
public class TestTree3 {

    public static void main(String args[]) {
        // 二叉树
        Integer[] nodeArray = {3,5,1,6,2,0,8,null,null,7,4};
        TreeNode root = TestTree.generateTreeNode(nodeArray);
        System.out.println("root = " + root);

        TestTree3 testTree = new TestTree3();
        TreeNode node5 = TestTree.preOrderTraversalSearch(root,5);
        System.out.println("node5:"+node5);
        TreeNode node1 = TestTree.preOrderTraversalSearch(root,1);
        System.out.println("node1:"+node1);
    }


}
