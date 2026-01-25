/*
 * LeetCode 3818 - Minimum Prefix Removal to Make Array Strictly Increasing
 * Problem: Find the minimum length of prefix to remove so that the remaining array
 *          is strictly increasing.
 *
 * Approach (Greedy - Backward Scan):
 * The key insight is that we want to keep the longest suffix (not prefix) that is
 * strictly increasing. This means we should:
 *
 * 1. Start from the second last element and work backwards
 * 2. Keep checking if consecutive elements are in strictly increasing order
 * 3. Stop when we find a violation (nums[i] >= nums[i+1])
 * 4. The index where we stop tells us the minimum prefix to remove
 *
 * Why This Works:
 * - A strictly increasing suffix is the optimal subarray to keep
 * - The longest such suffix starting from the rightmost elements is unique
 * - All elements before this suffix must be removed
 * - Since we process from right to left, we find the longest increasing suffix
 *
 * Examples:
 * - nums = [1, 2, 3, 4]: All increasing → i = -1 → return 0 (no removal)
 * - nums = [5, 1, 2, 3]: nums[2] < nums[3] is true, but nums[1] < nums[2] is true,
 *   nums[0] < nums[1] is false → return 1 (remove first element)
 * - nums = [5, 6, 1, 2]: nums[2] < nums[3] is true, nums[1] < nums[2] is false
 *   → return 2 (remove first two elements to get [1, 2])
 *
 * Time Complexity: O(n) - worst case we scan the entire array
 * Space Complexity: O(1) - only using a single index variable
 */

class Solution {
    public int minimumPrefixLength(int[] nums) {
        int n = nums.length;

        // Start from the second last element and move backwards
        int i = n - 2;

        // Continue while consecutive elements are in strictly increasing order
        // This finds the longest strictly increasing suffix
        while (i >= 0 && nums[i] < nums[i + 1]) {
            i--;
        }

        // i+1 is the starting index of the longest increasing suffix
        // All elements before this index must be removed
        return i + 1;
    }
}

/*
 * Detailed Trace Examples:
 *
 * Example 1: nums = [1, 2, 3, 4]
 * n = 4, i = 2
 * i=2: nums[2]=3 < nums[3]=4? YES → i=1
 * i=1: nums[1]=2 < nums[2]=3? YES → i=0
 * i=0: nums[0]=1 < nums[1]=2? YES → i=-1
 * i=-1: Loop ends (i < 0)
 * Return: -1 + 1 = 0 ✓ (No removal needed, entire array is increasing)
 *
 * Example 2: nums = [5, 1, 2, 3]
 * n = 4, i = 2
 * i=2: nums[2]=2 < nums[3]=3? YES → i=1
 * i=1: nums[1]=1 < nums[2]=2? YES → i=0
 * i=0: nums[0]=5 < nums[1]=1? NO → Loop ends
 * Return: 0 + 1 = 1 ✓ (Remove prefix of length 1, keep [1, 2, 3])
 *
 * Example 3: nums = [5, 6, 1, 2]
 * n = 4, i = 2
 * i=2: nums[2]=1 < nums[3]=2? YES → i=1
 * i=1: nums[1]=6 < nums[2]=1? NO → Loop ends
 * Return: 1 + 1 = 2 ✓ (Remove prefix of length 2, keep [1, 2])
 *
 * Example 4: nums = [2, 1]
 * n = 2, i = 0
 * i=0: nums[0]=2 < nums[1]=1? NO → Loop ends
 * Return: 0 + 1 = 1 ✓ (Remove prefix of length 1, keep [1])
 *
 * Key Insight:
 * - When we exit the loop at index i, it means:
 *   - nums[i] >= nums[i+1] (strictly increasing condition violated)
 *   - All elements from i+1 to n-1 form a strictly increasing sequence
 * - Therefore, removing elements 0 to i (which is i+1 elements total) gives us
 *   the minimum prefix removal to achieve strict increasing order
 */
