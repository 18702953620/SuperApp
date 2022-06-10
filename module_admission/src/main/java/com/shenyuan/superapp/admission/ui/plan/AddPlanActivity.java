package com.shenyuan.superapp.admission.ui.plan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcAddPlanBinding;
import com.shenyuan.admission.databinding.ItemPlanLineBinding;
import com.shenyuan.superapp.admission.api.presenter.PlanPresenter;
import com.shenyuan.superapp.admission.api.view.PlanView;
import com.shenyuan.superapp.admission.bean.PlanInfoBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.SimpleStringBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/13 10:50
 * desc 添加方案
 */
@Route(path = ARouterPath.Admission.ADMISSION_PLAN_ADD)
public class AddPlanActivity extends BaseActivity<AcAddPlanBinding, PlanPresenter> implements PlanView {
    private BaseQuickAdapter<HashMap<String, Object>, BaseDataBindingHolder> lineAdapter;

    public static final int REQUEST_CODE_PLAN_INFO = 100;
    public static final int REQUEST_FUND_CODE = 101;

    private HashMap<String, Object> allMap;

    @Override
    protected PlanPresenter createPresenter() {
        return new PlanPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_add_plan;
    }

    @Override
    protected void initView() {
        lineAdapter = new BaseQuickAdapter<HashMap<String, Object>, BaseDataBindingHolder>(R.layout.item_plan_line) {
            @SuppressLint("DefaultLocale")
            @Override
            protected void convert(@NotNull BaseDataBindingHolder holder, HashMap<String, Object> s) {
                ItemPlanLineBinding bi = (ItemPlanLineBinding) holder.getDataBinding();
                if (bi == null) {
                    return;
                }
                bi.tvLineText.setText(String.format("线路方案 - %d", holder.getAdapterPosition() + 1));
            }
        };

        binding.rvLine.setLayoutManager(new LinearLayoutManager(context));
        binding.rvLine.setAdapter(lineAdapter);
    }

    @Override
    protected void addListener() {
        binding.pickPlanInfo.setTextClickListener(v ->
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_PLAN_ADD_INFO)
                        .withSerializable("info", allMap)
                        .navigation(this, REQUEST_CODE_PLAN_INFO));

        binding.pickFund.setTextClickListener(v ->
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_PLAN_ADD_FUND)
                        .withSerializable("info", allMap)
                        .navigation(this, REQUEST_FUND_CODE));

        binding.tvRest.setOnClickListener(v -> addPlan(0));
        binding.tvSubmit.setOnClickListener(v -> addPlan(1));
    }

    private void addPlan(int i) {
        if (TextUtils.isEmpty(binding.etPlanName.getText())) {
            showToast(getString(R.string.please_input_plan_name));
            return;
        }
        if (!allMap.containsKey("beginTime") && !allMap.containsKey("endTime")) {
            showToast(getString(R.string.please_input_plan_info));
            return;
        }
        if (!allMap.containsKey("totalCostMoney")) {
            showToast(getString(R.string.please_input_claim));
            return;
        }
        allMap.put("planName", binding.etPlanName.getText());
        //方案状态 (0-草稿，1-审核中，2-审核通过，3-审核不通过)
        allMap.put("status", i);
        presenter.addPlan(allMap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_PLAN_INFO) {
                if (data != null) {
                    HashMap<String, Object> map = (HashMap<String, Object>) data.getSerializableExtra("plan");
                    if (map != null) {
                        binding.pickPlanInfo.setText(map.get("beginTime") + "--" + map.get("endTime"));
                        if (allMap == null) {
                            allMap = new HashMap<>();
                        }
                        allMap.putAll(map);

                        lineAdapter.setNewInstance((List<HashMap<String, Object>>) map.get("zsxtPropgtPlanLineList"));
                        //处理宣传员
                        spltStaffName();
                    }
                }
            } else if (requestCode == REQUEST_FUND_CODE) {
                if (data != null) {
                    HashMap<String, Object> map = (HashMap<String, Object>) data.getSerializableExtra("fund");
                    if (allMap == null) {
                        allMap = new HashMap<>();
                    }
                    allMap.putAll(map);
                    binding.pickFund.setText(map.get("totalCostMoney").toString());
                }
            }
        }
    }

    /**
     * 拆分宣传员
     */
    private void spltStaffName() {
        List<String> list = new ArrayList<>();

        StringBuilder names = new StringBuilder();

        lineAdapter.getData();
        if (lineAdapter.getData().size() > 0) {
            for (HashMap<String, Object> m : lineAdapter.getData()) {
                String na = (String) m.get("staffNames");
                if (!TextUtils.isEmpty(na)) {
                    if (na.contains(",")) {
                        String[] nas = na.split(",");
                        for (String n : nas) {
                            if (!list.contains(n)) {
                                list.add(n);
                            }
                        }
                    } else {
                        if (!list.contains(na)) {
                            list.add(na);
                        }
                    }
                }
            }
        }
        for (String n : list) {
            if (names.length() > 0) {
                names.append(",");
            }
            names.append(n);
        }
        binding.pickPropaganidst.setText(names.toString());
    }

    @Override
    public void onExamineList(List<SimpleBean> o) {

    }

    @Override
    public void onTaskList(List<SimpleBean> o) {

    }

    @Override
    public void onPlanList(List<PlanListBean> o) {

    }

    @Override
    public void onCreateList(List<SimpleStringBean> o) {

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
        showToast(getString(R.string.succ_add));
        setResult(RESULT_OK);
        finish();
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
}
