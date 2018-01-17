package com.gyz.javasamples.object;

/**
 * Created by guoyizhe on 2017/9/11.
 * 邮箱:gyzboy@126.com
 *
 * @author guoyizhe
 * @date 2017/09/11
 */

public class AutoBoxTest {
    public static void main(String[] args) {
        Integer i1 = 100;
        Integer i2 = 100;
        Integer i3 = 200;
        Integer i4 = 200;
        //在通过valueOf方法创建Integer对象的时候，如果数值在[-128,127]之间，便返回指向IntegerCache.cache中已经存在的对象的引用；
        // 否则创建一个新的Integer对象
        System.out.println(i1 == i2);//true
        System.out.println(i3 == i4);//false

        Double i5 = 100.0;
        Double i6 = 100.0;
        Double i7 = 200.0;
        Double i8 = 200.0;

        //double跟float类似
        System.out.println(i5 == i6);//false
        System.out.println(i7 == i8);//false


//        Integer i = new Integer(xxx)和Integer i =xxx;这两种方式的区别。
//        　　当然，这个题目属于比较宽泛类型的。但是要点一定要答上，我总结一下主要有以下这两点区别：
//        　　1）第一种方式不会触发自动装箱的过程；而第二种方式会触发；
//        　　2）在执行效率和资源占用上的区别。第二种方式的执行效率和资源占用在一般性情况下要优于第一种情况（注意这并不是绝对的）


        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        Long h = 2L;

        //1、当 "=="运算符的两个操作数都是 包装器类型的引用，则是比较指向的是否是同一个对象，
        //2、而如果其中有一个操作数是表达式（即包含算术运算）则比较的是数值（即会触发自动拆箱的过程）
        System.out.println(c==d);
        System.out.println(e==f);
        System.out.println(c==(a+b));
        System.out.println(c.equals(a+b));
        System.out.println(g==(a+b));
        System.out.println(g.equals(a+b));
        System.out.println(g.equals(a+h));

        //RPC方法的返回值和参数必须使用包装数据类型,避免空指针
        Integer test = null;
        System.out.println(test);
    }
}

//        基本类型	装箱类型	取值范围	是否缓存	缓存范围
//        byte	Byte	-128 ~ 127	是	-128 ~ 127
//        short	Short	-2^15 ~ (2^15 - 1)	是	-128 ~ 127
//        int	Integer	-2^31 ~ (2^31 - 1)	是	-128 ~ 127
//        long	Long	-2^63 ~ (2^63 - 1)	是	-128～127
//        float	Float	--	否	--
//        double	Double	--	否	--
//        boolean	Boolean	true, false	是	true, false
//        char	Character	\u0000 ~ \uffff