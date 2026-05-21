/*
 * Problem: Find the Length of the Longest Common Prefix (LeetCode 3043)
 * ---------------------------------------------------------------------
 * You are given two arrays arr1 and arr2 of positive integers.
 * A prefix of an integer is formed by one or more digits starting from the left.
 * A common prefix of two integers a and b is an integer c that is a prefix of both.
 * Goal: Find the length of the longest common prefix among ALL pairs (x, y) where
 *       x ∈ arr1 and y ∈ arr2.
 *
 * Example:
 *   arr1 = [1,10,100], arr2 = [1000]
 *   Output = 3
 *
 * ---------------------------------------------------------------------
 * Thought Process:
 * - Brute force: compare every pair → too slow.
 * - Optimized: generate all prefixes for arr1 and arr2, store in sets.
 * - Intersection of sets = common prefixes.
 * - Longest digit length among intersection = answer.
 *
 * ---------------------------------------------------------------------
 * Approach:
 * 1. For each number in arr1:
 *      - Generate prefixes by dividing by 10 (e.g., 123 → 123, 12, 1).
 *      - Store in set1.
 * 2. Do the same for arr2 → store in set2.
 * 3. For each prefix in set1:
 *      - If also in set2 → compute digit length.
 *      - Update maximum length.
 * 4. Return maximum length.
 *
 * ---------------------------------------------------------------------
 * Digit Length Calculation:
 * - Use log10 to compute number of digits:
 *   * log10(100) = 2 → floor + 1 = 3 digits.
 *   * log10(9) ≈ 0.95 → floor + 1 = 1 digit.
 *   * log10(1234) ≈ 3.09 → floor + 1 = 4 digits.
 * - Special case: n == 0 → 1 digit (by convention).
 *
 * ---------------------------------------------------------------------
 * Dry Run Example:
 *   arr1 = [1,10,100], arr2 = [1000]
 *
 *   Step 1: Build prefixes for arr1
 *     1 → {1}
 *     10 → {10, 1}
 *     100 → {100, 10, 1}
 *     set1 = {1, 10, 100}
 *
 *   Step 2: Build prefixes for arr2
 *     1000 → {1000, 100, 10, 1}
 *     set2 = {1000, 100, 10, 1}
 *
 *   Step 3: Intersection
 *     set1 ∩ set2 = {1, 10, 100}
 *
 *   Step 4: Compute lengths
 *     1 → length = 1
 *     10 → length = 2
 *     100 → length = 3
 *   Answer = 3
 *
 * ---------------------------------------------------------------------
 * Time Complexity:
 * - O(n * d) where d = number of digits (≤ 9 for 10^8).
 * - Efficient for n ≤ 50,000.
 *
 * Space Complexity:
 * - O(n * d) for storing prefixes in sets.
 *
 * ---------------------------------------------------------------------
 * Pattern Summary:
 * - Generate all prefixes by dividing by 10.
 * - Use sets to capture unique prefixes.
 * - Intersection gives common prefixes.
 * - Compute longest digit length among them.
 */
