package com.shenyuan.superapp.admission.window.plan;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.PopPlanTimeBinding;
import com.shenyuan.superapp.admission.api.presenter.StudentCommonPresenter;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.common.popup.BasePopupWindow;
import com.shenyuan.superapp.common.widget.datepicker.DatePickerUtils;

/**
 * @author ch
 * @date 2021/2/5 17:13
 * desc
 */
public class PlanTimeWindow extends BasePopupWindow<PopPlanTimeBinding, BasePresenter> {

    private OnSearchBack onBack;

    public PlanTimeWindow(Context context) {
        super(context, true, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_plan_time;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void addListener() {
        binding.tvStartTime.setOnClickListener(v -> DatePickerUtils.showYYMMDDDialog(context, binding.tvStartTime));

        binding.tvEndTime.setOnClickListener(v -> {
            if (TextUtils.isEmpty(getTv(binding.tvStartTime))) {
                showToast("请选择开始时间");
                return;
            }

            DatePickerUtils.showYYMMDDDialog(context, time -> {
                binding.tvEndTime.setText(time.split(" ")[0]);
                if (onBack != null) {
                    onBack.onBack(getTv(binding.tvStartTime), getTv(binding.tvEndTime));
                }
                dismiss();

            }, getTv(binding.tvStartTime));
        });
    }

    @Override
    protected StudentCommonPresenter createPresenter() {
        return null;
    }


    public void showFullAsDropDown(Activity activity, View anchor, OnSearchBack onBack) {
        super.showFullAsDropDown(activity, anchor);
        this.onBack = onBack;
    }


    /**
     * 回调
     * 业务不同 回调的参数也不同
     */
    public interface OnSearchBack {
        /**
         * 回调
         */
        void onBack(String start, String end);
    }
}
