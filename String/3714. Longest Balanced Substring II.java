import java.util.*;

/*
Revision Notes - 3714. Longest Balanced Substring II

Balanced substring means one of these forms:
1) Single character only: "aaaa" (all same)
2) Two-character balance: counts of x and y are equal, and no third char inside
3) Three-character balance: counts of a, b, c are all equal

Approach:
- Case 1: longest same-character streak in O(n).
- Case 2: run helper for each pair (a,b), (a,c), (b,c).
  Use prefix-diff technique with reset when third character appears.
- Case 3: use prefix counts (ca, cb, cc).
  Equal counts condition can be encoded by:
    (ca - cb, ca - cc) being same at two indices => balanced middle substring.

Why Case 3 map key works:
If at i and j, both differences are same,
(ca-cb)_i = (ca-cb)_j and (ca-cc)_i = (ca-cc)_j,
then between (i+1..j): delta(a)=delta(b)=delta(c).

Complexity:
- Time: O(n)
- Space: O(n)
*/
class Solution {

    static class Pair {
        int d1, d2;

        Pair(int d1, int d2) {
            this.d1 = d1;
            this.d2 = d2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair p = (Pair) o;
            return d1 == p.d1 && d2 == p.d2;
        }

        @Override
        public int hashCode() {
            return 31 * d1 + d2;
        }
    }

    public int longestBalanced(String s) {
        char[] c = s.toCharArray();
        int n = c.length;

        if (n == 0) return 0;

        int res = 0;

        // Case 1: longest block with only one repeated character.
        int cur = 1;
        for (int i = 1; i < n; i++) {
            if (c[i] == c[i - 1]) {
                cur++;
            } else {
                res = Math.max(res, cur);
                cur = 1;
            }
        }
        res = Math.max(res, cur);

        // Case 2: two-character balanced substrings.
        res = Math.max(res, find2(c, 'a', 'b'));
        res = Math.max(res, find2(c, 'a', 'c'));
        res = Math.max(res, find2(c, 'b', 'c'));

        // Case 3: three-character balanced substrings.
        int ca = 0, cb = 0, cc = 0;
        Map<Pair, Integer> mp = new HashMap<>();

        // Prefix before index 0: differences are (0,0) at index -1.
        mp.put(new Pair(0, 0), -1);

        for (int i = 0; i < n; i++) {
            if (c[i] == 'a') ca++;
            else if (c[i] == 'b') cb++;
            else cc++;

            Pair key = new Pair(ca - cb, ca - cc);
            Integer prev = mp.get(key);

            if (prev != null) {
                res = Math.max(res, i - prev);
            } else {
                mp.put(key, i);
            }
        }

        return res;
    }

    // Longest balanced substring using only x and y,
    // with all other characters acting as segment separators.
    private int find2(char[] c, char x, char y) {
        int n = c.length;
        int maxLen = 0;

        // diff = count(x) - count(y), shifted by +n to stay within array index.
        int[] first = new int[2 * n + 1];
        Arrays.fill(first, -2);

        int clearIdx = -1;
        int diff = n;

        // Base for current valid segment.
        first[diff] = -1;

        for (int i = 0; i < n; i++) {
            if (c[i] != x && c[i] != y) {
                // Start new segment after invalid character.
                clearIdx = i;
                diff = n;
                first[diff] = clearIdx;
            } else {
                if (c[i] == x) diff++;
                else diff--;

                if (first[diff] < clearIdx) {
                    // Not seen in current segment yet.
                    first[diff] = i;
                } else {
                    maxLen = Math.max(maxLen, i - first[diff]);
                }
            }
        }

        return maxLen;
    }
}
