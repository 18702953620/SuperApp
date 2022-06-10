package com.shenyuan.superapp.admission.adapter.school;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.databinding.ItemSchoolSearchMultpleBinding;
import com.shenyuan.superapp.admission.adapter.search.SearchMultpleAdapter;
import com.shenyuan.superapp.admission.bean.CreaterBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/18 11:41
 * desc
 */
public class CreaterAdapter extends SearchMultpleAdapter<CreaterBean> {
    public CreaterAdapter(@Nullable List<CreaterBean> data) {
        super(data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, CreaterBean s) {
        super.convert(baseDataBindingHolder, s);
        ItemSchoolSearchMultpleBinding multpleBinding = (ItemSchoolSearchMultpleBinding) baseDataBindingHolder.getDataBinding();
        if (multpleBinding != null) {
            multpleBinding.tvMultpleText.setText(s.getName());
        }
    }
}
