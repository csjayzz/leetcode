class Solution {
    public int numSteps(String s) {
        // Notes:
        // We process bits from right to left (least significant to most significant),
        // stopping before index 0 because we only need to reduce to leading '1'.
        //
        // carry represents whether a previous +1 operation is still affecting
        // the current bit as we move left.
        //
        // For each bit (excluding the first):
        // 1) If bit + carry == 1, the number is odd at this position:
        //    - one step to add 1
        //    - one step to divide by 2
        //    => +2 steps, and carry stays/becomes 1
        //
        // 2) If bit + carry == 0 or 2, the number is even at this position:
        //    - one step to divide by 2
        //    => +1 step
        //
        // After processing all bits, if carry == 1, we need one extra step
        // for the most significant position.

        int steps = 0;
        int carry = 0;
        int n = s.length();

        for (int i = n - 1; i > 0; i--) {
            int bit = s.charAt(i) - '0';

            if (bit + carry == 1) {
                steps += 2; // add 1 + divide by 2
                carry = 1;
            } else {
                steps += 1; // divide by 2
            }
        }

        return steps + carry;
    }
}
