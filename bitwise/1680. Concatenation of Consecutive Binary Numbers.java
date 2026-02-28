class Solution {
    public int concatenatedBinary(int n) {
        // Idea:
        // Build the answer incrementally.
        // If current answer is X and we append binary(i) with length L bits,
        // new value becomes: (X << L) + i.
        //
        // Since value can be huge, take modulo at every step.
        //
        // Key optimization:
        // The bit-length increases only when i is a power of 2.
        // Example: 1(1 bit), 2..3(2 bits), 4..7(3 bits), 8..15(4 bits).

        final int MOD = 1_000_000_007;
        long result = 0;
        int bitLength = 0;

        for (int i = 1; i <= n; i++) {
            // If i is power of 2, increase bit length.
            // (i & (i - 1)) == 0 is true only for powers of 2.
            if ((i & (i - 1)) == 0) {
                bitLength++;
            }

            // Append i in binary to current result.
            result = ((result << bitLength) + i) % MOD;
        }

        return (int) result;
    }
}

/*
Detailed Dry Run (n = 3):

Initial:
result = 0, bitLength = 0

i = 1
- 1 is power of 2 -> bitLength = 1
- result = ((0 << 1) + 1) % MOD = 1
- binary so far: "1"

i = 2
- 2 is power of 2 -> bitLength = 2
- result = ((1 << 2) + 2) % MOD = 6
- binary so far: "110" ("1" + "10")

i = 3
- 3 is not power of 2 -> bitLength stays 2
- result = ((6 << 2) + 3) % MOD = 27
- binary so far: "11011" ("110" + "11")

Final answer = 27

Quick intuition for n = 12:
- Keep left-shifting by current bit length and add i.
- Bit lengths by ranges:
  1 -> 1 bit
  2..3 -> 2 bits
  4..7 -> 3 bits
  8..12 -> 4 bits
- Applying the same recurrence till i = 12 gives:
  result = 505379714
*/
