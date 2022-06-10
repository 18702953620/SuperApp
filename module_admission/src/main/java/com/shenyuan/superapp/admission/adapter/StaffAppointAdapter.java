package com.shenyuan.superapp.admission.adapter;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemAppointPersonBinding;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/4/12 11:12
 * desc 已分配宣传员
 */
public class StaffAppointAdapter extends BaseVBAdapter<StaffBean, BaseDataBindingHolder> {
    public StaffAppointAdapter() {
        super(R.layout.item_appoint_person,false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, StaffBean staffBean) {
        ItemAppointPersonBinding personBinding = (ItemAppointPersonBinding) holder.getDataBinding();
        if (personBinding == null) {
            return;
        }
        personBinding.tvPersonName.setText(staffBean.getStaffName());
    }
}
