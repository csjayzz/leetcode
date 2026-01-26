/*
=============================================================================
PROBLEM: 1200. Minimum Absolute Difference
=============================================================================

QUESTION:
---------
Given an array of distinct integers arr, find all pairs of elements with the 
minimum absolute difference of any two elements in the array. Return a 2D 
array of these pairs, sorted in ascending order by first element (and second 
element if first is same).

Constraints:
- 2 <= arr.length <= 10^5
- -10^6 <= arr[i] <= 10^6
- All elements in arr are distinct


INTUITION:
----------
1. The key insight is that for ANY sorted array, the minimum absolute 
   difference will ALWAYS be between consecutive elements.
   
   Why? If we have arr[i] and arr[j] where i < j and j != i+1:
   - There's an arr[k] between them (i < k < j)
   - |arr[i] - arr[j]| > |arr[k] - arr[j]| or |arr[i] - arr[k]|
   
2. So the algorithm:
   - Sort the array
   - Find minimum difference between consecutive elements
   - Collect all consecutive pairs with that minimum difference


TIME COMPLEXITY: O(n log n) for sorting + O(n) for traversals = O(n log n)
SPACE COMPLEXITY: O(1) if not counting output array


EXAMPLE & DRY RUN:
------------------
Input: arr = [4, 2, 1, 3]

Step 1: Sort
  arr = [1, 2, 3, 4]

Step 2: Find minimum difference between consecutive elements
  |1-2| = 1
  |2-3| = 1
  |3-4| = 1
  minDiff = 1

Step 3: Collect all pairs with minDiff
  Pair (1,2): |1-2| = 1 ✓ Add
  Pair (2,3): |2-3| = 1 ✓ Add
  Pair (3,4): |3-4| = 1 ✓ Add

Output: [[1,2], [2,3], [3,4]]

---

Another Example:
Input: arr = [1, 3, 6, 10, 15]

Step 1: Sort (already sorted)
  arr = [1, 3, 6, 10, 15]

Step 2: Find minimum difference
  |1-3| = 2
  |3-6| = 3
  |6-10| = 4
  |10-15| = 5
  minDiff = 2

Step 3: Collect pairs with minDiff
  Only pair (1,3) has diff = 2

Output: [[1,3]]
=============================================================================
*/

import java.util.*;

class Solution {
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        List<List<Integer>> ls = new ArrayList<>();
        
        // Step 1: Sort array to ensure consecutive elements have min difference
        Arrays.sort(arr);
        
        // Step 2: Find the minimum difference between consecutive elements
        int minDiff = Integer.MAX_VALUE;
        for(int i = 0; i < arr.length - 1; i++) {
            minDiff = Math.min(minDiff, Math.abs(arr[i] - arr[i + 1]));
        }
        
        // Step 3: Collect all pairs with minimum difference
        for(int i = 0; i < arr.length - 1; i++) {
            if(Math.abs(arr[i] - arr[i + 1]) == minDiff) {
                List<Integer> temp = new ArrayList<>();
                temp.add(arr[i]);
                temp.add(arr[i + 1]);
                ls.add(temp);
            }
        }
        
        return ls;
    }
}
