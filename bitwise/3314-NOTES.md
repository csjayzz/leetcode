# 3314. Construct the Minimum Bitwise Array I

## Problem Description
Given an array `nums` consisting of n prime integers, construct an array `ans` of length n such that:
- For each index i: `ans[i] OR (ans[i] + 1) == nums[i]`
- Each value of `ans[i]` must be minimized
- If no valid value exists, set `ans[i] = -1`

## Key Insight
When we OR a number with its next consecutive number (n | n+1), we're setting additional bits. We need to find the smallest number that, when ORed with its successor, gives us the target prime.

## Examples

### Example 1
- Input: `nums = [2,3,5,7]`
- Output: `[-1,1,4,3]`
- Breakdown:
  - i=0: No value where `ans[0] | (ans[0]+1) = 2` → `-1`
  - i=1: `1 | 2 = 3` ✓
  - i=2: `4 | 5 = 5` ✓
  - i=3: `3 | 4 = 7` ✓

### Example 2
- Input: `nums = [11,13,31]`
- Output: `[9,12,15]`
- Breakdown:
  - i=0: `9 | 10 = 11` ✓
  - i=1: `12 | 13 = 13` ✓
  - i=2: `15 | 16 = 31` ✓

## Solution Approach
Brute force: Try all values from 0 to `nums[i]` and check if `x | (x+1) == nums[i]`

## Constraints
- `1 <= nums.length <= 100`
- `2 <= nums[i] <= 1000`
- `nums[i]` is a prime number
