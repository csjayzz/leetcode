import java.util.Arrays;

/**
 * LeetCode 1356 - Sort Integers by The Number of 1 Bits
 *
 * Problem summary:
 * Sort array elements by:
 * 1) Number of 1s in binary representation (ascending)
 * 2) Value itself (ascending) if bit counts are equal
 *
 * Key Java note:
 * - Arrays.sort(int[]) cannot take a custom comparator.
 * - Comparator works with objects, so convert int[] -> Integer[].
 *
 * Approach:
 * 1. Box int[] into Integer[].
 * Boxing means converting a primitive type into its wrapper object type.

In Java:

int -> Integer
char -> Character
double -> Double

Why it mattered in your 1356 code:

Arrays.sort(int[]) cannot use a custom comparator.
Comparator-based sort needs objects, so int[] was converted (boxed) to Integer[].

 * 2. Sort using comparator:
 *    - Compare Integer.bitCount(a) and Integer.bitCount(b)
 *    - If equal, compare a and b normally
 * 3. Unbox back into original int[] and return.
 *
 * Why this works:
 * - Comparator enforces the exact two-level ordering required by problem.
 * - Java sort guarantees elements are arranged according to comparator rules.
 *
 * Dry run (arr = [0,1,2,3,4,5,6,7,8]):
 * bit counts:
 * 0->0, 1->1, 2->1, 3->2, 4->1, 5->2, 6->2, 7->3, 8->1
 * grouped + sorted by value inside group:
 * [0] [1,2,4,8] [3,5,6] [7]
 * final: [0,1,2,4,8,3,5,6,7]
 *
 * Complexity:
 * - Time: O(n log n) for sorting
 * - Space: O(n) for boxed Integer[]
 */
class Solution {
    public int[] sortByBits(int[] arr) {
        // Box primitive int[] to Integer[] so we can use a comparator.
        Integer[] boxedArr = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            boxedArr[i] = arr[i];
        }

        Arrays.sort(boxedArr, (a, b) -> {
            int countA = Integer.bitCount(a);
            int countB = Integer.bitCount(b);

            if (countA != countB) {
                return Integer.compare(countA, countB);
            }
            return Integer.compare(a, b);
        });

        // Copy sorted values back to original primitive array.
        for (int i = 0; i < arr.length; i++) {
            arr[i] = boxedArr[i];
        }

        return arr;
    }
}
