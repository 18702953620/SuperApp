package com.shenyuan.superapp.admission.adapter.school;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemSchoolsBinding;
import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.utils.DateUtils;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/8 10:24
 * desc
 */
public class SchoolListAdapter extends BaseVBAdapter<SchoolListBean, BaseDataBindingHolder> {
    private final boolean hasSchoolUpdate;


    public SchoolListAdapter() {
        super(R.layout.item_schools);
        hasSchoolUpdate = PermissionCommon.hasSchoolUpdate();
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, SchoolListBean sm) {
        ItemSchoolsBinding bi = (ItemSchoolsBinding) holder.getDataBinding();
        if (bi == null) {
            return;
        }
        if (sm.isSelect()) {
            bi.ivSchoolState.setBackgroundResource(R.mipmap.ic_state_large_select);
        } else {
            bi.ivSchoolState.setBackgroundResource(R.mipmap.ic_state_large_normal);
        }

        bi.ivSchoolState.setVisibility(View.GONE);
        //名称
        bi.tvSchoolName.setText(StrUtils.isEmpty(sm.getSchoolName()));
        //地址
        bi.tvSchoolLocation.setText(String.format("%s%s%s", StrUtils.isEmpty(sm.getProvinceName()),
                StrUtils.isEmpty(sm.getCityName()), StrUtils.isEmpty(sm.getRegionName())));
        //所属区域
        bi.tvSchoolArea.setText(String.format(getContext().getString(R.string.school_area_label), StrUtils.isEmpty(sm.getAreaName())));
        //区域负责人
        bi.tvSchoolAreaLeader.setText(String.format(getContext().getString(R.string.school_person_label), StrUtils.isEmpty(sm.getAreaStaffName())));

        //是否挂牌
        if ("1".equals(sm.getIsListed())) {
            bi.tvSchoolListing.setVisibility(View.VISIBLE);
        } else {
            bi.tvSchoolListing.setVisibility(View.GONE);
        }
        if (sm.getSchoolType() == 0) {
            if (!TextUtils.isEmpty(sm.getBachelorType())) {
                bi.tvSchoolBacheiorTag.setText(String.format("本科 %s", sm.getBachelorType()));
                bi.tvSchoolBacheiorTag.setVisibility(View.VISIBLE);
            } else {
                bi.tvSchoolBacheiorTag.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(sm.getVocationType())) {
                bi.tvSchoolVocationTag.setText(String.format("专科 %s", sm.getVocationType()));
                bi.tvSchoolVocationTag.setVisibility(View.VISIBLE);
            } else {
                bi.tvSchoolVocationTag.setVisibility(View.GONE);
            }
            bi.tvSchoolDiplomaTag.setVisibility(View.GONE);
        } else {
            bi.tvSchoolBacheiorTag.setVisibility(View.GONE);
            bi.tvSchoolVocationTag.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(sm.getDiplomaType())) {
                bi.tvSchoolDiplomaTag.setText(String.format("专升本 %s", sm.getDiplomaType()));
                bi.tvSchoolDiplomaTag.setVisibility(View.VISIBLE);
            } else {
                bi.tvSchoolDiplomaTag.setVisibility(View.GONE);
            }
        }


        //本科录取人数
        if (sm.getBachelorEnrollList() != null && sm.getBachelorEnrollList().size() > 0) {
            SchoolListBean.BachelorEnrollListDTO dto = sm.getBachelorEnrollList().get(0);
            if (dto != null) {
                bi.tvBachelorEnrollNumber.setText(String.format("%d年本科录取人数", dto.getYear()));
                bi.tvSchoolUndergraduate.setText(String.valueOf(dto.getEnrollNumber()));
            }
        } else {
            bi.tvBachelorEnrollNumber.setText(String.format("%d年本科录取人数", DateUtils.getCurrentYear()));
            bi.tvSchoolUndergraduate.setText(String.valueOf(0));
        }
        //高职录取人数
        if (sm.getVocationEnrollList() != null && sm.getVocationEnrollList().size() > 0) {
            SchoolListBean.BachelorEnrollListDTO dto = sm.getVocationEnrollList().get(0);
            if (dto != null) {
                bi.tvVocationNumber.setText(String.format("%d年高职录取人数", dto.getYear()));
                bi.tvSchoolSpecialty.setText(String.valueOf(dto.getEnrollNumber()));
            }
        } else {
            bi.tvVocationNumber.setText(String.format("%d年高职录取人数", DateUtils.getCurrentYear()));
            bi.tvSchoolSpecialty.setText(String.valueOf(0));
        }

        if (hasSchoolUpdate) {
            bi.tvEdit.setVisibility(View.VISIBLE);
        } else {
            bi.tvEdit.setVisibility(View.GONE);
        }
    }
}
