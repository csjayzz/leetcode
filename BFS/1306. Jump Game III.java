/*
 * Problem: Jump Game III (LeetCode 1306)
 * --------------------------------------
 * You are given an array of non-negative integers arr and a starting index "start".
 * From index i, you can jump to:
 *    - i + arr[i]   (forward jump)
 *    - i - arr[i]   (backward jump)
 * The goal is to determine if you can reach ANY index with value 0.
 * Constraint: You cannot jump outside the array.
 *
 * Example:
 *   arr = [4,2,3,0,3,1,2], start = 5 → true
 *   Path: 5 → 4 → 1 → 3 (arr[3] == 0)
 *
 * -------------------------------------------------------------------
 * Thought Process:
 * - Each index can be seen as a "node" in a graph.
 * - From each node, you have up to 2 edges (forward and backward).
 * - The problem reduces to: "Is there a path from start to a node with value 0?"
 * - This is a classic graph traversal problem → BFS or DFS both work.
 *
 * Why BFS/DFS?
 * - Linear scan won't work because jumps are irregular.
 * - Binary search doesn't apply because the array is not sorted.
 * - BFS/DFS ensures we explore all reachable indices systematically.
 *
 * -------------------------------------------------------------------
 * BFS Approach (Queue):
 * - Use a queue to explore indices level by level.
 * - Mark visited indices to avoid infinite loops.
 * - If we dequeue an index with arr[i] == 0 → return true.
 *
 * Time Complexity: O(n) → each index is enqueued at most once.
 * Space Complexity: O(n) → visited[] + queue.
 *
 * -------------------------------------------------------------------
 * DFS Approach (Recursion):
 * - Recursively explore forward and backward jumps.
 * - Use visited[] to avoid revisiting indices.
 * - If arr[i] == 0 → return true.
 *
 * Time Complexity: O(n) → each index visited once.
 * Space Complexity: O(n) → visited[] + recursion stack.
 *
 * -------------------------------------------------------------------
 * Pattern Summary:
 * - Treat array indices as graph nodes.
 * - Use BFS or DFS to explore reachable nodes.
 * - Stop when you find arr[i] == 0.
 * - Always mark visited to prevent cycles.
 */

//bfs 
class Solution {
    public boolean canReach(int[] arr, int start) {
        int n = arr.length;
        boolean[] visited = new boolean[n];
        Queue<Integer> q = new LinkedList<>();

        q.offer(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            int i = q.poll();

            if (arr[i] == 0) return true;

            int forward = i + arr[i];
            if (forward < n && !visited[forward]) {
                visited[forward] = true;
                q.offer(forward);
            }

            int backward = i - arr[i];
            if (backward >= 0 && !visited[backward]) {
                visited[backward] = true;
                q.offer(backward);
            }
        }

        return false;
    }
}

//bfs
class SolutionDFS {
    public boolean canReach(int[] arr, int start) {
        boolean[] visited = new boolean[arr.length];
        return dfs(arr, start, visited);
    }

    private boolean dfs(int[] arr, int i, boolean[] visited) {
        if (i < 0 || i >= arr.length || visited[i]) return false;
        if (arr[i] == 0) return true;

        visited[i] = true;

        return dfs(arr, i + arr[i], visited) || dfs(arr, i - arr[i], visited);
    }
}
