package com.shenyuan.superapp.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.ch.cper.CPermission;
import com.ch.cper.PermissGroup;
import com.ch.cper.listener.PermissListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.h.cheng.filepicker.PsPickManager;
import com.h.cheng.filepicker.bean.NormalFile;
import com.h.cheng.filepicker.config.PickConfig;
import com.shenyuan.superapp.base.preview.BasePreviewActivity;
import com.shenyuan.superapp.base.utils.AppUtils;
import com.shenyuan.superapp.base.widget.recy.SpaceDecoration;
import com.shenyuan.superapp.common.MyImgLoader;
import com.shenyuan.superapp.common.R;
import com.shenyuan.superapp.common.databinding.ItemImgPickerBinding;
import com.shenyuan.superapp.common.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2020/10/10-17:12
 * desc 图片选择
 */
public class ImagePickerView extends LinearLayout {
    private Context context;
    private int maxChoosePic = 1;
    private int spanCount = 3;
    private Drawable addRes;
    private SelectAdapter selectAdapter;
    private ArrayList<String> pathList;

    public ImagePickerView(@NonNull Context context) {
        super(context);
    }

    public ImagePickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        parseAttrs(context, attrs);
        init();
    }

    public ImagePickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        parseAttrs(context, attrs);
        init();
    }

    private void parseAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImagePickerView);
        addRes = typedArray.getDrawable(R.styleable.ImagePickerView_ps_mv_add);
        maxChoosePic = typedArray.getInt(R.styleable.ImagePickerView_ps_mv_maxCount, 1);
        spanCount = typedArray.getInt(R.styleable.ImagePickerView_ps_mv_spanCount, 3);
        typedArray.recycle();
    }


    private void init() {
        RecyclerView rvChoose = new RecyclerView(context);
        pathList = new ArrayList<>();
        pathList.add(null);
        selectAdapter = new SelectAdapter(pathList);
        rvChoose.setLayoutManager(new GridLayoutManager(context, spanCount));
        rvChoose.setAdapter(selectAdapter);
        rvChoose.setOverScrollMode(OVER_SCROLL_NEVER);
        rvChoose.addItemDecoration(new SpaceDecoration(20, context.getResources().getColor(R.color.color_ffffff)));

        selectAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (adapter.getData().get(position) == null) {
                if (context instanceof FragmentActivity) {
                    CPermission.with(context).permiss().permission(PermissGroup.STORAGE).listener(new PermissListener<String>() {
                        @Override
                        public void onGranted(List<String> li) {
                            PickConfig config = new PickConfig();
                            config.setMax(maxChoosePic);
                            config.setSpanCount(spanCount);
                            config.setTitle("请选择图片");
                            config.setBackColor(Color.parseColor("#ffffff"));
                            config.setTitleColor(Color.parseColor("#333333"));
                            config.setRightColor(Color.parseColor("#333333"));
                            config.setImageLoader(new MyImgLoader());

                            PsPickManager.openImagePicker((FragmentActivity) context, config, list -> {
                                if (list != null) {
                                    ArrayList<String> lists = new ArrayList<>();
                                    for (NormalFile file : list) {
                                        String path = file.getPath();
                                        lists.add(path);
                                    }
                                    setPathList(lists);
                                }
                            });
                        }
                        @Override
                        public void onDenied(List<String> list) {

                        }
                    }).start();
                }
            } else {
                if (context instanceof Activity) {
                    BasePreviewActivity.startPreView((Activity) context, getPathList(), view, position);
                }
            }
        });
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(rvChoose, params);
    }

    /**
     * 获取路径 移除kong
     *
     * @return String
     */
    public ArrayList<String> getPathList() {
        ArrayList<String> strings = new ArrayList<>(pathList);
        strings.remove(null);
        return strings;
    }


    public void clear() {
        pathList.clear();
        pathList.add(null);
        if (selectAdapter != null) {
            selectAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置路径列表
     *
     * @param pathList pathList
     */
    public void setPathList(ArrayList<String> pathList) {
        if (pathList != null && pathList.size() > 0) {
            pathList.remove(null);
            if (pathList.size() < maxChoosePic) {
                pathList.add(null);
            }
        }
        this.pathList = pathList;
        if (selectAdapter != null) {
            selectAdapter.setNewInstance(pathList);
        }
    }

    class SelectAdapter extends BaseQuickAdapter<String, BaseDataBindingHolder> {

        public SelectAdapter(List<String> list) {
            super(R.layout.item_img_picker, list);
        }

        @Override
        protected void convert(BaseDataBindingHolder helper, String item) {
            ItemImgPickerBinding bi = (ItemImgPickerBinding) helper.getDataBinding();
            if (bi == null) {
                return;
            }

            if (item == null) {
                bi.btnDel.setVisibility(GONE);
                bi.ivPickImg.setImageDrawable(addRes);
            } else {
                bi.btnDel.setVisibility(VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    //10.0以上需要使用uri加载图片
                    Uri uri = AppUtils.getImageContentUri(context, item);
                    GlideUtils.load(context, uri, bi.ivPickImg, new RequestOptions().centerCrop());
                } else {
                    GlideUtils.load(context, item, bi.ivPickImg, new RequestOptions().centerCrop());
                }
            }
            helper.getView(R.id.btn_del).setOnClickListener(v -> {
                getData().remove(helper.getAdapterPosition());
                if (getData().size() < maxChoosePic && !getData().contains(null)) {
                    getData().add(null);
                }
                notifyDataSetChanged();
            });
        }
    }
}
