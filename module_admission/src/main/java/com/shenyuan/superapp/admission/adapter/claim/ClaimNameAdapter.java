package com.shenyuan.superapp.admission.adapter.claim;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemSchoolSearchBinding;
import com.shenyuan.superapp.admission.bean.ClaimListBean;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/19 14:50
 * desc
 */
public class ClaimNameAdapter extends BaseVBAdapter<ClaimListBean, BaseDataBindingHolder> {
    public ClaimNameAdapter() {
        super(R.layout.item_school_search, false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, ClaimListBean listBean) {
        ItemSchoolSearchBinding searchBinding = (ItemSchoolSearchBinding) holder.getDataBinding();
        if (searchBinding == null) {
            return;
        }
        searchBinding.tvSchoolName.setText(listBean.getClaimName());
    }
}
