/*
Problem explanation:
We need to build a spanning tree on n nodes.
A spanning tree must:
1. connect all n nodes
2. use exactly n - 1 edges
3. contain no cycle

Each edge is given as:
[u, v, strength, must]

- If must == 1, that edge is mandatory.
  We have no choice: it must be part of the final spanning tree.
  Also, mandatory edges cannot be upgraded.

- If must == 0, that edge is optional.
  We may use it or skip it.
  Also, we may upgrade it at most once, which doubles its strength.

The stability of the spanning tree is:
minimum edge strength among all chosen tree edges

So the goal is not to maximize the total sum.
The goal is to maximize the weakest edge in the final spanning tree.


Key idea:
Instead of directly building the best spanning tree,
we binary search the answer "X":

"Can we build a valid spanning tree such that every chosen edge
 has effective strength at least X?"

If the answer is yes for X, then all values <= X are also possible.
If the answer is no for X, then all values > X are also impossible.

This monotonic behavior makes binary search valid.


How to check a candidate stability X:
An edge can help us only if its usable strength is at least X.

1. Mandatory edges:
   - They must be included.
   - So if any mandatory edge has strength < X, then X is impossible.
   - Also, if mandatory edges alone create a cycle, no spanning tree is possible at all.

2. Optional edges with strength >= X:
   - They can be used directly without upgrade.

3. Optional edges with strength < X but 2 * strength >= X:
   - These can reach X after one upgrade.
   - These are upgrade candidates.

4. Optional edges with 2 * strength < X:
   - Even after upgrade they cannot reach X.
   - Ignore them.

During check(X):
- First merge all mandatory edges.
- Then merge all optional edges already strong enough without upgrade.
- Then try upgrade candidates only when they connect two different components.
- Every such chosen candidate spends 1 upgrade.
- At the end, if all nodes belong to one component, X is feasible.


Why greedily using upgrade candidates is correct:
For feasibility, we only care whether the whole graph can be connected
using at most k upgrades.
Whenever an upgrade candidate connects two different components, it is useful.
If it connects nodes already in the same component, using it would only form a cycle,
so it should not be taken.


Dry run on Example 2:
n = 3
edges = [[0,1,4,0],[1,2,3,0],[0,2,1,0]]
k = 2

Suppose we test X = 6.

Edge [0,1,4,0]:
- 4 < 6
- upgraded strength = 8 >= 6
- upgrade candidate

Edge [1,2,3,0]:
- 3 < 6
- upgraded strength = 6 >= 6
- upgrade candidate

Edge [0,2,1,0]:
- 1 < 6
- upgraded strength = 2 < 6
- useless for X = 6

Initially all nodes are separate:
{0}, {1}, {2}

Try candidate [0,1]:
- connects different components
- use 1 upgrade
- merge => {0,1}, {2}

Try candidate [1,2]:
- connects different components
- use 1 upgrade
- merge => {0,1,2}

All nodes connected and upgrades used = 2 <= k
So X = 6 is feasible.

If we test X = 7:
- [0,1,4,0] can become 8, still usable
- [1,2,3,0] can become 6, not enough
- [0,2,1,0] can become 2, not enough

Then only one usable edge exists, so all 3 nodes cannot be connected.
Hence X = 7 is not feasible.
Therefore answer = 6.


Time complexity:
- Each feasibility check is near O(E * alpha(N))
- Binary search range is up to 2 * 10^5, so about log(2 * 10^5)
- Total: O(E * log(MaxStrength) * alpha(N))

Space complexity:
O(N + E) in the worst case because of DSU and upgrade candidate list.
*/

class DSU {
    int[] parent;
    int[] rank;

    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int x) {
        // Path compression:
        // make every visited node point directly to the root.
        if (x == parent[x]) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }

    public boolean union(int x, int y) {
        int xParent = find(x);
        int yParent = find(y);

        // Same component => adding this edge would create a cycle.
        if (xParent == yParent) {
            return false;
        }

        // Union by rank keeps the DSU shallow.
        if (rank[xParent] > rank[yParent]) {
            parent[yParent] = xParent;
        } else if (rank[xParent] < rank[yParent]) {
            parent[xParent] = yParent;
        } else {
            parent[xParent] = yParent;
            rank[yParent]++;
        }

        return true;
    }
}

class Solution {

    /*
     * check(n, edges, k, mid)
     *
     * Meaning:
     * Can we build a valid spanning tree whose minimum edge strength
     * is at least 'mid'?
     *
     * If yes => mid is feasible
     * If no  => mid is not feasible
     */
    private boolean check(int n, int[][] edges, int k, int mid) {
        DSU dsu = new DSU(n);

        // Store optional edges that are not strong enough now,
        // but become strong enough after one upgrade.
        List<int[]> upgradeCandidates = new ArrayList<>();

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int s = edge[2];
            int m = edge[3];

            if (m == 1) {
                /*
                 * Mandatory edge:
                 * It must appear in the spanning tree.
                 * So if its strength is below mid, this mid is impossible
                 * because mandatory edges cannot be upgraded.
                 */
                if (s < mid) {
                    return false;
                }
                dsu.union(u, v);
            } else {
                /*
                 * Optional edge cases:
                 * 1. s >= mid        => usable directly
                 * 2. s < mid but 2s >= mid => usable only after upgrade
                 * 3. 2s < mid        => useless for this mid
                 */
                if (s >= mid) {
                    dsu.union(u, v);
                } else if (2 * s >= mid) {
                    upgradeCandidates.add(new int[] {u, v});
                }
            }
        }

        /*
         * Now try to use upgrade candidates.
         * We only spend an upgrade if the edge merges two different components.
         * If it connects nodes already in the same component, it is unnecessary.
         */
        for (int[] edge : upgradeCandidates) {
            int u = edge[0];
            int v = edge[1];

            if (dsu.find(u) != dsu.find(v)) {
                if (k <= 0) {
                    return false;
                }
                dsu.union(u, v);
                k--;
            }
        }

        // Final verification: all nodes must belong to one connected component.
        int root = dsu.find(0);

        for (int node = 1; node < n; node++) {
            if (dsu.find(node) != root) {
                return false;
            }
        }

        return true;
    }

    public int maxStability(int n, int[][] edges, int k) {

        /*
         * Step 1:
         * Validate mandatory edges first.
         *
         * Since all mandatory edges must be included,
         * if they already form a cycle, then no valid spanning tree exists.
         */
        DSU dsu = new DSU(n);

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int m = edge[3];

            if (m == 1) {
                if (dsu.find(u) == dsu.find(v)) {
                    return -1;
                }
                dsu.union(u, v);
            }
        }

        /*
         * Step 2:
         * Binary search the answer.
         *
         * Lower bound = 1
         * Upper bound = 2 * 10^5
         *
         * Why 2 * 10^5?
         * Max original strength is 10^5.
         * After one upgrade it can become 2 * 10^5.
         */
        int result = -1;
        int l = 1;
        int r = (int) 2e5;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (check(n, edges, k, mid)) {
                // mid is achievable, so try for a bigger minimum stability.
                result = mid;
                l = mid + 1;
            } else {
                // mid is too large, reduce the target.
                r = mid - 1;
            }
        }

        return result;
    }
}
