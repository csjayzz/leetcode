import java.util.*;
//better sol with set
class Solution {
    public int countPrimeSetBits(int L, int R) {
        Set<Integer> primes = new HashSet<>(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19));
        int count = 0;

        for (int num = L; num <= R; num++) {
            int setBits = Integer.bitCount(num);
            if (primes.contains(setBits)) {
                count++;
            }
        }
        return count;
    }
}

/*
===========================================================
762. Prime Number of Set Bits in Binary Representation (Set Version)
===========================================================

Idea:
- For each number in [L, R], count its set bits using `Integer.bitCount(num)`.
- If this count is prime, include it in answer.
- We store all possible prime set-bit counts in a `HashSet` for O(1) average lookup.

Why this works:
- Constraint range ensures set-bit count is small (at most around 20).
- Relevant prime counts are fixed: 2, 3, 5, 7, 11, 13, 17, 19.
- So checking primality becomes just: `primes.contains(setBits)`.

Dry run (L = 6, R = 10):
- 6  -> 110  -> bitCount = 2 -> prime -> count = 1
- 7  -> 111  -> bitCount = 3 -> prime -> count = 2
- 8  -> 1000 -> bitCount = 1 -> not prime
- 9  -> 1001 -> bitCount = 2 -> prime -> count = 3
- 10 -> 1010 -> bitCount = 2 -> prime -> count = 4
Answer = 4

Complexity:
- Let n = R - L + 1
- Time: O(n)  (each number processed once, set lookup is O(1) average)
- Space: O(1)
*/

//brute method
class Solution {
    public int countPrimeSetBits(int left, int right) {
        int ans = 0;
        for(int i = left;i<=right;i++){
            int n = Integer.bitCount(i);
            if(isPrime(n))ans++;
        }

        return ans ;
    }
    private boolean isPrime(int n)
    {
        // Corner case
        if (n <= 1)
            return false;

        // Check from 2 to n/2
        for (int i = 2; i <= n / 2; i++)
            if (n % i == 0)
                return false;

        return true;
    }
}


//most optimal
class Solution {
    public int countPrimeSetBits(int L, int R) {
        int count = 0;
        while (L <= R)
            count += 665772 >> Integer.bitCount(L++) & 1;
        return count;
    }
}

/*
===========================================================
762. Prime Number of Set Bits in Binary Representation
===========================================================

Code:
count += 665772 >> Integer.bitCount(x) & 1

What this does:
1) Integer.bitCount(x)
   - gives number of set bits (1s) in binary form of x.

2) 665772 is a precomputed bitmask where bit position p is 1
   if p is prime.
   - Here range values are small (LeetCode constraint), so possible set-bit
     counts are also small (up to about 20).
   - Prime counts we care about: 2, 3, 5, 7, 11, 13, 17, 19.

3) (665772 >> bitCount) & 1
   - shift mask right by bitCount.
   - now the least significant bit tells whether bitCount was prime.
   - result is:
     - 1 => prime set-bit count
     - 0 => not prime

4) Add result directly to answer.
   - No separate prime-check function needed.

Why 665772?
- It is:
  (1<<2) + (1<<3) + (1<<5) + (1<<7) + (1<<11) + (1<<13) + (1<<17) + (1<<19)
- So exactly prime indices are marked with 1.

Example:
- x = 10 (1010), bitCount = 2
- 665772 >> 2 has LSB = 1
- contributes +1

- x = 8 (1000), bitCount = 1
- 665772 >> 1 has LSB = 0
- contributes +0

Complexity:
- Let n = R - L + 1
- Time: O(n)  (each number processed once, O(1) work per number)
- Space: O(1)

Key takeaway:
- Bitmask encodes "is this count prime?" lookup in constant time.
- Very compact and fast for small bounded bit-count values.
*/

