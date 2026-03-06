class Solution {
    public boolean checkOnesSegment(String s) {
        // If "01" exists in the string, that means ones are split into multiple segments
        return !s.contains("01");
    }
}