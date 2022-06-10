package com.shenyuan.superapp.admission.adapter.search;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemSchoolSearchMultpleBinding;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.bean.BaseChooseBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author ch
 * @date 2021/1/28 15:07
 * desc
 */
public class SearchMultpleAdapter<B extends BaseChooseBean> extends BaseVBAdapter<B, BaseDataBindingHolder> {
    public SearchMultpleAdapter(@Nullable List<B> data) {
        super(R.layout.item_school_search_multple, data,false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, B s) {
        ItemSchoolSearchMultpleBinding multpleBinding = (ItemSchoolSearchMultpleBinding) baseDataBindingHolder.getDataBinding();
        if (multpleBinding == null) {
            return;
        }
        if (s.isSelect()) {
            multpleBinding.tvMultpleText.setPsBtnBackgroundColor(getContext().getResources().getColor(R.color.color_dbecff));
            multpleBinding.tvMultpleText.setTextColor(getContext().getResources().getColor(R.color.color_0077ff));
        } else {
            multpleBinding.tvMultpleText.setPsBtnBackgroundColor(getContext().getResources().getColor(R.color.color_eaeaea));
            multpleBinding.tvMultpleText.setTextColor(getContext().getResources().getColor(R.color.color_666666));
        }
    }
}
