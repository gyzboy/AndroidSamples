package com.gyz.javasamples.algorithm;

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
        System.out.println(_2_2("abgggbe"));
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

}
