package com.shenyuan.superapp.common.video;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.common.R;
import com.shenyuan.superapp.common.databinding.AcCommonVideoBinding;

/**
 * @author ch
 * @date 2021/4/7 10:26
 * desc
 */
@Route(path = ARouterPath.Common.COMMON_VIDEO)
public class VideoActivity extends BaseActivity<AcCommonVideoBinding, BasePresenter> {

    @Autowired
    public String videoPath;
    @Autowired
    public String videoName;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_common_video;
    }

    @Override
    protected void initView() {
        binding.title.setTitle(videoName);
        binding.jzVideo.setUp(videoPath, videoName);
    }

    @Override
    protected void addListener() {

    }
}
