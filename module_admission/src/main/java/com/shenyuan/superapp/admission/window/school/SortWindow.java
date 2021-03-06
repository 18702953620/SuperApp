package com.shenyuan.superapp.admission.window.school;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemSchoolLevelBinding;
import com.shenyuan.admission.databinding.PopSchoolSortBinding;
import com.shenyuan.superapp.admission.api.presenter.SchoolCommonPresenter;
import com.shenyuan.superapp.admission.api.view.CommonView;
import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.CreaterBean;
import com.shenyuan.superapp.admission.bean.SchoolTypeBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.common.popup.BasePopupWindow;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/5 17:06
 * desc
 */
public class SortWindow extends BasePopupWindow<PopSchoolSortBinding, SchoolCommonPresenter> implements CommonView {
    private BaseQuickAdapter<SimpleBean, BaseDataBindingHolder> sortAdapter;
    private int sortPosition;
    private OnSortBack onBack;

    public SortWindow(Context context) {
        super(context, true, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_school_sort;
    }

    @Override
    protected void initView() {
        presenter.getSortList();
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
    protected SchoolCommonPresenter createPresenter() {
        return new SchoolCommonPresenter(this);
    }

    @Override
    public void onAreaList(List<AreaBean> beans) {

    }

    @Override
    public void onCreaterList(List<CreaterBean> beans) {

    }

    @Override
    public void onLising(List<SimpleBean> beans) {

    }

    @Override
    public void onSchoolState(List<SimpleBean> beans) {

    }

    @Override
    public void onSchoolLevel(SchoolTypeBean beans) {

    }

    @Override
    public void ontUserArea(List<AreaUserBean> beans) {

    }

    @Override
    public void ontStaffList(List<StaffBean> o) {

    }

    @Override
    public void onSortList(List<SimpleBean> o) {
        if (o == null) {
            o = new ArrayList<>();
        }
        SimpleBean bean = new SimpleBean();
        bean.setKey(-1);
        bean.setValue("????????????");

        o.add(0, bean);
        sortAdapter.setNewInstance(o);
    }


    /**
     * ??????
     * ???????????? ????????????????????????
     */
    public interface OnSortBack {
        /**
         * ??????
         */
        void onBack(SimpleBean bean);
    }
}
