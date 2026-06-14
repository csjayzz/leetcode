package LinkedList;

/*
 * Problem: Maximum Twin Sum of a Linked List (LeetCode 2130)
 * ----------------------------------------------------------
 * In a linked list of even length n:
 *   - Node i (0-indexed) is the twin of node (n-1-i).
 *   - Twin sum = node[i].val + node[n-1-i].val.
 * Task: Find the maximum twin sum.
 *
 * ----------------------------------------------------------
 * Approach 1: Convert to ArrayList
 * - Traverse the linked list, store values in an ArrayList.
 * - Then compute twin sums:
 *     twinSum(i) = arr[i] + arr[n-1-i], for i in [0..n/2-1].
 * - Track maximum twin sum.
 *
 * Dry Run Example:
 *   head = [5,4,2,1]
 *   arr = [5,4,2,1], n=4
 *   i=0 → 5+1=6
 *   i=1 → 4+2=6
 *   max = 6
 *
 * ----------------------------------------------------------
 * Approach 2: Reverse Second Half (Optimized)
 * - Use fast/slow pointers to find middle.
 * - Reverse second half of list.
 * - Traverse first half and reversed second half together:
 *     twinSum = node1.val + node2.val
 * - Track maximum twin sum.
 *
 * Dry Run Example:
 *   head = [4,2,2,3]
 *   First half = [4,2], Second half reversed = [3,2]
 *   Pair sums: 4+3=7, 2+2=4 → max=7
 *
 * ----------------------------------------------------------
 * Time Complexity:
 * - Approach 1: O(n) to build array + O(n/2) to compute sums → O(n).
 * - Approach 2: O(n) (find middle, reverse, compute sums).
 *
 * Space Complexity:
 * - Approach 1: O(n) for array.
 * - Approach 2: O(1) extra space (in-place reverse).
 *
 * ----------------------------------------------------------
 * Pattern Summary:
 * - Twin sum pairs are symmetric around the list.
 * - Array approach is simple and clear.
 * - Reverse-half approach is memory-efficient and optimal.
 */

class Solution {
    public int pairSum(ListNode head) {
        ArrayList<Integer> ls = new ArrayList<>();
        ListNode curr = head;

        while (curr != null) {
            ls.add(curr.val);
            curr = curr.next;
        }

        int max = Integer.MIN_VALUE;
        int n = ls.size();
        for (int i = 0; i < n / 2; i++) {
            max = Math.max(max, ls.get(i) + ls.get(n - 1 - i));
        }

        return max;
    }
}



class Solution {
    public int pairSum(ListNode head) {
        // Step 1: Find middle
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Reverse second half
        ListNode prev = null, curr = slow;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        // Step 3: Compute twin sums
        int max = Integer.MIN_VALUE;
        ListNode first = head, second = prev;
        while (second != null) {
            max = Math.max(max, first.val + second.val);
            first = first.next;
            second = second.next;
        }

        return max;
    }
}
