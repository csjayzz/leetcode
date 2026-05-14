/*2784. Check if Array is Good
Solved
Easy
Topics
premium lock icon
Companies
Hint
You are given an integer array nums. We consider an array good if it is a permutation of an array base[n].

base[n] = [1, 2, ..., n - 1, n, n] (in other words, it is an array of length n + 1 which contains 1 to n - 1 exactly once, plus two occurrences of n). For example, base[1] = [1, 1] and base[3] = [1, 2, 3, 3].

Return true if the given array is good, otherwise return false.

Note: A permutation of integers represents an arrangement of these numbers.

 

Example 1:

Input: nums = [2, 1, 3]
Output: false
Explanation: Since the maximum element of the array is 3, the only candidate n for which this array could be a permutation of base[n], is n = 3. However, base[3] has four elements but array nums has three. Therefore, it can not be a permutation of base[3] = [1, 2, 3, 3]. So the answer is false. */


// class Solution {
//     public boolean isGood(int[] nums) {
//         Arrays.sort(nums);
//         int n = nums.length;
//         for(int i = 0;i<n-1;i++){
//             if(nums[i]!=i+1)return false;   
//         }
        
//         return nums[n-1] == n-1;
//     }
// }


class Solution {

    public boolean isGood(int[] nums) {

        int n = nums.length;

        int[] freq = new int[n];

        for (int num : nums) {

            // valid numbers should be from 1 to n-1
            if (num <= 0 || num >= n) {
                return false;
            }

            freq[num]++;
        }

        // 1 to n-2 should appear exactly once
        for (int i = 1; i < n - 1; i++) {
            if (freq[i] != 1) {
                return false;
            }
        }

        // n-1 should appear exactly twice
        return freq[n - 1] == 2;
    }
}