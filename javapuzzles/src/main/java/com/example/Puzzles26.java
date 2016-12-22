package com.example;

public class Puzzles26 {
    public static void main(String[] args) {
        System.out.println(X.Y.Z);
    }
}

class X{
    static class Y{
        static String Z = "black";
    }
    static C Y = new C();

}
class C{
    String Z = "white";
}
//PS:
//当一个变量和一个类型具有相同的名字,并且它们位于相同的作用域时,变量名具有优先权
