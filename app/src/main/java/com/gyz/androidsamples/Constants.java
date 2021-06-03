package com.gyz.androidsamples;

public class Constants {
    public static final String TOUCH_TAG = "GYZ_TOUCH";

    public static String actionToStr(int index){
        String ret = "DOWN";
        switch (index) {
            case 0:
                ret = "DOWN";
                break;
            case 1:
                ret = "UP";
                break;
            case 2:
                ret = "MOVE";
                break;
            case 3:
                ret = "CANCEL";
                break;
        }
        return ret;
    }
}
