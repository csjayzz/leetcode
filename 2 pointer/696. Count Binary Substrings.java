class Solution {
    public int countBinarySubstrings(String s) {
        int result = 0;
        int prevCount = 0;
        int currCount = 1;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                currCount++;
            } else {
                result += Math.min(prevCount, currCount);
                prevCount = currCount;
                currCount = 1;
            }
        }

        return result + Math.min(prevCount, currCount);
    }
}

/*
Dry run (s = "00110011"):
- Groups: 00, 11, 00, 11 -> sizes: 2, 2, 2, 2
- At each group change, add min(prevGroupSize, currGroupSize)
- Adds: min(0,2)=0, min(2,2)=2, min(2,2)=2, final min(2,2)=2
- Total = 0 + 2 + 2 + 2 = 6

Idea:
- currCount = size of current same-char block
- prevCount = size of previous block
- Valid binary substrings between two adjacent blocks = min(prevCount, currCount)
*/
