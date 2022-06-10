package com.shenyuan.superapp.common.file;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.common.BaseCommon;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superapp.common.R;
import com.shenyuan.superapp.common.databinding.AcCommonFileBinding;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsReaderView;
import com.tencent.smtt.sdk.ValueCallback;

import java.io.File;

/**
 * @author ch
 * @date 2021/4/7 11:02
 * desc
 */
@Route(path = ARouterPath.Common.COMMON_FILE)
public class FileActivity extends BaseActivity<AcCommonFileBinding, BasePresenter> implements TbsReaderView.ReaderCallback {

    private TbsReaderView tbsReaderView;
    @Autowired
    public String filePath;

    private String type;

    private String temPath;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_common_file;
    }

    @Override
    protected void initView() {
        //实例化TbsReaderView，然后将它装入我们准备的容器
        tbsReaderView = new TbsReaderView(context, this);
        binding.flFile.addView(tbsReaderView, new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        if (TextUtils.isEmpty(filePath)) {
            return;
        }

        if (!TextUtils.isEmpty(filePath) && filePath.contains(".")) {
            type = filePath.substring(filePath.lastIndexOf(".") + 1);
        }
        temPath = BaseCommon.getAppContext().getExternalFilesDir(null).getPath() + "/tem/";
        File tmpFile = new File(temPath);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        File file = new File(filePath);
        if (!file.exists()) {
            showToast("文件不存在");
            return;
        }

        previewFile(file);

    }

    @Override
    protected void addListener() {

    }

    /**
     * 预览
     *
     * @param file file
     */
    private void previewFile(File file) {
        Bundle bundle = new Bundle();
        //文件路径
        bundle.putString(TbsReaderView.KEY_FILE_PATH, file.getPath());
        //临时文件的路径，必须设置，否则会报错
        bundle.putString(TbsReaderView.KEY_TEMP_PATH, temPath);
        //准备
        boolean result = tbsReaderView.preOpen(type, false);
        if (result) {
            //预览文件
            tbsReaderView.openFile(bundle);
        } else {
            showToast("预览失败，请退出重试");
        }
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
    }


    @Override
    protected void onDestroy() {
        if (null != tbsReaderView) {
            tbsReaderView.onStop();
        }
        super.onDestroy();
    }
}
