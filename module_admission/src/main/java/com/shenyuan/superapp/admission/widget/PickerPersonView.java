package com.shenyuan.superapp.admission.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemAppointPersonBinding;
import com.shenyuan.superapp.admission.adapter.StaffAppointAdapter;
import com.shenyuan.superapp.admission.adapter.StaffDisAdapter;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.utils.NavigationBarUtils;
import com.shenyuan.superapp.base.utils.PopUtils;
import com.shenyuan.superapp.common.databinding.PopPickPersonBinding;
import com.shenyuan.superapp.common.widget.PickerTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/2 16:13
 * desc 宣传员选择
 */
public class PickerPersonView extends PickerTextView {
    private StaffDisAdapter allAdapter;
    private PopPickPersonBinding popBinding;
    private PopupWindow popupWindow;
    private StaffAppointAdapter appointAdapter;


    public PickerPersonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PickerPersonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        popBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.pop_pick_person, null, false);
        allAdapter = new StaffDisAdapter();
        popBinding.rvPerson.setLayoutManager(new GridLayoutManager(getContext(), 3));
        popBinding.rvPerson.setAdapter(allAdapter);

        appointAdapter = new StaffAppointAdapter();

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        popBinding.rvAppoint.setLayoutManager(layoutManager);
        popBinding.rvAppoint.setAdapter(appointAdapter);

        allAdapter.setOnItemClickListener((adapter, view, position) -> {
            StaffBean bean = allAdapter.getItem(position);
            if (bean != null) {
                bean.setSelect(!bean.isSelect());
            }
            if (bean.isSelect()) {
                appointAdapter.addData(bean);
            } else {
                appointAdapter.remove(bean);
            }
            allAdapter.notifyItemChanged(position);
            if (appointAdapter.getData().size() > 0) {
                popBinding.tvPersonName.setVisibility(View.GONE);
            } else {
                popBinding.tvPersonName.setVisibility(View.INVISIBLE);
            }
        });

        appointAdapter.setOnItemClickListener((adapter, view, position) -> {
            StaffBean bean = appointAdapter.getItem(position);
            int pos = allAdapter.getData().indexOf(bean);
            if (pos >= 0) {
                bean.setSelect(false);
                allAdapter.notifyItemChanged(pos);
            }

            appointAdapter.removeAt(position);

            if (appointAdapter.getData().size() > 0) {
                popBinding.tvPersonName.setVisibility(View.GONE);
            } else {
                popBinding.tvPersonName.setVisibility(View.INVISIBLE);
            }
        });

        popBinding.tvCancel.setOnClickListener(v -> popupWindow.dismiss());
        popBinding.tvSubmit.setOnClickListener(v -> {
            List<StaffBean> list = new ArrayList<>();
            StringBuilder name = new StringBuilder();
            for (StaffBean bean : allAdapter.getData()) {
                if (bean.isSelect()) {
                    list.add(bean);
                    if (name.length() > 0) {
                        name.append(",");
                    }
                    name.append(bean.getStaffName());
                }
            }
            if (list.size() == 0) {
                showToast("请至少选择一个");
                return;
            }

            setText(name.toString());
            chooseItem.choose(list);
            popupWindow.dismiss();
        });
    }

    @Override
    protected void showPickDialog() {
        if (popupWindow == null) {
            popupWindow = PopUtils.getPopupWindow(getContext(), popBinding.getRoot(), true, true);
        }

        popupWindow.showAtLocation(binding.etText, Gravity.BOTTOM, 0, NavigationBarUtils.getNavigationBarHeight(getContext()));
    }

    /**
     * 清除选择的宣传员
     */
    public void clearStaff() {
        appointAdapter.setNewInstance(null);
        for (StaffBean b : allAdapter.getData()) {
            b.setSelect(false);
        }
        allAdapter.notifyDataSetChanged();
        popBinding.tvPersonName.setVisibility(View.INVISIBLE);

        clear();
    }


    public void setPersonData(List<StaffBean> data, OnChooseItem<List<StaffBean>> chooseItem) {
        this.chooseItem = chooseItem;
        allAdapter.setNewInstance(data);
    }

    public void setPersonData(List<StaffBean> data, List<StaffBean> old, OnChooseItem<List<StaffBean>> chooseItem) {
        this.chooseItem = chooseItem;
        appointAdapter.setNewInstance(old);
        if (data == null) {
            return;
        }

        if (appointAdapter.getData().size() > 0) {
            for (StaffBean b : data) {
                for (StaffBean be : old) {
                    if (b.getId() == be.getId()) {
                        be.setSelect(true);
                        data.set(data.indexOf(b), be);
                    }
                }
            }
        }
        allAdapter.setNewInstance(data);

        if (appointAdapter.getData().size() > 0) {
            popBinding.tvPersonName.setVisibility(View.GONE);
        } else {
            popBinding.tvPersonName.setVisibility(View.INVISIBLE);
        }
    }
}
