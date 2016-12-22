package com.gyz.javasamples.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guoyizhe on 16/9/2.
 * 邮箱:gyzboy@126.com
 */
public class RegExTest {
    public static void main(String[] args) {
        String line = "this is java regex test123 , ee";
        String pattern = "[iso]";//匹配i、s、o几个字母
        lookFor(pattern, line);
        String pattern1 = "[a-c]";//匹配a-c
        lookFor(pattern1, line);
        String pattern2 = ".{2}";//匹配任意两个字符
        lookFor(pattern2, line);
        String pattern3 = "\\d";//匹配数字[0-9]
        lookFor(pattern3, line);
        String pattern4 = "\\D";//匹配非数字
        lookFor(pattern4,line);
        String pattern5 = "\\s";//匹配空白字符
        lookFor(pattern5,line);
        String pattern6 = "\\w";//匹配单词
        lookFor(pattern6,line);
        String pattern7 = "\\W";//匹配非单词
        lookFor(pattern7,line);
        String pattern8 = "a?";//0或1次a
        lookFor(pattern8,line);
        String pattern9 = "i*";//0或多次i
        lookFor(pattern9,line);
        String pattern10 = "t+";//1或多次t
        lookFor(pattern10,line);
        String pattern11 = "e{2}";//2次e
        lookFor(pattern11,line);
        String pattern12 = "e{2,}";//至少2次
        lookFor(pattern12,line);
        String pattern13 = "e{2,3}";//至少2次至多3次
        lookFor(pattern13,line);


        //find 返回找到匹配的下一个位置
        //group 返回匹配部分
        //matches 匹配整个才成功
        //lookingAt 匹配部分就可以


        //group组: A(B|C)D 组0是ABCD 组1是BC 组2是C
        String group = "(\\S+)\\s+((\\S+)\\s+(\\S+))$";
        String test = "Twas brilling and the slithy toves";
        Pattern p = Pattern.compile(group);
        Matcher m = p.matcher(test);
        StringBuilder sb = new StringBuilder();
        while (m.find()){
//            sb.append(m.group(0) + ",");//组0匹配整个正则
//            sb.append(m.group(1) + ",");//组1匹配第一个括号里的(\\S+)
//            sb.append(m.group(2) + ",");//组2匹配第二个括号里的(\S+)\s+(\S+)
            sb.append(m.group(3) + ",");//组3匹配第仨个括号里的(\S+)

        }
        System.out.println(sb.toString());


    }


    static void lookFor(String pattern, String line) {
        System.out.println("----------------");
        System.out.println("匹配" + pattern);
        System.out.println("----------------");
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(line);
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            sb.append(m.group(0) + ",");
        }
        System.out.println(sb.toString());
    }
}
