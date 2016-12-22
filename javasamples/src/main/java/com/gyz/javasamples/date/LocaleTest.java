package com.gyz.javasamples.date;


import java.util.Locale;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/**
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */

public class LocaleTest {

    public static void main(String[] args) {
        // 2种不同的Locale的创建方法
        testDiffDateLocales();

        // 显示所有的Locales
        testAllLocales();
    }


    /**
     *  2种不同的Locale的创建方法
     */
    private static void testDiffDateLocales() {
        // date为2013-09-19 14:22:30
        Date date = new Date(113, 8, 19, 14, 22, 30);

        // 创建“简体中文”的Locale
        Locale localeCN = Locale.SIMPLIFIED_CHINESE;
        // 创建“英文/美国”的Locale
        Locale localeUS = new Locale("en", "US");

        // 获取“简体中文”对应的date字符串
        String cn = DateFormat.getDateInstance(DateFormat.MEDIUM, localeCN).format(date);
        // 获取“英文/美国”对应的date字符串
        String us = DateFormat.getDateInstance(DateFormat.MEDIUM, localeUS).format(date);

        System.out.printf("cn=%s\nus=%s\n", cn, us);
    }

    /**
     *  显示所有的Locales
     */
    private static void testAllLocales() {
        Locale[] ls = Locale.getAvailableLocales();

        System.out.print("All Locales: ");
        for (Locale locale:ls) {
            System.out.printf(locale+", ");
        }
        System.out.println();
    }
}
