package com.gyz.javasamples.date;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

/**
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */
public class CalendarTest {
    public static void main(String[] args) {

        // 测试Calendar的“17个字段的公共函数接口”
        testAllCalendarSections() ;

        // 测试Calendar的“比较接口”
        testComparatorAPIs() ;

        // 测试Calendar的“比较接口”
        testLenientAPIs() ;

        // 测试Calendar的Date、TimeZone、MilliSecond等相关函数
        testTimeAPIs() ;

        // 测试Calendar的clone()，getFirstDayOfWeek()等接口
        testOtherAPIs() ;

    }


    private static void testSection(Calendar cal, int field, String title) {
        final Random random = new Random();
        final Date date = cal.getTime();

        final int min = cal.getMinimum(field);    // 获取"字段最小值"
        final int max = cal.getMaximum(field);    // 获取“字段最大值”

        final int actualMin = cal.getActualMinimum(field);    // 获取"当前日期下，该字段最小值"
        final int actualMax = cal.getActualMaximum(field);    // 获取“当前日期下，该字段的最大值”

        // 获取“字段的当前值”
        final int ori = cal.get(field);

        // 设置“字段的当前值”, 并获取“设置之后的值”
        final int r1 = random.nextInt(max);
        cal.set(field, r1);
        final int set = cal.get(field);
        try {
            // 回滚“字段的当前值”：在“字段最小值”和“字段最大值”之间回滚。
            // “回滚值”可以为正，也可以为负。
            cal.roll(field, -max);
        } catch (IllegalArgumentException e) {
            // 当field == Calendar.ZONE_OFFSET时，会抛出该异常！
            e.printStackTrace();
        }
        final int roll = cal.get(field);

        // 获取一个随机值
        final int sign = ( random.nextInt(2) == 1) ? 1 : -1;
        final int r2 = sign * random.nextInt(max);
        try {
            // 增加“字段的当前值” ，并获取“新的当前字段值”
            // add的“参数值”可以为正，也可以为负。
            cal.add(field, r2);
        } catch (IllegalArgumentException e) {
            // 当field == Calendar.ZONE_OFFSET时，会抛出该异常！
            e.printStackTrace();
        }
        final int add = cal.get(field);



        // 打印字段信息
        System.out.printf("%s:\n\trange is [%d - %d] actualRange is [%d - %d].  original=%d, set(%d)=%d, roll(%d)=%d, add(%d)=%d\n",
                title, min, max, actualMin, actualMax, ori, r1, set, -max, roll, r2, add);
    }

    private static void testAllCalendarSections() {
        // 00. ERA 字段
        testSection(Calendar.getInstance(), Calendar.ERA, "Calendar.ERA");
        // 01. YEAR 字段
        testSection(Calendar.getInstance(), Calendar.YEAR, "Calendar.YEAR");
        // 02. MONTH 字段
        testSection(Calendar.getInstance(), Calendar.MONTH, "Calendar.MONTH");
        // 03. WEEK_OF_YEAR 字段
        testSection(Calendar.getInstance(), Calendar.WEEK_OF_YEAR, "Calendar.WEEK_OF_YEAR");
        // 04. WEEK_OF_MONTH 字段
        testSection(Calendar.getInstance(), Calendar.WEEK_OF_MONTH, "Calendar.WEEK_OF_MONTH");
        // 05. DATE 字段
        testSection(Calendar.getInstance(), Calendar.DATE, "Calendar.DATE");
        // 06. DAY_OF_MONTH 字段
        testSection(Calendar.getInstance(), Calendar.DAY_OF_MONTH, "Calendar.DAY_OF_MONTH");
        // 07. DAY_OF_YEAR 字段
        testSection(Calendar.getInstance(), Calendar.DAY_OF_YEAR, "Calendar.DAY_OF_YEAR");
        // 08. DAY_OF_WEEK 字段
        testSection(Calendar.getInstance(), Calendar.DAY_OF_WEEK, "Calendar.DAY_OF_WEEK");
        // 09. DAY_OF_WEEK_IN_MONTH 字段
        testSection(Calendar.getInstance(), Calendar.DAY_OF_WEEK_IN_MONTH, "Calendar.DAY_OF_WEEK_IN_MONTH");
        // 10. AM_PM 字段
        testSection(Calendar.getInstance(), Calendar.AM_PM, "Calendar.AM_PM");
        // 11. HOUR 字段
        testSection(Calendar.getInstance(), Calendar.HOUR, "Calendar.HOUR");
        // 12. HOUR_OF_DAY 字段
        testSection(Calendar.getInstance(), Calendar.HOUR_OF_DAY, "Calendar.HOUR_OF_DAY");
        // 13. MINUTE 字段
        testSection(Calendar.getInstance(), Calendar.MINUTE, "Calendar.MINUTE");
        // 14. SECOND 字段
        testSection(Calendar.getInstance(), Calendar.SECOND, "Calendar.SECOND");
        // 15. MILLISECOND 字段
        testSection(Calendar.getInstance(), Calendar.MILLISECOND, "Calendar.MILLISECOND");
        // 16. ZONE_OFFSET 字段
        testSection(Calendar.getInstance(), Calendar.ZONE_OFFSET, "Calendar.ZONE_OFFSET");
    }


    private static void testComparatorAPIs() {
        // 新建cal1 ，且时间为1988年
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, 1988);
        // 新建cal2 ，且时间为2000年
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, 2000);
        // 新建cal3, 为cal1的克隆对象
        Calendar cal3 = (Calendar)cal1.clone();

        // equals 判断 cal1和cal2的“时间、时区等”内容是否相等
        boolean isEqual12 = cal1.equals(cal2);
        // equals 判断 cal1和cal3的“时间、时区等”内容是否相等
        boolean isEqual13 = cal1.equals(cal3);
        // cal1是否比cal2早
        boolean isBefore = cal1.before(cal2);
        // cal1是否比cal2晚
        boolean isAfter = cal1.after(cal2);
        // 比较cal1和cal2
        // (01) 若cal1 早于 cal2，返回-1
        // (02) 若cal1 等于 cal2，返回0
        // (03) 若cal1 晚于 cal2，返回1
        int icompare = cal1.compareTo(cal2);

        System.out.printf("\ntestComparatorAPIs: isEuqal12=%s, isEqual13=%s, isBefore=%s, isAfter=%s, icompare=%s\n",
                isEqual12, isEqual13, isBefore, isAfter, icompare);
    }

    private static void testLenientAPIs() {
        Calendar cal = Calendar.getInstance();

        // 获取默认的“宽容度”。返回true
        boolean oriLenient = cal.isLenient();
        // MONTH值只能是“0-11”，这里越界。但是由于当前cal是宽容的，所以不会抛出异常
        cal.set(Calendar.MONTH, 50);

        // 设置“宽容度”为false。
        cal.setLenient(false);
        // 获取设置后的“宽容度”
        boolean curLenient = cal.isLenient();
        try {
            // MONTH值只能是“0-11”，这里越界。而且当前cal是不宽容的，所以会产生异常。
            // 但是，异常到下次计算日期时才会抛出。即，set()中不回抛出异常，而要等到get()中才会抛出异常
            cal.set(Calendar.MONTH, 50);
            // 此时，对cal进行读取。读取会导致重新计算cal的值，所以此时抛出异常！
            int m2 = cal.get(Calendar.MONTH);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        System.out.printf("\ntestLenientAPIs: oriLenient=%s, curLenient=%s\n",
                oriLenient, curLenient);
    }


    private static void testTimeAPIs() {
        Calendar cal = Calendar.getInstance();

        // 设置cal的时区为“GMT+8”
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        // 获取当前的cal时区
        TimeZone timezone = cal.getTimeZone();

        // 设置 milliseconds
        cal.setTimeInMillis(1279419645742l);
        // 获取 milliseconds
        long millis = cal.getTimeInMillis();
        // 设置 milliseconds之后，时间也改变了。
        // 获取cal对应的日期
        Date date = cal.getTime();

        // 设置时间为“1988-08-08”
        cal.set(1988, 8, 8);
        // 设置时间为“1999-09-09 09:09”
        cal.set(1999, 9, 9, 9, 9);
        // 设置时间为“2000-10-10 10:10:10”
        cal.set(2000, 10, 10, 10, 10, 10);

        System.out.printf("\ntestTimeAPIs: date=%s, timezone=%s, millis=%s\n",
                date, timezone, millis);
    }

    private static void testOtherAPIs() {
        Calendar cal = Calendar.getInstance();
        // 克隆cal
        Calendar clone = (Calendar)cal.clone();

        // 设置 为 2013-01-10。
        // 注：2013-01-01 为“星期二”，2013-01-06为“星期天”，
        clone.set(Calendar.YEAR, 2013);
        clone.set(Calendar.MONTH, 0);
        clone.set(Calendar.DATE, 10);
        // 设置“本年的第一个星期最少包含1天”。
        // 则2013-01-10属于第2个星期
        clone.setMinimalDaysInFirstWeek(1);
        int m1 = clone.getMinimalDaysInFirstWeek();
        int index1 = clone.get(Calendar.WEEK_OF_YEAR);

        // 设置“本年的第一个星期最少包含7天”。
        // 则2013-01-10属于第1个星期
        clone.setMinimalDaysInFirstWeek(7);
        int m2 = clone.getMinimalDaysInFirstWeek();
        int index2 = clone.get(Calendar.WEEK_OF_YEAR);

        // 设置“每周的第一天是星期几”。
        clone.setFirstDayOfWeek(Calendar.WEDNESDAY);
        // 获取“每周的第一天是星期几”。
        int firstdayOfWeek = clone.getFirstDayOfWeek();

        System.out.printf("\ntestOtherAPIs: firstdayOfWeek=%s, [minimalDay, WeekOfYear]={(%s, %s), (%s, %s)} %s\n",
                firstdayOfWeek, m1, index1, m2, index2, clone.getTime());
    }
}
