package com.gyz.androidsamples.intent;

/**
 * Created by guoyizhe on 16/6/15.
 * 邮箱:gyzboy@126.com
 */
public class ASPendingIntent {
//PendingIntent 对象是 Intent 对象的包装器。PendingIntent 的主要目的是授权外部应用使用包含的 Intent，就像是它从您应用本身的进程中执行的一样。

//待定 Intent 的主要用例包括：
//声明用户使用您的通知执行操作时所要执行的 Intent（Android 系统的 NotificationManager 执行 Intent）。
//声明用户使用您的 应用小工具执行操作时要执行的 Intent（主屏幕应用执行 Intent）。
//声明未来某一特定时间要执行的 Intent（Android 系统的 AlarmManager 执行 Intent）。
//由于每个 Intent 对象均设计为由特定类型的应用组件进行处理（Activity、Service 或 BroadcastReceiver），因此还必须基于相同的考虑因素创建 PendingIntent。
//使用待定 Intent 时，应用不会使用调用（如 startActivity()）执行该 Intent。相反，通过调用相应的创建器方法创建 PendingIntent 时，您必须声明所需的组件类型：

//    PendingIntent.getActivity()，适用于启动 Activity 的 Intent。
//    PendingIntent.getService()，适用于启动 Service 的 Intent。
//    PendingIntent.getBroadcast()，适用于启动 BroadcastReceiver 的 Intent。

//除非您的应用正在从其他应用中接收待定 Intent，否则上述用于创建 PendingIntent 的方法可能是您所需的唯一 PendingIntent 方法。
//每种方法均会提取当前的应用 Context、您要包装的 Intent 以及一个或多个指定应如何使用该 Intent 的标志（例如，是否可以多次使用该 Intent）
    //通知、应用小工具用的比较多


//getActivity、getServie、getBroadcast有4个参数，其中第二个和第四个比较重要，第二个为requestCode，用于区分每个intent请求，UUID.randomUUID().hashCode()
//第四个为Flag，常用的有4个：
// FLAG_UPDATE_CURRENT，Intent有更新的时候需要用到这个flag去更新你的描述，否则组件在下次事件发生或时间到达的时候extras永远是第一次Intent的extras。
// FLAG_CANCEL_CURRENT也能做到更新extras，只不过是先把前面的extras清除。
// 另外FLAG_CANCEL_CURRENT和FLAG_UPDATE_CURRENT的区别在于能否新new一个Intent，FLAG_UPDATE_CURRENT能够新new一个Intent，而FLAG_CANCEL_CURRENT则不能，只能使用第一次的Intent。
//另外两flag就比较少用，利用FLAG_ONE_SHOT获取的PendingIntent只能使用一次，再使用PendingIntent也将失败，利用FLAG_NO_CREAT获取的PendingIntent若描述的Intent不存在则返回NULL值.
}
