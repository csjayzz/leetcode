/**
 * LeetCode 112 - Path Sum
 *
 * Problem:
 * Given the root of a binary tree and an integer targetSum,
 * return true if there exists a root-to-leaf path such that
 * the sum of all node values in that path equals targetSum.
 *
 * Leaf node:
 * A node with no left child and no right child.
 *
 * Approach (DFS + Running Sum):
 * 1. Start DFS from root with current sum = 0.
 * 2. At each node, add node.val to current sum.
 * 3. If node is a leaf, check if current sum == targetSum.
 *    - If yes, return true.
 *    - If no, return false.
 * 4. If node is not a leaf, recursively check left and right subtree.
 * 5. If either side returns true, answer is true.
 *
 * Why this works:
 * - Every root-to-leaf path is explored exactly once.
 * - At leaf level, we validate the exact path sum.
 * - Using OR ensures we stop early as soon as one valid path is found.
 *
 * Time Complexity: O(n)
 * - Each node is visited once.
 *
 * Space Complexity: O(h)
 * - Recursive call stack height h (tree height).
 * - Worst case skewed tree: O(n), balanced tree: O(log n).
 *
 * Dry Run:
 * Input:
 * root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
 *
 * DFS flow (node, runningSum):
 * - Start at 5: sum = 0 + 5 = 5
 *   - Go left to 4: sum = 5 + 4 = 9
 *     - Go left to 11: sum = 9 + 11 = 20
 *       - Go left to 7: sum = 20 + 7 = 27 (leaf)
 *         -> 27 != 22, return false
 *       - Go right to 2: sum = 20 + 2 = 22 (leaf)
 *         -> 22 == 22, return true
 *
 * Because one path returned true, final answer = true.
 * Valid path: 5 -> 4 -> 11 -> 2
 */

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    private boolean solve(TreeNode root, int targetSum, int val) {
        if (root == null) return false;

        val = val + root.val;

        if (root.left == null && root.right == null) {
            return val == targetSum;
        }

        return solve(root.left, targetSum, val) || solve(root.right, targetSum, val);
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        return solve(root, targetSum, 0);
    }
}
