package com.shenyuan.superapp.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.shenyuan.superapp.common.R;
import com.shenyuan.superapp.common.databinding.CommonDeleteEdittextBinding;

/**
 * @author ch
 * @date 2021/1/11-16:57
 * desc
 */
public class DeleteEditText extends LinearLayout {
    /**
     * 输入框提示
     */
    private String tipHint;
    /**
     * 是否显示右边按钮
     */
    private boolean showButton;
    /**
     * 文字大小
     */
    private float labelSize;
    /**
     * 按钮背景
     */
    private Drawable buttonDrawable;
    /**
     * 输入格式
     */
    private int inputType;

    private CommonDeleteEdittextBinding binding;


    public DeleteEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
        init();
    }

    public DeleteEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.common_delete_edittext, null, false);

        binding.etDelete.setHint(tipHint);
        binding.etDelete.setTextSize(px2sp(getContext(), labelSize));
        if (showButton) {
            if (buttonDrawable != null) {
                binding.ivDelete.setBackground(buttonDrawable);
            }
            binding.llDelete.setVisibility(VISIBLE);
        }
        binding.llDelete.setOnClickListener(v -> binding.etDelete.getText().clear());

        if (inputType == 1) {
            binding.etDelete.setInputType(InputType.TYPE_CLASS_PHONE);
        } else if (inputType == 2) {
            binding.etDelete.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.etDelete.setTransformationMethod(new PasswordTransformationMethod());
        } else if (inputType == 3) {
            binding.etDelete.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(binding.getRoot(), params);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DeleteTextView);

        tipHint = typedArray.getString(R.styleable.DeleteTextView_ps_delete_hint);

        showButton = typedArray.getBoolean(R.styleable.DeleteTextView_ps_delete_show_button, true);

        labelSize = typedArray.getDimension(R.styleable.DeleteTextView_ps_delete_text_size, 14);

        buttonDrawable = typedArray.getDrawable(R.styleable.DeleteTextView_ps_delete_button_res);

        inputType = typedArray.getInt(R.styleable.DeleteTextView_ps_delete_input_type, 0);

        typedArray.recycle();
    }

    /**
     * 设置按钮图片
     *
     * @param res res
     */
    public void setButtonDrawable(int res) {
        binding.ivDelete.setBackgroundResource(res);
    }


    public String getText() {
        return binding.etDelete.getText().toString().trim();
    }

    public void clear() {
        binding.etDelete.getText().clear();
    }


    /**
     * px转sp
     */
    private float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
