package com.shenyuan.superapp.admission.adapter.tree;

import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shenyuan.admission.R;
import com.shenyuan.superapp.admission.bean.FileTreeBean;
import com.shenyuan.superapp.base.utils.DensityUtils;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/9 11:02
 * desc
 */
public class FileProvider extends BaseNodeProvider {

    private final int viewType;

    public FileProvider(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType() {
        return viewType;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_admission_file;
    }

    @Override
    public void convert(@NotNull BaseViewHolder holder, BaseNode baseNode) {
        FileTreeBean bean = (FileTreeBean) baseNode;
        if (bean != null) {
            holder.setText(R.id.tv_title, bean.getName());
            setMargin(holder.getView(R.id.iv_file_img), bean.getLevel());
            if (bean.getIsFile() == 0) {
                if (bean.isExpanded()) {
                    holder.setBackgroundResource(R.id.iv_file_img, R.mipmap.ic_dir_open);
                } else {
                    holder.setBackgroundResource(R.id.iv_file_img, R.mipmap.ic_dir_close);
                }
            } else {
                holder.setBackgroundResource(R.id.iv_file_img, R.mipmap.ic_file_icon);
            }
        }
    }

    /**
     * 设置缩进
     *
     * @param view  view
     * @param level level
     */
    protected void setMargin(View view, int level) {
        if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.setMarginStart(level * 40 + DensityUtils.dp2px(getContext(), 15));
            view.setLayoutParams(params);
        }
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        getAdapter().expandOrCollapse(position, true, true, 100);
        getAdapter().notifyItemChanged(position);
    }
}
