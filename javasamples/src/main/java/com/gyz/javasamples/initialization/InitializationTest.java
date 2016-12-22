package com.gyz.javasamples.initialization;

/**
 * Created by guoyizhe on 16/8/30.
 * 邮箱:gyzboy@126.com
 */
public class InitializationTest {

    static void f(int c){
        System.out.println("f(int)");
    }

    public static void main(String[] args) {
        String overload = "重载:\n" +
                "   1.每个重载的方法必须有一个独一无二的参数类型列表\n" +
                "   2.参数顺序不同";

        System.out.println(overload);

        char c = 'd';
        f(c);//没有char的方法,直接提升为int

        long l = 2222L;
        f((int)l);//没有long,降为int

        B b = new B();
    }
}

class A{
    static{
        System.out.println("A static zone");
    }
    {
        System.out.println("A zone");
    }

    public A(){
        System.out.println("A construtor");
    }
    private  String a = "5";
}

class B extends A{
    enum TTTT{
        HELLO,WORLD,NIHAO,HAIHAOMA
    }

    static{
        System.out.println("B static zone");
        TTTT t = TTTT.HAIHAOMA;
        System.out.println(t.toString());
    }
    {
        System.out.println("B zone");
    }

    public B(){
        System.out.println("B construtor");
    }
}
