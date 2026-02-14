class Solution {
    private double solve(int poured, int query_row,int query_glass){
        int i = query_row;
        int j = query_glass;
        if(i<0||j<0||i<j){
            return 0.0;
        }
        if(i==0&&j==0)return poured;
        double left_up = (solve(poured,i-1,j-1)-1)/2.0;//upper left glass
        double right_up = (solve(poured,i-1,j)-1)/2.0;
        if(left_up<0)left_up = 0;
        if(right_up<0)right_up = 0;

        return left_up + right_up;
    }
    public double champagneTower(int poured, int query_row, int query_glass) {
          return Math.min(1.0,solve(poured,query_row,query_glass));                                                                                                                           
    }
} 
//without memoization, this solution will TLE. We can use a map/array to store the results of subproblems to avoid redundant calculations.
//the time complexity of this solution is O(query_row^2) due to the recursive calls, and the space complexity is also O(query_row^2) due to the memoization storage.
//the time complexity is query_row^2 because in the worst case, we will compute the amount of champagne in each glass up to the query_row, which involves computing for all glasses in those rows. The space complexity is also O(query_row^2) because we need to store the results for each glass in those rows to avoid redundant calculations.

/*
Memoized Approach (added separately, original code kept above)

Idea:
1) Let f(r, c) be the total champagne reaching glass (r, c) before capping it to 1.
2) Transition:
   f(r, c) = max(0, (f(r-1, c-1)-1)/2) + max(0, (f(r-1, c)-1)/2)
3) Base case:
   f(0, 0) = poured
4) Use memo[r][c] so each state is computed once.

Why memoization helps:
- The plain recursion recalculates the same (r, c) many times.
- Memoization stores answers for reuse, reducing repeated work.

Complexity:
- Time: O(query_row^2), because there are about query_row*(query_row+1)/2 states.
- Space: O(query_row^2) for memo + recursion stack up to O(query_row).
*/
class SolutionMemoized {
    private Double[][] memo;

    private double solveMemo(int poured, int r, int c) {
        if (r < 0 || c < 0 || c > r) return 0.0;
        if (r == 0 && c == 0) return poured;

        if (memo[r][c] != null) return memo[r][c];

        double leftUp = Math.max(0.0, (solveMemo(poured, r - 1, c - 1) - 1.0) / 2.0);
        double rightUp = Math.max(0.0, (solveMemo(poured, r - 1, c) - 1.0) / 2.0);

        memo[r][c] = leftUp + rightUp;
        return memo[r][c];
    }

    public double champagneTower(int poured, int query_row, int query_glass) {
        memo = new Double[query_row + 1][query_row + 1];
        return Math.min(1.0, solveMemo(poured, query_row, query_glass));
    }
}

class Solution {
     //double[][] t = new double[101][101];//query glass /query row <=100
     double [][] memo;
    private double solve(int poured, int query_row,int query_glass){
        int i = query_row;
        int j = query_glass;
        if(i<0||j<0||i<j){
            return 0.0;
        }
        if(memo[i][j]!=-1) return memo[i][j];
        if(i==0&&j==0)return poured;
        double left_up = (solve(poured,i-1,j-1)-1)/2.0;//upper left glass
        double right_up = (solve(poured,i-1,j)-1)/2.0;
        if(left_up<0)left_up = 0;
        if(right_up<0)right_up = 0;
        memo[i][j] = left_up + right_up;
        return memo[i][j];
    }
    public double champagneTower(int poured, int query_row, int query_glass) {
          memo = new double[query_row + 1][query_row + 1];
          for(int i = 0;i<memo.length;i++){
            for(int j = 0;j<memo.length;j++){
                memo[i][j] = -1;
            }
          }
          return Math.min(1.0,solve(poured,query_row,query_glass));                                                                                                                           
    }
}

class Solution {
    public double champagneTower(int poured, int query_row, int query_glass) {
        // Create a 2D array with enough space (extra buffer to avoid index issues)
        double[][] tower = new double[102][102];
        
        // Initial poured champagne goes into the top glass
        tower[0][0] = poured;
        
        // Fill the tower row by row
        for (int r = 0; r <= query_row; r++) {
            for (int c = 0; c <= r; c++) {
                if (tower[r][c] > 1.0) {
                    double excess = (tower[r][c] - 1.0) / 2.0;
                    tower[r + 1][c] += excess;
                    tower[r + 1][c + 1] += excess;
                }
            }
        }
        
        // The glass can hold at most 1 unit
        return Math.min(1.0, tower[query_row][query_glass]);
    }
}