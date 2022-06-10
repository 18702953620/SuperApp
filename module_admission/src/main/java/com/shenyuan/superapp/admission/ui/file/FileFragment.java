package com.shenyuan.superapp.admission.ui.file;

import android.graphics.Color;
import android.view.Gravity;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ch.cper.CPermission;
import com.ch.cper.PermissGroup;
import com.ch.cper.listener.PermissListener;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.h.cheng.filepicker.PsPickManager;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.FmAdmissionFileBinding;
import com.shenyuan.superapp.admission.adapter.tree.FileTreeAdapter;
import com.shenyuan.superapp.admission.api.presenter.FilePresenter;
import com.shenyuan.superapp.admission.api.view.FileView;
import com.shenyuan.superapp.admission.bean.FileTreeBean;
import com.shenyuan.superapp.admission.bean.event.FileUpdateEvent;
import com.shenyuan.superapp.admission.window.AddFileWindow;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseFragment;
import com.shenyuan.superapp.common.CompressManager;
import com.shenyuan.superapp.common.MyImgLoader;
import com.shenyuan.superapp.common.api.down.DownLoadHelper;
import com.shenyuan.superapp.common.api.down.DownModel;
import com.shenyuan.superapp.common.api.down.DownloadListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/8 16:59
 * desc
 */
public class FileFragment extends BaseFragment<FmAdmissionFileBinding, FilePresenter> implements FileView {


    public FileFragment(FileTreeBean treeBean) {
        this.treeBean = treeBean;
        if (treeBean != null) {
            this.fileList = treeBean.getChildren();
        }
    }

    public FileFragment(FileTreeBean treeBean, boolean isExpanded) {
        this.treeBean = treeBean;
        this.isExpanded = isExpanded;
        if (treeBean != null) {
            this.fileList = treeBean.getChildren();
        }
    }

    private FileTreeAdapter treeAdapter;
    /**
     * 回调到列表
     */
    private CallBack listCallback;
    /**
     * 当前文件夹下文件/文件夹
     */
    private List<FileTreeBean> fileList;
    /**
     * 操作弹窗
     */
    private AddFileWindow addFileWindow;
    /**
     * 当前文件夹
     */
    private final FileTreeBean treeBean;
    /**
     * 是否展开
     */
    private boolean isExpanded;

    public void setListCallback(CallBack listCallback) {
        this.listCallback = listCallback;
    }

    /**
     * 回调
     */
    private final AddFileWindow.CallBack callBack = new AddFileWindow.CallBack() {
        @Override
        public void addDir(HashMap<String, Object> map) {
            presenter.addDir(map);
        }

        @Override
        public void delete(int id) {
            presenter.deleteFile(id);
        }

        @Override
        public void updateDir(HashMap<String, Object> map) {
            presenter.updateDir(map);
        }

        @Override
        public void uploadImg(int id) {
            openImage(id);
        }

        @Override
        public void uploadFile(int id) {
            openFile(id);
        }

        @Override
        public void downFile(String url, String name) {
            ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_FILE_RECORD).navigation();
            DownLoadHelper.getInstance().downLoadFile(url, name, new DownloadListener() {
                @Override
                public void onStartDownload(String tag) {

                }

                @Override
                public void onProgress(String tag, int progress, long downSize, long totalSize) {

                }

                @Override
                public void onFinishDownload(String tag, File file) {
                    if (file == null) {
                        showToast("文件夹下载成功");
                    } else {
                        showToast("文件下载成功，路径为" + file.getPath());
                    }
                }

                @Override
                public void onFail(String tag, Throwable ex) {
                    showToast("文件下载失败，" + ex.getMessage());
                }
            });
        }

        @Override
        public void downDir(List<DownModel.ChildFile> childs, String name) {
            if (childs.size() == 0) {
                showToast("文件夹下没有文件");
                return;
            }
        }
    };

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fm_admission_file;
    }

    @Override
    protected void initView() {
        treeAdapter = new FileTreeAdapter();
        binding.rvFile.setLayoutManager(new LinearLayoutManager(context));
        binding.rvFile.setAdapter(treeAdapter);


        List<BaseNode> list = new ArrayList<>();

        if (fileList != null && fileList.size() > 0) {
            for (FileTreeBean bean : fileList) {

                if (bean.getChildren() != null && bean.getChildren().size() > 0) {
                    for (FileTreeBean child : bean.getChildren()) {
                        if (child != null) {
                            if (child.getChildren() != null && child.getChildren().size() > 0) {
                                child.setChildNode(new ArrayList<>(bean.getChildren()));
                            }
                            child.setLevel(1);
                            child.setExpanded(isExpanded);
                        }
                    }
                }
                if (bean.getChildren() != null && bean.getChildren().size() > 0) {
                    bean.setChildNode(new ArrayList<>(bean.getChildren()));
                }
                bean.setLevel(0);
                bean.setExpanded(isExpanded);
                list.add(bean);
            }
        }
        treeAdapter.setNewInstance(list);
        binding.rvFile.getItemAnimator().setChangeDuration(0);
    }

    @Override
    protected void addListener() {
        treeAdapter.addChildClickViewIds(R.id.ll_more);
        treeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ll_more) {
                if (addFileWindow == null) {
                    addFileWindow = new AddFileWindow(context);
                    addFileWindow.setCallBack(callBack);
                }
                FileTreeBean bean = (FileTreeBean) treeAdapter.getItem(position);
                if (bean.getIsFile() == 0) {
                    addFileWindow.setType(1);
                } else {
                    addFileWindow.setType(2);
                }
                addFileWindow.setTreeBean(bean);
                addFileWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            }
        });
        binding.btnAdd.setOnClickListener(v -> {
            if (addFileWindow == null) {
                addFileWindow = new AddFileWindow(context);
            }
            addFileWindow.setCallBack(callBack);
            addFileWindow.setType(0);
            addFileWindow.setTreeBean(treeBean);
            addFileWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        });
    }

    private void openImage(int id) {
        CPermission.with(context).permiss().permission(PermissGroup.STORAGE).listener(new PermissListener<String>() {
            @Override
            public void onGranted(List<String> li) {
                new PsPickManager.Buider()
                        .with(getActivity())
                        .max(1)
                        .spanCount(4)
                        .title("请选择图片")
                        .backColor(Color.parseColor("#ffffff"))
                        .titleColor(Color.parseColor("#333333"))
                        .rightColor(Color.parseColor("#666666"))
                        .rightBackColor(Color.parseColor("#ffffff"))
                        .imageLoader(new MyImgLoader())
                        .callback(list -> {
                            if (list != null && list.size() > 0) {
                                new CompressManager(context, list.get(0).getPath(), new CompressManager.CompressListener() {
                                    @Override
                                    public void onFinish(List<String> list) {
                                        presenter.addFile(list.get(0), id);
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                }).start();
                            }
                        })
                        .startImage();
            }

            @Override
            public void onDenied(List<String> list) {

            }
        }).start();
    }

    private void openFile(int id) {
        CPermission.with(context).permiss().permission(PermissGroup.STORAGE).listener(new PermissListener<String>() {
            @Override
            public void onGranted(List<String> li) {
                new PsPickManager.Buider()
                        .with(getActivity())
                        .max(1)
                        .spanCount(4)
                        .title("请选择文件")
                        .suffix(new String[]{"xlsx", "xls", "doc", "docx", "ppt", ".pptx", "pdf"})
                        .backColor(Color.parseColor("#ffffff"))
                        .titleColor(Color.parseColor("#333333"))
                        .rightColor(Color.parseColor("#666666"))
                        .rightBackColor(Color.parseColor("#ffffff"))
                        .imageLoader(new MyImgLoader())
                        .callback(list -> {
                            if (list != null && list.size() > 0) {
                                presenter.addFile(list.get(0).getPath(), id);
                                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_FILE_RECORD).withInt("type", 1).navigation();
                            }
                        })
                        .startFile();
            }

            @Override
            public void onDenied(List<String> list) {

            }
        }).start();

    }

    @Override
    protected FilePresenter createPresenter() {
        return new FilePresenter(this);
    }

    @Override
    public void onFileList(List<FileTreeBean> o) {

    }

    @Override
    public void addDir(String o) {
        showToast(getString(R.string.succ_add));
        if (listCallback != null) {
            listCallback.onUpdate();
        }
    }

    @Override
    public void onDelete(String o) {
        showToast(getString(R.string.succ_delete));
        if (listCallback != null) {
            listCallback.onUpdate();
        }
    }

    @Override
    public void onAddFile(String o) {
        showToast(getString(R.string.succ_update));
        if (listCallback != null) {
            listCallback.onUpdate();
        }
        //通知更新
        EventBus.getDefault().post(new FileUpdateEvent());
    }


    public interface CallBack {
        /**
         * 更新列表
         */
        void onUpdate();
    }
}
