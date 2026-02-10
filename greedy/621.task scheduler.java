import java.util.*;

/*
621. Task Scheduler

You are given tasks (A-Z) and a cooling interval n.
Same task must be separated by at least n intervals.
Each interval can execute one task or stay idle.
Return minimum intervals to finish all tasks.

Example:
tasks = [A, A, A, B, B, B], n = 2
One valid schedule: A, B, idle, A, B, idle, A, B
Answer = 8
*/

/*
Intuition (Greedy + Counting):
- The task with maximum frequency creates the strictest structure.
- If max frequency is maxFreq, think of arranging it as:
  _ A _ A _ A _   (for maxFreq = 4, there are maxFreq - 1 gaps)
- Number of gaps = maxFreq - 1.
- Each gap can hold at most n tasks to satisfy cooldown.
- So initial idle slots needed = (maxFreq - 1) * n.
- Fill these slots using other tasks (up to maxFreq - 1 from each type).
- Remaining slots (if any) are real idles.

Final answer:
- total intervals = tasks.length + remainingIdle

Why subtract by min(maxFreq - 1, count[i])?
- A task type can contribute to at most one position in each gap,
  so it can fill at most (maxFreq - 1) idle slots.

Complexity:
- Time: O(26 log 26) due to sorting fixed array (effectively O(1)).
- Space: O(26) (effectively O(1)).
*/

class Solution {
    public int leastInterval(char[] tasks, int n) {
        int[] count = new int[26];

        for (int i = 0; i < tasks.length; i++) {
            count[tasks[i] - 'A']++;
        }

        Arrays.sort(count);

        int maxFreq = count[25];
        int idle = (maxFreq - 1) * n;

        for (int i = 24; i >= 0; i--) {
            idle -= Math.min(maxFreq - 1, count[i]);
        }

        idle = Math.max(0, idle);
        return tasks.length + idle;
    }
}
