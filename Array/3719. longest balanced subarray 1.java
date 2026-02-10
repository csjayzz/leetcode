import java.util.Map;
import java.util.*;
import java.util.HashMap;

/*
3719. Longest Balanced Subarray I

Revision Notes:
- You are given an integer array nums.
- A subarray is considered balanced when:
  number of distinct even values == number of distinct odd values.
- Return the maximum length of such a subarray.

Example idea:
- nums = [2, 4, 1, 3]
  distinct evens = {2,4} (2), distinct odds = {1,3} (2)
  whole subarray is balanced, length = 4.

------------------------------------------------------------
Brute Force Intuition (your approach):
- Try every starting index i.
- Expand ending index j.
- Track distinct even values and distinct odd values in two sets.
- Whenever set sizes are equal, update answer.

Why it works:
- Every subarray nums[i..j] is checked exactly once.
- Set size gives distinct-count directly for even and odd groups.

Complexity:
- Time: O(n^2) average (hash operations are average O(1)).
- Space: O(n) in worst case for the sets.
------------------------------------------------------------

Optimal Approach Note:
- For this "distinct even count == distinct odd count" condition,
  there is no simple prefix-sum trick that yields true O(n), because
  distinct counts are not additive over ranges.
- A practical optimal approach for interview/contest constraints is
  still O(n^2), but with lower constants:
  use coordinate compression + timestamp arrays instead of rebuilding
  HashSet objects for each i.
*/

class Solution {

    // Practical optimized O(n^2) approach (recommended implementation)
    public int longestBalanced(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;

        // Coordinate compression for values to [0..m-1]
        Map<Integer, Integer> idMap = new HashMap<>();
        int[] ids = new int[n];
        int nextId = 0;

        for (int i = 0; i < n; i++) {
            Integer id = idMap.get(nums[i]);
            if (id == null) {
                id = nextId++;
                idMap.put(nums[i], id);
            }
            ids[i] = id;
        }

        int[] seenEven = new int[nextId];
        int[] seenOdd = new int[nextId];

        int answer = 0;
        int stamp = 1;

        for (int i = 0; i < n; i++) {
            int evenDistinct = 0;
            int oddDistinct = 0;

            for (int j = i; j < n; j++) {
                int id = ids[j];
                int val = nums[j];

                if ((val & 1) == 0) {
                    if (seenEven[id] != stamp) {
                        seenEven[id] = stamp;
                        evenDistinct++;
                    }
                } else {
                    if (seenOdd[id] != stamp) {
                        seenOdd[id] = stamp;
                        oddDistinct++;
                    }
                }

                if (evenDistinct == oddDistinct) {
                    answer = Math.max(answer, j - i + 1);
                }
            }

            stamp++;
        }

        return answer;
    }

    // Your original HashSet solution (kept for revision)
    public int longestBalancedBrute(int[] nums) {
        int maxL = 0;

        for (int i = 0; i < nums.length; i++) {
            HashSet<Integer> even = new HashSet<>();
            HashSet<Integer> odd = new HashSet<>();

            for (int j = i; j < nums.length; j++) {
                if (nums[j] % 2 == 0) {
                    even.add(nums[j]);
                } else {
                    odd.add(nums[j]);
                }

                if (even.size() == odd.size()) {
                    maxL = Math.max(maxL, j - i + 1);
                }
            }
        }

        return maxL;
    }
}
