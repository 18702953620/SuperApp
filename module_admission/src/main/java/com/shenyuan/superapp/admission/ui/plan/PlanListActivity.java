package com.shenyuan.superapp.admission.ui.plan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcPlanListBinding;
import com.shenyuan.superapp.admission.adapter.plan.PlanAdapter;
import com.shenyuan.superapp.admission.api.presenter.PlanPresenter;
import com.shenyuan.superapp.admission.api.view.PlanView;
import com.shenyuan.superapp.admission.bean.PlanInfoBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.SimpleStringBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.admission.ui.AdmissionSearchActivity;
import com.shenyuan.superapp.admission.window.plan.PlanCreateWindow;
import com.shenyuan.superapp.admission.window.plan.PlanTimeWindow;
import com.shenyuan.superapp.admission.window.plan.SimpleWindow;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.base.BaseActivity;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/12 11:28
 * desc 招生方案
 */
@Route(path = ARouterPath.Admission.ADMISSION_PLAN_LIST)
public class PlanListActivity extends BaseActivity<AcPlanListBinding, PlanPresenter> implements PlanView {
    private PlanAdapter planAdapter;

    public static final int REQUEST_CODE_PLAN_LIST = 100;
    /**
     * 审核状态
     */
    private SimpleWindow simpleWindow;
    /**
     * 审核状态
     */
    private PlanCreateWindow createWindow;
    /**
     * 时间安排
     */
    private PlanTimeWindow timeWindow;

    /**
     * 审核状态
     */
    private List<SimpleBean> emamineBeans;
    /**
     * 出差任务
     */
    private List<SimpleBean> taskBeans;

    /**
     * 创建时间
     */
    private List<SimpleStringBean> createBeans;


    private int page = 1;

    private String dateType;

    private int taskType = -1;

    private int status = -1;

    private String beginTime;
    private String endTime;

    @Override
    protected PlanPresenter createPresenter() {
        return new PlanPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_plan_list;
    }

    @Override
    protected void initView() {

        getPlanList();
        //审核状态
        presenter.getExamineStateList();
        //出差任务
        presenter.getPlanTaskList();
        //创建时间
        presenter.getPlanCreateList();

        planAdapter = new PlanAdapter();
        binding.rvPlan.setLayoutManager(new LinearLayoutManager(context));
        binding.rvPlan.setAdapter(planAdapter);
    }

    /**
     * 方案列表
     */
    private void getPlanList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", 10);
        if (!TextUtils.isEmpty(dateType)) {
            map.put("dateType", dateType);
        }
        if (!TextUtils.isEmpty(endTime)) {
            map.put("endTime", endTime);
        }
        if (!TextUtils.isEmpty(beginTime)) {
            map.put("beginTime", beginTime);
        }
        if (taskType != -1) {
            map.put("taskType", taskType);
        }
        if (status != -1) {
            map.put("status", status);
        }

        presenter.getPlanList(map);
    }

    @Override
    protected void addListener() {
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

        binding.llState.setOnClickListener(v -> {
            if (simpleWindow == null) {
                simpleWindow = new SimpleWindow(context);
            }
            simpleWindow.setData(emamineBeans);
            showUpArrow(binding.tvState);
            simpleWindow.showFullAsDropDown(this, v, bean -> {
                status = bean.getKey();
                binding.tvState.setText(bean.getValue());
                binding.tvState.setTextColor(getValuesColor(R.color.color_0077ff));

                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                binding.tvState.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                page = 1;
                getPlanList();
            });

            simpleWindow.setOnDismissListener(() -> showDownArrow(binding.tvState));

        });
        binding.llCreate.setOnClickListener(v -> {
            if (createWindow == null) {
                createWindow = new PlanCreateWindow(context);
            }
            showUpArrow(binding.tvCreate);

            createWindow.setData(createBeans);
            createWindow.showFullAsDropDown(this, v, bean -> {
                dateType = bean.getKey();

                binding.tvCreate.setText(bean.getValue());
                binding.tvCreate.setTextColor(getValuesColor(R.color.color_0077ff));

                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                binding.tvCreate.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                page = 1;
                getPlanList();
            });
            createWindow.setOnDismissListener(() -> {
                showDownArrow(binding.tvCreate);
            });
        });
        binding.llTask.setOnClickListener(v -> {
            if (simpleWindow == null) {
                simpleWindow = new SimpleWindow(context);
            }
            simpleWindow.setData(taskBeans);
            showUpArrow(binding.tvTask);
            simpleWindow.showFullAsDropDown(this, v, bean -> {
                taskType = bean.getKey();

                binding.tvTask.setText(bean.getValue());
                binding.tvTask.setTextColor(getValuesColor(R.color.color_0077ff));

                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                binding.tvTask.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                page = 1;
                getPlanList();
            });
            simpleWindow.setOnDismissListener(() -> {
                showDownArrow(binding.tvTask);
            });
        });

        binding.llTime.setOnClickListener(v -> {
            if (timeWindow == null) {
                timeWindow = new PlanTimeWindow(context);
            }
            showUpArrow(binding.tvTime);
            timeWindow.showFullAsDropDown(this, v, (start, end) -> {
                beginTime = start;
                endTime = end;
                binding.tvTime.setTextColor(getValuesColor(R.color.color_0077ff));

                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                binding.tvTime.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                page = 1;
                getPlanList();
            });

            timeWindow.setOnDismissListener(() -> {
                showDownArrow(binding.tvTime);
            });
        });
        planAdapter.addChildClickViewIds(R.id.content, R.id.tv_edit);
        planAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PlanListBean bean = planAdapter.getItem(position);
            if (view.getId() == R.id.tv_edit) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_PLAN_INFO)
                        .withInt("planId", bean.getId())
                        .withInt("status", bean.getStatus())
                        .withBoolean("showEdit", false)
                        .withBoolean("editAble", true)
                        .navigation(this, REQUEST_CODE_PLAN_LIST);
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_PLAN_INFO)
                        .withInt("planId", bean.getId())
                        .withInt("status", bean.getStatus())
                        .withBoolean("showEdit", true)
                        .withBoolean("editAble", false)
                        .navigation(this, REQUEST_CODE_PLAN_LIST);
            }
        });

        if (PermissionCommon.hasPlanAdd()) {
            binding.btnAdd.setVisibility(View.VISIBLE);
        } else {
            binding.btnAdd.setVisibility(View.GONE);
        }

        binding.btnAdd.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_PLAN_ADD)
                .navigation(this, REQUEST_CODE_PLAN_LIST));

        binding.llSchoolSearch.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_SEARCH)
                .withInt("searchType", AdmissionSearchActivity.SEARCH_TYPE_PLAN)
                .navigation());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void showDownArrow(TextView tv) {
        if (tv == null) {
            return;
        }

        Drawable arrowGrayUp = getResources().getDrawable(R.mipmap.ic_school_arrow_down);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowGrayUp, null);
    }

    private void showUpArrow(TextView tv) {
        if (tv == null) {
            return;
        }

        Drawable arrowGrayUp = getDrawableRes(R.mipmap.ic_school_arrow_up);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowGrayUp, null);
    }

    @Override
    public void onExamineList(List<SimpleBean> o) {
        emamineBeans = o;
    }

    @Override
    public void onTaskList(List<SimpleBean> o) {
        taskBeans = o;
    }


    @Override
    public void showError(String msg) {
        super.showError(msg);
        binding.mrlPlan.finishRefreshAndLoadMore();
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
    public void onCreateList(List<SimpleStringBean> o) {
        createBeans = o;
    }

    @Override
    public void onFeeTypeList(List<SimpleStringBean> o) {

    }

    @Override
    public void onPropagationWayDictListList(List<SimpleStringBean> o) {

    }

    @Override
    public void onMadeList(List<SimpleBean> o) {

    }

    @Override
    public void onPlanStaffList(List<StaffBean> o) {

    }


    @Override
    public void onAddPlan(String o) {

    }

    @Override
    public void onPlanInfo(PlanInfoBean o) {

    }

    @Override
    public void onAduitPlan(String o) {

    }

    @Override
    public void onUpdatePlan(String o) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PLAN_LIST) {
            page = 1;
            getPlanList();
        }
    }
}
