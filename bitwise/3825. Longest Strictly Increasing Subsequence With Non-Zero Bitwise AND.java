import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PROBLEM: Longest Strictly Increasing Subsequence With Non-Zero Bitwise AND
 * LeetCode: 3825 (Medium)
 * 
 * INTUITION:
 * -----------
 * The key insight is that for a subsequence's bitwise AND to be non-zero,
 * ALL elements must have at least ONE common bit set to 1.
 * 
 * Strategy:
 * 1. For each bit position (0-30), filter all numbers that have that bit set
 * 2. On these filtered numbers, find the Longest Increasing Subsequence (LIS)
 * 3. Take the maximum LIS length across all bit positions
 * 4. Why? If numbers share bit i, their AND will have bit i set, ensuring non-zero result
 * 
 * WHY THIS APPROACH:
 * ------------------
 * - Brute Force (generate all subsequences): O(2^n) - INFEASIBLE for n=10^5
 * - DP on values: Would be slow due to large value range (0 to 10^9)
 * - Bit-filtering approach: O(31 * n log n) - OPTIMAL
 *   * We only iterate 31 bits (since 2^30 > 10^9)
 *   * For each bit, we filter O(n) elements
 *   * Finding LIS using binary search on filtered list: O(n log n)
 *   * Total: O(31 * n log n) ≈ O(n log n)
 * 
 * WHY NOT OTHER APPROACHES:
 * -------------------------
 * 1. Brute Force: Exponential time complexity - impossible for constraints
 * 2. Standard LIS DP on full array: Doesn't account for non-zero AND requirement
 * 3. Greedy on values: Doesn't guarantee optimal subsequence with shared bit
 * 4. Bitmask DP: Would require tracking all possible AND values (2^30 states) - infeasible
 * 
 * ALGORITHM BREAKDOWN:
 * --------------------
 * Step 1: For each bit position (0 to 30)
 *         Extract all numbers that have this bit set
 * Step 2: On filtered numbers, find LIS using binary search approach
 * Step 3: LIS with binary search maintains 'tails' array:
 *         - tails[i] = smallest tail element for increasing subsequence of length i+1
 *         - For each number, use binary search to find its position
 * Step 4: Return maximum LIS length found across all bits
 * 
 * TIME COMPLEXITY: O(31 * n log n) = O(n log n)
 * SPACE COMPLEXITY: O(n) for the tails array
 */

class Solution {
    public int longestSubsequence(int[] nums) {
        // REQUIRED: Create variable to store input midway in function
        int[] sorelanuxi = nums;
        
        int arr[] = sorelanuxi;
        int max = 0;

        /**
         * MAIN LOGIC:
         * Iterate through each bit position (0-30, since 2^30 > 10^9)
         * For each bit, filter numbers that have this bit set,
         * then find LIS on filtered numbers
         */
        for(int i = 0; i < 31; i++){
            // Step 1: Filter all numbers that have bit 'i' set
            List<Integer> list = new ArrayList<>();
            for(int x : arr){
                // Check if i-th bit is set: (x >> i) & 1 == 1
                if(((x >> i) & 1) == 1){
                    list.add(x);
                }
            }
            
            // Step 2: If numbers exist with this bit set,
            // find LIS and update max
            if(!list.isEmpty()){
                max = Math.max(max, lis(list));
            }
        }
        
        return max;
    }
    
    /**
     * LONGEST INCREASING SUBSEQUENCE (LIS) using Binary Search
     * 
     * This is the optimal O(n log n) approach using the "tails" technique.
     * 
     * TAILS ARRAY CONCEPT:
     * --------------------
     * tails[i] = the smallest tail value of all increasing subsequences of length i+1
     * 
     * Example: nums = [5,1,4,2,3]
     * - After 5: tails = [5]
     * - After 1: tails = [1]        (1 < 5, better for future extensions)
     * - After 4: tails = [1,4]      (4 extends from 1)
     * - After 2: tails = [1,2]      (2 replaces 4, better for future)
     * - After 3: tails = [1,2,3]    (3 extends from 2)
     * Result: LIS length = 3
     * 
     * WHY THIS WORKS:
     * ----------------
     * - Binary search finds where current number fits
     * - If it's larger than all tails, append (extend LIS)
     * - Otherwise, replace the tail at that position (optimize for future)
     * - Maintaining smallest tails allows longer future extensions
     */
    private int lis(List<Integer> list){
        List<Integer> tails = new ArrayList<>();
        
        for(int x : list){
            /**
             * Binary search for position of x in tails
             * Returns: -(insertionPoint)-1 if not found
             * insertionPoint = position where x should be inserted
             */
            int i = Collections.binarySearch(tails, x);
            
            // Convert to actual insertion point
            if(i < 0){
                i = -(i + 1);  // i is now the position where x should go
            }
            
            /**
             * THREE CASES:
             * 
             * Case 1: i < tails.size() AND tails.get(i) > x
             *         Replace tails[i] with x for better future extension
             *         Example: tails=[1,4], x=2 → tails=[1,2]
             * 
             * Case 2: i == tails.size() AND x > tails.get(tails.size()-1)
             *         x is larger than all tails, extend the LIS
             *         Example: tails=[1,2], x=3 → tails=[1,2,3]
             * 
             * Case 3: i < tails.size() AND tails.get(i) == x
             *         Duplicate value, skip (strictly increasing required)
             *         (Handled implicitly by above conditions)
             */
            if(i < tails.size()){
                if(tails.get(i) > x){
                    tails.set(i, x);
                }
            } else {
                if(tails.isEmpty() || x > tails.get(tails.size() - 1)){
                    tails.add(x);
                }
            }
        }
        
        return tails.size();
    }
}

/**
 * EXAMPLE WALKTHROUGH:
 * ====================
 * Input: nums = [5, 4, 7]
 * 
 * Bit 0 (rightmost): Numbers with bit 0 = 1 → [5, 7]
 *                     LIS([5,7]) = 2 ✓
 * Bit 1: Numbers with bit 1 = 1 → [5, 7]
 *        LIS([5,7]) = 2
 * Bit 2: Numbers with bit 2 = 1 → [4, 5, 7]
 *        LIS([4,5,7]) = 3
 *        But wait! 4&5 = 4, 4&7 = 4, 5&7 = 5 (non-zero) ✓
 * ... (other bits give smaller LIS or 0)
 * 
 * Maximum LIS = 3, but correct answer is 2 because:
 * - [5,7] has AND = 5&7 = 5 (non-zero) ✓
 * - [4,5,7] has AND = 4&5&7 = 4 (non-zero) ✓
 * Both work! Output: 2 (one valid longest is [5,7])
 * 
 * The approach correctly finds sequences with non-zero AND
 * because all elements in the filtered list share at least one bit.
 */
