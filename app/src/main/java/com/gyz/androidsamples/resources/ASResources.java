package com.gyz.androidsamples.resources;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.gyz.androidsamples.R;

import java.text.Format;

/**
 * Created by guoyizhe on 16/8/24.
 * 邮箱:gyzboy@126.com
 */
public class ASResources extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();

//        res.getSystem();//只访问系统资源

//        getDimension返回float，
//        getDimensionPixelOffset返回int
//        getDimensionPixelSize则不管写的是dp还是sp还是px,都会乘以denstiy.
        TextView tv = new TextView(this);

        float a1=getResources().getDimension(R.dimen.dp_01);
        int a2=getResources().getDimensionPixelOffset(R.dimen.dp_01);
        int a3=getResources().getDimensionPixelSize(R.dimen.dp_01);

        float b1=getResources().getDimension(R.dimen.px_01);
        int b2=getResources().getDimensionPixelOffset(R.dimen.px_01);
        int b3=getResources().getDimensionPixelSize(R.dimen.px_01);

        float c1=getResources().getDimension(R.dimen.sp_01);
        int c2=getResources().getDimensionPixelOffset(R.dimen.sp_01);
        int c3=getResources().getDimensionPixelSize(R.dimen.sp_01);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        tv.append("density is" + dm.density);
        tv.append("\ngetDimension= "+a1+", getDimensionPixelOffset="+a2+",getDimensionPixelSize="+a3);
        tv.append("\ngetDimension= "+b1+", getDimensionPixelOffset="+b2+",getDimensionPixelSize="+b3);
        tv.append("\ngetDimension= "+c1+", getDimensionPixelOffset="+c2+",getDimensionPixelSize="+c3);

        setContentView(tv);
        /*
        animator/	存放属性动画的XML文件
        anim/	存放补间动画的XML文件
        color/	存放颜色状态变化的XML文件，selector等
        drawable/
            Bitmap files
            Nine-Patches (re-sizable bitmaps)
            State lists
            Shapes
            Animation drawables
            Other drawables

        mipmap/	适用于不同启动器图标密度的 Drawable 文件

        layout/	存放布局XML文件
        sw320dp  最小宽度为320dp
        w720dp    可用的最小宽度720dp
        h720dp     可用最小高度720dp

        menu/	存放菜单类文件, 比如Options Menu, Context Menu, or Sub Menu.
        raw/	(原始流) 要以原始形式保存的任意文件。要使用原始 InputStream 打开这些资源，请使用资源 ID（即 R.raw.filename）调用 Resources.openRawResource()。
        但是，如需访问原始文件名和文件层次结构，则可以考虑将某些资源保存在 assets/ 目录下（而不是 res/raw/）。assets/ 中的文件没有资源 ID，因此您只能使用 AssetManager 读取这些文件。

        values/ 包含字符串、整型数和颜色等简单值的 XML 文件。
        其他 res/ 子目录中的 XML 资源文件是根据 XML 文件名定义单个资源，而目录中的 values/ 文件可描述多个资源。对于此目录中的文件，<resources> 元素的每个子元素均定义一个资源。例如，<string> 元素创建 R.string 资源，<color> 元素创建 R.color 资源。
            arrays.xml，用于资源数组（类型化数组）。
            colors.xml：颜色值。
            dimens.xml：尺寸值。
            strings.xml：字符串值。
            styles.xml：样式。

        xml/	可以在运行时通过调用 Resources.getXML() 读取的任意 XML 文件。各种 XML 配置文件（如可搜索配置）都必须保存在此处。

        */

        //寻找资源匹配的步骤:
//        1.硬件设置 locale地域
//        2.MCC(移动信号国家码)->MNC(移动网络号码)->language->..
    }
}
