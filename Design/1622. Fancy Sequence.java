
/*
Problem explanation:
We need to design a data structure that supports 4 operations:

1. append(val)
   Add val at the end of the sequence.

2. addAll(inc)
   Add inc to every existing element.

3. multAll(m)
   Multiply every existing element by m.

4. getIndex(idx)
   Return the value at index idx modulo 1e9 + 7.
   If idx is invalid, return -1.


Naive idea:
Every time we call addAll or multAll, update every element in the list.

Why that is too slow:
There can be up to 10^5 operations.
If each addAll / multAll touches the entire list,
the total complexity can become O(n^2), which will time out.


Main insight:
All values in the sequence are affected by the same global transformation.

Suppose an element was originally x.
After some operations, its current value becomes:

current = x * mul + add

So instead of updating every stored element,
we only maintain two global values:

- mul : overall multiplication factor
- add : overall addition factor

That means:
every stored value is interpreted through the same formula:

actual value = storedValue * mul + add


The only tricky part is append(val):
When we append a new value, it should behave like a fresh number
inserted after all previous operations.

So if current formula is:
actual = stored * mul + add

and we want actual = val at insertion time,
then:

stored * mul + add = val
stored = (val - add) / mul

But division is not allowed directly in modular arithmetic.
So we multiply by modular inverse of mul:

stored = (val - add) * inverse(mul) mod MOD


Why modular inverse works:
Since MOD = 1e9 + 7 is prime,
inverse(mul) = mul^(MOD - 2) mod MOD
by Fermat's little theorem.


Transformation updates:

If current transformation is:
f(x) = x * mul + add

After addAll(inc):
new value = f(x) + inc
          = x * mul + (add + inc)

So:
add = add + inc

After multAll(m):
new value = f(x) * m
          = (x * mul + add) * m
          = x * (mul * m) + (add * m)

So:
mul = mul * m
add = add * m


Dry run:

Fancy()
sequence = []
mul = 1, add = 0

append(2)
stored = (2 - 0) / 1 = 2
list = [2]

addAll(3)
mul = 1
add = 3
actual sequence:
[2 * 1 + 3] = [5]

append(7)
stored = (7 - 3) / 1 = 4
list = [2, 4]

multAll(2)
mul = 2
add = 6
actual values:
index 0 -> 2 * 2 + 6 = 10
index 1 -> 4 * 2 + 6 = 14

getIndex(0) = 10


Time complexity:
- append   : O(log MOD) because of modular inverse power
- addAll   : O(1)
- multAll  : O(1)
- getIndex : O(1)

Space complexity:
O(n) for stored elements.
*/

class Fancy {
    List<Long> ls;
    long add = 0;
    long mul = 1;
    final long MOD = 1_000_000_007;

    public Fancy() {
        ls = new ArrayList<>();
    }

    public void append(int val) {
        /*
         * We do not store the visible value directly.
         *
         * Current visible formula is:
         * visible = stored * mul + add
         *
         * For this newly appended number, visible should be val right now.
         * So we reverse the current transformation:
         *
         * stored = (val - add) / mul
         *        = (val - add) * inverse(mul) mod MOD
         */
        long neutralVal = (val - add + MOD) % MOD;
        neutralVal = (neutralVal * power(mul, MOD - 2)) % MOD;
        ls.add(neutralVal);
    }

    public void addAll(int inc) {
        /*
         * If every value is currently:
         * x * mul + add
         *
         * After adding inc to all:
         * x * mul + (add + inc)
         */
        add = (add + inc) % MOD;
    }

    public void multAll(int m) {
        /*
         * If every value is currently:
         * x * mul + add
         *
         * After multiplying by m:
         * (x * mul + add) * m
         * = x * (mul * m) + add * m
         */
        mul = (mul * m) % MOD;
        add = (add * m) % MOD;
    }

    public int getIndex(int idx) {
        if (idx >= ls.size()) {
            return -1;
        }

        /*
         * Convert the stored neutral value back to its current visible value.
         */
        long res = (ls.get(idx) * mul + add) % MOD;
        return (int) res;
    }

    // Fast power computes base^exp % MOD.
    // Here it is used for modular inverse: a^(MOD-2) % MOD.
    private long power(long base, long exp) {
        long res = 1;
        base %= MOD;

        while (exp > 0) {
            if (exp % 2 == 1) {
                res = (res * base) % MOD;
            }
            base = (base * base) % MOD;
            exp /= 2;
        }

        return res;
    }
}
