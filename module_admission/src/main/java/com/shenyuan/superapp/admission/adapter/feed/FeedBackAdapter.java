package com.shenyuan.superapp.admission.adapter.feed;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemFeedBackBinding;
import com.shenyuan.superapp.admission.bean.FeedBackListBean;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.utils.DateUtils;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/20 11:38
 * desc
 */
public class FeedBackAdapter extends BaseVBAdapter<FeedBackListBean, BaseDataBindingHolder> {
    private final boolean hasFeedUpdate;
    private final boolean hasFeedDelete;

    public FeedBackAdapter() {
        super(R.layout.item_feed_back);
        hasFeedUpdate = PermissionCommon.hasFeedUpdate();
        hasFeedDelete = PermissionCommon.hasFeedDelete();
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, FeedBackListBean s) {
        ItemFeedBackBinding bi = (ItemFeedBackBinding) holder.getDataBinding();
        if (bi == null) {
            return;
        }
        bi.tvSchoolName.setText(s.getFbName());
        bi.tvPlanTime.setText(String.format("%s 至 %s", s.getStartTime(), s.getEndTime()));
        if (!TextUtils.isEmpty(s.getAreaName())) {
            bi.tvArea.setText(s.getAreaName());
            bi.tvArea.setVisibility(View.VISIBLE);
        } else {
            bi.tvArea.setVisibility(View.GONE);
        }
        bi.tvStaff.setText(s.getCreatorName());
        bi.tvTime.setText(DateUtils.getFormatDateMM(DateUtils.getFormatDateYs(s.getCreateTime())));
        bi.tvClassNumber.setText(String.valueOf(s.getClassNum()));
        bi.tvStudentNumber.setText(String.valueOf(s.getStudentNum()));

        if (hasFeedUpdate) {
            bi.tvEdit.setVisibility(View.VISIBLE);
        } else {
            bi.tvEdit.setVisibility(View.GONE);
        }

        if (hasFeedDelete) {
            bi.tvDelete.setVisibility(View.VISIBLE);
        } else {
            bi.tvDelete.setVisibility(View.GONE);
        }
    }
}
