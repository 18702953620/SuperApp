package com.shenyuan.superapp.admission.ui.school;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcSchoolDeleteBinding;
import com.shenyuan.superapp.admission.adapter.school.SchoolDeleteAdapter;
import com.shenyuan.superapp.admission.api.presenter.SchoolsPresenter;
import com.shenyuan.superapp.admission.api.view.SchoolsView;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.admission.bean.TemSchoolBean;
import com.shenyuan.superapp.admission.ui.AdmissionSearchActivity;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SimDialog;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2020/12/15-16:44
 * desc 学校库
 */
@Route(path = ARouterPath.Admission.ADMISSION_SCHOOLS_DELETE)
public class SchoolDeleteActivity extends BaseActivity<AcSchoolDeleteBinding, SchoolsPresenter> implements SchoolsView {
    private SchoolDeleteAdapter schoolsAdapter;
    private int page = 1;


    @Override
    protected SchoolsPresenter createPresenter() {
        return new SchoolsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_school_delete;
    }

    @Override
    protected void initView() {
        schoolsAdapter = new SchoolDeleteAdapter();
        binding.rvSchools.setLayoutManager(new LinearLayoutManager(context));
        binding.rvSchools.setAdapter(schoolsAdapter);

        if (binding.rvSchools.getItemAnimator() != null) {
            binding.rvSchools.getItemAnimator().setChangeDuration(0);
        }
        //学校库列表
        getSchoolList();
    }

    /**
     * 学校库列表
     */
    private void getSchoolList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", page);
        map.put("schoolName", getTv(binding.etSearch));
        presenter.getSchoolList(map);
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
                getSchoolList();
            }
        });

        //全选
        binding.cbDistribution.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (SchoolListBean b : schoolsAdapter.getData()) {
                b.setSelect(isChecked);
            }
            schoolsAdapter.notifyDataSetChanged();
        });
        //删除
        binding.tvDelete.setOnClickListener(v -> new SimDialog.Builder(context)
                .title(getString(R.string.is_delete_school))
                .submitText(getString(R.string.sure)).onBacklistener(new BaseDialog.BaseOnBack() {
                    @Override
                    public void onConfirm() {
                        StringBuilder ids = new StringBuilder();
                        for (SchoolListBean b : schoolsAdapter.getData()) {
                            if (b.isSelect()) {
                                if (ids.length() > 0) {
                                    ids.append(",");
                                }
                                ids.append(b.getId());
                            }
                        }
                        if (ids.length() == 0) {
                            showToast(getString(R.string.select_at_least_one));
                            return;
                        }
                        presenter.deleteSchool(ids.toString());
                    }
                }).show());

        schoolsAdapter.addChildClickViewIds(R.id.content, R.id.tv_edit);
        schoolsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.content) {
                SchoolListBean bean = schoolsAdapter.getItem(position);
                bean.setSelect(!bean.isSelect());
                schoolsAdapter.notifyItemChanged(position);
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
                .withInt("searchType", AdmissionSearchActivity.SEARCH_TYPE_SCHOOL)
                .navigation());
    }


    @Override
    public void showError(String msg) {
        super.showError(msg);
        binding.mrlSchools.finishRefreshAndLoadMore();
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
        showToast(o);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onSchoolInfo(SchoolInfoBean o) {

    }

    @Override
    public void onUpdateSchool(String o) {

    }

}
