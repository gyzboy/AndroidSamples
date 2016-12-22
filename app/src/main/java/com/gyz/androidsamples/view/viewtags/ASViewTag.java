package com.gyz.androidsamples.view.viewtags;

import android.app.Activity;
import android.os.Bundle;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/9/18.
 * 邮箱:gyzboy@126.com
 */
public class ASViewTag{


//    android:background
//    设置背景色/背景图片。可以通过以下两种方法设置背景为透明："@android:color/transparent"和"@null"。注意TextView默认是透明的，不用写此属性，但是Buttom/ImageButton/ImageView想透明的话就得写这个属性了。
//
//    android:clickable
//    是否响应点击事件。
//
//    android:contentDescription
//    设置View的备注说明，作为一种辅助功能提供,为一些没有文字描述的View提供说明，如ImageButton。这里在界面上不会有效果，自己在程序中控制，可临时放一点字符串数据。
//
//    android:drawingCacheQuality
//    设置绘图时半透明质量。有以下值可设置：auto（默认，由框架决定）/high（高质量，使用较高的颜色深度，消耗更多的内存/low（低质量，使用较低的颜色深度，但是用更少的内存）。
//
//    android:duplicateParentState
//    如果设置此属性，将直接从父容器中获取绘图状态（光标，按下等）。 见下面代码部分，注意根据目前测试情况仅仅是获取绘图状态，而没有获取事件，也就是你点一下LinearLayout时Button有被点击的效果，但是不执行点击事件。
//
//    android:fadingEdge
//    设置拉滚动条时 ，边框渐变的放向。none（边框颜色不变），horizontal（水平方向颜色变淡），vertical（垂直方向颜色变淡）。参照fadingEdgeLength的效果图
//
//    android:fadingEdgeLength
//    设置边框渐变的长度。
//
//    android:fitsSystemWindows
//    设置布局调整时是否考虑系统窗口（如状态栏）
//
//    android:focusable
//    设置是否获得焦点。若有requestFocus()被调用时，后者优先处理。注意在表单中想设置某一个如EditText获取焦点，光设置这个是不行的，需要将这个EditText前面的focusable都设置为false才行。在Touch模式下获取焦点需要设置focusableInTouchMode为true。
//
//    android:focusableInTouchMode
//    设置在Touch模式下View是否能取得焦点。
//
//    android:hapticFeedbackEnabled
//    设置触感反馈。按软键以及进行某些UI交互时振动，暂时不知道用法，大家可以找找performHapticFeedback或HapticFeedback这个关键字的资料看看。
//
//    android:id
//    给当前View设置一个在当前layout.xml中的唯一编号，可以通过调用View.findViewById() 或Activity.findViewById()根据这个编号查找到对应的View。不同的layout.xml之间定义相同的id不会冲突。格式如”@+id/btnName”
//
//    android:isScrollContainer
//    设置当前View为滚动容器。这里没有测试出效果来，ListView/ GridView/ ScrollView根本就不用设置这个属性，而EdidText设置android:scrollbars也能出滚动条。
//
//    android:keepScreenOn
//    View在可见的情况下是否保持唤醒状态。常在LinearLayout使用该属性，但是模拟器这里没有效果。
//
//    android:longClickable
//    设置是否响应长按事件.
//
//    android:minHeight
//    设置视图最小高度
//
//    android:minWidth
//    设置视图最小宽度度
//
//    android:nextFocusDown
//    设置下方指定视图获得下一个焦点。焦点移动是基于一个在给定方向查找最近邻居的算法。如果指定视图不存在，移动焦点时将报运行时错误。可以设置imeOptions= actionDone，这样输入完即跳到下一个焦点。
//
//    android:nextFocusLeft
//    设置左边指定视图获得下一个焦点。
//
//    android:nextFocusRight
//    设置右边指定视图获得下一个焦点。
//
//    android:nextFocusUp
//    设置上方指定视图获得下一个焦点。
//
//    android:onClick
//    点击时从上下文中调用指定的方法。这里指定一个方法名称，一般在Activity定义符合如下参数和返回值的函数并将方法名字符串指定为该值即可：
//    public void onClickButton(View view)
//    android:onClick=” onClickButton”
//
//    android:padding
//    设置上下左右的内边距，以像素为单位填充空白。（译者注：下图是Button外部嵌套了一个容器，设置了容器的内边距）
//
//    android:paddingBottom
//    设置底部的边距，以像素为单位填充空白。（译者注：下图是Button外部嵌套了一个容器，设置了容器的内边距）
//
//    android:paddingLeft
//    设置左边的边距，以像素为单位填充空白。（译者注：下图是Button外部嵌套了一个容器，设置了容器的内边距）
//
//    android:paddingRight
//    设置右边的边距，以像素为单位填充空白。（译者注：下图是Button外部嵌套了一个容器，设置了容器的内边距）
//
//    android:paddingTop
//    设置上方的边距，以像素为单位填充空白。（译者注：下图是Button外部嵌套了一个容器，设置了容器的内边距）
//
//    android:saveEnabled
//    设置是否在窗口冻结时（如旋转屏幕）保存View的数据，默认为true，但是前提是你需要设置id才能自动保存，参见这里。
//
//    android:scrollX
//    以像素为单位设置水平方向滚动的的偏移值，在GridView中可看的这个效果。
//
//    android:scrollY
//    以像素为单位设置垂直方向滚动的的偏移值
//
//    android:scrollbarAlwaysDrawHorizontalTrack
//    设置是否始终显示垂直滚动条。这里用ScrollView、ListView测试均没有效果。
//
//    android:scrollbarAlwaysDrawVerticalTrack
//    设置是否始终显示垂直滚动条。这里用ScrollView、ListView测试均没有效果。
//
//    android:scrollbarDefaultDelayBeforeFade
//    设置N毫秒后开始淡化，以毫秒为单位。
//
//    android:scrollbarFadeDuration
//    设置滚动条淡出效果（从有到慢慢的变淡直至消失）时间，以毫秒为单位。Android2.2中滚动条滚动完之后会消失，再滚动又会出来，在1.5、1.6版本里面会一直显示着。
//
//    android:scrollbarSize
//    设置滚动条的宽度。
//
//    android:scrollbarStyle
//    设置滚动条的风格和位置。设置值：insideOverlay、insideInset、outsideOverlay、outsideInset。
//    insideOverlay：默认值，表示在padding区域内并且覆盖在view上
//    insideInset：表示在padding区域内并且插入在view后面
//    outsideOverlay：表示在padding区域外并且覆盖在view上，推荐这个
//    outsideInset：表示在padding区域外并且插入在view后面
//
//    android:scrollbarThumbHorizontal
//    设置水平滚动条的drawable。
//
//    android:scrollbarThumbVertical
//    设置垂直滚动条的drawable.
//
//    android:scrollbarTrackHorizontal
//    设置水平滚动条背景（轨迹）的色drawable
//
//    android:scrollbarTrackVertical
//    设置垂直滚动条背景（轨迹）的drawable注意直接
//    设置颜色值如”android:color/white”将得出很难看的效果，
//    甚至都不理解这个属性了，这里可以
//    参见ApiDemos里res/drawable/
//    scrollbar_vertical_thumb.xml和
//    scrollbar_vertical_track.xml，设置代码为：
//    android:scrollbarTrackVertical
//    ="@drawable/scrollbar_vertical_track"
//
//    android:scrollbars
//    设置滚动条显示。none（隐藏），horizontal（水平），vertical（垂直）。使用该属性让EditText内有滚动条。但是其他容器如LinearLayout设置了但是没有效果。

//    android:soundEffectsEnabled
//    设置点击或触摸时是否有声音效果
//
//    android:tag
//    设置一个文本标签。可以通过View.getTag()或 for withView.findViewWithTag()检索含有该标签字符串的View。但一般最好通过ID来查询View，因为它的速度更快，并且允许编译时类型检查。
//
//    android:visibility
//    设置是否显示View。设置值：visible（默认值，显示），invisible（不显示，但是仍然占用空间），gone（不显示，不占用空间）

//    android:isScrollContainer="false"
//    对于键盘弹出时界面的显示效果是有影响的，当android:isScrollContainer=“false”时，效果类似于设置Activity的android:windowSoftInputMode="adjustPan"属性

//    android:fadeScrollbars="false"
//    设置渐隐

//    android:saveEnabled="true"
//    是否保存状态

//    android:overScrollMode="always"
//    边界回弹效果,当滑动超过view视图时出现

//    android:layerType="hardware"  离屏缓存模式
//    NONE view按一般方式绘制，不使用离屏缓冲．这是默认的行为．
//    LAYER_TYPE_HARDWARE:如果应用被硬加速了，view会被绘制到一个硬件纹理中．如果应用没被硬加速，此类型的layer的行为同于LAYER_TYPE_SOFTWARE．
//    LAYER_TYPE_SOFTWARE:view被绘制到一个bitmap中

//    android:layoutDirection
//    定义布局图纸的方向

//    android:textDirection
//    定义文字方向

//    android:textAlignment
//    文字对齐方式

}
