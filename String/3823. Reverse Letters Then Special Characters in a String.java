/**
 * PROBLEM: Reverse Letters Then Special Characters in a String
 * LeetCode: 3823 (Easy)
 * 
 * PROBLEM UNDERSTANDING:
 * ======================
 * Given a string with lowercase letters and special characters
 * Goal: 
 * 1. Extract letters, reverse them, place back in letter positions
 * 2. Extract special chars, reverse them, place back in special char positions
 * 
 * Key Insight: We need to reverse TWO things SEPARATELY:
 * - First: reverse only letters (maintain special char positions)
 * - Second: reverse only special chars (maintain letter positions)
 * 
 * INTUITION:
 * ==========
 * The Two-Pointer Two-Pass approach works because:
 * 
 * 1. First pass (reverse letters):
 *    - Two pointers approach from both ends
 *    - Skip special characters (they stay in place)
 *    - Swap only when both pointers point to letters
 *    - This reverses letter order while keeping them in letter positions
 * 
 * 2. Second pass (reverse special chars):
 *    - Same two-pointer logic
 *    - Now skip letters, only swap special characters
 *    - This reverses special char order while keeping them in special char positions
 * 
 * Example: ")ebc#da@f("
 * 
 * Pass 1 - Reverse letters:
 * Letters: e,b,c,d,a,f → Reversed: f,a,d,c,b,e
 * Position: )e bc#da@f( → )f ad#cb@e(
 * (Special chars ) # @ ( stay in their original positions)
 * 
 * Pass 2 - Reverse special chars:
 * Special chars: ), #, @, ( → Reversed: (, @, #, )
 * Position: )fad#cb@e( → (fad@cb#e)
 * (Letters stay in their original positions)
 * 
 * Result: "(fad@cb#e)" ✓
 * 
 * WHY THIS APPROACH (Two-Pointer Two-Pass):
 * ==========================================
 * 1. EFFICIENT: O(n) time, O(1) extra space (excluding output char array)
 *    - Single pass to separate types: O(n)
 *    - Two passes to reverse: O(n) + O(n)
 *    - Total: O(n)
 * 
 * 2. IN-PLACE: Modifies only the array, no extra lists needed
 *    - No separate data structures for letters/special chars
 *    - Just swap in-place
 * 
 * 3. CLEAN: Two-pointer is a standard pattern for reversing
 *    - Left pointer moves right, right pointer moves left
 *    - When both find matching type, swap and move both
 * 
 * WHY NOT OTHER APPROACHES:
 * ==========================
 * 
 * 1. Separate List Approach: O(n) time, O(n) space
 *    ❌ Extract letters into list: [e,b,c,d,a,f]
 *    ❌ Reverse list: [f,a,d,c,b,e]
 *    ❌ Place back in original positions
 *    ❌ Repeat for special characters
 *    Problem: Uses extra O(n) space for lists
 *    Solution: Two-pointer avoids this
 * 
 * 2. Recursive Approach: O(n) time, O(n) space (call stack)
 *    ❌ Recursive reversals are overkill for simple reversal
 *    ❌ Stack overhead with no benefit
 *    Solution: Iterative two-pointer is simpler
 * 
 * 3. String Concatenation: O(n²) time, O(n) space
 *    ❌ Building new strings with substring += operations
 *    ❌ Each += creates new string object (immutable)
 *    ❌ Very inefficient
 *    Solution: Char array modification is O(n)
 * 
 * ALGORITHM DETAILS:
 * ==================
 * 
 * General reverse(arr, target) logic:
 * - target = true: find and swap LETTERS
 * - target = false: find and swap SPECIAL CHARACTERS
 * 
 * Two-Pointer matching logic:
 * For each position, check if it matches the target type:
 * - iMatch = (target && isLetter) OR (!target && !isLetter)
 * - jMatch = (target && isLetter) OR (!target && !isLetter)
 * 
 * Action:
 * - If i doesn't match, skip it: i++
 * - If j doesn't match, skip it: j--
 * - If both match, swap them: swap arr[i] with arr[j], then i++, j--
 * 
 * Why this works:
 * - We find the next valid element on each side
 * - When both sides have valid elements, we swap
 * - This effectively reverses elements of the matching type
 * - Non-matching elements are skipped and stay in place
 */

class Solution {
    /**
     * MAIN FUNCTION: Reverse letters and special chars separately
     * 
     * Approach:
     * 1. Convert string to char array (mutable, efficient)
     * 2. First pass: reverse all letters in-place (true parameter)
     * 3. Second pass: reverse all special chars in-place (false parameter)
     * 4. Convert char array back to string
     * 
     * @param s - input string with letters and special characters
     * @return string with letters reversed and special chars reversed
     * 
     * TIME: O(2n) = O(n) - two full passes
     * SPACE: O(n) for char array (output)
     */
    public String reverseByType(String s) {
        // Convert string to mutable char array for in-place manipulation
        char[] arr = s.toCharArray();
        
        /**
         * First Pass: Reverse all letters
         * target = true means we're looking for letters
         * Special characters stay in place during this pass
         */
        reverse(arr, true);
        
        /**
         * Second Pass: Reverse all special characters
         * target = false means we're looking for non-letters
         * Letters (already reversed) stay in place during this pass
         */
        reverse(arr, false);
        
        // Convert char array back to string
        return new String(arr);
    }
    
    /**
     * HELPER FUNCTION: Reverse elements of a specific type in-place
     * 
     * Two-pointer approach:
     * - i starts at left, j starts at right
     * - Move towards center
     * - Skip elements that don't match the target type
     * - Swap elements when both match the target type
     * 
     * @param arr - char array to modify
     * @param target - true for letters, false for special characters
     * 
     * TIME: O(n) - each element visited once
     * SPACE: O(1) - only pointer variables
     */
    private void reverse(char[] arr, boolean target) {
        int i = 0;                    // Left pointer
        int j = arr.length - 1;       // Right pointer
        
        /**
         * MAIN LOOP: Continue until pointers cross
         */
        while(i < j) {
            /**
             * MATCHING LOGIC:
             * Check if each pointer's element matches the target type
             * 
             * target = true: match if it's a letter
             *   iMatch = Character.isLetter(arr[i])
             * 
             * target = false: match if it's NOT a letter (special char)
             *   iMatch = !Character.isLetter(arr[i])
             * 
             * Using ternary: target ? isLetter : !isLetter
             */
            boolean iMatch = target ? Character.isLetter(arr[i]) : !Character.isLetter(arr[i]);
            boolean jMatch = target ? Character.isLetter(arr[j]) : !Character.isLetter(arr[j]);
            
            /**
             * CASE 1: Left pointer doesn't match target type
             * Example: target=true (letters), arr[i]='#' (special char)
             * Action: Skip this position, move left pointer right
             * Reason: # is not a letter, keep it in place for now
             */
            if(!iMatch) {
                i++;
            }
            /**
             * CASE 2: Right pointer doesn't match target type
             * Example: target=true (letters), arr[j]='#' (special char)
             * Action: Skip this position, move right pointer left
             * Reason: # is not a letter, keep it in place for now
             */
            else if(!jMatch) {
                j--;
            }
            /**
             * CASE 3: Both pointers match the target type
             * Example: target=true, arr[i]='e' (letter), arr[j]='f' (letter)
             * Action: Swap and move both pointers
             * Reason: Both are valid, swap to reverse the order
             * 
             * Post-increment: i++ and j-- happen after assignment
             * char temp = arr[i];     // temp = 'e'
             * arr[i++] = arr[j];      // arr[i]='f', then i++
             * arr[j--] = temp;        // arr[j]='e', then j--
             */
            else {
                char temp = arr[i];
                arr[i++] = arr[j];      // Assign and increment i
                arr[j--] = temp;        // Assign and decrement j
            }
        }
    }
}

/**
 * EXAMPLE WALKTHROUGH:
 * ====================
 * 
 * Input: s = ")ebc#da@f("
 * Index:     0123456789
 * 
 * STEP 1: Convert to char array
 * arr = [')', 'e', 'b', 'c', '#', 'd', 'a', '@', 'f', '(']
 * 
 * STEP 2: First pass - reverse(arr, true) [reverse letters]
 * 
 * Letters found at positions: 1(e), 2(b), 3(c), 5(d), 6(a), 8(f)
 * Special chars stay at: 0()), 4(#), 7(@), 9(()
 * 
 * i=0, j=9:
 *   iMatch=false (0=')', not letter), skip: i++ → i=1
 * i=1, j=9:
 *   iMatch=true (1='e', is letter), jMatch=true (9='(', NOT letter)
 *   Skip j: j-- → j=8
 * i=1, j=8:
 *   iMatch=true (1='e'), jMatch=true (8='f')
 *   Both match! Swap arr[1] with arr[8]: arr[1]='f', arr[8]='e'
 *   arr = [')', 'f', 'b', 'c', '#', 'd', 'a', '@', 'e', '(']
 *   i=2, j=7
 * i=2, j=7:
 *   iMatch=true (2='b'), jMatch=false (7='@', not letter)
 *   Skip j: j-- → j=6
 * i=2, j=6:
 *   iMatch=true (2='b'), jMatch=true (6='a')
 *   Both match! Swap: arr[2]='a', arr[6]='b'
 *   arr = [')', 'f', 'a', 'c', '#', 'd', 'b', '@', 'e', '(']
 *   i=3, j=5
 * i=3, j=5:
 *   iMatch=true (3='c'), jMatch=true (5='d')
 *   Both match! Swap: arr[3]='d', arr[5]='c'
 *   arr = [')', 'f', 'a', 'd', '#', 'c', 'b', '@', 'e', '(']
 *   i=4, j=4
 * Loop ends (i >= j)
 * 
 * After Pass 1: arr = [')', 'f', 'a', 'd', '#', 'c', 'b', '@', 'e', '(']
 * 
 * STEP 3: Second pass - reverse(arr, false) [reverse special chars]
 * 
 * Special chars found at: 0()), 4(#), 7(@), 9(()
 * Letters stay at: 1(f), 2(a), 3(d), 5(c), 6(b), 8(e)
 * 
 * i=0, j=9:
 *   iMatch=true (0=')', not letter), jMatch=true (9='(', not letter)
 *   Both match! Swap: arr[0]='(', arr[9]=')'
 *   arr = ['(', 'f', 'a', 'd', '#', 'c', 'b', '@', 'e', ')']
 *   i=1, j=8
 * i=1, j=8:
 *   iMatch=false (1='f', IS letter), skip: i++ → i=2
 * i=2, j=8:
 *   iMatch=false (2='a', IS letter), skip: i++ → i=3
 * i=3, j=8:
 *   iMatch=false (3='d', IS letter), skip: i++ → i=4
 * i=4, j=8:
 *   iMatch=true (4='#'), jMatch=false (8='e', IS letter)
 *   Skip j: j-- → j=7
 * i=4, j=7:
 *   iMatch=true (4='#'), jMatch=true (7='@')
 *   Both match! Swap: arr[4]='@', arr[7]='#'
 *   arr = ['(', 'f', 'a', 'd', '@', 'c', 'b', '#', 'e', ')']
 *   i=5, j=6
 * i=5, j=6:
 *   iMatch=false (5='c', IS letter), skip: i++ → i=6
 * Loop ends (i >= j)
 * 
 * After Pass 2: arr = ['(', 'f', 'a', 'd', '@', 'c', 'b', '#', 'e', ')']
 * 
 * STEP 4: Convert to string
 * Result: "(fad@cb#e)" ✓
 * 
 * VERIFICATION:
 * Original: )ebc#da@f(
 * Letters: e,b,c,d,a,f → Reversed: f,a,d,c,b,e ✓
 * Special: ),#,@,( → Reversed: (,@,#,) ✓
 * Final: (fad@cb#e) ✓
 */

/**
 * COMPLEXITY ANALYSIS:
 * ====================
 * TIME COMPLEXITY: O(n)
 * - First pass (reverse letters): O(n) - each char visited once
 * - Second pass (reverse special chars): O(n) - each char visited once
 * - Total: 2*O(n) = O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - char array of length n
 * - Excluding output, only O(1) extra space for pointers
 * 
 * COMPARISON:
 * - List-based approach: O(n) time, O(n) space (uses lists)
 * - This approach: O(n) time, O(n) space (minimal overhead)
 * - Both optimal, but this is cleaner and faster in practice
 */

