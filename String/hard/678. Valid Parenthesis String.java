//brute force 
//tc - 0(3^N)  sc - auxillary space o(N)

class Solution {
    public boolean checkValidString(String s) {
        int cnt = 0;
        int idx = 0;
        return  solve(s,idx,cnt);
    }
    private boolean solve(String s, int idx, int cnt){
       if(idx==s.length())return cnt==0;
       if(cnt<0)return false;
       if(s.charAt(idx)=='(')return solve(s,idx+1,cnt+1);
       if(s.charAt(idx)==')')return solve(s,idx+1,cnt-1);

       return solve(s,idx+1,cnt+1) || solve(s,idx+1,cnt-1) || solve(s,idx+1,cnt);
       
    }
}


//to optimize use memoization 


