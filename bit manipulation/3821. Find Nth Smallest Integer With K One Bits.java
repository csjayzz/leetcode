/*
 * LeetCode 3821 - Find Nth Smallest Integer With K One Bits
 * Problem: Find the nth smallest integer that has exactly k set bits (1s) in binary representation
 *
 * Approach (Combinatorial - Bit Manipulation):
 * Instead of iterating through all numbers, we use combinatorics to directly compute
 * the nth number with k set bits. This uses the concept of choosing positions for bits.
 *
 * Key Insight:
 * - Numbers with exactly k set bits can be thought of as choosing k positions out of
 *   available bit positions to set to 1.
 * - We use the binomial coefficient C(n, k) to count how many numbers have k bits
 *   set in positions 0 to n.
 * - C(n, k) = number of ways to choose k bits from n positions
 *
 * Algorithm:
 * 1. Precompute Pascal's triangle (binomial coefficients) using DP:
 *    C[i][j] = C[i-1][j-1] + C[i-1][j]
 * 2. Start from the highest bit position (59) and work downwards.
 * 3. For each position, determine if we need to set this bit:
 *    - If C[b][k] >= n, we need to set bit at position b-1
 *    - Otherwise, skip this position and reduce n by C[b-1][k]
 * 4. Repeat until k bits are set.
 *
 * Examples:
 * - n=3, k=1: Numbers with 1 bit set are 1, 2, 4, 8, ... → 3rd is 4 (binary: 100)
 * - n=5, k=2: Numbers with 2 bits set are 3, 5, 6, 9, 10, ... → 5th is 10 (binary: 1010)
 *
 * Time Complexity: O(60 * 60) for Pascal's triangle + O(60) for main loop = O(1)
 * Space Complexity: O(60 * 60) = O(1)
 */

class Solution {
    public long nthSmallest(long n, int k) {
        // Build Pascal's triangle to store binomial coefficients C(i, j)
        long[][] c = new long[60][60];
        for (int i = 0; i < 60; i++) {
            c[i][0] = 1; // Base case: C(i, 0) = 1
            for (int j = 1; j <= i; j++) {
                c[i][j] = c[i - 1][j - 1] + c[i - 1][j]; // Pascal's triangle recurrence
            }
        }

        long ans = 0;
        while (k > 0) {
            // Find the lowest bit position where we can place a 1
            int b = 0;
            while (b < 60 && c[b][k] < n) {
                b++; // Keep moving to higher positions
            }

            // Set the bit at position (b-1)
            ans |= (1L << (b - 1));

            // Update n: subtract the count of numbers with k bits set using only positions 0 to (b-2)
            n -= c[b - 1][k];

            // One bit has been placed, need to place (k-1) more bits
            k--;
        }

        return ans;
    }
}

/*
 * Trace Example: n=3, k=1
 * We want the 3rd smallest number with exactly 1 bit set.
 *
 * Iteration 1 (k=1):
 * - Find b where C[b][1] >= 3
 * - C[0][1] = 0, C[1][1] = 1, C[2][1] = 2, C[3][1] = 3
 * - b = 3, so we set bit at position (3-1) = 2
 * - ans = 100 (binary) = 4 (decimal)
 * - n = 3 - C[2][1] = 3 - 2 = 1
 * - k = 0, loop ends
 * - Result: 4 ✓
 *
 * Why this works:
 * - C[b][k] = number of k-bit numbers using positions 0 to (b-1)
 * - When we set a bit at position b-1, we eliminate C[b-1][k] numbers
 * - We continue until all k bits are placed
 */
