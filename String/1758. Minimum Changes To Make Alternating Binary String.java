/*
LeetCode 1758. Minimum Changes To Make Alternating Binary String

PROBLEM
You are given a binary string s ('0' and '1').
In one operation, you can flip one character:
- '0' -> '1'
- '1' -> '0'
Return the minimum operations required to make s alternating.

Alternating means no two adjacent characters are equal.
Valid alternating strings of length n are only:
1) 010101...
2) 101010...

------------------------------------------------------------
INTUITION
For a fixed length, there are exactly two target patterns.
So we count mismatches with both patterns and take the minimum.
Each mismatch needs exactly one flip.

------------------------------------------------------------
APPROACH 1: BRUTE FORCE (Conceptual)
Try all possible strings formed by flips and check if alternating.
Pick the minimum flips.

Why not practical?
- Number of possibilities is exponential.
- Time complexity is too high for n up to 10^4.

Time: O(2^n * n)
Space: O(n)

------------------------------------------------------------
APPROACH 2: BETTER (Build 2 target strings)
- Build t1 = 010101...
- Build t2 = 101010...
- Count mismatches of s with t1 and t2.
- Answer = min(mismatch1, mismatch2)

Time: O(n)
Space: O(n)

------------------------------------------------------------
APPROACH 3: OPTIMAL (Single pass, O(1) extra space)
Without building target strings:
- If pattern starts with '0':
  expected at even index = '0', odd index = '1'
- If pattern starts with '1':
  expected at even index = '1', odd index = '0'
Count mismatches for both in one loop.

Time: O(n)
Space: O(1)

------------------------------------------------------------
DRY RUN
s = "0100"
Pattern 0101 -> mismatch count = 1
Pattern 1010 -> mismatch count = 3
Answer = min(1, 3) = 1
*/

class Solution {
    public int minOperations(String s) {
        int mismatchStart0 = 0; // target pattern: 0101...
        int mismatchStart1 = 0; // target pattern: 1010...

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            char expectedIfStart0 = (i % 2 == 0) ? '0' : '1';
            char expectedIfStart1 = (i % 2 == 0) ? '1' : '0';

            if (ch != expectedIfStart0) mismatchStart0++;
            if (ch != expectedIfStart1) mismatchStart1++;
        }

        return Math.min(mismatchStart0, mismatchStart1);
    }
}

/*
BETTER APPROACH CODE (for revision)

class Solution {
    public int minOperations(String s) {
        int n = s.length();
        StringBuilder t1 = new StringBuilder(); // 0101...
        StringBuilder t2 = new StringBuilder(); // 1010...

        for (int i = 0; i < n; i++) {
            t1.append(i % 2 == 0 ? '0' : '1');
            t2.append(i % 2 == 0 ? '1' : '0');
        }

        int mismatch1 = 0, mismatch2 = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != t1.charAt(i)) mismatch1++;
            if (s.charAt(i) != t2.charAt(i)) mismatch2++;
        }

        return Math.min(mismatch1, mismatch2);
    }
}
*/
