/*
3070. Count Submatrices with Top-Left Element and Sum Less Than k

Problem explanation:
We need to count submatrices that:
1. must contain the top-left element of the grid, which is grid[0][0]
2. have sum <= k


Very important observation:
If a submatrix must contain the top-left corner,
then its top-left corner is fixed at (0, 0).

So every valid submatrix looks like this:
from (0, 0) to (row, col)

That means we do not need to check all possible submatrices.
We only need to check prefix submatrices.


Example:
If row = 1 and col = 2,
then the submatrix is:
all cells from grid[0][0] to grid[1][2]

So the problem becomes:
"For how many cells (row, col) is the sum of rectangle (0,0) -> (row,col) <= k?"


This is exactly a 2D prefix sum problem.

2D prefix sum meaning:
prefix[row][col] stores the sum of all elements
inside the rectangle:
(0, 0) to (row, col)

Formula:
prefix[row][col] = grid[row][col]
                 + prefix[row - 1][col]
                 + prefix[row][col - 1]
                 - prefix[row - 1][col - 1]

Why subtract prefix[row - 1][col - 1]?
Because it gets added twice:
once from top and once from left.


Dry run:
grid = [[7,6,3],
        [6,6,1]]
k = 18

prefix[0][0] = 7
prefix[0][1] = 7 + 6 = 13
prefix[0][2] = 13 + 3 = 16

prefix[1][0] = 7 + 6 = 13
prefix[1][1] = 6 + 13 + 13 - 7 = 25
prefix[1][2] = 1 + 16 + 25 - 13 = 29

So prefix matrix becomes:
[[7, 13, 16],
 [13, 25, 29]]

Now count values <= 18:
7, 13, 16, 13
Total = 4


Why this works:
Every required submatrix must start at (0,0).
So each one corresponds to exactly one prefix sum cell.


Time complexity:
O(m * n)

Space complexity:
O(m * n)
*/

class Solution {
    public int countSubmatrices(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] prefix = new int[m][n];

        // Build 2D prefix sum matrix.
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                prefix[row][col] = grid[row][col];

                if (row > 0) {
                    prefix[row][col] += prefix[row - 1][col];
                }
                if (col > 0) {
                    prefix[row][col] += prefix[row][col - 1];
                }
                if (row > 0 && col > 0) {
                    prefix[row][col] -= prefix[row - 1][col - 1];
                }
            }
        }

        int ans = 0;

        /*
         * Every prefix[row][col] represents the sum of submatrix
         * from (0,0) to (row,col).
         * Count how many such sums are <= k.
         */
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (prefix[row][col] <= k) {
                    ans++;
                }
            }
        }

        return ans;
    }
}
