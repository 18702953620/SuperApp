package com.shenyuan.superapp.admission.ui.claim;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcPickPlanBinding;
import com.shenyuan.superapp.admission.adapter.claim.PickPlanAdapter;
import com.shenyuan.superapp.admission.api.presenter.ClaimPresenter;
import com.shenyuan.superapp.admission.api.view.ClaimView;
import com.shenyuan.superapp.admission.bean.ClaimInfoBean;
import com.shenyuan.superapp.admission.bean.ClaimListBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/23 13:40
 * desc
 */
@Route(path = ARouterPath.Admission.ADMISSION_REIMBURSE_PLAN)
public class PickPlanActivity extends BaseActivity<AcPickPlanBinding, ClaimPresenter> implements ClaimView {
    private PickPlanAdapter planAdapter;
    private int page = 1;

    @Override
    protected ClaimPresenter createPresenter() {
        return new ClaimPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_pick_plan;
    }

    @Override
    protected void initView() {
        planAdapter = new PickPlanAdapter();
        binding.rvPlan.setLayoutManager(new LinearLayoutManager(context));
        binding.rvPlan.setAdapter(planAdapter);

        getPlanList();
    }

    private void getPlanList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        if (!TextUtils.isEmpty(getTv(binding.etSearch))) {
            map.put("planName", getTv(binding.etSearch));
        }
        map.put("limit", 10);
        presenter.getPlanByCondition(map);
    }

    @Override
    protected void addListener() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getPlanList();
            }
        });


        binding.mrlPlan.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getPlanList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getPlanList();
            }
        });

        planAdapter.setOnItemClickListener((adapter, view, position) -> {
            PlanListBean bean = planAdapter.getItem(position);
            bean.setSelect(!bean.isSelect());
            planAdapter.notifyItemChanged(position);
            double total = getTotalPrice();
            binding.stTotal.setText(total + "元");
        });


        binding.tvSubmit.setOnClickListener(v -> {
            StringBuilder ids = new StringBuilder();
            for (PlanListBean b : planAdapter.getData()) {
                if (b.isSelect()) {
                    if (ids.length() > 0) {
                        ids.append(",");
                    }
                    ids.append(b.getId());
                }
            }
            if (ids.length() == 0) {
                showToast("请选择经费预算");
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("planIds", ids.toString());
            intent.putExtra("total", binding.stTotal.getText());
            setResult(RESULT_OK, intent);
            finish();

        });

        binding.tvRest.setOnClickListener(v -> {
            for (PlanListBean b : planAdapter.getData()) {
                b.setSelect(false);
            }
            planAdapter.notifyDataSetChanged();

            binding.stTotal.clear();
        });
    }


    private double getTotalPrice() {
        double total = 0;
        for (PlanListBean b : planAdapter.getData()) {
            if (b.isSelect()) {
                total += b.getTotalCostMoney();
            }
        }
        return BigDecimal.valueOf(total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    public void onAddClaim(String o) {

    }

    @Override
    public void onPlanList(List<PlanListBean> o) {
        if (page == 1) {
            planAdapter.setNewInstance(o);
        } else {
            planAdapter.addData(o);
        }
        binding.mrlPlan.finishRefreshAndLoadMore();
    }

    @Override
    public void onClaimList(List<ClaimListBean> o) {

    }

    @Override
    public void onClaimInfo(ClaimInfoBean o) {

    }

    @Override
    public void onAduitClaim(String o) {

    }

    @Override
    public void onDeleteClaim(String o) {

    }

    @Override
    public void onTopClaim(String o) {

    }

    @Override
    public void onUpdateClaim(String o) {

    }
}
