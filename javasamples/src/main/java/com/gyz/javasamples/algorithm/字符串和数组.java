package com.gyz.javasamples.algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by guoyizhe on 2017/3/22.
 * 邮箱:gyzboy@126.com
 */

public class 字符串和数组 {
    public static void main(String[] args) {
//        _1_1(new int[]{1,2,3,4,5,6,7},3);
//        _1_2(new int[]{1, 2, 3, 4, 5, 6, 7}, 3);
//        _1_3(new int[]{1, 2, 3, 4, 5, 6, 7}, 3);

//        System.out.println(_2_1("abgggbe"));
//        System.out.println(_2_2("abgggbe"));

//        Set<String> s = new HashSet<>();
//        s.add("leet");
//        s.add("code");
//        System.out.println(_3_1("leetcode",s));
//        System.out.println(_3_2("leetcode", s));

        int a[] = new int[]{3,2,4,8,9};
        int b[] = new int[]{5,9,0,3};
        System.out.println(_4_2(a,b));
    }

    //----------------------------------------------------
    //1.根据步数k旋转数组中的n个元素
    //例如:n = 7,K = 3 旋转 [1,2,3,4,5,6,7]为[5,6,7,1,2,3,4]
    //----------------------------------------------------
    private static void _1_1(int[] nums, int k) {
        if (k > nums.length) {
            k = k % nums.length;
        }

        int[] result = new int[nums.length];

        for (int i = 0; i < k; i++) {
            result[i] = nums[nums.length - k + i];
        }

        int j = 0;
        for (int i = k; i < nums.length; i++) {
            result[i] = nums[j];
            j++;
        }

        System.arraycopy(result, 0, nums, 0, nums.length);
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]);
        }
        //空间与时间复杂度均为o(n)
    }

    private static void _1_2(int[] arr, int order) {
        if (arr == null || order < 0) {
            throw new IllegalArgumentException("Illegal argument!");
        }

        for (int i = 0; i < order; i++) {
            for (int j = arr.length - 1; j > 0; j--) {
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        //空间复杂度均为o(1) 但时间复杂度为o(n * k)
    }

    private static void _1_3(int[] arr, int order) {
        if (arr == null || arr.length == 0 || order < 0) {
            throw new IllegalArgumentException("Illegal argument!");
        }

        if (order > arr.length) {
            order = order % arr.length;
        }

        //length of first part
        int a = arr.length - order;

        reverse(arr, 0, a - 1);
        reverse(arr, a, arr.length - 1);
        reverse(arr, 0, arr.length - 1);

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        //空间复杂度均为o(1) 但时间复杂度为o(n)


    }

    public static void reverse(int[] arr, int left, int right) {
        if (arr == null || arr.length == 1) {
            return;
        }

        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    //----------------------------------------------------
    //2.找出字符串中的最长回文字符串
    //例如:abbcbbe  最长字符为bbcbb
    //----------------------------------------------------
    public static String _2_1(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        int len = s.length();
        int maxLen = 1;
        boolean[][] dp = new boolean[len][len];

        String longest = null;
        for (int l = 0; l < s.length(); l++) {
            for (int i = 0; i < len - l; i++) {
                int j = i + l;
                if (s.charAt(i) == s.charAt(j) && (j - i <= 2 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;

                    if (j - i + 1 > maxLen) {
                        maxLen = j - i + 1;
                        longest = s.substring(i, j + 1);
                    }
                }
            }
        }

        return longest;
        //Time O(n^2) Space O(n^2)
    }

    public static String _2_2(String s) {
        if (s.length() == 0) {
            return null;
        }

        if (s.length() == 1) {
            return s;
        }

        String longest = s.substring(0, 1);
        for (int i = 0; i < s.length(); i++) {
            String tmp = helper(s, i, i);
            if (tmp.length() > longest.length()) {
                longest = tmp;
            }

            tmp = helper(s, i, i + 1);
            if (tmp.length() > longest.length()) {
                longest = tmp;
            }
        }

        return longest;
//        Time O(n^2), Space O(1)
    }

    public static String helper(String s, int begin, int end) {
        while (begin >= 0 && end <= s.length() - 1 && s.charAt(begin) == s.charAt(end)) {
            begin--;
            end++;
        }
        return s.substring(begin + 1, end);
    }

    //----------------------------------------------------
    //3.单词分割
    //例如:s = "leetcode" dict=["leet","code"] return true
    //----------------------------------------------------
    public static boolean _3_1(String s, Set<String> dict) {
        return wordBreakHelper(s, dict, 0);
        //时间复杂度o(n * n)
    }

    public static boolean wordBreakHelper(String s, Set<String> dict, int start) {
        if (start == s.length()) {
            return true;
        }

        for (String a : dict) {
            int len = a.length();
            int end = start + len;

            //end index should be <= string length
            if (end > s.length()) {
                continue;
            }

            if (s.substring(start, start + len).equals(a)) {
                if (wordBreakHelper(s, dict, start + len)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean _3_2(String s, Set<String> wordDict) {
        int[] pos = new int[s.length() + 1];
        Arrays.fill(pos, -1);
        pos[0] = 0;
        for (int i = 0; i < s.length(); i++) {
            if (pos[i] != -1) {
                for (int j = i + 1; j <= s.length(); j++) {
                    String sub = s.substring(i, j);
                    if (wordDict.contains(sub)) {
                        pos[j] = i;
                    }
                }
            }
        }
        return pos[s.length()] != -1;
    }

    //----------------------------------------------------
    //4.找出两个有序数组的中间数
    //----------------------------------------------------
    public static double _4_1(int[] nums1, int[] nums2) {
        int total = nums1.length + nums2.length;
        if (total % 2 == 0) {
            return (findKth(total / 2 + 1, nums1, nums2, 0, 0) + findKth(total / 2, nums1, nums2, 0, 0)) / 2.0;
        } else {
            return findKth(total / 2 + 1, nums1, nums2, 0, 0);
        }
    }

    public static int findKth(int k, int[] nums1, int[] nums2, int s1, int s2) {
        if (s1 >= nums1.length) {
            return nums2[s2 + k - 1];
        }

        if (s2 >= nums2.length) {
            return nums1[s1 + k - 1];
        }

        if (k == 1) {
            return Math.min(nums1[s1], nums2[s2]);
        }

        int m1 = s1 + k / 2 - 1;
        int m2 = s2 + k / 2 - 1;

        int mid1 = m1 < nums1.length ? nums1[m1] : Integer.MAX_VALUE;
        int mid2 = m2 < nums2.length ? nums2[m2] : Integer.MAX_VALUE;

        if (mid1 < mid2) {
            return findKth(k - k / 2, nums1, nums2, m1 + 1, s2);
        } else {
            return findKth(k - k / 2, nums1, nums2, s1, m2 + 1);
        }
    }


    public static double _4_2(int A[], int B[]) {
        int m = A.length;
        int n = B.length;

        if ((m + n) % 2 != 0) // odd
            return (double) findKth(A, B, (m + n) / 2, 0, m - 1, 0, n - 1);
        else { // even
            return (findKth(A, B, (m + n) / 2, 0, m - 1, 0, n - 1)
                    + findKth(A, B, (m + n) / 2 - 1, 0, m - 1, 0, n - 1)) * 0.5;
        }
    }

    public static int findKth(int A[], int B[], int k,
                              int aStart, int aEnd, int bStart, int bEnd) {

        int aLen = aEnd - aStart + 1;
        int bLen = bEnd - bStart + 1;

        // Handle special cases
        if (aLen == 0)
            return B[bStart + k];
        if (bLen == 0)
            return A[aStart + k];
        if (k == 0)
            return A[aStart] < B[bStart] ? A[aStart] : B[bStart];

        int aMid = aLen * k / (aLen + bLen); // a's middle count
        int bMid = k - aMid - 1; // b's middle count

        // make aMid and bMid to be array index
        aMid = aMid + aStart;
        bMid = bMid + bStart;

        if (A[aMid] > B[bMid]) {
            k = k - (bMid - bStart + 1);
            aEnd = aMid;
            bStart = bMid + 1;
        } else {
            k = k - (aMid - aStart + 1);
            bEnd = bMid;
            aStart = aMid + 1;
        }

        return findKth(A, B, k, aStart, aEnd, bStart, bEnd);
    }
}
