package com.shenyuan.superapp.admission.ui;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcAddStudentSearchSchoolBinding;
import com.shenyuan.superapp.admission.adapter.school.SchoolNameAdapter;
import com.shenyuan.superapp.admission.adapter.search.SearchHistoryAdapter;
import com.shenyuan.superapp.admission.api.presenter.SearchPresenter;
import com.shenyuan.superapp.admission.api.view.SearchView;
import com.shenyuan.superapp.admission.bean.ClaimListBean;
import com.shenyuan.superapp.admission.bean.FeedBackListBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.SoftInputUtils;
import com.shenyuan.superapp.base.widget.recy.DividerDecoration;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/25 16:29
 * desc 搜索学校
 */
@Route(path = ARouterPath.Admission.ADMISSION_STUDENT_ADD_SEARCH)
public class AddSearchSchoolActivity extends BaseActivity<AcAddStudentSearchSchoolBinding, SearchPresenter> implements SearchView {
    private SchoolNameAdapter schoolAdapter;
    private SearchHistoryAdapter historyAdapter;
    private SearchPresenter searchPresenter;
    @Autowired
    public int state = 1;

    private int page = 1;

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
        return R.layout.ac_add_student_search_school;
    }

    @Override
    protected void initView() {
        historyAdapter = new SearchHistoryAdapter();
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.rvSearchHistory.setLayoutManager(layoutManager);
        binding.rvSearchHistory.setAdapter(historyAdapter);

        schoolAdapter = new SchoolNameAdapter();
        binding.rvResultSchool.setLayoutManager(new LinearLayoutManager(context));
        binding.rvResultSchool.addItemDecoration(new DividerDecoration(getValuesColor(R.color.color_d8d8d8), 1));
        binding.rvResultSchool.setAdapter(schoolAdapter);


        searchPresenter = new SearchPresenter(this);
        searchPresenter.getSchoolHistoryList();
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
                page = 1;
                presenter.getSchoolListLikeName(s.toString(), state, page);
            }
        });

        schoolAdapter.setOnItemClickListener((adapter, view, position) -> {
            //关闭键盘
            SoftInputUtils.hideSoftInput(this);

            searchPresenter.addSchoolHistory(getTv(binding.etSearch));

            Intent intent = new Intent();
            intent.putExtra("school", schoolAdapter.getItem(position));
            intent.putExtra("key", getTv(binding.etSearch));
            setResult(RESULT_OK, intent);

            finish();
        });

        binding.tvFinish.setOnClickListener(v -> finish());

        historyAdapter.setOnItemClickListener((adapter, view, position) -> {
            String bean = historyAdapter.getItem(position);
            binding.etSearch.setText(bean);
            binding.etSearch.setSelection(bean.length());
        });
        binding.llSearchDelete.setOnClickListener(v -> presenter.deleteSchoolHistory());

        binding.mrlSchool.setOnLoadMoreListener(refreshLayout -> {
            page++;
            presenter.getSchoolListLikeName(getTv(binding.etSearch), state, page);
        });
    }

    @Override
    protected void onDestroy() {
        searchPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onSearchSchoolList(List<SchoolInfoBean> o) {

        if (page == 1) {
            schoolAdapter.setNewInstance(o);

            if (schoolAdapter.getData().size() > 0) {
                binding.llSearchResult.setVisibility(View.VISIBLE);
                binding.llSearchEmpty.setVisibility(View.GONE);
            } else {
                binding.llSearchEmpty.setVisibility(View.VISIBLE);
                binding.llSearchResult.setVisibility(View.GONE);
            }
        } else {
            schoolAdapter.addData(o);
        }
        binding.mrlSchool.finishRefreshAndLoadMore();
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

    }

    @Override
    public void onSearchPlanList(List<PlanListBean> o) {

    }

    @Override
    public void onDeleteHistory(String o) {
        historyAdapter.setNewInstance(null);
    }

    @Override
    public void onSearchFeedList(List<FeedBackListBean> o) {

    }

    @Override
    public void onSearchClaimList(List<ClaimListBean> o) {

    }
}
