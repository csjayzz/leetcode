/*
 * Problem: Partition Array According to Given Pivot (LeetCode 2161)
 * -----------------------------------------------------------------
 * Goal: Rearrange nums so that:
 *   - All elements < pivot come first.
 *   - All elements == pivot come next.
 *   - All elements > pivot come last.
 *   - Relative order within each group is preserved.
 *
 * -----------------------------------------------------------------
 * Approach 1: Three Lists (smaller, equal, larger)
 * - Traverse nums once, collect elements into three lists.
 * - Concatenate lists in order: smaller + equal + larger.
 * - Convert to result array.
 * Time Complexity: O(n)
 * Space Complexity: O(n) (three lists + result)
 * Stability: ✅ Preserves order
 * Pros: Very clear and easy to understand.
 * Cons: Extra space for three lists.
 *
 * -----------------------------------------------------------------
 * Approach 2: Count + Single Result Array
 * - First pass: count how many elements are smaller and equal.
 * - Allocate result array.
 * - Use three pointers:
 *     * smallerIdx → fill smaller elements
 *     * equalIdx   → fill equal elements
 *     * largerIdx  → fill larger elements
 * - Place elements directly into correct segment.
 * Time Complexity: O(n)
 * Space Complexity: O(n) (only result array)
 * Stability: ✅ Preserves order
 * Pros: More memory-efficient than Approach 1.
 * Cons: Slightly more bookkeeping with indices.
 *
 * -----------------------------------------------------------------
 * Approach 3: Multi-Pass Overwrite
 * - Allocate result array.
 * - Pass 1: write all smaller elements.
 * - Pass 2: write all equal elements.
 * - Pass 3: write all larger elements.
 * Time Complexity: O(n)
 * Space Complexity: O(n) (only result array)
 * Stability: ✅ Preserves order
 * Pros: Very clean and simple to implement.
 * Cons: Requires multiple passes over nums.
 *
 * -----------------------------------------------------------------
 * Approach 4: Two-Pointer Fill (Left & Right)
 * - Use two pointers:
 *     * left pointer → fill smaller elements from start.
 *     * right pointer → fill larger elements from end.
 * - After traversal, fill middle with pivot values.
 * Time Complexity: O(n)
 * Space Complexity: O(n) (result array)
 * Stability: ❌ Does NOT preserve order of larger elements (since filled backwards).
 * Pros: Compact, clever two-pointer idea.
 * Cons: Breaks stability requirement → not fully correct per problem statement.
 *
 * -----------------------------------------------------------------
 * Summary:
 * - Approach 1 → easiest to explain, but uses extra lists.
 * - Approach 2 → efficient, avoids extra lists, uses counts + indices.
 * - Approach 3 → clean multi-pass solution, stable and simple.
 * - Approach 4 → interesting two-pointer trick, but fails stability requirement.
 * Best Practical Choices: Approach 2 or Approach 3 (both O(n), stable, memory-efficient).
 */

/*

 * -----------------------------------------------------------------
 * Approach 3: Two-Pass In-Place Overwrite
 * - Instead of using extra lists or even a separate result array:
 *   1. Create a new array res of same size (to avoid shifting in-place complexities).
 *   2. First pass: write all elements < pivot into res.
 *   3. Second pass: write all elements == pivot into res.
 *   4. Third pass: write all elements > pivot into res.
 * - This preserves order and avoids storing three separate lists.
 *
 * Note: This is technically not "in-place" in the strictest sense
 *       (since we still use one result array), but it avoids multiple
 *       auxiliary lists and works in O(n) time with O(n) space.
 *
 * -----------------------------------------------------------------
 * Dry Run Example:
 *   nums = [9,12,5,10,14,3,10], pivot = 10
 *   Pass 1 → res = [9,5,3,...]
 *   Pass 2 → res = [9,5,3,10,10,...]
 *   Pass 3 → res = [9,5,3,10,10,12,14]
 *   Output = [9,5,3,10,10,12,14]
 *
 * -----------------------------------------------------------------
 * Time Complexity: O(n) → three passes.
 * Space Complexity: O(n) → one result array.
 *
 * -----------------------------------------------------------------
 * Pattern Summary:
 * - Use multiple passes to avoid extra buckets.
 * - Each pass fills one segment of the result array.
 * - Preserves order and is clean to implement.
 */

class Solution {
    public int[] pivotArray(int[] nums, int pivot) {
        int n = nums.length;
        int[] res = new int[n];
        int idx = 0;

        // Pass 1: smaller elements
        for (int num : nums) {
            if (num < pivot) {
                res[idx++] = num;
            }
        }

        // Pass 2: equal elements
        for (int num : nums) {
            if (num == pivot) {
                res[idx++] = num;
            }
        }

        // Pass 3: larger elements
        for (int num : nums) {
            if (num > pivot) {
                res[idx++] = num;
            }
        }

        return res;
    }
}


class Solution {
    public int[] pivotArray(int[] nums, int pivot) {
        int n = nums.length;
        int smCount = 0, eqCount = 0;

        // Count smaller and equal elements
        for (int num : nums) {
            if (num < pivot) smCount++;
            else if (num == pivot) eqCount++;
        }

        int[] res = new int[n];
        int smallIdx = 0;
        int equalIdx = smCount;
        int largeIdx = smCount + eqCount;

        // Fill result array directly
        for (int num : nums) {
            if (num < pivot) {
                res[smallIdx++] = num;
            } else if (num == pivot) {
                res[equalIdx++] = num;
            } else {
                res[largeIdx++] = num;
            }
        }

        return res;
    }
}


class Solution {
    public int[] pivotArray(int[] nums, int pivot) {
        List<Integer> smaller = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();
        List<Integer> larger = new ArrayList<>();

        for (int num : nums) {
            if (num < pivot) {
                smaller.add(num);
            } else if (num > pivot) {
                larger.add(num);
            } else {
                equal.add(num);
            }
        }

        // Concatenate
        List<Integer> ans = new ArrayList<>();
        ans.addAll(smaller);
        ans.addAll(equal);
        ans.addAll(larger);

        // Convert to array
        return ans.stream().mapToInt(Integer::intValue).toArray();
    }
}
