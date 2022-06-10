package com.shenyuan.superapp.admission.adapter.student;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.databinding.ItemSchoolSearchMultpleBinding;
import com.shenyuan.superapp.admission.adapter.search.SearchMultpleAdapter;
import com.shenyuan.superapp.admission.bean.YearBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/18 10:18
 * desc 毕业年份
 */
public class YearAdapter extends SearchMultpleAdapter<YearBean> {
    public YearAdapter(@Nullable List<YearBean> data) {
        super(data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, YearBean s) {
        super.convert(baseDataBindingHolder, s);
        ItemSchoolSearchMultpleBinding multpleBinding = (ItemSchoolSearchMultpleBinding) baseDataBindingHolder.getDataBinding();
        if (multpleBinding != null) {
            multpleBinding.tvMultpleText.setText(s.getYear());
        }
    }
}
