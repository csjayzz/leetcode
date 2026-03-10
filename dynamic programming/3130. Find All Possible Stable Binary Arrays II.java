class Solution {

    int M = (int) 1e9 + 7;

    public int numberOfStableArrays(int zero, int one, int limit) {

        /*
         * t[i][j][0] = number of valid arrays using exactly i zeroes and j ones,
         *              where the last element is 0
         * t[i][j][1] = number of valid arrays using exactly i zeroes and j ones,
         *              where the last element is 1
         *
         * Stability condition:
         * no run of identical values can be longer than limit.
         *
         * Instead of tracking the current run length explicitly, we use an
         * inclusion-exclusion style recurrence:
         *
         * To build an array ending in 0, append 0 to every valid array with
         * (i - 1) zeroes and j ones. This gives:
         *     t[i - 1][j][0] + t[i - 1][j][1]
         *
         * But some of those arrays already end with limit zeroes, so appending
         * one more 0 would create an invalid run of length limit + 1.
         *
         * Those invalid arrays correspond exactly to arrays counted in
         *     t[i - 1 - limit][j][1]
         * because they must end in 1 before that block of 'limit' zeroes.
         *
         * Symmetric logic applies for arrays ending in 1.
         */
        int[][][] t = new int[zero + 1][one + 1][2];

        // Base case: only zeroes. Valid only when their count does not exceed limit.
        for (int i = 1; i <= Math.min(zero, limit); i++) {
            t[i][0][0] = 1;
        }

        // Base case: only ones. Valid only when their count does not exceed limit.
        for (int j = 1; j <= Math.min(one, limit); j++) {
            t[0][j][1] = 1;
        }

        for (int i = 0; i <= zero; i++) {
            for (int j = 0; j <= one; j++) {

                if (i == 0 || j == 0) {
                    continue;
                }

                // End with 0:
                // append 0 to every valid state with one fewer zero.
                int val0 = (t[i - 1][j][0] + t[i - 1][j][1]) % M;

                // Remove states that already end with 'limit' zeroes,
                // because appending another 0 would violate stability.
                if (i - 1 >= limit) {
                    val0 = (val0 - t[i - 1 - limit][j][1] + M) % M;
                }

                t[i][j][0] = val0;

                // End with 1:
                // append 1 to every valid state with one fewer one.
                int val1 = (t[i][j - 1][0] + t[i][j - 1][1]) % M;

                // Remove states that already end with 'limit' ones.
                if (j - 1 >= limit) {
                    val1 = (val1 - t[i][j - 1 - limit][0] + M) % M;
                }

                t[i][j][1] = val1;
            }
        }

        return (t[zero][one][0] + t[zero][one][1]) % M;
    }
}
