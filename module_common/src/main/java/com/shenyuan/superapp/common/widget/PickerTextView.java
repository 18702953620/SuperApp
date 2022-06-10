package com.shenyuan.superapp.common.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shenyuan.superapp.base.utils.NavigationBarUtils;
import com.shenyuan.superapp.base.utils.PopUtils;
import com.shenyuan.superapp.base.utils.SoftInputUtils;
import com.shenyuan.superapp.common.R;
import com.shenyuan.superapp.common.databinding.PopPickTextBinding;
import com.shenyuan.superapp.common.widget.loopview.LoopView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/2 16:13
 * desc
 */
public class PickerTextView extends SimEditText {
    private PopPickTextBinding popBinding;
    /**
     * 回调
     */
    public OnChooseItem chooseItem;
    /**
     * 原始数据
     */
    private List<?> data;
    /**
     * 实际显示的数据
     */
    private List<String> newData;
    /**
     * 选中的位置
     */
    private int selectPosition;

    protected PopupWindow popupWindow;

    public PickerTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PickerTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    @SuppressLint("UseCompatLoadingForDrawables")
    protected void initView() {

        if (pickAble) {
            binding.tvUnit.setVisibility(VISIBLE);
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_arrow_right_gray);
            binding.tvUnit.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            binding.tvUnit.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        binding.etText.setFocusable(false);
        binding.etText.setOnClickListener(v -> {
            if (getContext() instanceof Activity) {
                SoftInputUtils.hideSoftInput((Activity) getContext());
            }
            if (pickAble) {
                showPickDialog();
            }
        });

        initPopWindow();
    }

    protected void initPopWindow() {
        popBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.pop_pick_text, null, false);

        popBinding.tvCancel.setOnClickListener(v -> popupWindow.dismiss());
        popBinding.tvSubmit.setOnClickListener(v -> {
            selectPosition = popBinding.wvText.getSelectedItem();

            if (chooseItem != null && data != null && data.size() > selectPosition) {
                //回调
                chooseItem.choose(data.get(selectPosition));
            }

            //设置显示的数据
            if (newData != null && newData.size() > selectPosition) {
                setText(newData.get(selectPosition));
            }
            popupWindow.dismiss();
        });
    }

    /**
     * 弹窗
     */
    protected void showPickDialog() {
        if (popupWindow == null) {
            popupWindow = PopUtils.getPopupWindow(getContext(), popBinding.getRoot(), true, true);
        }
        popupWindow.showAtLocation(binding.etText, Gravity.BOTTOM, 0, NavigationBarUtils.getNavigationBarHeight(getContext()));
    }

    /**
     * 设置数据
     *
     * @param data       data String类型
     * @param chooseItem chooseItem
     */
    public void setData(List<String> data, OnChooseItem<String> chooseItem) {
        this.chooseItem = chooseItem;
        this.data = data;
        this.newData = data;
        popBinding.wvText.setItems(data);
    }

    /**
     * 设置数据
     *
     * @param data       data 非String类型
     * @param key        key
     * @param chooseItem chooseItem
     * @param <M>        数据类型
     */
    public <M> void setData(List<M> data, String key, OnChooseItem<M> chooseItem) {
        this.chooseItem = chooseItem;
        this.data = data;
        if (data != null && data.size() > 0) {
            newData = new ArrayList<>();
            for (M m : data) {
                JSONObject obj = JSON.parseObject(JSONObject.toJSONString(m));
                if (obj.containsKey(key)) {
                    newData.add(obj.getString(key));
                }
            }
            popBinding.wvText.setItems(newData);
        }
    }

    /**
     * 设置数据
     *
     * @param data data 非String类型
     * @param key  key
     * @param <M>  数据类型
     */
    public <M> void setData(LoopView loopView, List<M> data, String key) {
        this.data = data;
        if (data != null && data.size() > 0) {
            newData = new ArrayList<>();
            for (M m : data) {
                JSONObject obj = JSON.parseObject(JSONObject.toJSONString(m));
                if (obj.containsKey(key)) {
                    newData.add(obj.getString(key));
                }
            }
            loopView.setItems(newData);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setPickAble(boolean pickAble) {
        this.pickAble = pickAble;
        if (pickAble) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_arrow_right_gray);
            binding.tvUnit.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            binding.tvUnit.setVisibility(VISIBLE);
        } else {
            binding.tvUnit.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            binding.tvUnit.setVisibility(GONE);
        }
    }


    public interface OnChooseItem<T> {
        /**
         * 回调
         *
         * @param t t
         */
        void choose(T t);
    }

}
