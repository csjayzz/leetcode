class Solution {
    public int totalWaviness(int num1, int num2) {
        int total = 0;

        for (int i = num1; i <= num2; i++) {
            String s = String.valueOf(i);

            // Numbers with fewer than 3 digits have waviness = 0
            if (s.length() < 3) continue;

            int waviness = 0;

            // Check each digit except first and last
            for (int j = 1; j < s.length() - 1; j++) {
                int prev = s.charAt(j - 1) - '0';
                int curr = s.charAt(j) - '0';
                int next = s.charAt(j + 1) - '0';

                // Peak
                if (curr > prev && curr > next) {
                    waviness++;
                }
                // Valley
                else if (curr < prev && curr < next) {
                    waviness++;
                }
            }

            total += waviness;
        }

        return total;
    }
}
