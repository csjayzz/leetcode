
import java.util.*;

class Solution {
    public boolean canBeValid(String s, String locked) {
        if (s.length() % 2 == 1) return false;

        Stack<Integer> lockedStack = new Stack<>();
        Stack<Integer> unlockedStack = new Stack<>();

        // Pass 1 (left -> right):
        // Try to satisfy every fixed ')' immediately using:
        // 1) a fixed '(' before it, else
        // 2) an unlocked index before it (convert it to '(').
        for (int i = 0; i < s.length(); i++) {
            if (locked.charAt(i) == '0') {
                unlockedStack.push(i);
            } else if (s.charAt(i) == '(') {
                lockedStack.push(i);
            } else { // locked ')' at i
                if (!lockedStack.isEmpty()) {
                    lockedStack.pop();
                } else if (!unlockedStack.isEmpty()) {
                    unlockedStack.pop();
                } else {
                    return false;
                }
            }
        }

        // Pass 2:
        // Remaining locked '(' must be matched with unlocked indices AFTER them,
        // so those unlocked positions can become ')'.
        while (!lockedStack.isEmpty() &&
               !unlockedStack.isEmpty() &&
               unlockedStack.peek() > lockedStack.peek()) {
            lockedStack.pop();
            unlockedStack.pop();
        }

        return lockedStack.isEmpty();
    }
}

/*
=====================================================
2116. Check if a Parentheses String Can Be Valid
=====================================================

Intuition:
- If length is odd, answer is always false (valid parentheses length must be even).
- locked[i] == '1' means character is fixed.
- locked[i] == '0' means we can flip it to '(' or ')'.
- We use unlocked indices as "wildcards" that can help where needed.

Why two stacks?
- lockedStack: stores indices of locked '(' not matched yet.
- unlockedStack: stores indices where locked[i] == '0' (flexible positions).

Pass 1 (left -> right):
- Goal: never let a forced ')' become impossible.
- For each index i:
  1) unlocked position -> store index in unlockedStack.
  2) locked '(' -> store index in lockedStack.
  3) locked ')' -> try to match:
     - first with locked '(' (best direct match),
     - else with an unlocked index (treat that unlocked as '('),
     - if none available -> false.

After pass 1:
- All problematic locked ')' are handled.
- But some locked '(' may still be unmatched.

Pass 2 (ordering check):
- Remaining locked '(' need ')' in future positions.
- We can use unlocked indices as ')' only if unlocked index is to the right.
- So while top(unlockedStack) > top(lockedStack), pair them and pop both.
- If locked '(' remain, they cannot be closed -> false.

Return:
- true only when all locked '(' are matched.

Complexity:
- Time: O(n) (each index pushed/popped at most once)
- Space: O(n) (two stacks)

Dry run (Example 1):
s = "))()))", locked = "010100"

Index 0: unlocked -> unlockedStack = [0]
Index 1: locked ')' -> use unlocked 0 as '(' -> unlockedStack = []
Index 2: unlocked -> unlockedStack = [2]
Index 3: locked ')' -> use unlocked 2 as '(' -> unlockedStack = []
Index 4: unlocked -> unlockedStack = [4]
Index 5: unlocked -> unlockedStack = [4, 5]

After pass 1:
- lockedStack = [] (no pending locked '(')
- unlockedStack = [4, 5]

Pass 2:
- lockedStack already empty, nothing to match.

Result: true

Key takeaway:
- Pass 1 prevents invalid prefix situations from locked ')'.
- Pass 2 fixes leftover locked '(' using only future unlocked indices.
*/


/*
I've created detailed notes for you based on the video **"Check if a Parentheses String Can Be Valid - Leetcode 2116 - Python"** by NeetCode.

### **Problem Overview**

The goal is to determine if a string of parentheses can be made valid. You are given two strings:

1. `s`: The parentheses string.
2. `locked`: A binary string where `'0'` means the character at that index is "unlocked" (can be changed to either `(` or `)`) and `'1'` means it is "locked" (cannot be changed).

A valid string must have an even length and follow standard parentheses matching rules (every closing bracket must have a preceding opening bracket).

---

### **Key Solutions**

#### **1. The Stack Solution (More Intuitive)** [[01:31](http://www.youtube.com/watch?v=KMIIGDiXLhY&t=91)]

This approach uses two stacks to keep track of the indices of characters as we iterate from left to right.

* **Locked Stack:** Stores indices of locked opening parentheses `(`.
* **Unlocked Stack:** Stores indices of all unlocked positions (`'0'` in the locked string).

**How it works:**

* **Forward Pass:** Iterate through the string.
* If the character is **unlocked**, push its index to the `unlocked` stack.
* If it is a **locked `(**`, push its index to the `locked` stack.
* If it is a **locked `)**`:
1. Try to pop from the `locked` stack first (prioritizing matching locked pairs) [[07:50](http://www.youtube.com/watch?v=KMIIGDiXLhY&t=470)].
2. If the `locked` stack is empty, try to pop from the `unlocked` stack.
3. If both are empty, return `False` immediately (cannot balance this closing bracket).




* **Validation Pass:** After the loop, you might still have locked `(` remaining [[10:39](http://www.youtube.com/watch?v=KMIIGDiXLhY&t=639)].
* Compare the top of both stacks. A locked `(` can only be balanced by an unlocked character that appears **after** it (higher index).
* If `locked_stack[-1] < unlocked_stack[-1]`, pop both.
* If a locked `(` is at a higher index than all available unlocked positions, it can't be closed. Return `False`.


* **Final Check:** If the `locked` stack is empty, return `True`. (The remaining unlocked characters don't matter as long as their total count is even, which is handled by a length check at the start).

#### **2. The Greedy Space-Optimized Solution** [[17:25](http://www.youtube.com/watch?v=KMIIGDiXLhY&t=1045)]

This approach uses counters instead of stacks, reducing space complexity to .

* **Two Passes:** You run a scan from left-to-right to ensure all closing brackets can be matched, then a scan from right-to-left to ensure all opening brackets can be matched [[19:21](http://www.youtube.com/watch?v=KMIIGDiXLhY&t=1161)].
* **Logic:** It's harder to reason about but essentially checks if at any point the number of "required" brackets exceeds the available "flexible" or matching brackets.

---

### **Implementation Tips**

* **Length Check:** The very first step should be checking if `len(s)` is odd. If it is, return `False` immediately, as an odd-length string can never be valid [[16:36](http://www.youtube.com/watch?v=KMIIGDiXLhY&t=996)].
* **Prioritize Locked:** When matching a closing bracket, always use a locked opening bracket before using an unlocked "wildcard" [[06:21](http://www.youtube.com/watch?v=KMIIGDiXLhY&t=381)].

### **Video Resource**

* **Title:** Check if a Parentheses String Can Be Valid - Leetcode 2116 - Python
* **Link:** [Watch on YouTube](https://www.google.com/search?q=https://youtu.be/KMIIGDiXLhY) */