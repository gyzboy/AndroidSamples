package com.example;

public class Puzzles21 {
    public static void main(String[] args) {
        Animal animal = new Animal();
        Animal dog = new Dog();
        animal.bark();
        dog.bark();
    }
}

class Animal{
    static void bark(){
        System.out.println("wu~~");
    }
}
class Dog extends Animal{
    static void bark(){
        System.out.println("wangwang");
    }
}

//PS:
//static方法没有重载一说,所以调用的都是父类的bark方法