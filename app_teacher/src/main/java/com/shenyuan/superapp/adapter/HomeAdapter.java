package com.shenyuan.superapp.adapter;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.bean.MenuBean;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superapp.databinding.ItemHomeMenuBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/13 9:29
 * desc
 */
public class HomeAdapter extends BaseVBAdapter<MenuBean, BaseDataBindingHolder> {
    public HomeAdapter() {
        super(R.layout.item_home_menu);
        setNeedEmptyView(false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, MenuBean menuBean) {
        ItemHomeMenuBinding binding = (ItemHomeMenuBinding) holder.getDataBinding();
        if (binding != null) {
            binding.tvMenuName.setText(menuBean.getServiceName());
            if (getContext().getResources().getString(R.string.more).equals(menuBean.getServiceName())) {
                binding.ivMenuImg.setImageResource(R.mipmap.ic_home_more);
            } else {
                GlideUtils.load(getContext(), menuBean.getIconUrl(), binding.ivMenuImg, R.mipmap.ic_default_menu);
            }
        }
    }
}
