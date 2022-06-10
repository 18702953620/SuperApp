package com.shenyuan.superapp.ui.exam;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.api.presenter.ExamPresenter;
import com.shenyuan.superapp.api.view.ExamView;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.base.widget.recy.DividerDecoration;
import com.shenyuan.superapp.bean.ExamStudentInfoBean;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.databinding.AcExamInfoBinding;
import com.shenyuan.superapp.databinding.ItemExamBinding;

import org.jetbrains.annotations.NotNull;

/**
 * @author ch
 * @date 2021/5/18 16:51
 * 考试信息确认
 */
@Route(path = ARouterPath.AppTeacher.APP_EXAM_INFO)
public class ExamActivity extends BaseActivity<AcExamInfoBinding, ExamPresenter> implements ExamView {
    @Autowired
    public String uuid;

    private BaseVBAdapter<ExamStudentInfoBean.ExamArrangeListBean, BaseDataBindingHolder> examAdapter;

    @Override
    protected ExamPresenter createPresenter() {
        return new ExamPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_exam_info;
    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(uuid)) {
            presenter.getStudentInfo(uuid);
        }

        examAdapter = new BaseVBAdapter<ExamStudentInfoBean.ExamArrangeListBean, BaseDataBindingHolder>(R.layout.item_exam) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder holder, ExamStudentInfoBean.ExamArrangeListBean bean) {
                ItemExamBinding bi = (ItemExamBinding) holder.getDataBinding();
                if (bi == null) {
                    return;
                }
                //考试名称
                bi.tvExamClass.setText(StrUtils.isEmpty(bean.getExamName()));
                //考试时间
                bi.tvExamTimes.setText(StrUtils.isEmpty(bean.getExamTime()));
                //考试地点
                bi.tvExamAddress.setText(StrUtils.isEmpty(bean.getExamLocation()));
            }
        };

        binding.rvExam.setLayoutManager(new LinearLayoutManager(context));
        binding.rvExam.setAdapter(examAdapter);
        binding.rvExam.addItemDecoration(new DividerDecoration(getValuesColor(R.color.color_999999), 1));

    }

    @Override
    protected void addListener() {

    }

    @Override
    public void onExamInfo(ExamStudentInfoBean o) {
        if (o != null) {
            //标题
            binding.tvTitle.setText(StrUtils.isEmpty(o.getTableSecondTitle()));
            //编号
            binding.exam.tvNumber.setText(StrUtils.isEmpty(o.getStuNum()));
            //名称
            binding.exam.tvName.setText(StrUtils.isEmpty(o.getStuName()));
            //性别
            binding.exam.tvSex.setText(StrUtils.isEmpty(o.getGender()));
            //联系方式
            binding.exam.tvTel.setText(StrUtils.isEmpty(o.getPhone()));
            //身份证号
            binding.exam.tvIdcard.setText(StrUtils.isEmpty(o.getIdentity()));
            //科类
            binding.exam.tvClass.setText(StrUtils.isEmpty(o.getSubject()));
            //报考专业
            binding.exam.tvMajor.setText(StrUtils.isEmpty(o.getMajor()));
            //教学方式
            binding.exam.tvWay.setText(StrUtils.isEmpty(o.getStudyMode()));

            examAdapter.setNewInstance(o.getExamArrangeList());

            if (o.getIsPassOrRefuse() == 0) {
                binding.exam.ivExamState.setVisibility(View.GONE);
                binding.llExamTools.setVisibility(View.VISIBLE);
            } else if (o.getIsPassOrRefuse() == 1) {
                binding.exam.ivExamState.setVisibility(View.VISIBLE);
                binding.llExamTools.setVisibility(View.GONE);
                binding.exam.ivExamState.setImageResource(R.mipmap.ic_exam_passed);
            } else {
                binding.exam.ivExamState.setVisibility(View.VISIBLE);
                binding.llExamTools.setVisibility(View.GONE);
                binding.exam.ivExamState.setImageResource(R.mipmap.ic_exam_unpasss);
            }
        }

        binding.tvRest.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.AppTeacher.APP_EXAM_REFUSE)
                .withSerializable("infoBean", o)
                .withString("uuid", uuid)
                .navigation(this, 100));

        binding.tvSubmit.setOnClickListener(v -> presenter.passValid(uuid));
    }

    @Override
    public void onExamOption(String o) {
        showToast(o);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            finish();
        }
    }
}
