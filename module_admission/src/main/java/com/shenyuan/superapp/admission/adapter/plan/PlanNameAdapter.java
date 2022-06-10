package com.shenyuan.superapp.admission.adapter.plan;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemSchoolSearchBinding;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/19 14:50
 * desc
 */
public class PlanNameAdapter extends BaseVBAdapter<PlanListBean, BaseDataBindingHolder> {
    public PlanNameAdapter() {
        super(R.layout.item_school_search,false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, PlanListBean listBean) {
        ItemSchoolSearchBinding searchBinding = (ItemSchoolSearchBinding) holder.getDataBinding();
        if (searchBinding == null) {
            return;
        }
        searchBinding.tvSchoolName.setText(listBean.getPlanName());
    }
}
