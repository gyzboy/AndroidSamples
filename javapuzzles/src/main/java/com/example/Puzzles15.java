package com.example;

import java.util.Random;

public class Puzzles15 {
    private static Random rnd = new Random();

    public static void main(String[] args) {
        StringBuffer sb = null;
        switch (rnd.nextInt(2)) {
            case 1:
                sb = new StringBuffer('P');
            case 2:
                sb = new StringBuffer('G');
            default:
                sb = new StringBuffer('M');
        }
        sb.append('a');
        sb.append('i');
        sb.append('n');
        System.out.println(sb.toString());
    }
}
//PS:
//输出 ain,几个点:
//1.随机因子为2,所以取值只会在0，1之间,2永远不会触发
//2.没有break,会一直执行到default
//3.StringBuffer的构造中传递的参数是容量因子,不是默认初始值,所以'P','G','M'会转换为相应的ASCII码,作为初始容量存在
//4.char更像是int,而不是String
