package com.shenyuan.superapp.admission.window.school;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.ItemSchoolLevelBinding;
import com.shenyuan.admission.databinding.PopSchoolLocationBinding;
import com.shenyuan.superapp.common.bean.RegionBean;
import com.shenyuan.superapp.common.api.presenter.RegionPresenter;
import com.shenyuan.superapp.common.api.view.RegionView;
import com.shenyuan.superapp.common.popup.BasePopupWindow;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/5 16:29
 * desc
 */
public class LocationWindow extends BasePopupWindow<PopSchoolLocationBinding, RegionPresenter> implements RegionView {
    /**
     * 省
     */
    private BaseQuickAdapter<RegionBean, BaseDataBindingHolder> provinceAdapter;
    /**
     * 市
     */
    private BaseQuickAdapter<RegionBean, BaseDataBindingHolder> cityAdapter;
    /**
     * 区
     */
    private BaseQuickAdapter<RegionBean, BaseDataBindingHolder> areaAdapter;

    private int provincePosition = -1;
    private int cityPosition = -1;
    private int areaPosition = -1;

    private OnLocationBack onBack;


    public LocationWindow(Context context) {
        super(context, true, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_school_location;
    }

    @Override
    protected void initView() {
        presenter.getProvinceList(1);
        provinceAdapter = new BaseQuickAdapter<RegionBean, BaseDataBindingHolder>(R.layout.item_school_level) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, RegionBean s) {
                ItemSchoolLevelBinding schoolLevelBinding = (ItemSchoolLevelBinding) baseDataBindingHolder.getDataBinding();
                if (schoolLevelBinding == null) {
                    return;
                }
                schoolLevelBinding.tvSchoolLevel.setText(s.getName());

                if (provincePosition == baseDataBindingHolder.getAdapterPosition()) {
                    schoolLevelBinding.tvSchoolLevel.setTextColor(getValuesColor(R.color.color_0077ff));
                    schoolLevelBinding.tvSchoolLevel.setBackgroundColor(getValuesColor(R.color.color_f5f5f5));
                } else {
                    schoolLevelBinding.tvSchoolLevel.setTextColor(getValuesColor(R.color.color_333333));
                    schoolLevelBinding.tvSchoolLevel.setBackgroundColor(getValuesColor(R.color.color_ffffff));
                }
            }
        };
        binding.rvSchoolsProvince.setLayoutManager(new LinearLayoutManager(context));
        binding.rvSchoolsProvince.setAdapter(provinceAdapter);

        cityAdapter = new BaseQuickAdapter<RegionBean, BaseDataBindingHolder>(R.layout.item_school_level) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, RegionBean s) {
                ItemSchoolLevelBinding schoolLevelBinding = (ItemSchoolLevelBinding) baseDataBindingHolder.getDataBinding();
                if (schoolLevelBinding == null) {
                    return;
                }
                schoolLevelBinding.tvSchoolLevel.setText(s.getName());

                if (cityPosition == baseDataBindingHolder.getAdapterPosition()) {
                    schoolLevelBinding.tvSchoolLevel.setTextColor(getValuesColor(R.color.color_0077ff));
                    schoolLevelBinding.tvSchoolLevel.setBackgroundColor(getValuesColor(R.color.color_eaeaea));
                } else {
                    schoolLevelBinding.tvSchoolLevel.setTextColor(getValuesColor(R.color.color_333333));
                    schoolLevelBinding.tvSchoolLevel.setBackgroundColor(getValuesColor(R.color.color_f5f5f5));
                }

            }
        };
        binding.rvSchoolsCity.setLayoutManager(new LinearLayoutManager(context));
        binding.rvSchoolsCity.setAdapter(cityAdapter);


        areaAdapter = new BaseQuickAdapter<RegionBean, BaseDataBindingHolder>(R.layout.item_school_level) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, RegionBean s) {
                ItemSchoolLevelBinding schoolLevelBinding = (ItemSchoolLevelBinding) baseDataBindingHolder.getDataBinding();
                if (schoolLevelBinding == null) {
                    return;
                }
                schoolLevelBinding.tvSchoolLevel.setText(s.getName());

                if (areaPosition == baseDataBindingHolder.getAdapterPosition()) {
                    schoolLevelBinding.tvSchoolLevel.setTextColor(getValuesColor(R.color.color_0077ff));
                } else {
                    schoolLevelBinding.tvSchoolLevel.setTextColor(getValuesColor(R.color.color_333333));
                }

            }
        };

        binding.rvSchoolsArea.setLayoutManager(new LinearLayoutManager(context));
        binding.rvSchoolsArea.setAdapter(areaAdapter);
    }

    @Override
    protected void addListener() {
        provinceAdapter.setOnItemClickListener((adapter, view, position) -> {
            provincePosition = position;
            RegionBean bean = provinceAdapter.getItem(position);
            if (bean != null && !"全国".equals(bean.getName())) {
                //请求市列表
                presenter.getCityList(bean.getId());
            } else {
                dismiss();
                if (onBack != null) {
                    String province = provinceAdapter.getData().get(provincePosition).getName();
                    onBack.onBack(province, "", "");
                }
            }

            provinceAdapter.notifyDataSetChanged();
            //清空市 区
            cityAdapter.setNewInstance(null);
            areaAdapter.setNewInstance(null);
            cityPosition = -1;
            areaPosition = -1;
        });


        cityAdapter.setOnItemClickListener((adapter, view, position) -> {
            cityPosition = position;
            RegionBean bean = cityAdapter.getItem(position);
            if (bean != null && !"全省".equals(bean.getName())) {
                presenter.getAreaList(bean.getId());
            } else {
                dismiss();
                String province = provinceAdapter.getData().get(provincePosition).getName();
                String city = cityAdapter.getData().get(cityPosition).getName();
                if (onBack != null) {
                    onBack.onBack(province, city, "");
                }
            }
            cityAdapter.notifyDataSetChanged();
            //清空区
            areaAdapter.setNewInstance(null);
            areaPosition = -1;
        });

        areaAdapter.setOnItemClickListener((adapter, view, position) -> {
            areaPosition = position;
            areaAdapter.notifyDataSetChanged();

            dismiss();


            if (onBack != null) {
                String province = provinceAdapter.getData().get(provincePosition).getName();
                String city = cityAdapter.getData().get(cityPosition).getName();
                String region = areaAdapter.getData().get(areaPosition).getName();
                onBack.onBack(province, city, region);
            }
        });
    }

    @Override
    protected RegionPresenter createPresenter() {
        return new RegionPresenter(this);
    }

    public void showFullAsDropDown(Activity activity, View anchor, OnLocationBack onBack) {
        super.showFullAsDropDown(activity, anchor);
        this.onBack = onBack;
    }

    @Override
    public void onProvinceList(List<RegionBean> beans) {
        if (beans != null) {
            RegionBean bean = new RegionBean();
            bean.setName("全国");
            beans.add(0, bean);
        }
        provinceAdapter.setNewInstance(beans);
    }

    @Override
    public void onCityList(List<RegionBean> beans) {
        if (beans != null) {
            RegionBean bean = new RegionBean();
            bean.setName("全省");
            beans.add(0, bean);
        }
        cityAdapter.setNewInstance(beans);
    }

    @Override
    public void onAreaList(List<RegionBean> beans) {
        if (beans != null) {
            RegionBean bean = new RegionBean();
            bean.setName("全市");
            beans.add(0, bean);
        }
        areaAdapter.setNewInstance(beans);
    }

    /**
     * 回调
     * 业务不同 回调的参数也不同
     */
    public interface OnLocationBack {
        /**
         * 回调
         */
        void onBack(String province, String city, String region);
    }
}
