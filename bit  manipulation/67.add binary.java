class Solution {
    public String addBinary(String a, String b) {
        int i = a.length() - 1;   // pointer for a (right to left)
        int j = b.length() - 1;   // pointer for b (right to left)
        int carry = 0;            // carry from previous addition

        StringBuilder sb = new StringBuilder();

        // Add bits until both strings are fully processed
        while (i >= 0 || j >= 0) {
            int sum = carry;

            if (i >= 0) {
                sum += a.charAt(i) - '0'; // convert '0'/'1' to 0/1
                i--;
            }

            if (j >= 0) {
                sum += b.charAt(j) - '0';
                j--;
            }

            sb.append(sum % 2); // current bit
            carry = sum / 2;    // next carry
        }

        if (carry == 1) {
            sb.append('1');
        }

        // Built from least significant to most significant bit
        return sb.reverse().toString();
    }
}

/*
Notes:
- Approach: Simulate manual binary addition from right to left.
- Each step: sum = bitA + bitB + carry.
  - result bit = sum % 2
  - new carry = sum / 2
- Time: O(max(a.length(), b.length()))
- Space: O(max(a.length(), b.length())) for output
*/
