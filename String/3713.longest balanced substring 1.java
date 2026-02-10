/*
3713. Longest Balanced Substring I

You are given a string s consisting of lowercase English letters.
A substring is balanced if all distinct characters in that substring
appear the same number of times.

Examples:
Input: s = "abbac"   -> Output: 4  ("abba")
Input: s = "zzabccy" -> Output: 4  ("zabc")
Input: s = "aba"     -> Output: 2  ("ab" or "ba")

Constraints:
1 <= s.length <= 1000
s consists of lowercase English letters.
*/

/*
Intuition:
- Since n <= 1000, checking all substrings is acceptable.
- For each start index i, expand end index j and maintain frequency counts.
- A substring is balanced when all non-zero frequencies are equal.
- If balanced, update the maximum length.

Approach (Brute Force + Frequency Validation):
1. Fix starting index i.
2. Create a fresh freq[26].
3. Move j from i to n-1, updating freq for s.charAt(j).
4. After each update, check if current freq state is balanced.
5. Track the maximum window length.

How balance check works:
- Ignore characters with freq = 0.
- Take the first non-zero frequency as the expected common value.
- If any other non-zero frequency differs, it is not balanced.
- Otherwise, it is balanced.

Complexity:
- Time: O(n^2 * 26), because there are O(n^2) substrings and each check scans 26 letters.
- Space: O(26) = O(1), fixed-size frequency array.
*/

class Solution {
    public int longestBalanced(String s) {
        int maxL = 0;

        for (int i = 0; i < s.length(); i++) {
            int[] freq = new int[26];

            for (int j = i; j < s.length(); j++) {
                freq[s.charAt(j) - 'a']++;

                if (checkBalanced(freq)) {
                    maxL = Math.max(maxL, j - i + 1);
                }
            }
        }

        return maxL;
    }

    private boolean checkBalanced(int[] freq) {
        int common = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] == 0) {
                continue;
            }

            if (common == 0) {
                common = freq[i];
            }

            if (freq[i] != common) {
                return false;
            }
        }

        return true;
    }
}
