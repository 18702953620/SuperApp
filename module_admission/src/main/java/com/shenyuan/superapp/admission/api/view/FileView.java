package com.shenyuan.superapp.admission.api.view;

import com.shenyuan.superapp.admission.bean.FileTreeBean;
import com.shenyuan.superapp.base.api.BaseView;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/9 13:38
 * desc
 */
public interface FileView extends BaseView {
    /**
     * 文件树
     *
     * @param o o
     */
    void onFileList(List<FileTreeBean> o);

    /**
     * 添加文件夹
     *
     * @param o o
     */
    void addDir(String o);

    /**
     * 删除
     *
     * @param o o
     */
    void onDelete(String o);

    /**
     * 添加文件
     *
     * @param o o
     */
    void onAddFile(String o);
}
