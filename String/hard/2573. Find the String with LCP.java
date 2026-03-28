/*
2573. Find the String with LCP

Problem explanation:
We are given an n x n matrix `lcp`.

lcp[i][j] means:
the length of the longest common prefix between:
- word[i...n-1]
- word[j...n-1]

In simpler words:
if we start comparing the suffix starting at i
and the suffix starting at j,
how many characters match from the beginning?


Example:
word = "abab"

suffix at 0 = "abab"
suffix at 2 = "ab"

Their longest common prefix is "ab", length = 2
So lcp[0][2] = 2


What we need:
Construct the lexicographically smallest string that matches the matrix.
If no such string exists, return "".


Very important observations:

1. Diagonal rule
   lcp[i][i] must be exactly n - i
   because the suffix always matches itself fully.

2. If lcp[i][j] > 0, then word[i] must equal word[j]
   because the first characters of the two suffixes must match.

3. If lcp[i][j] == 0, then word[i] must not equal word[j]
   because the first characters differ immediately.

4. If word[i] == word[j], then:
   lcp[i][j] should be 1 + lcp[i+1][j+1]
   unless one index is at the end.


Main idea used in both approaches:
We build the answer greedily from left to right.
At position i:

- first try to reuse a previous character if lcp[j][i] > 0
- otherwise assign the smallest possible character not forbidden

This greedy construction gives the lexicographically smallest candidate.

Then we validate whether that candidate truly matches the given LCP matrix.


Why greedy works for lexicographically smallest answer:
At every index i, we choose the smallest character that does not immediately
contradict previously seen LCP constraints.
So among all valid constructions, this one is the smallest in dictionary order.
*/

class Solution {

    /*
     * Approach-1 helper:
     * Build the full LCP matrix for the constructed word,
     * then compare it with the given matrix.
     *
     * This uses O(n^2) extra space for the computed matrix.
     */
    private int[][] checkLCP(String word) {
        int n = word.length();
        int[][] lcp = new int[n][n];

        // Last column:
        // comparing suffix at i with suffix at n-1
        for (int i = 0; i < n; i++) {
            lcp[i][n - 1] = (word.charAt(i) == word.charAt(n - 1)) ? 1 : 0;
        }

        // Last row:
        // comparing suffix at n-1 with suffix at j
        for (int j = 0; j < n; j++) {
            lcp[n - 1][j] = (word.charAt(n - 1) == word.charAt(j)) ? 1 : 0;
        }

        /*
         * Fill from bottom-right to top-left.
         *
         * If characters match:
         * lcp[i][j] = 1 + lcp[i+1][j+1]
         * else:
         * lcp[i][j] = 0
         */
        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                if (word.charAt(i) == word.charAt(j)) {
                    lcp[i][j] = 1 + lcp[i + 1][j + 1];
                } else {
                    lcp[i][j] = 0;
                }
            }
        }

        return lcp;
    }

    public String findTheString(int[][] lcp) {
        int n = lcp.length;
        char[] word = new char[n];

        // Placeholder means "not assigned yet"
        for (int i = 0; i < n; i++) {
            word[i] = '$';
        }

        /*
         * Step 1: Greedy construction
         *
         * For each position i:
         * - if some earlier index j has lcp[j][i] > 0,
         *   then word[i] must equal word[j]
         * - otherwise assign the smallest possible character
         *   not forbidden by any j where lcp[j][i] == 0
         */
        for (int i = 0; i < n; i++) {

            // Try to match with some previous position.
            for (int j = 0; j < i; j++) {
                if (lcp[j][i] != 0) {
                    word[i] = word[j];
                    break;
                }
            }

            // If still unassigned, give the smallest allowed character.
            if (word[i] == '$') {
                boolean[] forbidden = new boolean[26];

                for (int j = 0; j < i; j++) {
                    if (lcp[j][i] == 0) {
                        forbidden[word[j] - 'a'] = true;
                    }
                }

                for (int c = 0; c < 26; c++) {
                    if (!forbidden[c]) {
                        word[i] = (char) ('a' + c);
                        break;
                    }
                }

                // If we ran out of letters, no valid string exists.
                if (word[i] == '$') {
                    return "";
                }
            }
        }

        String result = new String(word);

        /*
         * Step 2: Final validation by recomputing the entire LCP matrix.
         * If even one cell mismatches, the candidate is invalid.
         */
        int[][] computed = checkLCP(result);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (computed[i][j] != lcp[i][j]) {
                    return "";
                }
            }
        }

        return result;
    }
}

/*
========================================================================
Detailed greedy construction idea
========================================================================

Suppose we are building the string left to right.

At index i:

Case 1:
There exists some earlier j < i such that lcp[j][i] > 0
That means suffix j and suffix i share at least 1 common character.
So word[j] == word[i].
Then the earliest such group can share the same character.

Case 2:
For all earlier j, lcp[j][i] == 0
Then word[i] must differ from word[j] for those j.
So we mark their characters as forbidden and choose the smallest available one.

This gives lexicographically smallest assignment because:
- we reuse earlier characters whenever required
- otherwise we choose the smallest possible new character


Dry run for Example 1:
lcp =
[[4,0,2,0],
 [0,3,0,1],
 [2,0,2,0],
 [0,1,0,1]]

n = 4

Start:
word = [$, $, $, $]

i = 0
No earlier character.
Smallest choice = 'a'
word = [a, $, $, $]

i = 1
lcp[0][1] = 0
So word[1] cannot be 'a'
Choose smallest possible = 'b'
word = [a, b, $, $]

i = 2
lcp[0][2] = 2 > 0
So word[2] must equal word[0] = 'a'
word = [a, b, a, $]

i = 3
lcp[0][3] = 0
lcp[1][3] = 1 > 0
So word[3] must equal word[1] = 'b'
word = [a, b, a, b]

Candidate = "abab"

Then validation confirms its LCP matrix matches.
Answer = "abab"
*/

/*
========================================================================
Approach-1: Greedy allocation + build full LCP matrix for validation
========================================================================

Steps:
1. Build the smallest possible candidate string greedily.
2. Recompute the entire LCP matrix for that candidate.
3. Compare with the given matrix.

Why this is valid:
If the greedily built string is valid, it is the lexicographically smallest one.
If it is invalid, then the given LCP constraints are inconsistent
with the candidate structure we were forced to build.

Time complexity:
- Building candidate: O(n^2)
- Building computed LCP: O(n^2)
- Comparing matrices: O(n^2)

Overall:
T.C. : O(n^2)
S.C. : O(n^2)


Code you gave for Approach-1:

class Solution {

    private int[][] checkLCP(String word) {
        int n = word.length();
        int[][] lcp = new int[n][n];

        for (int i = 0; i < n; i++) {
            lcp[i][n - 1] = (word.charAt(i) == word.charAt(n - 1)) ? 1 : 0;
        }

        for (int j = 0; j < n; j++) {
            lcp[n - 1][j] = (word.charAt(n - 1) == word.charAt(j)) ? 1 : 0;
        }

        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                if (word.charAt(i) == word.charAt(j)) {
                    lcp[i][j] = 1 + lcp[i + 1][j + 1];
                } else {
                    lcp[i][j] = 0;
                }
            }
        }

        return lcp;
    }

    public String findTheString(int[][] lcp) {
        int n = lcp.length;
        char[] word = new char[n];

        for (int i = 0; i < n; i++) {
            word[i] = '$';
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (lcp[j][i] != 0) {
                    word[i] = word[j];
                    break;
                }
            }

            if (word[i] == '$') {
                boolean[] forbidden = new boolean[26];

                for (int j = 0; j < i; j++) {
                    if (lcp[j][i] == 0) {
                        forbidden[word[j] - 'a'] = true;
                    }
                }

                for (int c = 0; c < 26; c++) {
                    if (!forbidden[c]) {
                        word[i] = (char) ('a' + c);
                        break;
                    }
                }

                if (word[i] == '$') {
                    return "";
                }
            }
        }

        String result = new String(word);
        int[][] computed = checkLCP(result);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (computed[i][j] != lcp[i][j]) {
                    return "";
                }
            }
        }

        return result;
    }
}
*/

/*
========================================================================
Approach-2: Greedy allocation + direct validation without building matrix
========================================================================

Idea:
The string construction remains exactly the same.
Only the validation changes.

Instead of creating a separate computed n x n matrix,
we directly verify whether the given matrix is consistent
with the final candidate string.

Validation rules:

1. Last column:
   Comparing suffix i with suffix n-1
   If the characters match, LCP must be 1.
   Else it must be 0.

2. Last row:
   Same logic for comparing suffix n-1 with suffix j.

3. Remaining cells:
   If word[i] == word[j], then lcp[i][j] must be 1 + lcp[i+1][j+1]
   Else lcp[i][j] must be 0

This avoids building another O(n^2) matrix.


Why it is better in space:
Approach-1 stores a new computed matrix.
Approach-2 directly checks consistency, so extra space is only O(n)
for the constructed word.

Time complexity:
The direct checking still examines all cells.

T.C. : O(n^2)
S.C. : O(n)


Code you gave for Approach-2:

class Solution {

    public boolean checkLCP(String word, int[][] lcp) {
        int n = word.length();

        for (int i = 0; i < n; i++) {
            if (word.charAt(i) != word.charAt(n - 1)) {
                if (lcp[i][n - 1] != 0) return false;
            } else {
                if (lcp[i][n - 1] != 1) return false;
            }
        }

        for (int j = 0; j < n; j++) {
            if (word.charAt(n - 1) != word.charAt(j)) {
                if (lcp[n - 1][j] != 0) return false;
            } else {
                if (lcp[n - 1][j] != 1) return false;
            }
        }

        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                if (word.charAt(i) == word.charAt(j)) {
                    if (lcp[i][j] != 1 + lcp[i + 1][j + 1]) return false;
                } else {
                    if (lcp[i][j] != 0) return false;
                }
            }
        }

        return true;
    }

    public String findTheString(int[][] lcp) {
        int n = lcp.length;

        char[] word = new char[n];
        for (int i = 0; i < n; i++) {
            word[i] = '$';
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (lcp[j][i] != 0) {
                    word[i] = word[j];
                    break;
                }
            }

            if (word[i] == '$') {
                boolean[] forbidden = new boolean[26];

                for (int j = 0; j < i; j++) {
                    if (lcp[j][i] == 0) {
                        forbidden[word[j] - 'a'] = true;
                    }
                }

                for (int idx = 0; idx < 26; idx++) {
                    if (!forbidden[idx]) {
                        word[i] = (char) ('a' + idx);
                        break;
                    }
                }

                if (word[i] == '$') {
                    return "";
                }
            }
        }

        String result = new String(word);
        return checkLCP(result, lcp) ? result : "";
    }
}
*/
