package com.shenyuan.superapp.admission.ui.student;

import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcAddRecordBinding;
import com.shenyuan.superapp.admission.api.presenter.RecordPresenter;
import com.shenyuan.superapp.admission.api.view.RecordView;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SimDialog;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/4 15:23
 * desc 添加沟通记录
 */
@Route(path = ARouterPath.Admission.ADMISSION_STUDENT_ADD_RECORD)
public class AddRecordActivity extends BaseActivity<AcAddRecordBinding, RecordPresenter> implements RecordView {

    private int targetDegree = -1;
    @Autowired
    public int studentId;

    @Override
    protected RecordPresenter createPresenter() {
        return new RecordPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_add_record;
    }

    @Override
    protected void initView() {
        presenter.getTargetDegreeList();
    }

    @Override
    protected void addListener() {
        binding.tvSubmit.setOnClickListener(v -> {
            if (targetDegree == -1) {
                showToast(getString(R.string.please_select_target_degree));
                return;
            }

            if (TextUtils.isEmpty(getTv(binding.etText))) {
                showToast(getString(R.string.please_input_record));
                return;
            }
            new SimDialog.Builder(context).title("是否确认提交？").onBacklistener(new BaseDialog.BaseOnBack() {
                @Override
                public void onConfirm() {
                    presenter.addCommu(getTv(binding.etText), studentId, targetDegree, binding.pickPhoto.getPathList());
                }
            }).show();
        });

        binding.tvRest.setOnClickListener(v -> {
            targetDegree = -1;
            binding.pickIntent.clear();
            binding.etText.setText("");
            binding.pickPhoto.clear();
        });
    }


    @Override
    public void onTargetDegree(List<SimpleBean> o) {
        binding.pickIntent.setData(o, "value", bean -> targetDegree = bean.getKey());
    }

    @Override
    public void onAddCommu(String o) {
        showToast(o);
        setResult(RESULT_OK);
        finish();
    }
}
