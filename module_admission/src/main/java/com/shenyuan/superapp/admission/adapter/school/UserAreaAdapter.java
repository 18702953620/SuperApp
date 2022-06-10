package com.shenyuan.superapp.admission.adapter.school;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.databinding.ItemSchoolSearchMultpleBinding;
import com.shenyuan.superapp.admission.adapter.search.SearchMultpleAdapter;
import com.shenyuan.superapp.admission.bean.AreaUserBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/18 10:18
 * desc 区域
 */
public class UserAreaAdapter extends SearchMultpleAdapter<AreaUserBean> {
    public UserAreaAdapter(@Nullable List<AreaUserBean> data) {
        super(data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, AreaUserBean s) {
        super.convert(baseDataBindingHolder, s);
        ItemSchoolSearchMultpleBinding multpleBinding = (ItemSchoolSearchMultpleBinding) baseDataBindingHolder.getDataBinding();
        if (multpleBinding != null) {
            multpleBinding.tvMultpleText.setText(s.getStaffName());
        }
    }
}
