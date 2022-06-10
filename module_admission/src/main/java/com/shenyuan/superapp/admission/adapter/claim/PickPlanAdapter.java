package com.shenyuan.superapp.admission.adapter.claim;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemPickPlanListBinding;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/4/12 10:55
 * desc
 */
public class PickPlanAdapter extends BaseVBAdapter<PlanListBean, BaseDataBindingHolder> {
    public PickPlanAdapter() {
        super(R.layout.item_pick_plan_list);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, PlanListBean s) {
        ItemPickPlanListBinding bi = (ItemPickPlanListBinding) holder.getDataBinding();
        if (bi == null) {
            return;
        }
        if (s.isSelect()) {
            bi.ivSchoolState.setBackgroundResource(R.mipmap.ic_state_large_select);
        } else {
            bi.ivSchoolState.setBackgroundResource(R.mipmap.ic_state_large_normal);
        }

        bi.tvPlanTitle.setText(s.getPlanName());
        bi.tvPlanTime.setText(String.format("时间安排：%s至%s", s.getBeginTime(), s.getEndTime()));
        bi.tvCreateTime.setText(s.getCreateTime());
        if (s.getStatus() == 0) {
            bi.ivPlanState.setBackgroundResource(R.mipmap.ic_plan_state_save);
        } else if (s.getStatus() == 1) {
            bi.ivPlanState.setBackgroundResource(R.mipmap.ic_plan_state_examine);
        } else if (s.getStatus() == 2) {
            bi.ivPlanState.setBackgroundResource(R.mipmap.ic_plan_state_pass);
        } else if (s.getStatus() == 3) {
            bi.ivPlanState.setBackgroundResource(R.mipmap.ic_plan_state_unpass);
        }
    }
}
