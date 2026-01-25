

public class Solution {
    public int minimumDifference(int[] nums, int k) {
        Arrays.sort(nums);
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i <= nums.length - k; i++) {
            minDiff = Math.min(minDiff, nums[i + k - 1] - nums[i]);
        }
        return minDiff;
    }
}

// #Note on Optimal Approach (Sorting + Sliding Window)
Your current solution using sorting and a sliding window is optimal for this problem:
* Time Complexity: O(n log n) due to sorting
* Space Complexity: O(1) if sorting in place
* Steps:
 1. Sort the array to arrange scores in ascending order.
 2. Use a sliding window of size k to find the minimum difference between the highest and lowest scores in each window.
 3. Update the minimum difference found.
This approach efficiently finds the minimum difference by leveraging the sorted order of scores, ensuring that the highest and lowest scores in any k-sized group are adjacent in the sorted array.
    4. Return the minimum difference after checking all possible windows.
    