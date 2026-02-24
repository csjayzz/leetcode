/**
 * LeetCode 129 - Sum Root to Leaf Numbers
 *
 * Problem:
 * You are given a binary tree where each node stores a digit (0 to 9).
 * Every root-to-leaf path forms a number.
 * Return the total sum of all root-to-leaf numbers.
 *
 * Example:
 * Path 1 -> 2 -> 3 forms number 123.
 *
 * Approach (DFS + Number Building):
 * 1. Use DFS traversal from root.
 * 2. Keep a running value `val` that represents digits formed so far.
 * 3. At each node: val = val * 10 + node.val
 *    - This shifts previous digits left and appends current digit.
 * 4. If node is a leaf, return val (one complete number formed).
 * 5. Otherwise, return sum from left subtree + right subtree.
 *
 * Why this works:
 * - Each root-to-leaf path is processed once.
 * - At leaf, `val` is exactly the number represented by that path.
 * - Summing leaf returns gives total of all path numbers.
 *
 * Time Complexity: O(n)
 * - Every node is visited once.
 *
 * Space Complexity: O(h)
 * - Recursion stack height h (tree height).
 * - Worst case O(n), balanced case O(log n).
 *
 * Dry Run 1:
 * Input: root = [1,2,3]
 *
 * Start solve(1, 0)
 * - Node 1: val = 0*10 + 1 = 1
 *   - Left child 2: val = 1*10 + 2 = 12 (leaf) -> return 12
 *   - Right child 3: val = 1*10 + 3 = 13 (leaf) -> return 13
 * Total = 12 + 13 = 25
 *
 * Dry Run 2:
 * Input: root = [4,9,0,5,1]
 *
 * Start solve(4, 0)
 * - Node 4: val = 4
 *   - Left node 9: val = 49
 *     - Left node 5: val = 495 (leaf) -> return 495
 *     - Right node 1: val = 491 (leaf) -> return 491
 *   - Right node 0: val = 40 (leaf) -> return 40
 * Total = 495 + 491 + 40 = 1026
 */

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    private int solve(TreeNode root, int val) {
        if (root == null) return 0;

        val = (10 * val) + root.val;

        if (root.left == null && root.right == null) return val;

        return solve(root.left, val) + solve(root.right, val);
    }

    public int sumNumbers(TreeNode root) {
        return solve(root, 0);
    }
}
