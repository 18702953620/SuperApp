package com.shenyuan.superapp.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.shenyuan.superapp.common.R;
import com.shenyuan.superapp.common.databinding.CommonTipEdittextBinding;

/**
 * @author ch
 * @date 2021/1/11-16:57
 * desc
 */
public class TipEditText extends LinearLayout {
    private static final int DEFAULT_TITLE_COLOR = Color.parseColor("#333333");
    private static final int DEFAULT_ERROR_COLOR = Color.parseColor("#fc602d");
    private static final int DEFAULT_LABEL_SIZE = 14;
    /**
     * 左边栏文字
     */
    private String tipLabel;
    /**
     * 输入框提示
     */
    private String tipHint;
    /**
     * 错误提示
     */
    private String errorText;
    /**
     * 左边栏文字颜色
     */
    private int labelTextColor;
    /**
     * 错误提示颜色
     */
    private int errorTextColor;
    /**
     * 左边栏文字 大小
     */
    private float labelSize;
    /**
     * 输入框文字 大小
     */
    private float hintSize;
    /**
     * 是否显示间隔线
     */
    private boolean showLine;
    /**
     * 是否显示右边按钮
     */
    private boolean showButton;
    /**
     * 按钮背景
     */
    private Drawable buttonDrawable;
    /**
     * 输入格式
     */
    private int inputType;

    private CommonTipEdittextBinding binding;


    public TipEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
        init();
    }

    public TipEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.common_tip_edittext, null, false);

        if (!TextUtils.isEmpty(tipLabel)) {
            binding.tvLabel.setVisibility(VISIBLE);
            binding.tvLabel.setText(tipLabel);
            binding.tvLabel.setTextSize(px2sp(getContext(), labelSize));
            binding.tvLabel.setTextColor(labelTextColor);
        }

        binding.etText.setTextSize(px2sp(getContext(), hintSize));
        binding.etText.setHint(tipHint);
        binding.viewLine.setVisibility(showLine ? VISIBLE : GONE);
        if (showButton) {
            if (buttonDrawable != null) {
                binding.ivTip.setBackground(buttonDrawable);
            }
            binding.llError.setVisibility(VISIBLE);
        }
        if (inputType == 1) {
            binding.etText.setInputType(InputType.TYPE_CLASS_PHONE);
        } else if (inputType == 2) {
            binding.etText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.etText.setTransformationMethod(new PasswordTransformationMethod());
        } else if (inputType == 3) {
            binding.etText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        if (!TextUtils.isEmpty(errorText)) {
            binding.tvErrorText.setText(errorText);
            binding.tvErrorText.setVisibility(VISIBLE);
        } else {
            binding.tvErrorText.setVisibility(GONE);
        }
        binding.tvErrorText.setTextColor(errorTextColor);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(binding.getRoot(), params);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TipEditText);

        tipLabel = typedArray.getString(R.styleable.TipEditText_ps_tip_label);
        tipHint = typedArray.getString(R.styleable.TipEditText_ps_tip_hint);
        errorText = typedArray.getString(R.styleable.TipEditText_ps_error_text);

        labelTextColor = typedArray.getColor(R.styleable.TipEditText_ps_tip_text_color, DEFAULT_TITLE_COLOR);
        errorTextColor = typedArray.getColor(R.styleable.TipEditText_ps_error_text_color, DEFAULT_ERROR_COLOR);

        labelSize = typedArray.getDimension(R.styleable.TipEditText_ps_tip_text_size, DEFAULT_LABEL_SIZE);
        hintSize = typedArray.getDimension(R.styleable.TipEditText_ps_tip_hint_size, DEFAULT_LABEL_SIZE);

        showLine = typedArray.getBoolean(R.styleable.TipEditText_ps_error_show_line, true);
        showButton = typedArray.getBoolean(R.styleable.TipEditText_ps_error_show_button, false);

        buttonDrawable = typedArray.getDrawable(R.styleable.TipEditText_ps_error_button_res);

        inputType = typedArray.getInt(R.styleable.TipEditText_ps_error_input_type, 0);

        typedArray.recycle();
    }

    /**
     * 设置错误信息
     *
     * @param errorText errorText
     */
    public void setErrorText(String errorText) {
        this.errorText = errorText;
        binding.tvErrorText.setText(errorText);
    }

    /**
     * 设置文本
     *
     * @param text text
     */
    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            binding.etText.setText(text);
            binding.etText.setSelection(text.length());
        }
    }

    /**
     * 设置按钮点击事件
     *
     * @param listener listener
     */
    public void setButtonClickListener(OnClickListener listener) {
        binding.llError.setOnClickListener(listener);
    }

    /**
     * 清除
     */
    public void clear() {
        binding.etText.getText().clear();
    }

    /**
     * 设置按钮图片
     *
     * @param res res
     */
    public void setButtonDrawable(int res) {
        binding.ivTip.setBackgroundResource(res);
    }

    public EditText getEditText() {
        return binding.etText;
    }


    public String getText() {
        return binding.etText.getText().toString().trim();
    }

    public ImageView getRightImageView() {
        return binding.ivTip;
    }

    public LinearLayout getRightLayout() {
        return binding.llError;
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
