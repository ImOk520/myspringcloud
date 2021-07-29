package leetCode;

import cn.hutool.core.lang.Console;

/**
 * 二叉树的最大深度
 *
 * 描述：给定一个二叉树，找出其最大深度。
 *      二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *      说明: 叶子节点是指没有子节点的节点。
 *
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回它的最大深度 3 。
 *
 * 链接：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree
 */
public class MaximumDepthOfBinaryTree {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        Console.log(maxDepth(root));
    }

    /**
     * 如果我们知道了左子树和右子树的最大深度 l 和 r，那么该二叉树的最大深度即为 max(l,r)+1
     * 而左子树和右子树的最大深度又可以以同样的方式进行计算。
     * 因此我们可以用「深度优先搜索」的方法来计算二叉树的最大深度。
     * 具体而言，在计算当前二叉树的最大深度时，可以先递归计算出其左子树和右子树的最大深度，
     * 然后在 O(1)O(1) 时间内计算出当前二叉树的最大深度。
     * 递归在访问到空节点时退出。
     */
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int leftHeight = maxDepth(root.left);
            int rightHeight = maxDepth(root.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }
}


