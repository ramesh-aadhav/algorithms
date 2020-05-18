package questions;

import java.util.LinkedList;
import java.util.Queue;

public class MissingElementInSortedArray {
    public int missingElement(int[] nums, int k) {
        return _linear_2(nums, k);
    }

    private int _naive(int[] nums, int k) {
        int count = 0;
        int idx = 1;
        Queue<Integer> queue = new LinkedList<>();

        int queuePushCount = 0;
        while(idx < nums.length && queuePushCount < k) {
            if(nums[idx-1] != nums[idx]+1) {
                int temp = nums[idx-1]+1;
                while(temp < nums[idx] && queuePushCount < k) {
                    queue.add(temp);
                    temp++;
                    queuePushCount++;
                }
            }

            idx++;
        }

        Integer element = 0;
        while(!queue.isEmpty() && count < k) {
            element = queue.poll();
            count++;
        }

        if(count < k) {
            element = nums[nums.length-1];
            while(count < k) {
                element++;
                count++;
            }
        }

        return element;
    }

    public int _linear(int[] nums, int k) {
        int idx = 1;
        int remaining = k;
        int element = nums[0];

        while(idx < nums.length && remaining > 0) {
            if(nums[idx-1] != nums[idx] + 1) {
                int numsBetween = nums[idx] - nums[idx-1] - 1;
                element = nums[idx-1];
                if(remaining <= numsBetween) {
                    element += remaining;
                    remaining = 0;
                }
                else {
                    element += numsBetween;
                    remaining = remaining - numsBetween;
                }
            }

            idx++;
        }

        if(remaining > 0) {
            element = nums[nums.length-1];
            element += remaining;
        }

        return element;
    }


    /**0 1 2 3 4 5
     * 1 2 3 4 8 10
     *
     * 8 - 1 - 4 = 7 - 4 = 3
     * @param nums
     * @param idx
     * @return
     */
    private int missing(int[] nums, int idx) {
        return nums[idx] - nums[0] - idx;
    }

    private int _linear_2(int[] nums, int k) {
        int n = nums.length;

        if(k > missing(nums, n-1)) {
            return nums[n-1] + k - missing(nums, n-1);
        }

        int idx = 1;
        while(missing(nums, idx) < k)
            idx++;

        return nums[idx-1] + k - missing(nums, idx-1);
    }

    /**
     * Binary Search Method
     * Algorithm
     *
     * Implement missing(idx) function that returns how many numbers are missing until array element with index idx.
     * Function returns nums[idx] - nums[0] - idx.
     *
     * Find an index such that missing(idx - 1) < k <= missing(idx) by a binary search.
     *
     * Return kth smallest nums[idx - 1] + k - missing(idx - 1).
     */
    // Return how many numbers are missing until nums[idx]
    int missing(int idx, int[] nums) {
        return nums[idx] - nums[0] - idx;
    }

    public int _missingElement(int[] nums, int k) {
        int n = nums.length;
        // If kth missing number is larger than
        // the last element of the array
        if (k > missing(n - 1, nums))
            return nums[n - 1] + k - missing(n - 1, nums);

        int left = 0, right = n - 1, pivot;
        // find left = right index such that
        // missing(left - 1) < k <= missing(left)
        while (left != right) {
            pivot = left + (right - left) / 2;

            if (missing(pivot, nums) < k) left = pivot + 1;
            else right = pivot;
        }

        // kth missing number is larger than nums[idx - 1]
        // and smaller than nums[idx]
        return nums[left - 1] + k - missing(left - 1, nums);
    }
}
