package com.shenyuan.superapp.base.widget;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;

import com.shenyuan.superapp.base.R;
import com.shenyuan.superapp.base.utils.BitmapUtils;

import pl.droidsonroids.gif.GifImageView;

/**
 * @author ch
 * @date 2021/1/8-13:46
 * desc 加载动画
 */
public class GifLoadingView extends DialogFragment {
    private Dialog mDialog;
    private GradientDrawable gd;
    private int CornerRadius = 30;
    private int backGroundColor = Color.parseColor("#001991EC");
    private GifImageView mGifImageView;
    private int id;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mDialog == null) {
            mDialog = new Dialog(getActivity(), R.style.gif_dialog);
            mDialog.setContentView(R.layout.dialog_loading);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.getWindow().setGravity(Gravity.CENTER);
            gd = new GradientDrawable();
            gd.setCornerRadius(CornerRadius);
            mGifImageView = (GifImageView) mDialog.findViewById(R.id.gifImageView);
            setBackGroundColor(BitmapUtils.getPixColor(BitmapFactory.decodeResource(getResources(), id)));
            mGifImageView.setImageResource(id);
            gd.setColor(backGroundColor);
            mDialog.findViewById(R.id.mBackground).setBackground(gd);
        }
        return mDialog;
    }

    private void setResource() {
        if (mDialog == null) {
            return;
        }
        setBackGroundColor(BitmapUtils.getPixColor(BitmapFactory.decodeResource(getResources(), id)));
        mGifImageView.setImageResource(id);
        gd.setColor(backGroundColor);
        mDialog.findViewById(R.id.mBackground).setBackground(gd);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mDialog = null;
    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, "");
    }

    public void setImageResource(int id) {
        this.id = id;
        setResource();
    }

    public void setBackgroundResource(int id) {
        this.id = id;
        setResource();
    }

    public GifImageView getmGifImageView() {
        return mGifImageView;
    }


    public void setCornerRadius(int cornerRadius) {
        CornerRadius = cornerRadius;
    }

    public void setBackGroundColor(int backGroundColor) {
        this.backGroundColor = backGroundColor;
        gd.setColor(backGroundColor);
        mDialog.findViewById(R.id.mBackground).setBackground(gd);
    }
}
