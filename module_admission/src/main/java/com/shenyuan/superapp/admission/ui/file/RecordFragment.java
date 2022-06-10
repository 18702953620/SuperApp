package com.shenyuan.superapp.admission.ui.file;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.FragmentRecordBinding;
import com.shenyuan.superapp.admission.adapter.DownAdapter;
import com.shenyuan.superapp.admission.bean.event.FileUpdateEvent;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.base.BaseFragment;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SimDialog;
import com.shenyuan.superapp.base.preview.BasePreviewActivity;
import com.shenyuan.superapp.base.utils.FileUtils;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superapp.base.widget.HProgressBar;
import com.shenyuan.superapp.common.api.down.DownCommon;
import com.shenyuan.superapp.common.api.down.DownLoadHelper;
import com.shenyuan.superapp.common.api.down.DownModel;
import com.shenyuan.superapp.common.api.down.DownloadListener;
import com.shenyuan.superapp.common.utils.ShareUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

/**
 * @author ch
 * @date 2021/3/11 16:10
 * desc
 */
public class RecordFragment extends BaseFragment<FragmentRecordBinding, BasePresenter> implements DownloadListener {
    /**
     * 0-下载
     * 1-上传
     */
    private int type;

    private DownAdapter loadAdapter;
    private DownAdapter finishAdapter;

    private boolean showTools;

    public RecordFragment(int type) {
        this.type = type;
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        setArguments(bundle);
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_record;
    }


    @Override
    @SuppressLint("DefaultLocale")
    protected void initView() {
        EventBus.getDefault().register(this);

        loadAdapter = new DownAdapter();
        binding.rvLoading.setLayoutManager(new LinearLayoutManager(context));
        binding.rvLoading.setAdapter(loadAdapter);


        finishAdapter = new DownAdapter();
        binding.rvFinish.setLayoutManager(new LinearLayoutManager(context));
        binding.rvFinish.setAdapter(finishAdapter);


        DownLoadHelper.getInstance().addDownLoadListener(this);

        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }

        setDownModel();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(FileUpdateEvent updateEvent) {
        setDownModel();
    }

    @SuppressLint("DefaultLocale")
    private void setDownModel() {
        loadAdapter.setNewInstance(DownCommon.getLoadingModels(type));
        finishAdapter.setNewInstance(DownCommon.getFinishModels(type));

        binding.tvLoading.setText(String.format("进行中(%d)", loadAdapter.getData().size()));
        binding.tvFinish.setText(String.format("已完成(%d)", finishAdapter.getData().size()));
    }

    @Override
    protected void addListener() {
        binding.tvLoadingTools.setOnClickListener(v -> {
            if (type == 0) {
                for (DownModel downModel : loadAdapter.getData()) {
                    if ("全部开始".equals(getTv(binding.tvLoadingTools))) {
                        if (downModel.getDownState() == DownModel.DOWN_PAUSE) {
                            DownLoadHelper.getInstance().downLoadModel(downModel);
                        }
                        binding.tvLoadingTools.setText("全部暂停");
                    } else {
                        if (downModel.getDownState() == DownModel.DOWN_DEFAULT) {
                            DownModel upModel = DownLoadHelper.getInstance().pauseTask(downModel.getTag());
                            if (upModel != null) {
                                downModel.setDownSize(upModel.getDownSize());
                                downModel.setTotalSize(upModel.getTotalSize());
                                downModel.setDownState(upModel.getDownState());
                            }
                        }
                        binding.tvLoadingTools.setText("全部开始");
                    }
                }
                loadAdapter.notifyDataSetChanged();
            }
        });

        finishAdapter.addChildClickViewIds(R.id.ll_option);
        finishAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ll_option) {
                showTools = true;
                finishAdapter.setShowTools(showTools);
                binding.clTools.setVisibility(View.VISIBLE);
            }
        });

        loadAdapter.addChildClickViewIds(R.id.ll_option);
        loadAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            DownModel downModel = loadAdapter.getItem(position);
            if (view.getId() == R.id.ll_option) {
                pause(position, downModel);
            }
        });

        finishAdapter.setOnItemClickListener((adapter, view, position) -> {
            DownModel model = finishAdapter.getItem(position);
            if (showTools) {
                model.setSelect(!model.isSelect());
                finishAdapter.notifyItemChanged(position);
            } else {
                if (FileUtils.isImage(model.getName())) {
                    BasePreviewActivity.startPreView(getActivity(), model.getDestDir() + model.getName(), null);
                } else if (FileUtils.isVideo(model.getName())) {
                    ARouter.getInstance().build(ARouterPath.Common.COMMON_VIDEO)
                            .withString("videoPath", model.getDestDir() + model.getName())
                            .withString("videoName", model.getName())
                            .navigation();
                } else {
                    ARouter.getInstance().build(ARouterPath.Common.COMMON_FILE)
                            .withString("filePath", model.getDestDir() + model.getName())
                            .navigation();
                }
            }
        });
        finishAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            DownModel model = finishAdapter.getItem(position);
            if (model.getType() == 1) {
                //上传
                ShareUtils.shareBySys(context, model.getUrl());
            } else {
                ShareUtils.shareBySys(context, model.getDestDir() + model.getName());
            }
            return false;
        });

        loadAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (showTools) {
                DownModel model = loadAdapter.getItem(position);
                model.setSelect(!model.isSelect());
                loadAdapter.notifyItemChanged(position);
            }
        });

        binding.cbDistribution.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (DownModel m : finishAdapter.getData()) {
                m.setSelect(isChecked);
            }
            finishAdapter.notifyDataSetChanged();
            for (DownModel m : loadAdapter.getData()) {
                m.setSelect(isChecked);
            }
            loadAdapter.notifyDataSetChanged();
        });
        //删除
        binding.tvDelete.setOnClickListener(v -> new SimDialog.Builder(context)
                .title(getString(R.string.is_delete_school))
                .submitText(getString(R.string.sure)).onBacklistener(new BaseDialog.BaseOnBack() {
                    @Override
                    public void onConfirm() {
                        for (DownModel m : finishAdapter.getData()) {
                            if (m.isSelect()) {
                                m.delete();
                            }
                        }
                        for (DownModel m : loadAdapter.getData()) {
                            if (m.isSelect()) {
                                m.delete();
                                //取消任务
                                DownLoadHelper.getInstance().cancelTask(m.getTag());
                            }
                        }

                        showTools = false;
                        binding.clTools.setVisibility(View.GONE);
                        finishAdapter.setShowTools(showTools);
                        loadAdapter.setShowTools(showTools);

                        setDownModel();
                    }
                }).show());


    }

    private void pause(int position, DownModel downModel) {
        if (downModel.getDownState() == DownModel.DOWN_DEFAULT) {
            downModel.setDownState(DownModel.DOWN_PAUSE);
            DownModel upModel = DownLoadHelper.getInstance().pauseTask(downModel.getTag());


            if (upModel != null) {
                downModel.setDownSize(upModel.getDownSize());
                downModel.setTotalSize(upModel.getTotalSize());
                downModel.setDownState(upModel.getDownState());
            }

            loadAdapter.notifyItemChanged(position);
        } else {
            downModel.setDownState(DownModel.DOWN_DEFAULT);
            DownLoadHelper.getInstance().downLoadModel(downModel);
            loadAdapter.notifyItemChanged(position);
        }
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public void onDestroy() {
        DownLoadHelper.getInstance().removeDownLoadListener(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onStartDownload(String tag) {
        LogUtils.e("onStartDownload", "onStartDownload");
    }

    @Override
    public void onProgress(String tag, int progress, long downSize, long totalSize) {
        int index = getIndexOfDownModel(tag);
        LogUtils.e("onProgress", "tag=" + tag + ",progress=" + progress);
        HProgressBar progressBar = (HProgressBar) loadAdapter.getViewByPosition(index, R.id.pb_down);

        TextView tvSize = (TextView) loadAdapter.getViewByPosition(index, R.id.tv_size);
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                if (progressBar != null) {
                    progressBar.setProgress(progress);
                }
                if (tvSize != null) {
                    tvSize.setText(String.format("%s/%s", FileUtils.getFormatSize(downSize),
                            FileUtils.getFormatSize(totalSize)));
                }
            });
        }

    }

    @Override
    public void onFinishDownload(String tag, File file) {
        setDownModel();
        LogUtils.e("onFinishDownload", "tag=" + tag + ",file=" + file.getPath());
    }

    @Override
    public void onFail(String tag, Throwable ex) {
        setDownModel();
    }

    private int getIndexOfDownModel(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return -1;
        }
        if (loadAdapter.getData().size() > 0) {
            for (DownModel model : loadAdapter.getData()) {
                if (tag.equals(model.getTag())) {
                    return loadAdapter.getData().indexOf(model);
                }
            }
        }
        return -1;
    }

}
