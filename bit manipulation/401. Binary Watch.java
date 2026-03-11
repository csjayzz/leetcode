/*
LeetCode 401 - Binary Watch

Problem:
- Binary watch has 4 LEDs for hours (0 to 11) and 6 LEDs for minutes (0 to 59).
- `turnedOn` tells how many LEDs are ON in total.
- Return all possible valid times.

Valid time format:
- Hour should not have leading zero ("1:00" is valid, "01:00" is invalid).
- Minute must always be 2 digits ("10:02" is valid, "10:2" is invalid).

Approach:
- Iterate all possible hours (0..11) and minutes (0..59).
- Count set bits using `Integer.bitCount(hour)` and `Integer.bitCount(minute)`.
- If their sum equals `turnedOn`, include the time in answer.

Why it works:
- We check every valid watch time exactly once and keep only matching LED counts.

Complexity:
- Time: O(12 * 60) = O(1), fixed iterations.
- Space: O(1) auxiliary (excluding output list).

Quick revision note:
- `11` hour needs 3 ON bits, so it cannot appear if `turnedOn = 2`.
- Same logic applies to minutes (e.g., `35` has 3 ON bits).
*/

class Solution {
    public List<String> readBinaryWatch(int turnedOn) {
        List<String> res = new ArrayList<>();
        for (int hh = 0; hh <= 11; hh++) {
            for (int mm = 0; mm <= 59; mm++) {
                if (Integer.bitCount(hh) + Integer.bitCount(mm) == turnedOn) {
                    String hour = String.valueOf(hh);
                    String min = (mm < 10 ? "0" : "") + mm;
                    res.add(hour + ":" + min);
                }
            }
        }
        return res;
    }
}
