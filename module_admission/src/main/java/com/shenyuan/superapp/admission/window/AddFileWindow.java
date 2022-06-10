package com.shenyuan.superapp.admission.window;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemAddFileBinding;
import com.shenyuan.admission.databinding.PopAddFileBinding;
import com.shenyuan.superapp.admission.bean.FileTreeBean;
import com.shenyuan.superapp.admission.bean.ToolMenuBean;
import com.shenyuan.superapp.admission.window.dialog.AddDirDialog;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.common.api.down.DownModel;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.popup.BasePopupWindow;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/9 15:36
 * desc
 */
public class AddFileWindow extends BasePopupWindow<PopAddFileBinding, BasePresenter> {

    public AddFileWindow(Context context) {
        super(context, true, true);
    }


    private BaseVBAdapter<ToolMenuBean, BaseDataBindingHolder> toolsAdapter;

    private FileTreeBean treeBean;

    public void setTreeBean(FileTreeBean treeBean) {
        this.treeBean = treeBean;
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 0-主页
     * 1-文件夹
     * 2-文件
     */
    public void setType(int type) {
        List<ToolMenuBean> list = new ArrayList<>();
        if (type == 0) {
            if (PermissionCommon.hasCreateFolder()) {
                list.add(new ToolMenuBean(R.mipmap.ic_add_dir, "新建文件夹"));
            }
            if (PermissionCommon.hasAddFile()) {
                list.add(new ToolMenuBean(R.mipmap.ic_add_img, "上传图片"));
                list.add(new ToolMenuBean(R.mipmap.ic_add_file, "上传文件"));
            }
        } else if (type == 1) {
            if (PermissionCommon.hasAddFile()) {
                list.add(new ToolMenuBean(R.mipmap.ic_add_img, "上传图片"));
                list.add(new ToolMenuBean(R.mipmap.ic_add_file, "上传文件"));
            }
            if (PermissionCommon.hasDeleteFile()) {
                list.add(new ToolMenuBean(R.mipmap.ic_add_delete, "删除"));
            }
            if (PermissionCommon.hasUpdateDir()) {
                list.add(new ToolMenuBean(R.mipmap.ic_add_rename, "重命名"));
            }
        } else if (type == 2) {
            list.add(new ToolMenuBean(R.mipmap.ic_add_down, "下载"));
            if (PermissionCommon.hasDeleteFile()) {
                list.add(new ToolMenuBean(R.mipmap.ic_add_delete, "删除"));
            }
        }
        toolsAdapter.setNewInstance(list);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_add_file;
    }

    @Override
    protected void initView() {
        toolsAdapter = new BaseVBAdapter<ToolMenuBean, BaseDataBindingHolder>(R.layout.item_add_file) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder holder, ToolMenuBean sm) {
                ItemAddFileBinding bi = (ItemAddFileBinding) holder.getDataBinding();
                if (bi == null) {
                    return;
                }

                bi.ivAddIcon.setImageResource(sm.getRes());
                bi.tvAddDir.setText(sm.getName());
            }
        };
        binding.rvTools.setLayoutManager(new GridLayoutManager(context, 3));
        binding.rvTools.setAdapter(toolsAdapter);


    }

    @Override
    protected void addListener() {
        toolsAdapter.setOnItemClickListener((adapter, view, position) -> {
            ToolMenuBean bean = toolsAdapter.getItem(position);
            if ("新建文件夹".equals(bean.getName())) {
                new AddDirDialog.Builder(context).title("新建文件夹").onBacklistener(new BaseDialog.BaseOnBack() {
                    @Override
                    public void onConfirm(Object object) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("isFile", 0);
                        map.put("name", object.toString());
                        if (treeBean != null) {
                            map.put("parentId", treeBean.getId());
                        }
                        if (callBack != null) {
                            callBack.addDir(map);
                        }
                    }
                }).show();
            } else if ("删除".equals(bean.getName())) {
                if (callBack != null) {
                    callBack.delete(treeBean.getId());
                }
            } else if ("重命名".equals(bean.getName())) {
                new AddDirDialog.Builder(context)
                        .dirName(treeBean.getName())
                        .title("文件夹重命名").onBacklistener(new BaseDialog.BaseOnBack() {
                    @Override
                    public void onConfirm(Object object) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("name", object.toString());
                        if (treeBean != null) {
                            map.put("id", treeBean.getId());
                        }
                        if (callBack != null) {
                            callBack.updateDir(map);
                        }
                    }
                }).show();
            } else if ("上传图片".equals(bean.getName())) {
                if (callBack != null) {
                    callBack.uploadImg(treeBean.getId());
                }
            } else if ("上传文件".equals(bean.getName())) {
                if (callBack != null) {
                    callBack.uploadFile(treeBean.getId());
                }
            } else if ("下载".equals(bean.getName())) {
                if (callBack != null) {
                    if (treeBean.getIsFile() == 0) {
                        List<DownModel.ChildFile> childs = new ArrayList<>();
                        if (treeBean.getChildren() != null && treeBean.getChildren().size() > 0) {
                            for (FileTreeBean be : treeBean.getChildren()) {
                                if (be.getIsFile() == 1) {
                                    DownModel.ChildFile ch = new DownModel.ChildFile();
                                    ch.setName(be.getName());
                                    ch.setUrl(be.getFilePath());
                                    childs.add(ch);
                                }
                            }
                        }
                        callBack.downDir(childs, treeBean.getName());
                    } else {
                        callBack.downFile(treeBean.getFilePath(), treeBean.getName());
                    }
                }
            }

            dismiss();
        });
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    /**
     * 接口应在fragment 中调用，所以回调
     */
    public interface CallBack {
        /**
         * 添加文件夹
         *
         * @param map map
         */
        void addDir(HashMap<String, Object> map);

        /**
         * 删除
         *
         * @param id id
         */
        void delete(int id);

        /**
         * 修改文件夹
         *
         * @param map map
         */
        void updateDir(HashMap<String, Object> map);

        /**
         * 上传图片
         *
         * @param id id
         */
        void uploadImg(int id);

        /**
         * 上传文件
         *
         * @param id id
         */
        void uploadFile(int id);

        /**
         * 下载
         *
         * @param url  链接
         * @param name 名称
         */
        void downFile(String url, String name);

        /**
         * 下载文件夹
         *
         * @param childs 链接
         */
        void downDir(List<DownModel.ChildFile> childs, String name);

    }
}
