package com.shenyuan.superstudent.api.view;

import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superstudent.bean.SearchResultBean;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/19 9:51
 * desc
 */
public interface SearchView extends BaseView {
    void onSearchResult(SearchResultBean o);

    void onHistory(List<String> o);

    void onFind(List<String> o);

    void onDelete(String o);
}
