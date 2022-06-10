package com.shenyuan.superapp.common.popup;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;

import com.bumptech.glide.request.RequestOptions;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.common.R;
import com.shenyuan.superapp.common.bean.AdvertBean;
import com.shenyuan.superapp.common.databinding.DialogAdvertBinding;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superapp.common.web.WebActivity;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

/**
 * @author ch
 * @date 2021/5/25 16:55
 * @
 */
public class AdvertDialog extends BaseDialog<DialogAdvertBinding> {
    private List<AdvertBean> advertBeans;

    public AdvertDialog(@NonNull Context context) {
        super(context);
    }

    public AdvertDialog(@NonNull Context context, List<AdvertBean> advertBeans) {
        super(context);
        this.advertBeans = advertBeans;
        initBanner();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_advert;
    }

    @Override
    protected void initView() {
        binding.ivClose.setOnClickListener(v -> dismiss());
    }

    private void initBanner() {
        binding.banner.addBannerLifecycleObserver(this);
        binding.banner.setAdapter(new BannerImageAdapter<AdvertBean>(advertBeans) {

            @Override
            public void onBindView(BannerImageHolder holder, AdvertBean data, int position, int size) {
                GlideUtils.load(getContext(), data.getPictureUrl(), holder.imageView, new RequestOptions().centerCrop());
            }
        });
        binding.banner.setIndicator(new CircleIndicator(getContext()));

        binding.banner.setOnBannerListener((OnBannerListener<AdvertBean>) (data, position) -> {

            if (!TextUtils.isEmpty(data.getJumpUrl())) {
                WebActivity.loadUrl(getContext(), data.getJumpUrl());
            }
        });
    }
}
