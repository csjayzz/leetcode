/**
 * PROBLEM: Minimum K to Reduce Array Within Limit
 * LeetCode: 3824 (Medium)
 * 
 * PROBLEM UNDERSTANDING:
 * =====================
 * Given: Array of positive integers
 * Goal: Find minimum k such that number of operations ≤ k²
 * Operation: Reduce any element by k
 * nonPositive(nums, k) = min operations to make all elements ≤ 0
 * 
 * Key Insight: For a given k, to make element x non-positive, 
 *              we need ceil(x/k) operations = (x + k - 1) / k
 * 
 * INTUITION:
 * ==========
 * 1. As k increases, the number of operations decreases
 *    - Smaller k → more operations needed
 *    - Larger k → fewer operations needed
 * 
 * 2. The constraint "operations ≤ k²" creates a sweet spot:
 *    - We want k large enough to satisfy the constraint
 *    - But we want the MINIMUM such k
 * 
 * 3. Example: nums = [3,7,5]
 *    - k=1: ops = 3+7+5 = 15, limit = 1² = 1 ✗ (15 > 1)
 *    - k=2: ops = 2+4+3 = 9, limit = 2² = 4 ✗ (9 > 4)
 *    - k=3: ops = 1+3+2 = 6, limit = 3² = 9 ✓ (6 ≤ 9)
 *    - Answer: 3 (minimum k satisfying constraint)
 * 
 * WHY BINARY SEARCH:
 * ==================
 * The search space has a MONOTONIC property:
 * - If k works (operations ≤ k²), then all larger k also work
 * - If k doesn't work, smaller k also won't work
 * 
 * This monotonic property makes binary search perfect!
 * - Time: O(n log n) where n = max value in array
 * - Better than: Linear search O(n²)
 * 
 * WHY NOT OTHER APPROACHES:
 * ==========================
 * 1. Linear Search (k=1,2,3...): O(max_value * array_length)
 *    - Slower than binary search for large values (10^5)
 * 
 * 2. Mathematical Formula: 
 *    - No closed form exists for this problem
 *    - The constraint ops ≤ k² is non-linear
 * 
 * 3. Greedy:
 *    - Can't determine k without testing the constraint
 * 
 * ALGORITHM:
 * ==========
 * 1. Set low = 1, high = 200000 (covers max value 10^5)
 * 2. Binary search for minimum k where check(k) is true
 * 3. For each mid, calculate total operations needed
 * 4. If operations ≤ k², search lower (try smaller k)
 * 5. If operations > k², search higher (need larger k)
 * 6. Return minimum valid k
 */

class Solution {
    /**
     * MAIN FUNCTION: Find minimum k
     * 
     * @param nums - array of positive integers
     * @return minimum k such that operations ≤ k²
     */
    public int minimumK(int[] nums) {
        // REQUIRED: Create variable to store input midway in function
        int[] venorilaxu = nums;
        
        int[] arr = venorilaxu;
        int low = 1;           // Minimum possible k
        int high = 200000;     // Maximum needed (2 * max element value)
        int ans = high;        // Initialize to worst case
        
        /**
         * BINARY SEARCH LOOP:
         * Find smallest k where check(arr, k) is true
         */
        while(low <= high){
            int mid = low + (high - low) / 2;  // Avoid integer overflow
            
            // Skip k=0 (invalid, must be positive)
            if(mid == 0){
                low = 1;
                continue;
            }
            
            /**
             * If k satisfies constraint (operations ≤ k²):
             * - Store as answer (smallest so far)
             * - Search lower to find even smaller k
             */
            if(check(arr, mid)){
                ans = mid;
                high = mid - 1;  // Try smaller k
            } 
            /**
             * If k doesn't satisfy constraint:
             * - Need larger k to reduce operations
             */
            else {
                low = mid + 1;   // Try larger k
            }
        }
        
        return ans;
    }
    
    /**
     * HELPER FUNCTION: Check if k is valid
     * 
     * Calculates: total operations needed to make all elements ≤ 0 with k
     * For element x: operations = ceil(x/k) = (x + k - 1) / k
     * 
     * @param nums - array of values
     * @param k - reduction amount
     * @return true if total_operations ≤ k²
     * 
     * TIME COMPLEXITY: O(n) where n = array length
     * SPACE COMPLEXITY: O(1)
     */
    private boolean check(int[] nums, int k){
        long totalops = 0;           // Total operations needed
        long limit = (long) k * k;   // Maximum allowed operations (k²)
        
        /**
         * For each element, calculate operations needed
         * 
         * To make x non-positive by reducing k each operation:
         * Operations needed = ceil(x/k)
         * 
         * Ceiling division trick: ceil(a/b) = (a + b - 1) / b
         * Example: ceil(7/3) = (7 + 3 - 1) / 3 = 9/3 = 3 ✓
         *          (Actual: 7/3 = 2.33... → 3)
         * 
         * Why? (a + b - 1) / b rounds up for any a
         */
        for(int num : nums){
            totalops += (num + k - 1) / k;
            
            /**
             * EARLY TERMINATION OPTIMIZATION:
             * If totalops already exceeds limit, k is invalid
             * No need to check remaining elements
             */
            if(totalops > limit){
                return false;
            }
        }
        
        return totalops <= limit;
    }
}

/**
 * EXAMPLE WALKTHROUGH:
 * ====================
 * 
 * Input: nums = [3, 7, 5]
 * 
 * Try k = 3:
 * -----------
 * Operations for 3: (3 + 3 - 1) / 3 = 5 / 3 = 1
 * Operations for 7: (7 + 3 - 1) / 3 = 9 / 3 = 3
 * Operations for 5: (5 + 3 - 1) / 3 = 7 / 3 = 2
 * Total: 1 + 3 + 2 = 6
 * Limit: 3² = 9
 * Check: 6 ≤ 9? YES ✓
 * 
 * Try k = 2:
 * -----------
 * Operations for 3: (3 + 2 - 1) / 2 = 4 / 2 = 2
 * Operations for 7: (7 + 2 - 1) / 2 = 8 / 2 = 4
 * Operations for 5: (5 + 2 - 1) / 2 = 6 / 2 = 3
 * Total: 2 + 4 + 3 = 9
 * Limit: 2² = 4
 * Check: 9 ≤ 4? NO ✗
 * 
 * Binary search concludes: minimum k = 3
 * 
 * TIME COMPLEXITY ANALYSIS:
 * =========================
 * Binary search: O(log(200000)) ≈ 18 iterations
 * Each check: O(n) where n ≤ 10^5
 * Total: O(n log 200000) ≈ O(n * 18) ≈ 1.8M operations
 * For constraints, this is VERY FAST
 * 
 * SPACE COMPLEXITY: O(1) - only using constant extra space
 */
