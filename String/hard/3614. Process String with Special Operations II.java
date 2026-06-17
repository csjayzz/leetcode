/*
 * Problem: Process String with Special Operations II (LeetCode 3614)
 * ------------------------------------------------------------------
 * You are given a string s with lowercase letters and special ops: '*', '#', '%'.
 * Build result string by:
 *   - Letter: append to result
 *   - '*': remove last char (if exists)
 *   - '#': duplicate result (result = result + result)
 *   - '%': reverse result
 * Return kth character of final result, or '.' if out of bounds.
 *
 * Constraints:
 * - s.length up to 1e5
 * - k up to 1e15
 * - Final length <= 1e15
 *
 * ------------------------------------------------------------------
 * Key Insight:
 * - Final string can be astronomically large → cannot construct directly.
 * - Instead, compute length L of final string, then reverse simulate operations.
 * - Reverse simulation traces back where kth character originated.
 *
 * ------------------------------------------------------------------
 * Reverse Simulation Rules:
 * - Letter (a–z):
 *     Forward: L++
 *     Reverse: L--, if L == k → return this letter
 *
 * - '*':
 *     Forward: if L>0 then L--
 *     Reverse: L++ (undo removal), k unchanged
 *
 * - '#':
 *     Forward: L = 2L
 *     Reverse: L = L/2
 *              if k >= L → k = k - L (k was in second half)
 *              else k unchanged
 *
 * - '%':
 *     Forward: reverse string
 *     Reverse: same length, but index mapping changes
 *              k = L - k - 1
 *
 * ------------------------------------------------------------------
 * Dry Run 1:
 *   s = "a#b%*", k = 1
 *   Forward length:
 *     'a' → L=1
 *     '#' → L=2
 *     'b' → L=3
 *     '%' → L=3
 *     '*' → L=2
 *   Final L=2, k=1 valid.
 *   Reverse:
 *     '*' → L=3
 *     '%' → k=3-1-1=1
 *     'b' → L=2, L!=k
 *     '#' → L=1, k>=1 → k=0
 *     'a' → L=0, L==k → return 'a'
 *   Answer = 'a'
 *
 * Dry Run 2:
 *   s = "cd%#*#", k = 3
 *   Forward length:
 *     'c' → L=1
 *     'd' → L=2
 *     '%' → L=2
 *     '#' → L=4
 *     '*' → L=3
 *     '#' → L=6
 *   Final L=6, k=3 valid.
 *   Reverse:
 *     '#' → L=3, k>=3 → k=0
 *     '*' → L=4
 *     '#' → L=2, k=0
 *     '%' → k=2-0-1=1
 *     'd' → L=1, L==k → return 'd'
 *   Answer = 'd'
 *
 * Dry Run 3:
 *   s = "z*#", k = 0
 *   Forward length:
 *     'z' → L=1
 *     '*' → L=0
 *     '#' → L=0
 *   Final L=0, k=0 out of bounds.
 *   Answer = '.'
 *
 * ------------------------------------------------------------------
 * Complexity:
 * - Time: O(n) → one forward pass for length, one backward pass for reverse simulation
 * - Space: O(1) → only counters and index tracking
 *
 * ------------------------------------------------------------------
 * Pattern Summary:
 * - Do not build final string.
 * - Track length and index transformations.
 * - Reverse simulate to trace kth character back to original input.
 */

/*//it is an Aptitude Qns asked in banking comp.
we just cant make the final string as it will be very large. so we need to do reverse simulation
lets say , for now there is only #, then for s = abc# , k = 4 -> result string = abcabc and ans = result[4] i.e b
its clear we cant make the result string so we do reverse simulation, in s the b is at index 1, we get the length of the result string without making it , like for # , abc# - 3, abcabc - 6; ** k - n/2 ** 4 - 6/2 = 1 which is the position of k in s. we got it without forming the result string.

for normal ch, we undo i.e L-- and check if(L==k) if it is then return s[i];
for example, abc#, start frm last, # = length 6, k = k - n/2; undo # : L = L/2, k-n/2 we are at c normal character so , l--, then b , l-- and check if l == k oh it is then return s[i];

for %, reverse, abcde% -> again start from the last, L = 5, edcba , k change to l - k - 1; length stays the same.

for*, to undo do L++;

*/

class Solution {
    public char processStr(String s, long k) {
        //Length
       long L = 0;
       int n = s.length();
       
       //1: get the Length of result string 
       for(char ch : s.toCharArray()){
        if(ch=='*'){
            if(L>0)
            L--;
        }else if(ch=='#'){
            L *= 2;
        }else if(ch=='%'){
            //no change in length
        }else{
            //'a' to 'z'
            L++;
        }
       }

       if(k>=L)return '.';

       for(int i = n-1;i>=0;i--){
        if(s.charAt(i)=='*'){
            //no change in k
            L++;
        }else if(s.charAt(i)=='#'){
            L = L/2;
            k = (k>=L) ? k - L: k;
        }else if(s.charAt(i)=='%'){
            //No change in L
            k = L - k - 1;
        }else{
            //'a' to 'z'
            L--;
        }

        if(L==k)return s.charAt(i);
       }

       return '.';
    }
}