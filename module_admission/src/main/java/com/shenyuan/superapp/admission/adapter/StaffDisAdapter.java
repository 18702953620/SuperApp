package com.shenyuan.superapp.admission.adapter;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.databinding.ItemDisPersonBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/4/12 11:06
 * desc 待分配宣传员
 */
public class StaffDisAdapter extends BaseVBAdapter<StaffBean, BaseDataBindingHolder> {
    public StaffDisAdapter() {
        super(R.layout.item_dis_person, false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, StaffBean staffBean) {
        ItemDisPersonBinding personBinding = (ItemDisPersonBinding) holder.getDataBinding();
        if (personBinding == null) {
            return;
        }
        personBinding.tvPersonName.setText(staffBean.getStaffName());
        if (staffBean.isSelect()) {
            personBinding.ivDisPerson.setBackgroundResource(R.mipmap.ic_state_small_select);
        } else {
            personBinding.ivDisPerson.setBackgroundResource(R.mipmap.ic_state_small_normal);
        }
    }
}
