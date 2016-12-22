package com.gyz.androidsamples.managers;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/6/16.
 * 邮箱:gyzboy@126.com
 */
public class ASWindowManager extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_text_jump);

        TextView tv = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.jump);

        StringBuilder sb = new StringBuilder();

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);//获得屏幕信息，缩放或者有其他标题栏后的
        getWindowManager().getDefaultDisplay().getRealSize(point);//获得屏幕信息，确切值

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        sb.append("x = " + point.x + "y = " + point.y);
        sb.append("\nrotation : " + getWindowManager().getDefaultDisplay().getRotation());//屏幕方向
        sb.append("\ndensity :" + dm.density);
        sb.append("\ndensityDpi :" + dm.densityDpi);
        sb.append("\nwidthPixels :" + dm.widthPixels);
        sb.append("\nheightPixels :" + dm.heightPixels);

        tv.setText(sb.toString());

        //是removeView(ViewTest) 的一个特殊扩展，在方法返回前能够立即调用该视图层次的View.onDetachedFromWindow()方法。
        //必须是通过getWindowManager addview添加的view
//        getWindowManager().removeViewImmediate(button);//

        UsingOfLayoutParams();

    }


    private void UsingOfLayoutParams() {

        Button bb = new Button(this);
        bb.setText("this is bb");
        WindowManager wmManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

        wmParams.x = -50;//这个默认是中心点为原点，当设置了 Gravity.LEFT 或 Gravity.RIGHT 之后，x值就表示到特定边的距离
        wmParams.y = -50;//这个默认是中心点为原点，当设置了 Gravity.TOP 或 Gravity.BOTTOM 之后，y值就表示到特定边的距离

        //在纵/横向上，为关联的view预留了多少扩展空间（像素）。如果是0，那么此view不能被拉伸。
        //其他情况下，扩展空间（像素）将被widget所均分
        wmParams.horizontalWeight = wmParams.verticalWeight = 0;

        //窗口类型。有3种主要类型：
//        a)Applicationwindows：
//        取值在 FIRST_APPLICATION_WINDOW 和 LAST_APPLICATION_WINDOW 之间。
//        是通常的、顶层的应用程序窗口。必须将 token 设置成 activity 的 token 。
//        b)Sub_windows：
//        取值在 FIRST_SUB_WINDOW 和 LAST_SUB_WINDOW 之间。
//        与顶层窗口相关联，token 必须设置为它所附着的宿主窗口的 token。
//        c)Systemwindows：
//        取值在 FIRST_SYSTEM_WINDOW 和 LAST_SYSTEM_WINDOW 之间。
//        用于特定的系统功能。它不能用于应用程序，使用时需要特殊权限。

//        下面定义了 type 的取值：
//        应用程序窗口。FIRST_APPLICATION_WINDOW = 1;
//        所有程序窗口的“基地”窗口，其他应用程序窗口都显示在它上面。TYPE_BASE_APPLICATION =1;
//        普通应哟功能程序窗口。token必须设置为Activity的token，以指出该窗口属谁。TYPE_APPLICATION = 2;
//        用于应用程序启动时所显示的窗口。应用本身不要使用这种类型。
//        它用于让系统显示些信息，直到应用程序可以开启自己的窗口。TYPE_APPLICATION_STARTING = 3;
//
//        应用程序窗口结束。LAST_APPLICATION_WINDOW
//        子窗口。子窗口的Z序和坐标空间都依赖于他们的宿主窗口。FIRST_SUB_WINDOW
//        面板窗口，显示于宿主窗口上层。TYPE_APPLICATION_PANEL
//        媒体窗口，例如视频。显示于宿主窗口下层。TYPE_APPLICATION_MEDIA
//        应用程序窗口的子面板。显示于所有面板窗口的上层。（GUI的一般规律，越“子”越靠上）TYPE_APPLICATION_SUB_PANEL
//        对话框。类似于面板窗口，绘制类似于顶层窗口，而不是宿主的子窗口。TYPE_APPLICATION_ATTACHED_DIALOG
//        媒体信息。显示在媒体层和程序窗口之间，需要实现透明（半透明）效果。（例如显示字幕）TYPE_APPLICATION_MEDIA_OVERLAY
//        子窗口结束。LAST_SUB_WINDOW
//        系统窗口。非应用程序创建。FIRST_SYSTEM_WINDOW
//        状态栏。只能有一个状态栏；它位于屏幕顶端，其他窗口都位于它下方。TYPE_STATUS_BAR
//        搜索栏。只能有一个搜索栏；它位于屏幕上方。TYPE_SEARCH_BAR
//        电话窗口。它用于电话交互（特别是呼入）。它置于所有应用程序之上，状态栏之下。TYPE_PHONE
//        系统提示。它总是出现在应用程序窗口之上。TYPE_SYSTEM_ALERT
//        锁屏窗口。TYPE_KEYGUARD
//        信息窗口。用于显示toast。TYPE_TOAST
//        系统顶层窗口。显示在其他一切内容之上。此窗口不能获得输入焦点，否则影响锁屏。TYPE_SYSTEM_OVERLAY
//        电话优先，当锁屏时显示。此窗口不能获得输入焦点，否则影响锁屏。TYPE_PRIORITY_PHONE
//        系统对话框。（例如音量调节框）。TYPE_SYSTEM_DIALOG
//        锁屏时显示的对话框。TYPE_KEYGUARD_DIALOG
//        系统内部错误提示，显示于所有内容之上。TYPE_SYSTEM_ERROR
//        内部输入法窗口，显示于普通UI之上。应用程序可重新布局以免被此窗口覆盖。TYPE_INPUT_METHOD
//        内部输入法对话框，显示于当前输入法窗口之上。TYPE_INPUT_METHOD_DIALOG= FIRST_SYSTEM_WINDOW +12;
//        墙纸窗口。TYPE_WALLPAPER
//        状态栏的滑动面板。TYPE_STATUS_BAR_PANEL
//        系统窗口结束。 LAST_SYSTEM_WINDOW
        wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        wmParams.format = 1;//bitmap format
        /**
         *这里的flags也很关键
         *代码实际是wmParams.flags |= FLAG_NOT_FOCUSABLE;
         *40的由来是wmParams的默认属性（32）+ FLAG_NOT_FOCUSABLE（8）,默认为none
         */

//        行为选项/旗标，默认为 none .
//        下面定义了 flags 的取值：
//        窗口之后的内容变暗。FLAG_DIM_BEHIND       = 0x00000002;
//
//        窗口之后的内容变模糊。
//        public static final int FLAG_BLUR_BEHIND       = 0x00000004;
//
//        不许获得焦点。
//        public static final int FLAG_NOT_FOCUSABLE     = 0x00000008;
//
//        不接受触摸屏事件。
//        public static final int FLAG_NOT_TOUCHABLE     = 0x00000010;
//
//        当窗口可以获得焦点（没有设置 FLAG_NOT_FOCUSALBE 选项）时，仍然将窗口范围之外的点设备事件（鼠标、触摸屏）发送给后面的窗口处理。否则它将独占所有的点设备事件，而不管它们是不是发生在窗口范围内。
//        public static final int FLAG_NOT_TOUCH_MODAL   = 0x00000020;
//
//        如果设置了这个标志，当设备休眠时，点击触摸屏，设备将收到这个第一触摸事件。
//        通常第一触摸事件被系统所消耗，用户不会看到他们点击屏幕有什么反应。
//        public static final int FLAG_TOUCHABLE_WHEN_WAKING = 0x00000040;
//
//        当此窗口为用户可见时，保持设备常开，并保持亮度不变。
//        public static final int FLAG_KEEP_SCREEN_ON    = 0x00000080;
//
//        窗口占满整个屏幕，忽略周围的装饰边框（例如状态栏）。此窗口需考虑到装饰边框的内容。
//        public static final int FLAG_LAYOUT_IN_SCREEN   =0x00000100;
//
//        允许窗口扩展到屏幕之外。
//        public static final int FLAG_LAYOUT_NO_LIMITS   =0x00000200;
//
//        窗口显示时，隐藏所有的屏幕装饰（例如状态条）。使窗口占用整个显示区域。
//        public static final int FLAG_FULLSCREEN     = 0x00000400;
//
//        此选项将覆盖FLAG_FULLSCREEN选项，并强制屏幕装饰（如状态条）弹出。
//        public static final int FLAG_FORCE_NOT_FULLSCREEN   =0x00000800;
//
//        抖动。指 对半透明的显示方法。又称“点透”。图形处理较差的设备往往用“点透”替代Alpha混合。
//        public static final int FLAG_DITHER           = 0x00001000;
//
//        不允许屏幕截图。
//        public static final int FLAG_SECURE           = 0x00002000;
//
//        一种特殊模式，布局参数用于指示显示比例。
//        public static final int FLAG_SCALED           = 0x00004000;
//
//        当屏幕有可能贴着脸时，这一选项可防止面颊对屏幕造成误操作。
//        public static final int FLAG_IGNORE_CHEEK_PRESSES   = 0x00008000;
//
//        当请求布局时，你的窗口可能出现在状态栏的上面或下面，从而造成遮挡。当设置这一选项后，窗口管理器将确保窗口内容不会被装饰条（状态栏）盖住。
//        public static final int FLAG_LAYOUT_INSET_DECOR = 0x00010000;
//
//        反转FLAG_NOT_FOCUSABLE选项。
//        如果同时设置了FLAG_NOT_FOCUSABLE选项和本选项，窗口将能够与输入法交互，允许输入法窗口覆盖；
//        如果FLAG_NOT_FOCUSABLE没有设置而设置了本选项，窗口不能与输入法交互，可以覆盖输入法窗口。
//        public static final int FLAG_ALT_FOCUSABLE_IM = 0x00020000;
//
//        如果你设置了FLAG_NOT_TOUCH_MODAL，那么当触屏事件发生在窗口之外事，可以通过设置此标志接收到一个 MotionEvent.ACTION_OUTSIDE事件。注意，你不会收到完整的down/move/up事件，只有第一次down事件时可以收到 ACTION_OUTSIDE。
//        public static final int FLAG_WATCH_OUTSIDE_TOUCH = 0x00040000;
//
//        当屏幕锁定时，窗口可以被看到。这使得应用程序窗口优先于锁屏界面。可配合FLAG_KEEP_SCREEN_ON选项点亮屏幕并直接显示在锁屏界面之前。可使用FLAG_DISMISS_KEYGUARD选项直接解除非加锁的锁屏状态。此选项只用于最顶层的全屏幕窗口。
//        public static final int FLAG_SHOW_WHEN_LOCKED = 0x00080000;
//
//        请求系统墙纸显示在你的窗口后面。窗口必须是半透明的。
//        public static final int FLAG_SHOW_WALLPAPER = 0x00100000;
//
//        窗口一旦显示出来，系统将点亮屏幕，正如用户唤醒设备那样。
//        public static final int FLAG_TURN_SCREEN_ON = 0x00200000;
//
//        解除锁屏。只有锁屏界面不是加密的才能解锁。如果锁屏界面是加密的，那么用户解锁之后才能看到此窗口，除非设置了FLAG_SHOW_WHEN_LOCKED选项。
//        public static final int FLAG_DISMISS_KEYGUARD = 0x00400000;
//
//        锁屏界面淡出时，继续运行它的动画。
//        public static final int FLAG_KEEP_SURFACE_WHILE_ANIMATING =0x10000000;
//
//        以原始尺寸显示窗口。用于在兼容模式下运行程序。
//        public static final int FLAG_COMPATIBLE_WINDOW = 0x20000000;
//
//        用于系统对话框。设置此选项的窗口将无条件获得焦点。
//        public static final int FLAG_SYSTEM_ERROR = 0x40000000;
        wmParams.flags = 32;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED;
        wmParams.gravity = Gravity.NO_GRAVITY;
//        wmParams.horizontalMargin = 50;
        wmParams.height = 80;

        wmManager.addView(bb, wmParams);  //创建View
    }
}
