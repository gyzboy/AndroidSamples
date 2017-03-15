package com.gyz.androidopensamples.utilCode;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StatFs;
import android.provider.Settings;

import java.io.File;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import static android.content.pm.ApplicationInfo.FLAG_EXTERNAL_STORAGE;


public class DeviceUtils {

    private DeviceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断设备是否root
     *
     * @return the boolean{@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/",
                "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取设备系统版本号
     *
     * @return 设备系统版本号
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 获取设备AndroidID
     *
     * @return AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID() {
        return Settings.Secure.getString(Utils.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    public static String getMacAddress() {
        String macAddress = getMacAddressByWifiInfo();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByNetworkInterface();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByFile();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        return "please open wifi";
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @return MAC地址
     */
    @SuppressLint("HardwareIds")
    private static String getMacAddressByWifiInfo() {
        try {
            WifiManager wifi = (WifiManager) Utils.getContext().getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) return info.getMacAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    private static String getMacAddressByNetworkInterface() {
        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nis) {
                if (!ni.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02x:", b));
                    }
                    return res1.deleteCharAt(res1.length() - 1).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备MAC地址
     *
     * @return MAC地址
     */
    private static String getMacAddressByFile() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("getprop wifi.interface", false);
        if (result.result == 0) {
            String name = result.successMsg;
            if (name != null) {
                result = ShellUtils.execCmd("cat /sys/class/net/" + name + "/address", false);
                if (result.result == 0) {
                    if (result.successMsg != null) {
                        return result.successMsg;
                    }
                }
            }
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备厂商
     * <p>如Xiaomi</p>
     *
     * @return 设备厂商
     */

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号
     * <p>如MI2SC</p>
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * 关机
     * <p>需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     */
    public static void shutdown() {
        ShellUtils.execCmd("reboot -p", true);
        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utils.getContext().startActivity(intent);
    }

    /**
     * 重启
     * <p>需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     *
     */
    public static void reboot() {
        ShellUtils.execCmd("reboot", true);
        Intent intent = new Intent(Intent.ACTION_REBOOT);
        intent.putExtra("nowait", 1);
        intent.putExtra("interval", 1);
        intent.putExtra("window", 0);
        Utils.getContext().sendBroadcast(intent);
    }

    /**
     * 重启
     * <p>需系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
     *
     * @param reason  传递给内核来请求特殊的引导模式，如"recovery"
     */
    public static void reboot(String reason) {
        PowerManager mPowerManager = (PowerManager) Utils.getContext().getSystemService(Context.POWER_SERVICE);
        try {
            mPowerManager.reboot(reason);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重启到recovery
     * <p>需要root权限</p>
     */
    public static void reboot2Recovery() {
        ShellUtils.execCmd("reboot recovery", true);
    }

    /**
     * 重启到bootloader
     * <p>需要root权限</p>
     */
    public static void reboot2Bootloader() {
        ShellUtils.execCmd("reboot bootloader", true);
    }

    /**
     * 获取手机内存使用状态
     *
     * @param context
     * @return
     */
    public static String getMemInfo(Context context) {
        try {
            ActivityManager e = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            Integer threshold = null;
            if (null != e) {
                e.getMemoryInfo(memoryInfo);
                threshold = Integer.valueOf((int) (memoryInfo.threshold >> 10));
            }

            return "JavaTotal:" + Runtime.getRuntime().totalMemory() + " JavaFree:" + Runtime.getRuntime().freeMemory() + " NativeHeap:" + Debug.getNativeHeapSize() + " NativeAllocated:" + Debug.getNativeHeapAllocatedSize() + " NativeFree:" + Debug.getNativeHeapFreeSize() + " threshold:" + (null != threshold ? threshold + " KB" : "not valid");
        } catch (Exception var4) {
            return "";
        }
    }

    /**
     * 获取手机存储空间大小
     *
     * @param context
     * @return
     */
    public static String getStorageInfo(Context context) {
        StringBuilder stringBuffer = new StringBuilder();
        boolean hasSDCard = false;

        try {
            if("mounted".equals(Environment.getExternalStorageState())) {
                hasSDCard = true;
            }
        } catch (Exception var11) {
        }

        boolean installSDCard = false;

        try {
            ApplicationInfo e = context.getApplicationInfo();
            if((e.flags & FLAG_EXTERNAL_STORAGE) != 0) {
                installSDCard = true;
            }
        } catch (Exception var10) {
        }

        stringBuffer.append("hasSDCard: " + hasSDCard + "\n");
        stringBuffer.append("installSDCard: " + installSDCard + "\n");

        try {
            File e1 = Environment.getRootDirectory();
            if(null != e1) {
                long[] dataDir = getSizes(e1.getAbsolutePath());
                stringBuffer.append("RootDirectory: " + e1.getAbsolutePath() + " ");
                stringBuffer.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", new Object[]{Long.valueOf(dataDir[0]), Long.valueOf(dataDir[1]), Long.valueOf(dataDir[2])}));
            }

            File dataDir1 = Environment.getDataDirectory();
            if(null != dataDir1) {
                long[] externalStorageDir = getSizes(dataDir1.getAbsolutePath());
                stringBuffer.append("DataDirectory: " + dataDir1.getAbsolutePath() + " ");
                stringBuffer.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", new Object[]{Long.valueOf(externalStorageDir[0]), Long.valueOf(externalStorageDir[1]), Long.valueOf(externalStorageDir[2])}));
            }

            File externalStorageDir1 = Environment.getExternalStorageDirectory();
            if(null != dataDir1) {
                stringBuffer.append("ExternalStorageDirectory: " + externalStorageDir1.getAbsolutePath() + " ");
                long[] downloadCacheDir = getSizes(externalStorageDir1.getAbsolutePath());
                stringBuffer.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", new Object[]{Long.valueOf(downloadCacheDir[0]), Long.valueOf(downloadCacheDir[1]), Long.valueOf(downloadCacheDir[2])}));
            }

            File downloadCacheDir1 = Environment.getDownloadCacheDirectory();
            if(null != downloadCacheDir1) {
                stringBuffer.append("DownloadCacheDirectory: " + downloadCacheDir1.getAbsolutePath() + " ");
                long[] sizes = getSizes(downloadCacheDir1.getAbsolutePath());
                stringBuffer.append(String.format("TotalSize: %s FreeSize: %s AvailableSize: %s \n", new Object[]{Long.valueOf(sizes[0]), Long.valueOf(sizes[1]), Long.valueOf(sizes[2])}));
            }
        } catch (Exception var9) {
        }

        return stringBuffer.toString();
    }

    private static long[] getSizes(String path) {
        long[] sizes = new long[]{-1L, -1L, -1L};

        try {
            StatFs e = new StatFs(path);
            long blockSize = 0L;
            long blockCount = 0L;
            long freeBlocks = 0L;
            long availableBlocks = 0L;
            if(Build.VERSION.SDK_INT < 18) {
                blockSize = (long)e.getBlockSize();
                blockCount = (long)e.getBlockCount();
                freeBlocks = (long)e.getFreeBlocks();
                availableBlocks = (long)e.getAvailableBlocks();
            } else {
                blockSize = e.getBlockSizeLong();
                blockCount = e.getBlockCountLong();
                freeBlocks = e.getFreeBlocksLong();
                availableBlocks = e.getAvailableBlocksLong();
            }

            sizes[0] = blockSize * blockCount;
            sizes[1] = blockSize * freeBlocks;
            sizes[2] = blockSize * availableBlocks;
        } catch (Exception var11) {
        }

        return sizes;
    }
}
