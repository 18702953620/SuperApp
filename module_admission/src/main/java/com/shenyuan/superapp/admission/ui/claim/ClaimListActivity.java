package com.shenyuan.superapp.admission.ui.claim;

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
import com.shenyuan.admission.databinding.AcClaimListBinding;
import com.shenyuan.superapp.admission.adapter.claim.ClaimAdapter;
import com.shenyuan.superapp.admission.api.presenter.ClaimPresenter;
import com.shenyuan.superapp.admission.api.view.ClaimView;
import com.shenyuan.superapp.admission.bean.ClaimInfoBean;
import com.shenyuan.superapp.admission.bean.ClaimListBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.admission.ui.AdmissionSearchActivity;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.base.BaseActivity;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/17 9:48
 * desc 招生报账
 */
@Route(path = ARouterPath.Admission.ADMISSION_REIMBURSE_LIST)
public class ClaimListActivity extends BaseActivity<AcClaimListBinding, ClaimPresenter> implements ClaimView {

    private ClaimAdapter claimAdapter;
    public static final int REQUEST_CODE_CLAIM_LIST = 100;
    private int page = 1;

    @Override
    protected ClaimPresenter createPresenter() {
        return new ClaimPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_claim_list;
    }

    @Override
    protected void initView() {
        claimAdapter = new ClaimAdapter();
        binding.rvReimbruse.setLayoutManager(new LinearLayoutManager(context));
        binding.rvReimbruse.setAdapter(claimAdapter);

        getClaimList();
    }

    private void getClaimList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", 10);
        map.put("sort", 0);
        presenter.getClaimList(map);
    }

    @Override
    protected void addListener() {
        claimAdapter.addChildClickViewIds(R.id.tv_top, R.id.tv_edit, R.id.tv_delete, R.id.content);
        claimAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ClaimListBean bean = claimAdapter.getItem(position);
            if (view.getId() == R.id.tv_edit) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_REIMBURSE_ADD)
                        .withInt("claimId", bean.getId())
                        .withBoolean("editAble", true)
                        .navigation(this, REQUEST_CODE_CLAIM_LIST);
            } else if (view.getId() == R.id.tv_delete) {
                presenter.deleteClaim(bean.getId());
            } else if (view.getId() == R.id.tv_top) {
                presenter.topClaimById(bean.getId());
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_REIMBURSE_ADD)
                        .withInt("claimId", bean.getId())
                        .withBoolean("editAble", false)
                        .withBoolean("showEdit", true)
                        .navigation();
            }

            EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) claimAdapter.getViewByPosition(position, R.id.esm_school);
            if (menuLayout != null) {
                menuLayout.resetStatus();
            }

        });
        binding.mrlReimbruse.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getClaimList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getClaimList();

            }
        });
        if (PermissionCommon.hasClaimAdd()) {
            binding.btnAdd.setVisibility(View.VISIBLE);
        } else {
            binding.btnAdd.setVisibility(View.GONE);
        }

        binding.btnAdd.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_REIMBURSE_ADD)
                .navigation(this, REQUEST_CODE_CLAIM_LIST));

        binding.llReimbruseSearch.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_SEARCH)
                .withInt("searchType", AdmissionSearchActivity.SEARCH_TYPE_CLAIM)
                .navigation());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CLAIM_LIST && resultCode == RESULT_OK) {
            page = 1;
            getClaimList();
        }
    }

    @Override
    public void onAddClaim(String o) {

    }

    @Override
    public void onPlanList(List<PlanListBean> o) {

    }

    @Override
    public void onClaimList(List<ClaimListBean> o) {
        if (page == 1) {
            claimAdapter.setNewInstance(o);
        } else {
            claimAdapter.addData(o);
        }
        binding.mrlReimbruse.finishRefreshAndLoadMore();
    }

    @Override
    public void onClaimInfo(ClaimInfoBean o) {

    }

    @Override
    public void onAduitClaim(String o) {

    }

    @Override
    public void onDeleteClaim(String o) {
        showToast(getString(R.string.succ_delete));
        page = 1;
        getClaimList();

    }

    @Override
    public void onTopClaim(String o) {
        showToast(getString(R.string.succ_option));
        page = 1;
        getClaimList();
    }

    @Override
    public void onUpdateClaim(String o) {

    }
}
