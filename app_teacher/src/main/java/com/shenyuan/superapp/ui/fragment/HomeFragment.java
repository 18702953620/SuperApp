package com.shenyuan.superapp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.request.RequestOptions;
import com.ch.cper.CPermission;
import com.ch.cper.PermissGroup;
import com.ch.cper.listener.PermissListener;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.superapp.BuildConfig;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.adapter.HomeAdapter;
import com.shenyuan.superapp.adapter.NewsTabAdapter;
import com.shenyuan.superapp.api.presenter.AuthPresenter;
import com.shenyuan.superapp.api.presenter.HomePresenter;
import com.shenyuan.superapp.api.view.AuthView;
import com.shenyuan.superapp.api.view.HomeView;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.base.BaseFragment;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.utils.AppUtils;
import com.shenyuan.superapp.bean.BannerBean;
import com.shenyuan.superapp.bean.ExamStudentInfoBean;
import com.shenyuan.superapp.bean.MenuBean;
import com.shenyuan.superapp.bean.NewsTypeBean;
import com.shenyuan.superapp.bean.ServiceBean;
import com.shenyuan.superapp.common.api.down.DownLoadHelper;
import com.shenyuan.superapp.common.api.down.DownloadListener;
import com.shenyuan.superapp.common.api.presenter.AdvertPresenter;
import com.shenyuan.superapp.common.api.view.AdvertView;
import com.shenyuan.superapp.common.bean.AdvertBean;
import com.shenyuan.superapp.common.bean.NewsBean;
import com.shenyuan.superapp.common.popup.AdvertDialog;
import com.shenyuan.superapp.common.scan.BarCodeUtils;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superapp.common.web.WebActivity;
import com.shenyuan.superapp.databinding.FmHomeBinding;
import com.shenyuan.superapp.ui.AppCaptureActivity;
import com.shenyuan.superapp.ui.dialog.UpdateDialog;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/1/4-10:58
 * desc
 */
public class HomeFragment extends BaseFragment<FmHomeBinding, HomePresenter> implements HomeView, AuthView, AdvertView {

    private HomeAdapter menuAdapter;

    private AuthPresenter authPresenter;

    private NewsTabAdapter tabsAdapter;

    private final int REQUEST_CODE_USERINFOR = 120;
    private AdvertPresenter advertPresenter;

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fm_home;
    }

    @Override
    protected void initView() {
        authPresenter = new AuthPresenter(this);
        //获取轮播图
        presenter.getBannerList();
        //我的服务
        presenter.getMyService();
        //资讯类型
        presenter.getNewsTypeList();
        //公告列表
        presenter.getNoticeList();

        advertPresenter = new AdvertPresenter(this);
        //弹窗广告
        advertPresenter.getPopupAdvert(AppConstant.STUDENT_CLIENT_ID);

        menuAdapter = new HomeAdapter();
        binding.rvMenu.setLayoutManager(new GridLayoutManager(context, 5));
        binding.rvMenu.setAdapter(menuAdapter);

        //版本更新
        presenter.findAppMaxVersion(AppUtils.getVersionCode(context));
    }

    @Override
    protected void addListener() {
        binding.llScan.setOnClickListener(v -> scanCode());
        binding.llRobot.setOnClickListener(v -> {
            if (BuildConfig.DEBUG) {
                ARouterPath.router(ARouterPath.AppTeacher.APP_EXAM_INFO);
            }
        });
        //搜索
        binding.cvSearch.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.AppTeacher.APP_SEARCH).navigation());

        menuAdapter.setItemClickListener((bean, view, position) -> {
            if (getString(R.string.more).equals(bean.getServiceName())) {
                ARouter.getInstance().build(ARouterPath.AppTeacher.APP_MORE).navigation(getActivity(), REQUEST_CODE_USERINFOR);
                return;
            }
            ARouterPath.router(bean.getRouterUrl());
        });
        binding.mrlHome.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (tabsAdapter != null) {
                    NewsFragment fragment = tabsAdapter.getFragmentByPosition(binding.tlTitle.getSelectedTabPosition());
                    if (fragment != null) {
                        fragment.loadMore(() -> binding.mrlHome.finishLoadMore());
                    }
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //获取轮播图
                presenter.getBannerList();
                //我的服务
                presenter.getMyService();
                //资讯类型
                presenter.getNewsTypeList();
                //公告列表
                presenter.getNoticeList();
            }
        });

        binding.tlTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.vpNews.setCurrentItem(tab.getPosition());
                if (tab.getCustomView() != null) {
                    TextView tv_label = tab.getCustomView().findViewById(R.id.tv_label);
                    tv_label.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    TextView tv_label = tab.getCustomView().findViewById(R.id.tv_label);
                    tv_label.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    TextView tv_label = tab.getCustomView().findViewById(R.id.tv_label);
                    tv_label.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.vpNews.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tlTitle.selectTab(binding.tlTitle.getTabAt(position));
            }
        });
    }

    /**
     * 扫码
     */
    private void scanCode() {
        if (getActivity() == null) {
            return;
        }
        CPermission.with(context).permiss().permission(PermissGroup.CAMERA).listener(new PermissListener<String>() {
            @Override
            public void onGranted(List<String> list) {
                BarCodeUtils.startScan(getActivity(), AppCaptureActivity.class, result -> {
                    if (result == null) {
                        return;
                    }
                    if ("scanLogin".equals(result.getAction())) {
                        //扫码登录
                        authPresenter.loginScan(result.getData());
                    } else if ("indepExam".equals(result.getAction())) {

                        ARouter.getInstance().build(ARouterPath.AppTeacher.APP_EXAM_INFO)
                                .withString("uuid", result.getData())
                                .navigation();
                    }
                });
            }

            @Override
            public void onDenied(List<String> list) {

            }
        }).start();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public void onDestroy() {
        authPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onLoginScan(String uuid) {
        ARouter.getInstance().build(ARouterPath.AppTeacher.APP_AUTH).withString("uuid", uuid).navigation();
    }

    @Override
    public void onLoginConfirm() {

    }

    @Override
    public void onBannerList(List<BannerBean> o) {
        binding.banner.addBannerLifecycleObserver(this);
        binding.banner.setAdapter(new BannerImageAdapter<BannerBean>(o) {

            @Override
            public void onBindView(BannerImageHolder holder, BannerBean data, int position, int size) {
                GlideUtils.load(context, data.getPictureUrl(), holder.imageView, new RequestOptions()
                        .centerCrop().error(R.mipmap.ic_default_banner)
                        .placeholder(R.mipmap.ic_default_banner)
                        .override(730, 330));
            }
        });
        binding.banner.setIndicator(new CircleIndicator(context));

        binding.banner.setOnBannerListener((OnBannerListener<BannerBean>) (data, position) -> {
            if (!TextUtils.isEmpty(data.getJumpUrl())) {
                WebActivity.loadUrl(context, data.getJumpUrl());
            }
        });
    }

    @Override
    public void onServiceList(List<ServiceBean> o) {

    }

    @Override
    public void onMyServiceList(List<MenuBean> o) {
        if (o == null) {
            o = new ArrayList<>();
        }
        if (o.size() > 9) {
            o = o.subList(0, 9);
        }

        MenuBean dt = new MenuBean();
        dt.setServiceName(getString(R.string.more));
        o.add(dt);

        menuAdapter.setNewInstance(o);
    }

    @Override
    public void onEditSucc(String o) {

    }

    @Override
    public void onNewsTypeList(List<NewsTypeBean> o) {
        binding.tlTitle.removeAllTabs();
        List<String> list = new ArrayList<>();
        if (o != null && o.size() > 0) {
            for (NewsTypeBean b : o) {
                list.add(b.getNewsTypeName());

                TabLayout.Tab tab1 = binding.tlTitle.newTab();
                tab1.setCustomView(R.layout.cust_table_view);
                if (tab1.getCustomView() != null) {
                    TextView tv_name = tab1.getCustomView().findViewById(R.id.tv_name);
                    tv_name.setText(b.getNewsTypeName());
                }

                if (tab1.getCustomView() != null) {
                    TextView tv_label = tab1.getCustomView().findViewById(R.id.tv_label);
                    if (o.indexOf(b) == 0) {
                        tv_label.setVisibility(View.VISIBLE);
                    } else {
                        tv_label.setVisibility(View.INVISIBLE);
                    }
                    binding.tlTitle.addTab(tab1);
                }
            }
        }

        if (getActivity() != null) {
            tabsAdapter = new NewsTabAdapter(getActivity(), list, o);
            binding.vpNews.setAdapter(tabsAdapter);
            binding.mrlHome.finishRefresh();
        }
    }


    @Override
    public void onNewsList(List<NewsBean> o) {
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        binding.mrlHome.finishRefresh();
    }

    @Override
    public void onNoticeList(List<NewsBean> o) {
        if (o != null && o.size() > 0) {
            binding.tvNoticeContent.setText(o.get(0).getTitle());

            binding.tvNoticeContent.setOnClickListener(v ->
                    ARouter.getInstance().build(ARouterPath.Common.COMMON_WEB).withString("noticeId",
                            String.valueOf(o.get(0).getId()))
                            .navigation());
        }
    }

    @Override
    public void onUpdate(HashMap<String, String> o) {
        if (o != null) {
            String description = o.get("description");
            String url = o.get("url");

            UpdateDialog updateDialog = new UpdateDialog(context);
            updateDialog.setContent(description);
            updateDialog.showDialog(new BaseDialog.BaseOnBack() {
                @Override
                public void onConfirm() {

                    DownLoadHelper.getInstance().downLoadFile(url, new DownloadListener() {
                        @Override
                        public void onStartDownload(String tag) {
                            showLoading();
                        }

                        @Override
                        public void onProgress(String tag, int progress, long downSize, long totalSize) {

                        }

                        @Override
                        public void onFinishDownload(String tag, File file) {
                            hideLoading();
                            CPermission.with(context).install().file(file).start();
                        }

                        @Override
                        public void onFail(String tag, Throwable ex) {
                            hideLoading();
                            if (ex != null && ex.getMessage() != null) {
                                showToast(ex.getMessage());
                            }
                        }
                    });
                }
            });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_USERINFOR && resultCode == Activity.RESULT_OK) {
            presenter.getMyService();
        }
    }

    @Override
    public void onLoadingAdvert(List<AdvertBean> o) {
        if (o != null && o.size() > 0) {
            AdvertDialog advertDialog = new AdvertDialog(context, o);
            advertDialog.show();
        }
    }
}
