package com.gyz.javasamples.date;


import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.FieldPosition;

/**
 * DateFormat 的作用是 格式化并解析“日期/时间”。实际上，它是Date的格式化工具，它能帮助我们格式化Date，进而将Date转换成我们想要的String字符串供我们使用
 * 不过DateFormat的格式化Date的功能有限，没有SimpleDateFormat强大；但DateFormat是SimpleDateFormat的父类
 *
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */

public class DateFormatTest {

    public static void main(String[] args) {

        // 只显示“时间”：调用getTimeInstance()函数
        testGetTimeInstance() ;

        // 只显示“日期”：调用getDateInstance()函数
        testGetDateInstance() ;

        // 显示“日期”+“时间”：调用getDateTimeInstance()函数
        testGetDateTimeInstance() ;

        // 测试format()函数
        testFormat();
    }

    /**
     * 测试DateFormat的getTimeInstance()函数
     * 它共有3种重载形式：
     * (01) getTimeInstance()
     * (02) getTimeInstance(int style)
     * (03) getTimeInstance(int style, Locale locale)
     *
     * @author skywang
     */
    private static void testGetTimeInstance() {
        Date date = new Date();

        //Locale locale = new Locale("fr", "FR");
        Locale locale = new Locale("zh", "CN");

        // 等价于 DateFormat.getTimeInstance( DateFormat.MEDIUM);
        DateFormat short0  = DateFormat.getTimeInstance( );

        // 参数是：“时间的显示样式”
        DateFormat short1  = DateFormat.getTimeInstance( DateFormat.SHORT);
        DateFormat medium1 = DateFormat.getTimeInstance( DateFormat.MEDIUM);
        DateFormat long1   = DateFormat.getTimeInstance( DateFormat.LONG);
        DateFormat full1   = DateFormat.getTimeInstance( DateFormat.FULL);

        // 参数是：“时间的显示样式” 和 “地区”
        DateFormat short2  = DateFormat.getTimeInstance( DateFormat.SHORT, locale);
        DateFormat medium2 = DateFormat.getTimeInstance( DateFormat.MEDIUM, locale);
        DateFormat long2   = DateFormat.getTimeInstance( DateFormat.LONG, locale);
        DateFormat full2   = DateFormat.getTimeInstance( DateFormat.FULL, locale);

        System.out.println("\n----getTimeInstance ----\n"
                + "(1.0) Empty Param   : " + short0.format(date) +"\n"
                + "(2.1) One Param(s)  : " + short1.format(date) +"\n"
                + "(2.2) One Param(m)  : " + medium1.format(date) +"\n"
                + "(2.3) One Param(l)  : " + long1.format(date) +"\n"
                + "(2.4) One Param(f)  : " + full1.format(date) +"\n"
                + "(3.1) One Param(s,l): " + short2.format(date) +"\n"
                + "(3.2) One Param(m,l): " + medium2.format(date) +"\n"
                + "(3.3) One Param(l,l): " + long2.format(date) +"\n"
                + "(3.4) One Param(f,l): " + full2.format(date) +"\n"
        );
    }

    /**
     * 测试DateFormat的getDateTimeInstance()函数
     * 它共有3种重载形式：
     * (01) getDateInstance()
     * (02) getDateInstance(int style)
     * (03) getDateInstance(int style, Locale locale)
     */
    public static void testGetDateTimeInstance() {
        Date date = new Date();

        Locale locale = new Locale("zh", "CN");

        // 等价于 DateFormat.getDateTimeInstance( DateFormat.MEDIUM);
        DateFormat short0  = DateFormat.getDateTimeInstance( );

        DateFormat short1  = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT);
        DateFormat medium1 = DateFormat.getDateTimeInstance( DateFormat.MEDIUM, DateFormat.MEDIUM);
        DateFormat long1   = DateFormat.getDateTimeInstance( DateFormat.LONG, DateFormat.LONG);
        DateFormat full1   = DateFormat.getDateTimeInstance( DateFormat.FULL, DateFormat.FULL);

        DateFormat short2  = DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.SHORT, locale);
        DateFormat medium2 = DateFormat.getDateTimeInstance( DateFormat.MEDIUM, DateFormat.MEDIUM, locale);
        DateFormat long2   = DateFormat.getDateTimeInstance( DateFormat.LONG, DateFormat.LONG, locale);
        DateFormat full2   = DateFormat.getDateTimeInstance( DateFormat.FULL, DateFormat.FULL, locale);

        System.out.println("\n----getDateTimeInstance ----\n"
                + "(1.0) Empty Param   : " + short0.format(date) +"\n"
                + "(2.1) One Param(s)  : " + short1.format(date) +"\n"
                + "(2.2) One Param(m)  : " + medium1.format(date) +"\n"
                + "(2.3) One Param(l)  : " + long1.format(date) +"\n"
                + "(2.4) One Param(f)  : " + full1.format(date) +"\n"
                + "(3.1) One Param(s,l): " + short2.format(date) +"\n"
                + "(3.2) One Param(m,l): " + medium2.format(date) +"\n"
                + "(3.3) One Param(l,l): " + long2.format(date) +"\n"
                + "(3.4) One Param(f,l): " + full2.format(date) +"\n"
        );
    }

    /**
     * 测试DateFormat的getDateInstance()函数
     * 它共有3种重载形式：
     * (01) getDateTimeInstance()
     * (02) getDateTimeInstance(int dateStyle, int timeStyle)
     * (03) getDateTimeInstance(int dateStyle, int timeStyle, Locale locale)
     */
    public static void testGetDateInstance() {
        Date date = new Date();

        //Locale locale = new Locale("en", "US");
        Locale locale = new Locale("zh", "CN");

        // 等价于 DateFormat.getDateInstance( DateFormat.MEDIUM);
        DateFormat short0  = DateFormat.getDateInstance( );

        DateFormat short1  = DateFormat.getDateInstance( DateFormat.SHORT);
        DateFormat medium1 = DateFormat.getDateInstance( DateFormat.MEDIUM);
        DateFormat long1   = DateFormat.getDateInstance( DateFormat.LONG);
        DateFormat full1   = DateFormat.getDateInstance( DateFormat.FULL);

        DateFormat short2  = DateFormat.getDateInstance( DateFormat.SHORT, locale);
        DateFormat medium2 = DateFormat.getDateInstance( DateFormat.MEDIUM, locale);
        DateFormat long2   = DateFormat.getDateInstance( DateFormat.LONG, locale);
        DateFormat full2   = DateFormat.getDateInstance( DateFormat.FULL, locale);

        System.out.println("\n----getDateInstance ----\n"
                + "(1.0) Empty Param   : " + short0.format(date) +"\n"
                + "(2.1) One Param(s)  : " + short1.format(date) +"\n"
                + "(2.2) One Param(m)  : " + medium1.format(date) +"\n"
                + "(2.3) One Param(l)  : " + long1.format(date) +"\n"
                + "(2.4) One Param(f)  : " + full1.format(date) +"\n"
                + "(3.1) One Param(s,l): " + short2.format(date) +"\n"
                + "(3.2) One Param(m,l): " + medium2.format(date) +"\n"
                + "(3.3) One Param(l,l): " + long2.format(date) +"\n"
                + "(3.4) One Param(f,l): " + full2.format(date) +"\n"
        );

    }


    /**
     * 测试DateFormat的format()函数
     */
    public static void testFormat() {
        Date date = new Date();
        StringBuffer sb = new StringBuffer();
        FieldPosition field = new FieldPosition(DateFormat.YEAR_FIELD);
        DateFormat format = DateFormat.getDateTimeInstance();

        sb =  format.format(date, sb, field);
        System.out.println("\ntestFormat");
        System.out.printf("sb=%s\n", sb);
    }
}
