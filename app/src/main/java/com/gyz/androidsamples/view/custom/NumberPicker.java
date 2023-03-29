package com.shouzhong.numberpicker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Filter;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Scroller;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 类似原生NumberPicker效果，在此基础上加了些其他功能
 *
 */
public class NumberPicker extends LinearLayout {

    public static final int VERTICAL = LinearLayout.VERTICAL;
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;

    public static final int ASCENDING = 0;
    public static final int DESCENDING = 1;

    public static final int RIGHT = 0;
    public static final int CENTER = 1;
    public static final int LEFT = 2;

    /**
     * 长按时默认更新间隔
     */
    private static final long DEFAULT_LONG_PRESS_UPDATE_INTERVAL = 300;

    /**
     * 最大速度默认值
     */
    private static final int DEFAULT_MAX_FLING_VELOCITY_COEFFICIENT = 8;

    /**
     * 滚轮持续时间
     */
    private static final int SELECTOR_ADJUSTMENT_DURATION_MILLIS = 800;

    /**
     * 捕捉到给定位置时滚动的持续时间
     */
    private static final int SNAP_SCROLL_DURATION = 300;

    /**
     * 绘制选择器时默认的渐变边缘强度
     */
    private static final float DEFAULT_FADING_EDGE_STRENGTH = 0.9f;

    /**
     * 分隔线的默认高度
     */
    private static final int UNSCALED_DEFAULT_DIVIDER_THICKNESS = 2;

    /**
     * 分隔线之间的默认距离
     */
    private static final int UNSCALED_DEFAULT_DIVIDER_DISTANCE = 48;

    /**
     * 未指定大小时的常量
     */
    private static final int SIZE_UNSPECIFIED = -1;

    /**
     * 分割线的默认颜色
     */
    private static final int DEFAULT_DIVIDER_COLOR = 0xFF000000;

    /**
     * 默认最大值
     */
    private static final int DEFAULT_MAX_VALUE = Integer.MAX_VALUE;

    /**
     * 默认最小值
     */
    private static final int DEFAULT_MIN_VALUE = Integer.MIN_VALUE;

    /**
     * 默认长度
     */
    private static final int DEFAULT_WHEEL_ITEM_COUNT = 3;

    /**
     * 默认文本位置
     */
    private static final int DEFAULT_TEXT_ALIGN = CENTER;

    /**
     * 默认文本颜色
     */
    private static final int DEFAULT_TEXT_COLOR = 0xFF000000;

    /**
     * 默认文本大小
     */
    private static final float DEFAULT_TEXT_SIZE = 25f;

    /**
     * 默认文本行间距系数
     */
    private static final float DEFAULT_LINE_SPACING_MULTIPLIER = 1f;

    /**
     * 使用自定义NumberPicker格式回调来使用两位数的分钟字符串如“01”。
     * 保持静态格式化程序等是最有效的这样做的方式; 它避免了在每次调用时创建临时对象format（）。
     */
    private static class TwoDigitFormatter implements Formatter {
        final StringBuilder mBuilder = new StringBuilder();

        char mZeroDigit;
        java.util.Formatter mFmt;

        final Object[] mArgs = new Object[1];

        Locale mLocale;

        TwoDigitFormatter() {
            mLocale = Locale.getDefault();
            init(mLocale);
        }

        private void init(Locale locale) {
            mFmt = createFormatter(locale);
            mZeroDigit = getZeroDigit(locale);
        }

        public String format(int value) {
            Locale currentLocale = Locale.getDefault();
            // to force the locale value set by using setter method
            if (!mLocale.equals(currentLocale)) {
                currentLocale = mLocale;
            }
            if (mZeroDigit != getZeroDigit(currentLocale)) {
                init(currentLocale);
            }
            mArgs[0] = value;
            mBuilder.delete(0, mBuilder.length());
            mFmt.format("%02d", mArgs);
            return mFmt.toString();
        }

        private static char getZeroDigit(Locale locale) {
            // return LocaleData.get(locale).zeroDigit;
            return new DecimalFormatSymbols(locale).getZeroDigit();
        }

        // to force the locale value set by using setter method
        void setLocale(Locale locale) {
            if (this.mLocale != null && mLocale.equals(locale)) {
                return;
            }
            this.mLocale = locale;
            init(mLocale);
        }

        private java.util.Formatter createFormatter(Locale locale) {
            return new java.util.Formatter(mBuilder, locale);
        }
    }

    private static final TwoDigitFormatter sTwoDigitFormatter = new TwoDigitFormatter();

    public static final Formatter getTwoDigitFormatter() {
        return sTwoDigitFormatter;
    }

    /**
     * 文本框
     */
    private final EditText mSelectedText;

    /**
     * 文本位于中间位置时的x坐标
     */
    private float mSelectedTextCenterX;

    /**
     * 文本位于中间位置时的y坐标
     */
    private float mSelectedTextCenterY;

    /**
     * 最小高度
     */
    private int mMinHeight = SIZE_UNSPECIFIED;

    /**
     * 最大高度
     */
    private int mMaxHeight = SIZE_UNSPECIFIED;

    /**
     * 最小宽度
     */
    private int mMinWidth = SIZE_UNSPECIFIED;

    /**
     * 最大宽度
     */
    private int mMaxWidth = SIZE_UNSPECIFIED;

    /**
     * 是否计算最大宽度
     */
    private final boolean mComputeMaxWidth;

    /**
     * 选中文本位置
     */
    private int mSelectedTextAlign = DEFAULT_TEXT_ALIGN;

    /**
     * 选中文本颜色
     */
    private int mSelectedTextColor = DEFAULT_TEXT_COLOR;

    /**
     * 选中文本大小
     */
    private float mSelectedTextSize = DEFAULT_TEXT_SIZE;

    /**
     * 选中文本是否删除线
     */
    private boolean mSelectedTextStrikeThru;

    /**
     * 选中文本是否下划线
     */
    private boolean mSelectedTextUnderline;

    /**
     * 文本位置
     */
    private int mTextAlign = DEFAULT_TEXT_ALIGN;

    /**
     * 文本颜色
     */
    private int mTextColor = DEFAULT_TEXT_COLOR;

    /**
     * 文本大小
     */
    private float mTextSize = DEFAULT_TEXT_SIZE;

    /**
     * 文本是否删除线
     */
    private boolean mTextStrikeThru;

    /**
     * 文本是否下划线
     */
    private boolean mTextUnderline;

    /**
     * 文本字体
     */
    private Typeface mTypeface;

    /**
     * 滚动时文本间距宽度
     */
    private int mSelectorTextGapWidth;

    /**
     * 滚动时文本间距高度
     */
    private int mSelectorTextGapHeight;

    /**
     * 展示的文本
     */
    private String[] mDisplayedValues;

    /**
     * 数据最小长度
     */
    private int mMinValue = DEFAULT_MIN_VALUE;

    /**
     * 数据最大长度
     */
    private int mMaxValue = DEFAULT_MAX_VALUE;

    /**
     * 当前值
     */
    private int mValue;

    /**
     * 选中的点击监听
     */
    private OnClickListener mOnClickListener;

    /**
     * 选中时监听
     */
    private OnValueChangeListener mOnValueChangeListener;

    /**
     * 滑动监听
     */
    private OnScrollListener mOnScrollListener;

    /**
     * 展示的值转换器
     */
    private Formatter mFormatter;

    /**
     * 长按时更新的速度
     */
    private long mLongPressUpdateInterval = DEFAULT_LONG_PRESS_UPDATE_INTERVAL;

    /**
     * 缓存的字符串
     */
    private final SparseArray<String> mSelectorIndexToStringCache = new SparseArray<>();

    /**
     * 显示的item数
     */
    private int mWheelItemCount = DEFAULT_WHEEL_ITEM_COUNT;

    /**
     * 显示的实际item数
     */
    private int mRealWheelItemCount = DEFAULT_WHEEL_ITEM_COUNT;

    /**
     * 中间项的索引
     */
    private int mWheelMiddleItemIndex = mWheelItemCount / 2;

    /**
     * 选择器索引
     */
    private int[] mSelectorIndices = new int[mWheelItemCount];

    /**
     * 用于绘制选择器
     */
    private final Paint mSelectorWheelPaint;

    /**
     * 选择项的尺寸（字体大小+间距）
     */
    private int mSelectorElementSize;

    /**
     * 初始偏移量
     */
    private int mInitialScrollOffset = Integer.MIN_VALUE;

    /**
     * 当前偏移量
     */
    private int mCurrentScrollOffset;

    /**
     * The {@link Scroller} 滑动
     */
    private final Scroller mFlingScroller;

    /**
     * {@link Scroller} 调整
     */
    private final Scroller mAdjustScroller;

    /**
     * {@link Locale}
     */
    private Locale mLocale;

    /**
     * 滚动时上一个的x坐标
     */
    private int mPreviousScrollerX;

    /**
     * 滚动时上一个的y坐标
     */
    private int mPreviousScrollerY;

    /**
     * 处理可重用命令以设置输入文本选择
     */
    private SetSelectionCommand mSetSelectionCommand;

    /**
     * 处理可重复使用的命令，将长按的当前值更改为1
     */
    private ChangeCurrentByOneFromLongPressCommand mChangeCurrentByOneFromLongPressCommand;

    /**
     * 最后一次事件的x坐标
     */
    private float mLastDownEventX;

    /**
     * 最后一次事件的y坐标
     */
    private float mLastDownEventY;

    /**
     * 最后一次向下或移动事件的x坐标
     */
    private float mLastDownOrMoveEventX;

    /**
     * 最后一次向下或移动事件的y坐标
     */
    private float mLastDownOrMoveEventY;

    /**
     * 决定触摸滚动期间的速度
     */
    private VelocityTracker mVelocityTracker;

    /**
     * @see ViewConfiguration#getScaledTouchSlop()
     */
    private int mTouchSlop;

    /**
     * @see ViewConfiguration#getScaledMinimumFlingVelocity()
     */
    private int mMinimumFlingVelocity;

    /**
     * @see ViewConfiguration#getScaledMaximumFlingVelocity()
     */
    private int mMaximumFlingVelocity;

    /**
     * 是否环绕显示，由mWrapSelectorWheelPreferred且显示的item数>=总item数
     */
    private boolean mWrapSelectorWheel;

    /**
     * 是否环绕显示
     */
    private boolean mWrapSelectorWheelPreferred = true;

    /**
     * divider背景
     */
    private Drawable mDividerDrawable;

    /**
     * divider颜色
     */
    private int mDividerColor = DEFAULT_DIVIDER_COLOR;

    /**
     * divider间距
     */
    private int mDividerDistance;

    /**
     * divider厚度
     */
    private int mDividerThickness;

    /**
     * 顶部divider的顶部位置
     */
    private int mTopDividerTop;

    /**
     * 底部divider的底部位置
     */
    private int mBottomDividerBottom;

    /**
     * 左边divider的左边位置
     */
    private int mLeftDividerLeft;

    /**
     * 右边分割线的右边位置
     */
    private int mRightDividerRight;

    /**
     * 当前滑动状态
     */
    private int mScrollState = OnScrollListener.SCROLL_STATE_IDLE;

    /**
     * @see #dispatchKeyEvent
     * 最后一次keyevent的keycode
     */
    private int mLastHandledDownDpadKeyCode = -1;

    /**
     * 聚焦前是否显示
     */
    private boolean mHideWheelUntilFocused;

    /**
     * 方向
     */
    private int mOrientation;

    /**
     * 排序方式
     */
    private int mOrder;

    /**
     * 边缘fade
     */
    private boolean mFadingEdgeEnabled = true;

    /**
     * 边缘fade强度
     */
    private float mFadingEdgeStrength = DEFAULT_FADING_EDGE_STRENGTH;

    /**
     * 是否可以滑动
     */
    private boolean mScrollerEnabled = true;

    /**
     * 文本行间距系数
     */
    private float mLineSpacingMultiplier = DEFAULT_LINE_SPACING_MULTIPLIER;

    /**
     * 最大滑动速度
     */
    private int mMaxFlingVelocityCoefficient = DEFAULT_MAX_FLING_VELOCITY_COEFFICIENT;

    /**
     *
     */
    private Context mContext;

    /**
     * 数字格式化
     */
    private NumberFormat mNumberFormatter;

    /**
     * view的配置
     */
    private ViewConfiguration mViewConfiguration;

    /**
     * 值变化监听
     */
    public interface OnValueChangeListener {

        /**
         * 当值发生变化时调用
         *
         * @param picker
         * @param oldVal 旧的值
         * @param newVal 当前值
         */
        void onValueChange(NumberPicker picker, int oldVal, int newVal);
    }

    /**
     * Interface to listen for the picker scroll state.
     */
    public interface OnScrollListener {
        /**
         * The view is not scrolling.
         */
        int SCROLL_STATE_IDLE = 0;

        /**
         * The user is scrolling using touch, and his finger is still on the screen.
         */
        int SCROLL_STATE_TOUCH_SCROLL = 1;

        /**
         * The user had previously been scrolling using touch and performed a fling.
         */
        int SCROLL_STATE_FLING = 2;

        /**
         * Callback invoked while the number picker scroll state has changed.
         *
         * @param view        The view whose scroll state is being reported.
         * @param scrollState The current scroll state. One of
         *                    {@link #SCROLL_STATE_IDLE},
         *                    {@link #SCROLL_STATE_TOUCH_SCROLL} or
         *                    {@link #SCROLL_STATE_IDLE}.
         */
        void onScrollStateChange(NumberPicker view, int scrollState);
    }

    /**
     * Interface used to format current value into a string for presentation.
     */
    public interface Formatter {

        /**
         * Formats a string representation of the current value.
         *
         * @param value The currently selected value.
         * @return A formatted string representation.
         */
        String format(int value);
    }

    /**
     * Create a new number picker.
     *
     * @param context The application environment.
     */
    public NumberPicker(Context context) {
        this(context, null);
    }

    /**
     * Create a new number picker.
     *
     * @param context The application environment.
     * @param attrs A collection of attributes.
     */
    public NumberPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Create a new number picker
     *
     * @param context the application environment.
     * @param attrs a collection of attributes.
     * @param defStyle The default style to apply to this view.
     */
    public NumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        mContext = context;
        mNumberFormatter = NumberFormat.getInstance();
        mLocale = Locale.getDefault();

        final TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.NumberPicker, defStyle, 0);

        final Drawable selectionDivider = attributes.getDrawable(
                R.styleable.NumberPicker_npDivider);
        if (selectionDivider != null) {
            selectionDivider.setCallback(this);
            if (selectionDivider.isStateful()) {
                selectionDivider.setState(getDrawableState());
            }
            mDividerDrawable = selectionDivider;
        } else {
            mDividerColor = attributes.getColor(R.styleable.NumberPicker_npDividerColor,
                    mDividerColor);
            setDividerColor(mDividerColor);
        }

        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        final int defDividerDistance = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                UNSCALED_DEFAULT_DIVIDER_DISTANCE, displayMetrics);
        final int defDividerThickness = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                UNSCALED_DEFAULT_DIVIDER_THICKNESS, displayMetrics);
        mDividerDistance = attributes.getDimensionPixelSize(
                R.styleable.NumberPicker_npDividerDistance, defDividerDistance);
        mDividerThickness = attributes.getDimensionPixelSize(
                R.styleable.NumberPicker_npDividerThickness, defDividerThickness);

        mOrder = attributes.getInt(R.styleable.NumberPicker_npOrder, ASCENDING);
        mOrientation = attributes.getInt(R.styleable.NumberPicker_npOrientation, VERTICAL);

        mComputeMaxWidth = true;

        mValue = attributes.getInt(R.styleable.NumberPicker_npValue, mValue);
        mMaxValue = attributes.getInt(R.styleable.NumberPicker_npMax, mMaxValue);
        mMinValue = attributes.getInt(R.styleable.NumberPicker_npMin, mMinValue);

        mSelectedTextAlign = attributes.getInt(R.styleable.NumberPicker_npSelectedTextAlign,
                mSelectedTextAlign);
        mSelectedTextColor = attributes.getColor(R.styleable.NumberPicker_npSelectedTextColor,
                mSelectedTextColor);
        mSelectedTextSize = attributes.getDimension(R.styleable.NumberPicker_npSelectedTextSize,
                spToPx(mSelectedTextSize));
        mSelectedTextStrikeThru = attributes.getBoolean(
                R.styleable.NumberPicker_npSelectedTextStrikeThru, mSelectedTextStrikeThru);
        mSelectedTextUnderline = attributes.getBoolean(
                R.styleable.NumberPicker_npSelectedTextUnderline, mSelectedTextUnderline);
        mTextAlign = attributes.getInt(R.styleable.NumberPicker_npTextAlign, mTextAlign);
        mTextColor = attributes.getColor(R.styleable.NumberPicker_npTextColor, mTextColor);
        mTextSize = attributes.getDimension(R.styleable.NumberPicker_npTextSize,
                spToPx(mTextSize));
        mTextStrikeThru = attributes.getBoolean(
                R.styleable.NumberPicker_npTextStrikeThru, mTextStrikeThru);
        mTextUnderline = attributes.getBoolean(
                R.styleable.NumberPicker_npTextUnderline, mTextUnderline);
        mTypeface = Typeface.create(attributes.getString(R.styleable.NumberPicker_npTypeface),
                Typeface.NORMAL);
        mFormatter = stringToFormatter(attributes.getString(R.styleable.NumberPicker_npFormatter));
        mFadingEdgeEnabled = attributes.getBoolean(R.styleable.NumberPicker_npFadingEdgeEnabled,
                mFadingEdgeEnabled);
        mFadingEdgeStrength = attributes.getFloat(R.styleable.NumberPicker_npFadingEdgeStrength,
                mFadingEdgeStrength);
        mScrollerEnabled = attributes.getBoolean(R.styleable.NumberPicker_npScrollerEnabled,
                mScrollerEnabled);
        mWheelItemCount = attributes.getInt(R.styleable.NumberPicker_npWheelItemCount,
                mWheelItemCount);
        mLineSpacingMultiplier = attributes.getFloat(
                R.styleable.NumberPicker_npLineSpacingMultiplier, mLineSpacingMultiplier);
        mMaxFlingVelocityCoefficient = attributes.getInt(
                R.styleable.NumberPicker_npMaxFlingVelocityCoefficient,
                mMaxFlingVelocityCoefficient);
        mHideWheelUntilFocused = attributes.getBoolean(
                R.styleable.NumberPicker_npHideWheelUntilFocused, false);

        // By default Linearlayout that we extend is not drawn. This is
        // its draw() method is not called but dispatchDraw() is called
        // directly (see ViewGroup.drawChild()). However, this class uses
        // the fading edge effect implemented by View and we need our
        // draw() method to be called. Therefore, we declare we will draw.
        setWillNotDraw(false);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.number_picker_material, this, true);

        // input text
        mSelectedText = findViewById(R.id.np__numberpicker_input);
        mSelectedText.setEnabled(false);
        mSelectedText.setFocusable(false);
        mSelectedText.setImeOptions(EditorInfo.IME_ACTION_NONE);

        // create the selector wheel paint
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        mSelectorWheelPaint = paint;

        setSelectedTextColor(mSelectedTextColor);
        setTextColor(mTextColor);
        setTextSize(mTextSize);
        setSelectedTextSize(mSelectedTextSize);
        setTypeface(mTypeface);
        setFormatter(mFormatter);
        updateInputTextView();

        setValue(mValue);
        setMaxValue(mMaxValue);
        setMinValue(mMinValue);

        setWheelItemCount(mWheelItemCount);

        mWrapSelectorWheel = attributes.getBoolean(R.styleable.NumberPicker_npWrapSelectorWheel,
                mWrapSelectorWheel);
        setWrapSelectorWheel(mWrapSelectorWheel);

        // initialize constants
        mViewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = mViewConfiguration.getScaledTouchSlop();
        mMinimumFlingVelocity = mViewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = mViewConfiguration.getScaledMaximumFlingVelocity()
                / mMaxFlingVelocityCoefficient;

        // create the fling and adjust scrollers
        mFlingScroller = new Scroller(context, null, true);
        mAdjustScroller = new Scroller(context, new DecelerateInterpolator(2.5f));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // If not explicitly specified this view is important for accessibility.
            if (getImportantForAccessibility() == IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
                setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Should be focusable by default, as the text view whose visibility changes is focusable
            if (getFocusable() == View.FOCUSABLE_AUTO) {
                setFocusable(View.FOCUSABLE);
                setFocusableInTouchMode(true);
            }
        }

        attributes.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int msrdWdth = getMeasuredWidth();
        final int msrdHght = getMeasuredHeight();

        // Input text centered horizontally.
        final int inptTxtMsrdWdth = mSelectedText.getMeasuredWidth();
        final int inptTxtMsrdHght = mSelectedText.getMeasuredHeight();
        final int inptTxtLeft = (msrdWdth - inptTxtMsrdWdth) / 2;
        final int inptTxtTop = (msrdHght - inptTxtMsrdHght) / 2;
        final int inptTxtRight = inptTxtLeft + inptTxtMsrdWdth;
        final int inptTxtBottom = inptTxtTop + inptTxtMsrdHght;
        mSelectedText.layout(inptTxtLeft, inptTxtTop, inptTxtRight, inptTxtBottom);
        mSelectedTextCenterX = mSelectedText.getX() + mSelectedText.getMeasuredWidth() / 2;
        mSelectedTextCenterY = mSelectedText.getY() + mSelectedText.getMeasuredHeight() / 2;

        if (changed) {
            // need to do all this when we know our size
            initializeSelectorWheel();
            initializeFadingEdges();

            final int dividerDistance = 2 * mDividerThickness + mDividerDistance;
            if (isHorizontalMode()) {
                mLeftDividerLeft = (getWidth() - mDividerDistance) / 2 - mDividerThickness;
                mRightDividerRight = mLeftDividerLeft + dividerDistance;
            } else {
                mTopDividerTop = (getHeight() - mDividerDistance) / 2 - mDividerThickness;
                mBottomDividerBottom = mTopDividerTop + dividerDistance;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Try greedily to fit the max width and height.
        final int newWidthMeasureSpec = makeMeasureSpec(widthMeasureSpec, mMaxWidth);
        final int newHeightMeasureSpec = makeMeasureSpec(heightMeasureSpec, mMaxHeight);
        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);
        // Flag if we are measured with width or height less than the respective min.
        final int widthSize = resolveSizeAndStateRespectingMinSize(mMinWidth, getMeasuredWidth(),
                widthMeasureSpec);
        final int heightSize = resolveSizeAndStateRespectingMinSize(mMinHeight, getMeasuredHeight(),
                heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    /**
     * Move to the final position of a scroller. Ensures to force finish the scroller
     * and if it is not at its final position a scroll of the selector wheel is
     * performed to fast forward to the final position.
     *
     * @param scroller The scroller to whose final position to get.
     * @return True of the a move was performed, i.e. the scroller was not in final position.
     */
    private boolean moveToFinalScrollerPosition(Scroller scroller) {
        scroller.forceFinished(true);
        if (isHorizontalMode()) {
            int amountToScroll = scroller.getFinalX() - scroller.getCurrX();
            int futureScrollOffset = (mCurrentScrollOffset + amountToScroll) % mSelectorElementSize;
            int overshootAdjustment = mInitialScrollOffset - futureScrollOffset;
            if (overshootAdjustment != 0) {
                if (Math.abs(overshootAdjustment) > mSelectorElementSize / 2) {
                    if (overshootAdjustment > 0) {
                        overshootAdjustment -= mSelectorElementSize;
                    } else {
                        overshootAdjustment += mSelectorElementSize;
                    }
                }
                amountToScroll += overshootAdjustment;
                scrollBy(amountToScroll, 0);
                return true;
            }
        } else {
            int amountToScroll = scroller.getFinalY() - scroller.getCurrY();
            int futureScrollOffset = (mCurrentScrollOffset + amountToScroll) % mSelectorElementSize;
            int overshootAdjustment = mInitialScrollOffset - futureScrollOffset;
            if (overshootAdjustment != 0) {
                if (Math.abs(overshootAdjustment) > mSelectorElementSize / 2) {
                    if (overshootAdjustment > 0) {
                        overshootAdjustment -= mSelectorElementSize;
                    } else {
                        overshootAdjustment += mSelectorElementSize;
                    }
                }
                amountToScroll += overshootAdjustment;
                scrollBy(0, amountToScroll);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        final int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                removeAllCallbacks();
                // Make sure we support flinging inside scrollables.
                getParent().requestDisallowInterceptTouchEvent(true);

                if (isHorizontalMode()) {
                    mLastDownOrMoveEventX = mLastDownEventX = event.getX();
                    if (!mFlingScroller.isFinished()) {
                        mFlingScroller.forceFinished(true);
                        mAdjustScroller.forceFinished(true);
                        onScrollStateChange(OnScrollListener.SCROLL_STATE_IDLE);
                    } else if (!mAdjustScroller.isFinished()) {
                        mFlingScroller.forceFinished(true);
                        mAdjustScroller.forceFinished(true);
                    } else if (mLastDownEventX >= mLeftDividerLeft
                            && mLastDownEventX <= mRightDividerRight) {
                        if (mOnClickListener != null) {
                            mOnClickListener.onClick(this);
                        }
                    } else if (mLastDownEventX < mLeftDividerLeft) {
                        postChangeCurrentByOneFromLongPress(false);
                    } else if (mLastDownEventX > mRightDividerRight) {
                        postChangeCurrentByOneFromLongPress(true);
                    }
                } else {
                    mLastDownOrMoveEventY = mLastDownEventY = event.getY();
                    if (!mFlingScroller.isFinished()) {
                        mFlingScroller.forceFinished(true);
                        mAdjustScroller.forceFinished(true);
                        onScrollStateChange(OnScrollListener.SCROLL_STATE_IDLE);
                    } else if (!mAdjustScroller.isFinished()) {
                        mFlingScroller.forceFinished(true);
                        mAdjustScroller.forceFinished(true);
                    } else if (mLastDownEventY >= mTopDividerTop
                            && mLastDownEventY <= mBottomDividerBottom) {
                        if (mOnClickListener != null) {
                            mOnClickListener.onClick(this);
                        }
                    } else if (mLastDownEventY < mTopDividerTop) {
                        postChangeCurrentByOneFromLongPress(false);
                    } else if (mLastDownEventY > mBottomDividerBottom) {
                        postChangeCurrentByOneFromLongPress(true);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        if (!isScrollerEnabled()) {
            return false;
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                if (isHorizontalMode()) {
                    float currentMoveX = event.getX();
                    if (mScrollState != OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        int deltaDownX = (int) Math.abs(currentMoveX - mLastDownEventX);
                        if (deltaDownX > mTouchSlop) {
                            removeAllCallbacks();
                            onScrollStateChange(OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
                        }
                    } else {
                        int deltaMoveX = (int) ((currentMoveX - mLastDownOrMoveEventX));
                        scrollBy(deltaMoveX, 0);
                        invalidate();
                    }
                    mLastDownOrMoveEventX = currentMoveX;
                } else {
                    float currentMoveY = event.getY();
                    if (mScrollState != OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        int deltaDownY = (int) Math.abs(currentMoveY - mLastDownEventY);
                        if (deltaDownY > mTouchSlop) {
                            removeAllCallbacks();
                            onScrollStateChange(OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
                        }
                    } else {
                        int deltaMoveY = (int) ((currentMoveY - mLastDownOrMoveEventY));
                        scrollBy(0, deltaMoveY);
                        invalidate();
                    }
                    mLastDownOrMoveEventY = currentMoveY;
                }
            }
            break;
            case MotionEvent.ACTION_UP: {
                removeChangeCurrentByOneFromLongPress();
                VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
                if (isHorizontalMode()) {
                    int initialVelocity = (int) velocityTracker.getXVelocity();
                    if (Math.abs(initialVelocity) > mMinimumFlingVelocity) {
                        fling(initialVelocity);
                        onScrollStateChange(OnScrollListener.SCROLL_STATE_FLING);
                    } else {
                        int eventX = (int) event.getX();
                        int deltaMoveX = (int) Math.abs(eventX - mLastDownEventX);
                        if (deltaMoveX <= mTouchSlop) {
                            int selectorIndexOffset = (eventX / mSelectorElementSize)
                                    - mWheelMiddleItemIndex;
                            if (selectorIndexOffset > 0) {
                                changeValueByOne(true);
                            } else if (selectorIndexOffset < 0) {
                                changeValueByOne(false);
                            } else {
                                ensureScrollWheelAdjusted();
                            }
                        } else {
                            ensureScrollWheelAdjusted();
                        }
                        onScrollStateChange(OnScrollListener.SCROLL_STATE_IDLE);
                    }
                } else {
                    int initialVelocity = (int) velocityTracker.getYVelocity();
                    if (Math.abs(initialVelocity) > mMinimumFlingVelocity) {
                        fling(initialVelocity);
                        onScrollStateChange(OnScrollListener.SCROLL_STATE_FLING);
                    } else {
                        int eventY = (int) event.getY();
                        int deltaMoveY = (int) Math.abs(eventY - mLastDownEventY);
                        if (deltaMoveY <= mTouchSlop) {
                            int selectorIndexOffset = (eventY / mSelectorElementSize)
                                    - mWheelMiddleItemIndex;
                            if (selectorIndexOffset > 0) {
                                changeValueByOne(true);
                            } else if (selectorIndexOffset < 0) {
                                changeValueByOne(false);
                            } else {
                                ensureScrollWheelAdjusted();
                            }
                        } else {
                            ensureScrollWheelAdjusted();
                        }
                        onScrollStateChange(OnScrollListener.SCROLL_STATE_IDLE);
                    }
                }
                mVelocityTracker.recycle();
                mVelocityTracker = null;
            }
            break;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                removeAllCallbacks();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        final int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                removeAllCallbacks();
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_UP:
                switch (event.getAction()) {
                    case KeyEvent.ACTION_DOWN:
                        if (mWrapSelectorWheel || ((keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
                                ? getValue() < getMaxValue() : getValue() > getMinValue())) {
                            requestFocus();
                            mLastHandledDownDpadKeyCode = keyCode;
                            removeAllCallbacks();
                            if (mFlingScroller.isFinished()) {
                                changeValueByOne(keyCode == KeyEvent.KEYCODE_DPAD_DOWN);
                            }
                            return true;
                        }
                        break;
                    case KeyEvent.ACTION_UP:
                        if (mLastHandledDownDpadKeyCode == keyCode) {
                            mLastHandledDownDpadKeyCode = -1;
                            return true;
                        }
                        break;
                }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        final int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                removeAllCallbacks();
                break;
        }
        return super.dispatchTrackballEvent(event);
    }

    @Override
    public void computeScroll() {
        if (!isScrollerEnabled()) {
            return;
        }

        Scroller scroller = mFlingScroller;
        if (scroller.isFinished()) {
            scroller = mAdjustScroller;
            if (scroller.isFinished()) {
                return;
            }
        }
        scroller.computeScrollOffset();
        if (isHorizontalMode()) {
            int currentScrollerX = scroller.getCurrX();
            if (mPreviousScrollerX == 0) {
                mPreviousScrollerX = scroller.getStartX();
            }
            scrollBy(currentScrollerX - mPreviousScrollerX, 0);
            mPreviousScrollerX = currentScrollerX;
        } else {
            int currentScrollerY = scroller.getCurrY();
            if (mPreviousScrollerY == 0) {
                mPreviousScrollerY = scroller.getStartY();
            }
            scrollBy(0, currentScrollerY - mPreviousScrollerY);
            mPreviousScrollerY = currentScrollerY;
        }
        if (scroller.isFinished()) {
            onScrollerFinished(scroller);
        } else {
            postInvalidate();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mSelectedText.setEnabled(enabled);
    }

    @Override
    public void scrollBy(int x, int y) {
        if (!isScrollerEnabled()) {
            return;
        }
        int[] selectorIndices = getSelectorIndices();
        int startScrollOffset = mCurrentScrollOffset;
        int gap;
        if (isHorizontalMode()) {
            if (isAscendingOrder()) {
                if (!mWrapSelectorWheel && x > 0
                        && selectorIndices[mWheelMiddleItemIndex] <= mMinValue) {
                    mCurrentScrollOffset = mInitialScrollOffset;
                    return;
                }
                if (!mWrapSelectorWheel && x < 0
                        && selectorIndices[mWheelMiddleItemIndex] >= mMaxValue) {
                    mCurrentScrollOffset = mInitialScrollOffset;
                    return;
                }
            } else {
                if (!mWrapSelectorWheel && x > 0
                        && selectorIndices[mWheelMiddleItemIndex] >= mMaxValue) {
                    mCurrentScrollOffset = mInitialScrollOffset;
                    return;
                }
                if (!mWrapSelectorWheel && x < 0
                        && selectorIndices[mWheelMiddleItemIndex] <= mMinValue) {
                    mCurrentScrollOffset = mInitialScrollOffset;
                    return;
                }
            }

            mCurrentScrollOffset += x;
            gap = mSelectorTextGapWidth;
        } else {
            if (isAscendingOrder()) {
                if (!mWrapSelectorWheel && y > 0
                        && selectorIndices[mWheelMiddleItemIndex] <= mMinValue) {
                    mCurrentScrollOffset = mInitialScrollOffset;
                    return;
                }
                if (!mWrapSelectorWheel && y < 0
                        && selectorIndices[mWheelMiddleItemIndex] >= mMaxValue) {
                    mCurrentScrollOffset = mInitialScrollOffset;
                    return;
                }
            } else {
                if (!mWrapSelectorWheel && y > 0
                        && selectorIndices[mWheelMiddleItemIndex] >= mMaxValue) {
                    mCurrentScrollOffset = mInitialScrollOffset;
                    return;
                }
                if (!mWrapSelectorWheel && y < 0
                        && selectorIndices[mWheelMiddleItemIndex] <= mMinValue) {
                    mCurrentScrollOffset = mInitialScrollOffset;
                    return;
                }
            }

            mCurrentScrollOffset += y;
            gap = mSelectorTextGapHeight;
        }

        while (mCurrentScrollOffset - mInitialScrollOffset > gap) {
            mCurrentScrollOffset -= mSelectorElementSize;
            if (isAscendingOrder()) {
                decrementSelectorIndices(selectorIndices);
            } else {
                incrementSelectorIndices(selectorIndices);
            }
            setValueInternal(selectorIndices[mWheelMiddleItemIndex], true);
            if (!mWrapSelectorWheel && selectorIndices[mWheelMiddleItemIndex] < mMinValue) {
                mCurrentScrollOffset = mInitialScrollOffset;
            }
        }
        while (mCurrentScrollOffset - mInitialScrollOffset < -gap) {
            mCurrentScrollOffset += mSelectorElementSize;
            if (isAscendingOrder()) {
                incrementSelectorIndices(selectorIndices);
            } else {
                decrementSelectorIndices(selectorIndices);
            }
            setValueInternal(selectorIndices[mWheelMiddleItemIndex], true);
            if (!mWrapSelectorWheel && selectorIndices[mWheelMiddleItemIndex] > mMaxValue) {
                mCurrentScrollOffset = mInitialScrollOffset;
            }
        }

        if (startScrollOffset != mCurrentScrollOffset) {
            if (isHorizontalMode()) {
                onScrollChanged(mCurrentScrollOffset, 0, startScrollOffset, 0);
            } else {
                onScrollChanged(0, mCurrentScrollOffset, 0, startScrollOffset);
            }
        }
    }

    private int computeScrollOffset(boolean isHorizontalMode) {
        return isHorizontalMode ? mCurrentScrollOffset : 0;
    }

    private int computeScrollRange(boolean isHorizontalMode) {
        return isHorizontalMode ? (mMaxValue - mMinValue + 1) * mSelectorElementSize : 0;
    }

    private int computeScrollExtent(boolean isHorizontalMode) {
        return isHorizontalMode ? getWidth() : getHeight();
    }

    @Override
    protected int computeHorizontalScrollOffset() {
        return computeScrollOffset(isHorizontalMode());
    }

    @Override
    protected int computeHorizontalScrollRange() {
        return computeScrollRange(isHorizontalMode());
    }

    @Override
    protected int computeHorizontalScrollExtent() {
        return computeScrollExtent(isHorizontalMode());
    }

    @Override
    protected int computeVerticalScrollOffset() {
        return computeScrollOffset(!isHorizontalMode());
    }

    @Override
    protected int computeVerticalScrollRange() {
        return computeScrollRange(!isHorizontalMode());
    }

    @Override
    protected int computeVerticalScrollExtent() {
        return computeScrollExtent(isHorizontalMode());
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mNumberFormatter = NumberFormat.getInstance();
    }

    /**
     * Set locale other than default locale
     */
    public void setLocale(Locale locale) {
        if (mLocale != null && mLocale.equals(locale)) {
            return;
        }
        this.mLocale = locale;
        sTwoDigitFormatter.setLocale(mLocale);
        invalidate();
    }

    /**
     * 选中框点击监听
     *
     * @param onClickListener The listener.
     */
    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    /**
     * 值变化监听
     *
     * @param onValueChangedListener The listener.
     */
    public void setOnValueChangedListener(OnValueChangeListener onValueChangedListener) {
        mOnValueChangeListener = onValueChangedListener;
        notifyChange(mValue, mValue);
    }

    /**
     * 滑动状态监听
     *
     * @param onScrollListener The listener.
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    /**
     * Set the formatter to be used for formatting the current value.
     * <p>
     * Note: If you have provided alternative values for the values this
     * formatter is never invoked.
     * </p>
     *
     * @param formatter The formatter object. If formatter is <code>null</code>,
     *             will be used.
     *@see #setDisplayedValues
     */
    public void setFormatter(Formatter formatter) {
        if (formatter == mFormatter) {
            return;
        }
        mFormatter = formatter;
        initializeSelectorWheelIndices();
        updateInputTextView();
    }

    /**
     * Set the current value for the number picker.
     * <p>
     * If the argument is less than the {@link NumberPicker#getMinValue()} and
     * {@link NumberPicker#getWrapSelectorWheel()} is <code>false</code> the
     * current value is set to the {@link NumberPicker#getMinValue()} value.
     * </p>
     * <p>
     * If the argument is less than the {@link NumberPicker#getMinValue()} and
     * {@link NumberPicker#getWrapSelectorWheel()} is <code>true</code> the
     * current value is set to the {@link NumberPicker#getMaxValue()} value.
     * </p>
     * <p>
     * If the argument is less than the {@link NumberPicker#getMaxValue()} and
     * {@link NumberPicker#getWrapSelectorWheel()} is <code>false</code> the
     * current value is set to the {@link NumberPicker#getMaxValue()} value.
     * </p>
     * <p>
     * If the argument is less than the {@link NumberPicker#getMaxValue()} and
     * {@link NumberPicker#getWrapSelectorWheel()} is <code>true</code> the
     * current value is set to the {@link NumberPicker#getMinValue()} value.
     * </p>
     *
     * @param value The current value.
     * @see #setWrapSelectorWheel(boolean)
     * @see #setMinValue(int)
     * @see #setMaxValue(int)
     */
    public void setValue(int value) {
        setValueInternal(value, true);
    }

    private float getMaxTextSize() {
        return Math.max(mTextSize, mSelectedTextSize);
    }

    private float getPaintCenterY(Paint.FontMetrics fontMetrics) {
        if (fontMetrics == null) {
            return 0;
        }
        return Math.abs(fontMetrics.top + fontMetrics.bottom) / 2;
    }

    /**
     * Computes the max width if no such specified as an attribute.
     */
    private void tryComputeMaxWidth() {
        if (!mComputeMaxWidth) {
            return;
        }
        mSelectorWheelPaint.setTextSize(getMaxTextSize());
        int maxTextWidth = 0;
        if (mDisplayedValues == null) {
            float maxDigitWidth = 0;
            for (int i = 0; i <= 9; i++) {
                final float digitWidth = mSelectorWheelPaint.measureText(formatNumber(i));
                if (digitWidth > maxDigitWidth) {
                    maxDigitWidth = digitWidth;
                }
            }
            int numberOfDigits = 0;
            int current = mMaxValue;
            while (current > 0) {
                numberOfDigits++;
                current = current / 10;
            }
            maxTextWidth = (int) (numberOfDigits * maxDigitWidth);
        } else {
            final int valueCount = mDisplayedValues.length;
            for (int i = 0; i < valueCount; i++) {
                final float textWidth = mSelectorWheelPaint.measureText(mDisplayedValues[i]);
                if (textWidth > maxTextWidth) {
                    maxTextWidth = (int) textWidth;
                }
            }
        }
        maxTextWidth += mSelectedText.getPaddingLeft() + mSelectedText.getPaddingRight();
        if (mMaxWidth != maxTextWidth) {
            if (maxTextWidth > mMinWidth) {
                mMaxWidth = maxTextWidth;
            } else {
                mMaxWidth = mMinWidth;
            }
            invalidate();
        }
    }

    /**
     * Gets whether the selector wheel wraps when reaching the min/max value.
     *
     * @return True if the selector wheel wraps.
     *
     * @see #getMinValue()
     * @see #getMaxValue()
     */
    public boolean getWrapSelectorWheel() {
        return mWrapSelectorWheel;
    }

    /**
     * Sets whether the selector wheel shown during flinging/scrolling should
     * wrap around the {@link NumberPicker#getMinValue()} and
     * {@link NumberPicker#getMaxValue()} values.
     * <p>
     * By default if the range (max - min) is more than the number of items shown
     * on the selector wheel the selector wheel wrapping is enabled.
     * </p>
     * <p>
     * <strong>Note:</strong> If the number of items, i.e. the range (
     * {@link #getMaxValue()} - {@link #getMinValue()}) is less than
     * the number of items shown on the selector wheel, the selector wheel will
     * not wrap. Hence, in such a case calling this method is a NOP.
     * </p>
     *
     * @param wrapSelectorWheel Whether to wrap.
     */
    public void setWrapSelectorWheel(boolean wrapSelectorWheel) {
        mWrapSelectorWheelPreferred = wrapSelectorWheel;
        updateWrapSelectorWheel();
    }

    /**
     * Whether or not the selector wheel should be wrapped is determined by user choice and whether
     * the choice is allowed. The former comes from {@link #setWrapSelectorWheel(boolean)}, the
     * latter is calculated based on min & max value set vs selector's visual length. Therefore,
     * this method should be called any time any of the 3 values (i.e. user choice, min and max
     * value) gets updated.
     */
    private void updateWrapSelectorWheel() {
        mWrapSelectorWheel = isWrappingAllowed() && mWrapSelectorWheelPreferred;
    }

    private boolean isWrappingAllowed() {
        return mMaxValue - mMinValue >= mSelectorIndices.length - 1;
    }

    /**
     * Sets the speed at which the numbers be incremented and decremented when
     * the up and down buttons are long pressed respectively.
     * <p>
     * The default value is 300 ms.
     * </p>
     *
     * @param intervalMillis The speed (in milliseconds) at which the numbers
     *            will be incremented and decremented.
     */
    public void setOnLongPressUpdateInterval(long intervalMillis) {
        mLongPressUpdateInterval = intervalMillis;
    }

    /**
     * Returns the value of the picker.
     *
     * @return The value.
     */
    public int getValue() {
        return mValue;
    }

    /**
     * Returns the min value of the picker.
     *
     * @return The min value
     */
    public int getMinValue() {
        return mMinValue;
    }

    /**
     * Sets the min value of the picker.
     *
     * @param minValue The min value inclusive.
     *
     * <strong>Note:</strong> The length of the displayed values array
     * set via {@link #setDisplayedValues} must be equal to the
     * range of selectable numbers which is equal to
     * {@link #getMaxValue()} - {@link #getMinValue()} + 1.
     */
    public void setMinValue(int minValue) {
//        if (minValue < 0) {
//            throw new IllegalArgumentException("minValue must be >= 0");
//        }
        mMinValue = minValue;
        if (mValue < minValue) {
            notifyChange(mValue, mMinValue);
            mValue = mMinValue;
        }
        setWrapSelectorWheel(isWrappingAllowed());
        initializeSelectorWheelIndices();
        updateInputTextView();
        tryComputeMaxWidth();
        invalidate();

    }

    /**
     * Returns the max value of the picker.
     *
     * @return The max value.
     */
    public int getMaxValue() {
        return mMaxValue;
    }

    /**
     * Sets the max value of the picker.
     *
     * @param maxValue The max value inclusive.
     *
     * <strong>Note:</strong> The length of the displayed values array
     * set via {@link #setDisplayedValues} must be equal to the
     * range of selectable numbers which is equal to
     * {@link #getMaxValue()} - {@link #getMinValue()} + 1.
     */
    public void setMaxValue(int maxValue) {
        if (maxValue < 0) {
            throw new IllegalArgumentException("maxValue must be >= 0");
        }
        mMaxValue = maxValue;
        if (mValue > maxValue) {
            notifyChange(mValue, mMaxValue);
            mValue = mMaxValue;
        }
        updateWrapSelectorWheel();
        initializeSelectorWheelIndices();
        updateInputTextView();
        tryComputeMaxWidth();
        postInvalidate();
    }

    /**
     * Gets the values to be displayed instead of string values.
     *
     * @return The displayed values.
     */
    public String[] getDisplayedValues() {
        return mDisplayedValues;
    }

    /**
     * Sets the values to be displayed.
     *
     * @param displayedValues The displayed values.
     *
     * <strong>Note:</strong> The length of the displayed values array
     * must be equal to the range of selectable numbers which is equal to
     * {@link #getMaxValue()} - {@link #getMinValue()} + 1.
     */
    public void setDisplayedValues(String[] displayedValues) {
        if (mDisplayedValues == displayedValues) {
            return;
        }
        mDisplayedValues = displayedValues;
        if (mDisplayedValues != null) {
            // Allow text entry rather than strictly numeric entry.
            mSelectedText.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE
                    | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        } else {
            mSelectedText.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        }
        updateInputTextView();
        initializeSelectorWheelIndices();
        tryComputeMaxWidth();
    }

    private float getFadingEdgeStrength(boolean isHorizontalMode) {
        return isHorizontalMode && mFadingEdgeEnabled ? mFadingEdgeStrength : 0;
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        return getFadingEdgeStrength(!isHorizontalMode());
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        return getFadingEdgeStrength(!isHorizontalMode());
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
        return getFadingEdgeStrength(isHorizontalMode());
    }

    @Override
    protected float getRightFadingEdgeStrength() {
        return getFadingEdgeStrength(isHorizontalMode());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeAllCallbacks();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        final Drawable selectionDivider = mDividerDrawable;
        if (selectionDivider != null && selectionDivider.isStateful()
                && selectionDivider.setState(getDrawableState())) {
            invalidateDrawable(selectionDivider);
        }
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (mDividerDrawable != null) {
            mDividerDrawable.jumpToCurrentState();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // save canvas
        canvas.save();

        final boolean showSelectorWheel = mHideWheelUntilFocused ? hasFocus() : true;
        float x, y;
        if (isHorizontalMode()) {
            x = mCurrentScrollOffset;
            y = mSelectedText.getBaseline() + mSelectedText.getTop();
            if (mRealWheelItemCount < DEFAULT_WHEEL_ITEM_COUNT) {
                canvas.clipRect(mLeftDividerLeft, 0, mRightDividerRight, getBottom());
            }
        } else {
            x = (getRight() - getLeft()) / 2;
            y = mCurrentScrollOffset;
            if (mRealWheelItemCount < DEFAULT_WHEEL_ITEM_COUNT) {
                canvas.clipRect(0, mTopDividerTop, getRight(), mBottomDividerBottom);
            }
        }

        // draw the selector wheel
        int[] selectorIndices = getSelectorIndices();
        for (int i = 0; i < selectorIndices.length; i++) {
            if (i == mWheelMiddleItemIndex) {
                mSelectorWheelPaint.setTextAlign(Paint.Align.values()[mSelectedTextAlign]);
                mSelectorWheelPaint.setTextSize(mSelectedTextSize);
                mSelectorWheelPaint.setColor(mSelectedTextColor);
                mSelectorWheelPaint.setStrikeThruText(mSelectedTextStrikeThru);
                mSelectorWheelPaint.setUnderlineText(mSelectedTextUnderline);
            } else {
                mSelectorWheelPaint.setTextAlign(Paint.Align.values()[mTextAlign]);
                mSelectorWheelPaint.setTextSize(mTextSize);
                mSelectorWheelPaint.setColor(mTextColor);
                mSelectorWheelPaint.setStrikeThruText(mTextStrikeThru);
                mSelectorWheelPaint.setUnderlineText(mTextUnderline);
            }

            int selectorIndex = selectorIndices[isAscendingOrder() ? i : selectorIndices.length - i - 1];
            String scrollSelectorValue = mSelectorIndexToStringCache.get(selectorIndex);
            // Do not draw the middle item if input is visible since the input
            // is shown only if the wheel is static and it covers the middle
            // item. Otherwise, if the user starts editing the text via the
            // IME he may see a dimmed version of the old value intermixed
            // with the new one.
            if ((showSelectorWheel && i != mWheelMiddleItemIndex)
                    || (i == mWheelMiddleItemIndex && mSelectedText.getVisibility() != VISIBLE)) {
                float textY = y;
                if (!isHorizontalMode()) {
                    textY += getPaintCenterY(mSelectorWheelPaint.getFontMetrics());
                }
                drawText(scrollSelectorValue, x, textY, mSelectorWheelPaint, canvas);
            }

            if (isHorizontalMode()) {
                x += mSelectorElementSize;
            } else {
                y += mSelectorElementSize;
            }
        }

        // restore canvas
        canvas.restore();

        // draw the dividers
        if (showSelectorWheel && mDividerDrawable != null) {
            if (isHorizontalMode()) {
                final int bottom = getBottom();

                // draw the left divider
                final int leftOfLeftDivider = mLeftDividerLeft;
                final int rightOfLeftDivider = leftOfLeftDivider + mDividerThickness;
                mDividerDrawable.setBounds(leftOfLeftDivider, 0, rightOfLeftDivider, bottom);
                mDividerDrawable.draw(canvas);

                // draw the right divider
                final int rightOfRightDivider = mRightDividerRight;
                final int leftOfRightDivider = rightOfRightDivider - mDividerThickness;
                mDividerDrawable.setBounds(leftOfRightDivider, 0, rightOfRightDivider, bottom);
                mDividerDrawable.draw(canvas);
            } else {
                final int right = getRight();

                // draw the top divider
                final int topOfTopDivider = mTopDividerTop;
                final int bottomOfTopDivider = topOfTopDivider + mDividerThickness;
                mDividerDrawable.setBounds(0, topOfTopDivider, right, bottomOfTopDivider);
                mDividerDrawable.draw(canvas);

                // draw the bottom divider
                final int bottomOfBottomDivider = mBottomDividerBottom;
                final int topOfBottomDivider = bottomOfBottomDivider - mDividerThickness;
                mDividerDrawable.setBounds(0, topOfBottomDivider, right, bottomOfBottomDivider);
                mDividerDrawable.draw(canvas);
            }
        }
    }

    private void drawText(String text, float x, float y, Paint paint, Canvas canvas) {
        if (text.contains("\n")) {
            final String[] lines = text.split("\n");
            final float height = Math.abs(paint.descent() + paint.ascent())
                    * mLineSpacingMultiplier;
            final float diff = (lines.length - 1) * height / 2;
            y -= diff;
            for (String line : lines) {
                canvas.drawText(line, x, y, paint);
                y += height;
            }
        } else {
            canvas.drawText(text, x, y, paint);
        }
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(NumberPicker.class.getName());
        event.setScrollable(isScrollerEnabled());
        final int scroll = (mMinValue + mValue) * mSelectorElementSize;
        final int maxScroll = (mMaxValue - mMinValue) * mSelectorElementSize;
        if (isHorizontalMode()) {
            event.setScrollX(scroll);
            event.setMaxScrollX(maxScroll);
        } else {
            event.setScrollY(scroll);
            event.setMaxScrollY(maxScroll);
        }
    }

    /**
     * Makes a measure spec that tries greedily to use the max value.
     *
     * @param measureSpec The measure spec.
     * @param maxSize The max value for the size.
     * @return A measure spec greedily imposing the max size.
     */
    private int makeMeasureSpec(int measureSpec, int maxSize) {
        if (maxSize == SIZE_UNSPECIFIED) {
            return measureSpec;
        }
        final int size = MeasureSpec.getSize(measureSpec);
        final int mode = MeasureSpec.getMode(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                return measureSpec;
            case MeasureSpec.AT_MOST:
                return MeasureSpec.makeMeasureSpec(Math.min(size, maxSize), MeasureSpec.EXACTLY);
            case MeasureSpec.UNSPECIFIED:
                return MeasureSpec.makeMeasureSpec(maxSize, MeasureSpec.EXACTLY);
            default:
                throw new IllegalArgumentException("Unknown measure mode: " + mode);
        }
    }

    /**
     * Utility to reconcile a desired size and state, with constraints imposed
     * by a MeasureSpec. Tries to respect the min size, unless a different size
     * is imposed by the constraints.
     *
     * @param minSize The minimal desired size.
     * @param measuredSize The currently measured size.
     * @param measureSpec The current measure spec.
     * @return The resolved size and state.
     */
    private int resolveSizeAndStateRespectingMinSize(int minSize, int measuredSize,
                                                     int measureSpec) {
        if (minSize != SIZE_UNSPECIFIED) {
            final int desiredWidth = Math.max(minSize, measuredSize);
            return resolveSizeAndState(desiredWidth, measureSpec, 0);
        } else {
            return measuredSize;
        }
    }

    /**
     * Utility to reconcile a desired size and state, with constraints imposed
     * by a MeasureSpec.  Will take the desired size, unless a different size
     * is imposed by the constraints.  The returned value is a compound integer,
     * with the resolved size in the {@link #MEASURED_SIZE_MASK} bits and
     * optionally the bit {@link #MEASURED_STATE_TOO_SMALL} set if the resulting
     * size is smaller than the size the view wants to be.
     *
     * @param size How big the view wants to be
     * @param measureSpec Constraints imposed by the parent
     * @return Size information bit mask as defined by
     * {@link #MEASURED_SIZE_MASK} and {@link #MEASURED_STATE_TOO_SMALL}.
     */
    public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize =  MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                if (specSize < size) {
                    result = specSize | MEASURED_STATE_TOO_SMALL;
                } else {
                    result = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result | (childMeasuredState&MEASURED_STATE_MASK);
    }

    /**
     * Resets the selector indices and clear the cached string representation of
     * these indices.
     */
    private void initializeSelectorWheelIndices() {
        mSelectorIndexToStringCache.clear();
        int[] selectorIndices = getSelectorIndices();
        int current = getValue();
        for (int i = 0; i < mSelectorIndices.length; i++) {
            int selectorIndex = current + (i - mWheelMiddleItemIndex);
            if (mWrapSelectorWheel) {
                selectorIndex = getWrappedSelectorIndex(selectorIndex);
            }
            selectorIndices[i] = selectorIndex;
            ensureCachedScrollSelectorValue(selectorIndices[i]);
        }
    }

    /**
     * Sets the current value of this NumberPicker.
     *
     * @param current The new value of the NumberPicker.
     * @param notifyChange Whether to notify if the current value changed.
     */
    private void setValueInternal(int current, boolean notifyChange) {
        if (mValue == current) {
            return;
        }
        // Wrap around the values if we go past the start or end
        if (mWrapSelectorWheel) {
            current = getWrappedSelectorIndex(current);
        } else {
            current = Math.max(current, mMinValue);
            current = Math.min(current, mMaxValue);
        }
        int previous = mValue;
        mValue = current;
        // If we're flinging, we'll update the text view at the end when it becomes visible
        if (mScrollState != OnScrollListener.SCROLL_STATE_FLING) {
            updateInputTextView();
        }
        if (notifyChange) {
            notifyChange(previous, current);
        }
        initializeSelectorWheelIndices();
        updateAccessibilityDescription();
        invalidate();
    }

    /**
     * Updates the accessibility values of the view,
     * to the currently selected value
     */
    private void updateAccessibilityDescription() {
        this.setContentDescription(String.valueOf(getValue()));
    }

    /**
     * Changes the current value by one which is increment or
     * decrement based on the passes argument.
     * decrement the current value.
     *
     * @param increment True to increment, false to decrement.
     */
    private void changeValueByOne(boolean increment) {
        if (!moveToFinalScrollerPosition(mFlingScroller)) {
            moveToFinalScrollerPosition(mAdjustScroller);
        }
        smoothScroll(increment, 1);
    }

    /**
     * Starts a smooth scroll to wheel position.
     *
     * @param position The wheel position to scroll to.
     */
    public void smoothScrollToPosition(int position) {
        final int currentPosition = getSelectorIndices()[mWheelMiddleItemIndex];
        if (currentPosition == position) {
            return;
        }
        smoothScroll(position > currentPosition, Math.abs(position - currentPosition));
    }

    /**
     * Starts a smooth scroll
     *
     * @param increment True to increment, false to decrement.
     * @param steps The steps to scroll.
     */
    public void smoothScroll(boolean increment, int steps) {
        if (isHorizontalMode()) {
            mPreviousScrollerX = 0;
            if (increment) {
                mFlingScroller.startScroll(0, 0, -mSelectorElementSize * steps, 0, SNAP_SCROLL_DURATION);
            } else {
                mFlingScroller.startScroll(0, 0, mSelectorElementSize * steps, 0, SNAP_SCROLL_DURATION);
            }
        } else {
            mPreviousScrollerY = 0;
            if (increment) {
                mFlingScroller.startScroll(0, 0, 0, -mSelectorElementSize * steps, SNAP_SCROLL_DURATION);
            } else {
                mFlingScroller.startScroll(0, 0, 0, mSelectorElementSize * steps, SNAP_SCROLL_DURATION);
            }
        }
        invalidate();
    }

    private void initializeSelectorWheel() {
        initializeSelectorWheelIndices();
        int[] selectorIndices = getSelectorIndices();
        int totalTextSize = (selectorIndices.length - 1) * (int) mTextSize
                + (int) mSelectedTextSize;
        float textGapCount = selectorIndices.length;
        if (isHorizontalMode()) {
            float totalTextGapWidth = (getRight() - getLeft()) - totalTextSize;
            mSelectorTextGapWidth = (int) (totalTextGapWidth / textGapCount);
            mSelectorElementSize = (int) getMaxTextSize() + mSelectorTextGapWidth;
            mInitialScrollOffset = (int) mSelectedTextCenterX
                    - (mSelectorElementSize * mWheelMiddleItemIndex);
        } else {
            float totalTextGapHeight = (getBottom() - getTop()) - totalTextSize;
            mSelectorTextGapHeight = (int) (totalTextGapHeight / textGapCount);
            mSelectorElementSize = (int) getMaxTextSize() + mSelectorTextGapHeight;
            mInitialScrollOffset = (int) mSelectedTextCenterY
                    - (mSelectorElementSize * mWheelMiddleItemIndex);
        }
        mCurrentScrollOffset = mInitialScrollOffset;
        updateInputTextView();
    }

    private void initializeFadingEdges() {
        if (isHorizontalMode()) {
            setHorizontalFadingEdgeEnabled(true);
            setFadingEdgeLength((getRight() - getLeft() - (int) mTextSize) / 2);
        } else {
            setVerticalFadingEdgeEnabled(true);
            setFadingEdgeLength((getBottom() - getTop() - (int) mTextSize) / 2);
        }
    }

    /**
     * Callback invoked upon completion of a given <code>scroller</code>.
     */
    private void onScrollerFinished(Scroller scroller) {
        if (scroller == mFlingScroller) {
            ensureScrollWheelAdjusted();
            updateInputTextView();
            onScrollStateChange(OnScrollListener.SCROLL_STATE_IDLE);
        } else if (mScrollState != OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            updateInputTextView();
        }
    }

    /**
     * Handles transition to a given <code>scrollState</code>
     */
    private void onScrollStateChange(int scrollState) {
        if (mScrollState == scrollState) {
            return;
        }
        mScrollState = scrollState;
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChange(this, scrollState);
        }
    }

    /**
     * Flings the selector with the given <code>velocity</code>.
     */
    private void fling(int velocity) {
        if (isHorizontalMode()) {
            mPreviousScrollerX = 0;
            if (velocity > 0) {
                mFlingScroller.fling(0, 0, velocity, 0, 0, Integer.MAX_VALUE, 0, 0);
            } else {
                mFlingScroller.fling(Integer.MAX_VALUE, 0, velocity, 0, 0, Integer.MAX_VALUE, 0, 0);
            }
        } else {
            mPreviousScrollerY = 0;
            if (velocity > 0) {
                mFlingScroller.fling(0, 0, 0, velocity, 0, 0, 0, Integer.MAX_VALUE);
            } else {
                mFlingScroller.fling(0, Integer.MAX_VALUE, 0, velocity, 0, 0, 0, Integer.MAX_VALUE);
            }
        }

        invalidate();
    }

    /**
     * @return The wrapped index <code>selectorIndex</code> value.
     */
    private int getWrappedSelectorIndex(int selectorIndex) {
        if (selectorIndex > mMaxValue) {
            return mMinValue + (selectorIndex - mMaxValue) % (mMaxValue - mMinValue) - 1;
        } else if (selectorIndex < mMinValue) {
            return mMaxValue - (mMinValue - selectorIndex) % (mMaxValue - mMinValue) + 1;
        }
        return selectorIndex;
    }

    private int[] getSelectorIndices() {
        return mSelectorIndices;
    }

    /**
     * Increments the <code>selectorIndices</code> whose string representations
     * will be displayed in the selector.
     */
    private void incrementSelectorIndices(int[] selectorIndices) {
        for (int i = 0; i < selectorIndices.length - 1; i++) {
            selectorIndices[i] = selectorIndices[i + 1];
        }
        int nextScrollSelectorIndex = selectorIndices[selectorIndices.length - 2] + 1;
        if (mWrapSelectorWheel && nextScrollSelectorIndex > mMaxValue) {
            nextScrollSelectorIndex = mMinValue;
        }
        selectorIndices[selectorIndices.length - 1] = nextScrollSelectorIndex;
        ensureCachedScrollSelectorValue(nextScrollSelectorIndex);
    }

    /**
     * Decrements the <code>selectorIndices</code> whose string representations
     * will be displayed in the selector.
     */
    private void decrementSelectorIndices(int[] selectorIndices) {
        for (int i = selectorIndices.length - 1; i > 0; i--) {
            selectorIndices[i] = selectorIndices[i - 1];
        }
        int nextScrollSelectorIndex = selectorIndices[1] - 1;
        if (mWrapSelectorWheel && nextScrollSelectorIndex < mMinValue) {
            nextScrollSelectorIndex = mMaxValue;
        }
        selectorIndices[0] = nextScrollSelectorIndex;
        ensureCachedScrollSelectorValue(nextScrollSelectorIndex);
    }

    /**
     * Ensures we have a cached string representation of the given <code>
     * selectorIndex</code> to avoid multiple instantiations of the same string.
     */
    private void ensureCachedScrollSelectorValue(int selectorIndex) {
        SparseArray<String> cache = mSelectorIndexToStringCache;
        String scrollSelectorValue = cache.get(selectorIndex);
        if (scrollSelectorValue != null) {
            return;
        }
        if (selectorIndex < mMinValue || selectorIndex > mMaxValue) {
            scrollSelectorValue = "";
        } else {
            if (mDisplayedValues != null) {
                int displayedValueIndex = selectorIndex - mMinValue;
                scrollSelectorValue = mDisplayedValues[displayedValueIndex];
            } else {
                scrollSelectorValue = formatNumber(selectorIndex);
            }
        }
        cache.put(selectorIndex, scrollSelectorValue);
    }

    private String formatNumber(int value) {
        return (mFormatter != null) ? mFormatter.format(value) : String.valueOf(value);
//        return (mFormatter != null) ? mFormatter.format(value) : formatNumberWithLocale(value);
    }

    /**
     * Updates the view of this NumberPicker. If displayValues were specified in
     * the string corresponding to the index specified by the current value will
     * be returned. Otherwise, the formatter specified in {@link #setFormatter}
     * will be used to format the number.
     *
     * @return Whether the text was updated.
     */
    private boolean updateInputTextView() {
        /*
         * If we don't have displayed values then use the current number else
         * find the correct value in the displayed values for the current
         * number.
         */
        String text = (mDisplayedValues == null) ? formatNumber(mValue)
                : mDisplayedValues[mValue - mMinValue];
        if (!TextUtils.isEmpty(text)) {
            CharSequence beforeText = mSelectedText.getText();
            if (!text.equals(beforeText.toString())) {
                mSelectedText.setText(text);
                return true;
            }
        }

        return false;
    }

    /**
     * Notifies the listener, if registered, of a change of the value of this
     * NumberPicker.
     */
    private void notifyChange(int previous, int current) {
        if (mOnValueChangeListener != null) {
            mOnValueChangeListener.onValueChange(this, previous, current);
        }
    }

    /**
     * Posts a command for changing the current value by one.
     *
     * @param increment Whether to increment or decrement the value.
     */
    private void postChangeCurrentByOneFromLongPress(boolean increment, long delayMillis) {
        if (mChangeCurrentByOneFromLongPressCommand == null) {
            mChangeCurrentByOneFromLongPressCommand = new ChangeCurrentByOneFromLongPressCommand();
        } else {
            removeCallbacks(mChangeCurrentByOneFromLongPressCommand);
        }
        mChangeCurrentByOneFromLongPressCommand.setStep(increment);
        postDelayed(mChangeCurrentByOneFromLongPressCommand, delayMillis);
    }

    /**
     * Posts a command for changing the current value by one.
     *
     * @param increment Whether to increment or decrement the value.
     */
    private void postChangeCurrentByOneFromLongPress(boolean increment) {
        postChangeCurrentByOneFromLongPress(increment, ViewConfiguration.getLongPressTimeout());
    }

    /**
     * Removes the command for changing the current value by one.
     */
    private void removeChangeCurrentByOneFromLongPress() {
        if (mChangeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(mChangeCurrentByOneFromLongPressCommand);
        }
    }

    /**
     * Removes all pending callback from the message queue.
     */
    private void removeAllCallbacks() {
        if (mChangeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(mChangeCurrentByOneFromLongPressCommand);
        }
        if (mSetSelectionCommand != null) {
            mSetSelectionCommand.cancel();
        }
    }

    /**
     * @return The selected index given its displayed <code>value</code>.
     */
    private int getSelectedPos(String value) {
        if (mDisplayedValues == null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // Ignore as if it's not a number we don't care
            }
        } else {
            for (int i = 0; i < mDisplayedValues.length; i++) {
                // Don't force the user to type in jan when ja will do
                value = value.toLowerCase();
                if (mDisplayedValues[i].toLowerCase().startsWith(value)) {
                    return mMinValue + i;
                }
            }

            /*
             * The user might have typed in a number into the month field i.e.
             * 10 instead of OCT so support that too.
             */
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // Ignore as if it's not a number we don't care
            }
        }
        return mMinValue;
    }

    /**
     * Posts a {@link SetSelectionCommand} from the given
     * {@code selectionStart} to {@code selectionEnd}.
     */
    private void postSetSelectionCommand(int selectionStart, int selectionEnd) {
        if (mSetSelectionCommand == null) {
            mSetSelectionCommand = new SetSelectionCommand(mSelectedText);
        } else {
            mSetSelectionCommand.post(selectionStart, selectionEnd);
        }
    }

    /**
     * The numbers accepted by the input text's {@link Filter}
     */
    private static final char[] DIGIT_CHARACTERS = new char[] {
            // Latin digits are the common case
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            // Arabic-Indic
            '\u0660', '\u0661', '\u0662', '\u0663', '\u0664',
            '\u0665', '\u0666', '\u0667', '\u0668', '\u0669',
            // Extended Arabic-Indic
            '\u06f0', '\u06f1', '\u06f2', '\u06f3', '\u06f4',
            '\u06f5', '\u06f6', '\u06f7', '\u06f8', '\u06f9',
            // Hindi and Marathi (Devanagari script)
            '\u0966', '\u0967', '\u0968', '\u0969', '\u096a',
            '\u096b', '\u096c', '\u096d', '\u096e', '\u096f',
            // Bengali
            '\u09e6', '\u09e7', '\u09e8', '\u09e9', '\u09ea',
            '\u09eb', '\u09ec', '\u09ed', '\u09ee', '\u09ef',
            // Kannada
            '\u0ce6', '\u0ce7', '\u0ce8', '\u0ce9', '\u0cea',
            '\u0ceb', '\u0cec', '\u0ced', '\u0cee', '\u0cef',
            // Negative
            '-'
    };

    /**
     * Filter for accepting only valid indices or prefixes of the string
     * representation of valid indices.
     */
    class InputTextFilter extends NumberKeyListener {

        // XXX This doesn't allow for range limits when controlled by a soft input method!
        public int getInputType() {
            return InputType.TYPE_CLASS_TEXT;
        }

        @Override
        protected char[] getAcceptedChars() {
            return DIGIT_CHARACTERS;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                                   int dstart, int dend) {
            // We don't know what the output will be, so always cancel any
            // pending set selection command.
            if (mSetSelectionCommand != null) {
                mSetSelectionCommand.cancel();
            }

            if (mDisplayedValues == null) {
                CharSequence filtered = super.filter(source, start, end, dest, dstart, dend);
                if (filtered == null) {
                    filtered = source.subSequence(start, end);
                }

                String result = String.valueOf(dest.subSequence(0, dstart)) + filtered
                        + dest.subSequence(dend, dest.length());

                if ("".equals(result)) {
                    return result;
                }
                int val = getSelectedPos(result);

                /*
                 * Ensure the user can't type in a value greater than the max
                 * allowed. We have to allow less than min as the user might
                 * want to delete some numbers and then type a new number.
                 * And prevent multiple-"0" that exceeds the length of upper
                 * bound number.
                 */
                if (val > mMaxValue || result.length() > String.valueOf(mMaxValue).length()) {
                    return "";
                } else {
                    return filtered;
                }
            } else {
                CharSequence filtered = String.valueOf(source.subSequence(start, end));
                if (TextUtils.isEmpty(filtered)) {
                    return "";
                }
                String result = String.valueOf(dest.subSequence(0, dstart)) + filtered
                        + dest.subSequence(dend, dest.length());
                String str = String.valueOf(result).toLowerCase();
                for (String val : mDisplayedValues) {
                    String valLowerCase = val.toLowerCase();
                    if (valLowerCase.startsWith(str)) {
                        postSetSelectionCommand(result.length(), val.length());
                        return val.subSequence(dstart, val.length());
                    }
                }
                return "";
            }
        }
    }

    /**
     * Ensures that the scroll wheel is adjusted i.e. there is no offset and the
     * middle element is in the middle of the widget.
     *
     * @return Whether an adjustment has been made.
     */
    private boolean ensureScrollWheelAdjusted() {
        // adjust to the closest value
        int delta = mInitialScrollOffset - mCurrentScrollOffset;
        if (delta != 0) {
            if (Math.abs(delta) > mSelectorElementSize / 2) {
                delta += (delta > 0) ? -mSelectorElementSize : mSelectorElementSize;
            }
            if (isHorizontalMode()) {
                mPreviousScrollerX = 0;
                mAdjustScroller.startScroll(0, 0, delta, 0, SELECTOR_ADJUSTMENT_DURATION_MILLIS);
            } else {
                mPreviousScrollerY = 0;
                mAdjustScroller.startScroll(0, 0, 0, delta, SELECTOR_ADJUSTMENT_DURATION_MILLIS);
            }
            invalidate();
            return true;
        }
        return false;
    }

    /**
     * Command for setting the input text selection.
     */
    private static class SetSelectionCommand implements Runnable {

        private final EditText mInputText;

        private int mSelectionStart;
        private int mSelectionEnd;

        /** Whether this runnable is currently posted. */
        private boolean mPosted;

        public SetSelectionCommand(EditText inputText) {
            mInputText = inputText;
        }

        public void post(int selectionStart, int selectionEnd) {
            mSelectionStart = selectionStart;
            mSelectionEnd = selectionEnd;
            if (!mPosted) {
                mInputText.post(this);
                mPosted = true;
            }
        }

        public void cancel() {
            if (mPosted) {
                mInputText.removeCallbacks(this);
                mPosted = false;
            }
        }

        public void run() {
            mPosted = false;
            mInputText.setSelection(mSelectionStart, mSelectionEnd);
        }
    }

    /**
     * Command for changing the current value from a long press by one.
     */
    class ChangeCurrentByOneFromLongPressCommand implements Runnable {
        private boolean mIncrement;

        private void setStep(boolean increment) {
            mIncrement = increment;
        }

        @Override
        public void run() {
            changeValueByOne(mIncrement);
            postDelayed(this, mLongPressUpdateInterval);
        }
    }

    private String formatNumberWithLocale(int value) {
        return mNumberFormatter.format(value);
    }

    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    private float pxToDp(float px) {
        return px / getResources().getDisplayMetrics().density;
    }

    private float spToPx(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }

    private float pxToSp(float px) {
        return px / getResources().getDisplayMetrics().scaledDensity;
    }

    private Formatter stringToFormatter(final String formatter) {
        if (TextUtils.isEmpty(formatter)) {
            return null;
        }

        return new Formatter() {
            @Override
            public String format(int i) {
                return String.format(mLocale, formatter, i);
            }
        };
    }

    public void setDividerColor(int color) {
        mDividerColor = color;
        mDividerDrawable = new ColorDrawable(color);
    }

    public void setDividerColorResource(int colorId) {
        setDividerColor(Build.VERSION.SDK_INT >= 23 ? mContext.getColor(colorId) : mContext.getResources().getColor(colorId));
    }

    public void setDividerDistance(int distance) {
        mDividerDistance = distance;
    }

    public void setDividerDistanceResource(int dimenId) {
        setDividerDistance(getResources().getDimensionPixelSize(dimenId));
    }

    public void setDividerThickness(int thickness) {
        mDividerThickness = thickness;
    }

    public void setDividerThicknessResource(int dimenId) {
        setDividerThickness(getResources().getDimensionPixelSize(dimenId));
    }

    /**
     * Should sort numbers in ascending or descending order.
     * @param order Pass {@link #ASCENDING} or {@link #ASCENDING}.
     * Default value is {@link #DESCENDING}.
     */
    public void setOrder(int order) {
        if (order != ASCENDING && order != DESCENDING) throw new IllegalArgumentException("illegal");
        mOrder = order;
    }

    public void setOrientation(int orientation) {
        if (orientation != VERTICAL && orientation != HORIZONTAL) throw new IllegalArgumentException("illegal");
        mOrientation = orientation;
    }

    public void setWheelItemCount(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Wheel item count must be >= 1");
        }
        mRealWheelItemCount = count;
        mWheelItemCount = count < DEFAULT_WHEEL_ITEM_COUNT ? DEFAULT_WHEEL_ITEM_COUNT : count;
        mWheelMiddleItemIndex = mWheelItemCount / 2;
        mSelectorIndices = new int[mWheelItemCount];
    }

    public void setFormatter(final String formatter) {
        if (TextUtils.isEmpty(formatter)) {
            return;
        }

        setFormatter(stringToFormatter(formatter));
    }

    public void setFormatter(int stringId) {
        setFormatter(getResources().getString(stringId));
    }

    public void setFadingEdgeEnabled(boolean fadingEdgeEnabled) {
        mFadingEdgeEnabled = fadingEdgeEnabled;
    }

    public void setFadingEdgeStrength(float strength) {
        mFadingEdgeStrength = strength;
    }

    public void setScrollerEnabled(boolean scrollerEnabled) {
        mScrollerEnabled = scrollerEnabled;
    }

    public void setSelectedTextAlign(int align) {
        if (align != LEFT && align != CENTER && align != RIGHT) throw new IllegalArgumentException("illegal");
        mSelectedTextAlign = align;
    }

    public void setSelectedTextColor(int color) {
        mSelectedTextColor = color;
        mSelectedText.setTextColor(mSelectedTextColor);
    }

    public void setSelectedTextColorResource(int colorId) {
        setSelectedTextColor(Build.VERSION.SDK_INT >= 23 ? mContext.getColor(colorId) : mContext.getResources().getColor(colorId));
    }

    public void setSelectedTextSize(float textSize) {
        mSelectedTextSize = textSize;
        mSelectedText.setTextSize(pxToSp(mSelectedTextSize));
    }

    public void setSelectedTextSizeResource(int dimenId) {
        setSelectedTextSize(getResources().getDimension(dimenId));
    }

    public void setSelectedTextStrikeThru(boolean strikeThruText) {
        mSelectedTextStrikeThru = strikeThruText;
    }

    public void setSelectedTextUnderline(boolean underlineText) {
        mSelectedTextUnderline = underlineText;
    }

    public void setTextAlign(int align) {
        if (align != LEFT && align != CENTER && align != RIGHT) throw new IllegalArgumentException("illegal");
        mTextAlign = align;
    }

    public void setTextColor(int color) {
        mTextColor = color;
        mSelectorWheelPaint.setColor(mTextColor);
    }

    public void setTextColorResource(int colorId) {
        setTextColor(Build.VERSION.SDK_INT >= 23 ? mContext.getColor(colorId) : mContext.getResources().getColor(colorId));
    }

    public void setTextSize(float textSize) {
        mTextSize = textSize;
        mSelectorWheelPaint.setTextSize(mTextSize);
    }

    public void setTextSizeResource(int dimenId) {
        setTextSize(getResources().getDimension(dimenId));
    }

    public void setTextStrikeThru(boolean strikeThruText) {
        mTextStrikeThru = strikeThruText;
    }

    public void setTextUnderline(boolean underlineText) {
        mTextUnderline = underlineText;
    }

    public void setTypeface(Typeface typeface) {
        mTypeface = typeface;
        if (mTypeface != null) {
            mSelectedText.setTypeface(mTypeface);
            mSelectorWheelPaint.setTypeface(mTypeface);
        } else {
            mSelectedText.setTypeface(Typeface.MONOSPACE);
            mSelectorWheelPaint.setTypeface(Typeface.MONOSPACE);
        }
    }

    public void setTypeface(String string, int style) {
        if (TextUtils.isEmpty(string)) {
            return;
        }
        setTypeface(Typeface.create(string, style));
    }

    public void setTypeface(String string) {
        setTypeface(string, Typeface.NORMAL);
    }

    public void setTypeface(int stringId, int style) {
        setTypeface(getResources().getString(stringId), style);
    }

    public void setTypeface(int stringId) {
        setTypeface(stringId, Typeface.NORMAL);
    }

    public void setLineSpacingMultiplier(float multiplier) {
        mLineSpacingMultiplier = multiplier;
    }

    public void setMaxFlingVelocityCoefficient(int coefficient) {
        mMaxFlingVelocityCoefficient = coefficient;
        mMaximumFlingVelocity = mViewConfiguration.getScaledMaximumFlingVelocity()
                / mMaxFlingVelocityCoefficient;
    }

    public boolean isHorizontalMode() {
        return getOrientation() == HORIZONTAL;
    }

    public boolean isAscendingOrder() {
        return getOrder() == ASCENDING;
    }

    public int getDividerColor() {
        return mDividerColor;
    }

    public float getDividerDistance() {
        return pxToDp(mDividerDistance);
    }

    public float getDividerThickness() {
        return pxToDp(mDividerThickness);
    }

    public int getOrder() {
        return mOrder;
    }

    public int getOrientation() {
        return mOrientation;
    }

    public int getWheelItemCount() {
        return mWheelItemCount;
    }

    public Formatter getFormatter() {
        return mFormatter;
    }

    public boolean isFadingEdgeEnabled() {
        return mFadingEdgeEnabled;
    }

    public float getFadingEdgeStrength() {
        return mFadingEdgeStrength;
    }

    public boolean isScrollerEnabled() {
        return mScrollerEnabled;
    }

    public int getSelectedTextAlign() {
        return mSelectedTextAlign;
    }

    public int getSelectedTextColor() {
        return mSelectedTextColor;
    }

    public float getSelectedTextSize() {
        return mSelectedTextSize;
    }

    public boolean getSelectedTextStrikeThru() {
        return mSelectedTextStrikeThru;
    }

    public boolean getSelectedTextUnderline() {
        return mSelectedTextUnderline;
    }

    public int getTextAlign() {
        return mTextAlign;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public float getTextSize() {
        return spToPx(mTextSize);
    }

    public boolean getTextStrikeThru() {
        return mTextStrikeThru;
    }

    public boolean getTextUnderline() {
        return mTextUnderline;
    }

    public Typeface getTypeface() {
        return mTypeface;
    }

    public float getLineSpacingMultiplier() {
        return mLineSpacingMultiplier;
    }

    public int getMaxFlingVelocityCoefficient() {
        return mMaxFlingVelocityCoefficient;
    }

}
