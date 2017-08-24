package com.gyz.androidsamples.view.spannablestring;

/**
 * Created by guoyizhe on 2017/3/20.
 * 邮箱:gyzboy@126.com
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.gyz.androidsamples.R;

/**
 * SpannaleString是对文字进行复合文本设置
 *
 * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE，这是在 setSpan 时需要指定的
 * flag，它的意义我试了很久也没试出来，睡个觉，今天早上才突然有点想法，试之，果然。它是用来标识在 Span
 * 范围内的文本前后输入新的字符时是否把它们也应用这个效果。分别有
 * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)、Spanned
 * .SPAN_INCLUSIVE_EXCLUSIVE(前面包括
 * ，后面不包括)、Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括
 * )、Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)
 *
 * @author gyz
 *
 */
public class ASSpannableString extends Activity {
    private WebView wb_show;
    private String used_mem = "50";
    private String Free_mem = "120";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_test);
        TextView tv_show = (TextView) findViewById(R.id.tv_show);
        wb_show = (WebView) findViewById(R.id.wb_show);
        wb_show.setWebViewClient(new webViewClient());// 处理webview
		/*
		 * 使用SpannableString设置TextView文本的前景、背景色
		 */
        // SpannableString spanText = new
        // SpannableString("This is GYZ！！！LOL！！");
        // spanText.setSpan(new BackgroundColorSpan(Color.GREEN), 0,
        // spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//设置背景色
        // spanText.setSpan(new ForegroundColorSpan(Color.BLUE), 6,
        // spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//设置前景色
        // tv_show.append("\n");
        // tv_show.append(spanText);
		/*
		 * 无下划线链接
		 */
        // SpannableString spStr = new SpannableString("This is GYZ！！！LOL！！");
        // NoLineClickSpan clickSpan = new
        // NoLineClickSpan("http://www.baidu.com"); // 设置超链接
        // spStr.setSpan(clickSpan, 0, spStr.length(),
        // Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        // tv_show.append(spStr);
        // tv_show.setMovementMethod(LinkMovementMethod.getInstance());//
        // 设置超链接为可点击状态，可触发事件
        /**
         * 浮雕、模糊效果
         */
        // SpannableString spanText = new
        // SpannableString("MaskFilterSpan,gyzboy,LOL");
        // int length = spanText.length();
        // //模糊(BlurMaskFilter)
        // MaskFilterSpan maskFilterSpan = new MaskFilterSpan(new
        // BlurMaskFilter(3, Blur.OUTER));
        // spanText.setSpan(maskFilterSpan, 0, length/2, Spannable.
        // SPAN_INCLUSIVE_EXCLUSIVE);
        // //浮雕(EmbossMaskFilter)
        // maskFilterSpan = new MaskFilterSpan(new EmbossMaskFilter(new
        // float[]{1,1,3}, 1.5f, 18, 13));
        // spanText.setSpan(maskFilterSpan, length/2, length,
        // Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        // tv_show.append("\n");
        // tv_show.append(spanText);
        /**
         * 光栅效果
         */
        // SpannableString spanText = new SpannableString("StrikethroughSpan");
        // spanText.setSpan(new StrikethroughSpan(), 0, 7, Spannable.
        // SPAN_INCLUSIVE_EXCLUSIVE);
        // tv_show.append("\n");
        // tv_show.append(spanText);
        /**
         * 中划线、下划线
         */
        // SpannableString spanText = new SpannableString("StrikethroughSpan");
        // spanText.setSpan(new StrikethroughSpan(), 0, spanText.length(),
        // Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//中划线
        // spanText.setSpan(new UnderlineSpan(), 0, spanText.length(),
        // Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//下划线
        // tv_show.append("\n");
        // tv_show.append(spanText);

        /**
         * DynamicDrawableSpan 设置图片，基于文本基线或底部对齐
         */
        // DynamicDrawableSpan drawableSpan = new DynamicDrawableSpan(
        // DynamicDrawableSpan.ALIGN_BASELINE) {
        // @Override
        // public Drawable getDrawable() {
        // Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
        // d.setBounds(0, 0, 50, 50);
        // return d;
        // }
        // };
        // DynamicDrawableSpan drawableSpan2 = new DynamicDrawableSpan(
        // DynamicDrawableSpan.ALIGN_BOTTOM) {
        // @Override
        // public Drawable getDrawable() {
        // Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
        // d.setBounds(0, 0, 50, 50);
        // return d;
        // }
        // };
        // SpannableString spanText = new
        // SpannableString("DynamicDrawableSpan ");
        // spanText.setSpan(drawableSpan, 3, 4,
        // Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        // spanText.setSpan(drawableSpan2, 7, 8,
        // Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        // tv_show.append("\n");
        // tv_show.append(spanText);
        /**
         * ImageSpan 图片,跟DynamicDrawable区别不大
         */
        // SpannableString spanText = new SpannableString("ImageSpan");
        // Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
        // d.setBounds(0, 0, 50, 50);
        // spanText.setSpan(new ImageSpan(d), 3, 4,
        // Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        // tv_show.append("\n");
        // tv_show.append(spanText);
        /**
         * RelativeSizeSpan 相对大小（文本字体）,相对大小的比例是基于当前文本字体大小
         */
        // SpannableString spanText = new SpannableString("RelativeSizeSpan");
        // //参数proportion:比例大小
        // spanText.setSpan(new RelativeSizeSpan(2.5f), 3, 4,
        // Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        // tv_show.append("\n");
        // tv_show.append(spanText);

        /**
         * ScaleXSpan 基于x轴缩放
         */
        // SpannableString spanText = new SpannableString("ScaleXSpan");
        // //参数proportion:比例大小
        // spanText.setSpan(new ScaleXSpan(3.8f), 3, 7,
        // Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        // tv_show.append("\n");
        // tv_show.append(spanText);
        /**
         * StyleSpan 字体样式：粗体、斜体等
         */
        // SpannableString spanText = new SpannableString("StyleSpan");
        // //Typeface.BOLD_ITALIC:粗体+斜体
        // spanText.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 3, 7,
        // Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        // tv_show.append("\n");
        // tv_show.append(spanText);
        /**
         * 数学公式上下标
         */
        // SpannableString spanText = new SpannableString("SubscriptSpan");
        // spanText.setSpan(new SubscriptSpan(), 6, 7,
        // Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//上标
        // spanText.setSpan(new SuperscriptSpan(), 6, 7,
        // Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//下标
        // tv_show.append("\n");
        // tv_show.append(spanText);
        /**
         * TextAppearanceSpan 文本外貌（包括字体、大小、样式和颜色）
         */
//		SpannableString spanText = new SpannableString("TextAppearanceSpan");
//		// 若需自定义TextAppearance，可以在系统样式上进行修改
//		spanText.setSpan(new TextAppearanceSpan(this,
//				android.R.style.TextAppearance_Medium), 6, 7,
//				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		tv_show.append("\n");
//		tv_show.append(spanText);
        /**
         * TypefaceSpan 文本字体
         */
//		SpannableString spanText = new SpannableString("TypefaceSpan");
//		//若需使用自定义字体，可能要重写类TypefaceSpan
//		spanText.setSpan(new TypefaceSpan("monospace"), 3, 10, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//		tv_show.append("\n");
//		tv_show.append(spanText);
        /**
         * URLSpan 文本超链接
         */
        SpannableString spanText = new SpannableString("URLSpan -- gyz的博客");
        spanText.setSpan(new URLSpan("http://www.baidu..com"), 10, spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_show.append("\n");
        tv_show.append(spanText);
        //让URLSpan可以点击
        tv_show.setMovementMethod(new LinkMovementMethod());

        String str = "已使用 " + used_mem + "，可用空间 " + Free_mem;
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#FF0000"));
        ForegroundColorSpan blueSpan1 = new ForegroundColorSpan(Color.parseColor("#2692FF"));
        ForegroundColorSpan blueSpan2 = new ForegroundColorSpan(Color.parseColor("#2692FF"));
        //同一行str中用到两个部分有相同颜色需要设置两个span对象进行设置 bluespan1、bluespan2

        style.setSpan(blueSpan1, str.indexOf(used_mem),
            str.indexOf(used_mem) + used_mem.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(redSpan, str.indexOf(Free_mem),
                str.indexOf(Free_mem) + Free_mem.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_show.setText(style);

    }

    // 无下划线超链接，使用textColorLink、textColorHighlight分别修改超链接前景色和按下时的颜色
    private class NoLineClickSpan extends ClickableSpan {
        String text;

        public NoLineClickSpan(String text) {
            super();
            this.text = text;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false); // 去掉下划线
        }

        @Override
        public void onClick(View widget) {
            wb_show.loadUrl(text);
        }
    }

    class webViewClient extends WebViewClient {
        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            // 如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }
    }
}
