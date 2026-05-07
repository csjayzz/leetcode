/*
3660. Jump Game IX

Problem explanation:
From index i:

1. You may jump to the right, to some j > i,
   only if nums[j] < nums[i]

2. You may jump to the left, to some j < i,
   only if nums[j] > nums[i]

For every starting index i,
we need the maximum value that can be reached
after making any number of valid jumps.


Important observation:
The jumps are based only on value comparison, not on distance.

So if there exists:
- some smaller value on the right, we can jump right
- some bigger value on the left, we can jump left

The key is to understand what values become reachable transitively.


Main insight:
For index i, compare:
- maxLeft[i]  = maximum value from index 0 to i
- minRight[i] = minimum value from index i to n - 1

If maxLeft[i] <= minRight[i + 1], then:
- every value on the left side is <= every value on the right side
- so from index i, there is no way to jump right and later come back
  to reach something larger than maxLeft[i]
- therefore the best reachable value is simply maxLeft[i]

Otherwise:
If maxLeft[i] > minRight[i + 1], then some left value is bigger than
some right value. That means the boundary between left and right is
"connected" through valid jumps.

So index i can eventually reach whatever maximum value is reachable
from i + 1 as well.

That leads to a right-to-left DP:
- if the boundary is disconnected, answer resets to maxLeft[i]
- if connected, answer is same as ans[i + 1]


Why prefix/suffix arrays help:
- `maxLeft[i]` quickly tells the largest value available on the left side
- `minRight[i + 1]` quickly tells the smallest value available on the right side

This lets us test whether index i can connect to the component on its right.
*/

class Solution {
    public int[] maxValue(int[] nums) {
        int n = nums.length;

        /*
         * maxLeft[i] = maximum value in nums[0...i]
         * minRight[i] = minimum value in nums[i...n-1]
         */
        int[] maxLeft = new int[n];
        int[] minRight = new int[n];

        maxLeft[0] = nums[0];
        minRight[n - 1] = nums[n - 1];

        for (int i = 1; i < n; i++) {
            maxLeft[i] = Math.max(nums[i], maxLeft[i - 1]);
        }

        for (int i = n - 2; i >= 0; i--) {
            minRight[i] = Math.min(nums[i], minRight[i + 1]);
        }

        int[] ans = new int[n];

        // Last index can only reach within prefix [0...n-1].
        ans[n - 1] = maxLeft[n - 1];

        /*
         * Build answer from right to left.
         */
        for (int i = n - 2; i >= 0; i--) {
            if (maxLeft[i] <= minRight[i + 1]) {
                /*
                 * Left part and right part are disconnected in terms of valid
                 * jump transitions, so best reachable value is confined to
                 * left prefix [0...i].
                 */
                ans[i] = maxLeft[i];
            } else {
                /*
                 * Boundary is connected, so index i can reach the same
                 * maximum value as index i + 1.
                 */
                ans[i] = ans[i + 1];
            }
        }

        return ans;
    }
}

/*
========================================================================
Step-by-step intuition
========================================================================

Think of the array as being split between i and i + 1.

If:
maxLeft[i] <= minRight[i + 1]

then every number on the left side is <= every number on the right side.

That means:
- from left to right, you can only jump to a smaller value
- from right to left, you can only jump to a bigger value

If the entire left side is already <= the entire right side,
then these two sides do not create a useful crossing that lets you
reach some larger component on the right and then come back stronger.

So the best answer for i is just the maximum already present on the left.

But if:
maxLeft[i] > minRight[i + 1]

then there exists:
- a larger value on the left
- a smaller value on the right

This creates a bridge:
some index on the left can jump to the right,
and from that right side we can continue following the same connected region.

So index i shares the same reachable maximum as i + 1.
*/

/*
========================================================================
Dry run on Example 2
========================================================================

nums = [2, 3, 1]

n = 3

maxLeft:
maxLeft[0] = 2
maxLeft[1] = max(3, 2) = 3
maxLeft[2] = max(1, 3) = 3

So:
maxLeft = [2, 3, 3]

minRight:
minRight[2] = 1
minRight[1] = min(3, 1) = 1
minRight[0] = min(2, 1) = 1

So:
minRight = [1, 1, 1]

Now build ans from right to left:

ans[2] = maxLeft[2] = 3

i = 1
check maxLeft[1] <= minRight[2]
3 <= 1 ? no
So ans[1] = ans[2] = 3

i = 0
check maxLeft[0] <= minRight[1]
2 <= 1 ? no
So ans[0] = ans[1] = 3

Final answer:
[3, 3, 3]


Interpretation:
From index 0 (value 2):
2 -> 1 (jump right to smaller)
1 -> 3 (jump left to bigger)
So 3 is reachable.
*/

/*
========================================================================
Dry run on Example 1
========================================================================

nums = [2, 1, 3]

maxLeft = [2, 2, 3]
minRight = [1, 1, 3]

ans[2] = 3

i = 1
maxLeft[1] <= minRight[2]
2 <= 3 ? yes
So ans[1] = maxLeft[1] = 2

i = 0
maxLeft[0] <= minRight[1]
2 <= 1 ? no
So ans[0] = ans[1] = 2

Final answer:
[2, 2, 3]
*/

/*
========================================================================
Time and space complexity
========================================================================

We build:
- one prefix maximum array
- one suffix minimum array
- one answer array

Each is filled in linear time.

T.C. : O(n)
S.C. : O(n)
*/
