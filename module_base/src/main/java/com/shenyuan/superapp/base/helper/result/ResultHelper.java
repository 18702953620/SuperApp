package com.shenyuan.superapp.base.helper.result;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * @author ch
 * 时间： 2019/3/7 0007-上午 10:54
 * 描述：
 * 来源：
 */

public class ResultHelper {

    private final RouterFragment mRouterFragment;
    private static final String TAG = "ResultHelper";
    private final Context mContext;

    /**
     * 初始化
     *
     * @param activity activity
     * @return ResultHelper
     */
    public static ResultHelper init(FragmentActivity activity) {
        return new ResultHelper(activity);
    }

    private ResultHelper(FragmentActivity activity) {
        mContext = activity;
        mRouterFragment = getRouterFragment(activity);
    }

    private RouterFragment getRouterFragment(FragmentActivity activity) {
        RouterFragment routerFragment = findRouterFragment(activity);
        if (routerFragment == null) {
            routerFragment = RouterFragment.newInstance();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(routerFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return routerFragment;
    }

    private RouterFragment findRouterFragment(FragmentActivity activity) {
        return (RouterFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG);
    }

    public void startActivityForResult(Class<?> clazz, CallBack callback) {
        Intent intent = new Intent(mContext, clazz);
        startActivityForResult(intent, callback);
    }

    public void startActivityForResult(Intent intent, CallBack callback) {
        mRouterFragment.startActivityForResult(intent, callback);
    }


    public interface CallBack {
        /**
         * onActivityResult
         *
         * @param resultCode resultCode
         * @param data       data
         */
        void onActivityResult(int resultCode, Intent data);
    }

}
