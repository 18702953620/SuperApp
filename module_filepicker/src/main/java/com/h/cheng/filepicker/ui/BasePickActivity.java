package com.h.cheng.filepicker.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.h.cheng.filepicker.PsPickManager;
import com.h.cheng.filepicker.R;
import com.h.cheng.filepicker.bean.NormalFile;
import com.h.cheng.filepicker.config.PickConfig;
import com.h.cheng.filepicker.databinding.AcFilePickerBinding;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.base.BaseActivity;

import java.util.ArrayList;

/**
 * @author ch
 * @date 2020/8/21-18:23
 * @desc 选择基类
 */
public class BasePickActivity extends BaseActivity<AcFilePickerBinding, BasePresenter> {

    protected String[] suffix;
    /**
     * 最大选择数
     */
    protected int max = 1;
    /**
     * 标题
     */
    protected String title;
    /**
     * 选中列表
     */
    protected ArrayList<NormalFile> selectedList = new ArrayList<>();

    protected PickConfig config;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_file_picker;
    }

    @Override
    protected void initView() {
        config = (PickConfig) getIntent().getSerializableExtra(PsPickManager.CONFIG);
        if (config != null) {
            //最大数量
            max = config.getMax();
            //筛选
            suffix = config.getSuffix();
            //标题
            title = config.getTitle();
            if (TextUtils.isEmpty(title)) {
                title = getString(R.string.picker_select_file);
            }
            //背景颜色
            if (config.getBackColor() != 0) {
                binding.rlPickerTitle.setBackgroundColor(config.getBackColor());
            }
            //返回键
            if (config.getBackRes() != 0) {
                binding.ivPickerBack.setImageResource(config.getBackRes());
            }
            //标题颜色
            if (config.getTitleColor() != 0) {
                binding.tvPickerTitle.setTextColor(config.getTitleColor());
            }
            //右边颜色
            if (config.getRightColor() != 0) {
                binding.tvPickerSelect.setTextColor(config.getRightColor());
                binding.tvPickerSelect.setPsBorderColor(config.getRightColor());
                binding.tvPickerSelect.setPsBtnBackgroundColor(config.getRightBackColor());
            }
            if (config.getSelectList() != null) {
                selectedList = config.getSelectList();
            }

            binding.tvPickerTitle.setText(title);
            binding.tvPickerSelect.setText("确定(" + (String.format("%d/%d", 0, max)) + ")");
        }
    }

    @Override
    protected void addListener() {
        binding.llPickerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.tvPickerSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedList.size() == 0) {
                    showToast(getString(R.string.picker_more_than_one));
                    return;
                }

                callResultBack();
            }
        });
    }

    public void callResultBack() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(PsPickManager.RESULT_PICK_FILE, selectedList);
        setResult(RESULT_OK, intent);
        finish();
    }
}
