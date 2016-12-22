package com.gyz.androidsamples.view;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/7/15.
 * 邮箱:gyzboy@126.com
 */
public class ASTextView extends Activity {

//    android:autoLink
//    设置是否当文本为URL链接/email/电话号码/map时，文本显示为可点击的链接。可选值(none/web/email/phone/map/all)
//
//    android:autoText
//    如果设置，将自动执行输入值的拼写纠正。此处无效果，在显示输入法并输入的时候起作用。
//
//    android:bufferType
//    指定getText()方式取得的文本类别。选项editable 类似于StringBuilder可追加字符，
//    也就是说getText后可调用append方法设置文本内容。spannable 则可在给定的字符区域使用样式。
//
//    android:capitalize
//    设置英文字母大写类型。此处无效果，需要弹出输入法才能看得到，参见EditText此属性说明。
//
//    android:cursorVisible
//    设定光标为显示/隐藏，默认显示。
//
//    android:digits
//    设置允许输入哪些字符。如“1234567890.+-*/%\n()”
//
//    android:drawableBottom
//    在text的下方输出一个drawable，如图片。如果指定一个颜色的话会把text的背景设为该颜色，并且同时和background使用时覆盖后者。
//
//    android:drawableLeft
//    在text的左边输出一个drawable，如图片。
//
//    android:drawablePadding
//    设置text与drawable(图片)的间隔，与drawableLeft、drawableRight、drawableTop、drawableBottom一起使用，可设置为负数，单独使用没有效果。
//
//    android:drawableRight
//    在text的右边输出一个drawable，如图片。
//
//    android:drawableTop
//    在text的正上方输出一个drawable，如图片。
//
//    android:editable
//    设置是否可编辑。这里无效果，参见EditView。
//
//    android:editorExtras
//    设置文本的额外的输入数据。在EditView再讨论。
//
//    android:ellipsize
//    设置当文字过长时,该控件该如何显示。有如下值设置：”start”—–省略号显示在开头；”end”——省略号显示在结尾；”middle”—-省略号显示在中间；”marquee” ——以跑马灯的方式显示(动画横向移动)
//
//    android:freezesText
//    设置保存文本的内容以及光标的位置。
//    一：
//    设置了android：id属性就会自动保存。
//    二：
//    设置android:freezesText="true"
//
//    android:gravity
//    设置文本位置，如设置成“center”，文本将居中显示。
//
//    android:hint
//    Text为空时显示的文字提示信息，可通过textColorHint设置提示信息的颜色。此属性在EditView中使用，但是这里也可以用。
//
//    android:imeOptions
//    附加功能，设置右下角IME动作与编辑框相关的动作，如actionDone右下角将显示一个“完成”，而不设置默认是一个回车符号。这个在EditText中再详细说明，此处无用。
//
//    android:imeActionId
//    设置IME动作ID。
//
//    android:imeActionLabel
//    设置IME动作标签。在EditText再做说明。
//
//    android:includeFontPadding
//    设置文本是否包含顶部和底部额外空白，默认为true。
//
//    android:inputMethod
//    为文本指定输入法，需要完全限定名（完整的包名）。例如：com.google.android.inputmethod.pinyin，但是这里报错找不到。
//
//    android:inputType
//    设置文本的类型，用于帮助输入法显示合适的键盘类型。在EditText中再详细说明，这里无效果。
//
//    android:linksClickable
//    设置链接是否点击连接，即使设置了autoLink。
//
//    android:marqueeRepeatLimit
//    在ellipsize指定marquee的情况下，设置重复滚动的次数，当设置为marquee_forever时表示无限次。
//
//    android:ems
//    设置TextView的宽度为N个字符的宽度。这里测试为一个汉字字符宽度，如图：
//
//    android:maxEms
//    设置TextView的宽度为最长为N个字符的宽度。与ems同时使用时覆盖ems选项。
//
//    android:minEms
//    设置TextView的宽度为最短为N个字符的宽度。与ems同时使用时覆盖ems选项。
//
//    android:maxLength
//    限制显示的文本长度，超出部分不显示。
//
//    android:lines
//    设置文本的行数，设置两行就显示两行，即使第二行没有数据。
//
//    android:maxLines
//    设置文本的最大显示行数，与width或者layout_width结合使用，超出部分自动换行，超出行数将不显示。
//
//    android:minLines
//    设置文本的最小行数，与lines类似。
//
//    android:lineSpacingExtra
//    设置行间距。
//
//    android:lineSpacingMultiplier
//    设置行间距的倍数。如”1.2”
//
//    android:numeric
//    如果被设置，该TextView有一个数字输入法。此处无用，设置后唯一效果是TextView有点击效果，此属性在EditText将详细说明。
//
//    android:password
//    以小点”.”显示文本
//
//    android:phoneNumber
//    设置为电话号码的输入方式。
//
//    android:privateImeOptions
//    设置输入法选项，此处无用，在EditText将进一步讨论。
//
//    android:scrollHorizontally
//    设置文本超出TextView的宽度的情况下，是否出现横拉条。
//
//    android:selectAllOnFocus
//    如果文本是可选择的，让他获取焦点而不是将光标移动为文本的开始位置或者末尾位置。EditText中设置后无效果。
//
//    android:shadowColor
//    指定文本阴影的颜色，需要与shadowRadius一起使用。
//
//    android:shadowDx
//    设置阴影横向坐标开始位置。
//
//    android:shadowDy
//    设置阴影纵向坐标开始位置。
//
//    android:shadowRadius
//    设置阴影的半径。设置为0.1就变成字体的颜色了，一般设置为3.0的效果比较好。
//
//    android:singleLine
//    设置单行显示。如果和layout_width一起使用，当文本不能全部显示时，后面用“…”来表示。如android:text="test_ singleLine " android:singleLine="true" android:layout_width="20dp"将只显示“t…”。如果不设置singleLine或者设置为false，文本将自动换行
//
//    android:text
//    设置显示文本.
//
//    android:textAppearance
//    设置文字外观。如“?android:attr/textAppearanceLargeInverse
//    ”这里引用的是系统自带的一个外观，？表示系统是否有这种外观，否则使用默认的外观。可设置的值如下：textAppearanceButton/textAppearanceInverse/textAppearanceLarge/textAppearanceLargeInverse/textAppearanceMedium/textAppearanceMediumInverse/textAppearanceSmall/textAppearanceSmallInverse
//
//    android:textColor
//    设置文本颜色
//
//    android:textColorHighlight
//    被选中文字的底色，默认为蓝色
//
//    android:textColorHint
//    设置提示信息文字的颜色，默认为灰色。与hint一起使用。
//
//    android:textColorLink
//    文字链接的颜色.
//
//    android:textScaleX
//    设置文字缩放，默认为1.0f。
//
//    android:textSize
//    设置文字大小，推荐度量单位”sp”，如”15sp”
//
//    android:textStyle
//    设置字形[bold(粗体) 0, italic(斜体) 1, bolditalic(又粗又斜) 2] 可以设置一个或多个，用“|”隔开
//
//    android:typeface
//    设置文本字体，必须是以下常量值之一：normal 0, sans 1, serif 2, monospace(等宽字体) 3]
//
//    android:height
//    设置文本区域的高度，支持度量单位：px(像素)/dp/sp/in/mm(毫米)
//
//    android:maxHeight
//    设置文本区域的最大高度
//
//    android:minHeight
//    设置文本区域的最小高度
//
//    android:width
//    设置文本区域的宽度，支持度量单位：px(像素)/dp/sp/in/mm(毫米)，与layout_width的区别看这里。
//
//    android:maxWidth
//    设置文本区域的最大宽度
//
//    android:minWidth
//    设置文本区域的最小宽度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TextView textView = new TextView(this);
        setContentView(textView);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) textView.getLayoutParams();
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = 50;
        textView.setLayoutParams(params);
//        textView.setText("33333333\n5555555\n33333333\n");
//        textView.post(new Runnable() {
//            @Override
//            public void run() {
//                getLayoutInfo(textView);
//            }
//        });

        //这里使用的是Xliff格式，
        // %n$ms：代表输出的是字符串，n代表是第几个参数，设置m的值可以在输出之前放置空格
        // %n$md：代表输出的是整数，n代表是第几个参数，设置m的值可以在输出之前放置空格，也可以设为0m,在输出之前放置m个0
        // %n$mf：代表输出的是浮点数，n代表是第几个参数，设置m的值可以控制小数位数，如m=2.2时，输出格式为00.00
        textView.setShadowLayer(2.5f,5.0f,5.0f,R.color.colorPrimary);
        textView.getPaint().setFakeBoldText(true);
        Typeface mTypeFace = Typeface.createFromAsset(getAssets(),"fzltxh.ttf");
        textView.setTypeface(mTypeFace);
        textView.setText(getString(R.string.text_test,5,"gyzboy",1.2));

        //ImageGetter的方法，可轻松实现图文混排
        String imgStr = "<b>sdfa</b><br><img src=\"" + R.mipmap.ic_launcher + "\"/>";
        Html.ImageGetter imageGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                int id = Integer.parseInt(source);
                Drawable draw = getResources().getDrawable(id);
                draw.setBounds(0, 0, 300, 200);
                return draw;
            }
        };
        textView.append(Html.fromHtml(imgStr, imageGetter, null));

        //这里是基于工作中遇到的一个问题，在适配的时候发现，textview的setTextSize和其他的控件在代码中设置数值效果不一致，比如设置
        //topmargin = 30表示30的像素，一般我会使用dp2px方法进行适配，然后在setTextSize的时候，从dimen文件中读取的dp值设置进去以后
        //发现过大，研究源码发现原来setTextSize默认的TypedValue是sp，而getDimenSion系列的方法返回的都是px值，比如在dimen文件中
        //设置字体大小为10dp，在720的手机上getDimen返回的大概就是30px,挂在sp的typeValue上30的数值就是差不多90px，所以就会出现字体大的情况
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.dimen_test));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,getResources().getDimensionPixelSize(R.dimen.dimen_test));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimensionPixelSize(R.dimen.dimen_test));

    }

    /**
     * 获得textview绘制布局相关的信息
     *
     * @param text
     */
    private void getLayoutInfo(TextView text) {
        Layout layout = text.getLayout();
        System.out.println("width---->" + layout.getWidth());
        System.out.println("height---->" + layout.getHeight());
        System.out.println("aligment--->" + layout.getAlignment());
        System.out.println("linecount--->" + layout.getLineCount());
        System.out.println("------------------------------");
        System.out.println("line0 width---->" + layout.getLineWidth(0));
        System.out.println("line0 start---->" + layout.getLineStart(0));
        System.out.println("line0 end---->" + layout.getLineEnd(0));
        System.out.println("line0 top---->" + layout.getLineTop(0));
        System.out.println("line0 bottom---->" + layout.getLineBottom(0));
        System.out.println("line0 left---->" + layout.getLineLeft(0));
        System.out.println("line0 right---->" + layout.getLineRight(0));
        System.out.println("line0 bound---->" + layout.getLineBounds(0, null));
        System.out.println("baseline0---->" + layout.getLineBaseline(0));
        System.out.println("------------------------------");
        System.out.println("line1 width---->" + layout.getLineWidth(1));
        System.out.println("line1 start---->" + layout.getLineStart(1));
        System.out.println("line1 end---->" + layout.getLineEnd(1));
        System.out.println("line1 top---->" + layout.getLineTop(1));
        System.out.println("line1 bottom---->" + layout.getLineBottom(1));
        System.out.println("line1 left---->" + layout.getLineLeft(1));
        System.out.println("line1 right---->" + layout.getLineRight(1));
        System.out.println("line1 bound---->" + layout.getLineBounds(1, null));
        System.out.println("baseline1---->" + layout.getLineBaseline(1));
        System.out.println("------------------------------");
        System.out.println("line2 width---->" + layout.getLineWidth(2));
        System.out.println("line2 start---->" + layout.getLineStart(2));
        System.out.println("line2 end---->" + layout.getLineEnd(2));
        System.out.println("line2 top---->" + layout.getLineTop(2));
        System.out.println("line2 bottom---->" + layout.getLineBottom(2));
        System.out.println("line2 left---->" + layout.getLineLeft(2));
        System.out.println("line2 right---->" + layout.getLineRight(2));
        System.out.println("line2 bound---->" + layout.getLineBounds(2, null));
        System.out.println("baseline2---->" + layout.getLineBaseline(2));
        System.out.println("------------------------------");
        System.out.println("line0descent--->" + layout.getLineDescent(0));
        System.out.println("line1descent--->" + layout.getLineDescent(1));
        System.out.println("line2descent--->" + layout.getLineDescent(2));
        System.out.println("------------------------------");
        System.out.println("verticalFor0--->" + layout.getLineForVertical(layout.getLineTop(0)));
        System.out.println("verticalFor1--->" + layout.getLineForVertical(layout.getLineTop(1)));
        System.out.println("verticalFor2--->" + layout.getLineForVertical(layout.getLineTop(2)));
        System.out.println("------------------------------");
        System.out.println("line0FromOffset--->" + layout.getLineForOffset(29));
        System.out.println("line1FromOffset--->" + layout.getLineForOffset(59));
        System.out.println("line2FromOffset--->" + layout.getLineForOffset(72));
        System.out.println("line2FromOffset--->" + layout.getLineForOffset(73));
        System.out.println("------------------------------");
        System.out.println(layout.getLineVisibleEnd(2));
    }
}
