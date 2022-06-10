package com.shenyuan.superapp.admission.ui.student;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcStudentPoolBinding;
import com.shenyuan.admission.databinding.PopStudentMoreBinding;
import com.shenyuan.superapp.admission.adapter.student.StudentListAdapter;
import com.shenyuan.superapp.admission.api.presenter.StudentPresenter;
import com.shenyuan.superapp.admission.api.view.StudentView;
import com.shenyuan.superapp.admission.bean.StudentInfoBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.admission.ui.AdmissionSearchActivity;
import com.shenyuan.superapp.admission.window.student.StudentClassWindow;
import com.shenyuan.superapp.admission.window.student.StudentLevelWindow;
import com.shenyuan.superapp.admission.window.student.StudentPurposeWindow;
import com.shenyuan.superapp.admission.window.student.StudentSearchWindow;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.DialogUtils;
import com.shenyuan.superapp.base.dialog.SimDialog;
import com.shenyuan.superapp.base.utils.PopUtils;
import com.shenyuan.superapp.base.ARouterPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2020/12/15-16:44
 * desc 生源池
 */
@Route(path = ARouterPath.Admission.ADMISSION_STUDENT_POOL)
public class StudentPoolActivity extends BaseActivity<AcStudentPoolBinding, StudentPresenter> implements StudentView {
    private StudentListAdapter studentAdapter;

    public static final int REQUEST_CODE_STUDENT_LIST = 100;

    private boolean isSearchSubmit;
    /**
     * 筛选
     */
    private StudentSearchWindow searchWindow;
    /**
     * 所属科室
     */
    private StudentClassWindow classWindow;
    /**
     * 生源意向
     */
    private StudentPurposeWindow purposeWindow;
    /**
     * 招生层次
     */
    private StudentLevelWindow levelWindow;

    private int page = 1;
    /**
     * 所属区域ID
     */
    private List<Integer> areaIds;
    /**
     * 区域负责人Id
     */
    private List<Integer> areaStaffIds;
    /**
     * 毕业年份
     */
    private List<Integer> graduateYear;
    /**
     * 预估分段
     */
    private double scoreEnd;
    private double scoreStart;
    /**
     * 生源来源数组 (字典值)
     */
    private List<Integer> source;
    /**
     * 意向专业ID数组
     */
    private List<Integer> majorIds;
    /**
     * 招生层次
     */
    private int studentGoal = -1;
    /**
     * 生源意向 (字典值)
     */
    private int studentTarget = -1;
    /**
     * 所属科类
     */
    private int subject = -1;
    /**
     * 宣传员
     */
    private List<Integer> staffIds;

    @Override
    protected StudentPresenter createPresenter() {
        return new StudentPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_student_pool;
    }

    @Override
    protected void initView() {
        studentAdapter = new StudentListAdapter();
        binding.rvStudent.setLayoutManager(new LinearLayoutManager(context));
        binding.rvStudent.setAdapter(studentAdapter);

        getStudentList();
    }

    @Override
    protected void addListener() {
        binding.mrlStudent.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
        binding.title.addRightListener(v -> showMoreDialog());
        //层次
        binding.llStudentLevel.setOnClickListener(v -> {
            showUpArrow(binding.tvStudentLevel);
            if (levelWindow == null) {
                levelWindow = new StudentLevelWindow(context);
            }
            levelWindow.showFullAsDropDown(StudentPoolActivity.this, binding.llStudentLevel, address -> {
                studentGoal = address.getKey();
                binding.tvStudentLevel.setText(address.getValue());
                binding.tvStudentLevel.setTextColor(getValuesColor(R.color.color_0077ff));

                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                binding.tvStudentLevel.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                page = 1;
                getStudentList();
            });
            levelWindow.setOnDismissListener(() -> showDownArrow(binding.tvStudentLevel));
        });
        //意向
        binding.llStudentPurpose.setOnClickListener(v -> {
            showUpArrow(binding.tvStudentPurpose);
            if (purposeWindow == null) {
                purposeWindow = new StudentPurposeWindow(context);
            }

            purposeWindow.showFullAsDropDown(StudentPoolActivity.this, binding.llStudentLevel, address -> {
                studentTarget = address.getKey();
                binding.tvStudentPurpose.setText(address.getValue());
                binding.tvStudentPurpose.setTextColor(getValuesColor(R.color.color_0077ff));

                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                binding.tvStudentPurpose.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                page = 1;
                getStudentList();
            });
            purposeWindow.setOnDismissListener(() -> showDownArrow(binding.tvStudentPurpose));
        });
        //科类
        binding.llStudentClass.setOnClickListener(v -> {
            showUpArrow(binding.tvStudentClass);
            if (classWindow == null) {
                classWindow = new StudentClassWindow(context);
            }

            classWindow.showFullAsDropDown(StudentPoolActivity.this, binding.llStudentLevel, address -> {
                subject = address.getKey();
                binding.tvStudentClass.setText(address.getValue());
                binding.tvStudentClass.setTextColor(getValuesColor(R.color.color_0077ff));

                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                binding.tvStudentClass.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                page = 1;
                getStudentList();
            });
            classWindow.setOnDismissListener(() -> showDownArrow(binding.tvStudentClass));
        });
        //筛选
        binding.llStudentScreen.setOnClickListener(v -> {
            showUpArrow(binding.tvStudentSearch);
            showSearchDialog();
        });
        //全选
        binding.cbDistribution.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (StudentListBean b : studentAdapter.getData()) {
                b.setSelect(isChecked);
            }
            studentAdapter.notifyDataSetChanged();
        });
        //删除
        binding.tvDelete.setOnClickListener(v -> new SimDialog.Builder(context).title("是否确认删除？").submitText("确认").onBacklistener(new BaseDialog.BaseOnBack() {
            @Override
            public void onConfirm() {
                StringBuilder ids = new StringBuilder();
                for (StudentListBean b : studentAdapter.getData()) {
                    if (b.isSelect()) {
                        if (ids.length() > 0) {
                            ids.append(",");
                        }
                        ids.append(b.getId());
                    }
                }
                if (ids.length() == 0) {
                    showToast("请至少选择一个");
                    return;
                }
                presenter.deleteStudent(ids.toString());

            }
        }).show());

        studentAdapter.addChildClickViewIds(R.id.content, R.id.tv_edit, R.id.ll_student_tel);
        studentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            StudentListBean bean = studentAdapter.getItem(position);
            if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_INFO)
                        .withInt("studentId", bean.getId())
                        .withBoolean("editAble", false)
                        .withBoolean("showEdit", true)
                        .navigation(this, REQUEST_CODE_STUDENT_LIST);
            } else if (view.getId() == R.id.tv_edit) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_INFO)
                        .withInt("studentId", bean.getId())
                        .withBoolean("editAble", true)
                        .navigation(this, REQUEST_CODE_STUDENT_LIST);
            } else if (view.getId() == R.id.ll_student_tel) {
                DialogUtils.showTelDialog(context, view, studentAdapter.getItem(position).getMobile());
            }
        });
        if (PermissionCommon.hasStudentAdd()) {

        }

        //添加
        if (PermissionCommon.hasStudentAdd()) {
            binding.btnStudentAdd.setVisibility(View.VISIBLE);
        } else {
            binding.btnStudentAdd.setVisibility(View.GONE);
        }

        //添加
        binding.btnStudentAdd.setOnClickListener(v -> ARouter.getInstance()
                .build(ARouterPath.Admission.ADMISSION_STUDENT_POOL_ADD).navigation(this, REQUEST_CODE_STUDENT_LIST));

        binding.llSchoolSearch.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_SEARCH)
                .withInt("searchType", AdmissionSearchActivity.SEARCH_TYPE_STUDENT)
                .navigation());
    }


    private void getStudentList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", page);
        //招生层次
        if (studentGoal != -1) {
            map.put("studentGoal", studentGoal);
        }
        //生源意向
        if (studentTarget != -1) {
            List<Integer> list = new ArrayList<>();
            list.add(studentTarget);
            map.put("studentTarget", list);
        }
        //所属科室
        if (subject != -1) {
            map.put("subject", subject);
        }
        //区域
        if (areaIds != null && areaIds.size() > 0) {
            map.put("areaIds", areaIds);
        }
        //区域负责人
        if (areaStaffIds != null && areaStaffIds.size() > 0) {
            map.put("areaStaffIds", areaStaffIds);
        }
        //毕业年份
        if (graduateYear != null && graduateYear.size() > 0) {
            map.put("graduateYear", graduateYear);
        }
        //分数
        if (scoreStart != 0 && scoreEnd != 0) {
            map.put("scoreStart", scoreStart);
            map.put("scoreEnd", scoreEnd);
        }
        //宣传员
        if (staffIds != null && staffIds.size() > 0) {
            map.put("staffIds", staffIds);
        }
        //来源
        if (source != null && source.size() > 0) {
            map.put("source", source);
        }
        //意向专业
        if (majorIds != null && majorIds.size() > 0) {
            map.put("majorIds", majorIds);
        }
        presenter.getStudentList(map);
    }

    /**
     * 筛选
     */
    private void showSearchDialog() {
        if (searchWindow == null) {
            searchWindow = new StudentSearchWindow(context);
        }
        searchWindow.showFullAsDropDown(StudentPoolActivity.this,
                binding.tvStudentSearch, (areaId, areaStaffId, years, staffId, sources, majorId, scoreStarts, scoreEnds) -> {

                    areaIds = areaId;
                    areaStaffIds = areaStaffId;
                    graduateYear = years;
                    staffIds = staffId;
                    source = sources;
                    majorIds = majorId;
                    scoreStart = scoreStarts;
                    scoreEnd = scoreEnds;

                    isSearchSubmit = true;
                    Drawable drawable = StudentPoolActivity.this.getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                    binding.tvStudentSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                    binding.tvStudentSearch.setTextColor(StudentPoolActivity.this.getValuesColor(R.color.color_0077ff));

                    page = 1;
                    getStudentList();
                });

        searchWindow.setOnDismissListener(() -> {
            if (!isSearchSubmit) {
                showDownArrow(binding.tvStudentSearch);
            }
            isSearchSubmit = false;
        });
    }

    /**
     * 显示更多菜单
     */
    private void showMoreDialog() {
        PopStudentMoreBinding popBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.pop_student_more, null, false);

        PopupWindow popupWindow = PopUtils.getTransparentWindow(context, popBinding.getRoot());
        popupWindow.showAsDropDown(binding.title, 0, 0, Gravity.END);

        if (PermissionCommon.hasStudentDistribution()) {
            popBinding.tvStudentDistribution.setVisibility(View.VISIBLE);
        } else {
            popBinding.tvStudentDistribution.setVisibility(View.GONE);
        }
        //分配
        popBinding.tvStudentDistribution.setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_DISTRITION_LIST).navigation();
            popupWindow.dismiss();
        });
        if (PermissionCommon.hasStudentRemove()) {
            popBinding.tvStudentDelete.setVisibility(View.VISIBLE);
        } else {
            popBinding.tvStudentDelete.setVisibility(View.GONE);
        }
        //删除
        popBinding.tvStudentDelete.setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_DELETE).navigation(this, REQUEST_CODE_STUDENT_LIST);
            popupWindow.dismiss();
        });
        if (PermissionCommon.hasReturnStudentDistribution()) {
            popBinding.tvStudentBack.setVisibility(View.VISIBLE);
        } else {
            popBinding.tvStudentBack.setVisibility(View.GONE);
        }

        //退回池
        popBinding.tvStudentBack.setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_BACK).navigation();
            popupWindow.dismiss();
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void showUpArrow(TextView tv) {
        if (tv == null) {
            return;
        }

        Drawable arrowGrayUp = getResources().getDrawable(R.mipmap.ic_school_arrow_up);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowGrayUp, null);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void showDownArrow(TextView tv) {
        if (tv == null) {
            return;
        }

        Drawable arrowGrayUp = getResources().getDrawable(R.mipmap.ic_school_arrow_down);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowGrayUp, null);
    }


    @Override
    public void onStudentList(List<StudentListBean> o) {
        if (page == 1) {
            studentAdapter.setNewInstance(o);
        } else {
            studentAdapter.addData(o);
        }
        binding.mrlStudent.finishRefreshAndLoadMore();
    }

    @Override
    public void onAddStudent(String o) {

    }

    @Override
    public void onStudentInfo(StudentInfoBean o) {

    }

    @Override
    public void onDeleteStudent(String o) {
        showToast(o);
        page = 1;
        getStudentList();
    }

    @Override
    public void onBackStudent(String o) {

    }

    @Override
    public void onBackStudentList(List<StudentListBean> o) {

    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        studentAdapter.setNewInstance(null);
        binding.mrlStudent.finishRefreshAndLoadMore();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_STUDENT_LIST && resultCode == RESULT_OK) {
            page = 1;
            getStudentList();
        }
    }
}
