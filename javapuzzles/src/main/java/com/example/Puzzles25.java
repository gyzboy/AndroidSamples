package com.example;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class Puzzles25 {
    public static void main(String[] args) {
        BigInteger b1 = new BigInteger("5000");
        BigInteger b2 = new BigInteger("50000");
        BigInteger b3 = new BigInteger("500000");
        BigInteger total = BigInteger.ZERO;
        BigInteger total1 = BigInteger.ZERO;

        total.add(b1);//bigInteger是不可变的
        total.add(b2);
        total.add(b3);


        total1 = total1.add(b1);
        total1 = total1.add(b2);
        total1 = total1.add(b3);

        System.out.println(total);
        System.out.println(total1);


        Set<Name> s = new HashSet<>();
        s.add(new Name("hello","me"));
        System.out.println(s.contains(new Name("hello","me")));
    }
}


class Name{
    private final String first,last;


    Name(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public boolean equals(Object o){
        if (!(o instanceof Name)) {
            return false;
        }
        Name n = (Name) o;
        return n.first.equals(first) && n.last.equals(last);
    }

    @Override
    public int hashCode() {
        return 37 * first.hashCode() + last.hashCode();//重写以后保证相同的对象值返回相同的散列值
    }
}
