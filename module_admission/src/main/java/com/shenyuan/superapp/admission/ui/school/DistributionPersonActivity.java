package com.shenyuan.superapp.admission.ui.school;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.AcDistributionPersonBinding;
import com.shenyuan.superapp.admission.adapter.StaffAppointAdapter;
import com.shenyuan.superapp.admission.adapter.StaffDisAdapter;
import com.shenyuan.superapp.admission.api.presenter.DistributionPresenter;
import com.shenyuan.superapp.admission.api.view.DistributionView;
import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SimDialog;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superapp.base.utils.StrUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/1 14:44
 * desc 分配宣传员
 */
@Route(path = ARouterPath.Admission.ADMISSION_DISTRITION_PERSON)
public class DistributionPersonActivity extends BaseActivity<AcDistributionPersonBinding, DistributionPresenter> implements DistributionView {

    private StaffDisAdapter allAdapter;
    private StaffAppointAdapter appointAdapter;
    @Autowired
    public SchoolListBean schoolBean;

    @Override
    protected DistributionPresenter createPresenter() {
        return new DistributionPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_distribution_person;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void initView() {
        List<StaffBean> staffBeans = new ArrayList<>();
        if (schoolBean != null) {
            //名称
            binding.tvSchoolName.setText(StrUtils.isEmpty(schoolBean.getSchoolName()));
            //地址
            binding.tvSchoolLocation.setText(String.format("%s%s%s", StrUtils.isEmpty(schoolBean.getProvinceName()), StrUtils.isEmpty(schoolBean.getCityName())
                    , StrUtils.isEmpty(schoolBean.getRegionName())));
            //所属区域
            binding.tvSchoolArea.setText(String.format("所属区域：%s", StrUtils.isEmpty(schoolBean.getAreaName())));
            //区域负责人
            binding.tvSchoolAreaLeader.setText(String.format("区域负责人：%s", StrUtils.isEmpty(schoolBean.getAreaStaffName())));

            //是否挂牌
            if ("1".equals(schoolBean.getIsListed())) {
                binding.tvSchoolListing.setVisibility(View.VISIBLE);
            } else {
                binding.tvSchoolListing.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(schoolBean.getBachelorType())) {
                binding.tvSchoolTag.setText(schoolBean.getBachelorType());
                binding.tvSchoolTag.setVisibility(View.VISIBLE);
            } else {
                binding.tvSchoolTag.setVisibility(View.GONE);
            }


            if (schoolBean.getEnrollList() != null && schoolBean.getEnrollList().size() > 0) {
                SchoolListBean.ErollListBean dto = schoolBean.getEnrollList().get(0);
                binding.tvSchoolUndergraduate.setText(String.valueOf(dto.getQuantity()));
                binding.tvSchoolTodayLabel.setText(String.format("%d年录取总数", dto.getYear()));

                if (schoolBean.getEnrollList().size() > 1) {
                    SchoolListBean.ErollListBean dt = schoolBean.getEnrollList().get(1);
                    binding.tvSchoolSpecialty.setText(String.valueOf(dt.getQuantity()));
                    binding.tvSchoolYestodayLabel.setText(String.format("%d年录取总数", dt.getYear()));
                } else {
                    binding.tvSchoolSpecialty.setText(String.valueOf(0));
                    binding.tvSchoolYestodayLabel.setText(String.format("%d年录取总数", (dto.getYear() - 1)));
                }

                int total = ParseUtils.parseInt(binding.tvSchoolUndergraduate.getText().toString())
                        + ParseUtils.parseInt(binding.tvSchoolSpecialty.getText().toString());

                binding.tvSchoolTotal.setText(String.valueOf(total));
            }

            StringBuilder sb = new StringBuilder();


            if (schoolBean.getStaffPropagandist() != null && schoolBean.getStaffPropagandist().size() > 0) {
                for (SchoolListBean.StaffPropagandistDTO dto : schoolBean.getStaffPropagandist()) {
                    if (sb.length() > 0) {
                        sb.append(" ");
                    }
                    sb.append(dto.getStaffName());

                    StaffBean bean = new StaffBean();
                    bean.setId(dto.getId());
                    bean.setStaffName(dto.getStaffName());

                    staffBeans.add(bean);
                }
            }
            binding.tvSchoolAreaPerson.setText(String.format("宣传员：%s", sb.toString()));
        }


        allAdapter = new StaffDisAdapter();
        binding.rvAll.setLayoutManager(new GridLayoutManager(context, 3));
        binding.rvAll.setAdapter(allAdapter);

        appointAdapter = new StaffAppointAdapter();

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.rvAppoint.setLayoutManager(layoutManager);
        binding.rvAppoint.setAdapter(appointAdapter);

        appointAdapter.setNewInstance(staffBeans);

        if (appointAdapter.getData().size() > 0) {
            binding.tvPersonName.setVisibility(View.GONE);
        } else {
            binding.tvPersonName.setVisibility(View.INVISIBLE);
        }
        //宣传员列表
        presenter.getStaffList();
    }

    @Override
    protected void addListener() {
        allAdapter.setOnItemClickListener((adapter, view, position) -> {
            StaffBean bean = allAdapter.getItem(position);
            bean.setSelect(!bean.isSelect());
            allAdapter.notifyItemChanged(position);

            if (bean.isSelect()) {
                appointAdapter.addData(bean);
            } else {
                appointAdapter.remove(bean);
            }
            if (appointAdapter.getData().size() > 0) {
                binding.tvPersonName.setVisibility(View.GONE);
            } else {
                binding.tvPersonName.setVisibility(View.INVISIBLE);
            }
        });
        appointAdapter.setOnItemClickListener((adapter, view, position) -> {
            StaffBean bean = appointAdapter.getItem(position);
            int pos = allAdapter.getData().indexOf(bean);
            if (pos >= 0) {
                bean.setSelect(false);
                allAdapter.notifyItemChanged(pos);
            }

            appointAdapter.removeAt(position);
        });
        binding.tvSubmit.setOnClickListener(v -> {
            if (appointAdapter.getData().size() == 0) {
                showToast(getString(R.string.please_select_staff));
                return;
            }
            if (schoolBean == null) {
                showToast(getString(R.string.please_check_school));
                return;
            }

            new SimDialog.Builder(context).title(getString(R.string.is_dis_school))
                    .submitText(getString(R.string.sure)).onBacklistener(new BaseDialog.BaseOnBack() {
                @Override
                public void onConfirm() {
                    List<Integer> list = new ArrayList<>();
                    for (StaffBean bean : appointAdapter.getData()) {
                        list.add(bean.getId());
                    }

                    presenter.distributionSchool(schoolBean.getId(), list);

                }
            }).show();
        });

        binding.tvRest.setOnClickListener(v -> {
            appointAdapter.setNewInstance(null);
            for (StaffBean bean : allAdapter.getData()) {
                bean.setSelect(false);
            }
            binding.tvPersonName.setVisibility(View.INVISIBLE);
            allAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void ontStaffList(List<StaffBean> o) {
        appointAdapter.getData();
        if (appointAdapter.getData().size() > 0) {
            for (StaffBean b : o) {
                for (StaffBean be : appointAdapter.getData()) {
                    if (b.getId() == be.getId()) {
                        be.setSelect(true);
                        o.set(o.indexOf(b), be);
                    }
                }
            }
        }
        allAdapter.setNewInstance(o);
    }

    @Override
    public void distributionSchool(String o) {
        showToast(o);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void distributionStudent(String o) {

    }
}
