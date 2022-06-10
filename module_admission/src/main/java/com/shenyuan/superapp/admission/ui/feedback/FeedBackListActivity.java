package com.shenyuan.superapp.admission.ui.feedback;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcFeedbackListBinding;
import com.shenyuan.superapp.admission.adapter.feed.FeedBackAdapter;
import com.shenyuan.superapp.admission.api.presenter.FeedBackPresenter;
import com.shenyuan.superapp.admission.api.view.FeedBackView;
import com.shenyuan.superapp.admission.bean.FeedBackInfoBean;
import com.shenyuan.superapp.admission.bean.FeedBackListBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.admission.ui.AdmissionSearchActivity;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/17 9:48
 * desc 招生反馈
 */
@Route(path = ARouterPath.Admission.ADMISSION_FEEDBACK_LIST)
public class FeedBackListActivity extends BaseActivity<AcFeedbackListBinding, FeedBackPresenter> implements FeedBackView {

    private FeedBackAdapter feedAdapter;
    public static final int REQUEST_CODE_FEED_LIST = 100;
    private int page = 1;

    @Override
    protected FeedBackPresenter createPresenter() {
        return new FeedBackPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_feedback_list;
    }

    @Override
    protected void initView() {
        feedAdapter = new FeedBackAdapter();
        binding.rvFeedback.setLayoutManager(new LinearLayoutManager(context));
        binding.rvFeedback.setAdapter(feedAdapter);

        getFeedList();
    }

    private void getFeedList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", 10);
        map.put("sort", 0);
        presenter.getFeedBackList(map);
    }

    @Override
    protected void addListener() {
        feedAdapter.addChildClickViewIds(R.id.tv_top, R.id.tv_edit, R.id.tv_delete, R.id.content);
        feedAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            FeedBackListBean bean = feedAdapter.getItem(position);
            if (view.getId() == R.id.tv_edit) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_FEEDBACK_ADD)
                        .withInt("feedId", bean.getId())
                        .withBoolean("editAble", true)
                        .navigation(this, REQUEST_CODE_FEED_LIST);
            } else if (view.getId() == R.id.tv_delete) {
                presenter.deleteFeed(bean.getId());
            } else if (view.getId() == R.id.tv_top) {
                presenter.topFeedBackById(bean.getId());
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_FEEDBACK_ADD)
                        .withInt("feedId", bean.getId())
                        .withBoolean("editAble", false)
                        .withBoolean("showEdit", true)
                        .navigation();
            }

            EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) feedAdapter.getViewByPosition(position, R.id.esm_school);
            if (menuLayout != null) {
                menuLayout.resetStatus();
            }

        });

        binding.mrlFeedback.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getFeedList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getFeedList();

            }
        });
        if (PermissionCommon.hasFeedAdd()) {
            binding.btnAdd.setVisibility(View.VISIBLE);
        } else {
            binding.btnAdd.setVisibility(View.GONE);
        }

        binding.btnAdd.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_FEEDBACK_ADD)
                .navigation(this, REQUEST_CODE_FEED_LIST));

        binding.llFeedbackSearch.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_SEARCH)
                .withInt("searchType", AdmissionSearchActivity.SEARCH_TYPE_FEED_BACK)
                .navigation());

    }

    @Override
    public void onIntentList(List<SimpleBean> o) {

    }

    @Override
    public void onGiftList(List<SimpleBean> o) {

    }

    @Override
    public void onListingList(List<SimpleBean> o) {

    }

    @Override
    public void onVisitList(List<SimpleBean> o) {

    }

    @Override
    public void onPlanStaffList(List<StaffBean> o) {

    }

    @Override
    public void onAddFeedBack(String o) {

    }

    @Override
    public void onFeedBackList(List<FeedBackListBean> o) {
        if (page == 1) {
            feedAdapter.setNewInstance(o);
        } else {
            feedAdapter.addData(o);
        }
        binding.mrlFeedback.finishRefreshAndLoadMore();
    }

    @Override
    public void onFeedBackInfo(FeedBackInfoBean o) {

    }

    @Override
    public void onUpdateFeedBack(String o) {

    }

    @Override
    public void onDeleteFeedBack(String o) {
        showToast(getString(R.string.succ_delete));
        page = 1;
        getFeedList();
    }

    @Override
    public void onTopFeedBack(String o) {
        showToast(o);
        page = 1;
        getFeedList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FEED_LIST && resultCode == RESULT_OK) {
            page = 1;
            getFeedList();
        }
    }
}
