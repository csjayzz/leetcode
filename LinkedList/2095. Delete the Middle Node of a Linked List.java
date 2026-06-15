/*
 * Problem: Delete the Middle Node of a Linked List (LeetCode 2095)
 * ----------------------------------------------------------------
 * You are given the head of a linked list.
 * Delete the middle node and return the head.
 *
 * Definition:
 * - Middle node = ⌊n/2⌋th node (0-indexed).
 *   Example: For n=7 → middle = index 3.
 *   Example: For n=4 → middle = index 2.
 *
 * ---------------------------------------------------------------
 * Approach 1: Length Count + Indexing
 * - Step 1: Traverse list to count length n.
 * - Step 2: Handle edge case: if n==1 → return null.
 * - Step 3: Traverse again to node (n/2 - 1).
 * - Step 4: Skip its next node (the middle).
 *
 * Dry Run Example:
 *   head = [1,3,4,7,1,2,6], n=7
 *   middle = 7/2 = 3
 *   Traverse to node index 2 (value=4).
 *   Skip next (value=7).
 *   Result = [1,3,4,1,2,6]
 *
 * Time Complexity: O(n) (two passes).
 * Space Complexity: O(1).
 *
 * ---------------------------------------------------------------
 * Approach 2: Slow/Fast Pointer (Optimized)
 * - Step 1: Use fast/slow pointers to find middle:
 *     * fast moves 2 steps, slow moves 1 step.
 *     * When fast reaches end, slow is at middle.
 * - Step 2: Keep track of node before slow (prev).
 * - Step 3: Skip slow node by linking prev.next = slow.next.
 *
 * Dry Run Example:
 *   head = [1,2,3,4], n=4
 *   fast moves twice as fast.
 *   When fast reaches end, slow at index 2 (value=3).
 *   Delete node 3.
 *   Result = [1,2,4]
 *
 * Time Complexity: O(n) (single pass).
 * Space Complexity: O(1).
 *
 * ---------------------------------------------------------------
 * Pattern Summary:
 * - Length count approach: simple, intuitive, but requires two passes.
 * - Slow/fast pointer approach: elegant, single pass, optimal.
 */

//Approach 1: Length Count + Indexing

class Solution {
    public ListNode deleteMiddle(ListNode head) {
        int n = 0;
        ListNode curr = head;
        while (curr != null) {
            n++;
            curr = curr.next;
        }

        if (n == 1) return null; // only one node → delete it

        curr = head;
        for (int i = 0; i < n/2 - 1; i++) {
            curr = curr.next;
        }

        curr.next = curr.next.next; // skip middle
        return head;
    }
}

//Approach 2: Slow/Fast Pointer (Optimized)

class Solution {
    public ListNode deleteMiddle(ListNode head) {
        if (head.next == null) return null; // only one node

        ListNode slow = head, fast = head, prev = null;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // slow is at middle, prev is before middle
        prev.next = slow.next;
        return head;
    }
}