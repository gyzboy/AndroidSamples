package com.example;

public class Puzzles17 {
    public static void main(String[] args) {
        final int START = 2000000000;
        int count = 0;
//        for (float f = START; f < START + 50; f++) {//尽量不要使用浮点数作为循环索引
        for (int f = START; f < START + 50; f++) {
            count++;
        }
        System.out.println(count);
    }
}
//PS:
//f的初值过大导致加上50后然后转换为float时,所产生的数值等于直接将f转换为float的数值,
// 导致(float)2000000000 == 2000000050,所以循环条件失败

//精度缺失的三种提升:
//int->float      long->float    long->double