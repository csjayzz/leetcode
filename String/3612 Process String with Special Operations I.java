/*
 * Problem: Process String with Special Operations I (LeetCode 3612)
 * -----------------------------------------------------------------
 * You are given a string s with lowercase letters and special ops: '*', '#', '%'.
 * Build result string by:
 *   - Letter: append to result
 *   - '*': remove last char (if exists)
 *   - '#': duplicate result (result = result + result)
 *   - '%': reverse result
 * Return the final string after processing all characters.
 *
 * Constraints:
 * - s.length <= 20 → small enough to directly simulate
 *
 * -----------------------------------------------------------------
 * Approach:
 * - Use a dynamic container (ArrayList<Character>) to simulate operations.
 * - Traverse s left to right:
 *     * If letter → add to list
 *     * If '*' → remove last element (list.removeLast())
 *     * If '#' → duplicate list (list.addAll(list))
 *     * If '%' → reverse list (Collections.reverse(list))
 * - Finally, join list into a string.
 *
 * -----------------------------------------------------------------
 * Dry Run Example 1:
 *   s = "a#b%*"
 *   Step 0: "" 
 *   'a' → "a"
 *   '#' → "aa"
 *   'b' → "aab"
 *   '%' → "baa"
 *   '*' → "ba"
 *   Output = "ba"
 *
 * Dry Run Example 2:
 *   s = "z*#"
 *   Step 0: ""
 *   'z' → "z"
 *   '*' → ""
 *   '#' → "" (duplicate empty string)
 *   Output = ""
 *
 * -----------------------------------------------------------------
 * Breakdown of Inbuilt Functions:
 *
 * 1. list.addAll(list)
 *    - Duplicates the current list by appending all elements of itself.
 *    - Example: list = [a,b], list.addAll(list) → [a,b,a,b].
 *
 * 2. list.removeLast()
 *    - Removes the last element of the list.
 *    - Equivalent to list.remove(list.size()-1).
 *    - Example: list = [a,b,c], removeLast() → [a,b].
 *
 * 3. Collections.reverse(list)
 *    - Reverses the order of elements in the list in place.
 *    - Example: list = [a,b,c], reverse → [c,b,a].
 *
 * 4. list.stream().map(String::valueOf).collect(Collectors.joining())
 *    - Converts each Character in list to String.
 *    - Joins them together into one final string.
 *    - Example: list = [b,a], result = "ba".
 *
 * -----------------------------------------------------------------
 * Complexity:
 * - Time: O(n * m) worst case (n = s.length, m = current list size during duplication).
 *   But since n <= 20, this is safe.
 * - Space: O(m) for list storage.
 *
 * -----------------------------------------------------------------
 * Pattern Summary:
 * - Direct simulation works because input size is small.
 * - Key operations: duplicate, reverse, remove last.
 * - Final joining uses Java Streams for clean conversion.
 */

class Solution {
    public String processStr(String s) {
        ArrayList<Character>list = new ArrayList<>();

        for(char ch : s.toCharArray()){
            if(ch=='#'&&list.size()!=0){
                list.addAll(list);
            }else if(ch=='*'&&list.size()!=0){
                list.removeLast();
            }else if(ch=='%'&&list.size()!=0){
                Collections.reverse(list);
            }else{
                if(ch!='#'&&ch!='%'&&ch!='*')
                list.add(ch);
            }
        }
        String result = list.stream().map(String::valueOf).collect(Collectors.joining());
        return result;
    }
    
}
