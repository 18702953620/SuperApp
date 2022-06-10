package com.shenyuan.superapp.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.adapter.MsgAdapter;
import com.shenyuan.superapp.api.presenter.MsgPresenter;
import com.shenyuan.superapp.api.view.MsgView;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseFragment;
import com.shenyuan.superapp.base.utils.DensityUtils;
import com.shenyuan.superapp.base.utils.PopUtils;
import com.shenyuan.superapp.base.utils.ScreenUtils;
import com.shenyuan.superapp.bean.MsgListBean;
import com.shenyuan.superapp.databinding.FmMessageBinding;
import com.shenyuan.superapp.databinding.PopMsgDeleteBinding;

import java.util.List;

/**
 * @author ch
 * @date 2021/1/4-10:58
 * desc 消息
 */
public class MessageFragment extends BaseFragment<FmMessageBinding, MsgPresenter> implements MsgView {
    private int page = 1;
    private MsgAdapter msgAdapter;

    public static final int REQUEST_MSG_INFO = 100;

    @Override
    protected void loadData() {
        presenter.getMsgList(page);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fm_message;
    }

    @Override
    protected void initView() {
        msgAdapter = new MsgAdapter();
        binding.rvMsg.setLayoutManager(new LinearLayoutManager(context));
        binding.rvMsg.setAdapter(msgAdapter);
    }

    @Override
    protected void addListener() {
        binding.mrlMsg.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.getMsgList(page);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.getMsgList(page);
            }
        });
        msgAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            showDeleteDialog(view, position);
            return false;
        });
        msgAdapter.setItemClickListener((bean, view, position) -> {
            bean.setViewed(1);
            msgAdapter.notifyItemChanged(position);
            ARouter.getInstance().build(ARouterPath.Common.COMMON_WEB)
                    .withString("msgId", String.valueOf(bean.getId())).navigation(getActivity(), REQUEST_MSG_INFO);
        });
    }

    /**
     * @param view     view
     * @param position position
     */
    private void showDeleteDialog(View view, int position) {
        PopMsgDeleteBinding popBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.pop_msg_delete, null, false);

        PopupWindow popupWindow = PopUtils.getTransparentWindow(context, popBinding.getRoot());
        int width = ScreenUtils.getScreenWidth(context);
        //3/4
        popupWindow.showAsDropDown(view, width / 4 * 3 - DensityUtils.dp2px(context, 30), -DensityUtils.dp2px(context, 10));

        popBinding.tvMsgDelete.setOnClickListener(v -> {
            presenter.delMsg(msgAdapter.getItem(position).getId(), position);
            popupWindow.dismiss();
        });

    }


    @Override
    protected MsgPresenter createPresenter() {
        return new MsgPresenter(this);
    }

    @Override
    public void onNoticeList(List<MsgListBean> o) {
        if (page == 1) {
            msgAdapter.setNewInstance(o);
        } else {
            msgAdapter.addData(o);
        }
        binding.mrlMsg.finishRefreshAndLoadMore();
    }

    @Override
    public void onDelMsg(String o, int position) {
        showToast(getString(R.string.succ_delete));
        msgAdapter.removeAt(position);
    }

    @Override
    public void onErrorCode(String code, String msg) {

    }
}
