package com.gyz.javasamples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class MyClass {

    public static void main(String[] args) {
        System.out.println('a' + 7 + "hello");//'a'先转换为int,'a'对应的ASIC码为97,97+7=104
        double a = Double.NEGATIVE_INFINITY;
        float b = Float.NEGATIVE_INFINITY;
        System.out.println(a == b);//负无穷大是相等的
        System.out.println(0.0 / 0.0);//出现非数
        System.out.println(0.0 / 0.0 == Float.NaN);//两个非数之间不相等
        System.out.println(6.0 / 0 == 555.0 / 0);//所有正无穷大都是相等的
        System.out.println(-8 / 0.0);//得到负无穷大

        //try {
        //    export("recordevent.txt", "recordtouse.txt");//转换16进制为10进制,为adb shell sendevent所用
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        String aa = "c";
        String bb = "d";
        boolean isOk = aa.equals("a") && (!"b".equals(bb));
        System.out.println(isOk);
    }

    public static void export(String input, String output) throws IOException {
        BufferedReader in =
                new BufferedReader(
                        new FileReader(input));
        StringBuilder sb = new StringBuilder();
        String str = null;
        String str4 = "0000";
        String str8 = "00000000";
        while ((str = in.readLine()) != null) {
            String num1 = Integer.parseInt(str.split(" ")[1], 16) + "";
            String num2 = Integer.parseInt(str.split(" ")[2], 16) + "";
            sb.append("0" + str.split(" ")[0]);
            sb.append(" ");
            sb.append(str4.substring(0, 4 - num1.length()) + num1);
            sb.append(" ");
            sb.append(str8.substring(0, 8 - num2.length()) + num2);
            sb.append("\r\n");
        }
        in.close();

        RandomAccessFile mm = new RandomAccessFile(output, "rw");
        mm.writeBytes(sb.toString());
        mm.close();
        System.out.println(sb.toString());

    }
}
