import java.util.*;

public class Array {
    // https://www.interviewbit.com/problems/spiral-order-matrix-i/
    static int[] spiralOrder(final int[][] a) {
        int n = a.length, m = a[0].length;
        // top, left, bottom, right
        int t = 0, l = 0, b = n - 1, r = m - 1, dir = 0;

        int[] out = new int[m * n];
        int k = 0;

        // keep crossing off row(t++ and then b--) and column(l++ and then r--)
        while (t <= b && l <= r) {
            switch (dir) {
                // move left to right top row
                case 0:
                    for (int i = l; i <= r; i++) {
                        out[k] = a[t][i];
                        k++;
                    }

                    t++;
                    dir = 1;
                    break;
                // move top to bottom rightmost row
                case 1:
                    for (int i = t; i <= b; i++) {
                        out[k] = a[i][r];
                        k++;
                    }

                    r--;
                    dir = 2;
                    break;
                // move right to left bottom row
                case 2:
                    for (int i = r; i >= l; i--) {
                        out[k] = a[b][i];
                        k++;
                    }
                    b--;
                    dir = 3;
                    break;
                // move bottom to top leftmost row
                case 3:
                    for (int i = b; i >= t; i--) {
                        out[k] = a[i][l];
                        k++;
                    }

                    l++;
                    dir = 0;
            }
        }

        return out;
    }

    // https://www.interviewbit.com/tutorial/insertion-sort-algorithm/
    static void insertionSort(int[] a) {
        int n = a.length;

        for (int i = 1; i < n; i++) {
            int j = i - 1;
            int key = a[i];

            // move elements of arr[0..i-1], that are greater than key,
            // to one position ahead of their current position
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }

            // insert the element from unsorted list at the hole created
            a[j + 1] = key;
        }
    }

    // https://www.interviewbit.com/tutorial/merge-sort-algorithm/
    static void mergeSort(int[] a, int l, int r) {
        // out of bounds
        if (l > r)
            return;

        int mid = l + (r - l) / 2;
        // recursively sort two subarrays
        mergeSort(a, l, mid);
        mergeSort(a, mid + 1, r);
        // merge the sorted subarrays
        merge(a, l, mid, r);
    }

    // util to merge two subarrays
    private static void merge(int[] a, int l, int m, int r) {
        int[] temp = new int[r - l + 1];
        int i = l, j = m + 1, k = 0;

        // merge the two sub-arrays
        while (i <= m && j <= r) {
            if (a[i] <= a[j])
                temp[k++] = a[i++];
            else
                temp[k++] = a[j++];
        }

        // append remaining of left sub-array
        while (i <= m)
            temp[k++] = a[i++];

        // else append remaining of right sub-array
        while (j <= r)
            temp[k++] = a[j++];

        // copy temp array into original array
        for (i = 0; i < temp.length; i++)
            a[i + l] = temp[i];
    }

    // https://www.interviewbit.com/tutorial/quicksort-algorithm/
    static void quickSort(int[] a, int l, int r) {
        // out of bounds
        if (l > r)
            return;

        // pi is partitioning index, arr[pi] is now at right place
        int pi = partition(a, l, r);

        // Recursively sort elements before partition and after partition
        quickSort(a, l, pi - 1);
        quickSort(a, pi + 1, r);
    }

    /* This function takes last element as pivot, places the pivot element at its correct position in sorted array,
     and places all smaller (smaller than pivot) to left of pivot and all greater elements to right of pivot */
    private static int partition(int[] a, int l, int r) {
        int pivot = a[r];
        int pi = l;

        for (int i = l; i < r; i++) {
            // if current element is smaller than or equal to pivot
            if (a[i] <= pivot) {
                // swap arr[i] and arr[pi]
                swap(a, i, pi);
                pi++;
            }
        }

        // swap arr[pi] and arr[high] (or pivot)
        swap(a, pi, r);

        return pi;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // https://www.interviewbit.com/tutorial/selection-sort/
    static void selectionSort(int[] a) {
        int n = a.length;

        for (int i = 0; i < n; i++) {
            int pos = findMinIndex(a, i, n);

            if (i != pos)
                swap(a, i, pos);
        }
    }

    // find index of minimum element in the array
    private static int findMinIndex(int[] a, int start, int end) {
        int min_pos = start;

        for (int i = start + 1; i < end; i++) {
            if (a[i] < a[min_pos])
                min_pos = i;
        }

        return min_pos;
    }

    // https://www.interviewbit.com/tutorial/bubble-sort/
    static void bubbleSort(int[] a) {
        int n = a.length;
        boolean swap = true;

        // loop till no swap was performed in any iteration
        while (swap) {
            swap = false;

            // push larger element to the end of the array
            for (int i = 0; i < n - 1; i++) {
                if (a[i] > a[i + 1]) {
                    swap(a, i, i + 1);
                    swap = true;
                }
            }

            // last element is maximum now so not needed to check it for swap
            n--;
        }
    }

    // https://www.interviewbit.com/problems/max-non-negative-subarray/
    static int[] maxset(final int[] a) {
        // finding the largest positive sum
        long curr_sum = 0, max_sum = Long.MIN_VALUE;
        int curr_start = 0, start = -1, end = -1;

        boolean allNeg = true;
        for (int val : a) {
            if (val >= 0) {
                allNeg = false;
                break;
            }
        }

        // all numbers are negative
        if (allNeg)
            return new int[0];

        for (int i = 0; i < a.length; i++) {
            // if negative number comes, reset the sum and sub-array endpoints
            if (a[i] < 0) {
                curr_sum = 0;
                curr_start = i + 1;
            } else {
                curr_sum += a[i];

                // sum of current sub-array is more than previous ones OR
                // sum is equal but current sub-array is larger than the previous one
                if ((max_sum < curr_sum) || (max_sum == curr_sum && i - curr_start > end - start)) {
                    start = curr_start;
                    end = i;
                    max_sum = curr_sum;
                }
            }
        }

        return Arrays.copyOfRange(a, start, end + 1);
    }

    // https://www.interviewbit.com/problems/large-factorial/
    static String largeFactorial(int n) {
        // digits of output in reverse order
        int[] res = new int[500];
        // 0! = 1! = 1
        res[0] = 1;

        // no of digits in output
        int res_size = 1;

        // calculate n! = 2 * 3 * ... * n
        for (int x = 2; x <= n; x++)
            res_size = multiply(res, x, res_size);

        // build string with output digits
        StringBuilder builder = new StringBuilder();
        for (int i = res_size - 1; i >= 0; i--)
            builder.append(res[i]);

        return builder.toString();
    }

    // util to multiply digits array with x
    private static int multiply(int[] res, int x, int res_size) {
        int carry = 0;

        for (int i = 0; i < res_size; i++) {
            int prod = res[i] * x + carry;

            res[i] = prod % 10;
            carry = prod / 10;
        }

        while (carry != 0) {
            res[res_size++] = carry % 10;
            carry /= 10;
        }

        return res_size;
    }

    // https://www.interviewbit.com/problems/min-steps-in-infinite-grid/
    static int coverPoints(int[] a, int[] b) {
        int steps = 0;
        // get minimum steps required from previous point to the next and sum up
        for (int i = 1; i < a.length; i++) {
            // minimum steps from (x1, y1) to (x2, y2) is moving diagonally
            // and then towards the coordinate which is not reached yet =
            // max of diagonal distance between both coordinates
            steps += Math.max(Math.abs(a[i] - a[i - 1]), Math.abs(b[i] - b[i - 1]));
        }

        return steps;
    }

    // https://www.interviewbit.com/problems/maximum-sum-triplet/
    static int maxSumTriplet(int[] A) {
        int n = A.length;

        // calculate max suffix array such that maxSuffix[i] = max(A[i]...A[n - 1])
        int[] maxSuffix = new int[n];
        maxSuffix[n - 1] = A[n - 1];

        for (int i = n - 2; i >= 0; i--)
            maxSuffix[i] = Math.max(maxSuffix[i + 1], A[i]);

        // use a treeSet to keep track of element just smaller than current
        TreeSet<Integer> leftMax = new TreeSet<>();
        leftMax.add(A[0]);

        int maxSum = Integer.MIN_VALUE;
        // select A[j] one by one and check for corresponding A[i] and A[k]
        for (int j = 1; j < n - 1; j++) {
            // choose optimum A[k]
            int ak = maxSuffix[j + 1];
            // j < k always so check if A[j] < A[k]
            if (A[j] < ak) {
                // get A[i] for A[j]
                Integer ai = leftMax.lower(A[j]);
                // if there exists an element just smaller than A[j] on its left
                if (ai != null)
                    maxSum = Math.max(maxSum, ai + A[j] + ak);

                // add A[j] to the set for finding just smaller in next iteration
                leftMax.add(A[j]);
            }
        }

        return maxSum;
    }

    // https://www.interviewbit.com/problems/minimum-lights-to-activate/
    static int minLights(int[] A, int B) {
        int n = A.length;
        // initial start position will be min of range of light and the last position
        int i = Math.min(B - 1, n - 1), cnt = 0;

        // keep jumping to the end
        while (i >= 0) {
            // if there is light
            if (A[i] == 1) {
                // turn on the light, mark as on and jump to next unlighted position
                cnt++;
                A[i] = 2;
                i += B;

                // if all positions are covered
                if (i >= n)
                    return cnt;

                // jump to minimum of last position of array or position where light should be
                i = Math.min(n - 1, i + B - 1);
            }
            // light was already turned on
            else if (A[i] == 2)
                return -1;
                // no light, move backwards
            else
                i--;
        }

        // some positions are left unlighted (i < n - 1)
        return -1;
    }

    // https://www.interviewbit.com/problems/add-one-to-number/
    static int[] plusOne(int[] a) {
        int zero_index = 0;
        // find first non-zero digit of the number
        while (zero_index < a.length && a[zero_index] == 0)
            zero_index++;

        // if the number is zero
        if (zero_index == a.length)
            return new int[]{1};

        int carry = 1;
        int[] ans = new int[a.length - zero_index + 1];
        int j = ans.length - 1;

        // add 1 to last digit and keep adding carry till the first non-zero digit
        for (int i = a.length - 1; i >= zero_index; i--) {
            int sum = a[i] + carry;

            ans[j--] = sum % 10;
            carry = sum / 10;
        }

        // if no carry left, start from 1st position
        int start = 1;
        // if there is carry left add at 0th position of answer array
        if (carry != 0) {
            ans[0] = carry;
            start = 0;
        }

        return Arrays.copyOfRange(ans, start, ans.length);
    }

    // https://www.interviewbit.com/problems/max-sum-contiguous-subarray/
    static int maxSubArray(final int[] a) {
        int max_so_far = a[0], curr_max = a[0];

        for (int i = 1; i < a.length; i++) {
            // check if current element is greater than the previous sum
            curr_max = Math.max(a[i], curr_max + a[i]);
            // check if sum of the sub-array till now is greater than any other previous sub-arrays
            max_so_far = Math.max(max_so_far, curr_max);
        }

        return max_so_far;
    }

    // https://www.interviewbit.com/problems/maximum-absolute-difference/
    static int maxArr(int[] a) {
        /*
         |a[i]-a[j]| + |i-j| =
         Case 1: (a[i]-a[j]) + (i-j) = (a[i]+i) - (a[j]+j)
         Case 2: (-a[i]+a[j]) + (-i+j) = -(a[i]+i) + (a[j]+j)
         Case 3: (a[i]-a[j]) + (-i+j) = (a[i]-i) - (a[j]-j)
         Case 4: (-a[i]+a[j]) + (i-j) = -(a[i]-i) + (a[j]-j)

         Hence ans = max([(a[i]-i)-(a[j]-j)], [(a[i]+i)-(a[j]+j)])
        */

        // min and max for a[i]-i
        int min1 = Integer.MAX_VALUE, max1 = Integer.MIN_VALUE;
        // min and max for a[i]+i
        int min2 = Integer.MAX_VALUE, max2 = Integer.MIN_VALUE;

        for (int i = 0; i < a.length; i++) {
            min1 = Math.min(min1, a[i] - i);
            max1 = Math.max(max1, a[i] - i);

            min2 = Math.min(min2, a[i] + i);
            max2 = Math.max(max2, a[i] + i);
        }

        return Math.max(max1 - min1, max2 - min2);
    }

    // https://www.interviewbit.com/problems/partitions/
    static int partitions(int n, int[] A) {
        // calculate total sum
        int s = 0;
        for (int val : A)
            s += val;

        // total sum cannot be divided into 3 parts
        if (s % 3 != 0)
            return 0;

        int s1 = s / 3, s2 = 2 * s1;
        // sum = running sum, cnt = total positions where sum is s1 till now
        int sum = 0, cnt = 0, ans = 0;

        for (int i = 0; i < n - 1; i++) {
            sum += A[i];

            // if sum at position i is s2, it can be paired with all prev positions where sum is s1
            if (sum == s2)
                ans += cnt;
            // if sum at position i is s1, increment the count of positions where sum is s1
            if (sum == s1)
                cnt++;
        }

        return ans;
    }

    // https://www.interviewbit.com/problems/maximum-area-of-triangle/
    static int maxArea(String[] matrix) {
        int r = matrix.length, c = matrix[0].length();
        // calculate first and last occurence from top for each color in each column
        int[][] top = new int[3][c], bottom = new int[3][c];
        // calculate first and last occurence from left for each color among all columns
        int[] left = new int[3], right = new int[3];

        for (int i = 0; i < 3; i++) {
            Arrays.fill(top[i], Integer.MAX_VALUE);
            Arrays.fill(bottom[i], Integer.MIN_VALUE);
        }
        Arrays.fill(left, Integer.MAX_VALUE);
        Arrays.fill(right, Integer.MIN_VALUE);

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                int color = mapColor(matrix[i].charAt(j));

                left[color] = Math.min(left[color], j);
                right[color] = Math.max(right[color], j);

                top[color][j] = Math.min(top[color][j], i);
                bottom[color][j] = Math.max(bottom[color][j], i);
            }
        }

        double maxArea = 0;
        // loop for each column
        for (int i = 0; i < c; i++) {
            // select color of top vertex of base
            for (int x = 0; x < 3; x++) {
                // select color of bottom vertex of base
                for (int y = 0; y < 3; y++) {
                    // if color is same or the vertices are not present in the column
                    if (x == y || top[x][i] == Integer.MAX_VALUE || bottom[y][i] == Integer.MIN_VALUE)
                        continue;

                    // calculate base of the triangle
                    int base = Math.abs(bottom[y][i] - top[x][i]) + 1;

                    // third vertex which forms the height
                    int z = 3 - x - y;
                    // check if the third vertex lies on the left of current column
                    if (left[z] != Integer.MAX_VALUE)
                        maxArea = Math.max(maxArea, 0.5 * base * Math.abs(i - left[z] + 1));

                    // check if the third vertex lies on the right of current column
                    if (right[z] != Integer.MIN_VALUE)
                        maxArea = Math.max(maxArea, 0.5 * base * Math.abs(right[z] - i + 1));
                }
            }
        }

        return (int) Math.ceil(maxArea);
    }

    // util to map 'r', 'g' and 'b' to 0, 1 and 2 resp
    private static int mapColor(char c) {
        switch (c) {
            case 'r':
                return 0;
            case 'g':
                return 1;
            case 'b':
                return 2;
            default:
                return -1;
        }
    }

    // https://www.interviewbit.com/problems/flip/
    static int[] flip(String a) {
        // all 1's in the string
        if (!a.contains("0"))
            return new int[0];

        // use Kadane's algorithm to find largest sequence of 0's
        int[] res = new int[2];
        int start = 0, diff = 0, maxDiff = Integer.MIN_VALUE;

        for (int i = 0; i < a.length(); i++) {
            // 0 is required hence +1, 1 is not required hence -1
            diff += (a.charAt(i) == '0') ? 1 : -1;

            // number of 1's is more in the current sequence
            // so reset starting point to next possible 0
            if (diff < 0) {
                diff = 0;
                start = i + 1;
            }
            // this sequence has more 0's than all previous ones
            else if (maxDiff < diff) {
                maxDiff = diff;
                res[0] = start + 1;
                res[1] = i + 1;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/perfect-peak-of-array/
    static int perfectPeak(int[] A) {
        int n = A.length;

        // leftMax[i] = max(A[0]...A[i])
        int[] leftMax = new int[n];
        leftMax[0] = A[0];
        for (int i = 1; i < n; i++)
            leftMax[i] = Math.max(leftMax[i - 1], A[i]);

        // rightMin = min(A[i]...A[n - 1])
        int rightMin = A[n - 1];

        // check for all middle positions from end
        for (int i = n - 2; i >= 1; i--) {
            // if A[i] is greater than max of all lefts and less than min of all rights
            if (leftMax[i - 1] < A[i] && A[i] < rightMin)
                return 1;

            // add current position in rightMin calculation
            rightMin = Math.min(rightMin, A[i]);
        }

        return 0;
    }

    // https://www.interviewbit.com/problems/spiral-order-matrix-ii/
    static int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int k = 1;
        int l = 0, t = 0, b = n - 1, r = n - 1;

        while (k <= n * n) {
            // move from left to right in topmost row
            for (int i = l; i <= r; i++)
                res[t][i] = k++;
            t++;

            // move from top to bottom in rightmost row
            for (int i = t; i <= b; i++)
                res[i][r] = k++;
            r--;

            // move from right to left in bottommost row
            for (int i = r; i >= l; i--)
                res[b][i] = k++;
            b--;

            // move from bottom to top in leftmost row
            for (int i = b; i >= t; i--)
                res[i][l] = k++;
            l++;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/pascal-triangle/
    static int[][] pascalTriangle(int n) {
        int[][] res = new int[n][];
        if (n == 0)
            return res;

        // first row
        res[0] = new int[]{1};
        if (n == 1)
            return res;

        // second row
        res[1] = new int[]{1, 1};
        if (n == 2)
            return res;

        for (int i = 3; i <= n; i++) {
            res[i - 1] = new int[i];

            // first and last elements of the row are 1
            res[i - 1][0] = 1;
            res[i - 1][i - 1] = 1;

            // middle ones are the sum of the upper row entries
            for (int j = 1; j < i - 1; j++)
                res[i - 1][j] = res[i - 2][j - 1] + res[i - 2][j];
        }

        return res;
    }

    // https://www.interviewbit.com/problems/kth-row-of-pascals-triangle/
    static int[] kthRowOfPascal(int k) {
        int[] res = new int[k + 1];

        // kth row is C(k, 0) to C(k, k)
        for (int i = 0; i <= k; i++)
            res[i] = (int) C(k, i);

        return res;
    }

    // util to calculate combination function
    private static long C(int n, int k) {
        // C(n, k) = C(n, n - k)
        if (k > n / 2)
            return C(n, n - k);

        long num = 1, den = 1;

        for (int i = 0; i < k; i++) {
            num *= (n - i);
            den *= (k - i);
        }

        return num / den;
    }

    // https://www.interviewbit.com/problems/anti-diagonals/
    static int[][] antiDiagonals(int[][] a) {
        int n = a.length;

        int[][] res = new int[2 * n - 1][];
        int pos = 0;

        // upper half of the matrix
        for (int k = 0; k < n; k++) {
            int i = 0, j = k, cnt = 0;

            res[pos] = new int[k + 1];

            // traversing the row
            while (i < n && j >= 0)
                res[pos][cnt++] = a[i++][j--];

            pos++;
        }

        // lower half of the matrix
        for (int k = 1; k < n; k++) {
            int i = k, j = n - 1, cnt = 0;

            res[pos] = new int[n - k];

            // traversing the row
            while (i < n && j >= 0)
                res[pos][cnt++] = a[i++][j--];

            pos++;
        }

        return res;
    }

    // https://www.interviewbit.com/problems/triplets-with-sum-between-given-range/
    static int tripletsInRange(String[] a) {
        // take first three numbers
        float x = Float.parseFloat(a[0]), y = Float.parseFloat(a[1]), z = Float.parseFloat(a[2]);

        for (int i = 3; i < a.length; i++) {
            // if satisfy condition, return
            if (x + y + z > 1 && x + y + z < 2)
                return 1;

            // if sum is greater, replace the largest element in the triplet
            if (x + y + z >= 2) {
                if (x > y && x > z)
                    x = Float.parseFloat(a[i]);
                else if (y > x && y > z)
                    y = Float.parseFloat(a[i]);
                else
                    z = Float.parseFloat(a[i]);
            }
            // sum is smaller, replace the smallest element in the triplet
            else {
                if (x < y && x < z)
                    x = Float.parseFloat(a[i]);
                else if (y < x & y < z)
                    y = Float.parseFloat(a[i]);
                else
                    z = Float.parseFloat(a[i]);
            }
        }

        // if satisfy condition
        if (x + y + z > 1 && x + y + z < 2)
            return 1;
        return 0;
    }

    // https://www.interviewbit.com/problems/find-duplicate-in-array/
    static int multiRepeatedNumber(final int[] a) {
        // consider array as linked list. The point of cycle will be the duplicate number
        int slow = a[0], fast = a[a[0]];

        // keep moving fast pointer at double rate till both meet
        while (slow != fast) {
            slow = a[slow];
            fast = a[a[fast]];
        }

        // set one pointer at start
        slow = a[0];

        // move both pointer at same speed
        while (slow != fast) {
            slow = a[slow];
            fast = a[fast];
        }

        return slow;
    }

    // https://www.interviewbit.com/problems/maximum-consecutive-gap/
    static int unsortedMaximumGap(final int[] a) {
        int n = a.length;
        // less than 2 elements
        if (n <= 1)
            return 0;

        // maximum and minimum of the array
        int max = a[0], min = a[0];
        for (int i = 1; i < n; i++) {
            max = Math.max(max, a[i]);
            min = Math.min(min, a[i]);
        }

        // max and min values for each bucket
        int[] maxBucket = new int[n];
        int[] minBucket = new int[n];

        Arrays.fill(maxBucket, Integer.MIN_VALUE);
        Arrays.fill(minBucket, Integer.MAX_VALUE);

        // average gap between buckets
        float delta = (float) (max - min) / (float) (n - 1);

        // for each value in array
        for (int val : a) {
            // find index of bucket
            int index = Math.round((val - min) / delta);

            // update maximum and minimum for the bucket
            maxBucket[index] = Math.max(maxBucket[index], val);
            minBucket[index] = Math.min(minBucket[index], val);
        }

        int prev = maxBucket[0];
        // maximum gap is the max of difference between minimum value of current bucket
        // and maximum value of previous bucket
        int maxGap = 0;

        for (int i = 1; i < n; i++) {
            if (minBucket[i] == Integer.MAX_VALUE)
                continue;

            // update maxGap
            maxGap = Math.max(maxGap, minBucket[i] - prev);
            prev = maxBucket[i];
        }

        return maxGap;
    }

    // https://www.interviewbit.com/problems/noble-integer/
    static int nobleInteger(int[] a) {
        int i = 0, n = a.length;

        Arrays.sort(a);

        while (i < n) {
            // skip elements equal to current number
            while (i + 1 < n && a[i + 1] == a[i])
                i++;

            // number of elements greater than this number is equal to the number
            if (n - 1 - i == a[i])
                return 1;

            i++;
        }

        // noble integer not found
        return -1;
    }

    // https://www.interviewbit.com/problems/wave-array/
    static int[] wave(int[] a) {
        Arrays.sort(a);

        int i = 1;
        // swap odd indices with their previous
        while (i < a.length) {
            swap(a, i, i - 1);
            i += 2;
        }

        return a;
    }

    // https://www.interviewbit.com/problems/hotel-bookings-possible/
    static boolean hotel(ArrayList<Integer> arrive, ArrayList<Integer> depart, int k) {
        Collections.sort(arrive);
        Collections.sort(depart);

        int n = arrive.size();
        int i = 0, j = 0, cnt = 0;

        while (i < n && j < n) {
            // if another guest arrives before previous ones departure,
            // allot new room, move to next guest
            if (arrive.get(i) < depart.get(j)) {
                cnt++;
                i++;
            }
            // previous guest left, empty his room, wait for next guest to leave
            else {
                cnt--;
                j++;
            }

            // max rooms exceeded
            if (cnt > k)
                return false;
        }

        return true;
    }

    // https://www.interviewbit.com/problems/maximum-unsorted-subarray/
    static int[] subUnsort(int[] A) {
        int n = A.length;
        // running maximum and minimum of array
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        // start and end indices for result
        int start = -1, end = -1;

        // compute running maximum from start
        for (int i = 0; i < n; i++) {
            max = Math.max(max, A[i]);
            // if maximum != current element, update end boundary
            if (max != A[i])
                end = i;
        }

        // if no end boundary computed, array is already sorted
        if (end == -1)
            return new int[]{-1};

        // compute running minimum from the end
        for (int i = n - 1; i >= 0; i--) {
            min = Math.min(min, A[i]);
            // if minimum != current element, update start boundary
            if (min != A[i])
                start = i;
        }

        // found unsorted interval
        return new int[]{start, end};
    }

    // https://www.interviewbit.com/problems/max-distance/
    static int maximumGap(final int[] A) {
        int n = A.length;

        // right maximums for each position
        int[] rMax = new int[n];
        rMax[n - 1] = A[n - 1];
        // rMax[i] = max(A[i..n-1])
        for (int i = n - 2; i >= 0; i--)
            rMax[i] = Math.max(rMax[i + 1], A[i]);

        int i = 0, j = 0, maxGap = 0;
        // traverse both arrays from left to right and maximize j-i
        while (i < n && j < n) {
            // update max gap and check for further j
            if (A[i] <= rMax[j]) {
                maxGap = Math.max(maxGap, j - i);
                j++;
            }
            // try another i which might have a smaller value in array
            else {
                i++;
            }
        }

        return maxGap;
    }

    // https://www.interviewbit.com/problems/largest-number/
    static String largestNumber(final int[] a) {
        int n = a.length;
        // convert to string array for sorting
        String[] arr = new String[n];
        for (int i = 0; i < n; i++)
            arr[i] = String.valueOf(a[i]);

        // sort array with custom comparator
        Arrays.sort(arr, (s1, s2) -> {
            String ab = s1 + s2, ba = s2 + s1;
            // greater string combination should come first
            return ba.compareTo(ab);
        });

        // skip leading zeroes
        int i = 0;
        while (i < n && arr[i].equals("0"))
            i++;

        // if all are zeroes
        if (i == n)
            return "0";

        // append remaining numbers to string
        StringBuilder builder = new StringBuilder();
        for (; i < n; i++)
            builder.append(arr[i]);

        return builder.toString();
    }

    // https://www.interviewbit.com/problems/rotate-matrix/
    @SuppressWarnings("SuspiciousNameCombination")
    static void rotate(ArrayList<ArrayList<Integer>> a) {
        int n = a.size();

        // compute for 4 x 4 matrix on paper to get the idea
        for (int x = 0; x < n / 2; x++) {
            for (int y = x; y < n - 1 - x; y++) {
                int temp = a.get(x).get(y);

                a.get(x).set(y, a.get(n - 1 - y).get(x));
                a.get(n - 1 - y).set(x, a.get(n - 1 - x).get(n - 1 - y));
                a.get(n - 1 - x).set(n - 1 - y, a.get(y).get(n - 1 - x));
                a.get(y).set(n - 1 - x, temp);
            }
        }
    }

    // https://www.interviewbit.com/problems/next-permutation/
    static int[] nextPermutation(int[] a) {
        int n = a.length, last = n - 2;

        // find the last increasing pair
        while (last >= 0 && a[last] > a[last + 1])
            last--;

        // all elements are in decreasing order (i.e last permutation)
        if (last < 0) {
            reverseArray(a, 0, n - 1);
            return a;
        }

        // find the successor of a[last]
        int nextPos = findRightMostSuccessor(a, last + 1, n, a[last]);

        // swap a[last] with its successor
        swap(a, last, nextPos);
        // reverse the array from last + 1 till end
        reverseArray(a, last + 1, n - 1);

        return a;
    }

    // util to find next greatest element (this half will be decreasingly sorted
    // so can find linearly or in O(log n))
    private static int findRightMostSuccessor(int[] a, int start, int n, int k) {
        for (int i = n - 1; i >= start; i--) {
            if (a[i] > k)
                return i;
        }

        return n - 1;
    }

    // util to reverse part of array
    private static void reverseArray(int[] a, int start, int end) {
        while (start < end) {
            swap(a, start, end);
            start++;
            end--;
        }
    }

    // https://www.interviewbit.com/problems/find-permutation/
    static ArrayList<Integer> findPerm(final String s, int n) {
        ArrayList<Integer> out = new ArrayList<>();
        // dual end pointers for deque of numbers 1..n
        int min = 1, max = n;

        // if I is encountered then add the smallest element remaining in output
        // else add the largest element remaining in output
        for (char c : s.toCharArray()) {
            if (c == 'I') {
                out.add(min);
                min++;
            } else {
                out.add(max);
                max--;
            }
        }

        // add the last remaining element
        out.add(max);

        return out;
    }

    // https://www.interviewbit.com/problems/merge-intervals/
    private static class Interval {
        int start = 0, end = 0;
    }

    static ArrayList<Interval> insertInterval(ArrayList<Interval> intervals, Interval newInterval) {
        // rearrange new interval
        if (newInterval.start > newInterval.end) {
            int temp = newInterval.start;
            newInterval.start = newInterval.end;
            newInterval.end = temp;
        }

        int n = intervals.size();
        // given list is empty
        if (n == 0) {
            ArrayList<Interval> ans = new ArrayList<>();
            ans.add(newInterval);
            return ans;
        }

        // Case-1: new interval is at the first position
        if (newInterval.end < intervals.get(0).start) {
            intervals.add(0, newInterval);
            return intervals;
        }

        // Case-2: new interval is at the last position
        if (newInterval.start > intervals.get(n - 1).end) {
            intervals.add(newInterval);
            return intervals;
        }

        // Case-3: new interval overlaps all intervals
        if (newInterval.start <= intervals.get(0).start && newInterval.end >= intervals.get(n - 1).end) {
            ArrayList<Interval> ans = new ArrayList<>();
            ans.add(newInterval);
            return ans;
        }

        // Case 4-5: new interval fits between other intervals or is overlapping
        ArrayList<Interval> ans = new ArrayList<>();
        int i = 0;

        while (i < n) {
            boolean overlap = doesOverlap(newInterval, intervals.get(i));

            if (!overlap) {
                ans.add(intervals.get(i));
                // Case-4: new interval lies between two intervals
                if (newInterval.start > intervals.get(i).end && newInterval.end < intervals.get(i + 1).start)
                    ans.add(newInterval);

                i++;
                continue;
            }

            // Case-5: merge overlapping intervals
            Interval temp = new Interval();
            temp.start = Math.min(newInterval.start, intervals.get(i).start);

            // traverse until intervals are overlapping
            while (i < n && overlap) {
                temp.end = Math.max(newInterval.end, intervals.get(i).end);

                if (i == n - 1)
                    overlap = false;
                else
                    overlap = doesOverlap(newInterval, intervals.get(i + 1));

                i++;
            }

            ans.add(temp);
        }

        return ans;
    }

    // util to check if two intervals overlap
    private static boolean doesOverlap(Interval a, Interval b) {
        return Math.min(a.end, b.end) >= Math.max(a.start, b.start);
    }

    // https://www.interviewbit.com/problems/merge-overlapping-intervals/
    static ArrayList<Interval> merge(ArrayList<Interval> intervals) {
        // sort all intervals by start time
        intervals.sort(Comparator.comparingInt(a -> a.start));

        ArrayList<Interval> res = new ArrayList<>();
        res.add(intervals.get(0));

        for (int i = 1; i < intervals.size(); i++) {
            Interval top = res.get(res.size() - 1), curr = intervals.get(i);
            // does not overlap
            if (top.end < curr.start)
                res.add(curr);
            else
                top.end = Math.max(top.end, curr.end);
        }

        return res;
    }

    // https://www.interviewbit.com/problems/set-matrix-zeros/
    @SuppressWarnings("ForLoopReplaceableByForEach")
    static void setZeroes(ArrayList<ArrayList<Integer>> a) {
        int m = a.size(), n = a.get(0).size();
        // whether first column and first row need to be converted
        boolean firstCol = false, firstRow = false;

        // check if first column contains 0
        for (int i = 0; i < m; i++) {
            if (a.get(i).get(0) == 0) {
                firstCol = true;
                break;
            }
        }

        // check if first row contains 0
        for (int j = 0; j < n; j++) {
            if (a.get(0).get(j) == 0) {
                firstRow = true;
                break;
            }
        }

        // check remaining matrix
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // if a[i][j] is 0, mark its row and column heads (a[i][0] and a[0][j]) as 0
                if (a.get(i).get(j) == 0) {
                    a.get(i).set(0, 0);
                    a.get(0).set(j, 0);
                }
            }
        }

        // for each cell in remaining matrix
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // if row or column header is 0, convert whole row or column to 0
                if (a.get(i).get(0) == 0 || a.get(0).get(j) == 0)
                    a.get(i).set(j, 0);
            }
        }

        // if first column needs to be converted
        if (firstCol) {
            for (int i = 0; i < m; i++)
                a.get(i).set(0, 0);
        }

        // if first row needs to be converted
        if (firstRow) {
            for (int j = 0; j < n; j++)
                a.get(0).set(j, 0);
        }
    }

    // https://www.interviewbit.com/problems/first-missing-integer/
    static int firstMissingPositive(int[] a) {
        // partition the array with positives at left and non-positives at right
        int pi = partitionPositives(a);

        // multiply the number at pos by -1 to mark as visited in the positive subarray
        // if all numbers are present, whole subarray array will be negative now
        for (int i = 0; i < pi; i++) {
            int pos = Math.abs(a[i]) - 1;
            if (pos >= 0 && pos < a.length)
                a[pos] *= -1;
        }

        // if any position is positive after marking, then it is the missing number
        for (int i = 0; i < pi; i++) {
            if (a[i] > 0)
                return i + 1;
        }

        // else the largest positive in the array is missing
        return pi + 1;
    }

    // util to partition array with positives on the left
    private static int partitionPositives(int[] a) {
        int pi = 0, pivot = 0;

        for (int i = 0; i < a.length; i++) {
            // move a[i] to left half and update partition index
            if (a[i] > pivot) {
                swap(a, i, pi);
                pi++;
            }
        }

        return pi;
    }

    // https://www.interviewbit.com/problems/maximum-sum-square-submatrix/
    static int maxSumSquareSubmatrix(int[][] A, int B) {
        int n = A.length;
        // pre-calculate sum of sub-matrix ending at A[i][j]
        // first calculate sum of i x 1 and 1 x i sub-matrices
        for (int i = 1; i < n; i++) {
            A[0][i] = A[0][i] + A[0][i - 1];
            A[i][0] = A[i][0] + A[i - 1][0];
        }

        // sum of sub-matrix ending at A[i][j]
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++)
                A[i][j] = A[i][j] + A[i - 1][j] + A[i][j - 1] - A[i - 1][j - 1];
        }

        // max sum of sub-matrix of size B. Initialize with the first sub-matrix possible
        int maxSum = A[B - 1][B - 1];
        // calculate and compare sub-matrix sum ending at (i, j) of B x B for corner cases (i.e i = B - 1 or j = B - 1)
        for (int i = B; i < n; i++) {
            maxSum = Math.max(maxSum, A[B - 1][i] - A[B - 1][i - B]);
            maxSum = Math.max(maxSum, A[i][B - 1] - A[i - B][B - 1]);
        }

        // calculate and compare sub-matrix sum ending at (i, j) of B x B
        for (int i = B; i < n; i++) {
            for (int j = B; j < n; j++) {
                int matrixSum = A[i][j] - A[i - B][j] - A[i][j - B] + A[i - B][j - B];
                maxSum = Math.max(maxSum, matrixSum);
            }
        }

        return maxSum;
    }

    // https://www.interviewbit.com/problems/repeat-and-missing-number-array/
    static int[] repeatedNumber(final int[] a) {
        long n = a.length;
        long sum = 0, sum2 = 0;

        // sum of 1st n numbers
        long n_sum = n * (n + 1L) / 2L;
        // sum of squares of 1st n numbers
        long n2_sum = n * (n + 1L) * (2L * n + 1L) / 6L;

        // sum and square sum of all numbers in the array
        // sum = n_sum + x - y
        // sum2 = n2_sum + x^2 - y^2 ,
        // where x is the repeating number and y is the missing number
        for (int value : a) {
            sum += value;
            sum2 += (long) Math.pow(value, 2);
        }

        // x - y
        long diff_repeat_miss = sum - n_sum;
        // x + y = (x^2 - y^2)/(x - y)
        long sum_repeat_miss = (sum2 - n2_sum) / diff_repeat_miss;

        // x = (x - y + x + y) / 2
        int repeat = (int) (diff_repeat_miss + sum_repeat_miss) / 2;
        // substitute x in x + y equation
        int missing = (int) sum_repeat_miss - repeat;

        return new int[]{repeat, missing};
    }

    // https://www.interviewbit.com/problems/n3-repeat-number/
    static int repeatedNumber(final List<Integer> a) {
        int n = a.size();
        // empty list, no repeating number
        if (n == 0)
            return -1;
        // single element
        if (n == 1)
            return a.get(0);

        // Moore's voting algorithm variant. Use two candidates
        int first = Integer.MIN_VALUE, second = Integer.MAX_VALUE;
        int count1 = 0, count2 = 0;

        for (int val : a) {
            // first candidate repeating
            if (val == first) {
                count1++;
            }
            // second candidate repeating
            else if (val == second) {
                count2++;
            }
            // change first candidate
            else if (count1 == 0) {
                count1 = 1;
                first = val;
            }
            // change second candidate
            else if (count2 == 0) {
                count2 = 1;
                second = val;
            }
            // number is not equal to both first and second
            else {
                count1--;
                count2--;
            }
        }

        count1 = 0;
        count2 = 0;

        // count actual frequencies of first and second
        for (int val : a) {
            if (val == first)
                count1++;
            else if (val == second)
                count2++;
        }

        // first is the answer
        if (count1 > n / 3)
            return first;
        // second is the answer
        if (count2 > n / 3)
            return second;
        // no n/3 repeating found
        return -1;
    }

    // https://www.interviewbit.com/problems/pick-from-both-sides/
    // consider e.g [-1 100 .... 1 2 3] with B = 2
    static int pickFromBothSides(int[] A, int B) {
        int n = A.length;
        // calculate prefix and suffix sum
        int[] prefixSum = new int[n + 1], suffixSum = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            prefixSum[i] = A[i - 1] + prefixSum[i - 1];
            suffixSum[n - i] = A[n - i] + suffixSum[n - i + 1];
        }

        // find max of (prefix[i] + suffix[n-(B - i)])
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i <= B; i++)
            maxSum = Math.max(maxSum, prefixSum[i] + suffixSum[n - (B - i)]);

        return maxSum;
    }

    // https://www.interviewbit.com/problems/sort-array-with-squares/
    static int[] sortArrayWithSquares(int[] A) {
        int n = A.length;
        // index of first positive number in array
        int pi = findFirstPositiveIndex(A);
        // output array
        int[] res = new int[n];
        int k = 0;

        // i - start of negative half, j - start of positive half
        int i = pi - 1, j = pi;
        // traverse both halves
        while (i >= 0 && j < n) {
            // square of negative number will come first
            if (Math.abs(A[i]) < Math.abs(A[j])) {
                res[k++] = A[i] * A[i];
                i--;
            }
            // square of positive number will come first
            else {
                res[k++] = A[j] * A[j];
                j++;
            }
        }

        // traverse remaining of negative half
        while (i >= 0) {
            res[k++] = A[i] * A[i];
            i--;
        }

        // traverse remaining of positive half
        while (j < n) {
            res[k++] = A[j] * A[j];
            j++;
        }

        return res;
    }

    // util to find index of first positive number in array
    private static int findFirstPositiveIndex(int[] A) {
        int n = A.length;
        int l = 0, r = n - 1, pi = -1;
        // binary search for first positive number
        while (l <= r) {
            int mid = l + (r - l) / 2;
            // if negative, search in 2nd half
            if (A[mid] < 0) {
                l = mid + 1;
            }
            // update answer and search in 1st half
            else {
                pi = mid;
                r = mid - 1;
            }
        }

        return pi;
    }

    // https://www.interviewbit.com/problems/balance-array/
    static int balanceArray(int[] A) {
        int n = A.length;
        // calculate prefix sum for odd and even positions
        // leftOdd[i] = prefix sum of odd positions till i-1
        int[] leftOdd = new int[n], leftEven = new int[n];

        int odd = 0, even = 0;
        for (int i = 0; i < n; i++) {
            leftOdd[i] = odd;
            leftEven[i] = even;

            // update left prefix sum for odd and even positions
            if (i % 2 == 0)
                even += A[i];
            else
                odd += A[i];
        }

        // calculate running suffix sum for odd and even positions after the current position
        int rightOdd = 0, rightEven = 0;
        int cnt = 0;

        for (int i = n - 1; i >= 0; i--) {
            // if the special property holds
            if (leftOdd[i] + rightEven == leftEven[i] + rightOdd)
                cnt++;

            // update right even and odd suffix sums
            if (i % 2 == 0)
                rightEven += A[i];
            else
                rightOdd += A[i];
        }

        return cnt;
    }

    // https://www.interviewbit.com/problems/max-min-05542f2f-69aa-4253-9cc7-84eb7bf739c4/
    // two methods: this and Tournament method (https://www.geeksforgeeks.org/maximum-and-minimum-in-an-array/)
    static int maxMin(int[] A) {
        int n = A.length;
        // minimum and maximum for the array
        int min, max;
        // starting index
        int i;

        // if array length is odd, same min-max and start from 1
        if (n % 2 == 1) {
            min = A[0];
            max = A[0];
            i = 1;
        }
        // else one comparison to find initial min-max and start from 2
        else {
            if (A[0] < A[1]) {
                min = A[0];
                max = A[1];
            } else {
                min = A[1];
                max = A[0];
            }

            i = 2;
        }

        // take elements in pairs - compare larger of them with max and smaller with min and update global max-min
        while (i + 1 < n) {
            if (A[i] > A[i + 1]) {
                if (A[i] > max)
                    max = A[i];
                if (A[i + 1] < min)
                    min = A[i + 1];
            } else {
                if (A[i + 1] > max)
                    max = A[i + 1];
                if (A[i] < min)
                    min = A[i];
            }

            // skip 2 since taking elements in pairs
            i += 2;
        }

        return min + max;
    }

    // https://www.interviewbit.com/problems/leaders-in-an-array/
    static ArrayList<Integer> leadersInArray(ArrayList<Integer> A) {
        ArrayList<Integer> res = new ArrayList<>();
        int n = A.size();
        if (n == 0)
            return res;

        // keep track of max elements to the right of A[i]
        int maxRight = A.get(n - 1);
        // right most is always leader
        res.add(maxRight);

        // check for each element from the end
        for (int i = n - 2; i >= 0; i--) {
            // A[i] is a leader. Add to result and update maxRight
            if (A.get(i) > maxRight) {
                res.add(A.get(i));
                maxRight = A.get(i);
            }
        }

        return res;
    }
}