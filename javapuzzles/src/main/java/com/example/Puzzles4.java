package com.example;

public class Puzzles4 {
    private static final byte TARGET = (byte)0*90;
    public static void main(String[] args) {
        //结果并不是1cafebabe,这里右边是一个int类型的数值,为了执行计算,java会将int的数值拓宽为
        //long类型,然后对两个long类型的数值进行相加


        //     0xffffffffcafebabeL
        // +   0x0000000100000000L
        //------------------------
        //     0x0000000cafebabeL

        //这样就是我们的结果  cafebabe
        System.out.println(Long.toHexString(0x100000000L + 0xcafebabe));
        System.out.println(Long.toHexString(0x100000000L + 0xcafebabeL));



        for (byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b++) {
            if (b == TARGET) {
                System.out.println("Get");
            }
        }


    }
}

//PS:
//尽量避免这种混合计算,或者使用常量定义这些魔幻数字
