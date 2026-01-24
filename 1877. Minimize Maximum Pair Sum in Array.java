//Greedy + Two Pointers + Sorting
// LeetCode 1877 - Minimize Maximum Pair Sum in Array

// [https://leetcode.com/problems/minimize-maximum-pair-sum-in-array/](https://leetcode.com/problems/minimize-maximum-pair-sum-in-array/)

//Approach:

//To minimize the maximum pair sum, we should pair the smallest number

//with the largest number, the second smallest with the second largest, and so on.

#

//Steps:

//1. Sort the array.

//2. Use two pointers: one from the start (smallest), one from the end (largest).

//3. For each pair, compute nums[left] + nums[right] and track the maximum among them.

//4. Move both pointers inward until all pairs are formed.

//This greedy strategy works because pairing extremes balances the sums and

//prevents any single pair from becoming unnecessarily large.

//Time Complexity: O(n log n)  (due to sorting)

//Space Complexity: O(1)      (ignoring sorting overhead)

import java.util.Arrays;
class Solution {
    public int minPairSum(int[] nums) {
        Arrays.sort(nums);
        int left = 0;
        int right = nums.length - 1;
        int maxPairSum = 0;

        while (left < right) {
            int sum = nums[left] + nums[right];
            maxPairSum = Math.max(maxPairSum, sum);
            left++;
            right--;
        }

        return maxPairSum;
    }
}
```

// #Note on your current solution (Heaps)

Your heap-based approach is **correct**, but:

* Time Complexity: O(n log n)
* Space Complexity: O(n) (two heaps)

The **sorting + two pointers** method is simpler and uses less memory, which is why it’s preferred for this problem.

