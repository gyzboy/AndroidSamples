package com.gyz.javasamples.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by guoyizhe on 2017/3/21.
 * 邮箱:gyzboy@126.com
 */

public class _40 {
    public static void main(String[] args) throws IOException {
//        _1();
//        _2();
//        _3();
//        _4();
//        _5(65);
//        _6();
//        _7();
//        _8();
//        _9();
//        _10();
//        System.out.println(reverse(123));
//        String[] test = ["flow","flower","floi"];
//        longestCommonPrefix(test);
    }


//1、题目：古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第四个月后每个月又生一对兔子，假如兔子都不死，问第20个月的兔子总数为多少？
//程序分析:兔子的规律为数列1,1,2,3,5,8,13,21....

    private static void _1() {
        int i = 1;
        for (i = 1; i <= 20; i++) {
            System.out.println(f(i));
        }
    }

    private static int f(int x) {
        if (x == 1 || x == 2) {
            return 1;
        } else {
            return f(x - 1) + f(x - 2);
        }
    }
//2、题目：判断101-200之间有多少个素数，并输出所有素数。
//程序分析：判断素数的方法：用一个数分别去除2到sqrt(这个数)，如果能被整除，
//则表明此数不是素数，反之是素数。

    private static int _2() {
        int i = 0;
        int count = 0;
        for (i = 101; i <= 200; i++) {
            boolean f = true;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    f = false;
                    break;
                }
            }
            if (f) {
                System.out.print(i + "\n");
                count++;
            } else {
                continue;
            }
        }
        return count;
    }

//3、题目：打印出所有的 "水仙花数 "，所谓 "水仙花数 "是指一个三位数，其各位数字立方和等于该数本身。例如：153是一个 "水仙花数 "，
// 因为153=1的三次方＋5的三次方＋3的三次方。
//程序分析：利用for循环控制100-999个数，每个数分解出个位，十位，百位。

    private static void _3() {
        for (int i1 = 100; i1 < 999; i1++) {
            int i = 0, j = 0, k = 0;
            i = i1 / 100;
            j = (i1 % 100) / 10;
            k = i1 % 10;
            if (i1 == i * i * i + j * j * j + k * k * k) {
                System.out.println(i1);
            }

        }
    }

//4、题目：将一个正整数分解质因数。例如：输入90,打印出90=2*3*3*5。
//程序分析：对n进行分解质因数，应先找到一个最小的质数k，然后按下述步骤完成：
//  (1)如果这个质数恰等于n，则说明分解质因数的过程已经结束，打印出即可。
//  (2)如果n <> k，但n能被k整除，则应打印出k的值，并用n除以k的商,作为新的正整数你,重复执行第一步。
//  (3)如果n不能被k整除，则用k+1作为k的值,重复执行第一步。

    private static void _4() {
        Scanner scan = new Scanner(System.in);// 接收控制台输入的信息
        System.out.print("请输入一个正整数：");

        try {
            int num = scan.nextInt();// 取出控制台输入的信息
            if (num < 2) {// 若输入的数小于2,输出提示信息
                System.out.println("必须输入不小于2的正整数！");
            } else {
                int primeNumber = 2;// 定义最小的质数
                System.out.print(num + " = ");
                while (primeNumber <= num) {// 在质数小于输入的数时，进行循环
                    if (primeNumber == num) {// 当质数等于输入的数时,直接输出
                        System.out.print(num);
                        break;// 跳出循环
                    } else if (num % primeNumber == 0) {// 当输入的数与质数的余数为0时,输出这个质数
                        System.out.print(primeNumber + " * ");
                        num = num / primeNumber;// 把剩下的结果赋给num
                    } else {// 在余数不为0时,质数递增
                        primeNumber++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("必须输入正整数！");// 捕捉异常,输出必须输入整数
        }
    }

//5、题目：利用条件运算符的嵌套来完成此题：学习成绩> =90分的同学用A表示，60-89分之间的用B表示，60分以下的用C表示。
//程序分析：(a> b)?a:b这是条件运算符的基本例子。

    private static void _5(int score) {
        System.out.println("mark is " + (score < 90 ? (score > 60 && score <= 89 ? "B" : "C") : "A"));
    }

//6、题目：输入两个正整数m和n，求其最大公约数和最小公倍数。
//程序分析：利用辗除法。

//把相同质因数跳出来,取两者较小的次幂乘起来,就是最大公约数
//两个数的积除以最大公约数,就是最小公倍数\

//    比如说12和40
//    12=2^2*3
//    40=2^3*5
//    最大公约数=2^2=4
//    最小公倍数=12*40/4=120

    private static void _6() {
        Scanner scan = new Scanner(System.in);// 接收控制台输入的信息
        System.out.println("输入两个正整数(回车键换行):");
        int m = scan.nextInt();
        int n = scan.nextInt();
        int c = gcd(m, n);
        System.out.println("最小公倍数为:" + m * n / c + " 最大公约数为:" + c);
    }

    public static int gcd(int m, int n) {
        while (true) {
            if ((m = m % n) == 0) {
                return n;
            }
            if ((n = n % m) == 0) {
                return m;
            }
        }
    }

    //7、题目：输入一行字符，分别统计出其中英文字母、空格、数字和其它字符的个数。
//程序分析：利用while语句,条件为输入的字符不为 '\n '.
    private static void _7() {
        System.out.println("请输入字符串：");
        Scanner scan = new Scanner(System.in);
        String str = scan.next();
        String E1 = "[\u4e00-\u9fa5]";
        String E2 = "[a-zA-Z]";
        int countH = 0;
        int countE = 0;
        char[] arrChar = str.toCharArray();
        String[] arrStr = new String[arrChar.length];
        for (int i = 0; i < arrChar.length; i++) {
            arrStr[i] = String.valueOf(arrChar[i]);
        }
        for (String i : arrStr) {
            if (i.matches(E1)) {
                countH++;
            }
            if (i.matches(E2)) {
                countE++;
            }
        }
        System.out.println("汉字的个数" + countH);
        System.out.println("字母的个数" + countE);
    }

    //8、题目：求s=a+aa+aaa+aaaa+aa...a的值，其中a是一个数字。例如2+22+222+2222+22222(此时共有5个数相加)，几个数相加有键盘控制。
//程序分析：关键是计算出每一项的值。
    private static void _8() throws IOException {
        int s = 0;
        String output = "";
        BufferedReader stadin = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入a的值");
        String input = stadin.readLine();
        for (int i = 1; i <= Integer.parseInt(input); i++) {
            output += input;
            int a = Integer.parseInt(output);
            s += a;
        }
        System.out.println(s);
    }

    //9、题目：一个数如果恰好等于它的因子之和，这个数就称为 "完数 "。例如6=1＋2＋3.编程找出1000以内的所有完数。
    private static void _9() {
        int s;
        for (int i = 1; i <= 1000; i++) {
            s = 0;
            for (int j = 1; j < i; j++) {
                if (i % j == 0) {
                    s = s + j;
                }
            }
            if (s == i) {
                System.out.print(i + " ");
            }
        }
    }

    //10、题目：有1、2、3、4个数字，能组成多少个互不相同且无重复数字的三位数？都是多少？
//程序分析：可填在百位、十位、个位的数字都是1、2、3、4。组成所有的排列后再去掉不满足条件的排列。
    private static void _10() {
        int i = 0;
        int j = 0;
        int k = 0;
        int t = 0;
        for (i = 1; i <= 4; i++) {
            for (j = 1; j <= 4; j++) {
                for (k = 1; k <= 4; k++) {
                    if (i != j && j != k && i != k) {
                        t += 1;
                        System.out.println(i * 100 + j * 10 + k);
                    }
                }
            }
        }
        System.out.println(t);
    }

    public static int reverse(int x) {
        int result = 0;
        int t = 1;
        int temp = x;
        Queue<Integer> stack = new LinkedList<>();
        while (temp >= 10){
            temp = temp / 10;
            t = t * 10;
        }
        while(t > 0){
            stack.offer(x / t);
            x = x - t;
            t = t / 10;
        }
        for (int i = 0; i < stack.size(); i++) {
            result += stack.poll() * i * 10;
        }
        if(x < 0){
            result = -result;
        }
        return result;
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 1) {
            return strs[0];
        }
        int max = 0;
        int minLen = strs[0].length();
        for (int i = 0; i < strs.length; i++) {
            minLen = Math.min(minLen,strs[i].length());
        }
        for (int j = 0; j < minLen; j++) {
            char tag = strs[0].charAt(j);
            for (int i = 0; i < strs.length; i++) {
                if (tag != strs[i].charAt(j)) {
                    break;
                }
            }
            max = j;
        }
        return strs[0].substring(0,max);
    }
}
