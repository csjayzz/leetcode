/*
 * Problem: Map Word Weights to Reverse Alphabet
 * ---------------------------------------------
 * You are given:
 *   - An array of words (strings of lowercase letters).
 *   - An integer array weights[26], where weights[i] is the weight of the ith letter.
 *
 * Word Weight:
 *   - For a word, sum the weights of its characters.
 *   - Take (sum % 26).
 *   - Map result to a letter using reverse alphabetical order:
 *       0 -> 'z', 1 -> 'y', ..., 25 -> 'a'.
 *
 * Task:
 *   - For each word, compute its mapped letter.
 *   - Concatenate all mapped letters into a final string.
 *
 * -------------------------------------------------
 * Approach:
 * 1. Initialize a StringBuilder for result.
 * 2. For each word:
 *      - Compute sum of weights for all characters.
 *      - Compute sum % 26.
 *      - Map to letter: (char)('z' - (sum % 26)).
 *      - Append to result.
 * 3. Return the final string.
 *
 * -------------------------------------------------
 * Dry Run Example:
 *   words = ["abcd","def","xyz"]
 *   weights = [5,3,12,14,1,2,3,2,10,6,6,9,7,8,7,10,8,9,6,9,9,8,3,7,7,2]
 *
 *   "abcd":
 *     sum = 5+3+12+14 = 34
 *     34 % 26 = 8
 *     'z' - 8 = 'r'
 *
 *   "def":
 *     sum = 14+1+2 = 17
 *     17 % 26 = 17
 *     'z' - 17 = 'i'
 *
 *   "xyz":
 *     sum = 7+7+2 = 16
 *     16 % 26 = 16
 *     'z' - 16 = 'j'
 *
 *   Result = "rij"
 *
 * -------------------------------------------------
 * Time Complexity:
 * - O(total characters across all words).
 * - Each character processed once.
 *
 * Space Complexity:
 * - O(1) extra (besides output string).
 *
 * -------------------------------------------------
 * Pattern Summary:
 * - Compute weighted sum per word.
 * - Reduce modulo 26.
 * - Map to reverse alphabet.
 * - Concatenate results.
 */


class Solution {
    public String mapWordWeights(String[] words, int[] weights) {
        StringBuilder sb = new StringBuilder("");
        // char[] alphabet = new char[26];
        // int index = 0;

        // for (char c = 'z'; c >= 'a'; c--) {
        // alphabet[index++] = c;
        // }
        for(String s : words){
            int sum = 0;
            for(char ch : s.toCharArray()){
                sum += weights[ch-'a'];
            }

           // sb.append(alphabet[sum%26]);
            sb.append((char) ('z' - (sum%26)));
        }

        return sb.toString();
    }
}
