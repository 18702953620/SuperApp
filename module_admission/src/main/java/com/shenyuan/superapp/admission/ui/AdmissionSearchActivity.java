package com.shenyuan.superapp.admission.ui;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcSchoolSearchBinding;
import com.shenyuan.superapp.admission.adapter.claim.ClaimAdapter;
import com.shenyuan.superapp.admission.adapter.claim.ClaimNameAdapter;
import com.shenyuan.superapp.admission.adapter.feed.FeedBackAdapter;
import com.shenyuan.superapp.admission.adapter.feed.FeedNameAdapter;
import com.shenyuan.superapp.admission.adapter.plan.PlanAdapter;
import com.shenyuan.superapp.admission.adapter.plan.PlanNameAdapter;
import com.shenyuan.superapp.admission.adapter.school.SchoolBackAdapter;
import com.shenyuan.superapp.admission.adapter.school.SchoolDisAdapter;
import com.shenyuan.superapp.admission.adapter.school.SchoolListAdapter;
import com.shenyuan.superapp.admission.adapter.school.SchoolNameAdapter;
import com.shenyuan.superapp.admission.adapter.school.SchoolTargetAdapter;
import com.shenyuan.superapp.admission.adapter.search.SearchHistoryAdapter;
import com.shenyuan.superapp.admission.adapter.student.MyStudentAdapter;
import com.shenyuan.superapp.admission.adapter.student.StudentBackAdapter;
import com.shenyuan.superapp.admission.adapter.student.StudentListAdapter;
import com.shenyuan.superapp.admission.adapter.student.StudentNameAdapter;
import com.shenyuan.superapp.admission.adapter.student.StudentTargetAdapter;
import com.shenyuan.superapp.admission.api.presenter.ClaimPresenter;
import com.shenyuan.superapp.admission.api.presenter.FeedBackPresenter;
import com.shenyuan.superapp.admission.api.presenter.PlanPresenter;
import com.shenyuan.superapp.admission.api.presenter.SchoolsPresenter;
import com.shenyuan.superapp.admission.api.presenter.SearchPresenter;
import com.shenyuan.superapp.admission.api.presenter.StudentPresenter;
import com.shenyuan.superapp.admission.api.presenter.TargetPresenter;
import com.shenyuan.superapp.admission.api.view.ClaimView;
import com.shenyuan.superapp.admission.api.view.FeedBackView;
import com.shenyuan.superapp.admission.api.view.PlanView;
import com.shenyuan.superapp.admission.api.view.SchoolsView;
import com.shenyuan.superapp.admission.api.view.SearchView;
import com.shenyuan.superapp.admission.api.view.StudentView;
import com.shenyuan.superapp.admission.api.view.TargetView;
import com.shenyuan.superapp.admission.bean.ClaimInfoBean;
import com.shenyuan.superapp.admission.bean.ClaimListBean;
import com.shenyuan.superapp.admission.bean.FeedBackInfoBean;
import com.shenyuan.superapp.admission.bean.FeedBackListBean;
import com.shenyuan.superapp.admission.bean.PlanInfoBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.SimpleStringBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.admission.bean.StudentInfoBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.DialogUtils;
import com.shenyuan.superapp.base.dialog.SimEditDialog;
import com.shenyuan.superapp.base.widget.recy.DividerDecoration;
import com.shenyuan.superapp.common.base.BaseVBAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/8 10:06
 * desc 搜索
 */
@Route(path = ARouterPath.Admission.ADMISSION_SCHOOLS_SEARCH)
public class AdmissionSearchActivity extends BaseActivity<AcSchoolSearchBinding, SearchPresenter> implements SearchView,
        SchoolsView, TargetView, StudentView, PlanView, FeedBackView, ClaimView {

    public static final int REQUEST_CODE = 100;
    public static final int STUDENT_REQUEST_CODE = 101;
    /*
     * 0 学校库
     */
    @Autowired
    public int searchType;
    /**
     * 学校库
     */
    public static final int SEARCH_TYPE_SCHOOL = 100;
    /**
     * 分配学校
     */
    public static final int SEARCH_TYPE_SCHOOL_DIS = 110;
    /**
     * 目标学校
     */
    public static final int SEARCH_TYPE_TARGET_SCHOOL = 120;
    /**
     * 退回库
     */
    public static final int SEARCH_TYPE_BACK_SCHOOL = 130;
    /**
     * 生源池
     */
    public static final int SEARCH_TYPE_STUDENT = 201;
    /**
     * 分配生源
     */
    public static final int SEARCH_TYPE_DIS_STUDENT = 211;
    /**
     * 我的生源
     */
    public static final int SEARCH_TYPE_MY_STUDENT = 221;
    /**
     * 退回池
     */
    public static final int SEARCH_TYPE_BACK_STUDENT = 231;
    /**
     * 招生方案
     */
    public static final int SEARCH_TYPE_PLAN = 301;
    /**
     * 招生反馈
     */
    public static final int SEARCH_TYPE_FEED_BACK = 401;
    /**
     * 招生出差报账
     */
    public static final int SEARCH_TYPE_CLAIM = 501;


    private SchoolsPresenter schoolsPresenter;
    private TargetPresenter targetPresenter;

    private StudentPresenter studentPresenter;

    private PlanPresenter planPresenter;

    private FeedBackPresenter feedBackPresenter;

    private ClaimPresenter claimPresenter;
    /**
     * 学校名称
     */
    private SchoolNameAdapter schoolNameAdapter;
    /**
     * 学生名称
     */
    private StudentNameAdapter studentNameAdapter;
    /**
     * 方案名称
     */
    private PlanNameAdapter planNameAdapter;
    /**
     * 反馈名称
     */
    private FeedNameAdapter feedNameAdapter;
    /**
     * 报账名称
     */
    private ClaimNameAdapter claimNameAdapter;
    /**
     * 搜索历史
     */
    private SearchHistoryAdapter historyAdapter;

    /**
     * 学校库
     */
    private SchoolListAdapter schoolListAdapter;
    /**
     * 分配目标学校
     */
    private SchoolDisAdapter schoolDisAdapter;
    /**
     * 目标学校
     */
    private SchoolTargetAdapter schoolTargetAdapter;
    /**
     * 退回库
     */
    private SchoolBackAdapter schoolBackAdapter;

    /**
     * 生源池
     */
    private StudentListAdapter studentListAdapter;
    /**
     * 分配生源
     */
    private StudentTargetAdapter studentTargetAdapter;
    /**
     * 退回池
     */
    private StudentBackAdapter studentBackAdapter;

    /**
     * 我的生源
     */
    private MyStudentAdapter myStudentAdapter;

    /**
     * 招生方案
     */
    private PlanAdapter planAdapter;
    /**
     * 招生反馈
     */
    private FeedBackAdapter feedAdapter;
    /**
     * 出差报账
     */
    private ClaimAdapter claimAdapter;


    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    protected void setStatusBar() {
        setNoStatusBarByLight();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_school_search;
    }

    @Override
    protected void initView() {
        presenter.getSchoolHistoryList();

        initPresenter();
        initAdapter();
    }

    private void initPresenter() {
        schoolsPresenter = new SchoolsPresenter(this);

        targetPresenter = new TargetPresenter(this);

        studentPresenter = new StudentPresenter(this);

        planPresenter = new PlanPresenter(this);

        feedBackPresenter = new FeedBackPresenter(this);

        claimPresenter = new ClaimPresenter(this);
    }

    private void initAdapter() {
        //学校名称
        schoolNameAdapter = new SchoolNameAdapter();
        //生源名称
        studentNameAdapter = new StudentNameAdapter();
        //方案名称
        planNameAdapter = new PlanNameAdapter();
        //反馈名称
        feedNameAdapter = new FeedNameAdapter();
        //报账名称
        claimNameAdapter = new ClaimNameAdapter();
        //搜索历史列表
        historyAdapter = new SearchHistoryAdapter();

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.rvSearchHistory.setLayoutManager(layoutManager);
        binding.rvSearchHistory.setAdapter(historyAdapter);

        //学校库
        schoolListAdapter = new SchoolListAdapter();
        schoolListAdapter.addChildClickViewIds(R.id.content, R.id.tv_edit);
        schoolListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_edit) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_ADD)
                        .withInt("schoolId", schoolListAdapter.getItem(position).getId())
                        .navigation(this, REQUEST_CODE);
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_ADD)
                        .withInt("schoolId", schoolListAdapter.getItem(position).getId())
                        .withBoolean("showEdit", true)
                        .withBoolean("editAble", false)
                        .navigation(this, REQUEST_CODE);
            }
        });

        //分配学校
        schoolDisAdapter = new SchoolDisAdapter();
        schoolDisAdapter.addChildClickViewIds(R.id.tv_option, R.id.content);
        schoolDisAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_option) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_DISTRITION_PERSON)
                        .withSerializable("schoolBean", schoolDisAdapter.getItem(position)).navigation(this, REQUEST_CODE);
                EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) schoolDisAdapter.getViewByPosition(position, R.id.esm_school);
                if (menuLayout != null) {
                    menuLayout.resetStatus();
                }
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_ADD)
                        .withInt("schoolId", schoolDisAdapter.getItem(position).getId())
                        .withBoolean("editAble", false)
                        .navigation();
            }
        });
        //目标学校
        schoolTargetAdapter = new SchoolTargetAdapter();
        schoolTargetAdapter.addChildClickViewIds(R.id.tv_option, R.id.content);
        schoolTargetAdapter.setOnItemChildClickListener((ap, vw, pn) -> {
            if (vw.getId() == R.id.tv_option) {
                EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) schoolTargetAdapter.getViewByPosition(pn, R.id.esm_school);
                if (menuLayout != null) {
                    menuLayout.resetStatus();
                }
                SchoolListBean bean = schoolTargetAdapter.getItem(pn);

                new SimEditDialog.Builder(context)
                        .submitText("确定")
                        .title("请填写退回原因")
                        .onBacklistener(new BaseDialog.BaseOnBack() {
                            @Override
                            public void onConfirm(Object object) {
                                targetPresenter.returnBackSchool(String.valueOf(bean.getId()), object.toString());
                            }
                        }).show();

            } else if (vw.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_ADD)
                        .withInt("schoolId", schoolTargetAdapter.getItem(pn).getId())
                        .withBoolean("editAble", false)
                        .withInt("formWay", AppConstant.FORM_WAY_TARGET_SCHOOL)
                        .navigation();
            }
        });
        //退回库
        schoolBackAdapter = new SchoolBackAdapter();
        schoolBackAdapter.addChildClickViewIds(R.id.tv_option, R.id.content);
        schoolBackAdapter.setOnItemChildClickListener((ap, vw, pn) -> {
            if (vw.getId() == R.id.tv_option) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_DISTRITION_PERSON)
                        .withSerializable("schoolBean", schoolBackAdapter.getItem(pn)).navigation(this, REQUEST_CODE);
                EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) schoolBackAdapter.getViewByPosition(pn, R.id.esm_school);
                if (menuLayout != null) {
                    menuLayout.resetStatus();
                }
            } else if (vw.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_ADD)
                        .withInt("schoolId", schoolBackAdapter.getItem(pn).getId())
                        .withBoolean("editAble", false)
                        .withInt("formWay", AppConstant.FORM_WAY_BACK_SCHOOL)
                        .navigation();
            }
        });

        //生源池
        studentListAdapter = new StudentListAdapter();
        studentListAdapter.addChildClickViewIds(R.id.content, R.id.tv_edit, R.id.ll_student_tel);
        studentListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            StudentListBean bean = studentListAdapter.getItem(position);
            if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_INFO)
                        .withInt("studentId", bean.getId())
                        .withBoolean("editAble", false)
                        .withBoolean("showEdit", true)
                        .navigation(this, REQUEST_CODE);
            } else if (view.getId() == R.id.tv_edit) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_INFO)
                        .withInt("studentId", bean.getId())
                        .withBoolean("editAble", true)
                        .navigation(this, REQUEST_CODE);
            } else if (view.getId() == R.id.ll_student_tel) {
                DialogUtils.showTelDialog(context, view, studentListAdapter.getItem(position).getMobile());
            }
        });
        //分配生源
        studentTargetAdapter = new StudentTargetAdapter();
        studentTargetAdapter.addChildClickViewIds(R.id.tv_option, R.id.content);
        studentTargetAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_option) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_DISTRITION)
                        .withSerializable("studentBean", studentTargetAdapter.getItem(position)).navigation(this, REQUEST_CODE);
                EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) studentTargetAdapter.getViewByPosition(position, R.id.esm_school);
                if (menuLayout != null) {
                    menuLayout.resetStatus();
                }
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_INFO)
                        .withInt("studentId", studentTargetAdapter.getItem(position).getId())
                        .navigation();
            }
        });
        //退回池
        studentBackAdapter = new StudentBackAdapter();

        studentBackAdapter.addChildClickViewIds(R.id.tv_option, R.id.ll_student_tel, R.id.content);
        studentBackAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_option) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_DISTRITION)
                        .withSerializable("studentBean", studentBackAdapter.getItem(position))
                        .navigation(this, STUDENT_REQUEST_CODE);
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

        myStudentAdapter = new MyStudentAdapter();
        myStudentAdapter.addChildClickViewIds(R.id.tv_option, R.id.ll_student_tel, R.id.content);
        myStudentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ll_student_tel) {
                DialogUtils.showTelDialog(context, view, myStudentAdapter.getItem(position).getMobile());
            } else if (view.getId() == R.id.tv_option) {
                EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) myStudentAdapter.getViewByPosition(position, R.id.esm_school);
                if (menuLayout != null) {
                    menuLayout.resetStatus();
                }

                new SimEditDialog.Builder(context)
                        .submitText("确定")
                        .title("请填写退回原因")
                        .onBacklistener(new BaseDialog.BaseOnBack() {
                            @Override
                            public void onConfirm(Object object) {
                                studentPresenter.returnBackStudent(String.valueOf(myStudentAdapter.getItem(position).getId()), object.toString());
                            }
                        }).show();
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_STUDENT_INFO)
                        .withInt("studentId", myStudentAdapter.getItem(position).getId())
                        .withInt("formWay", AppConstant.FORM_WAY_MY_STUDENT)
                        .navigation();
            }
        });

        //方案
        planAdapter = new PlanAdapter();
        planAdapter.addChildClickViewIds(R.id.content, R.id.tv_edit);
        planAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PlanListBean bean = planAdapter.getItem(position);
            if (view.getId() == R.id.tv_edit) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_PLAN_INFO)
                        .withInt("planId", bean.getId())
                        .withInt("status", bean.getStatus())
                        .withBoolean("showEdit", false)
                        .withBoolean("editAble", true)
                        .navigation(this, REQUEST_CODE);
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_PLAN_INFO)
                        .withInt("planId", bean.getId())
                        .withInt("status", bean.getStatus())
                        .withBoolean("showEdit", true)
                        .withBoolean("editAble", false)
                        .navigation(this, REQUEST_CODE);
            }
        });
        feedAdapter = new FeedBackAdapter();
        feedAdapter.addChildClickViewIds(R.id.tv_top, R.id.tv_edit, R.id.tv_delete, R.id.content);
        feedAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            FeedBackListBean bean = feedAdapter.getItem(position);
            if (view.getId() == R.id.tv_edit) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_FEEDBACK_ADD)
                        .withInt("feedId", bean.getId())
                        .withBoolean("editAble", true)
                        .navigation(this, REQUEST_CODE);
            } else if (view.getId() == R.id.tv_delete) {
                feedBackPresenter.deleteFeed(bean.getId());
            } else if (view.getId() == R.id.tv_top) {
                feedBackPresenter.topFeedBackById(bean.getId());
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_FEEDBACK_ADD)
                        .withInt("feedId", bean.getId())
                        .withBoolean("editAble", false)
                        .withBoolean("showEdit", true)
                        .navigation();
            }

            EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) feedAdapter.getViewByPosition(position, R.id.esm_school);
            if (menuLayout != null) {
                menuLayout.resetStatus();
            }

        });

        claimAdapter = new ClaimAdapter();
        claimAdapter.addChildClickViewIds(R.id.tv_top, R.id.tv_edit, R.id.tv_delete, R.id.content);
        claimAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ClaimListBean bean = claimAdapter.getItem(position);
            if (view.getId() == R.id.tv_edit) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_REIMBURSE_ADD)
                        .withInt("claimId", bean.getId())
                        .withBoolean("editAble", true)
                        .navigation(this, REQUEST_CODE);
            } else if (view.getId() == R.id.tv_delete) {
                claimPresenter.deleteClaim(bean.getId());
            } else if (view.getId() == R.id.tv_top) {
                claimPresenter.topClaimById(bean.getId());
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_REIMBURSE_ADD)
                        .withInt("claimId", bean.getId())
                        .withBoolean("editAble", false)
                        .withBoolean("showEdit", true)
                        .navigation();
            }

            EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) claimAdapter.getViewByPosition(position, R.id.esm_school);
            if (menuLayout != null) {
                menuLayout.resetStatus();
            }

        });
        binding.rvResultSchool.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    protected void addListener() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    binding.clHistory.setVisibility(View.GONE);
                } else {
                    binding.clHistory.setVisibility(View.VISIBLE);
                }
                if (s.length() == 0) {
                    clearData();
                    return;
                }

                binding.cvResult.setVisibility(View.VISIBLE);
                binding.rvResultSchool.setVisibility(View.GONE);

                if (searchType == SEARCH_TYPE_SCHOOL || searchType == SEARCH_TYPE_TARGET_SCHOOL
                        || searchType == SEARCH_TYPE_BACK_SCHOOL || searchType == SEARCH_TYPE_SCHOOL_DIS) {
                    presenter.getSchoolListLikeName(s.toString(), 1);
                } else if (searchType == SEARCH_TYPE_STUDENT || searchType == SEARCH_TYPE_DIS_STUDENT) {
                    presenter.getStudentListLikeName(s.toString());
                } else if (searchType == SEARCH_TYPE_BACK_STUDENT) {
                    presenter.getStudentListLikeNameByReturn(s.toString());
                } else if (searchType == SEARCH_TYPE_MY_STUDENT) {
                    presenter.getStudentListLikeNameByMy(s.toString());
                } else if (searchType == SEARCH_TYPE_PLAN) {
                    presenter.getPlanListLikeName(s.toString());
                } else if (searchType == SEARCH_TYPE_FEED_BACK) {
                    presenter.getFeedListLikeName(s.toString());
                } else if (searchType == SEARCH_TYPE_CLAIM) {
                    presenter.getClaimListLikeName(s.toString());
                }
            }
        });
        //清空搜索历史
        binding.llSearchDelete.setOnClickListener(v -> presenter.deleteSchoolHistory());

        binding.tvFinish.setOnClickListener(v -> finish());

        historyAdapter.setOnItemClickListener((adapter, view, position) -> {
            String bean = historyAdapter.getItem(position);
            binding.etSearch.setText(bean);
            binding.etSearch.setSelection(bean.length());
        });
        //方案
        planNameAdapter.setOnItemClickListener((ap, vw, pn) -> {
            PlanListBean bn = planNameAdapter.getItem(pn);
            presenter.addSchoolHistory(bn.getPlanName());
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", bn.getId());

            planPresenter.getPlanList(map);
        });
        //反馈
        feedNameAdapter.setOnItemClickListener((ap, vw, pn) -> {
            FeedBackListBean bn = feedNameAdapter.getItem(pn);
            presenter.addSchoolHistory(bn.getFbName());

            HashMap<String, Object> map = new HashMap<>();
            map.put("id", bn.getId());
            feedBackPresenter.getFeedBackList(map);
        });
        //反馈
        claimNameAdapter.setOnItemClickListener((ap, vw, pn) -> {
            ClaimListBean bn = claimNameAdapter.getItem(pn);
            presenter.addSchoolHistory(bn.getClaimName());

            HashMap<String, Object> map = new HashMap<>();
            map.put("id", bn.getId());
            claimPresenter.getClaimList(map);
        });
        //生源
        studentNameAdapter.setOnItemClickListener((ap, vw, pn) -> {
            StudentListBean bn = studentNameAdapter.getItem(pn);
            presenter.addSchoolHistory(bn.getName());

            HashMap<String, Object> map = new HashMap<>();
            map.put("id", bn.getId());
            if (searchType == SEARCH_TYPE_STUDENT) {
                //生源池
                studentPresenter.getStudentList(map);
            } else if (searchType == SEARCH_TYPE_DIS_STUDENT) {
                studentPresenter.getStudentList(map);
            } else if (searchType == SEARCH_TYPE_BACK_STUDENT) {
                //退回池
                studentPresenter.getStudentBackList(map);
            } else if (searchType == SEARCH_TYPE_MY_STUDENT) {
                studentPresenter.getMyStudentList(map);
            }
        });
        //学校相关
        schoolNameAdapter.setOnItemClickListener((adapter, view, position) -> {

            SchoolInfoBean ben = schoolNameAdapter.getItem(position);
            presenter.addSchoolHistory(ben.getSchoolName());
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", ben.getId());

            if (searchType == SEARCH_TYPE_SCHOOL) {
                //学校库
                schoolsPresenter.getSchoolList(map);
            } else if (searchType == SEARCH_TYPE_SCHOOL_DIS) {
                //分配学校
                schoolsPresenter.getSchoolList(map);
            } else if (searchType == SEARCH_TYPE_TARGET_SCHOOL) {
                //目标学校
                targetPresenter.getTargetSchoolList(map);
            } else if (searchType == SEARCH_TYPE_BACK_SCHOOL) {
                //退回库
                targetPresenter.getBackSchoolList(map);
            }
        });
    }

    private void clearData() {
        if (binding.rvResultSchool.getVisibility() == View.VISIBLE) {
            BaseVBAdapter adapter = (BaseVBAdapter) binding.rvResultSchool.getAdapter();
            if (adapter != null) {
                adapter.setNeedEmptyView(false);
                adapter.setNewInstance(null);
            }
        }
        if (binding.cvResult.getVisibility() == View.VISIBLE) {
            BaseVBAdapter nameAdapter = (BaseVBAdapter) binding.rvResultName.getAdapter();
            if (nameAdapter != null) {
                nameAdapter.setNeedEmptyView(false);
                nameAdapter.setNewInstance(null);
            }
        }
    }

    @Override
    public void onSchoolList(List<SchoolListBean> o) {
        showSearchResult();
        if (searchType == SEARCH_TYPE_SCHOOL) {
            binding.rvResultSchool.setAdapter(schoolListAdapter);
            schoolListAdapter.setNewInstance(o);
        } else if (searchType == SEARCH_TYPE_SCHOOL_DIS) {
            binding.rvResultSchool.setAdapter(schoolDisAdapter);
            schoolDisAdapter.setNewInstance(o);
        }

    }

    @Override
    public void onAddSchool(String o) {

    }

    @Override
    public void onDeleteSchool(String o) {

    }

    @Override
    public void onSchoolInfo(SchoolInfoBean o) {

    }

    @Override
    public void onUpdateSchool(String o) {

    }


    @Override
    public void onSearchSchoolList(List<SchoolInfoBean> o) {
        binding.rvResultName.setLayoutManager(new LinearLayoutManager(context));
        binding.rvResultName.addItemDecoration(new DividerDecoration(getValuesColor(R.color.color_d8d8d8), 1));
        binding.rvResultName.setAdapter(schoolNameAdapter);
        schoolNameAdapter.setNewInstance(o);
    }

    @Override
    public void onAddHistory(String o) {

    }

    @Override
    public void onHistoryList(List<String> o) {
        historyAdapter.setNewInstance(o);
    }

    @Override
    public void onSearchStudentList(List<StudentListBean> o) {
        binding.rvResultName.setLayoutManager(new LinearLayoutManager(context));
        binding.rvResultName.addItemDecoration(new DividerDecoration(getValuesColor(R.color.color_d8d8d8), 1));
        binding.rvResultName.setAdapter(studentNameAdapter);
        studentNameAdapter.setNewInstance(o);
    }

    @Override
    public void onSearchPlanList(List<PlanListBean> o) {
        binding.rvResultName.setLayoutManager(new LinearLayoutManager(context));
        binding.rvResultName.addItemDecoration(new DividerDecoration(getValuesColor(R.color.color_d8d8d8), 1));
        binding.rvResultName.setAdapter(planNameAdapter);
        planNameAdapter.setNewInstance(o);
    }

    @Override
    public void onDeleteHistory(String o) {
        showToast(getString(R.string.succ_delete));
        historyAdapter.setNewInstance(null);
    }

    @Override
    public void onSearchFeedList(List<FeedBackListBean> o) {
        binding.rvResultName.setLayoutManager(new LinearLayoutManager(context));
        binding.rvResultName.addItemDecoration(new DividerDecoration(getValuesColor(R.color.color_d8d8d8), 1));
        binding.rvResultName.setAdapter(feedNameAdapter);
        feedNameAdapter.setNewInstance(o);
    }

    @Override
    public void onSearchClaimList(List<ClaimListBean> o) {
        binding.rvResultName.setLayoutManager(new LinearLayoutManager(context));
        binding.rvResultName.addItemDecoration(new DividerDecoration(getValuesColor(R.color.color_d8d8d8), 1));
        binding.rvResultName.setAdapter(claimNameAdapter);
        claimNameAdapter.setNewInstance(o);
    }


    @Override
    public void onTargetSchoolList(List<SchoolListBean> o) {
        showSearchResult();
        binding.rvResultSchool.setAdapter(schoolTargetAdapter);
        schoolTargetAdapter.setNewInstance(o);
    }

    @Override
    public void onBackSchoolList(List<SchoolListBean> o) {
        showSearchResult();
        binding.rvResultSchool.setAdapter(schoolBackAdapter);
        schoolBackAdapter.setNewInstance(o);
    }

    @Override
    public void onBackSchool(String o) {
        showToast(o);
        schoolTargetAdapter.setNewInstance(null);
    }


    @Override
    public void onStudentList(List<StudentListBean> o) {
        showSearchResult();
        if (searchType == SEARCH_TYPE_DIS_STUDENT) {
            //分配生源
            binding.rvResultSchool.setAdapter(studentTargetAdapter);
            studentTargetAdapter.setNewInstance(o);
        } else if (searchType == SEARCH_TYPE_MY_STUDENT) {
            //我的生源
            binding.rvResultSchool.setAdapter(myStudentAdapter);
            myStudentAdapter.setNewInstance(o);
        } else {
            //生源池
            binding.rvResultSchool.setAdapter(studentListAdapter);
            studentListAdapter.setNewInstance(o);
        }

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
        finish();
    }

    @Override
    public void onBackStudentList(List<StudentListBean> o) {
        showSearchResult();
        binding.rvResultSchool.setAdapter(studentBackAdapter);
        studentBackAdapter.setNewInstance(o);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            finish();
        }
    }

    @Override
    public void onExamineList(List<SimpleBean> o) {

    }

    @Override
    public void onTaskList(List<SimpleBean> o) {

    }

    @Override
    public void onAddClaim(String o) {

    }

    @Override
    public void onPlanList(List<PlanListBean> o) {
        showSearchResult();

        binding.rvResultSchool.setAdapter(planAdapter);
        planAdapter.setNewInstance(o);
    }

    @Override
    public void onClaimList(List<ClaimListBean> o) {
        showSearchResult();

        binding.rvResultSchool.setAdapter(claimAdapter);
        claimAdapter.setNewInstance(o);
    }

    @Override
    public void onClaimInfo(ClaimInfoBean o) {

    }

    @Override
    public void onAduitClaim(String o) {
        finish();
    }

    @Override
    public void onDeleteClaim(String o) {
        finish();
    }

    @Override
    public void onTopClaim(String o) {
        finish();
    }

    @Override
    public void onUpdateClaim(String o) {
        finish();
    }

    @Override
    public void onCreateList(List<SimpleStringBean> o) {

    }

    @Override
    public void onFeeTypeList(List<SimpleStringBean> o) {

    }

    @Override
    public void onPropagationWayDictListList(List<SimpleStringBean> o) {

    }

    @Override
    public void onMadeList(List<SimpleBean> o) {

    }

    @Override
    public void onIntentList(List<SimpleBean> o) {

    }

    @Override
    public void onGiftList(List<SimpleBean> o) {

    }

    @Override
    public void onListingList(List<SimpleBean> o) {

    }

    @Override
    public void onVisitList(List<SimpleBean> o) {

    }

    @Override
    public void onPlanStaffList(List<StaffBean> o) {

    }

    @Override
    public void onAddFeedBack(String o) {

    }

    @Override
    public void onFeedBackList(List<FeedBackListBean> o) {
        showSearchResult();
        binding.rvResultSchool.setAdapter(feedAdapter);
        feedAdapter.setNewInstance(o);
    }

    @Override
    public void onFeedBackInfo(FeedBackInfoBean o) {

    }

    @Override
    public void onUpdateFeedBack(String o) {
        finish();
    }

    @Override
    public void onDeleteFeedBack(String o) {
        finish();
    }

    @Override
    public void onTopFeedBack(String o) {
        finish();
    }

    @Override
    public void onAddPlan(String o) {

    }

    @Override
    public void onPlanInfo(PlanInfoBean o) {

    }

    @Override
    public void onAduitPlan(String o) {
        finish();
    }

    @Override
    public void onUpdatePlan(String o) {
        finish();
    }


    private void showSearchResult() {
        binding.cvResult.setVisibility(View.GONE);
        binding.rvResultSchool.setVisibility(View.VISIBLE);
    }
}
