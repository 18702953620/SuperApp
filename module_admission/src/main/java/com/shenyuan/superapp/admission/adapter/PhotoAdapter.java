package com.shenyuan.superapp.admission.adapter;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemPhotoBinding;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.utils.GlideUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/4 14:38
 * desc
 */
public class PhotoAdapter extends BaseVBAdapter<String, BaseDataBindingHolder> {
    public PhotoAdapter(@Nullable List<String> data) {
        super(R.layout.item_photo, data, false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, String s) {
        ItemPhotoBinding bi = (ItemPhotoBinding) holder.getDataBinding();
        if (bi == null) {
            return;
        }
        GlideUtils.load(getContext(), s, bi.ivPhoto, new RequestOptions().centerCrop()
                .override(200, 200)
                .placeholder(R.mipmap.ic_default_record)
                .error(R.mipmap.ic_default_record));
    }
}
