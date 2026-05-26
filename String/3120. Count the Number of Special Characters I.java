/*
 * Problem: Count the Number of Special Characters I (LeetCode 3120)
 * -----------------------------------------------------------------
 * A letter is "special" if it appears in BOTH lowercase and uppercase
 * form in the given string.
 * Task: Return the number of special letters in the string.
 *
 * Example:
 *   word = "aaAbcBC" → Output = 3
 *   Special letters = 'a', 'b', 'c'
 *
 * -----------------------------------------------------------------
 * Approach 1: Boolean Arrays
 * - Maintain two boolean arrays of size 26:
 *   * lower[i] = true if lowercase (i+'a') appears.
 *   * upper[i] = true if uppercase (i+'A') appears.
 * - Traverse the string once, mark presence.
 * - At the end, count indices where both lower[i] and upper[i] are true.
 *
 * Time Complexity: O(n + 26) ≈ O(n)
 * Space Complexity: O(26) = O(1)
 *
 * -----------------------------------------------------------------
 * Approach 2: HashSet (direct check)
 * - Store all characters in a HashSet.
 * - For each lowercase letter 'a'..'z':
 *   * Check if set contains both 'ch' and its uppercase version (ch-32 in ASCII).
 *   * If yes → increment count.
 *
 * Time Complexity: O(n + 26) ≈ O(n)
 * Space Complexity: O(n) for HashSet
 *
 * -----------------------------------------------------------------
 * Approach 3: HashSet (pair difference check)
 * - Store all characters in a HashSet.
 * - For each pair of characters in the set:
 *   * If their ASCII difference is 32 (e.g., 'a' vs 'A'), increment count.
 * - This works because lowercase and uppercase versions differ by 32 in ASCII.
 *
 * Time Complexity: O(n^2) in worst case (nested loop over set).
 * Space Complexity: O(n) for HashSet
 *
 * -----------------------------------------------------------------
 * Dry Run Example:
 *   word = "aaAbcBC"
 *   Approach 1:
 *     lower[] marks: a, b, c
 *     upper[] marks: A, B, C
 *     Both present for indices a, b, c → count = 3
 *
 *   Approach 2:
 *     set = {a, A, b, B, c, C}
 *     For 'a' → both 'a' and 'A' exist → +1
 *     For 'b' → both 'b' and 'B' exist → +1
 *     For 'c' → both 'c' and 'C' exist → +1
 *     Answer = 3
 *
 *   Approach 3:
 *     set = {a, A, b, B, c, C}
 *     Compare pairs → find differences of 32 → count = 3
 *
 * -----------------------------------------------------------------
 * Pattern Summary:
 * - Boolean arrays → most efficient and clean.
 * - HashSet direct check → simpler to write, slightly heavier.
 * - HashSet pair difference → less efficient, but demonstrates ASCII trick.
 */

//approach 1; boolean array 
class Solution {
    public int numberOfSpecialChars(String word) {
        boolean[] lower = new boolean[26];
        boolean[] upper = new boolean[26];

        for (char ch : word.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                lower[ch - 'a'] = true;
            } else {
                upper[ch - 'A'] = true;
            }
        }

        int count = 0;
        for (int i = 0; i < 26; i++) {
            if (lower[i] && upper[i]) {
                count++;
            }
        }

        return count;
    }
}


class Solution {
    public int numberOfSpecialChars(String word) {
        HashSet<Character> set = new HashSet<>();
        for (char ch : word.toCharArray()) {
            set.add(ch);
        }

        int cnt = 0;
        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (set.contains(ch) && set.contains((char)(ch - 32))) {
                cnt++;
            }
        }

        return cnt;
    }
}


/*
class Solution {
    public int numberOfSpecialChars(String word) {
        int n = word.length();
    int cnt =0;
        HashSet<Character>set = new HashSet<>();
        for(int i = 0;i<n;i++){
            set.add(word.charAt(i));
        }
        for(char ch: set){
            for(char c : set){
                if(ch-c==32)cnt++;
            }
        }
        
       
        return cnt;
    }
}
 */