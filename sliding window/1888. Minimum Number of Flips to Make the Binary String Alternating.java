/*
LeetCode 1888. Minimum Number of Flips to Make the Binary String Alternating

PROBLEM STATEMENT
You are given a binary string s.
You can do two operations:

1) Rotate left by one:
   remove the first character and append it to the end

2) Flip any one character:
   '0' -> '1'
   '1' -> '0'

Return the minimum number of flips needed to make the string alternating.

Alternating means:
- no two adjacent characters are equal
- valid forms are only:
  010101...
  101010...

------------------------------------------------------------
KEY OBSERVATION
Rotation does not change the characters present.
It only changes which character appears at which index.

So for every possible rotation, we only need to know:
"How many positions mismatch the alternating pattern?"

For a string of length n, there are only 2 target patterns:
1) start with '0' => 010101...
2) start with '1' => 101010...

Answer for one fixed rotation:
min(mismatches with 0101..., mismatches with 1010...)

The real problem is:
How do we check all rotations efficiently?

------------------------------------------------------------
WHY s + s WORKS
If we concatenate:
    doubled = s + s

then every rotation of length n appears as a contiguous window of size n
inside doubled.

Example:
s = "1110"
doubled = "11101110"

Windows of size 4:
1110
1101
1011
0111

These are exactly all rotations of s.

This is a very important pattern:
Whenever the problem involves ALL ROTATIONS of a string/array,
it is often useful to try:
    original + original
and then process windows of original length.

Common situations where s + s is useful:
- checking all rotations
- circular string / circular array problems
- next greater in circular array
- matching cyclic patterns
- minimum/maximum over all rotations

------------------------------------------------------------
APPROACH 1: TRUE BRUTE FORCE
Generate every rotation explicitly.
For each rotation:
- compare with 0101...
- compare with 1010...
- take the minimum

How to generate rotations?
- rotate once, rotate twice, ... rotate n times

Time:
- each rotation check takes O(n)
- there are n rotations
- total O(n^2)

Space:
- O(n) if we build rotated strings

This works conceptually but is too slow for large n.

------------------------------------------------------------
APPROACH 2: YOUR IDEA (BETTER)
This is the approach you wrote:

1) Build:
   concat = s + s

2) Build two alternating target strings for concat length:
   pattern1 = 101010...
   pattern0 = 010101...

3) Use a sliding window of size s.length() on concat

4) For the current window, maintain:
   flip1 = mismatches with pattern1
   flip2 = mismatches with pattern0

5) While expanding/shrinking the window:
   - when a character enters, update mismatch count
   - when a character leaves, remove its contribution

6) For every valid window of size n:
   answer = min(answer, flip1, flip2)

Why it works:
- every window of size n in s + s is one rotation
- mismatch count with alternating pattern tells how many flips are needed

Time: O(2n) => O(n)
Space: O(2n) => O(n)

This is already an optimal time solution.
The only thing to improve is extra space used by the built patterns.

------------------------------------------------------------
APPROACH 3: OPTIMAL
We do the same sliding window on s + s,
but we do NOT build the target strings.

Instead, for any index i in doubled:
- expected char in pattern 0101... is:
  even index -> '0'
  odd index  -> '1'

- expected char in pattern 1010... is:
  even index -> '1'
  odd index  -> '0'

So we compute mismatches directly using index parity.

This removes the StringBuilder patterns completely.

Time: O(n)
Space: O(n) because doubled = s + s is still created

Note:
You can discuss an even tighter thought process where you simulate circular
access instead of building doubled, but in interviews and LeetCode,
the s + s version is already clean and accepted.

------------------------------------------------------------
ODD LENGTH VS EVEN LENGTH INTUITION
This detail is good to remember:

If n is even:
- rotating does not change whether a character lands on even/odd parity
  in a way that breaks the same target structure
- often the mismatch count behaves more simply

If n is odd:
- after one rotation, parity flips for all characters
- that is why checking all rotations actually matters much more

The sliding window on s + s handles both cases uniformly,
so we do not need separate logic.

------------------------------------------------------------
DRY RUN
s = "111000"
doubled = "111000111000"
n = 6

Check every window of size 6:
111000
110001
100011
000111
001110
011100

For each window, compute mismatches with:
- 010101...
- 101010...

Best window is:
100011

Compare with:
101010
Mismatch positions = 2

Answer = 2

------------------------------------------------------------
INTUITION TO REMEMBER
When a problem says:
- rotate string
- circular arrangement
- every shift matters
- compare every rotation with some target pattern

Think:
1) Convert circular to linear using s + s
2) Use a window of original size
3) Maintain rolling information in that window

That pattern shows up a lot in:
- circular arrays
- rotating strings
- cyclic matching
- substring windows over repeated sequences

------------------------------------------------------------
YOUR ORIGINAL STYLE OF SOLUTION (cleaned up)

class Solution {
    public int minFlips(String s) {
        String concat = s + s;
        StringBuilder startWith1 = new StringBuilder();
        StringBuilder startWith0 = new StringBuilder();

        for (int i = 0; i < concat.length(); i++) {
            startWith1.append(i % 2 == 0 ? '1' : '0');
            startWith0.append(i % 2 == 0 ? '0' : '1');
        }

        int left = 0;
        int mismatch1 = 0;
        int mismatch0 = 0;
        int answer = Integer.MAX_VALUE;
        int n = s.length();

        for (int right = 0; right < concat.length(); right++) {
            if (concat.charAt(right) != startWith1.charAt(right)) mismatch1++;
            if (concat.charAt(right) != startWith0.charAt(right)) mismatch0++;

            if (right - left + 1 > n) {
                if (concat.charAt(left) != startWith1.charAt(left)) mismatch1--;
                if (concat.charAt(left) != startWith0.charAt(left)) mismatch0--;
                left++;
            }

            if (right - left + 1 == n) {
                answer = Math.min(answer, Math.min(mismatch1, mismatch0));
            }
        }

        return answer;
    }
}

Time: O(n)
Space: O(n)

------------------------------------------------------------
OPTIMAL CODE
*/

class Solution {
    public int minFlips(String s) {
        int n = s.length();
        String doubled = s + s;

        int left = 0;
        int mismatchStart0 = 0; // target pattern: 010101...
        int mismatchStart1 = 0; // target pattern: 101010...
        int answer = Integer.MAX_VALUE;

        for (int right = 0; right < doubled.length(); right++) {
            char current = doubled.charAt(right);

            char expectedStart0 = (right % 2 == 0) ? '0' : '1';
            char expectedStart1 = (right % 2 == 0) ? '1' : '0';

            if (current != expectedStart0) mismatchStart0++;
            if (current != expectedStart1) mismatchStart1++;

            if (right - left + 1 > n) {
                char outgoing = doubled.charAt(left);
                char outgoingExpectedStart0 = (left % 2 == 0) ? '0' : '1';
                char outgoingExpectedStart1 = (left % 2 == 0) ? '1' : '0';

                if (outgoing != outgoingExpectedStart0) mismatchStart0--;
                if (outgoing != outgoingExpectedStart1) mismatchStart1--;

                left++;
            }

            if (right - left + 1 == n) {
                answer = Math.min(answer, Math.min(mismatchStart0, mismatchStart1));
            }
        }

        return answer;
    }
}

/*
COMPLEXITY
Time: O(n)
- doubled has length 2n
- each character enters/leaves the window once

Space: O(n)
- because of doubled = s + s

------------------------------------------------------------
TAKEAWAYS
1) For alternating binary string problems, always think of the 2 valid patterns:
   010101...
   101010...

2) For rotation problems, check whether:
   s + s
   turns all rotations into fixed-size windows

3) If a window is compared against a repeating pattern,
   often you do not need to build that pattern explicitly.
   Index parity is enough.

4) This is a sliding window + pattern matching problem on a circular string.

5) Your solution idea was already strong.
   The main optimization is removing the extra pattern strings.
*/
