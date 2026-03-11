/*
Problem summary:
We are given a non-negative integer n.
We have to flip every bit in its binary representation:
0 becomes 1 and 1 becomes 0.

Important detail:
We only flip the bits that are part of n's actual binary representation.
We do not consider leading zeroes.

Example:
n = 5
binary = 101
complement = 010
answer = 2

Special case:
n = 0
binary representation is "0"
after flipping, it becomes "1"
so the answer is 1.

Observation:
If a number has k bits, then a mask of k ones can be used to flip all bits.
Example:
n = 10 = 1010
mask = 1111
n ^ mask = 0101 = 5

Note:
Number of bits in n = floor(log2(n)) + 1, for n > 0.
*/

class Solution {
    public int bitwiseComplement(int n) {
        // Final active solution: Approach-3 (log based mask construction)
        if (n == 0) {
            return 1;
        }

        /*
         * Approach-3 idea:
         * 1. Find how many bits are present in n.
         * 2. Create a mask of the same size with all bits = 1.
         * 3. XOR n with that mask to flip every relevant bit.
         *
         * Example:
         * n    = 1010
         * mask = 1111
         * ans  = 0101
         */
        int bits = (int) (Math.log(n) / Math.log(2)) + 1;
        int mask = (1 << bits) - 1;

        return n ^ mask;
    }
}

/*
Approach-0: String / binary conversion

Explanation:
1. Convert the number to its binary string.
2. Traverse each character.
3. Replace '1' with '0' and '0' with '1'.
4. Convert the new binary string back to decimal.

Why it works:
This directly simulates the definition of complement.

T.C. : O(number of bits) = O(log n)
S.C. : O(number of bits) = O(log n)

class Solution {
    public int bitwiseComplement(int n) {
        String b = Integer.toBinaryString(n);
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < b.length(); i++) {
            if (b.charAt(i) == '1') {
                s.append('0');
            } else {
                s.append('1');
            }
        }

        int res = Integer.parseInt(s.toString(), 2);
        return res;
    }
}
*/

/*
Approach-1: Build the answer bit by bit

Explanation:
1. Read the last bit of n.
2. If that bit is 0, put 1 in the answer at the same position.
   If that bit is 1, put 0 in the answer.
3. Shift n right and continue.

Why it works:
We process each binary digit independently and construct the complement manually.

T.C. : O(number of bits) = O(log n)
S.C. : O(1)

class Solution {
    public int bitwiseComplement(int n) {
        if (n == 0)
            return 1;

        int result = 0;
        int counter = 0;

        while (n != 0) {
            int r = n % 2;
            result += (int) (Math.pow(2, counter) * (r == 0 ? 1 : 0));
            counter++;
            n = n >> 1;
        }

        return result;
    }
}
*/

/*
Approach-2: Build a mask using bit operations

Explanation:
1. Start with mask = 1.
2. Keep extending the mask like:
   1 -> 11 -> 111 -> 1111 ...
3. Stop when mask becomes large enough to cover all bits of n.
4. XOR n with mask.

Why XOR works:
1 ^ 1 = 0
0 ^ 1 = 1
So XOR with a mask of all ones flips every bit.

T.C. : O(number of bits) = O(log n)
S.C. : O(1)

class Solution {
    public int bitwiseComplement(int n) {
        if (n == 0)
            return 1;

        int mask = 1;
        while (mask < n) {
            mask = (mask << 1) | 1;
        }
        return n ^ mask;
    }
}
*/

/*
Approach-3: Use number of bits directly

Explanation:
1. Compute how many bits are needed to represent n.
2. Create mask = 2^bits - 1, which is a sequence of all ones.
3. XOR with n to get the complement.

Example:
n = 5 = 101
bits = 3
mask = 111
101 ^ 111 = 010 = 2

Why this is efficient:
It avoids constructing the answer bit by bit.
Once the mask is known, one XOR gives the result.

T.C. : O(1) for the arithmetic operations here
S.C. : O(1)

class Solution {
    public int bitwiseComplement(int n) {
        if (n == 0)
            return 1;

        int bits = (int)(Math.log(n) / Math.log(2)) + 1;
        int mask = (1 << bits) - 1;
        return n ^ mask;
    }
}
*/
