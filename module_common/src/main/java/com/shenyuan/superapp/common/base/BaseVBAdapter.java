package com.shenyuan.superapp.common.base;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.superapp.common.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/20 11:54
 * desc
 */
public class BaseVBAdapter<T, VB extends BaseDataBindingHolder> extends BaseQuickAdapter<T, BaseDataBindingHolder> {
    /**
     * 是否需要空view
     */
    protected boolean needEmptyView = true;

    public void setNeedEmptyView(boolean needEmptyView) {
        this.needEmptyView = needEmptyView;
    }

    public BaseVBAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BaseVBAdapter(int layoutResId, @Nullable List<T> data, boolean needEmptyView) {
        super(layoutResId, data);
        this.needEmptyView = needEmptyView;
    }

    public BaseVBAdapter(int layoutResId, boolean needEmptyView) {
        super(layoutResId);
        this.needEmptyView = needEmptyView;
    }

    public BaseVBAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void setNewInstance(@Nullable List<T> list) {
        super.setNewInstance(list);

        if (list == null || list.size() == 0) {
            if (needEmptyView && getContext() != null) {
                setEmptyView(getViewByRes(R.layout.empty_no_data));
            }
        }
    }

    public void setItemClickListener(ItemClickListener<T> listener) {
        if (listener == null) {
            return;
        }
        setOnItemClickListener((adapter, view, position) -> listener.onItemClick(getItem(position), view, position));
    }

    /**
     * @param color color
     * @return int
     */
    public int getValuesColor(int color) {
        return getContext().getResources().getColor(color);
    }

    /**
     * 通过资源res获得view
     *
     * @param res res
     * @return View
     */
    public View getViewByRes(@LayoutRes int res) {
        return LayoutInflater.from(getContext()).inflate(res, null);
    }


    /**
     * @param s s
     */
    public void showToast(String s) {
        Toast toast = Toast.makeText(getContext(), s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, T t) {

    }


    public interface ItemClickListener<T> {
        /**
         * 点击事件
         *
         * @param bean     bean
         * @param view     view
         * @param position position
         */
        void onItemClick(@NonNull T bean, @NonNull View view, int position);
    }
}
