package com.gyz.javasamples.date;

import java.util.Calendar;

/**
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */

public class CalendarSelfDefineTest {

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();

        // 设置日期为“2013-09-18”
        cal.set(2013, Calendar.SEPTEMBER, 18);

        // 获取“年”
        System.out.printf("year: %s\n", getYear(cal) );
        // 获取“月”
        System.out.printf("month: %s\n", getMonth(cal) );
        // 获取“上月”
        System.out.printf("previcou month: %s\n", getLastMonth(cal) );
        // 获取“下月”
        System.out.printf("next month: %s\n", getNextMonth(cal) );
        // 获取“日”
        System.out.printf("day: %s\n", getDay(cal) );
        // 获取Cal对应星期几
        System.out.printf("weekday: %s\n", getWeekDay(cal) );
        // 本月天数
        System.out.printf("Current Month days: %s\n", getMonthDays(cal) );
        // 上月天数
        System.out.printf("Previcous Month days: %s\n", getPrevMonthDays(cal) );
        // 下月天数
        System.out.printf("Next Month days: %s\n", getNextMonthDays(cal) );
        // 获取当月第一天的星期几
        System.out.printf("First day' weekday : %s\n", getFirstDayWeekday(cal) );
        // 获取当前月最后一天的星期几
        System.out.printf("Last day' weekday : %s\n", getLastDayWeekday(cal) );
        // 获取上月最后一天的星期几
        System.out.printf("PrevMonth Last day' weekday: %s\n", getLastDayWeekdayOfPrevMonth(cal) );
        // 获取下月第一天的星期几
        System.out.printf("NextMonth First day' weekday: %s\n", getFirstDayWeekdayOfNextMonth(cal) );
    }

    /**
     * 获取“年”
     *
     * @return 例如，2013-09-18，则返回2013
     */
    public static int getYear(Calendar cal) {
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取“月”
     *
     * @return 返回值可以为以下值：
     *  JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER, UNDECIMBER。
     *  其中第一个月是 JANUARY，它为 0。
     *
     *  例如，2013-09-18，则返回8
     */
    public static int getMonth(Calendar cal) {
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取“上一个月”
     *
     * @return 返回值可以为以下值：
     *  JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER, UNDECIMBER。
     *  其中第一个月是 JANUARY，它为 0。
     *
     *  例如，2012-01-12的上一个月是“11”(即DECEMBER)。
     */
    public static int getLastMonth(Calendar cal) {
        return (cal.get(Calendar.MONTH) + 11) % 12;
    }

    /**
     * 获取“下一个月”
     *
     * @return 返回值可以为以下值：
     *  JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER, UNDECIMBER。
     *  其中第一个月是 JANUARY，它为 0。
     *
     *  例如，2013-12-12的下一个月是“1”(即DECEMBER)。
     */
    public static int getNextMonth(Calendar cal) {
        return (cal.get(Calendar.MONTH) + 13) % 12;
    }

    /**
     * 获取“日”
     *
     * @return 例如，2013-09-18，则返回18
     *
     */
    public static int getDay(Calendar cal) {
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取“本月的天数”
     *
     * @return 例如，2013-09-18，则返回30
     *
     */
    public static int getMonthDays(Calendar cal) {
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取“上一个月的天数”
     *
     * @return 例如，2013-01-18，则返回31 (因为2012-12有31天)
     *
     */
    public static int getPrevMonthDays(Calendar cal) {
        Calendar tmpCal = (Calendar)cal.clone();        // 克隆cal。后面对tmpCal操作，就不会改变cal
        tmpCal.add(Calendar.MONTH, -1);                    // 设为“上一个月”
        return tmpCal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取“下一个月的天数”
     *
     * @return 例如，2013-12-18，则返回31 (因为2014-01有31天)
     *
     */
    public static int getNextMonthDays(Calendar cal) {
        Calendar tmpCal = (Calendar)cal.clone();        // 克隆cal。后面对tmpCal操作，就不会改变cal
        tmpCal.add(Calendar.MONTH, 1);                    // 设为“下一个月”
        return tmpCal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取Cal对应星期几
     *
     * @return 返回“星期几”，可以为以下值：
     *   SUNDAY、MONDAY、TUESDAY、WEDNESDAY、THURSDAY、FRIDAY 和 SATURDAY。
     *   SUNDAY为1，MONDAY为2，依次类推。
     * 例如，2013-09-18(星期三)，则返回4
     */
    public static int getWeekDay(Calendar cal) {
        return cal.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 获取当月第一天对应星期几
     *
     * @return SUNDAY为1，MONDAY为2，依次类推。
     */
    public static int getFirstDayWeekday(Calendar cal) {

        Calendar tmpCal = (Calendar)cal.clone();        // 克隆cal。后面对tmpCal操作，就不会改变cal
        tmpCal.set(Calendar.DATE, 1);                    // 把日期设置为当月第一天
        return tmpCal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前月最后一天对应星期几
     *
     * @return SUNDAY为1，MONDAY为2，依次类推。
     */
    public static int getLastDayWeekday(Calendar cal) {
        Calendar tmpCal = (Calendar)cal.clone();        // 克隆cal。后面对tmpCal操作，就不会改变cal
        tmpCal.set(Calendar.DATE, 1);                    // 把日期设置为当月第一天
        tmpCal.roll(Calendar.DATE, -1);                    // 把日期设置为当月最后一天
        return tmpCal.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 获取上月最后一天的星期几
     *
     * @return SUNDAY为1，MONDAY为2，依次类推。
     */
    public static int getLastDayWeekdayOfPrevMonth(Calendar cal) {

        Calendar tmpCal = (Calendar)cal.clone();        // 克隆cal。后面对tmpCal操作，就不会改变cal
        tmpCal.set(Calendar.DATE, 1);                    // 把日期设置为当月第一天
        tmpCal.add(Calendar.DATE, -1);                    // 把日期设置为上一个月最后一天
        return tmpCal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取下月第一天的星期偏移
     *
     * @return SUNDAY为1，MONDAY为2，依次类推。
     */
    public static int getFirstDayWeekdayOfNextMonth(Calendar cal) {

        Calendar tmpCal = (Calendar)cal.clone();        // 克隆cal。后面对tmpCal操作，就不会改变cal
        tmpCal.add(Calendar.MONTH, 1);                    // 设为“下一个月”
        tmpCal.set(Calendar.DATE, 1);                    // 设为“第一天”
        return tmpCal.get(Calendar.DAY_OF_WEEK);
    }
}
