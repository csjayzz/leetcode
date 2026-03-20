import java.util.*;

/*
3567. Minimum Absolute Difference in Sliding Submatrix

Problem explanation:
For every contiguous k x k submatrix,
we need to find the minimum absolute difference between any two distinct values.

If all values inside that submatrix are the same,
then there are no two distinct values,
and the answer is defined as 0.


Key observation:
To find the minimum absolute difference among a set of numbers,
it is enough to sort the distinct values and check only adjacent pairs.

Why?
Because in sorted order, the closest value to any number must appear next to it.
So the minimum absolute difference can only come from neighboring values
in the sorted distinct list.


So for each k x k submatrix:
1. collect all values
2. keep only distinct values
3. sort them
4. check adjacent differences

Your TreeSet does steps 2 and 3 together:
- it keeps values distinct
- it keeps them sorted automatically


Example:
grid = [[1, -2, 3],
        [2,  3, 5]]
k = 2

Submatrix starting at (0, 0):
[[1, -2],
 [2,  3]]

Distinct sorted values = [-2, 1, 2, 3]
Adjacent differences:
1 - (-2) = 3
2 - 1    = 1
3 - 2    = 1
Minimum = 1


Dry run on Example 3:
grid = [[1,-2,3],
        [2,3,5]]
k = 2

There are 2 possible 2 x 2 submatrices.

1. Top-left = (0, 0)
   values = [1, -2, 2, 3]
   TreeSet = [-2, 1, 2, 3]
   min diff = min(3, 1, 1) = 1

2. Top-left = (0, 1)
   values = [-2, 3, 3, 5]
   distinct TreeSet = [-2, 3, 5]
   min diff = min(5, 2) = 2

Answer = [[1, 2]]


Why this solution is acceptable:
The constraints are small:
m, n <= 30
So a direct check of every k x k submatrix is fast enough.


Time complexity:
There are (m - k + 1) * (n - k + 1) submatrices.
For each one, we inspect k^2 cells and insert them into a TreeSet.
Each insertion costs log(k^2), which is effectively log k.

T.C. : O((m - k + 1) * (n - k + 1) * k^2 * log(k^2))

Space complexity:
At most k^2 distinct elements can be stored in the TreeSet.

S.C. : O(k^2)
*/

class Solution {
    public int[][] minAbsDiff(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] result = new int[m - k + 1][n - k + 1];

        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j <= n - k; j++) {

                /*
                 * TreeSet keeps values:
                 * 1. sorted
                 * 2. distinct
                 */
                TreeSet<Integer> vals = new TreeSet<>();

                // Collect all values inside current k x k submatrix.
                for (int r = i; r <= i + k - 1; r++) {
                    for (int c = j; c <= j + k - 1; c++) {
                        vals.add(grid[r][c]);
                    }
                }

                // If all values are the same, answer stays 0.
                if (vals.size() == 1) {
                    continue;
                }

                int minAbsDiff = Integer.MAX_VALUE;
                Integer prev = null;

                /*
                 * Since values are sorted, only adjacent pairs
                 * need to be checked.
                 */
                for (int val : vals) {
                    if (prev != null) {
                        minAbsDiff = Math.min(minAbsDiff, val - prev);
                    }
                    prev = val;
                }

                result[i][j] = minAbsDiff;
            }
        }

        return result;
    }
}
