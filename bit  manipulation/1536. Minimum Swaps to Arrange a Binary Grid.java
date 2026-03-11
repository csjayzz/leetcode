// Idea:
// For row i, all cells above main diagonal must be 0.
// So row i needs at least (n - i - 1) trailing zeros.
// Example: n = 3, row 0 needs 2 trailing zeros.
// We can swap only adjacent rows (no column swaps), so:
// - Precompute trailing zeros for each row in endZeros[]
// - For each position i, find nearest row j >= i with endZeros[j] >= need
// - Bring that row up to i using adjacent swaps
// - Cost added = (j - i), which is minimum because we pick nearest valid j

/*
Steps:
1) endZeros[i] = count of continuous 0s from right side of row i
2) For each row i:
   - need = n - i - 1
   - find nearest j >= i such that endZeros[j] >= need
3) If no such j exists, return -1
4) Else add swaps (j - i) and bubble row j up to i in endZeros[]

Dry run (grid = [[0,0,1],[1,1,0],[1,0,0]]):
endZeros = [0,1,2]
i=0, need=2 -> j=2, swaps += 2, endZeros becomes [2,0,1]
i=1, need=1 -> j=2, swaps += 1, endZeros becomes [2,1,0]
i=2, need=0 -> j=2, swaps += 0
Total swaps = 3

Code Explanation:
- n = size of grid, endZeros[] stores trailing zero count for each row.
- First loop fills endZeros by scanning each row from right side.
- For each target row i, we compute need = n - i - 1.
- We search nearest row j (from i onward) where endZeros[j] >= need.
- If no such j exists, arrangement is impossible -> return -1.
- Otherwise required adjacent swaps are (j - i), add to steps.
- Then bubble row j upward to i in endZeros[] to simulate row swaps.
- After processing all rows, steps is the minimum answer.

Complexity:
- Time: O(n^2)
- Space: O(n)
*/

class Solution {
    public int minSwaps(int[][] grid) {

        int n = grid.length;
        int[] endZeros = new int[n];

        for (int i = 0; i < n; i++) {
            int count = 0;
            int j = n - 1;

            while (j >= 0 && grid[i][j] == 0) {
                count++;
                j--;
            }

            endZeros[i] = count;
        }

        int steps = 0;

        for (int i = 0; i < n; i++) {
            int need = n - i - 1;

            int j = i;

            while (j < n && endZeros[j] < need) {
                j++;
            }

            if (j == n) return -1;

            steps += j - i;

            while (j > i) {
                int temp = endZeros[j];
                endZeros[j] = endZeros[j - 1];
                endZeros[j - 1] = temp;
                j--;
            }
        }

        return steps;
    }
}
