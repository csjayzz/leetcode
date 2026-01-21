//brute force 
class Solution {
    public int[] minBitwiseArray(List<Integer> nums) {
        int [] result = new int [nums.size()];
       
        for(int i = 0;i<nums.size();i++){
            for(int x = 0;x<nums.get(i);x++){
                if((x|x+1)==nums.get(i)){
                    result[i] = x;
                    break;
                }
                result[i] = -1;
            }
        }

        return result;
    }
}