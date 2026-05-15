/*
 * Problem: Find the minimum element in a sorted and rotated array.
 *
 * Key Concepts:
 * 1. A sorted array rotated at some pivot will have two sorted halves.
 *    Example: [4,5,6,7,0,1,2]
 *    - Left half: [4,5,6,7]
 *    - Right half: [0,1,2]
 *    The minimum lies at the "rotation point" where ascending order breaks.
 *
 * 2. Binary Search Pattern:
 *    - Instead of scanning linearly, we use binary search to cut the search space in half.
 *    - At each step, compare nums[mid] with nums[r].
 *
 *    Case A: nums[mid] > nums[r]
 *       → Minimum must be in the right half (strictly after mid).
 *       → Move left pointer: l = mid + 1
 *
 *    Case B: nums[mid] <= nums[r]
 *       → Minimum could be at mid OR in the left half.
 *       → Move right pointer: r = mid
 *
 * 3. Why "r = mid" and not "r = mid - 1"?
 *    - If nums[mid] <= nums[r], mid itself could be the minimum.
 *    - Excluding mid (by doing r = mid - 1) risks skipping the answer.
 *    - Therefore, we keep mid in the search space by setting r = mid.
 *
 * 4. Loop Condition:
 *    - Use while (l < r), not while (l > r).
 *    - We shrink the search space until l == r, which points to the minimum.
 *
 * 5. Return Value:
 *    - At the end, l == r, so nums[l] (or nums[r]) is the minimum element.
 *
 * Pattern Summary:
 * - Initialize l = 0, r = n - 1
 * - While l < r:
 *      mid = l + (r - l) / 2
 *      if nums[mid] > nums[r] → l = mid + 1
 *      else → r = mid
 * - Return nums[l]
 *
 * This is a classic binary search variant:
 * - Instead of searching for a target, we search for the "minimum pivot".
 * - The trick is carefully deciding whether to keep or discard mid.
 */

class Solution {
    public int findMin(int[] nums) {
        int l = 0, r = nums.length - 1;

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (nums[mid] > nums[r]) {
                l = mid + 1;
            } else {
                r = mid; // keep mid in the search space
            }
        }

        return nums[l];
    }
}
