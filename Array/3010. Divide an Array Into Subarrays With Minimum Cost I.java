package Array;

class Solution {
    public int minimumCost(int[] nums) {
       
        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < first) {
                second = first;
                first = nums[i];
            }
            else if (nums[i] < second) {
                second = nums[i];
            }
        }
     return nums[0] + first + second;
    }
}

//the first element is always included in the cost, so we just need to find the two smallest elements in the rest of the array and add them to the first element to get the minimum cost.

/*problem link: https://leetcode.com/problems/divide-an-array-into-subarrays-with-minimum-cost-i/description/ 

You are given an array of integers nums of length n.

The cost of an array is the value of its first element. For example, the cost of [1,2,3] is 1 while the cost of [3,4,1] is 3.

You need to divide nums into 3 disjoint contiguous subarrays.

Return the minimum possible sum of the cost of these subarrays.

 

Example 1:

Input: nums = [1,2,3,12]
Output: 6
Explanation: The best possible way to form 3 subarrays is: [1], [2], and [3,12] at a total cost of 1 + 2 + 3 = 6.
The other possible ways to form 3 subarrays are:
- [1], [2,3], and [12] at a total cost of 1 + 2 + 12 = 15.
- [1,2], [3], and [12] at a total cost of 1 + 3 + 12 = 16.*/

