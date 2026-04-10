package Hashmap;

import java.util.*;

class Solution {
    public int minimumDistance(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        
        // Collect indices for each number
        for (int i = 0; i < nums.length; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }
        
        int ans = Integer.MAX_VALUE;
        
        // For each number, check if it appears at least 3 times
        for (List<Integer> positions : map.values()) {
            if (positions.size() >= 3) {
                // Check all triplets of indices
                for (int i = 0; i < positions.size() - 2; i++) {
                    int a = positions.get(i);
                    int b = positions.get(i + 1);
                    int c = positions.get(i + 2);
                    
                    // Distance = sum of pairwise differences
                    int dist = Math.abs(a - b) + Math.abs(b - c) + Math.abs(c - a);
                    ans = Math.min(ans, dist);
                }
            }
        }
        
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}


/* Problem Statement : 
You are given an integer array nums.
A tuple (i, j, k) of 3 distinct indices is good if nums[i] == nums[j] == nums[k].
The distance of a good tuple is abs(i - j) + abs(j - k) + abs(k - i), where abs(x) denotes the absolute value of x.
Return an integer denoting the minimum possible distance of a good tuple. If no good tuples exist, return -1.

 Example 1:
Input: nums = [1,2,1,1,3]
Output: 6
Explanation:
The minimum distance is achieved by the good tuple (0, 2, 3).
(0, 2, 3) is a good tuple because nums[0] == nums[2] == nums[3] == 1. Its distance is abs(0 - 2) + abs(2 - 3) + abs(3 - 0) = 2 + 1 + 3 = 6.
Example 2:
Input: nums = [1,1,2,3,2,1,2]
Output: 8
Explanation:
The minimum distance is achieved by the good tuple (2, 4, 6).
(2, 4, 6) is a good tuple because nums[2] == nums[4] == nums[6] == 2. Its distance is abs(2 - 4) + abs(4 - 6) + abs(6 - 2) = 2 + 2 + 4 = 8.
 */

/*
ANSWER: 

### Step 1: Collect indices for each number
```java
for (int i = 0; i < nums.length; i++) {
    map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
}
```

- `for (int i = 0; i < nums.length; i++)`  
  → Loop through the array `nums` using index `i`.

- `map.computeIfAbsent(nums[i], k -> new ArrayList<>())`  
  → Look at the current number `nums[i]`.  
  → If this number is **not yet in the map**, create a new empty `ArrayList` for it.  
  → If it **is already in the map**, just return the existing list.

- `.add(i)`  
  → Add the current index `i` to that list.  
  → So the map ends up like:  
    ```
    number -> list of all positions where it appears
    ```

Example:  
`nums = [5, 1, 5, 2, 5]`  
After this loop:  
```
map = {
   5 -> [0, 2, 4],
   1 -> [1],
   2 -> [3]
}
```

---

### Step 2: Check each number’s positions
```java
for (List<Integer> positions : map.values()) {
    if (positions.size() >= 3) {
        ...
    }
}
```

- `map.values()`  
  → Gives you all the lists of positions (ignores the keys).  
  → Example: `[ [0,2,4], [1], [3] ]`.

- `if (positions.size() >= 3)`  
  → Only care about numbers that appear **at least 3 times** (since you need 3 indices).

---

### Step 3: Slide a window of size 3
```java
for (int i = 0; i < positions.size() - 2; i++) {
    int a = positions.get(i);
    int b = positions.get(i + 1);
    int c = positions.get(i + 2);
```

- This loop checks **triplets of consecutive positions**.  
- Example: if positions = `[0, 2, 4, 6]`, then:
  - First triplet: `(0, 2, 4)`
  - Second triplet: `(2, 4, 6)`

---

### Step 4: Compute distance
```java
int dist = Math.abs(a - b) + Math.abs(b - c) + Math.abs(c - a);
ans = Math.min(ans, dist);
```

- `Math.abs(a - b)` → distance between first and second index.  
- `Math.abs(b - c)` → distance between second and third index.  
- `Math.abs(c - a)` → distance between first and third index.  
- Add them up = total “pairwise distance.”  
- Compare with current best answer `ans` and keep the smaller one.

---

### Step 5: Return result
```java
return ans == Integer.MAX_VALUE ? -1 : ans;
```

- If `ans` was never updated (still `Integer.MAX_VALUE`), that means no number appeared 3 times → return `-1`.  
- Otherwise, return the minimum distance found.

---

✅ So in short:
1. Build a map: number → list of all indices.  
2. For each number with ≥3 indices, check consecutive triplets.  
3. Compute distance for each triplet.  
4. Return the smallest distance.


 */