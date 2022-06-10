package com.shenyuan.superstudent.adapter;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.bean.IntegralListBean;
import com.shenyuan.superstudent.databinding.ItemIntegralBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/5/11 10:09
 * desc
 */
public class IntegralAdapter extends BaseVBAdapter<IntegralListBean, BaseDataBindingHolder> {
    public IntegralAdapter() {
        super(R.layout.item_integral, true);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, IntegralListBean s) {
        ItemIntegralBinding bi = (ItemIntegralBinding) holder.getDataBinding();
        if (bi == null) {
            return;
        }
        bi.tvName.setText(StrUtils.isEmpty(s.getReason()));
        bi.tvTime.setText(StrUtils.isEmpty(s.getCreateTime()));
        bi.tvIntegral.setText(StrUtils.isEmpty(s.getChangeValue()));
        if (s != null) {
            if (s.getChangeValue().startsWith("-")) {
                bi.tvIntegral.setTextColor(getValuesColor(R.color.color_e65758));
            } else {
                bi.tvIntegral.setTextColor(getValuesColor(R.color.color_3cbf7f));
            }
        }

    }
}
