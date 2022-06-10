package com.shenyuan.superstudent.adapter;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.databinding.ItemSearchHistoryBinding;
import com.shenyuan.superstudent.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/19 10:42
 * desc
 */
public class SearchHistoryAdapter extends BaseVBAdapter<String, BaseDataBindingHolder> {
    public SearchHistoryAdapter() {
        super(R.layout.item_search_history);
        setNeedEmptyView(false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, String s) {
        ItemSearchHistoryBinding binding = (ItemSearchHistoryBinding) holder.getDataBinding();
        if (binding != null) {
            binding.tvSearchHistory.setText(s);
        }
    }
}
