/*
LeetCode 1784. Check if Binary String Has at Most One Segment of Ones

PROBLEM
Given a binary string s (no leading zeros, so s[0] == '1'),
return true if s has at most one contiguous segment of '1's.
Otherwise return false.

Example:
- "1001" -> false (two separate 1-segments)
- "110"  -> true  (single 1-segment)

------------------------------------------------------------
KEY OBSERVATION
If ones are in more than one segment, pattern "01" must appear:
- First segment of ones ends with 0
- Later another 1 starts again
So presence of "01" means multiple 1-segments.

Hence:
- contains("01") -> false
- does not contain("01") -> true

------------------------------------------------------------
APPROACH 1: BRUTE FORCE (Count 1-segments)
Traverse string and count how many times a new 1-segment starts.
If count > 1, return false.

Time: O(n)
Space: O(1)

Pseudo idea:
- segments = 0
- for each i:
  if s[i] == '1' and (i == 0 or s[i-1] == '0') segments++
- return segments <= 1

------------------------------------------------------------
APPROACH 2: BETTER (Find first zero, ensure no one after it)
Since s starts with 1:
- Move while chars are '1'
- Move through zeros
- If any '1' appears again => false
Else true

Time: O(n)
Space: O(1)

------------------------------------------------------------
APPROACH 3: OPTIMAL (Library-based concise check)
Use string pattern check:
return !s.contains("01");

Time: O(n)
Space: O(1)

------------------------------------------------------------
DRY RUN
s = "1001"
contains("01") = true
So return false.

s = "1110"
contains("01") = false
So return true.
*/

class Solution {
    public boolean checkOnesSegment(String s) {
        // "01" implies ones ended and then started again => more than one segment.
        return !s.contains("01");
    }
}

/*
BRUTE FORCE CODE (for revision)

class Solution {
    public boolean checkOnesSegment(String s) {
        int segments = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1' && (i == 0 || s.charAt(i - 1) == '0')) {
                segments++;
                if (segments > 1) return false;
            }
        }

        return true;
    }
}
*/
