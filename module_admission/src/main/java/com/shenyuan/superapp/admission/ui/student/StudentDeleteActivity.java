package com.shenyuan.superapp.admission.ui.student;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcStudentDeleteBinding;
import com.shenyuan.superapp.admission.adapter.student.StudentDeleteAdapter;
import com.shenyuan.superapp.admission.adapter.student.StudentListAdapter;
import com.shenyuan.superapp.admission.api.presenter.StudentPresenter;
import com.shenyuan.superapp.admission.api.view.StudentView;
import com.shenyuan.superapp.admission.bean.StudentInfoBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.dialog.DialogUtils;
import com.shenyuan.superapp.base.dialog.SimDialog;

import java.util.HashMap;
import java.util.List;

import static com.shenyuan.superapp.base.dialog.BaseDialog.*;

/**
 * @author ch
 * @date 2020/12/15-16:44
 * desc 生源池 - 删除
 */
@Route(path = ARouterPath.Admission.ADMISSION_STUDENT_DELETE)
public class StudentDeleteActivity extends BaseActivity<AcStudentDeleteBinding, StudentPresenter> implements StudentView {
    private StudentDeleteAdapter studentAdapter;

    public static final int REQUEST_CODE_DELETE = 100;

    private int page = 1;

    @Override
    protected StudentPresenter createPresenter() {
        return new StudentPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_student_delete;
    }

    @Override
    protected void initView() {
        studentAdapter = new StudentDeleteAdapter();
        binding.rvStudent.setLayoutManager(new LinearLayoutManager(context));
        binding.rvStudent.setAdapter(studentAdapter);

        if (binding.rvStudent.getItemAnimator() != null) {
            binding.rvStudent.getItemAnimator().setChangeDuration(0);
        }

        studentAdapter.addChildClickViewIds(R.id.ll_student_tel);

        getStudentList();
    }

    @Override
    protected void addListener() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getStudentList();
            }
        });

        binding.mrlStudent.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getStudentList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getStudentList();
            }
        });
        //全选
        binding.cbDistribution.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (StudentListBean b : studentAdapter.getData()) {
                b.setSelect(isChecked);
            }
            studentAdapter.notifyDataSetChanged();
        });
        //删除
        binding.tvDelete.setOnClickListener(v -> new SimDialog.Builder(context).title("是否确认删除？").submitText("确认").onBacklistener(new BaseOnBack() {
            @Override
            public void onConfirm() {
                StringBuilder ids = new StringBuilder();
                for (StudentListBean b : studentAdapter.getData()) {
                    if (b.isSelect()) {
                        if (ids.length() > 0) {
                            ids.append(",");
                        }
                        ids.append(b.getId());
                    }
                }
                if (ids.length() == 0) {
                    showToast("请至少选择一个");
                    return;
                }
                presenter.deleteStudent(ids.toString());

            }
        }).show());

        studentAdapter.setOnItemClickListener((adapter, view, position) -> {
            StudentListBean bean = studentAdapter.getItem(position);
            //分配
            bean.setSelect(!bean.isSelect());
            studentAdapter.notifyItemChanged(position);

        });
        studentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ll_student_tel) {
                DialogUtils.showTelDialog(context, view, studentAdapter.getItem(position).getMobile());
            }
        });
    }


    private void getStudentList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", page);
        map.put("studentName", getTv(binding.etSearch));
        presenter.getStudentList(map);
    }


    @Override
    public void onStudentList(List<StudentListBean> o) {
        if (page == 1) {
            studentAdapter.setNewInstance(o);
        } else {
            studentAdapter.addData(o);
        }
        binding.mrlStudent.finishRefreshAndLoadMore();
    }

    @Override
    public void onAddStudent(String o) {

    }

    @Override
    public void onStudentInfo(StudentInfoBean o) {

    }

    @Override
    public void onDeleteStudent(String o) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackStudent(String o) {

    }

    @Override
    public void onBackStudentList(List<StudentListBean> o) {

    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        binding.mrlStudent.finishRefreshAndLoadMore();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DELETE && resultCode == RESULT_OK) {
            finish();
        }
    }
}
