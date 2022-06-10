package com.shenyuan.superapp.admission.adapter.student;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemTargetStudentBinding;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/8 15:33
 * desc
 */
public class StudentBackAdapter extends BaseVBAdapter<StudentListBean, BaseDataBindingHolder> {
    public StudentBackAdapter() {
        super(R.layout.item_target_student);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, StudentListBean sm) {
        ItemTargetStudentBinding bi = (ItemTargetStudentBinding) holder.getDataBinding();
        if (bi == null) {
            return;
        }
        bi.tvOption.setText("重新\n分配");
        bi.itemStudent.tvStudentGraduateSchoolAddress.setVisibility(View.VISIBLE);
        bi.itemStudent.tvStudentGraduateSchoolAddress.setText(String.format("%s %s %s", StrUtils.isEmpty(sm.getProvince()),
                StrUtils.isEmpty(sm.getCity()), StrUtils.isEmpty(sm.getRegion())));

        if (sm.isSelect()) {
            bi.itemStudent.ivStudentState.setBackgroundResource(R.mipmap.ic_state_large_select);
        } else {
            bi.itemStudent.ivStudentState.setBackgroundResource(R.mipmap.ic_state_large_normal);
        }
        //名称
        bi.itemStudent.tvStudentName.setText(StrUtils.isEmpty(sm.getStudentName()));
        //性别
        bi.itemStudent.tvStudentSex.setText(StrUtils.isEmpty(sm.getGender()));
        //招生层次
        bi.itemStudent.tvStudentLevel.setText(StrUtils.isEmpty(sm.getStudentGoal()));
        if (!TextUtils.isEmpty(sm.getStudentGoal())) {
            bi.itemStudent.tvStudentLevel.setVisibility(View.VISIBLE);
        } else {
            bi.itemStudent.tvStudentLevel.setVisibility(View.GONE);
        }

        //预估分数
        if (sm.getPredicateScore() == 0) {
            bi.itemStudent.tvStudentFraction.setVisibility(View.GONE);
        } else {
            bi.itemStudent.tvStudentFraction.setText(String.format("预估%s分", sm.getPredicateScore()));
            bi.itemStudent.tvStudentFraction.setVisibility(View.VISIBLE);
        }
        //毕业年份
        if (sm.getGraduateYear() == 0) {
            bi.itemStudent.tvStudentSession.setVisibility(View.GONE);
        } else {
            bi.itemStudent.tvStudentSession.setText(String.format("%d届", sm.getGraduateYear()));
            bi.itemStudent.tvStudentSession.setVisibility(View.VISIBLE);
        }
        //所属科类
        bi.itemStudent.tvStudentClass.setText(StrUtils.isEmpty(sm.getSubject()));
        //毕业学校
        bi.itemStudent.tvStudentGraduateSchool.setText(String.format("毕业学校：%s", StrUtils.isEmpty(sm.getSchoolName())));
        //生源意向
        bi.itemStudent.tvStudentType.setText(StrUtils.isEmpty(sm.getStudentTargetName()));

        if (sm.getStudentTarget() == 1) {
            bi.itemStudent.ivStudentTel.setBackgroundResource(R.mipmap.ic_student_tel_red);
            bi.itemStudent.tvStudentType.setTextColor(getValuesColor(R.color.color_fc602d));
        } else if (sm.getStudentTarget() == 2) {
            bi.itemStudent.ivStudentTel.setBackgroundResource(R.mipmap.ic_student_tel_green);
            bi.itemStudent.tvStudentType.setTextColor(getValuesColor(R.color.color_07c160));
        } else {
            bi.itemStudent.ivStudentTel.setBackgroundResource(R.mipmap.ic_student_tel_gray);
            bi.itemStudent.tvStudentType.setTextColor(getValuesColor(R.color.color_999999));
        }
        //宣传员
        StringBuilder name = new StringBuilder();
        if (sm.getStaffName() != null && sm.getStaffName().size() > 0) {
            for (String na : sm.getStaffName()) {
                if (name.length() > 0) {
                    name.append(",");
                }
                name.append(na);
            }
        }
        bi.itemStudent.tvStudentPropagandist.setText(String.format("退回宣传员：%s", sm.getReturnedName()));
    }
}
