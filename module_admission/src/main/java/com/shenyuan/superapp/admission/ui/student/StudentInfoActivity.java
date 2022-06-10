package com.shenyuan.superapp.admission.ui.student;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcStudentInfoBinding;
import com.shenyuan.admission.databinding.ItemRecordBinding;
import com.shenyuan.superapp.admission.adapter.PhotoAdapter;
import com.shenyuan.superapp.admission.api.presenter.StudentPresenter;
import com.shenyuan.superapp.admission.api.view.StudentView;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.admission.bean.StudentInfoBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.dialog.DialogUtils;
import com.shenyuan.superapp.base.preview.BasePreviewActivity;
import com.shenyuan.superapp.base.preview.PreViewReenter;
import com.shenyuan.superapp.base.utils.DensityUtils;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.base.widget.recy.DividerDecoration;
import com.shenyuan.superapp.base.widget.recy.SpaceDecoration;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/4 9:35
 * desc 生源信息
 */
@Route(path = ARouterPath.Admission.ADMISSION_STUDENT_INFO)
public class StudentInfoActivity extends BaseActivity<AcStudentInfoBinding, StudentPresenter> implements StudentView {

    private BaseVBAdapter<StudentInfoBean.CommuListBean, BaseDataBindingHolder> recordAdapter;

    public static final int REQUEST_CODE_STUDENT_INFO = 100;

    private PreViewReenter reenter;

    private PhotoAdapter pAdapter;
    /**
     * 是否修改
     */
    private boolean isEdit;

    @Autowired
    public int studentId = -1;

    /**
     * 是否展示编辑按钮
     */
    @Autowired
    public boolean showEdit;

    /**
     * 是否可以编辑
     */
    @Autowired
    public boolean editAble;
    /**
     * 0-生源池
     * 1-分配生源
     * 2-退回池
     * 3-我的生源
     */
    @Autowired
    public int formWay;

    @Override
    protected StudentPresenter createPresenter() {
        return new StudentPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_student_info;
    }

    @Override
    protected void initView() {
        recordAdapter = new BaseVBAdapter<StudentInfoBean.CommuListBean, BaseDataBindingHolder>(R.layout.item_record) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder holder, StudentInfoBean.CommuListBean sm) {
                ItemRecordBinding bi = (ItemRecordBinding) holder.getDataBinding();
                if (bi == null) {
                    return;
                }
                bi.tvText.setText(sm.getContent());
                bi.tvStaff.setText(String.format("跟进人：%s", sm.getCreatorName()));
                bi.tvTime.setText(sm.getCreateTime());

                if (!TextUtils.isEmpty(sm.getPicture())) {
                    ArrayList<String> paths = new ArrayList<>();
                    if (sm.getPicture().contains(",")) {
                        paths.addAll(Arrays.asList(sm.getPicture().split(",")));
                    } else {
                        paths.add(sm.getPicture());
                    }
                    PhotoAdapter photoAdapter = new PhotoAdapter(paths);
                    bi.rvPhoto.setLayoutManager(new GridLayoutManager(context, 3));
                    bi.rvPhoto.setAdapter(photoAdapter);
                    if (bi.rvPhoto.getItemDecorationCount() == 0) {
                        SpaceDecoration spaceDecoration = new SpaceDecoration(DensityUtils.dp2px(context, 10),
                                getValuesColor(R.color.color_ffffff));
                        spaceDecoration.setPaddingStart(false);
                        bi.rvPhoto.addItemDecoration(spaceDecoration);
                    }
                    photoAdapter.setOnItemClickListener((adapter, view, position) -> {
                        pAdapter = photoAdapter;
                        BasePreviewActivity.startPreView((Activity) context, paths, view, position);
                    });

                }
            }
        };
        binding.rvRecord.setLayoutManager(new LinearLayoutManager(context));
        binding.rvRecord.setAdapter(recordAdapter);
        binding.rvRecord.addItemDecoration(new DividerDecoration(getValuesColor(R.color.color_d8d8d8), 1));

        if (studentId != -1) {
            if (formWay == AppConstant.FORM_WAY_BACK_STUDENT) {
                presenter.getReturnStudentById(String.valueOf(studentId));
            } else if (formWay == AppConstant.FORM_WAY_MY_STUDENT) {
                presenter.getMyStudentById(String.valueOf(studentId));
            } else {
                presenter.getStudentById(String.valueOf(studentId));
            }
        }
        reenter = new PreViewReenter(this);
    }

    @Override
    protected void addListener() {
        binding.tvInfoContent.setOnClickListener(v -> {
            if (studentId == -1) {
                return;
            }
            ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_POOL_ADD)
                    .withInt("studentId", studentId)
                    .withInt("formWay", formWay)
                    .withBoolean("editAble", editAble)
                    .withBoolean("showEdit", showEdit)
                    .navigation(this, REQUEST_CODE_STUDENT_INFO);
        });
        //添加沟通记录
        binding.btnAdd.setOnClickListener(v ->
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_ADD_RECORD)
                        .withInt("studentId", studentId).navigation(this, REQUEST_CODE_STUDENT_INFO));
    }

    @Override
    public void onStudentList(List<StudentListBean> o) {

    }

    @Override
    public void onAddStudent(String o) {

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onStudentInfo(StudentInfoBean studentBean) {
        if (studentBean != null) {
            binding.tvStudentGraduateSchoolAddress.setVisibility(View.VISIBLE);
            binding.tvStudentGraduateSchoolAddress.setText(String.format("%s %s %s", StrUtils.isEmpty(studentBean.getProvince()),
                    StrUtils.isEmpty(studentBean.getCity()), StrUtils.isEmpty(studentBean.getRegion())));
            //名称
            binding.tvStudentName.setText(studentBean.getStudentName());
            //性别
            binding.tvStudentSex.setText(studentBean.getGenderName());
            //招生层次
            binding.tvStudentLevel.setText(studentBean.getStudentGoalName());

            if (!TextUtils.isEmpty(studentBean.getStudentGoalName())) {
                binding.tvStudentLevel.setVisibility(View.VISIBLE);
            } else {
                binding.tvStudentLevel.setVisibility(View.GONE);
            }

            //预估分数
            if (studentBean.getPredicateScore() == 0) {
                binding.tvStudentFraction.setVisibility(View.GONE);
            } else {
                binding.tvStudentFraction.setText(String.format("预估%s分", studentBean.getPredicateScore()));
                binding.tvStudentFraction.setVisibility(View.VISIBLE);
            }
            //毕业年份
            if (studentBean.getGraduateYear() == 0) {
                binding.tvStudentSession.setVisibility(View.GONE);
            } else {
                binding.tvStudentSession.setText(String.format("%d届", studentBean.getGraduateYear()));
                binding.tvStudentSession.setVisibility(View.VISIBLE);
            }
            //所属科类
            binding.tvStudentClass.setText(studentBean.getSubjectName());
            //毕业学校
            binding.tvStudentGraduateSchool.setText(String.format("毕业学校：%s", StrUtils.isEmpty(studentBean.getSchoolName())));
            //生源意向
            binding.tvStudentType.setText(studentBean.getStudentTargetName());
            if (studentBean.getStudentTarget() == 1) {
                binding.ivStudentTel.setBackgroundResource(R.mipmap.ic_student_tel_red);
                binding.tvStudentType.setTextColor(getValuesColor(R.color.color_fc602d));
            } else if (studentBean.getStudentTarget() == 2) {
                binding.tvStudentType.setTextColor(getValuesColor(R.color.color_07c160));
                binding.ivStudentTel.setBackgroundResource(R.mipmap.ic_student_tel_green);
            } else {
                binding.tvStudentType.setTextColor(getValuesColor(R.color.color_999999));
                binding.ivStudentTel.setBackgroundResource(R.mipmap.ic_student_tel_gray);
            }
            //宣传员
            StringBuilder name = new StringBuilder();
            if (studentBean.getPropagandistVoList() != null && studentBean.getPropagandistVoList().size() > 0) {
                for (StaffBean na : studentBean.getPropagandistVoList()) {
                    if (name.length() > 0) {
                        name.append(",");
                    }
                    name.append(na.getStaffName());
                }
            }
            binding.tvStudentPropagandist.setText(String.format("宣传员：%s", name.toString()));
            //沟通记录
            recordAdapter.setNewInstance(studentBean.getCommuList());

            binding.llStudentTel.setOnClickListener(v -> DialogUtils.showTelDialog(context, v, studentBean.getMobile()));

        }
    }

    @Override
    public void onDeleteStudent(String o) {

    }

    @Override
    public void onBackStudent(String o) {

    }

    @Override
    public void onBackStudentList(List<StudentListBean> o) {

    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        reenter.onActivityReenter(resultCode, data, pAdapter, R.id.iv_photo);
    }

    @Override
    public void onBackPressed() {
        if (isEdit) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_STUDENT_INFO && resultCode == RESULT_OK) {
            if (studentId != -1) {
                isEdit = true;
                if (formWay == AppConstant.FORM_WAY_BACK_STUDENT) {
                    presenter.getReturnStudentById(String.valueOf(studentId));
                } else if (formWay == AppConstant.FORM_WAY_MY_STUDENT) {
                    presenter.getMyStudentById(String.valueOf(studentId));
                } else {
                    presenter.getStudentById(String.valueOf(studentId));
                }
            }
        }
    }
}
