package com.shenyuan.superapp.admission.adapter.student;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemStudentBinding;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/3/8 15:15
 * desc
 */
public class StudentDeleteAdapter extends BaseVBAdapter<StudentListBean, BaseDataBindingHolder> {

    public StudentDeleteAdapter() {
        super(R.layout.item_student);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseViewHolder, StudentListBean sm) {
        ItemStudentBinding bi = (ItemStudentBinding) baseViewHolder.getDataBinding();
        if (bi == null) {
            return;
        }
        if (sm.isSelect()) {
            bi.ivStudentState.setBackgroundResource(R.mipmap.ic_state_large_select);
        } else {
            bi.ivStudentState.setBackgroundResource(R.mipmap.ic_state_large_normal);
        }

        bi.ivStudentState.setVisibility(View.VISIBLE);
        //名称
        bi.tvStudentName.setText(StrUtils.isEmpty(sm.getStudentName()));
        //性别
        bi.tvStudentSex.setText(StrUtils.isEmpty(sm.getGender()));
        //招生层次
        bi.tvStudentLevel.setText(StrUtils.isEmpty(sm.getStudentGoal()));
        if (!TextUtils.isEmpty(sm.getStudentGoal())) {
            bi.tvStudentLevel.setVisibility(View.VISIBLE);
        } else {
            bi.tvStudentLevel.setVisibility(View.GONE);
        }

        //预估分数
        if (sm.getPredicateScore() == 0) {
            bi.tvStudentFraction.setVisibility(View.GONE);
        } else {
            bi.tvStudentFraction.setText(String.format("预估%s分", sm.getPredicateScore()));
            bi.tvStudentFraction.setVisibility(View.VISIBLE);
        }
        //毕业年份
        if (sm.getGraduateYear() == 0) {
            bi.tvStudentSession.setVisibility(View.GONE);
        } else {
            bi.tvStudentSession.setText(String.format("%d届", sm.getGraduateYear()));
            bi.tvStudentSession.setVisibility(View.VISIBLE);
        }

        //所属科类
        bi.tvStudentClass.setText(StrUtils.isEmpty(sm.getSubject()));
        //毕业学校
        bi.tvStudentGraduateSchool.setText(String.format("毕业学校：%s", StrUtils.isEmpty(sm.getSchoolName())));
        //生源意向
        bi.tvStudentType.setText(StrUtils.isEmpty(sm.getStudentTargetName()));

        if (sm.getStudentTarget() == 1) {
            bi.ivStudentTel.setBackgroundResource(R.mipmap.ic_student_tel_red);
            bi.tvStudentType.setTextColor(getValuesColor(R.color.color_fc602d));
        } else if (sm.getStudentTarget() == 2) {
            bi.ivStudentTel.setBackgroundResource(R.mipmap.ic_student_tel_green);
            bi.tvStudentType.setTextColor(getValuesColor(R.color.color_07c160));
        } else {
            bi.ivStudentTel.setBackgroundResource(R.mipmap.ic_student_tel_gray);
            bi.tvStudentType.setTextColor(getValuesColor(R.color.color_999999));
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
        bi.tvStudentPropagandist.setText(String.format("宣传员：%s", name.toString()));
    }
}
