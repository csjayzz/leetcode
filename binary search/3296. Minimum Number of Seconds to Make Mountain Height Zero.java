import java.util.*;

/*
Problem explanation:
We are given a mountain of height mountainHeight.
We also have several workers, and each worker has a base work time.

If worker i reduces:
1 unit  -> workerTimes[i] * 1 seconds
2 units -> workerTimes[i] * 1 + workerTimes[i] * 2
3 units -> workerTimes[i] * 1 + workerTimes[i] * 2 + workerTimes[i] * 3
...
x units -> workerTimes[i] * (1 + 2 + 3 + ... + x)

So if a worker has base time t and removes x height,
the total time taken by that worker is:

t * (1 + 2 + ... + x)
= t * x * (x + 1) / 2

All workers work simultaneously.
That means the total finishing time is decided by the slowest chosen worker,
because everyone is working in parallel.

Our goal:
Distribute the total mountainHeight across workers so that:
1. total removed height = mountainHeight
2. the maximum finishing time among workers is as small as possible


Example:
mountainHeight = 4
workerTimes = [2, 1, 1]

Possible split:
- worker 0 removes 1
  time = 2 * 1 = 2
- worker 1 removes 2
  time = 1 * (1 + 2) = 3
- worker 2 removes 1
  time = 1 * 1 = 1

Since they work together, total time = max(2, 3, 1) = 3


Core observation:
For a fixed time T, we can ask:
"How much height can all workers remove within T seconds?"

If together they can remove at least mountainHeight, then T is enough.
If not, T is too small.

This yes/no behavior is monotonic:
- if time T works, then any larger time also works
- if time T does not work, any smaller time also does not work

That makes binary search the best optimized solution.
*/

class Solution {

    /*
     * check(mid, workerTimes, mH)
     *
     * Meaning:
     * Can all workers together remove at least mH height
     * within 'mid' seconds?
     *
     * For one worker with base time t:
     * t * x * (x + 1) / 2 <= mid
     *
     * We solve for x:
     * x * (x + 1) <= 2 * mid / t
     *
     * From quadratic formula:
     * x = floor((sqrt(1 + 8 * mid / t) - 1) / 2)
     *
     * The expression below is the same formula written in a numerically
     * convenient form:
     * floor(sqrt(2 * mid / t + 0.25) - 0.5)
     */
    private boolean check(long mid, int[] workerTimes, int mH) {
        long removedHeight = 0;

        for (int t : workerTimes) {
            long units = (long) (Math.sqrt(2.0 * mid / t + 0.25) - 0.5);
            removedHeight += units;

            // Early stopping saves time once we already know mid works.
            if (removedHeight >= mH) {
                return true;
            }
        }

        return removedHeight >= mH;
    }

    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {
        int maxTime = 0;
        for (int t : workerTimes) {
            maxTime = Math.max(maxTime, t);
        }

        /*
         * Search space:
         * minimum possible time = 1
         * maximum possible time = slowest worker does all work alone
         *
         * If one worker with time maxTime removes mountainHeight units alone:
         * total = maxTime * mountainHeight * (mountainHeight + 1) / 2
         */
        long left = 1;
        long right = (long) maxTime * mountainHeight * (mountainHeight + 1) / 2;
        long result = right;

        while (left <= right) {
            long mid = left + (right - left) / 2;

            if (check(mid, workerTimes, mountainHeight)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return result;
    }
}

/*
========================================================================
Approach-1: Greedy simulation with PriorityQueue
========================================================================

Main idea:
We assign the mountain height one unit at a time.
Each time, we give the next unit to the worker who would finish earliest
after taking that unit.

Why this feels natural:
The 1st unit for a worker costs t
The 2nd extra unit costs 2t
The 3rd extra unit costs 3t
So each next unit has increasing cost.

That means if we always assign the next unit to the worker with the
smallest next finishing time, we keep the loads balanced.


What the PriorityQueue stores:
[time_after_next_unit, worker_index]

For every worker, we track:
1. workload[i]          -> how many units are assigned to this worker
2. totalTimePerWorker[i] -> total finishing time after those assigned units


Dry run for Example 1:
mountainHeight = 4
workerTimes = [2, 1, 1]

Initially:
worker 0 next finish = 2
worker 1 next finish = 1
worker 2 next finish = 1

PQ has: [1,1], [1,2], [2,0]

Step 1:
pick worker 1
assign 1 unit
workload[1] = 1
totalTime[1] = 1
next extra unit for worker 1 costs 2
next finish = 1 + 2 = 3

Step 2:
pick worker 2
assign 1 unit
workload[2] = 1
totalTime[2] = 1
next finish = 1 + 2 = 3

Step 3:
pick worker 0
assign 1 unit
workload[0] = 1
totalTime[0] = 2
next finish = 2 + 4 = 6

Step 4:
pick either worker 1 or 2 with next finish 3
suppose worker 1
assign 1 more unit
workload[1] = 2
totalTime[1] = 3

Final worker times:
worker 0 -> 2
worker 1 -> 3
worker 2 -> 1
answer = max = 3


Complexity:
Each of the mountainHeight units is assigned once.
Every assignment does one poll and one add in PQ.

T.C. : O(mountainHeight * log n)
S.C. : O(n)

This approach is intuitive and often easy to understand first.
But the optimized expected solution is Approach-2 using binary search.

Reference code for the greedy idea:

class Solution {
    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {
        int n = workerTimes.length;
        int[] workload = new int[n];
        long[] totalTimePerWorker = new long[n];

        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));

        for (int i = 0; i < n; i++) {
            pq.add(new long[] {(long) workerTimes[i], i});
        }

        while (mountainHeight > 0) {
            long[] best = pq.poll();
            long finishTime = best[0];
            int idx = (int) best[1];

            workload[idx]++;
            totalTimePerWorker[idx] = finishTime;
            mountainHeight--;

            long nextUnitNumber = workload[idx] + 1L;
            long nextFinishTime = totalTimePerWorker[idx] + (long) workerTimes[idx] * nextUnitNumber;
            pq.add(new long[] {nextFinishTime, idx});
        }

        long ans = 0;
        for (long time : totalTimePerWorker) {
            ans = Math.max(ans, time);
        }

        return ans;
    }
}


Code teaching notes for Approach-1:
- `workload[idx]` tells us how many height units a worker has already received.
- `totalTimePerWorker[idx]` is the total time that worker needs for the assigned units.
- The heap always tells us which worker can take the next unit most cheaply in terms of final finish time.
- After assigning one more unit to a worker, we compute that worker's next finish time and push it back.
*/

/*
========================================================================
Approach-2: Binary search on answer
========================================================================

This is the main optimized solution.

Big idea:
Instead of asking:
"How should I distribute units?"

We ask:
"If I am allowed T seconds, can the workers together remove the whole mountain?"

That converts the problem into feasibility checking.


For one worker:
If worker time is t and they remove x units,
required time = t * x * (x + 1) / 2

For a fixed T:
We want the largest x such that
t * x * (x + 1) / 2 <= T

Once we compute this for every worker, we sum all such x values.
If total removable height >= mountainHeight, then T is feasible.


Dry run for Example 2:
mountainHeight = 10
workerTimes = [3, 2, 2, 4]

Suppose we test T = 12.

Worker 0, t = 3:
3 * x * (x + 1) / 2 <= 12
x = 2 works because 3 * 2 * 3 / 2 = 9
x = 3 fails because 3 * 3 * 4 / 2 = 18
So worker 0 can remove 2

Worker 1, t = 2:
2 * x * (x + 1) / 2 <= 12
x * (x + 1) <= 12
x = 3 works because 3 * 4 = 12
So worker 1 can remove 3

Worker 2, t = 2:
same, so can remove 3

Worker 3, t = 4:
4 * x * (x + 1) / 2 <= 12
2 * x * (x + 1) <= 12
x * (x + 1) <= 6
x = 2 works
So worker 3 can remove 2

Total = 2 + 3 + 3 + 2 = 10
That exactly matches mountainHeight.
So T = 12 is feasible.

If we test smaller time, say T = 11,
the total removable height becomes smaller than 10,
so 11 is not enough.
Hence the answer is 12.


Why binary search works:
If T seconds are enough, then T + 1, T + 2, ... are also enough.
If T seconds are not enough, then smaller times are also not enough.

That monotonic property is exactly what binary search needs.


Complexity:
Let n = number of workers
Binary search range is from 1 to:
max(workerTimes) * mountainHeight * (mountainHeight + 1) / 2

For each mid, we scan all workers once.

T.C. : O(n * log(maxTime * mountainHeight^2))
S.C. : O(1)


Code teaching notes for Approach-2:
- `check(mid, workerTimes, mountainHeight)` answers whether `mid` seconds are enough.
- For each worker, we compute how many units that worker can finish within `mid`.
- We add those units across all workers.
- If the sum reaches the target, the time works.
- Binary search then tries to minimize that feasible time.


Equivalent C++ version you gave:

class Solution {
public:
    typedef long long ll;

    bool Check(ll mid, vector<int>& workerTimes, int mH) {
        ll h = 0;

        for(int &t : workerTimes) {
            h += (ll)(sqrt(2.0 * mid/t + 0.25) - 0.5);

            if(h >= mH) {
                return true;
            }
        }

        return h >= mH;
    }

    long long minNumberOfSeconds(int mountainHeight, vector<int>& workerTimes) {
        int maxTime = *max_element(begin(workerTimes), end(workerTimes));
        ll l = 1;
        ll r = (ll)maxTime * mountainHeight * (mountainHeight+1)/2;

        ll result = 0;

        while(l <= r) {
            ll mid = l + (r-l)/2;

            if(Check(mid, workerTimes, mountainHeight)) {
                result = mid;
                r = mid-1;
            } else {
                l = mid+1;
            }
        }

        return result;
    }
};
*/
