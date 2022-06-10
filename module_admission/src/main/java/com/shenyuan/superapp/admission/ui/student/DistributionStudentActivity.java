package com.shenyuan.superapp.admission.ui.student;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcDistributionStudentBinding;
import com.shenyuan.superapp.admission.adapter.StaffAppointAdapter;
import com.shenyuan.superapp.admission.adapter.StaffDisAdapter;
import com.shenyuan.superapp.admission.api.presenter.DistributionPresenter;
import com.shenyuan.superapp.admission.api.view.DistributionView;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SimDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/3 11:39
 * desc 分配宣传员
 */
@Route(path = ARouterPath.Admission.ADMISSION_STUDENT_DISTRITION)
public class DistributionStudentActivity extends BaseActivity<AcDistributionStudentBinding, DistributionPresenter> implements DistributionView {
    @Autowired
    public StudentListBean studentBean;
    private StaffDisAdapter allAdapter;
    private StaffAppointAdapter appointAdapter;

    @Override
    protected DistributionPresenter createPresenter() {
        return new DistributionPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_distribution_student;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void initView() {
        binding.itemStudent.llStudentTel.setVisibility(View.GONE);
        if (studentBean != null) {
            binding.itemStudent.tvStudentGraduateSchoolAddress.setVisibility(View.VISIBLE);
            binding.itemStudent.tvStudentGraduateSchoolAddress.setText(String.format("%s %s %s", studentBean.getProvince(),
                    studentBean.getCity(), studentBean.getRegion()));
            //名称
            binding.itemStudent.tvStudentName.setText(studentBean.getStudentName());
            //性别
            binding.itemStudent.tvStudentSex.setText(studentBean.getGender());
            //招生层次
            binding.itemStudent.tvStudentLevel.setText(studentBean.getStudentGoal());
            if (!TextUtils.isEmpty(studentBean.getStudentGoal())) {
                binding.itemStudent.tvStudentLevel.setVisibility(View.VISIBLE);
            } else {
                binding.itemStudent.tvStudentLevel.setVisibility(View.GONE);
            }

            //预估分数
            binding.itemStudent.tvStudentFraction.setText(String.format("预估%s分", studentBean.getPredicateScore()));
            //毕业年份
            binding.itemStudent.tvStudentSession.setText(String.format("%d届", studentBean.getGraduateYear()));
            //所属科类
            binding.itemStudent.tvStudentClass.setText(studentBean.getSubject());
            //毕业学校
            binding.itemStudent.tvStudentGraduateSchool.setText(String.format("毕业学校：%s", studentBean.getSchoolName()));
            //生源意向
            binding.itemStudent.tvStudentType.setText(studentBean.getTargetDegreeName());
            //宣传员
            StringBuilder name = new StringBuilder();
            if (studentBean.getStaffName() != null && studentBean.getStaffName().size() > 0) {
                for (String na : studentBean.getStaffName()) {
                    if (name.length() > 0) {
                        name.append(",");
                    }
                    name.append(na);
                }
            }
            binding.itemStudent.tvStudentPropagandist.setText(String.format("宣传员：%s", name.toString()));
        }

        allAdapter = new StaffDisAdapter();
        binding.rvAll.setLayoutManager(new GridLayoutManager(context, 3));
        binding.rvAll.setAdapter(allAdapter);

        appointAdapter = new StaffAppointAdapter();

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.rvAppoint.setLayoutManager(layoutManager);
        binding.rvAppoint.setAdapter(appointAdapter);

        appointAdapter.setNewInstance(studentBean.getStaffPropagandist());

        if (appointAdapter.getData().size() > 0) {
            binding.tvPersonName.setVisibility(View.GONE);
        } else {
            binding.tvPersonName.setVisibility(View.INVISIBLE);
        }
        //宣传员列表
        presenter.getStaffList();

    }

    @Override
    protected void addListener() {
        allAdapter.setOnItemClickListener((adapter, view, position) -> {
            StaffBean bean = allAdapter.getItem(position);
            bean.setSelect(!bean.isSelect());
            allAdapter.notifyItemChanged(position);

            if (bean.isSelect()) {
                appointAdapter.addData(bean);
            } else {
                appointAdapter.remove(bean);
            }
            if (appointAdapter.getData().size() > 0) {
                binding.tvPersonName.setVisibility(View.GONE);
            } else {
                binding.tvPersonName.setVisibility(View.INVISIBLE);
            }
        });
        appointAdapter.setOnItemClickListener((adapter, view, position) -> {
            StaffBean bean = appointAdapter.getItem(position);
            int pos = allAdapter.getData().indexOf(bean);
            if (pos >= 0) {
                bean.setSelect(false);
                allAdapter.notifyItemChanged(pos);
            }

            appointAdapter.removeAt(position);
        });
        binding.tvSubmit.setOnClickListener(v -> {
            if (appointAdapter.getData().size() == 0) {
                showToast("请选择宣传员");
                return;
            }
            if (studentBean == null) {
                showToast("生源数据有误，请检查");
                return;
            }

            new SimDialog.Builder(context).title("是否确认分配").submitText("确认").onBacklistener(new BaseDialog.BaseOnBack() {
                @Override
                public void onConfirm() {
                    List<Integer> list = new ArrayList<>();
                    for (StaffBean bean : appointAdapter.getData()) {
                        list.add(bean.getId());
                    }
                    presenter.distributionStudent(studentBean.getId(), list);

                }
            }).show();
        });

        binding.tvRest.setOnClickListener(v -> {
            appointAdapter.setNewInstance(null);
            for (StaffBean bean : allAdapter.getData()) {
                bean.setSelect(false);
            }
            binding.tvPersonName.setVisibility(View.INVISIBLE);
            allAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void ontStaffList(List<StaffBean> o) {
        if (appointAdapter.getData().size() > 0) {
            for (StaffBean b : o) {
                for (StaffBean be : appointAdapter.getData()) {
                    if (b.getId() == be.getId()) {
                        be.setSelect(true);
                        o.set(o.indexOf(b), be);
                    }
                }
            }
        }
        allAdapter.setNewInstance(o);
    }

    @Override
    public void distributionSchool(String o) {

    }

    @Override
    public void distributionStudent(String o) {
        showToast(o);
        setResult(RESULT_OK);
        finish();
    }
}
