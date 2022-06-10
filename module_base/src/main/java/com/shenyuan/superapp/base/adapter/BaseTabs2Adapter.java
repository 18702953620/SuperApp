package com.shenyuan.superapp.base.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * @author ch
 * @date 2020/5/5-16:13
 * desc
 */
public abstract class BaseTabs2Adapter extends FragmentStateAdapter {
    private List<String> titles;

    public BaseTabs2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public BaseTabs2Adapter(@NonNull FragmentActivity fragmentActivity, List<String> titles) {
        super(fragmentActivity);
        this.titles = titles;
    }

    /**
     * 获取 fragment
     *
     * @param position position
     * @return Fragment
     */
    protected abstract Fragment getFragmentInPosition(int position);


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return getFragmentInPosition(position);
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }




}
