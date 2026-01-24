/*
 * LeetCode 503 - Next Greater Element II
 * Problem: https://leetcode.com/problems/next-greater-element-ii/
 *
 * Approach (Brute Force - Circular Scan):
 * Since the array is circular, for each element nums[i], we look to the right
 * and wrap around to the beginning if needed.
 *
 * Steps:
 * 1. Initialize answer array with -1 (default if no greater element exists).
 * 2. For each index i, start checking from (i+1) % n.
 * 3. Move forward circularly until:
 *    - We find an element greater than nums[i], or
 *    - We come back to i (full circle completed).
 * 4. If found, store it in ans[i].
 *
 * This directly simulates the problem definition but is inefficient.
 *
 * Time Complexity: O(n^2)
 * Space Complexity: O(1) (excluding output array)
 */

class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1); // Default if no greater element found

        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;

            // Try at most n - 1 steps (avoid comparing with itself)
            while (j != i) {
                if (nums[j] > nums[i]) {
                    ans[i] = nums[j];
                    break;
                }
                j = (j + 1) % n;
            }
        }

        return ans;
    }
}

/*
 * Topic Classification:
 * This problem comes under:
 * - Monotonic Stack (main intended topic)
 * - Stack
 * - Circular Array
 *
 * Current Solution: Brute Force / Simulation approach
 * Optimal Solution: Monotonic decreasing stack with O(n) complexity
 */

/*https://leetcode.com/problems/next-greater-element-ii/
Approach (Optimal - Monotonic Stack + Circular Array):
We use a monotonic decreasing stack that stores indices of elements
whose next greater element has not been found yet.
Since the array is circular, we simulate traversing it twice
(from 0 to 2*n - 1) using index = i % n.
Steps:
1. Initialize answer array with -1.
2. Traverse from i = 0 to 2*n - 1:
- Let idx = i % n.
- While stack is not empty and nums[idx] > nums[stack.peek()]:
→ nums[idx] is the next greater for stack.peek()
→ pop and update answer.
- If i < n, push idx into stack (only push original indices once).
3. After traversal, remaining indices in stack have no greater element,
so their answer stays -1.
This works because each index is pushed and popped at most once.
Time Complexity: O(n)
Space Complexity: O(n)*/