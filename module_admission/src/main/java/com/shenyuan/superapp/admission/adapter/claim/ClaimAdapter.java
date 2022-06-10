package com.shenyuan.superapp.admission.adapter.claim;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemClaimBinding;
import com.shenyuan.superapp.admission.bean.ClaimListBean;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/23 18:12
 * desc
 */
public class ClaimAdapter extends BaseVBAdapter<ClaimListBean, BaseDataBindingHolder> {

    private final boolean hasClaimUpdate;
    private final boolean hasClaimDelete;

    public ClaimAdapter() {
        super(R.layout.item_claim);
        hasClaimUpdate = PermissionCommon.hasClaimUpdate();
        hasClaimDelete = PermissionCommon.hasClaimDelete();
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, ClaimListBean sm) {
        ItemClaimBinding bi = (ItemClaimBinding) holder.getDataBinding();
        if (bi == null) {
            return;
        }
        bi.tvName.setText(sm.getClaimName());
        bi.tvPlanTime.setText(String.format("%s 至 %s", sm.getStartTime(), sm.getEndTime()));
        if (!TextUtils.isEmpty(sm.getAreaName())) {
            bi.tvArea.setText(sm.getAreaName());
            bi.tvArea.setVisibility(View.VISIBLE);
        } else {
            bi.tvArea.setVisibility(View.GONE);
        }

        bi.tvStaff.setText(sm.getCreatorName());

        if (sm.getStatus() == 0) {
            bi.ivPlanState.setBackgroundResource(R.mipmap.ic_plan_state_save);
            if (PermissionCommon.hasClaimUpdate()) {
                bi.tvEdit.setVisibility(View.VISIBLE);
            } else {
                bi.tvEdit.setVisibility(View.GONE);
            }
        } else if (sm.getStatus() == 1) {
            bi.ivPlanState.setBackgroundResource(R.mipmap.ic_plan_state_examine);
            bi.tvEdit.setVisibility(View.GONE);
        } else if (sm.getStatus() == 2) {
            bi.ivPlanState.setBackgroundResource(R.mipmap.ic_plan_state_pass);
            bi.tvEdit.setVisibility(View.GONE);
        } else if (sm.getStatus() == 3) {
            bi.ivPlanState.setBackgroundResource(R.mipmap.ic_plan_state_unpass);
            if (hasClaimUpdate) {
                bi.tvEdit.setVisibility(View.VISIBLE);
            } else {
                bi.tvEdit.setVisibility(View.GONE);
            }
        }
        bi.tvTotal.setText(String.format(getContext().getString(R.string.claim_total_travel_person_number), sm.getTravelPersonNum()));
        if (hasClaimDelete) {
            bi.tvDelete.setVisibility(View.VISIBLE);
        } else {
            bi.tvDelete.setVisibility(View.GONE);
        }
    }
}
