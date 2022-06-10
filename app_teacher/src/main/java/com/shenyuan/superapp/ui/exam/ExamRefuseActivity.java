package com.shenyuan.superapp.ui.exam;

import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.api.presenter.ExamPresenter;
import com.shenyuan.superapp.api.view.ExamView;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.bean.ExamStudentInfoBean;
import com.shenyuan.superapp.databinding.AcExamInfoBinding;
import com.shenyuan.superapp.databinding.AcExamRefuseBinding;

/**
 * @author ch
 * @date 2021/5/18 16:51
 * 考试信息 -拒绝
 */
@Route(path = ARouterPath.AppTeacher.APP_EXAM_REFUSE)
public class ExamRefuseActivity extends BaseActivity<AcExamRefuseBinding, ExamPresenter> implements ExamView {
    @Autowired
    public ExamStudentInfoBean infoBean;

    @Autowired
    public String uuid;

    @Override
    protected ExamPresenter createPresenter() {
        return new ExamPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_exam_refuse;
    }

    @Override
    protected void initView() {
        if (infoBean != null) {
            //标题
            binding.tvTitle.setText(StrUtils.isEmpty(infoBean.getTableSecondTitle()));
            //编号
            binding.exam.tvNumber.setText(StrUtils.isEmpty(infoBean.getStuNum()));
            //名称
            binding.exam.tvName.setText(StrUtils.isEmpty(infoBean.getStuName()));
            //性别
            binding.exam.tvSex.setText(StrUtils.isEmpty(infoBean.getGender()));
            //联系方式
            binding.exam.tvTel.setText(StrUtils.isEmpty(infoBean.getPhone()));
            //身份证号
            binding.exam.tvIdcard.setText(StrUtils.isEmpty(infoBean.getIdentity()));
            //科类
            binding.exam.tvClass.setText(StrUtils.isEmpty(infoBean.getSubject()));
            //报考专业
            binding.exam.tvMajor.setText(StrUtils.isEmpty(infoBean.getMajor()));
        }
    }

    @Override
    protected void addListener() {
        binding.tvSubmit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(getTv(binding.etRefuse))) {
                showToast("请输入拒绝原因");
                return;
            }
            presenter.refuseValid(uuid, getTv(binding.etRefuse));
        });

    }

    @Override
    public void onExamInfo(ExamStudentInfoBean o) {

    }

    @Override
    public void onExamOption(String o) {
        showToast(o);
        setResult(RESULT_OK);
        finish();
    }
}
