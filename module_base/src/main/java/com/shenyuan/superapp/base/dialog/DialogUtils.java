package com.shenyuan.superapp.base.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.databinding.DataBindingUtil;

import com.shenyuan.superapp.base.R;
import com.shenyuan.superapp.base.databinding.PopTelBinding;
import com.shenyuan.superapp.base.utils.PopUtils;

/**
 * @author ch
 * @date 2021/2/3 17:59
 * desc 特殊dialog 汇总
 */
public class DialogUtils {
    /**
     * 底部拨打电话弹窗
     *
     * @param context context
     * @param view    view
     * @param tel     tel
     */
    public static void showTelDialog(Context context, View view, String tel) {
        if (context == null || view == null || TextUtils.isEmpty(tel)) {
            return;
        }


        PopTelBinding popBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.pop_tel, null, false);

        popBinding.tvTel.setText(tel);

        PopupWindow popupWindow = PopUtils.getPopupWindow(context, popBinding.getRoot(), true, true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popBinding.tvCancel.setOnClickListener(v -> popupWindow.dismiss());
        popBinding.tvTel.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + tel));
            context.startActivity(intent);
        });
    }


}
