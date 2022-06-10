package com.shenyuan.superapp.admission.ui.student;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcAddStudentBinding;
import com.shenyuan.admission.databinding.ItemParentTelBinding;
import com.shenyuan.superapp.admission.api.presenter.StudentCommonPresenter;
import com.shenyuan.superapp.admission.api.presenter.StudentPresenter;
import com.shenyuan.superapp.admission.api.view.StudentCommonView;
import com.shenyuan.superapp.admission.api.view.StudentView;
import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.MajorBean;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.admission.bean.StudentInfoBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.admission.bean.YearBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.StrUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/8 16:44
 * desc 添加/编辑生源
 */
@Route(path = ARouterPath.Admission.ADMISSION_STUDENT_POOL_ADD)
public class AddStudentActivity extends BaseActivity<AcAddStudentBinding, StudentPresenter> implements StudentView, StudentCommonView {

    private StudentCommonPresenter commonPresenter;
    private BaseQuickAdapter<String, BaseDataBindingHolder> telAdapter;

    public static final int REQUEST_CODE_ADD_STUDENT = 100;

    private boolean showMore;
    /**
     * 宣传员id数组
     */
    private List<Integer> staffIds;
    /**
     * 招生层次（字典值：本科、专科、专升本）
     */
    private int studentGoal = -1;
    /**
     * 生源意向（字典值，高分生源，目标生源，无效生源）
     */
    private int studentTarget = -1;
    /**
     * 科类 (字典值)
     */
    private int subject = -1;
    /**
     * 意向专业ID
     */
    private int majorId = -1;
    /**
     * 性别
     */
    private int gender = -1;
    /**
     * 毕业年份
     */
    private int graduateYear;
    /**
     * 毕业学校
     */
    private int finishSchoolId = -1;

    @Autowired
    public int studentId = -1;

    /**
     * 0-生源池
     * 1-分配生源
     * 2-退回池
     * 3-我的生源
     */
    @Autowired
    public int formWay;

    /**
     * 是否可以编辑
     */
    @Autowired
    public boolean editAble = true;

    /**
     * 是否展示编辑按钮
     */
    @Autowired
    public boolean showEdit;

    private StudentInfoBean studentInfoBean;


    @Override
    protected StudentPresenter createPresenter() {
        return new StudentPresenter(this);
    }

    @Override
    protected void setStatusBar() {
        setNoStatusBarByLight();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_add_student;
    }

    @Override
    protected void initView() {
        commonPresenter = new StudentCommonPresenter(this);
        //生源意向
        commonPresenter.getSchoolTargetList();
        //招生层次
        commonPresenter.getSchoolLevelList();
        //所属科类
        commonPresenter.getSubjectList();
        //意向专业
        commonPresenter.getStudentMajorList();
        //性别
        commonPresenter.getSexList();
        //年级
        commonPresenter.getGenderList();
        //父母手机号
        telAdapter = new BaseQuickAdapter<String, BaseDataBindingHolder>(R.layout.item_parent_tel) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder holder, String s) {
                ItemParentTelBinding telBinding = (ItemParentTelBinding) holder.getDataBinding();
                if (telBinding != null) {
                    telBinding.etParentTel.setText(s);
                }
            }
        };
        binding.rvParentTel.setLayoutManager(new LinearLayoutManager(context));
        binding.rvParentTel.setAdapter(telAdapter);
        telAdapter.addChildClickViewIds(R.id.ll_mins);
        if (studentId != -1) {
            if (formWay == AppConstant.FORM_WAY_BACK_STUDENT) {
                presenter.getReturnStudentById(String.valueOf(studentId));
            } else if (formWay == AppConstant.FORM_WAY_MY_STUDENT) {
                presenter.getMyStudentById(String.valueOf(studentId));
            } else {
                presenter.getStudentById(String.valueOf(studentId));
            }
        } else {
            //宣传员
            commonPresenter.getStaffList();
        }
        setAble();
    }

    @Override
    protected void addListener() {
        binding.clMore.setOnClickListener(v -> {
            showMore = !showMore;
            if (showMore) {
                binding.ivMore.setBackgroundResource(R.mipmap.ic_arrow_down);
                binding.llMoreContent.setVisibility(View.GONE);
            } else {
                binding.ivMore.setBackgroundResource(R.mipmap.ic_arrow_up);
                binding.llMoreContent.setVisibility(View.VISIBLE);
            }
        });

        binding.llAdd.setOnClickListener(v -> {
            if (!editAble) {
                return;
            }

            if (telAdapter.getData().size() >= 2) {
                showToast("最多只能添加2个父母手机号");
                return;
            }
            telAdapter.addData("");
        });

        telAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ll_mins) {
                telAdapter.removeAt(position);
            }
        });

        binding.tvRest.setOnClickListener(v -> reset());
        binding.tvSubmit.setOnClickListener(v -> {
            if (studentTarget == -1) {
                showToast(getString(R.string.please_select_student_target));
                return;
            }
            if (studentGoal == -1) {
                showToast(getString(R.string.please_select_student_goal));
                return;
            }
            if (TextUtils.isEmpty(binding.etStudentName.getText())) {
                showToast(getString(R.string.please_input_name));
                return;
            }
            if (gender == -1) {
                showToast(getString(R.string.please_select_sex));
            }
            if (TextUtils.isEmpty(binding.etStudentTel.getText())) {
                showToast(getString(R.string.please_input_tel));
                return;
            }
            if (finishSchoolId == -1) {
                showToast(getString(R.string.please_select_finish_school));
                return;
            }
            if (subject == -1) {
                showToast(getString(R.string.please_select_subject));
                return;
            }

            HashMap<String, Object> map = new HashMap<>();
            //	生源意向（字典值，高分生源，目标生源，无效生源）
            if (studentTarget != -1) {
                map.put("studentTarget", studentTarget);
            }
            //招生层次
            if (studentGoal != -1) {
                map.put("studentGoal", studentGoal);
            }

            //性别 (0-男，1-女)
            if (gender != -1) {
                map.put("gender", gender);
            }
            //毕业年份
            if (graduateYear != 0) {
                map.put("graduateYear", graduateYear);
            }
            //完整身份证号
            map.put("identityNum", binding.etStudentIdCard.getText());
            //身份证号-后六位
            map.put("identitySub", binding.etStudentIdCardSix.getText());
            //手机号
            map.put("mobile", binding.etStudentTel.getText());
            //预估分数

            if (!TextUtils.isEmpty(binding.etPredictScore.getText())) {
                map.put("predicateScore", binding.etPredictScore.getText());
            }
            //毕业学校ID
            if (finishSchoolId != -1) {
                map.put("schoolId", finishSchoolId);
            }
            //意向专业
            if (majorId != -1) {
                map.put("majorId", majorId);
            }

            //微信/QQ
            map.put("socialNum", binding.etStudentWx.getText());
            //学生姓名
            if (!TextUtils.isEmpty(binding.etStudentName.getText())) {
                map.put("studentName", binding.etStudentName.getText());
            }

            //科类
            if (subject != -1) {
                map.put("subject", subject);
            }
            //宣传员
            if (staffIds != null && staffIds.size() > 0) {
                map.put("staffIds", staffIds);
            }
            //父母手机号
            map.put("parentMobile1", getTv(binding.etParentTel));
            if (telAdapter.getData().size() > 0) {
                for (int i = 0; i < telAdapter.getData().size(); i++) {
                    EditText edit = (EditText) telAdapter.getViewByPosition(i, R.id.et_parent_tel);
                    if (edit != null) {
                        if (!TextUtils.isEmpty(getTv(edit))) {
                            map.put("parentMobile" + (i + 2), getTv(edit));
                        }
                    }
                }
            }
            //备注
            map.put("remark", binding.etRemark.getText());
            if (studentId != -1) {
                map.put("id", studentId);
                if (formWay == AppConstant.FORM_WAY_MY_STUDENT) {
                    presenter.updateMyStudent(map);
                } else {
                    presenter.updateStudent(map);
                }
            } else {
                presenter.addStudent(map);
            }
        });

        binding.tvEdit.setOnClickListener(v -> {
            editAble = true;
            setAble();
        });
    }

    private void setAble() {
        if (editAble) {
            if (studentId == -1) {
                binding.title.setTitle(getString(R.string.student_add));
            } else {
                binding.title.setTitle(getString(R.string.student_edit));
            }

            binding.llSearchTools.setVisibility(View.VISIBLE);
            binding.tvEdit.setVisibility(View.GONE);
            binding.tvRest.setVisibility(View.VISIBLE);
            binding.tvSubmit.setVisibility(View.VISIBLE);
        } else {
            binding.title.setTitle(getString(R.string.title_student_info));

            if (formWay == AppConstant.FORM_WAY_MY_STUDENT) {
                if (PermissionCommon.hasMyStudentUpdate() && showEdit) {
                    //展示编辑按钮
                    binding.llSearchTools.setVisibility(View.VISIBLE);
                    binding.tvEdit.setVisibility(View.VISIBLE);
                    binding.tvRest.setVisibility(View.GONE);
                    binding.tvSubmit.setVisibility(View.GONE);
                } else {
                    binding.llSearchTools.setVisibility(View.GONE);
                }
            } else {
                if (PermissionCommon.hasStudentUpdate() && showEdit) {
                    //展示编辑按钮
                    binding.llSearchTools.setVisibility(View.VISIBLE);
                    binding.tvEdit.setVisibility(View.VISIBLE);
                    binding.tvRest.setVisibility(View.GONE);
                    binding.tvSubmit.setVisibility(View.GONE);
                } else {
                    binding.llSearchTools.setVisibility(View.GONE);
                }
            }


        }

        //生源意向
        binding.pickTarget.setPickAble(editAble);
        //招生层次
        binding.pickLevel.setPickAble(editAble);
        //姓名
        binding.etStudentName.setEditAble(editAble);
        //性别
        binding.pickSex.setPickAble(editAble);
        //手机号
        binding.etStudentTel.setEditAble(editAble);
        //毕业学校
        binding.etGraduate.setPickAble(editAble);
        if (editAble) {
            binding.etGraduate.setTextClickListener(v ->
                    ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_ADD_SEARCH)
                            .navigation(this, REQUEST_CODE_ADD_STUDENT));
        }
        binding.pickSubject.setPickAble(editAble);
        //年级
        binding.pickGrade.setPickAble(editAble);
        //身份证
        binding.etStudentIdCard.setEditAble(editAble);
        //身份证后六位
        binding.etStudentIdCardSix.setEditAble(editAble);
        //wx
        binding.etStudentWx.setEditAble(editAble);

        binding.etParentTel.setEnabled(editAble);

        //意向专业
        binding.pickMajor.setPickAble(editAble);
        //预估分数
        binding.etPredictScore.setEditAble(editAble);
        //宣传员
        binding.pickStaff.setPickAble(editAble);
        //备注
        binding.etRemark.setEnabled(editAble);
    }

    private void reset() {
        //生源意向
        studentTarget = -1;
        binding.pickTarget.clear();
        //招生层次
        studentGoal = -1;
        binding.pickLevel.clear();
        //姓名
        binding.etStudentName.clear();
        //性别
        gender = -1;
        binding.pickSex.clear();
        //手机号
        binding.etStudentTel.clear();


        //毕业学校
        finishSchoolId = -1;
        binding.etGraduate.clear();

        //所属区域*
        binding.etSchoolArea.clear();
        //区域负责人*
        binding.etSchoolPerson.clear();


        //科类
        subject = -1;
        binding.pickSubject.clear();

        //年级
        binding.pickGrade.clear();

        //毕业年份
        graduateYear = -1;
        binding.etFinishYear.clear();
        //身份证
        binding.etStudentIdCard.clear();
        //身份证后六位
        binding.etStudentIdCardSix.clear();
        //wx
        binding.etStudentWx.clear();

        binding.etParentTel.setText("");

        telAdapter.setNewInstance(null);
        //意向专业
        majorId = -1;
        binding.pickMajor.clear();
        //预估分数
        binding.etPredictScore.clear();
        //宣传员
        binding.pickStaff.clear();
        //备注
        binding.etRemark.setText("");
    }

    @Override
    public void onStudentList(List<StudentListBean> o) {

    }

    @Override
    public void onAddStudent(String o) {
        showToast(o);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onStudentInfo(StudentInfoBean o) {
        studentInfoBean = o;
        if (o == null) {
            return;
        }
        //宣传员
        commonPresenter.getStaffList();

        //生源意向
        studentTarget = o.getStudentTarget();
        binding.pickTarget.setText(o.getStudentTargetName());
        //招生层次
        studentGoal = o.getStudentGoal();
        binding.pickLevel.setText(o.getStudentGoalName());
        //姓名
        binding.etStudentName.setText(o.getStudentName());
        //性别
        gender = o.getGender();
        binding.pickSex.setText(o.getGenderName());
        //手机号
        binding.etStudentTel.setText(o.getMobile());


        //毕业学校
        finishSchoolId = o.getSchoolId();
        binding.etGraduate.setText(o.getSchoolName());

        //所属区域*
        binding.etSchoolArea.setText(o.getAreaName());
        //区域负责人*
        binding.etSchoolPerson.setText(o.getAreaStaffName());


        //科类
        subject = o.getSubject();
        binding.pickSubject.setText(o.getSubjectName());

        //年级
        binding.pickGrade.setText(o.getGradeName());

        //毕业年份
        graduateYear = o.getGraduateYear();
        binding.etFinishYear.setText(String.valueOf(o.getGraduateYear()));
        //身份证
        binding.etStudentIdCard.setText(StrUtils.isEmpty(o.getIdentityNum()));
        binding.etStudentIdCardSix.setText(StrUtils.isEmpty(o.getIdentitySub()));
        //wx
        binding.etStudentWx.setText(o.getSocialNum());

        binding.etParentTel.setText(o.getParentMobile1());

        if (!TextUtils.isEmpty(o.getParentMobile2())) {
            telAdapter.addData(o.getParentMobile2());
        }
        if (!TextUtils.isEmpty(o.getParentMobile3())) {
            telAdapter.addData(o.getParentMobile3());
        }

        //意向专业
        majorId = o.getMajorId();
        binding.pickMajor.setText(o.getMajorName());
        //预估分数
        binding.etPredictScore.setText(String.valueOf(o.getPredicateScore()));
        if (o.getPropagandistVoList() != null && o.getPropagandistVoList().size() > 0) {
            StringBuilder name = new StringBuilder();
            staffIds = new ArrayList<>();
            for (StaffBean dt : o.getPropagandistVoList()) {
                staffIds.add(dt.getId());
                if (name.length() > 0) {
                    name.append(",");
                }
                name.append(dt.getStaffName());
            }
            //宣传员
            binding.pickStaff.setText(name.toString());
        }
        //备注
        binding.etRemark.setText(o.getRemark());

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
    public void onStudentSourceList(List<SimpleBean> o) {

    }

    @Override
    public void onAreaList(List<AreaBean> o) {

    }

    @Override
    public void ontUserArea(List<AreaUserBean> o) {

    }

    @Override
    public void onStudentYearList(List<YearBean> o) {

    }


    @Override
    public void ontStaffList(List<StaffBean> o) {
        List<StaffBean> old = new ArrayList<>();
        if (studentInfoBean != null) {
            old = studentInfoBean.getPropagandistVoList();
        }

        binding.pickStaff.setPersonData(o, old, staffBeans -> {
            staffIds = new ArrayList<>();
            if (staffBeans != null && staffBeans.size() > 0) {
                for (StaffBean bean : staffBeans) {
                    staffIds.add(bean.getId());
                }
            }
        });
    }

    @Override
    public void onStudentMajorList(List<MajorBean> o) {
        binding.pickMajor.setData(o, "majorName", majorBean -> majorId = majorBean.getMajorId());
    }

    @Override
    public void onStudentSubjectList(List<SimpleBean> o) {
        binding.pickSubject.setData(o, "value", bean -> subject = bean.getKey());
    }

    @Override
    public void onSchoolLevelList(List<SimpleBean> o) {
        binding.pickLevel.setData(o, "value", bean -> studentGoal = bean.getKey());
    }

    @Override
    public void onStudentTargetList(List<SimpleBean> o) {
        binding.pickTarget.setData(o, "value", bean -> studentTarget = bean.getKey());
    }

    @Override
    public void onStudentSexList(List<SimpleBean> o) {
        binding.pickSex.setData(o, "value", bean -> gender = bean.getKey());
    }

    @Override
    public void onStudentGenderList(List<SimpleBean> o) {
        binding.pickGrade.setData(o, "value", bean -> {
            binding.etFinishYear.setText(String.valueOf(bean.getKey()));
            graduateYear = bean.getKey();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_STUDENT && resultCode == RESULT_OK) {
            if (data != null) {
                SchoolInfoBean listBean = (SchoolInfoBean) data.getSerializableExtra("school");
                if (listBean != null) {
                    finishSchoolId = listBean.getId();
                    binding.etGraduate.setText(listBean.getSchoolName());
                    binding.etSchoolArea.setText(listBean.getAreaName());
                    binding.etSchoolPerson.setText(listBean.getAreaStaffName());
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        commonPresenter.detachView();
        super.onDestroy();
    }
}
