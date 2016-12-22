package com.gyz.javasamples.date;

import java.util.Date;
import java.util.Calendar;

/**
 * Date 是表示时间的类。
 * 一个Date对象表示一个特定的瞬间，能精确到毫秒。我们可以通过这个特定的瞬间，来获取到Date对应的“年、月、日、时、分、秒”。反之亦然，我们也可以通过设置Date的“年、月、日、时、分、秒”等信息，来改变Date所指定的特定瞬间。
 * 除了“年月日时分秒”等信息之外，Data也允许格式化和解析日期字符串。即，我们可以定义一个字符串，这个字符串包含时间信息，然后将字符串通过Date来解析，从而得到相应的Date对象。
 *
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */

public class DateTest {

    public static void main(String[] args) {

        // 测试Date的构造函数：Date共有5类构造函数
        testDateConstructor();

        // 测试Date类的“设置”、“读取”函数
        testDateSet();

        // 测试Date类的before(), after(), compareTo(), equals(), clone(), parse()等接口
        testOtherDateAPIs();
    }

    /**
     * 测试Date的构造函数：Date共有5类构造函数
     */
    private static void testDateConstructor() {

        Date date;

        // Date构造函数一：传入“年、月、日”。
        // 参数说明
        // (01) 年 -- 减 1900 的年份。若要设为1988，则“年”应该是88。
        // (02) 月 -- 0-11 的月份。0是一月,1是二月,依次类推。
        // (03) 日 -- 1-31 之间的某一天。
        // 设置时间为“1988-08-08”
        date = new Date(88,7,8);
        System.out.printf("Constructor-1  : %s\n", tostring(date));

        // Date构造函数二：传入“年、月、日、时、分”
        // (01) 年 -- 减 1900 的年份。若要设为1988，则“年”应该是88。
        // (02) 月 -- 0-11 的月份。0是一月,1是二月,依次类推。
        // (03) 日 -- 1-31 之间的某一天。
        // (04) 时 -- 0-23 之间的小时数。
        // (05) 分 -- 0-59 之间的分钟数。
        // 设置时间为“1999-09-09 19:19”
        date = new Date(99,8,9,19,19);
        System.out.printf("Constructor-2  : %s\n", tostring(date));

        // Date构造函数三：传入“年、月、日、时、分、秒”
        // (01) 年 -- 减 1900 的年份。若要设为1988，则“年”应该是88。
        // (02) 月 -- 0-11 的月份。0是一月,1是二月,依次类推。
        // (03) 日 -- 1-31 之间的某一天。
        // (04) 时 -- 0-23 之间的小时数。
        // (05) 分 -- 0-59 之间的分钟数。
        // (06) 秒 -- 0-59 之间的秒钟数。
        date = new Date(100,10,10,20,10,10);
        System.out.printf("Constructor-3  : %s\n", tostring(date));

        // Date构造函数四：传入“毫秒”。 毫秒 = “目标时间” - “1970-01-01 00:00:00 GMT”
        // 973858210000(ms) 对应时间 2000-10-10 8:10:10
        date = new Date(973858210000l);
        System.out.printf("Constructor-4  : %s\n", tostring(date));

        // Date构造函数五：传入“字符串”。可以为以下几种格式：
        // (注意，year值 = “实际年份-1900”)
        // 1955-08-12 13:30:00
        date = new Date("Sat, 12 Aug 95 13:30:00 GMT");
        System.out.printf("Constructor-5.1: %s\n", tostring(date));
        // 1955-08-12 13:30:00
        date = new Date("12 Aug 95 13:30:00");
        System.out.printf("Constructor-5.2: %s\n", tostring(date));
        // 1955-08-12
        date = new Date("12 Aug 95");
        System.out.printf("Constructor-5.3: %s\n", tostring(date));
    }

    /**
     * 测试Date类的“读取”函数
     */
    private static void testGet(Date date) {
        // “年”。减 1900 的年份。若为1988，则“年”是88。
        int year = date.getYear();
        // “月”。 0-11 的月份。0是一月,1是二月,依次类推。
        int month = date.getMonth();
        // “日”
        int day = date.getDate();
        // “时”
        int hour = date.getHours();
        // “分”
        int minute = date.getMinutes();
        // “秒”
        int second = date.getSeconds();
        // “星期(几)”。 周日是1,周一是2,周二是3,依次类推。
        int weekday = date.getDay();
        // “毫秒”。毫秒 = “目标时间” - “1970-01-01 00:00:00 GMT”
        long millis = date.getTime();
        // “时区偏移”。相对于 UTC 的本地时区的偏移量（以分钟为单位）。
        int timezoneoffset = date.getTimezoneOffset();

        //System.out.printf("\t!!!date get is: %s\n", tostring(date));
        System.out.println("\t!!!get date: "+year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+"\t"+millis+"(ms)");
    }


    /**
     * 测试Date类的“设置”函数
     */
    private static void testDateSet() {
        // 新建date
        Date date = new Date(99,8,9);

        // 设置Date为“2013-09-19 15:28:30”
        // 设为“2013年”。传入值是“‘目标年份’ - ‘1900 的年份’”
        date.setYear(113);
        // 设为“8月”，传入的参数应该是8。因为，一月是0,二月是1,依次类推。
        date.setMonth(8);
        // 设为“19日”
        date.setDate(19);
        // 设为“15”(上午)。采用的24时制；因此，若要设为上午3点，应该传入参数3。
        date.setHours(11);
        // 设为“28分”
        date.setMinutes(28);
        // 设为“30秒”
        date.setSeconds(30);
        System.out.printf("new date-01 is: %s\n", tostring(date));

        // 测试Date的获取函数
        testGet(date);

        // 设为“毫秒”，1379561310000(ms) 对应的时间是“2013-09-19 15:28:30”
        date.setTime(1379561310000l);
        System.out.printf("new date-02 is: %s\n", tostring(date));
    }

    /**
     * 测试Date类的before(), after(), compareTo(), equals(), clone(), parse()等接口
     */
    private static void testOtherDateAPIs() {
        // 初始化d1=2008-05-12, d2=2009-03-15。
        Date d1 = new Date(108,4,12);
        System.out.printf("\nd1 is: %s\n", tostring(d1));
        Date d2 = new Date(109,2,15);
        System.out.printf("d2 is: %s\n", tostring(d2));

        // 克隆
        Date d3 = (Date) d1.clone();
        System.out.printf("clone of d1 is: %s\n", tostring(d3));

        // d1 是否早于 d2
        boolean isBefore = d1.before(d2);
        // d1 是否晚于 d2
        boolean isAfter = d1.after(d2);
        // d1 是否等于 d2
        boolean isEquals = d1.after(d2);
        // d1 和 d2 比较。
        // 若d1 早于 d2，返回 -1
        // 若d1 晚于 d2，返回 1
        // 若d1 等于 d2，返回 0
        int comp = d1.compareTo(d2);
        System.out.printf("isBefore=%s, isAfter=%s, isEquals=%s, comp=%s\n",
                isBefore, isAfter, isEquals, comp);

        // parse接口
        long millis = Date.parse("13 Mar 2009");
        // (注意，通过这种方式设置Date，获取的Year值是“实际年份-1900”)
        Date d4 = new Date(millis);
        System.out.printf("millis=%s, d4=%s\n", millis, tostring(d4));
        System.out.printf("d1.toGMTString()%s\n", d1.toGMTString());
        System.out.printf("d1.toLocaleString()%s\n", d1.toLocaleString());
        System.out.printf("d1.toString()%s\n", d1.toString());
    }

    /**
     * 将date转换Calendar对象，并返回实际的年月日。
     */
    private static String tostring(Date date) {
        // 获取Date对应的Calendar
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        long millis = cal.getTimeInMillis();

        return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+"\t"+millis+"(ms)";
    }
}