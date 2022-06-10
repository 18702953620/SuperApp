package com.shenyuan.superapp.admission.adapter.school;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemDisSchoolsBinding;
import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/19 16:29
 * desc
 */
public class SchoolDisAdapter extends BaseVBAdapter<SchoolListBean, BaseDataBindingHolder> {
    public SchoolDisAdapter() {
        super(R.layout.item_dis_schools);
        setNeedEmptyView(false);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, SchoolListBean sm) {
        ItemDisSchoolsBinding bi = (ItemDisSchoolsBinding) holder.getDataBinding();
        if (bi == null) {
            return;
        }
        //名称
        bi.tvSchoolName.setText(StrUtils.isEmpty(sm.getSchoolName()));
        //地址
        bi.tvSchoolLocation.setText(String.format("%s%s%s", StrUtils.isEmpty(sm.getProvinceName()),
                StrUtils.isEmpty(sm.getCityName())
                , StrUtils.isEmpty(sm.getRegionName())));
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
                bi.tvSchoolVocationTag.setText(String.format("高职 %s", sm.getVocationType()));
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

        if (sm.getEnrollList() != null && sm.getEnrollList().size() > 0) {
            SchoolListBean.ErollListBean dto = sm.getEnrollList().get(0);
            bi.tvSchoolUndergraduate.setText(String.valueOf(dto.getQuantity()));
            bi.tvSchoolTodayLabel.setText(String.format(getContext().getString(R.string.school_total_number), dto.getYear()));

            if (sm.getEnrollList().size() > 1) {
                SchoolListBean.ErollListBean dt = sm.getEnrollList().get(1);
                bi.tvSchoolSpecialty.setText(String.valueOf(dt.getQuantity()));
                bi.tvSchoolYestodayLabel.setText(String.format(getContext().getString(R.string.school_total_number), dt.getYear()));
            } else {
                bi.tvSchoolSpecialty.setText(String.valueOf(0));
                bi.tvSchoolYestodayLabel.setText(String.format(getContext().getString(R.string.school_total_number), (dto.getYear() - 1)));
            }

            int total = ParseUtils.parseInt(bi.tvSchoolUndergraduate.getText().toString())
                    + ParseUtils.parseInt(bi.tvSchoolSpecialty.getText().toString());

            bi.tvSchoolTotal.setText(String.valueOf(total));
        }

        StringBuilder sb = new StringBuilder();
        if (sm.getStaffPropagandist() != null && sm.getStaffPropagandist().size() > 0) {
            for (SchoolListBean.StaffPropagandistDTO dto : sm.getStaffPropagandist()) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(dto.getStaffName());
            }
        }
        bi.tvSchoolAreaPerson.setText(String.format(getContext().getString(R.string.school_staff_label), sb.toString()));
    }
}
