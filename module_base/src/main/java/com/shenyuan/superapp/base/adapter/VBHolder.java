package com.shenyuan.superapp.base.adapter;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/2/20 18:07
 * desc
 */
public class VBHolder<VB extends ViewDataBinding> extends BaseDataBindingHolder<VB> {
    public VBHolder(@NotNull View view) {
        super(view);
    }
}
