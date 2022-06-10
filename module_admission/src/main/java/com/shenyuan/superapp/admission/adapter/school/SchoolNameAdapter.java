package com.shenyuan.superapp.admission.adapter.school;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemSchoolSearchBinding;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/8 10:58
 * desc
 */
public class SchoolNameAdapter extends BaseVBAdapter<SchoolInfoBean, BaseDataBindingHolder> {
    public SchoolNameAdapter() {
        super(R.layout.item_school_search, false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, SchoolInfoBean schoolListBean) {
        ItemSchoolSearchBinding searchBinding = (ItemSchoolSearchBinding) holder.getDataBinding();
        if (searchBinding == null) {
            return;
        }
        searchBinding.tvSchoolName.setText(schoolListBean.getSchoolName());
    }
}
