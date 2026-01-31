/*You are given an array of characters letters that is sorted in non-decreasing order, and a character target. There are at least two different characters in letters.

Return the smallest character in letters that is lexicographically greater than target. If such a character does not exist, return the first character in letters.

 

Example 1:

Input: letters = ["c","f","j"], target = "a"
Output: "c"
Explanation: The smallest character that is lexicographically greater than 'a' in letters is 'c'.
Example 2:

Input: letters = ["c","f","j"], target = "c"
Output: "f"
Explanation: The smallest character that is lexicographically greater than 'c' in letters is 'f'.
Example 3:

Input: letters = ["x","x","y","y"], target = "z"
Output: "x"
Explanation: There are no characters in letters that is lexicographically greater than 'z' so we return letters[0].
 

Constraints:

2 <= letters.length <= 104
letters[i] is a lowercase English letter.
letters is sorted in non-decreasing order.
letters contains at least two different characters.
target is a lowercase English letter.*/

class Solution {
    public char nextGreatestLetter(char[] letters, char target) {
        for(char ch : letters){
            if(ch>target){
                return ch;
            }
        }
        return  letters[0];
    }
}

//binary search/744. Find Smallest Letter Greater Than Target.java
class Sol{
    public char nextGreatestLetter(char[] letters, char target) {
        int left = 0;
        int right = letters.length - 1;
        
        // Binary search to find the smallest letter greater than target
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (letters[mid] <= target) {
                left = mid + 1; // Move right if mid letter is less than or equal to target
            } else {
                right = mid - 1; // Move left if mid letter is greater than target
            }
        }
        
        // If left index goes out of bounds, wrap around to the first letter
        return letters[left % letters.length];
    }
}