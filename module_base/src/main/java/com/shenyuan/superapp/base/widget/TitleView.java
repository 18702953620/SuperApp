package com.shenyuan.superapp.base.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenyuan.superapp.base.R;


/**
 * @author ch
 * @date 2020/4/21-10:20
 * desc 标题栏
 */
public class TitleView extends RelativeLayout {

    private final Context context;
    private static final int DEFAULT_TITLE_COLOR = Color.BLACK;
    private static final int DEFAULT_LINE_COLOR = Color.parseColor("#f3f3f3");
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private static final int DEFAULT_BACK_RES = R.mipmap.back;

    private final int DEFAULT_TITLE_SIZE = sp2px(getContext(), 18);
    private final int DEFAULT_RIGHT_SIZE = sp2px(getContext(), 14);
    private final int DEFAULT_HIGHT = dp2px(getContext(), 44);
    private final int DEFAULT_LINE_HIGHT = dp2px(getContext(), 1);
    /**
     * 标题颜色
     */
    private int titleTextColor;
    /**
     * 右边颜色
     */
    private int rightTextColor;
    /**
     * 标题
     */
    private String title;
    /**
     * 右边文字
     */
    private String rightText;
    /**
     * 标题字号
     */
    private float titleSize;
    /**
     * 右边字号
     */
    private float rightSize;
    /**
     * 返回键
     */
    private Drawable backDrawable;
    /**
     * 右边图片
     */
    private Drawable rightDrawable;
    /**
     * 是否需要返回键
     */
    private boolean needBack;
    /**
     * 背景颜色
     */
    private int backgroundColor;
    /**
     * 是否需要下间隔线
     */
    private boolean needLine;
    /**
     * 间隔线高度
     */
    private int lineHeight;
    /**
     * 间隔线颜色
     */
    private int lineColor;
    private LinearLayout rightLayout;
    private TextView tvTitle;
    private LinearLayout backLayout;


    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttr(attrs);
        init();
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttr(attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, DEFAULT_HIGHT);
    }

    private void init() {

        if (needBack) {
            //需要返回键
            backLayout = new LinearLayout(context);

            backLayout.setGravity(Gravity.CENTER);

            //返回键
            ImageView back = new ImageView(context);

            addBackListener(backLayout);

            if (backDrawable != null) {
                back.setBackground(backDrawable);
            } else {
                back.setBackgroundResource(DEFAULT_BACK_RES);
            }


            LayoutParams backLayoutParams = new LayoutParams(DEFAULT_HIGHT, DEFAULT_HIGHT);
            backLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

            backLayout.addView(back);

            addView(backLayout, backLayoutParams);
        }


        tvTitle = new TextView(context);
        tvTitle.setText(title);
        tvTitle.setTextSize(px2sp(getContext(), titleSize));
        tvTitle.setTextColor(titleTextColor);

        LayoutParams titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        addView(tvTitle, titleParams);

        //右边布局
        rightLayout = new LinearLayout(context);
        rightLayout.setGravity(Gravity.CENTER);
        rightLayout.setMinimumWidth(DEFAULT_HIGHT);

        if (rightDrawable != null) {
            ImageView ivright = new ImageView(context);
            ivright.setBackground(rightDrawable);

            rightLayout.addView(ivright);
        }

        if (!TextUtils.isEmpty(rightText)) {
            TextView tvRight = new TextView(context);
            tvRight.setTextColor(rightTextColor);
            tvRight.setTextSize(px2sp(getContext(), rightSize));
            tvRight.setText(rightText);

            rightLayout.addView(tvRight);
        }
        LayoutParams layoutRightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, DEFAULT_HIGHT);
        layoutRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutRightParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutRightParams.rightMargin = dp2px(getContext(), 5);
        rightLayout.setMinimumWidth(DEFAULT_HIGHT);

        addView(rightLayout, layoutRightParams);


        LayoutParams parentParams = new LayoutParams(LayoutParams.MATCH_PARENT, DEFAULT_HIGHT);
        setLayoutParams(parentParams);

        setBackgroundColor(backgroundColor);


        //间隔线
        if (needLine) {
            View view = new View(context);
            view.setBackgroundColor(lineColor);
            LayoutParams lineParams = new LayoutParams(LayoutParams.MATCH_PARENT, lineHeight);
            lineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            addView(view, lineParams);
        }
    }

    private void initAttr(AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        title = typedArray.getString(R.styleable.TitleView_ps_title);
        rightText = typedArray.getString(R.styleable.TitleView_ps_right_text);

        titleTextColor = typedArray.getColor(R.styleable.TitleView_ps_title_text_color, DEFAULT_TITLE_COLOR);
        rightTextColor = typedArray.getColor(R.styleable.TitleView_ps_right_text_color, DEFAULT_TITLE_COLOR);
        backgroundColor = typedArray.getColor(R.styleable.TitleView_ps_title_background_color, DEFAULT_BACKGROUND_COLOR);
        lineColor = typedArray.getColor(R.styleable.TitleView_ps_line_color, DEFAULT_LINE_COLOR);

        titleSize = typedArray.getDimension(R.styleable.TitleView_ps_title_text_size, DEFAULT_TITLE_SIZE);
        rightSize = typedArray.getDimension(R.styleable.TitleView_ps_right_text_size, DEFAULT_RIGHT_SIZE);
        lineHeight = typedArray.getDimensionPixelSize(R.styleable.TitleView_ps_line_height, DEFAULT_LINE_HIGHT);

        backDrawable = typedArray.getDrawable(R.styleable.TitleView_ps_back_res);
        rightDrawable = typedArray.getDrawable(R.styleable.TitleView_ps_right_res);

        needBack = typedArray.getBoolean(R.styleable.TitleView_ps_need_back, true);
        needLine = typedArray.getBoolean(R.styleable.TitleView_ps_need_line, false);

        typedArray.recycle();

    }

    /**
     * 添加返回监听
     */
    private void addBackListener(View view) {
        if (context instanceof Activity) {
            view.setOnClickListener(v -> ((Activity) context).onBackPressed());
        }
    }

    /**
     * 右边监听
     */
    public void addRightListener(OnClickListener listener) {
        if (rightLayout == null) {
            return;
        }
        rightLayout.setOnClickListener(listener);
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        if (tvTitle == null) {
            return;
        }
        tvTitle.setText(title);
    }


    /**
     * 是否需要返回键
     */
    public void needBack(boolean needBack) {
        this.needBack = needBack;
        if (needBack) {
            if (null != backLayout) {
                backLayout.setVisibility(VISIBLE);
            }
        } else {
            if (null != backLayout) {
                backLayout.setVisibility(INVISIBLE);
            }
        }
    }

    /**
     * 是否需要右边按钮
     *
     * @param need need
     */
    public void needRightBtn(boolean need) {
        rightLayout.setVisibility(need ? VISIBLE : GONE);
    }


    /**
     * dp转px
     */
    private int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     */
    public int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal,
                context.getResources().getDisplayMetrics());
    }

    /**
     * px转sp
     */
    private float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
