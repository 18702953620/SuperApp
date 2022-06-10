package com.shenyuan.superapp.admission.ui.student;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayout;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcTargetStudentBinding;
import com.shenyuan.superapp.admission.adapter.student.StudentBackAdapter;
import com.shenyuan.superapp.admission.api.presenter.StudentCommonPresenter;
import com.shenyuan.superapp.admission.api.presenter.StudentPresenter;
import com.shenyuan.superapp.admission.api.view.StudentCommonView;
import com.shenyuan.superapp.admission.api.view.StudentView;
import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.MajorBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.admission.bean.StudentInfoBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.admission.bean.YearBean;
import com.shenyuan.superapp.admission.ui.AdmissionSearchActivity;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.dialog.DialogUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/3 10:35
 * desc 退回池
 */
@Route(path = ARouterPath.Admission.ADMISSION_STUDENT_BACK)
public class StudentBackActivity extends BaseActivity<AcTargetStudentBinding, StudentPresenter> implements StudentView, StudentCommonView {

    private StudentBackAdapter studentBackAdapter;

    private StudentCommonPresenter commonPresenter;

    private int page = 1;
    private int studentGoal = -1;
    public static final int REQUEST_CODE_BACK = 100;
    private List<SimpleBean> levelBeanList;

    @Override
    protected StudentPresenter createPresenter() {
        return new StudentPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_target_student;
    }

    @Override
    protected void initView() {
        binding.title.setTitle("退回池");

        commonPresenter = new StudentCommonPresenter(this);
        commonPresenter.getSchoolLevelList();

        studentBackAdapter = new StudentBackAdapter();

        binding.rvTarget.setLayoutManager(new LinearLayoutManager(context));
        binding.rvTarget.setAdapter(studentBackAdapter);

        getStudentList();
    }

    private void getStudentList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", page);
        //招生层次
        if (studentGoal != -1) {
            map.put("studentGoal", studentGoal);
        }
        presenter.getStudentBackList(map);
    }

    @Override
    protected void addListener() {
        binding.mrlTarget.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getStudentList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getStudentList();
            }
        });

        binding.tlLevel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                studentGoal = levelBeanList.get(pos).getKey();
                getStudentList();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        studentBackAdapter.addChildClickViewIds(R.id.tv_option, R.id.ll_student_tel, R.id.content);
        studentBackAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_option) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_DISTRITION)
                        .withSerializable("studentBean", studentBackAdapter.getItem(position)).navigation(this, REQUEST_CODE_BACK);
                EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) studentBackAdapter.getViewByPosition(position, R.id.esm_school);
                if (menuLayout != null) {
                    menuLayout.resetStatus();
                }
            } else if (view.getId() == R.id.ll_student_tel) {
                DialogUtils.showTelDialog(context, view, studentBackAdapter.getItem(position).getMobile());
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_INFO)
                        .withInt("studentId", studentBackAdapter.getItem(position).getId())
                        .withInt("formWay", AppConstant.FORM_WAY_BACK_STUDENT)
                        .navigation();
            }
        });
        binding.llSchoolSearch.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_SEARCH)
                .withInt("searchType", AdmissionSearchActivity.SEARCH_TYPE_BACK_STUDENT)
                .navigation());
    }

    @Override
    public void onStudentList(List<StudentListBean> o) {

    }

    @Override
    public void onAddStudent(String o) {

    }

    @Override
    public void onStudentInfo(StudentInfoBean o) {

    }

    @Override
    public void onDeleteStudent(String o) {

    }

    @Override
    public void onBackStudent(String o) {

    }

    @Override
    public void onBackStudentList(List<StudentListBean> o) {
        if (page == 1) {
            studentBackAdapter.setNewInstance(o);
        } else {
            studentBackAdapter.addData(o);
        }
        binding.mrlTarget.finishRefreshAndLoadMore();
    }

    @Override
    public void onStudentSourceList(List<SimpleBean> o) {

    }

    @Override
    public void onStudentMajorList(List<MajorBean> o) {

    }

    @Override
    public void onAreaList(List<AreaBean> o) {

    }

    @Override
    public void ontUserArea(List<AreaUserBean> o) {

    }

    @Override
    public void ontStaffList(List<StaffBean> o) {

    }

    @Override
    public void onStudentYearList(List<YearBean> o) {

    }

    @Override
    public void onStudentSubjectList(List<SimpleBean> o) {

    }

    @Override
    public void onSchoolLevelList(List<SimpleBean> o) {
        levelBeanList = o;
        if (o != null && o.size() > 0) {
            for (SimpleBean bean : o) {
                binding.tlLevel.addTab(binding.tlLevel.newTab().setText(bean.getValue()));
            }
        }
    }

    @Override
    public void onStudentTargetList(List<SimpleBean> o) {

    }

    @Override
    public void onStudentSexList(List<SimpleBean> o) {

    }

    @Override
    public void onStudentGenderList(List<SimpleBean> o) {

    }

    @Override
    protected void onDestroy() {
        commonPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        binding.mrlTarget.finishRefreshAndLoadMore();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_BACK) {
            page = 1;
            getStudentList();
        }
    }
}
