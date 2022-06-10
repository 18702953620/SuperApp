package com.shenyuan.superapp.admission.adapter.tree;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.shenyuan.superapp.admission.bean.FileTreeBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/8 17:30
 * desc
 */
public class FileTreeAdapter extends BaseNodeAdapter {

    private boolean isExpanded;

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }


    public FileTreeAdapter() {
        addNodeProvider(new FileProvider(0));
        addNodeProvider(new FileProvider(1));
        addNodeProvider(new FileProvider(2));
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int position) {
        BaseNode node = list.get(position);
        if (node instanceof FileTreeBean) {
            FileTreeBean bean = (FileTreeBean) node;
            return bean.getLevel();
        }
        return 0;
    }
}
