/*
3643. Flip Square Submatrix Vertically

Problem explanation:
We are given a matrix and a square submatrix inside it.

- (x, y) is the top-left corner of that square
- k is the side length

We need to flip that square vertically.

What does "flip vertically" mean here?
It means reverse the order of rows inside that square.

So:
- the first row of the square swaps with the last row
- the second row swaps with the second last row
- and so on

Only the columns from y to y + k - 1 are affected.
Everything outside the square stays unchanged.


Example:
If the selected square rows are:
row A
row B
row C

After vertical flip:
row C
row B
row A


Main idea:
Use two row pointers:
- startRow = top row of the square
- endRow   = bottom row of the square

While startRow < endRow:
- swap all k elements between these two rows
- move startRow down
- move endRow up

This is an in-place solution.


Dry run:
grid =
[[1,2,3,4],
 [5,6,7,8],
 [9,10,11,12],
 [13,14,15,16]]

x = 1, y = 0, k = 3

Selected square is:
[[5,6,7],
 [9,10,11],
 [13,14,15]]

Swap row 1 with row 3 for columns 0 to 2:
[[1,2,3,4],
 [13,14,15,8],
 [9,10,11,12],
 [5,6,7,16]]

Now startRow = 2, endRow = 2, stop.

Answer:
[[1,2,3,4],
 [13,14,15,8],
 [9,10,11,12],
 [5,6,7,16]]


Time complexity:
We swap about k / 2 row pairs,
and for each pair we process k columns.

T.C. : O(k^2)

Space complexity:
S.C. : O(1)
*/

class Solution {
    public int[][] reverseSubmatrix(int[][] grid, int x, int y, int k) {
        int startRow = x;
        int endRow = x + k - 1;

        int startCol = y;
        int endCol = y + k - 1;

        /*
         * Swap the top row of the square with the bottom row,
         * then move inward.
         */
        while (startRow < endRow) {
            for (int j = startCol; j <= endCol; j++) {
                int temp = grid[startRow][j];
                grid[startRow][j] = grid[endRow][j];
                grid[endRow][j] = temp;
            }

            startRow++;
            endRow--;
        }

        return grid;
    }
}
