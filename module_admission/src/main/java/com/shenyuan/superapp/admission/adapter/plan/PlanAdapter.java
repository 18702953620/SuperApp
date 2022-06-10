package com.shenyuan.superapp.admission.adapter.plan;

import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemPlanListBinding;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/19 14:55
 * desc
 */
public class PlanAdapter extends BaseVBAdapter<PlanListBean, BaseDataBindingHolder> {
    private final boolean hasPlanUpdate;

    public PlanAdapter() {
        super(R.layout.item_plan_list);
        hasPlanUpdate = PermissionCommon.hasPlanUpdate();
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, PlanListBean s) {
        ItemPlanListBinding bi = (ItemPlanListBinding) holder.getDataBinding();
        if (bi == null) {
            return;
        }
        bi.tvPlanTitle.setText(s.getPlanName());
        bi.tvPlanTime.setText(String.format(getContext().getString(R.string.plan_time), s.getBeginTime(), s.getEndTime()));
        bi.tvCreateTime.setText(s.getCreateTime());
        bi.tvCreateUser.setText(s.getCreatorName());
        if (s.getStatus() == 0) {
            //草稿
            bi.ivPlanState.setBackgroundResource(R.mipmap.ic_plan_state_save);
            if (hasPlanUpdate) {
                bi.tvEdit.setVisibility(View.VISIBLE);
            } else {
                bi.tvEdit.setVisibility(View.GONE);
            }
        } else if (s.getStatus() == 1) {
            //审核中
            bi.ivPlanState.setBackgroundResource(R.mipmap.ic_plan_state_examine);
            bi.tvEdit.setVisibility(View.GONE);
        } else if (s.getStatus() == 2) {
            //通过
            bi.ivPlanState.setBackgroundResource(R.mipmap.ic_plan_state_pass);
            bi.tvEdit.setVisibility(View.GONE);
        } else if (s.getStatus() == 3) {
            //拒绝
            bi.ivPlanState.setBackgroundResource(R.mipmap.ic_plan_state_unpass);

            if (hasPlanUpdate) {
                bi.tvEdit.setVisibility(View.VISIBLE);
            } else {
                bi.tvEdit.setVisibility(View.GONE);
            }
        }
    }
}
