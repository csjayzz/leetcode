/**
 * 1545. Find kth Bit in Nth Binary String
 * 
 * PROBLEM:
 * Given two positive integers n and k, find the kth bit in the binary string Sn.
 * 
 * String formation:
 * S1 = "0"
 * Si = S(i-1) + "1" + reverse(invert(S(i-1)))
 * 
 * Examples:
 * S1 = "0"
 * S2 = "011"
 * S3 = "0111001"
 * S4 = "011100110110001"
 * 
 * INTUITION:
 * 1. Notice that Sn has length 2^n - 1
 * 2. The middle element at position 2^(n-1) is always "1"
 * 3. The string has a recursive structure:
 *    - Left part: S(n-1)
 *    - Middle: "1"
 *    - Right part: reverse(invert(S(n-1)))
 * 
 * KEY INSIGHT: We don't need to build the entire string!
 * - If k is in the left part (k < 2^(n-1)): recursively find in S(n-1)
 * - If k is the middle (k == 2^(n-1)): return 1
 * - If k is in the right part (k > 2^(n-1)): 
 *   - Find the mirror position in S(n-1)
 *   - Invert the bit at that position
 */

class Solution {
    
    // ========== APPROACH 1: Recursive Solution (OPTIMAL) ==========
    // Time Complexity: O(n) - we recurse at most n times
    // Space Complexity: O(n) - recursion stack
    public int findKthBit_Recursive(int n, int k) {
        // Base case: S1 = "0", so if we reach length 1, return 0
        if (n == 1) {
            return 0;
        }
        
        // Length of S(n-1) is 2^(n-1) - 1
        long midPoint = 1L << (n - 1); // 2^(n-1)
        
        if (k < midPoint) {
            // k is in the left part S(n-1)
            return findKthBit_Recursive(n - 1, (int)k);
        } else if (k == midPoint) {
            // k is at the middle position
            return 1;
        } else {
            // k is in the right part: reverse(invert(S(n-1)))
            // Mirror position: midPoint + (midPoint - k)
            int mirrorPos = (int)(2 * midPoint - k);
            // Get the bit at mirror position and invert it
            return 1 - findKthBit_Recursive(n - 1, mirrorPos);
        }
    }
    
    
    // ========== APPROACH 2: Iterative Solution ==========
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    // More efficient than recursive approach as it avoids stack overhead
    public int findKthBit_Iterative(int n, int k) {
        int bit = 0; // Start from the bit at position k in S1 = "0"
        
        for (int i = 1; i < n; i++) {
            long midPoint = 1L << i; // 2^i
            
            if (k == midPoint) {
                // At the middle of Si
                bit = 1;
            } else if (k > midPoint) {
                // In the right part: reverse(invert(S(i-1)))
                // Find mirror position
                k = (int)(2 * midPoint - k);
                // Invert the bit
                bit = 1 - bit;
            }
            // If k < midPoint, we're in the left part S(i-1), bit remains unchanged
        }
        
        return bit;
    }
    
    
    // ========== APPROACH 3: Pattern Recognition with String Building ==========
    // Time Complexity: O(2^n) - builds the entire string
    // Space Complexity: O(2^n) - stores the entire string
    // Not optimal but helps understand the pattern
    public int findKthBit_StringBuilding(int n, int k) {
        String s = "0";
        
        for (int i = 2; i <= n; i++) {
            // Build the next string
            String inverted = invert(s);
            String reversed = new StringBuilder(inverted).reverse().toString();
            s = s + "1" + reversed;
        }
        
        return s.charAt(k - 1) - '0'; // k is 1-indexed
    }
    
    private String invert(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(c == '0' ? '1' : '0');
        }
        return sb.toString();
    }
    
    
    // ========== EXAMPLE WALKTHROUGH ==========
    // For n = 4, k = 5:
    // S1 = "0"
    // S2 = "011"
    // S3 = "0111001"
    // S4 = "011100110110001"
    //      ^    ^     ^      Position 5 is '0'
    //      1    5    11
    //
    // Using Approach 1:
    // findKthBit(4, 5):
    //   midPoint = 8
    //   k=5 < 8, so recurse on findKthBit(3, 5)
    //   
    // findKthBit(3, 5):
    //   midPoint = 4
    //   k=5 > 4, so k is in right part
    //   mirrorPos = 2*4 - 5 = 3
    //   return 1 - findKthBit(2, 3)
    //   
    // findKthBit(2, 3):
    //   midPoint = 2
    //   k=3 > 2, so k is in right part
    //   mirrorPos = 2*2 - 3 = 1
    //   return 1 - findKthBit(1, 1)
    //   
    // findKthBit(1, 1):
    //   return 0 (base case)
    //   
    // Back to findKthBit(2, 3): return 1 - 0 = 1
    // Back to findKthBit(3, 5): return 1 - 1 = 0
    // Answer: 0 ✓
}
