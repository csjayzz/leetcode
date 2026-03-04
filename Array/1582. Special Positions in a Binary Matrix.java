class Solution {
    /*
     * Problem in simple words:
     * A position (r, c) is "special" if:
     * 1) mat[r][c] == 1
     * 2) In row r, this is the only 1
     * 3) In column c, this is the only 1
     *
     * Idea:
     * - First pass: count how many 1s are present in each row and each column.
     * - Second pass: for every cell with value 1, check if row count == 1 and column count == 1.
     *   If yes, it is a special position.
     */
    public int numSpecial(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        int[] rows = new int[m];
        int[] cols = new int[n];

        // Count number of 1s in each row and each column.
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (mat[r][c] == 1) {
                    rows[r]++;
                    cols[c]++;
                }
            }
        }

        int specialPos = 0;

        // A cell is special if it is 1 and its row/column each contain exactly one 1.
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (mat[r][c] == 1 && rows[r] == 1 && cols[c] == 1) {
                    specialPos++;
                }
            }
        }

        return specialPos;
    }
}

// Notes:
// 1) Build row and column count arrays for number of 1s.
// 2) Re-scan matrix:
//    a) current cell must be 1
//    b) its row must have exactly one 1
//    c) its column must have exactly one 1
//    If all true, increment answer.
// Time Complexity: O(m * n)
// Space Complexity: O(m + n)
