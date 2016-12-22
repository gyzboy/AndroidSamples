package com.example;

public class Puzzles24 {
    public static void main(String[] args) {

        //这里想要在子类Mything调用父类构造前获得i的值,然后赋值给arg
        System.out.println(new MyThing());
    }
}

class Thing{
    public Thing(int i) {
        System.out.println("thing 's i is :" + i);
    }
}

class MyThing extends Thing{

    private final int arg;

//    public MyThing() {
//        super(arg = Add.add());//is wrong
//    }

    public MyThing(){
        this(Add.add());//私有构造器捕获模式
    }

    private MyThing(int i){
        super(i);
        arg = i;
    }

}

class Add{
    static int add(){
        return 15;
    }
}
