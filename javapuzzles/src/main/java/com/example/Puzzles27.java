package com.example;

public class Puzzles27 {

    private final String name;

    public Puzzles27(String name) {
        this.name = name;
    }

    private String name() {
        return name;
    }

    private void reproduce() {
        new Puzzles27("reproduce") {
            void printName() {
                System.out.println(name());//匿名内部类无法访问private对象
            }
        }.printName();
    }

    public static void main(String[] args) {
        new Puzzles27("main").reproduce();
    }
}

