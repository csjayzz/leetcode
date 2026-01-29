/**
 * 190. Reverse Bits
 *
 * Reverse bits of a given 32-bit signed integer.
 *
 * Example:
 * Input:  n = 43261596 (00000010100101000001111010011100)
 * Output: 964176192  (00111001011110000010100101000000)
 *
 * ============ APPROACH 1: Check Each Bit Position ============
 * Intuition:
 * - Iterate through all 32 bits from right to left.
 * - For each position i (1 to 32):
 *   - Check if the i-th bit (from right) is set using: (1 << (i-1)) & n
 *   - If set, place a 1 at position (32-i) in the result using: 1 << (32-i) | ans
 * - This directly maps position i to position (32-i).
 *
 * Time: O(32) = O(1) — constant number of iterations.
 * Space: O(1).
 */
class ReverseBitsApproach1 {
    public int reverseBits(int n) {
        int ans = 0;
        for (int i = 1; i <= 32; i++) {
            // Check if the i-th bit (from right) is set
            if (findIfKthBitSet(i, n)) {
                // Place a 1 at position (32-i) in result
                ans = (1 << (32 - i)) | ans;
            }
        }
        return ans;
    }

    private boolean findIfKthBitSet(int i, int n) {
        // Extract the i-th bit: shift left by (i-1), AND with n
        return ((1 << (i - 1)) & n) != 0;
    }
}

/*
 * ============ APPROACH 2: Most Optimal (Recommended) ============
 * Intuition:
 * - Process bits from right to left of the input.
 * - For each bit processed, shift the result left and append the rightmost bit.
 * - Use n >>>= 1 (unsigned right shift) to process the next bit.
 *
 * Algorithm:
 * - Initialize result = 0
 * - Loop 32 times:
 *   - Shift result left: result <<= 1
 *   - Extract rightmost bit of n: n & 1
 *   - Add it to result: result |= (n & 1)
 *   - Shift n right: n >>>= 1
 *
 * Time: O(32) = O(1).
 * Space: O(1).
 * Why optimal: No bit-shifting calculation needed per iteration; simpler operations.
 */
class ReverseBitsApproach2 {
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            // Shift result left to make space for next bit
            result <<= 1;
            // Extract and append the rightmost bit of n
            result |= (n & 1);
            // Move to the next bit in n
            n >>>= 1;  // Use unsigned right shift to handle negative numbers
        }
        return result;
    }
}

/*
 * ============ APPROACH 3: Using Integer Built-in (Not Recommended for Interview) ============
 * - Java provides Integer.reverse(n) which does exactly this.
 * - But this defeats the purpose of the bit manipulation exercise.
 *
 * public int reverseBits(int n) {
 *     return Integer.reverse(n);
 * }
 */
