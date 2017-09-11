package com.gyz.javasamples.exceptions;

/**
 * Created by guoyizhe on 16/9/2.
 * 邮箱:gyzboy@126.com
 */
public class ExceptionTest {
    public static void main(String[] args) {
        String throwable = "异常分类:\n" +
                "   Throwable：Throwable 指定代码中可用异常传播机制通过Java 应用程序传输的任何问题的共性。有两个重要的子类：Exception（异常）和Error（错误），二者都是 Java 异常处理的重要子类，各自都包含大量子类。\n" +
                "   Error：程序无法处理的错误，表示运行程序中较严重问题。大多数错误与Coder无关，而表示代码运行时JVM出现的问题。\n" +
                "   Exception：程序本身可以处理的异常。\n" +
                "区别：异常能被程序本身处理，错误是无法处理。\n" +
                "   运行时异常：RuntimeException类及其子类异常，如NullPointerException、IndexOutBoundsException等，这些异常是不检查异常，可以选择捕获处理，也可以不捕获。\n" +
                "   运行时异常的特点是Java编译器不会检查它，也就是说，当程序中可能出现这类异常，即使没有用try-catch语句捕获它，也没有用throws子句声明抛出它，也会编译通过。\n" +
                "   非运行时异常：是RuntimeException以外的异常，类型上都属于Exception类及其子类。从程序语法角度讲是必须进行处理的异常，如果不处理，程序就不能编译通过。如IOException、SQLException等以及用户自定义的Exception异常，一般情况下不自定义检查异常。";

        System.out.println(throwable);


        System.out.println(intc1());
        System.out.println(intc2());
        System.out.println(intc3());

        String tryFinally = "执行顺序原因:\n" +
                "    java虚拟机在执行具有返回值的方法时，会在本地变量列表中创建一块区域来存储方法的返回值，在执行返回语句时便会从这个区域中读取值进行返回。\n" +
                "    代码一中在try中将1赋值给变量x，然后将变量x的值复制到存储返回值的区域，最后返回值区域存储的就是1，执行返回语句时便返回一。\n" +
                "    代码二中，同样将1赋值给变量x，然后将x的值复制到存储返回值的区域，此时返回值的区域的值为1，然后跳转到finally语句中，此时将3赋值给局部变量x，\n" +
                "    然后在将x的值复制到存储返回值的区域，最后执行返回语句，读取到的返回区域中的值就是3.\n" +
                "    代码三中，try中执行的语句是一样的，跳转到了finally语句中后，将3赋值给局部变量，然后将0赋值到存储返回值的区域，最后执行返回语句，读取到的返回区域中的值就是0，所以就返回0。";

        System.out.println(tryFinally);
    }

    static int intc1() {
        int x = 0;
        try {
            x = 1;
            return x;
        } finally {
            x = 3;
        }
    }

    static int intc2() {
        int x = 0;
        try {
            x = 1;
            return x;
        } finally {
            x = 3;
            return x;
        }
    }

    static int intc3(){
        int x =0;
        try{
            x=1;
            return x;
        }finally {
            x = 3;
            return 0;
        }
    }

//    不能在 finally 块中使用 return，finally 块中的 return 返回后方法结束执行，不 会再执行 try 块中的 return 语句
}
