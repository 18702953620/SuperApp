package com.shenyuan.superapp.admission.adapter;

import android.annotation.SuppressLint;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemDownBinding;
import com.shenyuan.superapp.base.utils.FileUtils;
import com.shenyuan.superapp.common.api.down.DownModel;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/11 16:52
 * desc
 */
public class DownAdapter extends BaseQuickAdapter<DownModel, BaseDataBindingHolder> {
    private boolean showTools;

    public void setShowTools(boolean showTools) {
        this.showTools = showTools;
        notifyDataSetChanged();
    }

    public DownAdapter() {
        super(R.layout.item_down);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, DownModel sm) {
        ItemDownBinding bi = (ItemDownBinding) holder.getDataBinding();
        if (bi == null) {
            return;
        }
        if (showTools) {
            bi.ivState.setVisibility(View.VISIBLE);
        } else {
            bi.ivState.setVisibility(View.GONE);
        }

        if (sm.isSelect()) {
            bi.ivState.setBackgroundResource(R.mipmap.ic_down_select);
        } else {
            bi.ivState.setBackgroundResource(R.mipmap.ic_down_normal);
        }

        if (sm.isDir()) {
            bi.ivDownType.setBackgroundResource(R.mipmap.ic_down_dir);
        } else {
            bi.ivDownType.setBackgroundResource(R.mipmap.ic_down_file);
        }
        bi.tvName.setText(sm.getName());
        if (sm.getType() == 0) {
            if (sm.getTotalSize() != 0) {
                int pro = (int) (sm.getDownSize() * 100 / sm.getTotalSize());
                bi.pbDown.setProgress(pro);
                if (pro == 100) {
                    bi.pbDown.setVisibility(View.GONE);
                    bi.ivOption.setBackgroundResource(R.mipmap.ic_file_tools);
                } else {
                    bi.pbDown.setVisibility(View.VISIBLE);

                    if (sm.getDownState() == DownModel.DOWN_DEFAULT) {
                        bi.ivOption.setBackgroundResource(R.mipmap.ic_down_start);
                    } else {
                        bi.ivOption.setBackgroundResource(R.mipmap.ic_down_stop);
                    }
                }
            }

            if (sm.getDownState() == DownModel.DOWN_FINISH) {
                if (sm.isDir()) {
                    bi.tvSize.setText(sm.getFiles() == null ? "0个" : sm.getFiles().size() + "个");
                } else {
                    bi.tvSize.setText(FileUtils.getFormatSize(sm.getTotalSize()));
                }

            } else {
                if (sm.isDir()) {
                    bi.tvSize.setText(String.format("%d/%d", sm.getDownSize(), sm.getTotalSize()));
                } else {
                    bi.tvSize.setText(String.format("%s/%s", FileUtils.getFormatSize(sm.getDownSize()), FileUtils.getFormatSize(sm.getTotalSize())));
                }

            }
        } else {
            bi.pbDown.setVisibility(View.GONE);
            bi.ivOption.setBackgroundResource(R.mipmap.ic_file_tools);
            bi.tvSize.setText(FileUtils.getFormatSize(sm.getTotalSize()));
        }

    }
}
