package com.shenyuan.superapp.admission.ui.student;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcStudentPoolBinding;
import com.shenyuan.superapp.admission.adapter.student.MyStudentAdapter;
import com.shenyuan.superapp.admission.api.presenter.StudentPresenter;
import com.shenyuan.superapp.admission.api.view.StudentView;
import com.shenyuan.superapp.admission.bean.StudentInfoBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.admission.ui.AdmissionSearchActivity;
import com.shenyuan.superapp.admission.window.student.StudentClassWindow;
import com.shenyuan.superapp.admission.window.student.StudentLevelWindow;
import com.shenyuan.superapp.admission.window.student.StudentPurposeWindow;
import com.shenyuan.superapp.admission.window.student.StudentSearchWindow;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.DialogUtils;
import com.shenyuan.superapp.base.dialog.SimDialog;
import com.shenyuan.superapp.base.dialog.SimEditDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2020/12/15-16:44
 * desc 我的生源
 */
@Route(path = ARouterPath.Admission.ADMISSION_STUDENT_POOL_MY)
public class MyStudentPoolActivity extends BaseActivity<AcStudentPoolBinding, StudentPresenter> implements StudentView {
    private MyStudentAdapter studentAdapter;

    public static final int REQUEST_CODE_MY_STUDENT = 100;

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
        binding.title.setTitle("我的生源");
        binding.title.needRightBtn(false);
        studentAdapter = new MyStudentAdapter();
        binding.rvStudent.setLayoutManager(new LinearLayoutManager(context));
        binding.rvStudent.setAdapter(studentAdapter);

        binding.btnStudentAdd.setVisibility(View.GONE);

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
        //层次
        binding.llStudentLevel.setOnClickListener(v -> {
            showUpArrow(binding.tvStudentLevel);
            if (levelWindow == null) {
                levelWindow = new StudentLevelWindow(context);
            }
            levelWindow.showFullAsDropDown(MyStudentPoolActivity.this, binding.llStudentLevel, address -> {
                studentGoal = address.getKey();
                binding.tvStudentLevel.setText(address.getValue());
                binding.tvStudentLevel.setTextColor(getValuesColor(R.color.color_0077ff));

                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                binding.tvStudentLevel.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                page = 1;
                getStudentList();
            });
            levelWindow.setOnDismissListener(() -> {
                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_down);
                binding.tvStudentLevel.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            });
        });
        //意向
        binding.llStudentPurpose.setOnClickListener(v -> {
            showUpArrow(binding.tvStudentPurpose);
            if (purposeWindow == null) {
                purposeWindow = new StudentPurposeWindow(context);
            }

            purposeWindow.showFullAsDropDown(MyStudentPoolActivity.this, binding.llStudentLevel, address -> {
                studentTarget = address.getKey();
                binding.tvStudentPurpose.setText(address.getValue());
                binding.tvStudentPurpose.setTextColor(getValuesColor(R.color.color_0077ff));

                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                binding.tvStudentPurpose.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                page = 1;
                getStudentList();
            });
            purposeWindow.setOnDismissListener(() -> {
                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_down);
                binding.tvStudentPurpose.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            });
        });
        //科类
        binding.llStudentClass.setOnClickListener(v -> {
            showUpArrow(binding.tvStudentClass);
            if (classWindow == null) {
                classWindow = new StudentClassWindow(context);
            }

            classWindow.showFullAsDropDown(MyStudentPoolActivity.this, binding.llStudentLevel, address -> {
                subject = address.getKey();
                binding.tvStudentClass.setText(address.getValue());
                binding.tvStudentClass.setTextColor(getValuesColor(R.color.color_0077ff));

                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                binding.tvStudentClass.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                page = 1;
                getStudentList();
            });
            classWindow.setOnDismissListener(() -> {
                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_down);
                binding.tvStudentClass.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            });
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

        studentAdapter.addChildClickViewIds(R.id.tv_option, R.id.ll_student_tel, R.id.content);
        studentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ll_student_tel) {
                DialogUtils.showTelDialog(context, view, studentAdapter.getItem(position).getMobile());
            } else if (view.getId() == R.id.tv_option) {
                EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) studentAdapter.getViewByPosition(position, R.id.esm_school);
                if (menuLayout != null) {
                    menuLayout.resetStatus();
                }

                new SimEditDialog.Builder(context)
                        .submitText("确定")
                        .title("请填写退回原因")
                        .onBacklistener(new BaseDialog.BaseOnBack() {
                            @Override
                            public void onConfirm(Object object) {
                                presenter.returnBackStudent(String.valueOf(studentAdapter.getItem(position).getId()), object.toString());
                            }
                        }).show();
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_INFO)
                        .withInt("studentId", studentAdapter.getItem(position).getId())
                        .withInt("formWay", AppConstant.FORM_WAY_MY_STUDENT)
                        .withBoolean("showEdit", true)
                        .navigation();
            }
        });

        binding.llSchoolSearch.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_SEARCH)
                .withInt("searchType", AdmissionSearchActivity.SEARCH_TYPE_MY_STUDENT)
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
        presenter.getMyStudentList(map);
    }


    /**
     * 筛选
     */
    private void showSearchDialog() {
        if (searchWindow == null) {
            searchWindow = new StudentSearchWindow(context);
        }
        searchWindow.showFullAsDropDown(MyStudentPoolActivity.this,
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
                    Drawable drawable = MyStudentPoolActivity.this.getDrawableRes(R.mipmap.ic_school_arrow_up_blue);
                    binding.tvStudentSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                    binding.tvStudentSearch.setTextColor(MyStudentPoolActivity.this.getValuesColor(R.color.color_0077ff));

                    page = 1;
                    getStudentList();
                });

        searchWindow.setOnDismissListener(() -> {
            if (!isSearchSubmit) {
                Drawable drawable = getDrawableRes(R.mipmap.ic_school_arrow_down);
                binding.tvStudentSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
            isSearchSubmit = false;
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
    }

    @Override
    public void onBackStudent(String o) {
        showToast(o);
        page = 1;
        getStudentList();
    }

    @Override
    public void onBackStudentList(List<StudentListBean> o) {

    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        binding.mrlStudent.finishRefreshAndLoadMore();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MY_STUDENT && resultCode == RESULT_OK) {
            page = 1;
            getStudentList();
        }
    }
}
