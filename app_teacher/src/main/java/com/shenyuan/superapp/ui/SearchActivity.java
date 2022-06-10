package com.shenyuan.superapp.ui;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.adapter.NewsAdapter;
import com.shenyuan.superapp.adapter.SearchHistoryAdapter;
import com.shenyuan.superapp.adapter.SearchHotAdapter;
import com.shenyuan.superapp.api.presenter.SearchPresenter;
import com.shenyuan.superapp.api.view.SearchView;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.DensityUtils;
import com.shenyuan.superapp.base.widget.PSTextView;
import com.shenyuan.superapp.base.widget.recy.DividerDecoration;
import com.shenyuan.superapp.bean.MenuBean;
import com.shenyuan.superapp.bean.SearchResultBean;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superapp.databinding.AcSearchBinding;
import com.shenyuan.superapp.databinding.ItemSearchMenuBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author ch
 * @date 2021/1/7-16:03
 * desc 搜索
 */
@Route(path = ARouterPath.AppTeacher.APP_SEARCH)
public class SearchActivity extends BaseActivity<AcSearchBinding, SearchPresenter> implements SearchView {
    private SearchHistoryAdapter historyAdapter;
    private SearchHotAdapter hotAdapter;
    private NewsAdapter newsAdapter;
    private BaseVBAdapter<MenuBean, BaseDataBindingHolder> menuAdapter;

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
        return R.layout.ac_search;
    }

    @Override
    protected void initView() {
        //搜索历史
        presenter.searchHistory();
        //搜索发现
        presenter.searchFind();

        historyAdapter = new SearchHistoryAdapter();

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.rvSearchHistory.setLayoutManager(layoutManager);
        binding.rvSearchHistory.setAdapter(historyAdapter);

        hotAdapter = new SearchHotAdapter();
        binding.rvSearchHot.setLayoutManager(new LinearLayoutManager(context));
        DividerDecoration decoration = new DividerDecoration(getValuesColor(R.color.color_e5e5e5),
                1, DensityUtils.dp2px(context, 47), DensityUtils.dp2px(context, 15));
        binding.rvSearchHot.addItemDecoration(decoration);
        binding.rvSearchHot.setAdapter(hotAdapter);


        newsAdapter = new NewsAdapter();
        binding.rvResultNews.setLayoutManager(new LinearLayoutManager(context));
        binding.rvResultNews.setAdapter(newsAdapter);

        menuAdapter = new BaseVBAdapter<MenuBean, BaseDataBindingHolder>(R.layout.item_search_menu, false) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder baseViewHolder, MenuBean s) {
                ItemSearchMenuBinding bi = (ItemSearchMenuBinding) baseViewHolder.getDataBinding();
                if (bi == null) {
                    return;
                }
                bi.tvMenuName.setText(s.getServiceName());
                GlideUtils.load(context, s.getIconUrl(), bi.ivMenuImg);
            }
        };
        binding.rvResultMenu.setLayoutManager(new LinearLayoutManager(context));
        binding.rvResultMenu.setAdapter(menuAdapter);
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
                if (!TextUtils.isEmpty(s)) {
                    binding.llSearchResult.setVisibility(View.VISIBLE);
                    binding.llSearchEmpty.setVisibility(View.GONE);
                } else {
                    binding.llSearchResult.setVisibility(View.GONE);
                    binding.llSearchEmpty.setVisibility(View.VISIBLE);
                }
                if (s.length() > 0) {
                    binding.tvFinish.setText(getString(R.string.search));
                } else {
                    binding.tvFinish.setText(getString(R.string.cancel));
                }
                if (!TextUtils.isEmpty(s)) {
                    presenter.search(s.toString(), 0);
                }
            }
        });
        historyAdapter.setItemClickListener((bean, view, position) -> {
            binding.etSearch.setText(bean);
            binding.etSearch.setSelection(bean.length());
        });
        hotAdapter.setItemClickListener((bean, view, position) -> {
            binding.etSearch.setText(bean);
            binding.etSearch.setSelection(bean.length());
        });

        binding.tvFinish.setOnClickListener(v -> {
            if (getString(R.string.search).equals(getTv(binding.tvFinish))) {
                presenter.search(getTv(binding.etSearch), 1);
            } else {
                finish();
            }
        });
        //删除
        binding.llSearchDelete.setOnClickListener(v -> presenter.searchDelete());

        newsAdapter.setItemClickListener((bean, view, position) -> {
            ARouter.getInstance().build(ARouterPath.Common.COMMON_WEB).withString("newsId",
                    String.valueOf(bean.getId())).navigation();
        });

        menuAdapter.setItemClickListener((bean, view, position) -> {
            if (bean == null) {
                showToast(getString(R.string.more_arouter_error));
                return;
            }

            if (getString(R.string.more).equals(bean.getServiceName())) {
                ARouter.getInstance().build(ARouterPath.AppTeacher.APP_MORE).navigation();
                return;
            }

            ARouterPath.router(bean.getRouterUrl());
        });
    }

    @Override
    public void onSearchResult(SearchResultBean o) {
        if (o != null) {
            newsAdapter.setNewInstance(o.getNewsList());

            menuAdapter.setNewInstance(o.getServiceInfoList());
        }
    }

    @Override
    public void onHistory(List<String> o) {
        historyAdapter.setNewInstance(o);
        binding.rvSearchHistory.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFind(List<String> o) {
        hotAdapter.setNewInstance(o);
    }

    @Override
    public void onDelete(String o) {
        showToast(getString(R.string.succ_delete));
        historyAdapter.setNewInstance(null);
        binding.rvSearchHistory.setVisibility(View.GONE);
    }
}
