package com.shenyuan.superapp.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.shenyuan.superapp.base.utils.DensityUtils;
import com.shenyuan.superapp.common.R;
import com.shenyuan.superapp.common.databinding.CommonSimpleEdittextBinding;

/**
 * @author ch
 * @date 2021/1/11-16:57
 * desc
 */
public class SimEditText extends LinearLayout {
    private static final int DEFAULT_TITLE_COLOR = Color.parseColor("#333333");
    private static final int DEFAULT_LABEL_SIZE = 14;
    /**
     * 左边栏文字
     */
    private String tipLabel;
    /**
     * 左边栏宽度
     */
    private float labelWidth;
    /**
     * 输入框提示
     */
    private String tipHint;
    /**
     * 左边栏文字颜色
     */
    private int labelTextColor;
    /**
     * 内容文字颜色
     */
    private int textColor;
    /**
     * 左边栏文字 大小
     */
    private float labelSize;
    /**
     * 输入框文字 大小
     */
    private float hintSize;
    /**
     * 是否必须
     */
    private boolean showStar;
    /**
     * 是否可输入
     */
    private boolean editAble;
    /**
     * 输入格式
     */
    private int inputType;
    /**
     * 是否可以点击
     */
    protected boolean pickAble;
    /**
     * 单位
     */
    protected String unitText;
    /**
     * 最大长度
     */
    private int maxLength;

    protected CommonSimpleEdittextBinding binding;


    public SimEditText(Context context) {
        super(context, null);
    }


    public SimEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
        init();
    }

    public SimEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.common_simple_edittext, null, false);

        if (!TextUtils.isEmpty(tipLabel)) {
            if (labelWidth != 0) {
                LinearLayout.LayoutParams params = new LayoutParams((int) labelWidth, LayoutParams.WRAP_CONTENT);
                binding.llLabel.setLayoutParams(params);
            } else {
                if (tipLabel.length() < 7) {
                    LinearLayout.LayoutParams params = new LayoutParams(DensityUtils.dp2px(getContext(), 140), LayoutParams.WRAP_CONTENT);
                    binding.llLabel.setLayoutParams(params);
                }
            }

            binding.tvLabel.setVisibility(VISIBLE);
            binding.tvLabel.setText(tipLabel);
            binding.tvLabel.setTextSize(DensityUtils.px2sp(getContext(), labelSize));
            binding.tvLabel.setTextColor(labelTextColor);
        }

        binding.etText.setTextSize(DensityUtils.px2sp(getContext(), hintSize));
        binding.etText.setTextColor(textColor);
        binding.etText.setHint(tipHint);

        binding.tvUnit.setTextSize(DensityUtils.px2sp(getContext(), hintSize));
        binding.tvUnit.setTextColor(textColor);


        if (inputType == 1) {
            binding.etText.setInputType(InputType.TYPE_CLASS_PHONE);
        } else if (inputType == 2) {
            binding.etText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.etText.setTransformationMethod(new PasswordTransformationMethod());
        } else if (inputType == 3) {
            binding.etText.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (inputType == 4) {
            binding.etText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        }

        if (showStar) {
            binding.tvStar.setVisibility(VISIBLE);
        } else {
            binding.tvStar.setVisibility(INVISIBLE);
        }

        if (!TextUtils.isEmpty(unitText)) {
            binding.tvUnit.setVisibility(VISIBLE);
            binding.tvUnit.setText(unitText);
        } else {
            binding.tvUnit.setVisibility(GONE);
            binding.tvUnit.setText("");
        }
        binding.etText.setFocusable(editAble);

        if (maxLength > 0) {
            binding.etText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(binding.getRoot(), params);
        initView();
    }

    private void initAttr(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SimEditText);

        tipLabel = typedArray.getString(R.styleable.SimEditText_ps_sim_label);
        tipHint = typedArray.getString(R.styleable.SimEditText_ps_sim_hint);

        labelWidth = typedArray.getDimension(R.styleable.SimEditText_ps_sim_label_width, 0);

        labelTextColor = typedArray.getColor(R.styleable.SimEditText_ps_sim_label_text_color, DEFAULT_TITLE_COLOR);

        textColor = typedArray.getColor(R.styleable.SimEditText_ps_sim_text_color, DEFAULT_TITLE_COLOR);

        labelSize = typedArray.getDimension(R.styleable.SimEditText_ps_sim_label_text_size, DEFAULT_LABEL_SIZE);

        hintSize = typedArray.getDimension(R.styleable.SimEditText_ps_sim_hint_size, DEFAULT_LABEL_SIZE);

        showStar = typedArray.getBoolean(R.styleable.SimEditText_ps_sim_show_star, true);

        editAble = typedArray.getBoolean(R.styleable.SimEditText_ps_sim_edit_able, true);

        pickAble = typedArray.getBoolean(R.styleable.SimEditText_ps_sim_pick_able, true);

        unitText = typedArray.getString(R.styleable.SimEditText_ps_sim_show_unit);

        inputType = typedArray.getInt(R.styleable.SimEditText_ps_sim_input_type, 0);

        maxLength = typedArray.getInt(R.styleable.SimEditText_ps_sim_max_length, 0);

        typedArray.recycle();
    }

    protected void initView() {
    }


    public void addTextWatcher(TextWatcher watcher) {
        binding.etText.addTextChangedListener(watcher);
    }


    /**
     * 清除
     */
    public void clear() {
        binding.etText.getText().clear();
    }


    public String getText() {
        return binding.etText.getText().toString().trim();
    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            binding.etText.setText(text);
        } else {
            binding.etText.setText("");
        }
    }

    public void setEditAble(boolean editAble) {
        this.editAble = editAble;
        binding.etText.setFocusable(editAble);
        binding.etText.setFocusableInTouchMode(editAble);
    }

    public void setTextClickListener(OnClickListener listener) {
        if (pickAble) {
            binding.etText.setOnClickListener(listener);
        }
    }

    public void setPickAble(boolean pickAble) {
        this.pickAble = pickAble;
    }

    public EditText getEditText() {
        return binding.etText;
    }

    public void setLabel(String label) {
        binding.tvLabel.setText(label);
    }

    /**
     * @param s s
     */
    public void showToast(String s) {
        Toast toast = Toast.makeText(getContext(), s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
