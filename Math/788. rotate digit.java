/*
788. Rotated Digits

Problem explanation:
We need to count how many numbers from 1 to n are "good".

A number is good if:
1. after rotating every digit by 180 degrees, the result is still a valid number
2. the rotated number is different from the original number


Digit behavior after rotation:
- 0 -> 0
- 1 -> 1
- 8 -> 8
These remain the same.

- 2 -> 5
- 5 -> 2
- 6 -> 9
- 9 -> 6
These are valid and change the number.

- 3, 4, 7
These become invalid.


So a number is good if:
- it contains no invalid digit
- it contains at least one changing digit among {2, 5, 6, 9}


Examples:
1 -> rotates to 1, valid but unchanged, so not good
2 -> rotates to 5, valid and changed, so good
10 -> rotates to 10, valid but unchanged, so not good
12 -> rotates to 15, valid and changed, so good


Useful classification:
For any number we can classify it into 3 states:
0 = valid but unchanged
1 = good
2 = invalid

This classification makes the memoized and DP solutions very clean.
*/

class Solution {
    public int rotatedDigits(int n) {

        /*
         * Active solution: Approach-3 (Bottom-up DP)
         *
         * t[i] meaning:
         * 0 -> valid but unchanged after rotation
         * 1 -> good
         * 2 -> invalid
         */
        int[] t = new int[n + 1];
        java.util.Arrays.fill(t, -1);

        t[0] = 0;
        int count = 0;

        for (int i = 1; i <= n; i++) {

            // Classification of all digits except the last one.
            int remain = t[i / 10];

            // If prefix is already invalid, whole number is invalid.
            if (remain == 2) {
                t[i] = 2;
                continue;
            }

            int d = i % 10;
            int digitCheck;

            if (d == 0 || d == 1 || d == 8) {
                digitCheck = 0; // valid but unchanged
            } else if (d == 2 || d == 5 || d == 6 || d == 9) {
                digitCheck = 1; // valid and changes
            } else {
                t[i] = 2; // invalid digit
                continue;
            }

            /*
             * If both the prefix and the last digit are "unchanged",
             * then the whole number is unchanged.
             * Otherwise, since it is valid and at least one part changes,
             * it becomes a good number.
             */
            if (remain == 0 && digitCheck == 0) {
                t[i] = 0;
            } else {
                t[i] = 1;
            }

            if (t[i] == 1) {
                count++;
            }
        }

        return count;
    }
}

/*
========================================================================
Approach-1: Brute force simulation
========================================================================

Idea:
Check every number from 1 to n independently.
For each number:
- inspect every digit
- if any digit is in {3,4,7}, number is invalid
- if at least one digit is in {2,5,6,9}, the number changes
- otherwise it remains the same

At the end:
- valid + changed => good


Dry run for n = 10:

1 -> digits = [1]
     valid, unchanged => not good

2 -> digits = [2]
     valid, changed => good

3 -> invalid

4 -> invalid

5 -> good
6 -> good
7 -> invalid
8 -> unchanged
9 -> good
10 -> digits [1,0], both unchanged => not good

Count = 4


Why it works:
The problem is local to each digit.
So checking the digits directly is enough.


Time complexity:
Each number takes O(number of digits) to inspect.

Strictly speaking:
T.C. : O(n * log10(n))
S.C. : O(1)

Your code comment said O(n), which is often accepted informally here
because the number of digits is very small for n <= 10^4.


Code you gave for Approach-1:

class Solution {

    private boolean isGood(int num) {
        boolean changed = false;

        while (num > 0) {
            int digit = num % 10;

            if (digit == 3 || digit == 4 || digit == 7) return false;
            if (digit == 2 || digit == 5 || digit == 6 || digit == 9) changed = true;

            num /= 10;
        }
        return changed;
    }

    public int rotatedDigits(int n) {
        int count = 0;

        for (int i = 1; i <= n; i++) {
            if (isGood(i)) {
                count++;
            }
        }
        return count;
    }
}
*/

/*
========================================================================
Approach-2: Recursion + Memoization
========================================================================

Idea:
Instead of checking all digits from scratch for every number,
reuse the answer of the prefix.

For number `num`:
- prefix = num / 10
- last digit = num % 10

If we already know the state of prefix,
we can combine it with the state of the last digit.


State meaning:
0 = valid but unchanged
1 = good
2 = invalid


Transition:
1. If prefix is invalid, whole number is invalid
2. If last digit is invalid, whole number is invalid
3. If both prefix and last digit are unchanged, whole number is unchanged
4. Otherwise, whole number is good


Dry run for 26:
solve(26)
-> remain = solve(2)
solve(2):
   solve(0) = 0
   digit 2 is changing
   so solve(2) = 1

Back to 26:
remain = 1
digit 6 is changing
So 26 is valid and changed => good => 1


Why memoization helps:
Many numbers share prefixes.
For example:
12, 13, 14 all use the result for prefix 1.


Time complexity:
T.C. : O(n)
S.C. : O(n)


Code you gave for Approach-2:

class Solution {

    int[] t;

    private int solve(int num) {
        if (t[num] != -1)
            return t[num];

        if (num == 0)
            return t[num] = 0;

        int remain = solve(num / 10);
        if (remain == 2)
            return t[num] = 2;

        int d = num % 10;
        int digitCheck;

        if (d == 0 || d == 1 || d == 8)
            digitCheck = 0;
        else if (d == 2 || d == 5 || d == 6 || d == 9)
            digitCheck = 1;
        else
            return t[num] = 2;

        if (remain == 0 && digitCheck == 0)
            return t[num] = 0;

        return t[num] = 1;
    }

    public int rotatedDigits(int n) {
        t = new int[n + 1];
        java.util.Arrays.fill(t, -1);

        int count = 0;

        for (int i = 1; i <= n; i++) {
            if (solve(i) == 1) {
                count++;
            }
        }

        return count;
    }
}
*/

/*
========================================================================
Approach-3: Bottom-up DP
========================================================================

This is the iterative form of Approach-2.

Idea:
We compute answers from smaller numbers to larger numbers.
When processing i:
- state of prefix i/10 is already known
- just combine it with last digit i%10


Why this is nice:
- no recursion stack
- easy transitions
- same classification logic as memoization


Dry run for n = 12:

t[0] = 0

i = 1
remain = t[0] = 0
digit = 1 => unchanged
so t[1] = 0

i = 2
remain = t[0] = 0
digit = 2 => changing
so t[2] = 1, count++

i = 3
digit 3 invalid
so t[3] = 2

i = 10
remain = t[1] = 0
digit = 0 => unchanged
both unchanged => t[10] = 0

i = 12
remain = t[1] = 0
digit = 2 => changing
so t[12] = 1


Complexity:
T.C. : O(n)
S.C. : O(n)


Code teaching notes:
- `t[i / 10]` gives classification of all digits except the last
- `i % 10` gives last digit classification
- combine both to classify entire number
- count only those with final state = 1
*/
