/*
 * Problem: Maximum Total Subarray Value I (LeetCode 3689)
 * --------------------------------------------------------
 * You are given an integer array nums and an integer k.
 * You must choose exactly k non-empty subarrays (they may overlap, and duplicates are allowed).
 * The value of a subarray nums[l..r] = max(nums[l..r]) - min(nums[l..r]).
 * Goal: Maximize the total value (sum of chosen subarray values).
 *
 * --------------------------------------------------------
 * Key Insight:
 * - Since subarrays can overlap and even be chosen multiple times,
 *   the optimal strategy is to repeatedly choose the subarray that gives
 *   the maximum possible value.
 * - The maximum possible value of ANY subarray is simply:
 *       max(nums) - min(nums)
 *   because the whole array contains both the global maximum and minimum.
 * - Therefore:
 *   * Best subarray value = globalMax - globalMin
 *   * Choose this subarray k times → total = k * (globalMax - globalMin)
 *
 * --------------------------------------------------------
 * Dry Run Example 1:
 *   nums = [1,3,2], k = 2
 *   globalMax = 3, globalMin = 1 → value = 2
 *   total = 2 * 2 = 4
 *
 * Dry Run Example 2:
 *   nums = [4,2,5,1], k = 3
 *   globalMax = 5, globalMin = 1 → value = 4
 *   total = 3 * 4 = 12
 *
 * --------------------------------------------------------
 * Time Complexity:
 * - O(n) to find global max and min.
 * - O(1) to compute result.
 *
 * Space Complexity:
 * - O(1) → only a few variables.
 *
 * --------------------------------------------------------
 * Pattern Summary:
 * - Because overlapping and duplicates are allowed,
 *   the problem reduces to finding (max - min) once and multiplying by k.
 * - Elegant reduction from subarray problem to global min/max.
 */


class Solution {
    public long maxTotalValue(int[] nums, int k) {
        if(nums.length==0||nums.length==1)return 0;
        long min = Integer.MAX_VALUE;
        long max = Integer.MIN_VALUE;

        for(int i = 0;i<nums.length;i++){
             min = Math.min(nums[i],min);
             max = Math.max(nums[i],max);
        }

        return (max - min) * k;
    }
}
