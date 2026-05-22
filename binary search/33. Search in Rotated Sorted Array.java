/*
 * Problem: Search in Rotated Sorted Array (LeetCode 33)
 * -----------------------------------------------------
 * You are given an ascending sorted array that may be rotated at some pivot.
 * Task: Find the index of target in O(log n) time, or return -1 if not found.
 *
 * Example:
 *   nums = [4,5,6,7,0,1,2], target = 0 → Output = 4
 *   nums = [4,5,6,7,0,1,2], target = 3 → Output = -1
 *
 * -----------------------------------------------------
 * Thought Process:
 * - A rotated sorted array still has one half sorted at any point.
 * - Using binary search, we can decide which half to explore.
 * - Key observation:
 *   * Either left half [low..mid] is sorted OR right half [mid..high] is sorted.
 *   * Once we know which half is sorted, we check if target lies inside it.
 *   * If yes → shrink search space to that half.
 *   * If no → search the other half.
 *
 * -----------------------------------------------------
 * Binary Search Pattern:
 * 1. Compute mid = low + (high - low) / 2.
 * 2. If nums[mid] == target → return mid.
 * 3. If left half is sorted (nums[low] <= nums[mid]):
 *      - If target lies in [nums[low]..nums[mid-1]] → move high = mid - 1.
 *      - Else → move low = mid + 1.
 * 4. Else (right half is sorted):
 *      - If target lies in [nums[mid+1]..nums[high]] → move low = mid + 1.
 *      - Else → move high = mid - 1.
 * 5. Continue until low > high.
 *
 * -----------------------------------------------------
 * Dry Run Example:
 *   nums = [4,5,6,7,0,1,2], target = 0
 *   low=0, high=6 → mid=3 → nums[3]=7
 *   Left half [4..7] is sorted, but target=0 not in range → search right half.
 *   low=4, high=6 → mid=5 → nums[5]=1
 *   Left half [0..1] is sorted, target=0 lies inside → shrink to left.
 *   low=4, high=5 → mid=4 → nums[4]=0 → found at index 4.
 *
 * -----------------------------------------------------
 * Time Complexity:
 * - O(log n) → binary search halves the search space each step.
 *
 * Space Complexity:
 * - O(1) → only pointers and variables used.
 *
 * -----------------------------------------------------
 * Pattern Summary:
 * - Rotated sorted array still has one sorted half.
 * - Use binary search to decide which half to explore.
 * - Guarantees O(log n) search time.
 */
