package com.gyz.androidsamples.view.choreographer;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.Choreographer;
import com.gyz.androidopensamples.timber.Timber;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by guangcheng.fan on 2016/10/10.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class FrameSkipMonitor implements Choreographer.FrameCallback {
    protected final String TAG = "FrameSkipMonitor";
    private static final long ONE_FRAME_TIME = 16600000; // 1 Frame time cost
    private static final long MIN_FRAME_TIME = ONE_FRAME_TIME * 3; // 3 Frame time cost
    private static final long MAX_FRAME_TIME = 60 * ONE_FRAME_TIME;
        // 60 Frame time cost, not record some special cases.

    private static final String SKIP_EVENT_NAME = "frame_skip";

    private static FrameSkipMonitor sInstance;

    private long mLastFrameNanoTime = 0;
    private HashMap<String, Long> mSkipRecordMap;
    private HashMap<String, Long> mActivityShowTimeMap;
    private String mActivityName;
    private long mActivityStartTime = 0;

    private FrameSkipMonitor() {
        mSkipRecordMap = new HashMap<>();
        mActivityShowTimeMap = new HashMap<>();
    }

    public static FrameSkipMonitor getInstance() {
        if (sInstance == null) {
            sInstance = new FrameSkipMonitor();
        }
        return sInstance;
    }

    public void setActivityName(String activityName) {
        mActivityName = activityName;
    }

    public void start() {
        Choreographer.getInstance().postFrameCallback(FrameSkipMonitor.getInstance());
    }

    //当新的一帧被绘制的时候被调用
    @Override
    public void doFrame(long frameTimeNanos) {
        if (mLastFrameNanoTime != 0) {
            long frameInterval = frameTimeNanos - mLastFrameNanoTime;
            //如果时间间隔大于最小时间间隔，3*16ms，小于最大的时间间隔，60*16ms，就认为是掉帧，累加统计该时间
            //正常情况下，两帧的间隔都是在16ms以内 ,如果我们统计到的两帧间隔时间大于三倍的普通绘制时间，我们就认为是出现了卡顿，
            // 之所以设置最大时间间隔，是为了有时候页面不刷新绘制的时候，不做统计处理
            if (frameInterval > MIN_FRAME_TIME && frameInterval < MAX_FRAME_TIME) {
                long time = 0;
                if (mSkipRecordMap.containsKey(mActivityName)) {
                    time = mSkipRecordMap.get(mActivityName);
                }
                mSkipRecordMap.put(mActivityName, time + frameInterval);
            }
        }
        mLastFrameNanoTime = frameTimeNanos;
        Choreographer.getInstance().postFrameCallback(this);
        Runtime.getRuntime().maxMemory();
    }

    public void report() {
        Choreographer.getInstance().removeFrameCallback(this);
        Iterator iter = mSkipRecordMap.entrySet().iterator();
        StringBuilder pages = new StringBuilder();
        StringBuilder skips = new StringBuilder();
        long skipFrames = 0;
        long skipFramesPerSecond = 0;
        long activityShowTime = 0;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            pages.append((String)entry.getKey()).append(",\n");
            skipFrames = ((long)entry.getValue() / ONE_FRAME_TIME);

            if (mActivityShowTimeMap.containsKey(entry.getKey())) {
                activityShowTime = mActivityShowTimeMap.get(entry.getKey());
            }
            skipFramesPerSecond = skipFrames;//activityShowTime <= 0 ? -1 : skipFrames * 1000 / activityShowTime;
            skips.append(Long.toString(skipFramesPerSecond)).append(",\n");
        }
        HashMap map = new HashMap();
        map.put("page", pages.toString());
        map.put("time", skips.toString());
        mSkipRecordMap.clear();
        Log.d(TAG, "page：\n" + pages.toString() + "time：\n" + skips.toString());
    }

    public void OnActivityResume() {
        mActivityStartTime = System.currentTimeMillis();
    }

    public void OnActivityPause() {
        long activityShowInterval = System.currentTimeMillis() - mActivityStartTime;
        long time = 0;
        if (mActivityShowTimeMap.containsKey(mActivityName)) {
            time = mActivityShowTimeMap.get(mActivityName);
        }
        mActivityShowTimeMap.put(mActivityName, time + activityShowInterval);
    }
}
