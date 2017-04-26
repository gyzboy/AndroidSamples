package com.gyz.androidsamples.view.drawtools;

import android.graphics.Color;

/**
 * Created by gyzboy on 2017/4/26.
 */

public class ASColorUtil{

    /**
     * string类型的color值转换为带透明度的argb色值
     * @param stringColor
     * @param alpha
     * @return
     */
    public static int stringColorToArgb(String stringColor,int alpha){
        int color= Color.parseColor(stringColor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha,red,green,blue);
    }
}
