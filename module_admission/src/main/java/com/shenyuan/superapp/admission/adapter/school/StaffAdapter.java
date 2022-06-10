package com.shenyuan.superapp.admission.adapter.school;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.databinding.ItemSchoolSearchMultpleBinding;
import com.shenyuan.superapp.admission.adapter.search.SearchMultpleAdapter;
import com.shenyuan.superapp.admission.bean.StaffBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/18 10:18
 * desc 宣传员
 */
public class StaffAdapter extends SearchMultpleAdapter<StaffBean> {
    public StaffAdapter(@Nullable List<StaffBean> data) {
        super(data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, StaffBean s) {
        super.convert(baseDataBindingHolder, s);
        ItemSchoolSearchMultpleBinding multpleBinding = (ItemSchoolSearchMultpleBinding) baseDataBindingHolder.getDataBinding();
        if (multpleBinding != null) {
            multpleBinding.tvMultpleText.setText(s.getStaffName());
        }
    }
}
