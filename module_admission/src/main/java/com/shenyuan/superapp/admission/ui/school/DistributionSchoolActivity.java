package com.shenyuan.superapp.admission.ui.school;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcDistributionSchoolsBinding;
import com.shenyuan.superapp.admission.adapter.school.SchoolDisAdapter;
import com.shenyuan.superapp.admission.api.presenter.SchoolsPresenter;
import com.shenyuan.superapp.admission.api.view.SchoolsView;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.admission.bean.TemSchoolBean;
import com.shenyuan.superapp.admission.ui.AdmissionSearchActivity;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.base.BaseActivity;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/1/29 16:51
 * desc 分配目标学校
 */

@Route(path = ARouterPath.Admission.ADMISSION_SCHOOLS_DISTRITION)
public class DistributionSchoolActivity extends BaseActivity<AcDistributionSchoolsBinding, SchoolsPresenter> implements SchoolsView {
    private SchoolDisAdapter schoolsAdapter;
    private int page = 1;
    public static final int REQUEST_CODE_DIS_SCHOOL = 100;

    @Override
    protected SchoolsPresenter createPresenter() {
        return new SchoolsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_distribution_schools;
    }

    @Override
    protected void initView() {
        schoolsAdapter = new SchoolDisAdapter();
        schoolsAdapter.setNeedEmptyView(true);
        binding.rvSchools.setLayoutManager(new LinearLayoutManager(context));
        binding.rvSchools.setAdapter(schoolsAdapter);

        getSchoolList();
    }

    private void getSchoolList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", page);
        presenter.getSchoolList(map);
    }

    @Override
    protected void addListener() {
        schoolsAdapter.addChildClickViewIds(R.id.tv_option, R.id.content);
        schoolsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_option) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_DISTRITION_PERSON)
                        .withSerializable("schoolBean", schoolsAdapter.getItem(position)).navigation(this, REQUEST_CODE_DIS_SCHOOL);
                EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) schoolsAdapter.getViewByPosition(position, R.id.esm_school);
                if (menuLayout != null) {
                    menuLayout.resetStatus();
                }
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_ADD)
                        .withInt("schoolId", schoolsAdapter.getItem(position).getId())
                        .withBoolean("editAble", false)
                        .navigation();
            }
        });

        binding.mrlSchools.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getSchoolList();

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getSchoolList();
            }
        });
        binding.llSchoolSearch.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_SEARCH)
                .withInt("searchType", AdmissionSearchActivity.SEARCH_TYPE_SCHOOL_DIS)
                .navigation());
    }

    @Override
    public void onSchoolList(List<SchoolListBean> o) {
        if (page == 1) {
            schoolsAdapter.setNewInstance(o);
        } else {
            schoolsAdapter.addData(o);
        }

        binding.mrlSchools.finishRefreshAndLoadMore();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_DIS_SCHOOL) {
            page = 1;
            getSchoolList();
        }
    }
}
