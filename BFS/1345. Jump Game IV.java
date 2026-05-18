import java.util.ArrayList;
import java.util.*;
/*
 * Problem: Jump Game IV (LeetCode 1345)
 * --------------------------------------
 * You are given an array arr and start at index 0.
 * In one step, you can jump:
 *   - i + 1 (move right)
 *   - i - 1 (move left)
 *   - j (any index j where arr[i] == arr[j] and i != j)
 *
 * Goal: Find the minimum number of steps to reach the last index.
 *
 * -------------------------------------------------------------------
 * Thought Process:
 * - This is a shortest path problem in an implicit graph:
 *   * Each index is a "node".
 *   * Edges exist to neighbors (i+1, i-1) and to all indices with the same value.
 * - We need the minimum steps → BFS is the natural choice.
 *   * BFS explores level by level.
 *   * The first time we reach the last index, we are guaranteed to have the minimum steps.
 *
 * -------------------------------------------------------------------
 * Why HashMap<Integer, List<Integer>>?
 * - To quickly find all indices j where arr[i] == arr[j].
 * - Without this, we would need O(n) scans at each step → too slow.
 * - With the map, we can jump to all "same value" indices in O(k) where k is the frequency of that value.
 *
 * -------------------------------------------------------------------
 * Optimization (mp.remove(arr[curr])):
 * - Once we process all jumps for a value arr[curr], we remove it from the map.
 * - This prevents revisiting the same group of indices again and again.
 * - Without this, BFS would repeatedly enqueue duplicates → Time Limit Exceeded (TLE).
 *
 * -------------------------------------------------------------------
 * BFS Algorithm:
 * 1. Build a map: value → list of indices.
 * 2. Initialize queue with start index (0), visited[] array.
 * 3. While queue not empty:
 *      - For each index at current level:
 *          * If index == last → return steps.
 *          * Enqueue i-1, i+1 if valid and not visited.
 *          * Enqueue all indices with same value arr[i].
 *          * Remove arr[i] from map to avoid future duplicates.
 *      - Increment steps after finishing the level.
 *
 * -------------------------------------------------------------------
 * Time Complexity:
 * - O(n) because each index is visited at most once.
 * - Each value group is processed once due to mp.remove().
 *
 * Space Complexity:
 * - O(n) for visited[], queue, and HashMap storage.
 *
 * -------------------------------------------------------------------
 * Pattern Summary:
 * - Treat indices as graph nodes.
 * - BFS ensures minimum steps.
 * - Use HashMap to handle "same value" jumps efficiently.
 * - Remove processed values to avoid redundant work.
 */
class Solution {
    public int minJumps(int[] arr) {
        int n = arr.length;
        
        if(n == 1)
            return 0;
        
        boolean[] visited = new boolean[n];
        
        // why ?
        // Because, we need to access those indices where arr[i] == arr[j]
        HashMap<Integer, List<Integer>> mp = new HashMap<>();
        
        for(int i = 0; i < n; i++) {
            mp.putIfAbsent(arr[i], new ArrayList<>());
            mp.get(arr[i]).add(i);
        }
        
        Queue<Integer> que = new LinkedList<>();
        que.offer(0);
        visited[0] = true;
        
        int steps = 0;
        
        while(!que.isEmpty()) {
            int size = que.size();
            
            //check this level
            while(size-- > 0) {
                int curr = que.poll();
                
                if(curr == n - 1) {
                    //BFS ensures minimum steps
                    //We are moving level by level
                    return steps;
                }
                
                int left  = curr - 1;
                int right = curr + 1;
                
                if(left >= 0 && !visited[left]) {
                    que.offer(left);
                    visited[left] = true;
                }
                
                if(right < n && !visited[right]) {
                    que.offer(right);
                    visited[right] = true;
                }
                
               for (int idx : mp.getOrDefault(arr[curr], Collections.emptyList())) {
                     if (!visited[idx]) {
                     que.offer(idx);
                      visited[idx] = true;
                    }
                }


                
                //If you don't erase it, you may be again checking for it
                //in future. It will cause TLE
                mp.remove(arr[curr]);
            }
            
            steps++;
        }
        
        return -1;
    }
}
