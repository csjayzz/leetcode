/*
1674. Minimum Moves to Make Array Complementary

Problem explanation:
We are given an even-length array.

For every pair:
(nums[i], nums[n - 1 - i])

we want all such pairs to have the same sum.

In one move, we can change any number to any value between 1 and limit.

Goal:
Choose one target pair sum S,
such that all mirrored pairs can be changed to sum S
with minimum total moves.


Why brute force is too slow:
Possible target sums are from 2 to 2 * limit.
For each target sum, checking every pair would cost O(n).
So brute force becomes O(n * limit), which is too large.


Main idea:
For each pair (a, b), instead of checking every target sum one by one,
we describe how many moves this pair needs over ranges of target sums.


For one pair (a, b):

1. By default, any target sum needs 2 moves
   because we can always change both numbers.

2. Some target sums need only 1 move
   If we keep one number fixed and change the other,
   achievable sums form this range:

   [min(a, b) + 1, max(a, b) + limit]

3. Exactly one target sum needs 0 moves:
   a + b


So the cost pattern for one pair is:

- 2 moves for most sums
- 1 move on a continuous range
- 0 moves at one exact point

This is perfect for a difference array / line sweep approach.


Range-cost picture for one pair:

All sums:                    cost = 2
[min(a,b)+1 ... max(a,b)+limit]   reduce by 1 => cost = 1
[a+b]                              reduce by 1 => cost = 0


Then:
- accumulate these effects for all pairs
- prefix sum over target sums from 2 to 2 * limit
- take the minimum total cost
*/

class Solution {
    public int minMoves(int[] nums, int limit) {
        int n = nums.length;

        /*
         * delta[s] stores how the total move count changes
         * when we move from target sum s-1 to s.
         *
         * Final move counts are obtained by prefix summing delta.
         */
        int[] delta = new int[2 * limit + 2];

        for (int i = 0; i < n / 2; i++) {
            int a = nums[i];
            int b = nums[n - 1 - i];

            /*
             * Step 1:
             * Assume this pair costs 2 moves for every target sum
             * in [2, 2*limit].
             */
            delta[2] += 2;
            delta[2 * limit + 1] -= 2;

            /*
             * Step 2:
             * One-move range:
             * [min(a, b) + 1, max(a, b) + limit]
             *
             * In this whole range, cost becomes 1 instead of 2,
             * so subtract 1 over the range.
             */
            int low = Math.min(a, b) + 1;
            int high = Math.max(a, b) + limit;
            delta[low] -= 1;
            delta[high + 1] += 1;

            /*
             * Step 3:
             * Exact zero-move point:
             * target sum = a + b
             *
             * At this one point, cost becomes 0 instead of 1,
             * so subtract another 1 only at that point.
             */
            delta[a + b] -= 1;
            delta[a + b + 1] += 1;
        }

        int minMoves = n;
        int currentMoves = 0;

        /*
         * Prefix sum gives total cost for each target sum.
         * Choose the minimum over all possible sums [2 ... 2*limit].
         */
        for (int sum = 2; sum <= 2 * limit; sum++) {
            currentMoves += delta[sum];
            minMoves = Math.min(minMoves, currentMoves);
        }

        return minMoves;
    }
}

/*
========================================================================
How one pair behaves
========================================================================

Take one pair (a, b).
Suppose a = 2, b = 4, limit = 5

Possible target sums are 2 to 10.

Current sum = 6
So:
- target 6 needs 0 moves

One-move range:
[min(2,4)+1, max(2,4)+limit]
= [3, 9]

Why?
- Keep 2 fixed, change 4 to something in [1,5]
  sums become [3,7]
- Keep 4 fixed, change 2 to something in [1,5]
  sums become [5,9]
- combined range = [3,9]

So cost table becomes:
sum 2     -> 2 moves
sum 3..5  -> 1 move
sum 6     -> 0 moves
sum 7..9  -> 1 move
sum 10    -> 2 moves

Instead of filling all these one by one,
we update them using the difference array.
*/

/*
========================================================================
Dry run on Example 1
========================================================================

nums = [1, 2, 4, 3]
limit = 4

Pairs:
(1, 3)
(2, 4)


Pair 1: (1, 3)

Default:
all sums [2..8] cost +2

One-move range:
[min(1,3)+1, max(1,3)+4]
=[2, 7]
subtract 1 there

Zero-move point:
1 + 3 = 4
subtract another 1 at sum 4

Cost contribution of pair (1,3):
sum 2,3 -> 1 move
sum 4   -> 0 move
sum 5,6,7 -> 1 move
sum 8   -> 2 moves


Pair 2: (2, 4)

Default:
all sums [2..8] cost +2

One-move range:
[min(2,4)+1, max(2,4)+4]
=[3, 8]
subtract 1 there

Zero-move point:
2 + 4 = 6
subtract another 1 at sum 6

Cost contribution of pair (2,4):
sum 2   -> 2 moves
sum 3,4,5 -> 1 move
sum 6   -> 0 move
sum 7,8 -> 1 move


Now combine both pairs:
sum 2 -> 1 + 2 = 3
sum 3 -> 1 + 1 = 2
sum 4 -> 0 + 1 = 1
sum 5 -> 1 + 1 = 2
sum 6 -> 1 + 0 = 1
sum 7 -> 1 + 1 = 2
sum 8 -> 2 + 1 = 3

Minimum = 1

Answer = 1
*/

/*
========================================================================
Why the difference array works
========================================================================

Difference array is useful when we want to apply many range updates.

If we want to add value x to range [l, r], we do:
delta[l] += x
delta[r + 1] -= x

Later, prefix sum reconstructs the actual values.

Here we use the same idea for move costs:
- add 2 to all sums
- subtract 1 from one-move range
- subtract 1 at zero-move point

After processing every pair, prefix sum gives:
total moves needed for each target sum.
*/

/*
========================================================================
Time and space complexity
========================================================================

Let n = nums.length

We process each pair once: O(n)
Then scan all target sums from 2 to 2*limit: O(limit)

Since limit <= 10^5 and n <= 10^5,
this is efficient.

T.C. : O(n + limit)
S.C. : O(limit)

Your original comment said O(n), but the more precise complexity is:
O(n + limit)
because of the final scan over all possible sums.
*/
