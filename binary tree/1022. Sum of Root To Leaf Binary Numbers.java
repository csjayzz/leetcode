/**
 * LeetCode 1022 - Sum of Root To Leaf Binary Numbers
 *
 * Problem:
 * Each node in the binary tree has value 0 or 1.
 * Every root-to-leaf path forms a binary number (root is MSB).
 * Return the sum of all root-to-leaf binary numbers.
 *
 * Approach (DFS + Binary Number Building):
 * 1. Traverse tree using DFS.
 * 2. Keep running value `val` for current path.
 * 3. At each node: val = 2 * val + root.val
 *    - Multiplying by 2 shifts bits left by one position.
 *    - Adding root.val appends current bit (0 or 1).
 * 4. If node is leaf, return `val` (one complete binary number).
 * 5. Otherwise, return leftSum + rightSum.
 *
 * Why this works:
 * - Every root-to-leaf path is visited exactly once.
 * - At a leaf, `val` equals decimal value of that binary path.
 * - Summing all leaf values gives required result.
 *
 * Time Complexity: O(n)
 * - Each node is processed once.
 *
 * Space Complexity: O(h)
 * - Recursion stack depth h (height of tree).
 *
 * Dry Run 1:
 * Input: root = [1,0,1,0,1,0,1]
 * Paths and values:
 * - 1 -> 0 -> 0  => (100)_2 = 4
 * - 1 -> 0 -> 1  => (101)_2 = 5
 * - 1 -> 1 -> 0  => (110)_2 = 6
 * - 1 -> 1 -> 1  => (111)_2 = 7
 * Total = 4 + 5 + 6 + 7 = 22
 *
 * Dry Run 2:
 * Input: root = [0]
 * - Single node is root and leaf.
 * - val = 2*0 + 0 = 0
 * Total = 0
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
    private int solve(TreeNode root, int val) {
        if (root == null) return 0;

        val = (2 * val) + root.val;

        if (root.left == null && root.right == null) {
            return val;
        }

        return solve(root.left, val) + solve(root.right, val);
    }

    public int sumRootToLeaf(TreeNode root) {
        return solve(root, 0);
    }
}
