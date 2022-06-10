package com.shenyuan.superapp.admission.window.student;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemSchoolLevelBinding;
import com.shenyuan.admission.databinding.PopSchoolSortBinding;
import com.shenyuan.superapp.admission.api.presenter.StudentCommonPresenter;
import com.shenyuan.superapp.admission.api.view.StudentCommonView;
import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.MajorBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.admission.bean.YearBean;
import com.shenyuan.superapp.common.popup.BasePopupWindow;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/5 17:06
 * desc 所属科类
 */
public class StudentClassWindow extends BasePopupWindow<PopSchoolSortBinding, StudentCommonPresenter> implements StudentCommonView {
    private BaseQuickAdapter<SimpleBean, BaseDataBindingHolder> sortAdapter;
    private int sortPosition = -1;
    private OnSortBack onBack;

    public StudentClassWindow(Context context) {
        super(context, true, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_school_sort;
    }

    @Override
    protected void initView() {
        presenter.getSubjectList();
        sortAdapter = new BaseQuickAdapter<SimpleBean, BaseDataBindingHolder>(R.layout.item_school_level) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, SimpleBean s) {
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

    @Override
    protected void addListener() {

    }

    public void showFullAsDropDown(Activity activity, View anchor, OnSortBack onBack) {
        super.showFullAsDropDown(activity, anchor);
        this.onBack = onBack;
    }

    @Override
    protected StudentCommonPresenter createPresenter() {
        return new StudentCommonPresenter(this);
    }

    @Override
    public void onStudentSourceList(List<SimpleBean> o) {

    }

    @Override
    public void onStudentMajorList(List<MajorBean> o) {

    }

    @Override
    public void onAreaList(List<AreaBean> o) {

    }

    @Override
    public void ontUserArea(List<AreaUserBean> o) {

    }

    @Override
    public void ontStaffList(List<StaffBean> o) {

    }

    @Override
    public void onStudentYearList(List<YearBean> o) {

    }

    @Override
    public void onStudentSubjectList(List<SimpleBean> o) {
        sortAdapter.setNewInstance(o);
    }

    @Override
    public void onSchoolLevelList(List<SimpleBean> o) {

    }

    @Override
    public void onStudentTargetList(List<SimpleBean> o) {

    }

    @Override
    public void onStudentSexList(List<SimpleBean> o) {

    }

    @Override
    public void onStudentGenderList(List<SimpleBean> o) {

    }


    /**
     * 回调
     * 业务不同 回调的参数也不同
     */
    public interface OnSortBack {
        /**
         * 回调
         */
        void onBack(SimpleBean address);
    }
}
