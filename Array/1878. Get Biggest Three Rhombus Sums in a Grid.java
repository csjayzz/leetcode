import java.util.*;

/*
Problem explanation:
We are given a grid and need the biggest 3 distinct rhombus border sums.

A rhombus here means a square rotated by 45 degrees.
Only the border cells are counted.

A rhombus is determined by:
1. its center (r, c)
2. its side length

If side = 0, the rhombus is just one single cell.
So every cell itself is also a valid rhombus sum.


Goal:
Find all possible rhombus border sums,
keep only distinct values,
and return the largest 3 in descending order.


Corners of a rhombus centered at (r, c) with side length = side:
- top    = (r - side, c)
- right  = (r, c + side)
- bottom = (r + side, c)
- left   = (r, c - side)

The rhombus is valid only if all these corners are inside the grid.


Main challenge:
How do we quickly compute the border sum of each rhombus?

There are two approaches:

Approach-1:
Walk over all 4 sides cell by cell.
This is straightforward and easy to understand.

Approach-2:
Use diagonal prefix sums so each side can be computed in O(1),
removing the innermost loop.

The active code below uses Approach-2 because it is more optimized.
*/

class Solution {
    public int[] getBiggestThree(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        /*
         * d1[i][j]:
         * prefix sum on the diagonal coming from top-left to bottom-right.
         *
         * Example direction: \
         */
        int[][] d1 = new int[m][n];

        /*
         * d2[i][j]:
         * prefix sum on the diagonal coming from top-right to bottom-left.
         *
         * Example direction: /
         */
        int[][] d2 = new int[m][n];

        // Build d1
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                d1[i][j] = grid[i][j];
                if (i > 0 && j > 0) {
                    d1[i][j] += d1[i - 1][j - 1];
                }
            }
        }

        // Build d2
        for (int i = 0; i < m; i++) {
            for (int j = n - 1; j >= 0; j--) {
                d2[i][j] = grid[i][j];
                if (i > 0 && j + 1 < n) {
                    d2[i][j] += d2[i - 1][j + 1];
                }
            }
        }

        /*
         * TreeSet keeps sums distinct and sorted.
         * We only keep the largest 3 values.
         */
        TreeSet<Integer> st = new TreeSet<>();

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {

                // side = 0 rhombus => single cell
                addToSet(st, grid[r][c]);

                for (int side = 1; r - side >= 0 && r + side < m && c - side >= 0 && c + side < n; side++) {

                    int top_r = r - side, top_c = c;
                    int right_r = r, right_c = c + side;
                    int bottom_r = r + side, bottom_c = c;
                    int left_r = r, left_c = c - side;

                    int sum = 0;

                    /*
                     * We add the four border edges using diagonal prefix sums.
                     *
                     * Edge 1: top -> right      uses d1
                     * Edge 2: right -> bottom   uses d2
                     * Edge 3: left -> bottom    uses d1
                     * Edge 4: top -> left       uses d2
                     */

                    // top to right
                    sum += d1[right_r][right_c];
                    if (top_r - 1 >= 0 && top_c - 1 >= 0) {
                        sum -= d1[top_r - 1][top_c - 1];
                    }

                    // right to bottom
                    sum += d2[bottom_r][bottom_c];
                    if (right_r - 1 >= 0 && right_c + 1 < n) {
                        sum -= d2[right_r - 1][right_c + 1];
                    }

                    // left to bottom
                    sum += d1[bottom_r][bottom_c];
                    if (left_r - 1 >= 0 && left_c - 1 >= 0) {
                        sum -= d1[left_r - 1][left_c - 1];
                    }

                    // top to left
                    sum += d2[left_r][left_c];
                    if (top_r - 1 >= 0 && top_c + 1 < n) {
                        sum -= d2[top_r - 1][top_c + 1];
                    }

                    /*
                     * Each corner gets counted twice because each corner belongs
                     * to two border edges. So subtract all 4 corners once.
                     */
                    sum -= grid[top_r][top_c];
                    sum -= grid[right_r][right_c];
                    sum -= grid[bottom_r][bottom_c];
                    sum -= grid[left_r][left_c];

                    addToSet(st, sum);
                }
            }
        }

        return buildAnswer(st);
    }

    private void addToSet(TreeSet<Integer> st, int val) {
        st.add(val);
        if (st.size() > 3) {
            st.pollFirst();
        }
    }

    private int[] buildAnswer(TreeSet<Integer> st) {
        int[] res = new int[st.size()];
        int idx = 0;

        Iterator<Integer> it = st.descendingIterator();
        while (it.hasNext()) {
            res[idx++] = it.next();
        }

        return res;
    }
}

/*
========================================================================
Approach-1: Brute force border traversal
========================================================================

Idea:
For every possible center (r, c),
try every possible side length.
Then walk along the 4 sides of the rhombus and add border cells manually.

This is the most direct way to think about the problem.


How the 4 loops work:
For a rhombus centered at (r, c) with side = s:

1. top to right
   (r - s, c) -> (r, c + s)

2. right to bottom
   (r, c + s) -> (r + s, c)

3. bottom to left
   (r + s, c) -> (r, c - s)

4. left to top
   (r, c - s) -> (r - s, c)

In your code, one loop with variable k walks exactly one step on each side,
so the whole border is collected.


Dry run on grid = [[1,2,3],[4,5,6],[7,8,9]]

Take center = (1,1), side = 1
Corners:
top = 2
right = 6
bottom = 8
left = 4

Border sum = 2 + 6 + 8 + 4 = 20

That is the largest rhombus sum in this grid.


Complexity:
For every cell, we try all valid side lengths,
and for each rhombus we walk along its border.

T.C. : O(m * n * min(m, n)^2)
S.C. : O(1) apart from the set


Reference code you gave:

class Solution {
    public int[] getBiggestThree(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        TreeSet<Integer> st = new TreeSet<>();

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {

                addToSet(st, grid[r][c]);

                for (int side = 1; r - side >= 0 && r + side < m && c - side >= 0 && c + side < n; side++) {

                    int sum = 0;

                    for (int k = 0; k < side; k++) {
                        sum += grid[r - side + k][c + k];
                        sum += grid[r + k][c + side - k];
                        sum += grid[r + side - k][c - k];
                        sum += grid[r - k][c - side + k];
                    }

                    addToSet(st, sum);
                }
            }
        }

        return buildAnswer(st);
    }

    private void addToSet(TreeSet<Integer> st, int val) {
        st.add(val);
        if (st.size() > 3)
            st.pollFirst();
    }

    private int[] buildAnswer(TreeSet<Integer> st) {
        int[] res = new int[st.size()];
        int idx = 0;

        Iterator<Integer> it = st.descendingIterator();
        while (it.hasNext())
            res[idx++] = it.next();

        return res;
    }
}
*/

/*
========================================================================
Approach-2: Diagonal prefix sums
========================================================================

Why we need this optimization:
In Approach-1, for every rhombus we walk through all border cells.
That repeated traversal is the expensive part.

So the improvement is:
precompute diagonal prefix sums,
then extract each rhombus edge in O(1).


Diagonal prefix meaning:

1. d1 stores sums on '\' diagonals
   d1[i][j] = grid[i][j] + d1[i - 1][j - 1]

2. d2 stores sums on '/' diagonals
   d2[i][j] = grid[i][j] + d2[i - 1][j + 1]

Using these, we can get any diagonal segment sum by subtraction,
just like normal prefix sums.


How each edge is computed:
Suppose center = (r, c), side = s

Corners:
top    = (r - s, c)
right  = (r, c + s)
bottom = (r + s, c)
left   = (r, c - s)

Edges:
1. top -> right    along '\' diagonal => use d1
2. right -> bottom along '/' diagonal => use d2
3. left -> bottom  along '\' diagonal => use d1
4. top -> left     along '/' diagonal => use d2

After adding these 4 edge sums,
every corner is counted twice,
so subtract the 4 corners once.


Dry run on grid = [[1,2,3],[4,5,6],[7,8,9]]

Center = (1,1), side = 1
Corners:
top = (0,1) = 2
right = (1,2) = 6
bottom = (2,1) = 8
left = (1,0) = 4

Edge sums together give:
2 + 6 + 8 + 4, but corners are double-counted through edge extraction,
so subtracting the corners once leaves final border sum = 20.


Complexity:
We try every center and every side length,
but each rhombus sum is now computed in O(1).

T.C. : O(m * n * min(m, n))
S.C. : O(m * n)


Code teaching notes:
- `TreeSet` keeps sums unique and sorted automatically.
- `addToSet` ensures we only keep the top 3 distinct values.
- `buildAnswer` reads the set in descending order.
- This is a classic "optimize repeated range sum queries with prefix sums" idea,
  except here the ranges are diagonal, not horizontal or vertical.
*/
