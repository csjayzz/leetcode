/*
 * LeetCode 3819 - Rotate Non Negative Elements
 * Problem: Rotate only the non-negative elements in the array by k positions,
 *          while keeping negative elements in their original positions.
 *
 * Approach (Extract, Rotate, Reinsert):
 * Instead of rotating the entire array, we:
 * 1. Extract all non-negative elements into a separate list
 * 2. Rotate that list by k positions
 * 3. Reinsert the rotated elements back into the original positions
 *
 * Key Steps:
 * - Extract: Iterate through the array and collect all non-negative elements
 * - Normalize k: Since rotation is circular, use k = k % size to avoid redundant rotations
 * - Rotate: Use circular indexing to access elements after rotation
 *   - Starting index becomes k (moves first element to position k)
 *   - Access elements cyclically: (k + i) % size
 * - Reinsert: Place rotated elements back at positions where non-negative elements were
 *
 * Examples:
 * - nums = [1, 2, -3, 4], k = 2
 *   Non-negative: [1, 2, 4], rotate by 2 → [2, 4, 1]
 *   Result: [2, 4, -3, 1]
 *
 * - nums = [-1, -2, 3], k = 1
 *   Non-negative: [3], rotate by 1 → [3]
 *   Result: [-1, -2, 3]
 *
 * Time Complexity: O(n) - single pass to extract, reinsert, and access rotated elements
 * Space Complexity: O(m) where m is the count of non-negative elements
 */

class Solution {
    public int[] rotateElements(int[] nums, int k) {
        // Step 1: Extract all non-negative elements
        List<Integer> ls = new ArrayList<>();
        for (int num : nums) {
            if (num >= 0) {
                ls.add(num);
            }
        }

        // Step 2: Handle edge case - no non-negative elements
        if (ls.isEmpty()) {
            return nums; // Return original array unchanged
        }

        // Step 3: Normalize rotation amount
        int m = ls.size();
        k = k % m; // Avoid unnecessary full rotations

        // Step 4: Reinsert rotated non-negative elements back
        int idx = k; // Start from position k (rotated position)
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                // Place the rotated element and move to the next
                nums[i] = ls.get(idx);
                idx = (idx + 1) % m; // Wrap around when reaching the end
            }
        }

        return nums;
    }
}

/*
 * Visual Example: nums = [1, 2, -3, 4, -5, 6], k = 2
 *
 * Step 1: Extract non-negative
 * Original:       [1,  2, -3,  4, -5,  6]
 * Extracted:      [1,  2,      4,      6]  (positions: 0, 1, 3, 5)
 *
 * Step 2: Normalize k
 * k = 2 % 4 = 2
 *
 * Step 3: Rotate (circular indexing from position k=2)
 * Original list:  [1, 2, 4, 6] (indices: 0, 1, 2, 3)
 * Start from idx=2: [4, 6, 1, 2] (read 2, 3, 0, 1)
 *
 * Step 4: Reinsert
 * Position 0 (num=1 >= 0): nums[0] = ls.get(2) = 4
 * Position 1 (num=2 >= 0): nums[1] = ls.get(3) = 6
 * Position 2 (num=-3 < 0): skip (keep -3)
 * Position 3 (num=4 >= 0): nums[3] = ls.get(0) = 1
 * Position 4 (num=-5 < 0): skip (keep -5)
 * Position 5 (num=6 >= 0): nums[5] = ls.get(1) = 2
 *
 * Result: [4, 6, -3, 1, -5, 2]
 *
 * Correctness: Non-negative elements [1,2,4,6] are rotated left by 2 → [4,6,1,2]
 *             They occupy the same positions in the original array
 *             Negative elements remain unchanged
 */
