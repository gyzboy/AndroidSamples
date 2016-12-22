package com.example;

import java.math.BigDecimal;

public class Puzzles2 {
    public static void main(String[] args) {

        System.out.println(2.00 - 1.10);//输出并不是想象中的0.9,并不是所有浮点数都可以用二进制浮点数精确表示
        System.out.printf("%.2f%n",2.00-1.10);

        //使用BigDecimal的(String)参数构造器,而不要使用(Double)参数的构造器,后者会生成一个跟类型位数相同的精确值
        System.out.println(new BigDecimal("2.00").subtract(new BigDecimal("1.10")));

    }
}

//TIPS：
//在需要精确答案的地方避免使用float和double,货币计算尽量使用int\long\BigDecimal
