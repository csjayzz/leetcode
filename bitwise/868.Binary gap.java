class Solution {
    public int binaryGap(int n) {
        String bin = Integer.toBinaryString(n);
        int maxGap = 0;
        int i = 0;
        int j = 0;

        while (i < bin.length()) {
            if (bin.charAt(i) == '1') {
                j = i + 1;
                while (j < bin.length() && bin.charAt(j) != '1') {
                    j++;
                }
                if (j < bin.length()) {
                    maxGap = Math.max(maxGap, j - i);
                }
                i = j;
            } else {
                i++;
            }
        }
        return maxGap;
    }
}

/*
===========================================================
868. Binary Gap
===========================================================

Idea:
- Convert n to binary string.
- Walk through the string and whenever you find a '1',
  search for the next '1'.
- Distance between these two adjacent 1s is (nextIndex - currentIndex).
- Keep track of the maximum such distance.

Why this works:
- "Adjacent 1s" means consecutive 1s in left-to-right order,
  allowing any number of 0s between them.
- For each 1, the code checks only the next 1 to form one adjacent pair.
- Taking max over all such pairs gives the required longest distance.

Dry run (n = 22):
- Binary = "10110"
- Pair at indices (0, 2) -> distance = 2
- Pair at indices (2, 3) -> distance = 1
- maxGap = 2

Edge case:
- If binary has fewer than two 1s (example: "1000"),
  no pair exists and answer remains 0.

Complexity:
- Time: O(k), where k = number of bits in n
- Space: O(k), due to binary string
*/
