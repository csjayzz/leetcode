class Solution {
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
// 1) Build row and column frequency arrays for count of 1s.
// 2) Re-scan matrix and count cells where mat[r][c] == 1, rows[r] == 1, cols[c] == 1.
// Time Complexity: O(m * n)
// Space Complexity: O(m + n)
