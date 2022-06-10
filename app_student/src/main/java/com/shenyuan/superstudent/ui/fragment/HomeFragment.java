package com.shenyuan.superstudent.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.request.RequestOptions;
import com.ch.cper.CPermission;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.base.BaseFragment;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.utils.AppUtils;
import com.shenyuan.superapp.base.widget.recy.pager.CarouselLayoutManager;
import com.shenyuan.superapp.base.widget.recy.pager.CenterSnapHelper;
import com.shenyuan.superapp.base.widget.recy.pager.ScrollHelper;
import com.shenyuan.superapp.common.api.down.DownLoadHelper;
import com.shenyuan.superapp.common.api.down.DownloadListener;
import com.shenyuan.superapp.common.api.presenter.AdvertPresenter;
import com.shenyuan.superapp.common.bean.AdvertBean;
import com.shenyuan.superapp.common.bean.NewsBean;
import com.shenyuan.superapp.common.popup.AdvertDialog;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superapp.common.web.WebActivity;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.adapter.HomeMenuAdapter;
import com.shenyuan.superstudent.adapter.NewsTabAdapter;
import com.shenyuan.superstudent.api.presenter.HomePresenter;
import com.shenyuan.superapp.common.api.view.AdvertView;
import com.shenyuan.superstudent.api.view.HomeView;
import com.shenyuan.superstudent.bean.BannerBean;
import com.shenyuan.superstudent.bean.MenuBean;
import com.shenyuan.superstudent.bean.NewsTypeBean;
import com.shenyuan.superstudent.bean.ServiceBean;
import com.shenyuan.superstudent.databinding.FmHomeBinding;
import com.shenyuan.superstudent.ui.dialog.UpdateDialog;
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
 * @date 2021/5/8 11:08
 * desc
 */
public class HomeFragment extends BaseFragment<FmHomeBinding, HomePresenter> implements HomeView, AdvertView {
    private NewsTabAdapter tabsAdapter;
    private HomeMenuAdapter homeMenuAdapter;
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

        //轮播图
        presenter.getBannerList();
        //资讯类型
        presenter.getNewsTypeList(1);
        //我的服务
        presenter.getMyService();
        //最新版本
        presenter.findAppMaxVersion(AppUtils.getVersionCode(context));

        advertPresenter = new AdvertPresenter(this);
        //弹窗广告
        advertPresenter.getPopupAdvert(AppConstant.STUDENT_CLIENT_ID);



        homeMenuAdapter = new HomeMenuAdapter();

        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager(
                new CarouselLayoutManager.Builder(context, 440)
                        .setMinScale(0.65f)
                        .setMoveSpeed(1.5f)
                        .setOrientation(CarouselLayoutManager.HORIZONTAL));
        carouselLayoutManager.setInfinite(true);


        binding.rvMenu.setLayoutManager(carouselLayoutManager);
        binding.rvMenu.setAdapter(homeMenuAdapter);

        homeMenuAdapter.setItemClickListener((bean, view, position) -> {
            if (TextUtils.isEmpty(TokenCommon.getToken())) {
                showLoginDialog();
                return;
            }
            if (getString(R.string.more).equals(bean.getServiceName())) {
                ARouter.getInstance().build(ARouterPath.AppStudent.APP_STUDENT_MORE).navigation(getActivity(), REQUEST_CODE_USERINFOR);
            }
            ScrollHelper.smoothScrollToTargetView(binding.rvMenu, view);

        });

        CenterSnapHelper snapHelper = new CenterSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvMenu);
    }

    @Override
    protected void addListener() {
        //搜索
        binding.cvSearch.setOnClickListener(v -> ARouterPath.router(ARouterPath.AppStudent.APP_STUDENT_SEARCH));

        binding.tlTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() != 0) {
                    if (TextUtils.isEmpty(TokenCommon.getToken())) {
                        showLoginDialog();
                        binding.tlTitle.selectTab(binding.tlTitle.getTabAt(0));
                        return;
                    }
                }

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
                if (tab.getPosition() != 0) {
                    if (TextUtils.isEmpty(TokenCommon.getToken())) {
                        showLoginDialog();
                        binding.tlTitle.selectTab(binding.tlTitle.getTabAt(0));
                        return;
                    }
                }

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
                presenter.getNewsTypeList(1);
            }
        });
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
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

            if (TextUtils.isEmpty(TokenCommon.getToken())) {
                showLoginDialog();
                return;
            }

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
        MenuBean dt = new MenuBean();
        dt.setServiceName(getString(R.string.more));
        o.add(dt);
        homeMenuAdapter.setNewInstance(o);
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
    public void onNoticeList(List<NewsBean> o) {

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (advertPresenter != null) {
            advertPresenter.detachView();
        }
    }
}
