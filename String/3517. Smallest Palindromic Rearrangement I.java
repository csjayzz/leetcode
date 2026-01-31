/*You are given a palindromic string s.

Return the lexicographically smallest palindromic permutation of s.

 

Example 1:

Input: s = "z"

Output: "z"

Explanation:

A string of only one character is already the lexicographically smallest palindrome.

Example 2:

Input: s = "babab"

Output: "abbba"

Explanation:

Rearranging "babab" → "abbba" gives the smallest lexicographic palindrome.

Example 3:

Input: s = "daccad"

Output: "acddca"

Explanation:

Rearranging "daccad" → "acddca" gives the smallest lexicographic palindrome.

 

Constraints:

1 <= s.length <= 105
s consists of lowercase English letters.
s is guaranteed to be palindromic.*/

class Solution {
    public String smallestPalindrome(String s) {
        int [] cnt = new int[26];
        StringBuilder sb = new StringBuilder();
        for(char ch : s.toCharArray()){
           cnt[ch - 'a']++;
        }
        for(int i = 0;i<26;i++){
           for(int j = 0;j<cnt[i]/2;j++){
            sb.append((char)(97+i));
            //('a'+i)
           }
           cnt[i] %= 2;
        }
        StringBuilder rev = new StringBuilder(sb).reverse();
        if(s.length()%2!=0){
            for(int i = 0;i<26;i++){
                if(cnt[i]==1){
                    sb.append((char)(97+i));
                    break;
                }
            }
        }

        return sb.append(rev.toString()).toString();
    }
}