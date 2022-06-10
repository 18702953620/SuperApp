package com.shenyuan.superapp.admission.window.plan;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemSchoolLevelBinding;
import com.shenyuan.admission.databinding.PopSchoolSortBinding;
import com.shenyuan.superapp.admission.bean.SimpleStringBean;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.common.popup.BasePopupWindow;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/5 17:06
 * desc 审核状态
 */
public class PlanCreateWindow extends BasePopupWindow<PopSchoolSortBinding, BasePresenter> {
    private BaseQuickAdapter<SimpleStringBean, BaseDataBindingHolder> sortAdapter;
    private int sortPosition = -1;
    private OnSortBack onBack;

    public PlanCreateWindow(Context context) {
        super(context, true, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_school_sort;
    }

    @Override
    protected void initView() {
        sortAdapter = new BaseQuickAdapter<SimpleStringBean, BaseDataBindingHolder>(R.layout.item_school_level) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, SimpleStringBean s) {
                ItemSchoolLevelBinding schoolLevelBinding = (ItemSchoolLevelBinding) baseDataBindingHolder.getDataBinding();
                if (schoolLevelBinding == null) {
                    return;
                }
                schoolLevelBinding.tvSchoolLevel.setText(s.getValue());

                if (sortPosition == baseDataBindingHolder.getAdapterPosition()) {
                    schoolLevelBinding.tvSchoolLevel.setTextColor(getValuesColor(R.color.color_0077ff));
                } else {
                    schoolLevelBinding.tvSchoolLevel.setTextColor(getValuesColor(R.color.color_333333));
                }

            }
        };
        binding.rvSchoolsSort.setLayoutManager(new LinearLayoutManager(context));
        binding.rvSchoolsSort.setAdapter(sortAdapter);

        sortAdapter.setOnItemClickListener((adapter, view, position) -> {
            sortPosition = position;
            sortAdapter.notifyDataSetChanged();

            dismiss();

            if (onBack != null) {
                onBack.onBack(sortAdapter.getItem(position));
            }
        });

    }


    public void setData(List<SimpleStringBean> data) {
        if (sortAdapter != null) {
            sortAdapter.setNewInstance(data);
        }
    }

    @Override
    protected void addListener() {

    }

    public void showFullAsDropDown(Activity activity, View anchor, OnSortBack onBack) {
        super.showFullAsDropDown(activity, anchor);
        this.onBack = onBack;
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    /**
     * 回调
     * 业务不同 回调的参数也不同
     */
    public interface OnSortBack {
        /**
         * 回调
         */
        void onBack(SimpleStringBean bean);
    }
}
