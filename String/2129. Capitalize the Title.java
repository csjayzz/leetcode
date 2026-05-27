/*
 * Problem: Capitalize the Title (LeetCode 2129)
 * ---------------------------------------------
 * You are given a string title consisting of words separated by spaces.
 * Rules:
 *   1. If a word length ≤ 2 → all lowercase.
 *   2. If a word length ≥ 3 → capitalize first letter, rest lowercase.
 * Task: Return the transformed title.
 *
 * Example:
 *   Input: "capiTalIze tHe titLe"
 *   Output: "Capitalize The Title"
 *
 *   Input: "First leTTeR of EACH Word"
 *   Output: "First Letter of Each Word"
 *
 *   Input: "i lOve leetcode"
 *   Output: "i Love Leetcode"
 *
 * -----------------------------------------------------
 * Thought Process:
 * - We need to normalize capitalization word by word.
 * - Two main approaches:
 *
 * Approach 1: Split + String Operations
 * - Split title into words.
 * - Convert each word to lowercase.
 * - If length ≤ 2 → keep lowercase.
 * - Else → capitalize first letter + append rest lowercase.
 * - Join words back with spaces.
 *
 * Approach 2: Character Array Manipulation
 * - Work directly on char[] to avoid extra string objects.
 * - Traverse characters, track word length.
 * - Lowercase all letters first.
 * - When a word ends:
 *     * If length > 2 → capitalize its first character.
 * - Reset length counter at each space.
 *
 * -----------------------------------------------------
 * Dry Run Example:
 *   Input: "First leTTeR of EACH Word"
 *   Split → ["First","leTTeR","of","EACH","Word"]
 *   Normalize:
 *     "First" → "First"
 *     "leTTeR" → "Letter"
 *     "of" → "of"
 *     "EACH" → "Each"
 *     "Word" → "Word"
 *   Output: "First Letter of Each Word"
 *
 * -----------------------------------------------------
 * Time Complexity:
 * - O(n) for both approaches (n = length of title).
 *
 * Space Complexity:
 * - Approach 1: O(n) for split array.
 * - Approach 2: O(1) extra space (in-place char array).
 *
 * -----------------------------------------------------
 * Pattern Summary:
 * - Normalize words to lowercase.
 * - Apply capitalization rules based on word length.
 * - Either use split + string ops (simpler) or char[] (efficient).
 */

//Approach 1: Split + String Operations
class Solution {
    public String capitalizeTitle(String title) {
        String[] words = title.split("\\s+");

        for (int i = 0; i < words.length; i++) {
            String s = words[i].toLowerCase(); // normalize to lowercase

            if (s.length() <= 2) {
                words[i] = s; // keep lowercase
            } else {
                words[i] = Character.toUpperCase(s.charAt(0)) + s.substring(1);
            }
        }

        return String.join(" ", words);
    }
}


//Approach 2: Character Array Manipulation
class Solution {
    public String capitalizeTitle(String title) {
        char[] c = title.toCharArray();
        int len = 0;

        for (int i = 0; i <= c.length; i++) {
            if (i < c.length && c[i] != ' ') {
                if (c[i] < 'a') c[i] += 32; // convert to lowercase
                len++;
            } else {
                if (len > 2) {
                    c[i - len] -= 32; // capitalize first letter of word
                }
                len = 0; // reset for next word
            }
        }

        return String.valueOf(c);
    }
}

