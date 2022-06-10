package com.shenyuan.superapp.base.adapter;

import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;


/**
 * @author ch
 * @date 2020/4/22-14:19
 * desc
 */
public abstract class BaseTabsAdapter extends FragmentPagerAdapter {
    private ViewGroup viewGroup;
    private final FragmentManager fm;
    private List<String> titles;

    /**
     * 获取 fragment
     *
     * @param position position
     * @return Fragment
     */
    protected abstract Fragment getFragmentInPosition(int position);


    public BaseTabsAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        this.titles = titles;
        this.fm = fm;
    }


    public BaseTabsAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return getFragmentInPosition(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        viewGroup = container;
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : titles.get(position);
    }

    @Override
    public int getCount() {
        return titles == null ? 0 : titles.size();
    }

    public Fragment getFragment(int position) {
        String name = makeFragmentName(viewGroup.getId(), position);
        Fragment fragment = fm.findFragmentByTag(name);
        if (fragment == null) {
            return getItem(position);
        }
        return fragment;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

}
