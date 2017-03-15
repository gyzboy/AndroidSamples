package com.gyz.androidopensamples.crashconsume;

import android.content.Context;

import com.gyz.androidopensamples.utilCode.AppUtils;
import com.gyz.androidopensamples.utilCode.FileUtils;
import com.gyz.androidopensamples.utilCode.StringUtils;
import com.gyz.androidopensamples.utilCode.SystemPropertiesUtils;
import com.taobao.weex.devtools.common.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guoyizhe on 2017/3/15.
 * 邮箱:gyzboy@126.com
 */

public class ANRCatcher {

    File mSystemTraceFile;
    String mSystemTraceFilePath = "/data/anr/traces.txt";
    File mProcessANRFlagFile;
    volatile boolean enable = false;
    volatile boolean canScan = false;
    AtomicBoolean scaning = new AtomicBoolean(false);
    Context mContext ;

    public ANRCatcher(Context context) {
        this.mSystemTraceFile = new File(this.mSystemTraceFilePath);
        if(!this.mSystemTraceFile.exists()) {
            String propSystemTraceFilePath = SystemPropertiesUtils.get("dalvik.vm.stack-trace-file");
            if(!this.mSystemTraceFile.equals(propSystemTraceFilePath)) {
                try {
                    this.mSystemTraceFile = new File(propSystemTraceFilePath);
                    this.mSystemTraceFilePath = propSystemTraceFilePath;
                } catch (Exception var4) {
                    LogUtil.e("system traces file error", var4);
                }
            }
        }
        this.mContext = context;

        if(null != this.mSystemTraceFile) {
            if(FileUtils.writeFileFromString(this.mProcessANRFlagFile, (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(System.currentTimeMillis())),false)) {
                this.canScan = true;
            }
        }

    }

    public void doScan() {
        long start = System.currentTimeMillis();
        if(this.canScan && this.scaning.compareAndSet(false, true)) {
            try {
                final TracesFinder end = new TracesFinder(this.mSystemTraceFile);
                end.find();
                if(end.found) {
                    Thread thread = new Thread("CrashReportANRCatch") {
                        @Override
                        public void run() {
                            try {
                                //上传
                            } catch (Exception var3) {
                                LogUtil.e("send anr report", var3);
                            }

                        }
                    };
                    thread.setDaemon(true);
                    thread.start();
                }
            } catch (Exception var5) {
                LogUtil.e("do scan traces file", var5);
            }
        }

        long end1 = System.currentTimeMillis();
        LogUtil.d("scaning anr complete elapsed time:" + (end1 - start) + ".ms");
    }

    public class TracesFinder {
        String strStartFlag = "";
        String strEndFlag = "";
        String strPid = "";
        String strTriggerTime = "";
        String strProcessName = "";
        boolean found = false;
        File mSystemTraceFile;

        public TracesFinder(File systemTraceFile) {
            this.mSystemTraceFile = systemTraceFile;
        }

        public void find() {
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.mSystemTraceFile)));

                String e;
                while(null != (e = reader.readLine()) && StringUtils.isBlank(e)) {
                    ;
                }

                if(null != e) {
                    String line2 = reader.readLine();
                    if(null == line2) {
                        return;
                    }
                    Pattern triggerTimePattern = Pattern.compile("-----\\spid\\s(\\d+?)\\sat\\s(.+?)\\s-----");
                    Matcher matcher = triggerTimePattern.matcher(e);
                    if(matcher.find()) {
                        this.strPid = matcher.group(1);
                        this.strTriggerTime = matcher.group(2);
                        Pattern processPattern = Pattern.compile("Cmd\\sline:\\s(.+)");
                        matcher = processPattern.matcher(line2);
                        if(matcher.find()) {
                            this.strProcessName = matcher.group(1);
                            if(this.strProcessName.equals(AppUtils.getAppPackageName(mContext))) {
                                String strLastTriggerTime = FileUtils.readLines(ANRCatcher.this.mProcessANRFlagFile,1).get(0);
                                if(!StringUtils.isBlank(strLastTriggerTime)) {
                                    try {
                                        SimpleDateFormat e1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Date lastTriggerTime = e1.parse(strLastTriggerTime);
                                        Date triggerTime = e1.parse(this.strTriggerTime);
                                        if(triggerTime.getTime() > lastTriggerTime.getTime() && FileUtils.writeFileFromString(ANRCatcher.this.mProcessANRFlagFile, this.strTriggerTime,false)) {
                                            this.strStartFlag = e;
                                            this.strEndFlag = String.format("----- end %s -----", new Object[]{this.strPid});
                                            this.found = true;
                                            return;
                                        }
                                    } catch (Exception var22) {
                                        LogUtil.e("compare triggerTime", var22);
                                    }

                                    return;
                                }
                            }

                            return;
                        }
                    }

                    return;
                }
            } catch (IOException var23) {
                LogUtil.e("do scan traces file", var23);
                return;
            } finally {
                if(null != reader) {
                    try {
                        reader.close();
                    } catch (IOException var21) {
                        LogUtil.e("close traces file", var21);
                    }
                }

            }

        }
    }
}
