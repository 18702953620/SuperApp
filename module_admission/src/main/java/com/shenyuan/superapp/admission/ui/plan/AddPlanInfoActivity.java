package com.shenyuan.superapp.admission.ui.plan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcAddPlanInfoBinding;
import com.shenyuan.admission.databinding.ItemPlanAddLineBinding;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.widget.recy.DividerDecoration;
import com.shenyuan.superapp.common.widget.datepicker.DatePickerUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/13 10:50
 * desc 添加方案详情
 */
@Route(path = ARouterPath.Admission.ADMISSION_PLAN_ADD_INFO)
public class AddPlanInfoActivity extends BaseActivity<AcAddPlanInfoBinding, BasePresenter> {
    private BaseQuickAdapter<HashMap<String, Object>, BaseDataBindingHolder> lineAdapter;
    public static final int REQUEST_CODE_PLAN_INFO = 100;
    @Autowired
    public HashMap<String, Object> info;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_add_plan_info;
    }

    @Override
    protected void initView() {
        lineAdapter = new BaseQuickAdapter<HashMap<String, Object>, BaseDataBindingHolder>(R.layout.item_plan_add_line) {
            @SuppressLint("DefaultLocale")
            @Override
            protected void convert(@NotNull BaseDataBindingHolder holder, HashMap<String, Object> s) {
                ItemPlanAddLineBinding bi = (ItemPlanAddLineBinding) holder.getDataBinding();
                if (bi == null) {
                    return;
                }
                bi.tvLineTitle.setText(String.format("线路方案 - %d", holder.getAdapterPosition() + 1));
                bi.stDestination.setText(s.get("lineStart") + "--" + s.get("lineEnd"));
                bi.stTime.setText(s.get("beginTime") + "--" + s.get("endTime"));
                bi.stStaff.setText(s.get("staffNames").toString());

                List<HashMap<String, Object>> targetList = (List<HashMap<String, Object>>) s.get("zsxtPropgtPlanTargetList");
                if (targetList != null && targetList.size() > 0) {
                    StringBuilder names = new StringBuilder();
                    for (HashMap<String, Object> map : targetList) {
                        if (names.length() > 0) {
                            names.append(",");
                        }
                        names.append(map.get("targetSchoolName"));
                    }
                    bi.stSchool.setText(names.toString());
                }
            }
        };

        binding.rvLine.setLayoutManager(new LinearLayoutManager(context));
        binding.rvLine.addItemDecoration(new DividerDecoration(getValuesColor(R.color.color_eaeaea), 1));
        binding.rvLine.setAdapter(lineAdapter);

        if (info != null) {
            lineAdapter.setNewInstance((List<HashMap<String, Object>>) info.get("zsxtPropgtPlanLineList"));

            if (info.get("beginTime") != null) {
                binding.tvStartTime.setText(info.get("beginTime").toString());
            }
            if (info.get("endTime") != null) {
                binding.tvEndTime.setText(info.get("endTime").toString());
            }
        }
    }

    @Override
    protected void addListener() {
        binding.tvSubmit.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_PLAN_ADD_LINE)
                .navigation(this, REQUEST_CODE_PLAN_INFO));
        binding.tvRest.setOnClickListener(v -> {
            if (TextUtils.isEmpty(getTv(binding.tvStartTime))) {
                showToast(getString(R.string.please_select_start_time));
                return;
            }
            if (TextUtils.isEmpty(getTv(binding.tvEndTime))) {
                showToast(getString(R.string.please_select_end_time));
                return;
            }
            if (lineAdapter.getData().size() == 0) {
                showToast(getString(R.string.please_add_line));
                return;
            }

            HashMap<String, Object> map = new HashMap<>();

            //开始时间
            map.put("beginTime", getTv(binding.tvStartTime));
            //结束时间
            map.put("endTime", getTv(binding.tvEndTime));
            map.put("zsxtPropgtPlanLineList", lineAdapter.getData());

            Intent intent = new Intent();
            intent.putExtra("plan", map);
            setResult(RESULT_OK, intent);
            finish();
        });

        binding.tvStartTime.setOnClickListener(v -> DatePickerUtils.showYYMMDDDialog(context, binding.tvStartTime));
        binding.tvEndTime.setOnClickListener(v -> {
            if (TextUtils.isEmpty(getTv(binding.tvStartTime))) {
                showToast(getString(R.string.please_select_start_time));
                return;
            }

            DatePickerUtils.showYYMMDDDialog(context, binding.tvEndTime, getTv(binding.tvStartTime));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PLAN_INFO && resultCode == RESULT_OK) {
            if (data != null) {
                HashMap<String, Object> map = (HashMap<String, Object>) data.getSerializableExtra("planLine");
                if (map != null) {
                    lineAdapter.addData(map);
                }
            }
        }
    }
}
