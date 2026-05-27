/*
 * Problem: Detect Capital (LeetCode 520)
 * --------------------------------------
 * A word's capitalization is considered correct if it satisfies one of these rules:
 *   1. All letters are uppercase (e.g., "USA").
 *   2. All letters are lowercase (e.g., "leetcode").
 *   3. Only the first letter is uppercase, and the rest are lowercase (e.g., "Google").
 *
 * Task: Return true if the capitalization usage is correct, false otherwise.
 *
 * ------------------------------------------------------------------
 * Thought Process:
 * - We need to check three possible valid patterns.
 * - Count uppercase and lowercase letters, then verify conditions.
 * - Alternatively, explicitly check each rule with loops.
 *
 * ------------------------------------------------------------------
 * Approach 1: Count Uppercase and Lowercase
 * - Traverse the word:
 *     * Count uppercase letters.
 *     * Count lowercase letters.
 * - Valid if:
 *     * All uppercase (small == 0).
 *     * All lowercase (capital == 0).
 *     * Exactly one uppercase and it is the first character.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 *
 * ------------------------------------------------------------------
 * Approach 2: Explicit Rule Checking
 * - Check three rules separately:
 *     * Rule A: All lowercase.
 *     * Rule B: All uppercase.
 *     * Rule C: First uppercase, rest lowercase.
 * - Return true if any rule holds.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 *
 * ------------------------------------------------------------------
 * Dry Run Example:
 *   word = "USA"
 *   capital = 3, small = 0 → valid (all uppercase).
 *
 *   word = "leetcode"
 *   capital = 0, small = 8 → valid (all lowercase).
 *
 *   word = "Google"
 *   capital = 1 (first letter), small = 5 → valid (first uppercase only).
 *
 *   word = "FlaG"
 *   capital = 2, small = 2 → invalid.
 *
 * ------------------------------------------------------------------
 * Pattern Summary:
 * - Either all uppercase, all lowercase, or first uppercase only.
 * - Simple counting or explicit rule checking both solve the problem.
 */

