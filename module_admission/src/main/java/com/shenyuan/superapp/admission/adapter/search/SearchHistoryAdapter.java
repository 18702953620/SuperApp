package com.shenyuan.superapp.admission.adapter.search;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.databinding.ItemSearchHistoryBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/8 11:59
 * desc 搜索历史列表
 */
public class SearchHistoryAdapter extends BaseVBAdapter<String, BaseDataBindingHolder> {

    public SearchHistoryAdapter() {
        super(R.layout.item_search_history, false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseViewHolder, String s) {
        ItemSearchHistoryBinding binding = (ItemSearchHistoryBinding) baseViewHolder.getDataBinding();
        if (binding != null) {
            binding.tvSearchHistory.setText(s);
        }
    }
}
