/**
 * 338. Counting Bits
 *
 * Given an integer n, return an array ans of length n + 1 such that
 * ans[i] is the number of 1's in the binary representation of i.
 *
 * Intuition / Explanation:
 * - Observing how bits change when shifting right: i >> 1 is i / 2 (integer division).
 * - The least-significant bit (LSB) of i determines whether we add 1 to the
 *   bit-count of i>>1:
 *     - If i is even, LSB is 0 -> bits(i) == bits(i>>1).
 *     - If i is odd,  LSB is 1 -> bits(i) == bits(i>>1) + 1.
 * - This gives a simple dynamic programming relation:
 *     res[0] = 0;
 *     res[i] = res[i >> 1] + (i & 1);
 *   where (i & 1) extracts the LSB (0 for even, 1 for odd).
 *
 * Complexity:
 * - Time: O(n) — each integer 1..n is processed in constant time.
 * - Space: O(n) — result array of size n+1.
 */
public class CountingBits {
	/**
	 * Returns an array of bit counts from 0 to n.
	 * @param n the maximum integer
	 * @return an int[] where result[i] == number of 1-bits in i
	 */
	public int[] countBits(int n) {
		int[] res = new int[n + 1];
		// res[0] is 0 by default
		for (int i = 1; i <= n; i++) {
			// Use right shift to get i/2 and (i & 1) to test the LSB.
			// Example: i = 5 (101b) -> i>>1 = 2 (10b), res[5] = res[2] + 1
			res[i] = res[i >> 1] + (i & 1);
		}
		return res;
	}
}

