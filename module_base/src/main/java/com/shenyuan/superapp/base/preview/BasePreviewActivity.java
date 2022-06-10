package com.shenyuan.superapp.base.preview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.SharedElementCallback;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jaeger.library.StatusBarUtil;
import com.shenyuan.superapp.base.R;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.databinding.AcPreviewBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ch
 * @date 2020/7/23-16:51
 * desc 大图预览
 */
public class BasePreviewActivity<P extends BasePresenter<?>> extends BaseActivity<AcPreviewBinding, P> {
    /**
     * 图片列表
     */
    public static final String IMG_LIST = "IMG_LIST";
    /**
     * 进入时的位置
     */
    public static final String IMG_POSITION = "IMG_POSITION";
    /**
     * Adapter
     */
    private BaseQuickAdapter<String, BaseViewHolder> imgAdapter;
    /**
     * 进入时的位置
     */
    private int enterPosition;
    /**
     * 当前位置
     */
    private int currentPosition;
    /**
     * 图片列表
     */
    private ArrayList<String> imgList;

    @Override
    protected P createPresenter() {
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_preview;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucent(this, 0);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void initView() {
        // 延迟共享动画的执行
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }

        imgList = getIntent().getStringArrayListExtra(IMG_LIST);

        enterPosition = getIntent().getIntExtra(IMG_POSITION, 0);

        currentPosition = enterPosition;

        imgAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_base_preview, imgList) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, String item) {
                Glide.with(context).load(item).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //图片加载完成的回调中，启动过渡动画
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startPostponedEnterTransition();
                        }
                        return false;
                    }
                }).into((ImageView) helper.getView(R.id.pv_preview));
            }
        };
        imgAdapter.addChildClickViewIds(R.id.pv_preview);
        PagerSnapHelper snapHelper = new PagerSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                currentPosition = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
                binding.tvCount.setText(String.format("%d/%d", (currentPosition + 1), imgAdapter.getData().size()));
                return currentPosition;
            }
        };
        snapHelper.attachToRecyclerView(binding.rvPreview);

        binding.rvPreview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.rvPreview.setAdapter(imgAdapter);

        binding.rvPreview.scrollToPosition(currentPosition);
        binding.tvCount.setText(String.format("%d/%d", (currentPosition + 1), imgAdapter.getData().size()));
    }

    @Override
    protected void addListener() {
        imgAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.pv_preview) {
                onBackPressed();
            }
        });
    }


    @Override
    public void finishAfterTransition() {
        Intent intent = new Intent();
        intent.putExtra(IMG_POSITION, currentPosition);
        setResult(RESULT_OK, intent);
        super.finishAfterTransition();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (enterPosition != currentPosition) {
            //滑动过，需要刷新
            View exitView = getExitView(currentPosition, imgAdapter, R.id.pv_preview);
            setExitCallback(this, exitView);
        }
    }

    /**
     * 刷新退出 view
     *
     * @param activity activity
     * @param exitView exitView
     */
    private void setExitCallback(Activity activity, View exitView) {
        ActivityCompat.setEnterSharedElementCallback(activity, new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                names.clear();
                sharedElements.clear();
                names.add(ViewCompat.getTransitionName(exitView));
                sharedElements.put(ViewCompat.getTransitionName(exitView), exitView);
            }
        });
    }

    /**
     * 大图预览
     *
     * @param activity activity
     * @param imgList  图片路径
     * @param view     view
     */
    public static void startPreView(Activity activity, ArrayList<String> imgList, View view) {
        startPreView(activity, imgList, view, 0);
    }

    /**
     * 大图预览
     *
     * @param activity activity
     * @param path     图片路径
     * @param view     view
     */
    public static void startPreView(Activity activity, String path, View view) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        if (!new File(path).exists()) {
            Toast.makeText(activity, "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> imgList = new ArrayList<>();
        imgList.add(path);
        startPreView(activity, imgList, view, 0);
    }

    /**
     * 大图预览
     *
     * @param activity activity
     * @param imgList  图片路径
     * @param view     view
     * @param position 当前位置
     */
    public static void startPreView(Activity activity, ArrayList<String> imgList, View view, int position) {
        if (activity == null) {
            return;
        }

        Intent intent = new Intent(activity, BasePreviewActivity.class);
        intent.putExtra(IMG_LIST, imgList);
        intent.putExtra(IMG_POSITION, position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && view != null) {
            //5.0 以上
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(activity, view, "share").toBundle();
            activity.startActivity(intent, bundle);
        } else {
            activity.startActivity(intent);
        }
    }

    /**
     * 获取 指定的view
     *
     * @param position     position
     * @param quickAdapter quickAdapter
     * @param idRes        idRes
     * @return View
     */
    private static View getExitView(int position, BaseQuickAdapter<?, BaseViewHolder> quickAdapter, int idRes) {
        if (position == -1) {
            return null;
        }
        if (quickAdapter != null) {
            return quickAdapter.getViewByPosition(position, idRes);
        }
        return null;
    }

}
