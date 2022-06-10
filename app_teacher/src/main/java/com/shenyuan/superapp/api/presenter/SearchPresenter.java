package com.shenyuan.superapp.api.presenter;

import com.shenyuan.superapp.api.AppApiServer;
import com.shenyuan.superapp.api.view.SearchView;
import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.bean.SearchResultBean;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/19 9:52
 * desc
 */
public class SearchPresenter extends BasePresenter<SearchView> {
    private final AppApiServer appServer;

    public SearchPresenter(SearchView baseView) {
        super(baseView);
        appServer = ApiRetrofit.getInstance().getService(AppApiServer.class);
    }

    /**
     * 搜索
     *
     * @param keyWords keyWords
     * @param isSearch isSearch
     */
    public void search(String keyWords, int isSearch) {
        addDisposable(appServer.search(keyWords, isSearch, 6), new BaseSubscriber<SearchResultBean>(baseView) {
            @Override
            public void onSuccess(SearchResultBean o) {
                baseView.onSearchResult(o);
            }
        });
    }

    /**
     * 搜索历史
     */
    public void searchHistory() {
        addDisposable(appServer.searchHistory(20), new BaseSubscriber<List<String>>(baseView) {
            @Override
            public void onSuccess(List<String> o) {
                baseView.onHistory(o);
            }
        });
    }

    /**
     * 搜索发现
     */
    public void searchFind() {
        addDisposable(appServer.searchFind(10), new BaseSubscriber<List<String>>(baseView) {
            @Override
            public void onSuccess(List<String> o) {
                baseView.onFind(o);
            }
        });
    }

    /**
     * 搜索删除
     */
    public void searchDelete() {
        addDisposable(appServer.searchDelete(), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.onDelete(o);
            }
        });
    }
}
