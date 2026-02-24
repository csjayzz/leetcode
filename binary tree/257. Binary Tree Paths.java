import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 257 - Binary Tree Paths
 *
 * Problem:
 * Return all root-to-leaf paths in a binary tree.
 * A leaf is a node with no left and right child.
 *
 * Approach (DFS):
 * 1. Traverse tree using DFS.
 * 2. Keep current path as a string.
 * 3. At each node, append current node value to path:
 *    - If path is empty: path = "value"
 *    - Else: path = path + "->" + value
 * 4. If node is a leaf, add built path to result.
 * 5. Recurse for left and right child.
 *
 * Why this works:
 * - DFS explores every root-to-leaf route.
 * - At each leaf, current path string exactly represents that route.
 *
 * Time Complexity: O(n * h)
 * - n = number of nodes, h = height.
 * - String concatenation along paths contributes extra cost.
 *
 * Space Complexity: O(h) recursion + O(total output size)
 *
 * Dry Run 1:
 * Input: root = [1,2,3,null,5]
 * - Start at 1: path = "1"
 *   - Go left to 2: path = "1->2"
 *     - Go right to 5: path = "1->2->5" (leaf) -> add
 *   - Go right to 3: path = "1->3" (leaf) -> add
 * Output: ["1->2->5", "1->3"]
 *
 * Dry Run 2:
 * Input: root = [1]
 * - Node 1 is root and leaf, path = "1" -> add
 * Output: ["1"]
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
    List<String> result = new ArrayList<>();

    private void solve(TreeNode root, String path) {
        if (root == null) return;

        if (path.isEmpty()) {
            path = String.valueOf(root.val);
        } else {
            path = path + "->" + root.val;
        }

        if (root.left == null && root.right == null) {
            result.add(path);
            return;
        }

        solve(root.left, path);
        solve(root.right, path);
    }

    public List<String> binaryTreePaths(TreeNode root) {
        solve(root, "");
        return result;
    }
}


//better way to write 
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
    public List<String> binaryTreePaths(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        helper(root, sb, res);
        return res;
    }
    private void helper(TreeNode root, StringBuilder sb, List<String> res) {
        int len = sb.length();
        if (len != 0) {
            sb.append("->");
        }
        sb.append(root.val);

        if (root.left == null && root.right == null) {
            res.add(sb.toString());
        }

        if (root.left != null) {
            helper(root.left, sb, res);
            // sb.setLength(len);
        }
        if (root.right != null) {
            helper(root.right, sb, res);
            // sb.setLength(len);
        }
        sb.setLength(len);
    }
}
/**
It's a all paths problem -> backtracking

What do we pass down?
    res
    path

what do we do on each node?
    for each node:
        case1: root
            add root.val
        case2: else
            add -> + root.val

When do we have the result?
    reach to the leaf node
 */