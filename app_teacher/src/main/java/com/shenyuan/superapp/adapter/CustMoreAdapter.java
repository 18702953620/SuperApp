package com.shenyuan.superapp.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.DraggableModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.bean.MenuBean;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superapp.databinding.ItemMoreCustBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author ch
 * @date 2021/1/20 15:40
 * desc
 */
public class CustMoreAdapter extends BaseVBAdapter<MenuBean, BaseDataBindingHolder> implements DraggableModule {
    private boolean showEdit;

    public void setShowEdit(boolean showEdit) {
        this.showEdit = showEdit;
        notifyDataSetChanged();
    }

    public CustMoreAdapter() {
        super(R.layout.item_more_cust);
        setNeedEmptyView(false);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, MenuBean menuBean) {
        ItemMoreCustBinding binding = (ItemMoreCustBinding) baseDataBindingHolder.getDataBinding();
        if (binding != null) {
            binding.tvMenuName.setText(menuBean.getServiceName());
            if (getContext().getResources().getString(R.string.edit).equals(menuBean.getServiceName())) {
                binding.ivMenuImg.setImageResource(R.mipmap.ic_more_edit);
            } else {
                GlideUtils.load(getContext(), menuBean.getIconUrl(), binding.ivMenuImg, R.mipmap.ic_default_menu);
            }
            if (showEdit) {
                if (!getContext().getResources().getString(R.string.edit).equals(menuBean.getServiceName())) {
                    binding.llDelete.setVisibility(View.VISIBLE);
                } else {
                    binding.llDelete.setVisibility(View.GONE);
                }
            } else {
                binding.llDelete.setVisibility(View.GONE);
            }
        }
    }
}
