import java.util.HashSet;

/*
36. Valid Sudoku

Problem explanation:
We are given a 9 x 9 Sudoku board.
Some cells are filled with digits '1' to '9',
and some cells are empty, represented by '.'.

We only need to check whether the current board is valid.
We do NOT need to solve the Sudoku.


A board is valid if:
1. each row has no repeated digit
2. each column has no repeated digit
3. each 3 x 3 box has no repeated digit


Important note:
The board may be incomplete and still valid.
So empty cells '.' are simply ignored.


Main idea:
Check the three Sudoku rules separately:

1. check every row
2. check every column
3. check every 3 x 3 sub-box

For each check, use a HashSet:
- if a digit is seen for the first time, insert it
- if insertion fails, that means the digit is repeated
- repeated digit => invalid board


Why HashSet works:
HashSet stores unique values only.
So it is perfect for duplicate detection.
*/

class Solution {
    public boolean isValidSudoku(char[][] board) {

        /*
         * Step 1:
         * Check all rows and columns.
         */
        for (int i = 0; i < 9; i++) {
            HashSet<Character> row = new HashSet<>();
            HashSet<Character> col = new HashSet<>();

            for (int j = 0; j < 9; j++) {

                // Row check
                if (board[i][j] != '.' && !row.add(board[i][j])) {
                    return false;
                }

                // Column check
                if (board[j][i] != '.' && !col.add(board[j][i])) {
                    return false;
                }
            }
        }

        /*
         * Step 2:
         * Check all 3 x 3 boxes.
         *
         * boxRow and boxCol represent which box we are in:
         * (0,0), (0,1), (0,2)
         * (1,0), (1,1), (1,2)
         * (2,0), (2,1), (2,2)
         *
         * Start of each box:
         * row = boxRow * 3
         * col = boxCol * 3
         */
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                HashSet<Character> box = new HashSet<>();

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        char val = board[boxRow * 3 + i][boxCol * 3 + j];

                        if (val != '.' && !box.add(val)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}

/*
========================================================================
Dry run on Example 1
========================================================================

Take the first row:
[5,3,.,.,7,.,.,.,.]

HashSet row:
- add 5
- add 3
- skip .
- skip .
- add 7
- skip remaining dots

No duplicate, so row is valid.


Take first column:
[5,6,.,8,4,7,.,.,.]

HashSet col:
- add 5
- add 6
- skip .
- add 8
- add 4
- add 7
- skip remaining dots

No duplicate, so column is valid.


Take top-left 3 x 3 box:
[5,3,.]
[6,.,.]
[.,9,8]

HashSet box:
- add 5
- add 3
- skip .
- add 6
- skip .
- skip .
- skip .
- add 9
- add 8

No duplicate, so box is valid.

If every row, column, and box passes like this,
the board is valid.


Why Example 2 fails:
There are two '8' values in the top-left 3 x 3 box.
When the second 8 is inserted into the box set,
`add()` returns false, so we return false.


Time complexity:
The board size is fixed: 9 x 9.
So practically the runtime is constant.

T.C. : O(1)

If we describe it more generally for an n x n board,
then it is O(n^2).


Space complexity:
We use sets that store at most 9 characters at a time.

S.C. : O(1)
*/
