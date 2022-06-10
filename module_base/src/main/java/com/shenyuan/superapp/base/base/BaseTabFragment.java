package com.shenyuan.superapp.base.base;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.shenyuan.superapp.base.R;
import com.shenyuan.superapp.base.adapter.BaseTabsAdapter;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superapp.base.databinding.FmBaseTabBinding;

import java.util.List;


/**
 * @author ch
 * @date 2020/7/1-11:19
 * @desc tab 页
 */
public abstract class BaseTabFragment<P extends BasePresenter> extends BaseFragment<FmBaseTabBinding, P> implements BaseView {

    private BaseTabsAdapter tabsAdapter;


    /**
     * 创建  Fragment 如果有多个 会调用多次
     *
     * @param position position
     * @return Fragment
     */
    protected abstract Fragment buildFragment(int position);

    /**
     * 创建标题
     *
     * @return List
     */
    protected abstract List<String> buildTitles();

    /**
     * 创建adapter
     */
    protected abstract void buildAdapter();


    @Override
    protected int getLayoutId() {
        return R.layout.fm_base_tab;
    }

    @Override
    protected void initView() {
        buildAdapter();

        setTabIndicatorColor(Color.parseColor("#00ff00"));
        setTabTextColors(Color.parseColor("#00ff00"), Color.parseColor("#0000ff"));

        tabsAdapter = new BaseTabsAdapter(getChildFragmentManager(), buildTitles()) {
            @Override
            protected Fragment getFragmentInPosition(int position) {
                return buildFragment(position);
            }
        };
        binding.vpBase.setAdapter(tabsAdapter);
        binding.tbBase.setupWithViewPager(binding.vpBase);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onErrorCode(String code, String msg) {

    }


    /**
     * 设置标题
     *
     * @param text text
     */
    public void setTitle(@NonNull String text) {
        if (binding != null) {
            binding.title.setTitle(text);
        }
    }

    /**
     * 设置tab text color
     *
     * @param normalColor 正常
     * @param selectColor 选中
     */
    public void setTabTextColors(int normalColor, int selectColor) {
        if (binding != null) {
            binding.tbBase.setTabTextColors(normalColor, selectColor);
        }
    }

    /**
     * 设置下标的颜色
     *
     * @param color color
     */
    public void setTabIndicatorColor(int color) {
        if (binding != null) {
            binding.tbBase.setSelectedTabIndicatorColor(color);
        }
    }

    /**
     * 是否需要标题
     *
     * @param need need
     */
    public void needTitle(boolean need) {
        binding.title.setVisibility(need ? View.VISIBLE : View.GONE);
    }

    /**
     * 是否需要状态栏
     *
     * @param need need
     */
    public void needStatusBar(boolean need) {
        binding.statusbar.setVisibility(need ? View.VISIBLE : View.GONE);
    }

}
