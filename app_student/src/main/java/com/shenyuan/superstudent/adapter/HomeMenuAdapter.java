package com.shenyuan.superstudent.adapter;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.bean.MenuBean;
import com.shenyuan.superstudent.databinding.ItemHomeMenuBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/5/8 16:31
 * desc
 */
public class HomeMenuAdapter extends BaseVBAdapter<MenuBean, BaseDataBindingHolder> {
    public HomeMenuAdapter() {
        super(R.layout.item_home_menu, false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, MenuBean s) {
        ItemHomeMenuBinding bi = (ItemHomeMenuBinding) holder.getDataBinding();
        if (bi == null) {
            return;
        }
        if (getContext().getString(R.string.more).equals(s.getServiceName())) {
            bi.ivHomeMenu.setBackgroundResource(R.mipmap.item3);
        } else {
            GlideUtils.load(getContext(), s.getStuIconUrl(), bi.ivHomeMenu);
        }
    }
}
