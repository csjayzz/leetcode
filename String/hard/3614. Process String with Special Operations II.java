/*
 * Problem: Process String with Special Operations II (LeetCode 3614)
 * ------------------------------------------------------------------
 * You are given a string s with lowercase letters and special ops: '*', '#', '%'.
 * Build result string by:
 *   - Letter: append to result
 *   - '*': remove last char (if exists)
 *   - '#': duplicate result (result = result + result)
 *   - '%': reverse result
 * Return kth character of final result, or '.' if out of bounds.
 *
 * Constraints:
 * - s.length up to 1e5
 * - k up to 1e15
 * - Final length <= 1e15
 *
 * ------------------------------------------------------------------
 * Key Insight:
 * - Final string can be astronomically large → cannot construct directly.
 * - Instead, compute length L of final string, then reverse simulate operations.
 * - Reverse simulation traces back where kth character originated.
 *
 * ------------------------------------------------------------------
 * Reverse Simulation Rules:
 * - Letter (a–z):
 *     Forward: L++
 *     Reverse: L--, if L == k → return this letter
 *
 * - '*':
 *     Forward: if L>0 then L--
 *     Reverse: L++ (undo removal), k unchanged
 *
 * - '#':
 *     Forward: L = 2L
 *     Reverse: L = L/2
 *              if k >= L → k = k - L (k was in second half)
 *              else k unchanged
 *
 * - '%':
 *     Forward: reverse string
 *     Reverse: same length, but index mapping changes
 *              k = L - k - 1
 *
 * ------------------------------------------------------------------
 * Dry Run 1:
 *   s = "a#b%*", k = 1
 *   Forward length:
 *     'a' → L=1
 *     '#' → L=2
 *     'b' → L=3
 *     '%' → L=3
 *     '*' → L=2
 *   Final L=2, k=1 valid.
 *   Reverse:
 *     '*' → L=3
 *     '%' → k=3-1-1=1
 *     'b' → L=2, L!=k
 *     '#' → L=1, k>=1 → k=0
 *     'a' → L=0, L==k → return 'a'
 *   Answer = 'a'
 *
 * Dry Run 2:
 *   s = "cd%#*#", k = 3
 *   Forward length:
 *     'c' → L=1
 *     'd' → L=2
 *     '%' → L=2
 *     '#' → L=4
 *     '*' → L=3
 *     '#' → L=6
 *   Final L=6, k=3 valid.
 *   Reverse:
 *     '#' → L=3, k>=3 → k=0
 *     '*' → L=4
 *     '#' → L=2, k=0
 *     '%' → k=2-0-1=1
 *     'd' → L=1, L==k → return 'd'
 *   Answer = 'd'
 *
 * Dry Run 3:
 *   s = "z*#", k = 0
 *   Forward length:
 *     'z' → L=1
 *     '*' → L=0
 *     '#' → L=0
 *   Final L=0, k=0 out of bounds.
 *   Answer = '.'
 *
 * ------------------------------------------------------------------
 * Complexity:
 * - Time: O(n) → one forward pass for length, one backward pass for reverse simulation
 * - Space: O(1) → only counters and index tracking
 *
 * ------------------------------------------------------------------
 * Pattern Summary:
 * - Do not build final string.
 * - Track length and index transformations.
 * - Reverse simulate to trace kth character back to original input.
 */
