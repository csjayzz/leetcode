/*761. Special Binary String
the core understanding the binaray string has just 1 and 0
1.the special string has equal number of 1 and 0 
2.Every prefix of the binary string has at least as many 1's as 0's.
means in string Input: s = "11011000"
11011000 has equal no. of 1 and 0 and also the prefix 1101 has aleast same or more 1's 
10 , 1100
every special binary string is like a balanced paranthesis string ()
1 is open ( and 0 is closed )    
(()(())) -> 11011000
 if we consider +1 for 1 and -1 for 0 , the end sum result will be zero for the special binary string
 also to make the special binary string it should always start with 1 and end with 0
  1  1  0  1  1  0  0  0
 +1 +1 -1 +1 +1 -1 -1 -1  = 0
 we can use that here all the given string are special string 
 A move consists of choosing two consecutive, non-empty, special substrings of s, and swapping them. Two strings are consecutive if the last character of the first string is exactly one index before the first character of the second string.

Return the lexicographically largest resulting string possible after applying the mentioned operations on the string.

 

Example 1:

Input: s = "11011000"
Output: "11100100"
Explanation: The strings "10" [occuring at s[1]] and "1100" [at s[3]] are swapped.
This is the lexicographically largest string possible after some number of swaps.

valid subBInaryString are 10 and 1100 , even number of 1 and 0 and prefix has more or same 1's
and there sum is 0 (o=-1,1=+1) jaha bhi sum zero aayega we realise ye toh khud hi special string h, now that we have these subs we swap them to get a lexiographically greater binary string
# there is one more condition that the sub string should be consecutive.
but now there can also be swap within the string we found per say in 1100 so as here we are breaking down the problem into sub problems and futher sub problem 
intution is that its a recursive problem.

now ill hand the problem to my recursion function to find sub special string and have leap of faith that it will return lexiographically
largest string and then ill append 1 at start and 0 at end and thats my answer 

*/

class Solution {
    public String makeLargestSpecial(String s) {
     List<String>specials = new ArrayList<>();
     int sum = 0;
     int start = 0;

     for(int i = 0;i<s.length();i++){
       sum += (s.charAt(i) == '1') ? 1 : -1;
        if(sum==0){
            String inner = s.substring(start + 1, i);
            String processed = "1" + makeLargestSpecial(inner) + "0";
            specials.add(processed);
            start = i + 1;
        } 
     } 
     Collections.sort(specials,Collections.reverseOrder());
     StringBuilder result = new StringBuilder();
     for (String str : specials) {
            result.append(str);
        }

        return result.toString();
    }
}

/*
===============================
Clean Notes (Approach + Dry Run)
===============================

Intuition:
- A special binary string behaves like a valid parentheses string:
  - '1' -> opening bracket '('
  - '0' -> closing bracket ')'
- So every special block:
  - has equal count of 1 and 0
  - never lets 0-count exceed 1-count in any prefix
- If we track:
  - +1 for '1'
  - -1 for '0'
  then any segment where running sum comes back to 0 forms one complete special block.

Why recursion?
- Inside any special block of form: 1 + inner + 0,
  the "inner" part can also contain smaller special blocks.
- To maximize whole string lexicographically:
  1. maximize each inner part first (recursive call)
  2. then sort all top-level special blocks in descending lexicographic order
  3. concatenate them

Why sorting descending works:
- Problem allows swapping consecutive special substrings.
- Repeated swaps let us reorder sibling special blocks.
- For lexicographic maximum, larger block should come earlier.
- So after maximizing each block internally, we place blocks in reverse sorted order.

Algorithm Steps:
1. Traverse string with running sum.
2. Whenever sum becomes 0:
   - current segment [start...i] is one top-level special substring.
   - extract inner = s.substring(start + 1, i).
   - recursively maximize inner.
   - rebuild block as "1" + maximizedInner + "0".
   - store it.
   - move start = i + 1.
3. Sort stored blocks in descending order.
4. Join and return.

Dry Run: s = "11011000"

Top-level traversal:
- i=0 '1' sum=1
- i=1 '1' sum=2
- i=2 '0' sum=1
- i=3 '1' sum=2
- i=4 '1' sum=3
- i=5 '0' sum=2
- i=6 '0' sum=1
- i=7 '0' sum=0  => one full block: s[0..7] = "11011000"

Process this block:
- inner = s.substring(1, 7) = "101100"
- processed = "1" + makeLargestSpecial("101100") + "0"

Now solve makeLargestSpecial("101100"):
- Traverse:
  - i=0 '1' sum=1
  - i=1 '0' sum=0 => block "10"
    inner="" -> makeLargestSpecial("")=""
    processed block = "10"
  - i=2 '1' sum=1
  - i=3 '1' sum=2
  - i=4 '0' sum=1
  - i=5 '0' sum=0 => block "1100"
    inner="10" -> makeLargestSpecial("10")

Now solve makeLargestSpecial("10"):
- Traverse:
  - i=0 '1' sum=1
  - i=1 '0' sum=0 => block "10"
    inner="" -> ""
    processed="10"
- sort ["10"] -> ["10"]
- return "10"

Back to "101100":
- second processed block = "1" + "10" + "0" = "1100"
- collected blocks: ["10", "1100"]
- sort descending => ["1100", "10"]
- join => "110010"

Back to original "11011000":
- processed top block = "1" + "110010" + "0" = "11100100"
- only one top-level block, return "11100100"

Final Answer: "11100100"

Complexity:
- Let n = length of s.
- Splitting + recursion + sorting at each level gives overall O(n^2) in worst case (due to substring/sorting across recursive layers in Java implementation).
- Extra space: O(n) for recursion + list/storage.

Key takeaways:
- Detect special blocks using running sum hitting 0.
- Recursively optimize inner content.
- Sort sibling special blocks descending to get lexicographically largest result.
*/
 
