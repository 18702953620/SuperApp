package com.shenyuan.superapp.adapter;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.bean.NewsBean;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superapp.databinding.ItemHomeNewsBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/12 19:13
 * desc
 */
public class NewsAdapter extends BaseVBAdapter<NewsBean, BaseDataBindingHolder> {
    public NewsAdapter() {
        super(R.layout.item_home_news);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, NewsBean s) {
        ItemHomeNewsBinding newsBinding = (ItemHomeNewsBinding) holder.getDataBinding();
        if (newsBinding == null) {
            return;
        }
        newsBinding.tvNewsTitle.setText(s.getNewsTitle());
        newsBinding.tvNewsContent.setText(StrUtils.isEmpty(s.getSummary()));
        newsBinding.tvCreate.setText(s.getSignature());
        newsBinding.tvLookCount.setText(String.valueOf(s.getViewCount()));
        GlideUtils.load(getContext(), s.getPictureUrl(), newsBinding.ivNews,
                new RequestOptions().centerCrop().error(R.mipmap.ic_default_news)
                        .placeholder(R.mipmap.ic_default_news).override(240, 200));
    }
}
