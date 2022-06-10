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
import com.shenyuan.admission.databinding.AcBackSchoolsBinding;
import com.shenyuan.superapp.admission.adapter.school.SchoolBackAdapter;
import com.shenyuan.superapp.admission.api.presenter.TargetPresenter;
import com.shenyuan.superapp.admission.api.view.TargetView;
import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.admission.ui.AdmissionSearchActivity;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.ARouterPath;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/1 14:15
 * desc 退回库
 */
@Route(path = ARouterPath.Admission.ADMISSION_SCHOOLS_BACK)
public class SchoolBackActivity extends BaseActivity<AcBackSchoolsBinding, TargetPresenter> implements TargetView {
    private SchoolBackAdapter schoolsAdapter;
    private int page = 1;

    public static final int REQUEST_CODE_BACK = 100;

    @Override
    protected TargetPresenter createPresenter() {
        return new TargetPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_back_schools;
    }

    @Override
    protected void initView() {

        schoolsAdapter = new SchoolBackAdapter();
        binding.rvSchools.setLayoutManager(new LinearLayoutManager(context));
        binding.rvSchools.setAdapter(schoolsAdapter);

        getSchoolList();
    }

    /**
     * 学校库列表
     */
    private void getSchoolList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", page);
        presenter.getBackSchoolList(map);
    }

    @Override
    protected void addListener() {
        schoolsAdapter.addChildClickViewIds(R.id.tv_option, R.id.content);
        schoolsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.tv_option) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_DISTRITION_PERSON)
                        .withSerializable("schoolBean", schoolsAdapter.getItem(position)).navigation(this, REQUEST_CODE_BACK);
                EasySwipeMenuLayout menuLayout = (EasySwipeMenuLayout) schoolsAdapter.getViewByPosition(position, R.id.esm_school);
                if (menuLayout != null) {
                    menuLayout.resetStatus();
                }
            } else if (view.getId() == R.id.content) {
                ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_SCHOOLS_ADD)
                        .withInt("schoolId", schoolsAdapter.getItem(position).getId())
                        .withBoolean("editAble", false)
                        .withInt("formWay", AppConstant.FORM_WAY_BACK_SCHOOL)
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
                .withInt("searchType", AdmissionSearchActivity.SEARCH_TYPE_BACK_SCHOOL)
                .navigation());
    }

    @Override
    public void onTargetSchoolList(List<SchoolListBean> o) {

    }

    @Override
    public void onBackSchoolList(List<SchoolListBean> o) {
        if (page == 1) {
            schoolsAdapter.setNewInstance(o);
        } else {
            schoolsAdapter.addData(o);
        }

        binding.mrlSchools.finishRefreshAndLoadMore();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        binding.mrlSchools.finishRefreshAndLoadMore();
    }

    @Override
    public void onBackSchool(String o) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_BACK) {
            page = 1;
            getSchoolList();
        }
    }
}
