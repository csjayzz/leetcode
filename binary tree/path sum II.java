import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 113 - Path Sum II
 *
 * Problem:
 * Return all root-to-leaf paths where sum of node values equals targetSum.
 * Each path should be stored as a list of node values.
 *
 * Approach (DFS + Backtracking):
 * 1. Traverse tree using DFS.
 * 2. Keep:
 *    - `sum`: current path sum.
 *    - `temp`: current path nodes.
 * 3. At each node:
 *    - Add node value to `sum`.
 *    - Add node value to `temp`.
 * 4. If node is leaf:
 *    - If sum == targetSum, copy `temp` into `result`.
 *    - Backtrack: remove last element from `temp` and return.
 * 5. Recurse left and right.
 * 6. After both calls, backtrack (remove current node from `temp`).
 *
 * Why backtracking is required:
 * - Same `temp` list is reused across recursive calls.
 * - If we do not remove the last added node when returning,
 *   sibling paths will contain wrong extra values.
 *
 * Time Complexity: O(n * h)
 * - n nodes visited.
 * - Copying a valid path takes O(h).
 * - In worst case many valid leaves, total copy cost can add up.
 *
 * Space Complexity: O(h) recursion + O(k * h) output
 * - h = tree height
 * - k = number of valid paths
 *
 * Dry Run (Example 1):
 * Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
 *
 * Valid root-to-leaf paths checked:
 * - 5 -> 4 -> 11 -> 7 = 27 (not added)
 * - 5 -> 4 -> 11 -> 2 = 22 (added)
 * - 5 -> 8 -> 13 = 26 (not added)
 * - 5 -> 8 -> 4 -> 5 = 22 (added)
 * - 5 -> 8 -> 4 -> 1 = 18 (not added)
 *
 * Final result:
 * [[5,4,11,2], [5,8,4,5]]
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
    List<List<Integer>> result = new ArrayList<>();

    private void solve(TreeNode root, int sum, ArrayList<Integer> temp, int targetSum) {
        if (root == null) return;

        sum += root.val;
        temp.add(root.val);

        if (root.right == null && root.left == null) {
            if (sum == targetSum) {
                result.add(new ArrayList<>(temp));
            }
            temp.remove(temp.size() - 1);
            return;
        }

        solve(root.left, sum, temp, targetSum);
        solve(root.right, sum, temp, targetSum);

        temp.remove(temp.size() - 1);
    }

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        ArrayList<Integer> temp = new ArrayList<>();
        solve(root, 0, temp, targetSum);
        return result;
    }
}
