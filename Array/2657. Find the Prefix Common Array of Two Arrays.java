/*
 * Problem: Find the Prefix Common Array of Two Arrays (LeetCode 2657)
 * -------------------------------------------------------------------
 * You are given two integer permutations A and B of length n.
 * A prefix common array C is defined such that:
 *   - C[i] = count of numbers that appear in BOTH A and B
 *     at or before index i.
 *
 * Example:
 *   A = [1,3,2,4], B = [3,1,2,4]
 *   Output = [0,2,3,4]
 *
 * Explanation:
 *   i = 0 → no common → C[0] = 0
 *   i = 1 → {1,3} common → C[1] = 2
 *   i = 2 → {1,2,3} common → C[2] = 3
 *   i = 3 → {1,2,3,4} common → C[3] = 4
 *
 * -------------------------------------------------------------------
 * Thought Process:
 * - We need to track which numbers have appeared in A and B up to index i.
 * - At each step, mark A[i] and B[i] as "seen".
 * - Count how many numbers are marked in BOTH arrays.
 * - Store that count in C[i].
 *
 * -------------------------------------------------------------------
 * Approach:
 * - Use two boolean arrays (size n+1, since values range from 1..n).
 *   * a[x] = true if x has appeared in A up to current index.
 *   * b[x] = true if x has appeared in B up to current index.
 * - At each index i:
 *   * Mark A[i] and B[i] as seen.
 *   * Scan through all possible values (1..n) and count those seen in both.
 *   * Store count in res[i].
 *
 * -------------------------------------------------------------------
 * Time Complexity:
 * - Outer loop runs n times.
 * - Inner loop scans up to n values each time.
 * - Total = O(n^2).
 * - Since n <= 50 (small constraint), this is efficient enough.
 *
 * Space Complexity:
 * - O(n) for boolean arrays and result array.
 *
 * -------------------------------------------------------------------
 * Pattern Summary:
 * - This is a prefix accumulation problem.
 * - Track "seen" elements in both arrays.
 * - At each prefix, count intersection size.
 * - Constraints allow a simple O(n^2) solution.
 */
