package com.shenyuan.superapp.admission.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.PopPickSchoolLevelBinding;
import com.shenyuan.superapp.admission.api.presenter.SchoolCommonPresenter;
import com.shenyuan.superapp.admission.api.view.CommonView;
import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.CreaterBean;
import com.shenyuan.superapp.admission.bean.SchoolTypeBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.utils.PopUtils;
import com.shenyuan.superapp.common.widget.PickerTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/23 10:35
 * desc
 */
public class PickerSchoolLevel extends PickerTextView implements CommonView {
    private PopPickSchoolLevelBinding popBinding;
    private SchoolTypeBean schoolTypeBean;
    private List<SchoolTypeBean.DiplomaTypeDTO> dots;
    private List<String> leftString;
    private int leftPosition;
    private int rightPosition;

    private OnLevelCallBack callBack;

    private int bachelor = -1;
    private int vocation = -1;
    private int diploma = -1;

    public PickerSchoolLevel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PickerSchoolLevel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCallBack(OnLevelCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected void initPopWindow() {
        SchoolCommonPresenter commonPresenter = new SchoolCommonPresenter(this);
        commonPresenter.getSchoolLevel();

        popBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.pop_pick_school_level, null, false);

        popBinding.getRoot().setOnClickListener(v -> {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
        });
        popBinding.tvCancel.setOnClickListener(v -> popupWindow.dismiss());
        popBinding.tvSubmit.setOnClickListener(v -> {
            setText(leftString.get(leftPosition) + " " + dots.get(rightPosition).getLevelName());


            if (leftPosition == 0) {
                bachelor = dots.get(rightPosition).getSchoolLevel();
                vocation = -1;
                diploma = -1;
            } else if (leftPosition == 1) {
                vocation = dots.get(rightPosition).getSchoolLevel();
                bachelor = -1;
                diploma = -1;
            } else {
                diploma = dots.get(rightPosition).getSchoolLevel();
                bachelor = -1;
                vocation = -1;
            }

            if (callBack != null) {
                callBack.onSelect(bachelor, vocation, diploma);
            }
            popupWindow.dismiss();
        });

        popBinding.wvTextLeft.setListener(index -> {
            leftPosition = index;
            if (schoolTypeBean != null) {
                if (index == 0) {
                    dots = schoolTypeBean.getBachelorType();
                } else if (index == 1) {
                    dots = schoolTypeBean.getDiplomaType();
                } else {
                    dots = schoolTypeBean.getVocationType();
                }
                setData(popBinding.wvTextRight, dots, "levelName");
            }
        });
        popBinding.wvTextRight.setListener(index -> rightPosition = index);
    }

    @Override
    protected void showPickDialog() {
        if (popupWindow == null) {
            popupWindow = PopUtils.getPopupWindow(getContext(), popBinding.getRoot(), true, true);
        }
        popupWindow.showAtLocation(binding.etText, Gravity.BOTTOM, 0, 0);
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
        schoolTypeBean = beans;
        if (beans == null) {
            return;
        }
        leftString = new ArrayList<>();
        leftString.add(beans.getBachelorName());
        leftString.add(beans.getDiplomaName());
        leftString.add(beans.getVocationName());

        popBinding.wvTextLeft.setItems(leftString);

        dots = beans.getBachelorType();
        setData(popBinding.wvTextRight, dots, "levelName");
    }

    @Override
    public void ontUserArea(List<AreaUserBean> beans) {

    }

    @Override
    public void ontStaffList(List<StaffBean> o) {

    }

    @Override
    public void onSortList(List<SimpleBean> o) {

    }



    @Override
    public void showLoading() {

    }


    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onErrorCode(String code, String msg) {

    }

    public interface OnLevelCallBack {
        void onSelect(int bachelor, int vocation, int diploma);
    }
}
