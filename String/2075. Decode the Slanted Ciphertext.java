/*
2075. Decode the Slanted Ciphertext

Problem explanation:
The encoded string was formed using a matrix with a fixed number of rows.

Encoding process:
1. Put the original text diagonally in the matrix:
   top-left to bottom-right
2. Fill remaining empty cells with spaces
3. Read the matrix row by row to create encodedText

Now we are given:
- encodedText
- rows

We need to recover the original text.


Key observation:
If encodedText is read row-wise from the matrix,
then we can rebuild that matrix first.

Once the matrix is rebuilt,
the original text is exactly the diagonal traversal:
- start from column 0 of row 0
- move diagonally down-right
- then start from column 1 of row 0
- and so on


How many columns are there?
If encodedText length = l,
and the matrix has `rows` rows,
then:

columns = l / rows

This works because encodedText contains every cell of the matrix.


Why do we remove trailing spaces?
During encoding, empty cells were filled with spaces.
Those extra spaces may appear at the end of the decoded diagonal read.
The problem guarantees the original text had no trailing spaces,
so we trim only trailing spaces.
*/

class Solution {
    public String decodeCiphertext(String encodedText, int rows) {
        /*
         * Active solution: Approach-2
         * We decode directly from the encoded string without explicitly
         * rebuilding the full matrix.
         */
        int l = encodedText.length();
        int columns = l / rows;

        StringBuilder originalText = new StringBuilder();

        /*
         * Suppose encodedText is the row-wise flattening of a matrix
         * with `rows` rows and `columns` columns.
         *
         * Index jump to move diagonally down-right in that flattened form:
         * one row down  => +columns
         * one column right => +1
         * total jump => columns + 1
         */
        for (int col = 0; col < columns; col++) {
            for (int j = col; j < l; j += (columns + 1)) {
                originalText.append(encodedText.charAt(j));
            }
        }

        // Remove trailing spaces added during encoding.
        while (originalText.length() > 0
                && originalText.charAt(originalText.length() - 1) == ' ') {
            originalText.deleteCharAt(originalText.length() - 1);
        }

        return originalText.toString();
    }
}

/*
========================================================================
Approach-1: Rebuild the matrix, then read diagonally
========================================================================

Idea:
1. Compute number of columns = encodedText.length() / rows
2. Fill a rows x columns matrix row by row from encodedText
3. Read the matrix diagonally starting from each column in the first row
4. Remove trailing spaces


Why this works:
The matrix used in encoding is exactly the one whose row-wise traversal is
encodedText. Once we reconstruct it, decoding becomes direct.


Dry run for Example 1:
encodedText = "ch   ie   pr"
rows = 3

length = 12
columns = 12 / 3 = 4

Rebuilt matrix:
row 0: c h _ _
row 1: _ i e _
row 2: _ _ p r
(_ means space)

Now read diagonally:
start col 0 => c i p
start col 1 => h e r
start col 2 => _
start col 3 => _

Combined = "cipher  "
After trimming trailing spaces => "cipher"


Time complexity:
We visit each character a constant number of times.

T.C. : O(l), where l = encodedText.length()
S.C. : O(l), because of the matrix


Code you gave for Approach-1:

class Solution {
    public String decodeCiphertext(String encodedText, int rows) {
        if (rows == 1) return encodedText;

        int l = encodedText.length();
        int columns = l / rows;

        char[][] mat = new char[rows][columns];

        int idx = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                mat[i][j] = encodedText.charAt(idx++);
            }
        }

        StringBuilder originalText = new StringBuilder();

        for (int col = 0; col < columns; col++) {
            int i = 0, j = col;

            while (i < rows && j < columns) {
                originalText.append(mat[i][j]);
                i++;
                j++;
            }
        }

        while (originalText.length() > 0 &&
               originalText.charAt(originalText.length() - 1) == ' ') {
            originalText.deleteCharAt(originalText.length() - 1);
        }

        return originalText.toString();
    }
}
*/

/*
========================================================================
Approach-2: Decode directly without building the matrix
========================================================================

This is the optimized space version.

Main trick:
In the flattened row-wise matrix representation,
moving diagonally down-right means:

current index + columns + 1

Why?
- moving to next row adds `columns`
- moving to next column adds `1`

So every diagonal can be read directly from encodedText
without storing the matrix separately.


Dry run for Example 2:
encodedText = "iveo    eed   l te   olc"
rows = 4

Let columns = length / rows

Now start from each top-row column:

start col 0:
take positions 0, 0 + (columns + 1), 0 + 2*(columns + 1), ...

start col 1:
take positions 1, 1 + (columns + 1), ...

and so on

This reconstructs:
"i love leetcode   "

After removing trailing spaces:
"i love leetcode"


Why this is better:
We avoid the extra matrix.
We still read the same characters in the same diagonal order.


Time complexity:
T.C. : O(l)
S.C. : O(1) extra space
Ignoring the output StringBuilder.


Code teaching notes:
- `columns = l / rows` reconstructs matrix width
- outer loop chooses the starting column of each diagonal
- inner loop jumps by `columns + 1`
- trailing spaces are removed at the end because they are padding


Code you gave for Approach-2:

class Solution {
    public String decodeCiphertext(String encodedText, int rows) {
        int l = encodedText.length();
        int columns = l / rows;

        StringBuilder originalText = new StringBuilder();

        for (int col = 0; col < columns; col++) {
            for (int j = col; j < l; j += (columns + 1)) {
                originalText.append(encodedText.charAt(j));
            }
        }

        while (originalText.length() > 0 &&
               originalText.charAt(originalText.length() - 1) == ' ') {
            originalText.deleteCharAt(originalText.length() - 1);
        }

        return originalText.toString();
    }
}
*/
