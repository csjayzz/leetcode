//first observation is that if the number is 32 then the binary will take will have 2 bits only yeah not 111 10 + 11 + 11 hi hogya toh first observation yahi h ki number k places jitna hi bits ka binary is valid.//2. aur ab mai inmese sabse bada wala binary number lungu aur kyu mai sabse bada wala le rha hu kyuki hume minimum number of binary numbers lene h 
//brute way is to take string of 11111111 (of length same as the given take 1 in binary for non zero in given and 0 for 0) and keep subtracting until its all zero but u see the trick is the u will need to keep reducing until u reach 0 for the max digit that is in the given number so trick is to find the max num and simply returning that 

//this is the trick way to solve it 
class Solution {
    public int minPartitions(String n) {
        int max = 0;

        for(int i = 0;i<n.length();i++){
           max = Math.max(max,n.charAt(i)-'0');
        }

        return max;
    }
}