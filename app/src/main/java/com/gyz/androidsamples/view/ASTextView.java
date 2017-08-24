package com.gyz.androidsamples.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.NumberKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/7/15.
 * 邮箱:gyzboy@126.com
 */
public class ASTextView extends Activity implements TextView.OnEditorActionListener {

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
        textView.setText(Html.fromHtml("12312" + String.format("<font color=\"#FF0000\">%s", "234")));

        //        forTestText(textView);
//        setContentView(R.layout.activity_text_2); //for testTextAttrs()
//        final EditText search = (EditText) findViewById(R.id.imeOption1);
//        search.setOnEditorActionListener(this);
//        EditText go = (EditText) findViewById(R.id.imeOption2);
//        go.setOnEditorActionListener(this);
//        TextView text  = (TextView) findViewById(R.id.text);
//        search.setEnabled(false);//点击后没反应
//        TextView passwd = (TextView) findViewById(R.id.passwd);
//        search.setText("2131312312");
//        final TextView text_pic = (TextView) findViewById(R.id.text_pic);
//        testTextAttrs(text,passwd,text_pic,search,go);

    }

    private void forTestText(TextView textView){
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

    public void testTextAttrs(TextView text, TextView passwd, TextView text_pic, final EditText search, EditText go){
        //（1）getText()和getEditableText()这两个方法是定义在android.widget.TextView控件中的，在默认情况下TextView是不可编辑的，getText().toString()能获得控件中的内容，而getEditableText()返回的值为null。
        //（2）android.widget.EditText继承自TextView，它只稍微重写了TextView中的getText()方法，。在EditText中使用getText().toString() 和getEditableText().toString()效果是一样的
        System.out.println(search.getEditableText());//返回作为可编辑对象的text，这里返回的就是21313
        System.out.println(search.getLineHeight());//获得一行的高度
        search.post(new Runnable() {//总是在最后运行

            @Override
            public void run() {
                System.out.println(search.getLayout());//当view正在绘制时返回null，跟view的绘制一样原理
                System.out.println(search.getLineCount());//获得行数，当view绘制完成时返回正确数值
//				System.out.println(text_pic.getCompoundPaddingLeft());
//				System.out.println(text_pic.getTotalPaddingLeft());
            }
        });
        System.out.println(search.getLayout());//返回null
        System.out.println(search.getLineCount());//返回0

        search.setTypeface(Typeface.MONOSPACE,Typeface.BOLD);//设置字体

        go.setKeyListener(new NumberKeyListener() {

            @Override
            public int getInputType() {//软键盘输入类型，这里默认数字键盘
                // TODO Auto-generated method stub
                return android.text.InputType.TYPE_CLASS_PHONE;
            }

            @Override
            protected char[] getAcceptedChars() {//允许输入的字符
                // TODO Auto-generated method stub
                return new char[] { '1', '2', '3', '4', '5', '6', '7', '8','9', '0' };
            }
        });
        text.setText("一二三：www.baidu.com");
        text.setAutoLinkMask(Linkify.WEB_URLS);//两者协同合作,将所有text设置为链接
        text.setLinkTextColor(Color.BLACK);//设置链接字体颜色
        text.setMovementMethod(LinkMovementMethod.getInstance());

        URLSpan[] span = text.getUrls();
        System.out.println(span[0].getURL());//获得所设置的url

        passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//显示密码
        passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());//隐藏密码，相当于passwd=true

        System.out.println(text.getKeyListener());

        System.out.println(text_pic.getCompoundPaddingLeft());
        System.out.println(text_pic.getTotalPaddingLeft());

        Drawable draw = getResources().getDrawable(R.mipmap.ic_launcher);
        draw.setBounds(0, 0, 50, 50);
        text.setCompoundDrawables(draw, null, null, null);//必须手动设置drawable的bounds

        text.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.ic_launcher), null);//不需要设置drawable的bounds，使用图片原有bounds


        text.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault);//设置字体样式

        System.out.println(text.getTextLocale());//获得当前文字区域

//		text.setScaleX((float)0.5);
        text.setScaleY((float)1.5);

        text.setTextScaleX((float)1.5);

        text.setGravity(Gravity.CENTER);//设置位置
        text_pic.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);//添加下划线

        text_pic.setHorizontallyScrolling(true);//不让超出屏幕的文本自动换行，使用滚动条
//		text_pic.setFocusable(true);

//		text_pic.setMinLines(10);//至少几行高
//		text_pic.setMinHeight(150);//至少多高
//		text_pic.setMaxLines(10);
//		text_pic.setLines(5);
//		search.setLineSpacing(1,2);//设置行间距以及倍数，分别对应lineSpacingExtra与lineSpacingMultiplier
        search.append("444");//追加字符串
        search.setTextKeepState(search.getText(), TextView.BufferType.EDITABLE);//保存光标位置，返回界面时依旧在原位置
        go.setHighlightColor(Color.BLACK);//全选时的背景色
        text_pic.setEllipsize(TextUtils.TruncateAt.MARQUEE);//文字超出后如何显示
        text_pic.setMarqueeRepeatLimit(-1);//在设置marquee后才起效
//		text_pic.setFocusable(true);
        text_pic.setFocusableInTouchMode(true);//跑马灯效果三要素


        go.setShadowLayer((float)3.0,(float)10.0,(float)8.0,Color.BLUE);//阴影效果
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch(actionId){
            case EditorInfo.IME_ACTION_GO:
                Toast.makeText(getApplicationContext(), "点击了去往", Toast.LENGTH_SHORT).show();
                break;
            case EditorInfo.IME_ACTION_SEARCH:
                Toast.makeText(getApplicationContext(), "点击了搜索", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
