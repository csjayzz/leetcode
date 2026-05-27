import java.util.*;
/*
 * Problem: Count the Number of Special Characters II (LeetCode 3121)
 * ------------------------------------------------------------------
 * A letter c is "special" if:
 *   1. It appears in BOTH lowercase and uppercase in the string.
 *   2. Every lowercase occurrence of c appears BEFORE the first uppercase occurrence of c.
 *
 * Task: Return the number of special letters in the string.
 *
 * Example:
 *   word = "aaAbcBC" → Output = 3
 *   Special letters = 'a', 'b', 'c'
 *
 *   word = "abc" → Output = 0
 *   No uppercase letters → no special characters.
 *
 *   word = "AbBCab" → Output = 0
 *   Lowercase 'a' and 'b' appear AFTER uppercase → invalid.
 *
 * ------------------------------------------------------------------
 * Thought Process:
 * - We need to check ordering of lowercase vs uppercase occurrences.
 * - For each letter:
 *   * Track the LAST occurrence of its lowercase form.
 *   * Track the FIRST occurrence of its uppercase form.
 * - A letter is special if:
 *   * Both lowercase and uppercase exist.
 *   * lastLowerIndex < firstUpperIndex.
 *
 * ------------------------------------------------------------------
 * Approach:
 * 1. Initialize two arrays of size 26:
 *      - lastSmall[i] = last index of lowercase (i+'a'), -1 if not present.
 *      - firstCapital[i] = first index of uppercase (i+'A'), -1 if not present.
 * 2. Traverse the string:
 *      - Update lastSmall for lowercase.
 *      - Update firstCapital for uppercase (only first occurrence).
 * 3. After traversal:
 *      - For each letter i:
 *          If lastSmall[i] != -1 && firstCapital[i] != -1 && lastSmall[i] < firstCapital[i],
 *          then count++.
 * 4. Return count.
 *
 * ------------------------------------------------------------------
 * Dry Run Example:
 *   word = "aaAbcBC"
 *   Traverse:
 *     'a' → lastSmall['a'] = 1
 *     'A' → firstCapital['A'] = 2
 *     'b' → lastSmall['b'] = 3
 *     'c' → lastSmall['c'] = 4
 *     'B' → firstCapital['B'] = 5
 *     'C' → firstCapital['C'] = 6
 *   Check:
 *     'a': lastSmall=1, firstCapital=2 → valid
 *     'b': lastSmall=3, firstCapital=5 → valid
 *     'c': lastSmall=4, firstCapital=6 → valid
 *   Answer = 3
 *
 * ------------------------------------------------------------------
 * Time Complexity:
 * - O(n + 26) ≈ O(n), where n = length of word.
 * - Single pass + constant check.
 *
 * Space Complexity:
 * - O(26) = O(1) for arrays.
 *
 * ------------------------------------------------------------------
 * Pattern Summary:
 * - Track positions of lowercase and uppercase letters.
 * - Compare last lowercase vs first uppercase.
 * - Efficient O(n) solution using index arrays.
 */


class Solution {
    public int numberOfSpecialChars(String word) {
      //every small letter should come before the first occurnance of the capital letter 
      //so all we have to do is note the last occurance of small letter 'a' and first occurence of capital letter 'A'
      //lastly check if last occurance of small is smaller than the first occurance of capital 
      int [] lastSmall = new int[26];
      int [] firstCapital = new int [26];

      Arrays.fill(lastSmall,-1);
      Arrays.fill(firstCapital,-1);

      for(int i = 0;i<word.length();i++){
        char ch = word.charAt(i);
        if(Character.isLowerCase(ch)){
            lastSmall[ch - 'a'] = i;
        }
        else{
            if(firstCapital[ch-'A']==-1){
                firstCapital[ch-'A'] = i;
            }
        }
      }  
     
     int cnt = 0;
      for(int i = 0;i<26;i++){
        if(lastSmall[i]!=-1 && firstCapital[i]!=-1 && lastSmall[i]<firstCapital[i]){
            cnt++;
        }
      }

      return cnt;
    }
}