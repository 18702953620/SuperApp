package com.shenyuan.superapp.base.preview;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.app.SharedElementCallback;
import androidx.core.view.ViewCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;
import java.util.Map;

/**
 * @author ch
 * @date 2020/7/29-16:08
 * desc
 */
public class PreViewReenter implements OnActivityReenter {

    private final Activity activity;

    public PreViewReenter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onActivityReenter(int resultCode, Intent intent, BaseQuickAdapter quickAdapter, int idRes) {
        if (resultCode == Activity.RESULT_OK && intent != null) {
            int exitPos = intent.getIntExtra(BasePreviewActivity.IMG_POSITION, -1);
            final View exitView = getExitView(exitPos, quickAdapter, idRes);
            if (exitView != null) {
                ActivityCompat.setExitSharedElementCallback(activity, new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        names.clear();
                        sharedElements.clear();
                        names.add(ViewCompat.getTransitionName(exitView));
                        sharedElements.put(ViewCompat.getTransitionName(exitView), exitView);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            activity.setExitSharedElementCallback(new android.app.SharedElementCallback() {
                                @Override
                                public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                                    super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
                                }
                            });
                        }
                    }
                });
            }
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
    private static View getExitView(int position, BaseQuickAdapter quickAdapter, int idRes) {
        if (position == -1) {
            return null;
        }
        if (quickAdapter != null) {
            return quickAdapter.getViewByPosition(position, idRes);
        }
        return null;
    }
}
