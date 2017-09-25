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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Stack;

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



        Stack<Integer> s = new Stack<Integer>();
        s.push(3);
        s.push(2);
        s.push(1);
        s.push(5);

        Stack<Integer> help = new Stack<>();
        while (!s.isEmpty()){
            int result = s.pop();
            while(!help.isEmpty() && result > help.peek()){
                s.push(help.pop());
            }
            help.push(result);
        }
        while (!help.isEmpty()){
            s.push(help.pop());
        }

        for (Integer integer : s) {
            System.out.println(integer);
        }


        int[] cc = new int[]{3,6,1,33,5};

        //for (int i = 0; i < cc.length; i++) {
        //    for (int j = i; j > 0; j--) {
        //        if(cc[j] < cc[j - 1]){
        //            int temp = cc[j];
        //            cc[j] = cc[j - 1];
        //            cc[j - 1] = temp;
        //        }
        //    }
        //}

        //for (int i = 0,k = 0; i < cc.length; i++,k = i) {
        //    for (int j = i + 1; j < cc.length; j++) {
        //        if (cc[k] > cc[j]) {
        //            k = j;
        //        }
        //        if(i != k){
        //            int temp = cc[i];
        //            cc[i] = cc[k];
        //            cc[k] = temp;
        //        }
        //    }
        //}


        for (int i = cc.length / 2; i>= 0 ; i--) {
            heapInit(cc,i,cc.length - 1);
        }
        for (int i = cc.length - 1; i >= 0; i--) {
            int temp = cc[i];
            cc[i] = cc[0];
            cc[0] = temp;
            heapInit(cc,0,i);
        }


        for (int i = 0; i < cc.length; i++) {
            System.out.println(cc[i]);
        }


        String str = "ababa";

        char[] ch = str.toCharArray();
        Stack<Character> ss = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            ss.push(str.charAt(i));
        }
        for (int i = 0; i < str.length(); i++) {
            if(ss.peek().equals(str.charAt(i))){
                ss.pop();
            }
        }
        if(ss.isEmpty()){
            System.out.println("is ok");
        }else{
            System.out.println("is not ok");
        }
    }

    static void heapInit(int[] a, int parent, int length){
        int temp = a[parent];
        int child = 2 * parent + 1;
        while (child < length){
            if(child + 1 < length && a[child] < a[child + 1]){
                child++;
            }
            if(temp >= a[child]){
                break;
            }
            a[parent] = a[child];
            parent = child;
            child = child * 2 + 1;
        }
        a[parent] = temp;
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
