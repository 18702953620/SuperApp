package com.shenyuan.superstudent.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseStuActivity;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.adapter.IntegralAdapter;
import com.shenyuan.superstudent.api.presenter.UserPresenter;
import com.shenyuan.superstudent.api.view.UserView;
import com.shenyuan.superstudent.bean.IntegralListBean;
import com.shenyuan.superstudent.bean.UserInfoBean;
import com.shenyuan.superstudent.databinding.AcIntegralBindingImpl;

import java.util.List;

/**
 * @author ch
 * @date 2021/5/11 9:27
 * desc 我的积分
 */
@Route(path = ARouterPath.AppStudent.APP_STUDENT_INTEGRAL)
public class IntegralActivity extends BaseStuActivity<AcIntegralBindingImpl, UserPresenter> implements UserView {
    private int page = 1;
    private IntegralAdapter integralAdapter;

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_integral;
    }

    @Override
    protected void initView() {
        presenter.getTotalPoint();
        presenter.getPointDetail(page);

        integralAdapter = new IntegralAdapter();
        binding.rvIntegral.setLayoutManager(new LinearLayoutManager(context));
        binding.rvIntegral.setAdapter(integralAdapter);
    }

    @Override
    protected void addListener() {
        binding.mrlIntegral.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.getPointDetail(page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.getPointDetail(page);
            }
        });
    }

    @Override
    public void onUserInfo(UserInfoBean bean) {

    }

    @Override
    public void onEditAvatar(String o) {

    }

    @Override
    public void onSmsCode(String msgId) {

    }

    @Override
    public void onModify(String o) {

    }

    @Override
    public void onTotalPoint(Integer o) {
        binding.tvIntegral.setText(StrUtils.isEmpty(o));
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        binding.mrlIntegral.finishRefreshAndLoadMore();
    }

    @Override
    public void onPointList(List<IntegralListBean> o) {
        if (page == 1) {
            integralAdapter.setNewInstance(o);
        } else {
            integralAdapter.addData(o);
        }
        binding.mrlIntegral.finishRefreshAndLoadMore();
    }

    @Override
    public void onVisitorMobile(Integer o) {

    }
}
