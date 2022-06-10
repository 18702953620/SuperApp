package com.shenyuan.superapp.adapter;

import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.databinding.ItemSearchHotBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/19 10:43
 * desc
 */
public class SearchHotAdapter extends BaseVBAdapter<String, BaseDataBindingHolder<>> {
    public SearchHotAdapter() {
        super(R.layout.item_search_hot);
        setNeedEmptyView(false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, String s) {
        ItemSearchHotBinding binding = (ItemSearchHotBinding) holder.getDataBinding();
        if (binding != null) {
            if (holder.getAdapterPosition() == 0 || holder.getAdapterPosition() == 1 || holder.getAdapterPosition() == 2) {
                binding.tvSearchSort.setTextColor(getValuesColor(R.color.color_fc602d));
                binding.ivSearchHot.setVisibility(View.VISIBLE);
            } else {
                binding.tvSearchSort.setTextColor(getValuesColor(R.color.color_999999));
                holder.setTextColor(R.id.tv_search_sort, getValuesColor(R.color.color_999999));
                binding.ivSearchHot.setVisibility(View.GONE);
            }
            binding.tvSearchSort.setText(String.valueOf(holder.getAdapterPosition() + 1));
            binding.tvSearchContent.setText(s);
        }
    }
}
