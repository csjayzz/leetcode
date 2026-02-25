import java.util.Arrays;
import java.util.Comparator;

/**
 * LeetCode 2099 - Find Subsequence of Length K With the Largest Sum
 *
 * Goal:
 * Pick k elements whose sum is maximum, but return them in original relative order
 * (because answer must be a subsequence, not just a set of values).
 *
 * ---------------------------------------------------------------
 * Core idea
 * ---------------------------------------------------------------
 * 1) Attach each value with its original index: [index, value].
 * 2) Sort all pairs by value descending so best values come first.
 * 3) Keep first k pairs (these are the k largest values).
 * 4) Sort only those k pairs by index ascending to restore subsequence order.
 * 5) Extract values.
 *
 * ---------------------------------------------------------------
 * Comparator functions used (important)
 * ---------------------------------------------------------------
 * A Comparator defines ordering between 2 objects:
 *   compare(x, y) returns:
 *   - negative -> x comes before y
 *   - zero     -> same order priority
 *   - positive -> x comes after y
 *
 * 1) Arrays.sort(vec, (a, b) -> Integer.compare(b[1], a[1]))
 *    - vec is int[][], each element is int[] pair: [index, value].
 *    - a and b are two pairs.
 *    - a[1] and b[1] are values.
 *    - Integer.compare(b[1], a[1]) gives DESCENDING order by value
 *      (we compare b to a, reverse of ascending).
 *
 * 2) Arrays.sort(vec, 0, k, Comparator.comparingInt(a -> a[0]))
 *    - Sort only subarray vec[0..k-1].
 *    - Comparator.comparingInt(keyExtractor) creates comparator by int key.
 *    - keyExtractor: a -> a[0] means key is original index.
 *    - Result: top-k elements are reordered by index ascending,
 *      which restores original relative order => valid subsequence.
 *
 * Why Integer.compare instead of (b[1] - a[1])?
 * - Safer and clearer; subtraction can overflow in general cases.
 *
 * ---------------------------------------------------------------
 * Dry run
 * ---------------------------------------------------------------
 * nums = [2,1,3,3], k = 2
 * pairs: [0,2], [1,1], [2,3], [3,3]
 * sort by value desc -> [2,3], [3,3], [0,2], [1,1]
 * top k -> [2,3], [3,3]
 * sort top k by index -> [2,3], [3,3]
 * extract values -> [3,3]
 *
 * ---------------------------------------------------------------
 * Complexity
 * ---------------------------------------------------------------
 * Time: O(n log n)   (dominant first sort)
 * Space: O(n)        (pairs array + output)
 */
class Solution {
    public int[] maxSubsequence(int[] nums, int k) {
        int n = nums.length;
        if (k == n) {
            return nums;
        }

        // pair[i] = [originalIndex, value]
        int[][] pair = new int[n][2];
        for (int i = 0; i < n; i++) {
            pair[i][0] = i;
            pair[i][1] = nums[i];
        }

        // Step 1: highest values first
        Arrays.sort(pair, (a, b) -> Integer.compare(b[1], a[1]));

        // Step 2: among chosen top-k, restore original order by index
        Arrays.sort(pair, 0, k, Comparator.comparingInt(a -> a[0]));

        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = pair[i][1];
        }
        return ans;
    }
}
