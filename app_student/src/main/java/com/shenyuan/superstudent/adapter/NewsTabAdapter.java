package com.shenyuan.superstudent.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.shenyuan.superapp.base.adapter.BaseTabs2Adapter;
import com.shenyuan.superstudent.bean.NewsTypeBean;
import com.shenyuan.superstudent.ui.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/29 16:38
 * desc
 */
public class NewsTabAdapter extends BaseTabs2Adapter {

    private final List<NewsFragment> newsFragments = new ArrayList<>();
    private final List<NewsTypeBean> newsTypeBeans;

    public NewsTabAdapter(@NonNull FragmentActivity fragmentActivity, List<String> titles, List<NewsTypeBean> o) {
        super(fragmentActivity, titles);
        this.newsTypeBeans = o;
    }
    @Override
    protected Fragment getFragmentInPosition(int position) {
        NewsFragment newsFragment = new NewsFragment(position, newsTypeBeans.get(position));

        if (!newsFragments.contains(newsFragment)) {
            newsFragment.setUserVisibleHint(true);
            newsFragments.add(newsFragment);
        }
        return newsFragment;
    }

    public NewsFragment getFragmentByPosition(int position) {
        return newsFragments.get(position);
    }
}
