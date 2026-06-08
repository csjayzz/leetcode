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
