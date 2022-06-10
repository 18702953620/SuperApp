package com.shenyuan.superstudent.adapter;

import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.superapp.base.utils.DateUtils;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.bean.MsgListBean;
import com.shenyuan.superstudent.databinding.ItemMsgBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/24 15:41
 * desc
 */
public class MsgAdapter extends BaseVBAdapter<MsgListBean, BaseDataBindingHolder> {
    public MsgAdapter() {
        super(R.layout.item_msg);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, MsgListBean s) {
        ItemMsgBinding msgBinding = (ItemMsgBinding) holder.getDataBinding();
        if (msgBinding == null) {
            return;
        }
        msgBinding.tvMsgTitle.setText(s.getTitle());
        msgBinding.tvMsgDept.setText(s.getPublisherType());
        msgBinding.tvMsgTime.setText(DateUtils.getFormatDateHM(DateUtils.getFormatDateYs(s.getPublishTime())));
        if (s.getViewed() == 0) {
            msgBinding.tvMsgNew.setVisibility(View.VISIBLE);
        } else {
            msgBinding.tvMsgNew.setVisibility(View.GONE);
        }
        GlideUtils.load(getContext(), s.getPictureUrl(), msgBinding.ivMsgImg, new RequestOptions()
                .placeholder(R.mipmap.ic_default_msg).error(R.mipmap.ic_default_msg)
                .override(100, 100));
    }
}
