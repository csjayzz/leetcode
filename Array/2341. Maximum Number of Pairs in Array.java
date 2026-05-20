/*
 * Problem: Maximum Number of Pairs in Array (LeetCode 2341)
 * ---------------------------------------------------------
 * You are given an integer array nums.
 * Operation: Choose two equal integers, remove them → form a pair.
 * Repeat until no more pairs can be formed.
 * Return: [number_of_pairs, number_of_leftover_elements].
 *
 * Example:
 *   nums = [1,3,2,1,3,2,2]
 *   → Pairs formed = 3, leftover = 1
 *   Output = [3,1]
 *
 * ---------------------------------------------------------
 * Thought Process:
 * - We need to count how many pairs can be formed from equal numbers.
 * - Each pair requires 2 occurrences of the same number.
 * - Leftovers are simply the numbers that cannot be paired.
 *
 * ---------------------------------------------------------
 * Approach 1: Frequency Array
 * - Since nums[i] ranges from 0..100, we can use a fixed-size array freq[101].
 * - Count occurrences of each number.
 * - For each count:
 *     * pairs += count / 2
 *     * leftover += count % 2
 * - Return [pairs, leftover].
 *
 * Time Complexity: O(n + k), where k = 101 (constant).
 * Space Complexity: O(k) = O(1).
 *
 * ---------------------------------------------------------
 * Approach 2: HashMap
 * - More general solution if nums[i] range was larger.
 * - Count occurrences using HashMap<Integer, Integer>.
 * - For each frequency:
 *     * pairs += val / 2
 *     * leftover += val % 2
 * - Return [pairs, leftover].
 *
 * Time Complexity: O(n).
 * Space Complexity: O(unique elements).
 *
 * ---------------------------------------------------------
 * Pattern Summary:
 * - This is a counting problem.
 * - Use frequency counting (array or map).
 * - Divide counts into pairs and leftovers.
 */

//approach : frequency array
class Solution {
    public int[] numberOfPairs(int[] nums) {
        int[] freq = new int[101]; // values range 0..100

        for (int num : nums) {
            freq[num]++;
        }

        int pairs = 0, leftover = 0;
        for (int count : freq) {
            pairs += count / 2;
            leftover += count % 2;
        }

        return new int[]{pairs, leftover};
    }
}
