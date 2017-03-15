package com.gyz.androidopensamples.utilCode;

/**
 * Created by guoyizhe on 2017/3/15.
 * 邮箱:gyzboy@126.com
 */

public class ThreadUtils {

    /**
     * 获取线程信息
     *
     * @param thread
     * @return
     */
    public static String getThreadInfo(Thread thread){
        StringBuilder sb = new StringBuilder();

        try {
            sb.append(String.format("Thread Name: \'%s\'\n", new Object[]{thread.getName()}));
            sb.append(String.format("\"%s\" prio=%d tid=%d %s\n", new Object[]{thread.getName(), Integer.valueOf(thread.getPriority()), Long.valueOf(thread.getId()), thread.getState()}));
            StackTraceElement[] e = thread.getStackTrace();
            StackTraceElement[] arr$ = e;
            int len$ = e.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                StackTraceElement stackTraceElement = arr$[i$];
                sb.append(String.format("\tat %s\n", new Object[]{stackTraceElement.toString()}));
            }
        } catch (Exception var7) {

        }

        return sb.toString();
    }
}
