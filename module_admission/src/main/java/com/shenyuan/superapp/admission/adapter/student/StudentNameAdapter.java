package com.shenyuan.superapp.admission.adapter.student;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemSchoolSearchBinding;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/8 10:58
 * desc
 */
public class StudentNameAdapter extends BaseVBAdapter<StudentListBean, BaseDataBindingHolder> {
    public StudentNameAdapter() {
        super(R.layout.item_school_search, false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, StudentListBean schoolListBean) {
        ItemSchoolSearchBinding searchBinding = (ItemSchoolSearchBinding) holder.getDataBinding();
        if (searchBinding == null) {
            return;
        }
        searchBinding.tvSchoolName.setText(schoolListBean.getName());
    }
}
