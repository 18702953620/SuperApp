package com.shenyuan.superapp.admission.ui.file;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcAdmissionFileBinding;
import com.shenyuan.superapp.admission.api.presenter.FilePresenter;
import com.shenyuan.superapp.admission.api.view.FileView;
import com.shenyuan.superapp.admission.bean.FileTreeBean;
import com.shenyuan.superapp.base.adapter.BaseTabs2Adapter;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.ARouterPath;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/8 9:47
 * desc 招生资料
 */
@Route(path = ARouterPath.Admission.ADMISSION_FILE_LIST)
public class FileActivity extends BaseActivity<AcAdmissionFileBinding, FilePresenter> implements FileView {
    private TabLayoutMediator tabLayoutMediator;
    private List<String> titles;
    private List<Fragment> fragments;

    private boolean isExpanded;

    private final FileFragment.CallBack callBack = () -> presenter.getFileList(getTv(binding.etSearch));

    @Override
    protected FilePresenter createPresenter() {
        return new FilePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_admission_file;
    }

    @Override
    protected void initView() {
        presenter.getFileList("");
        binding.title.addRightListener(v -> ARouter.getInstance().build(ARouterPath.Admission.ADMISSION_FILE_RECORD).navigation());
    }

    private List<String> buildTitles() {
        return titles;
    }

    private Fragment buildFragment(int position) {
        return fragments.get(position);
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
                if (!TextUtils.isEmpty(s)) {
                    isExpanded = true;
                    presenter.getFileList(s.toString());
                } else {
                    isExpanded = false;
                    presenter.getFileList("");
                }

            }
        });
    }

    @Override
    public void onFileList(List<FileTreeBean> o) {
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
        if (o != null && o.size() > 0) {
            for (FileTreeBean bean : o) {
                titles.add(bean.getName());
                FileFragment fragment = new FileFragment(bean, isExpanded);
                fragment.setListCallback(callBack);
                fragments.add(fragment);
            }
        }
        BaseTabs2Adapter tabsAdapter = new BaseTabs2Adapter(this, buildTitles()) {
            @Override
            protected Fragment getFragmentInPosition(int position) {
                return buildFragment(position);
            }
        };
        binding.vpFile.setAdapter(tabsAdapter);

        tabLayoutMediator = new TabLayoutMediator(binding.tlLevel, binding.vpFile, (tab, position) -> tab.setText(buildTitles().get(position)));
        tabLayoutMediator.attach();
    }

    @Override
    public void addDir(String o) {
    }

    @Override
    public void onDelete(String o) {

    }

    @Override
    public void onAddFile(String o) {
    }

    @Override
    protected void onDestroy() {
        if (tabLayoutMediator != null) {
            tabLayoutMediator.detach();
        }
        super.onDestroy();
    }
}
