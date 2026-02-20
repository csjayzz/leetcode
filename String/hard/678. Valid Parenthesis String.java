//brute tc - o(3^N) sc -  auxillary space o(N);


/*class Solution {
    public boolean checkValidString(String s) {
        int cnt = 0;
        int idx = 0;
        return  solve(s,idx,cnt);
    }
    private boolean solve(String s, int idx, int cnt){
       if(idx==s.length())return cnt==0;
       if(cnt<0)return false;
       if(s.charAt(idx)=='(')return solve(s,idx+1,cnt+1);
       if(s.charAt(idx)==')')return solve(s,idx+1,cnt-1);

       return solve(s,idx+1,cnt+1) || solve(s,idx+1,cnt-1) || solve(s,idx+1,cnt);
       
    }
}



import java.util.*;

class Solution {
    public boolean checkValidString(String s) {
        Map<String, Boolean> memo = new HashMap<>();
        return solve(s, 0, 0, memo);
    }

    private boolean solve(String s, int idx, int cnt, Map<String, Boolean> memo) {
        if (idx == s.length()) return cnt == 0;
        if (cnt < 0) return false;

        String key = idx + "," + cnt;
        if (memo.containsKey(key)) return memo.get(key);

        boolean res;
        if (s.charAt(idx) == '(') {
            res = solve(s, idx + 1, cnt + 1, memo);
        } else if (s.charAt(idx) == ')') {
            res = solve(s, idx + 1, cnt - 1, memo);
        } else { // '*'
            res = solve(s, idx + 1, cnt + 1, memo) ||
                  solve(s, idx + 1, cnt - 1, memo) ||
                  solve(s, idx + 1, cnt, memo);
        }

        memo.put(key, res);
        return res;
    }
}

/*
=========================================
678. Valid Parenthesis String - Notes
=========================================

Problem idea:
- '(' must be matched by a later ')'
- ')' closes one previous '('
- '*' can behave as:
  1) '('
  2) ')'
  3) empty ""

State definition:
- idx = current index in string
- cnt = number of currently open brackets not closed yet
- solve(idx, cnt) = can suffix s[idx..end] become valid with current open count cnt?

Base cases:
1) idx == n:
   valid only if cnt == 0 (all opened brackets closed)
2) cnt < 0:
   invalid immediately (more ')' than '(' at some prefix)

Transitions:
- If s[idx] == '(' => cnt + 1
- If s[idx] == ')' => cnt - 1
- If s[idx] == '*' => try all 3 possibilities:
  - treat as '(' => cnt + 1
  - treat as ')' => cnt - 1
  - treat as empty => cnt

Why memoization optimizes:
- Brute force revisits same (idx, cnt) state many times from different paths.
- Memo stores computed answer for each state.
- Next time same state appears, return in O(1) instead of recomputing full subtree.

Time Complexity:
- Number of unique states is O(n * n) in worst case:
  - idx: 0..n
  - cnt: 0..n
- Each state does O(1) transition work (up to 3 calls).
- TC = O(n^2)

Space Complexity:
- Memo stores O(n^2) states.
- Recursion stack up to O(n).
- SC = O(n^2)

Dry Run (s = "(*))"):

Call solve(0,0):
- idx 0 = '(' -> solve(1,1)

At solve(1,1), char = '*', branch 3 ways:
1) '*' as '('  -> solve(2,2)
   - idx 2 = ')' -> solve(3,1)
   - idx 3 = ')' -> solve(4,0)
   - idx == n and cnt == 0 => true

Since one branch is true, final answer becomes true.
Memo also stores intermediate states like:
- (3,1) -> true
- (2,2) -> true
- (1,1) -> true
- (0,0) -> true

Output: true

Key takeaway:
- This is a classic "decision + overlapping subproblems" pattern.
- Recursive state (idx, cnt) + memo converts exponential brute force to polynomial.
*/

