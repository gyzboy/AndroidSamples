package com.example;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzles14 {
    public static void main(String[] args) {
        System.out.println(Puzzles14.class.getName().replaceAll(".","/") + ".class");//输出全是/,因为replaceAll第一个参数为正则表达式
        System.out.println(Puzzles14.class.getName().replaceAll("\\.","/") + ".class");
        System.out.println(Puzzles14.class.getName().replaceAll(Pattern.quote("."),"/") + ".class");

        System.out.println(Puzzles14.class.getName().replaceAll(Pattern.quote("."), Matcher.quoteReplacement(File.separator)) + ".class");//将字符串转换为相应的替代字符串
        System.out.println(Puzzles14.class.getName().replace(".", File.separator) + ".class");

    }
}
