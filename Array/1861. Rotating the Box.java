/*
1861. Rotating the Box

Problem explanation:
We are given a box represented by a matrix:

- '#' = stone
- '*' = obstacle
- '.' = empty cell

Then the box is rotated 90 degrees clockwise.
After rotation, gravity pulls stones downward.


Key observation:
Instead of rotating first and then making stones fall downward,
we can simulate the same effect in the original box by making stones
slide to the right inside each row.

Why does this work?
Because after a 90 degree clockwise rotation:
- "right" in the original box becomes "down" in the rotated box

So:
1. settle stones as far right as possible within each obstacle-separated segment
2. rotate the matrix clockwise

That directly gives the final answer.


Main idea:

Step 1:
For every row, process from right to left.
Maintain `lastIdx`, the rightmost position where the next stone can settle.

- if we see an obstacle '*', then stones cannot cross it,
  so reset `lastIdx = j - 1`

- if we see a stone '#', move it to `lastIdx` if possible,
  then decrease `lastIdx`

Step 2:
Rotate the settled box 90 degrees clockwise.


Rotation formula:
If original cell is at (i, j),
then after clockwise rotation it goes to:

(j, m - 1 - i)

where original size is m x n
and rotated size becomes n x m
*/

class Solution {
    public char[][] rotateTheBox(char[][] boxGrid) {
        int m = boxGrid.length;
        int n = boxGrid[0].length;

        /*
         * Step 1:
         * Settle stones to the right inside each row.
         * Obstacles split the row into independent segments.
         */
        for (int i = 0; i < m; i++) {
            int lastIdx = n - 1;

            for (int j = n - 1; j >= 0; j--) {
                if (boxGrid[i][j] == '*') {
                    // Stones cannot pass an obstacle.
                    lastIdx = j - 1;
                } else if (boxGrid[i][j] == '#') {
                    /*
                     * If the rightmost free position is empty,
                     * move the stone there.
                     */
                    if (boxGrid[i][lastIdx] == '.') {
                        boxGrid[i][lastIdx] = '#';
                        boxGrid[i][j] = '.';
                        lastIdx--;
                    } else {
                        /*
                         * If lastIdx already has a stone,
                         * this stone is already as far right as it can go
                         * in the current segment.
                         */
                        lastIdx = j - 1;
                    }
                }
            }
        }

        /*
         * Step 2:
         * Rotate the matrix 90 degrees clockwise.
         */
        char[][] rotated = new char[n][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][m - 1 - i] = boxGrid[i][j];
            }
        }

        return rotated;
    }
}

/*
========================================================================
Dry run on Example 2
========================================================================

boxGrid =
[
 ['#', '.', '*', '.'],
 ['#', '#', '*', '.']
]

m = 2, n = 4


Step 1: settle stones to the right

Row 0: ['#', '.', '*', '.']
Start lastIdx = 3

j = 3 -> '.' do nothing
j = 2 -> '*' so lastIdx = 1
j = 1 -> '.' do nothing
j = 0 -> '#'
        lastIdx = 1 and box[0][1] = '.'
        move stone from 0 to 1

Row 0 becomes:
['.', '#', '*', '.']


Row 1: ['#', '#', '*', '.']
Start lastIdx = 3

j = 3 -> '.' do nothing
j = 2 -> '*' so lastIdx = 1
j = 1 -> '#'
        box[1][1] already has '#'
        so this stone is already settled
        lastIdx = 0
j = 0 -> '#'
        box[1][0] already has '#'
        so settled

Row 1 stays:
['#', '#', '*', '.']


After settling:
[
 ['.', '#', '*', '.'],
 ['#', '#', '*', '.']
]


Step 2: rotate clockwise

Original (i, j) -> rotated (j, m - 1 - i)

Final rotated matrix:
[
 ['#', '.'],
 ['#', '#'],
 ['*', '*'],
 ['.', '.']
]

which matches the expected output.


Why the right-slide simulation is correct:
After rotation, gravity acts downward.
In the original orientation, that same effect corresponds to moving
stones toward the right before rotating.
Obstacles stay fixed, so each obstacle-separated segment is independent.


Time complexity:
We scan every cell once to settle stones,
and every cell once again to rotate.

T.C. : O(m * n)

Space complexity:
The rotated matrix takes O(m * n) space.

S.C. : O(m * n)
*/
