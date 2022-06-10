package com.shenyuan.superapp.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.superapp.base.utils.PopUtils;
import com.shenyuan.superapp.common.R;
import com.shenyuan.superapp.common.api.presenter.RegionPresenter;
import com.shenyuan.superapp.common.api.view.RegionView;
import com.shenyuan.superapp.common.bean.RegionBean;
import com.shenyuan.superapp.common.databinding.ItemPickerAddressBinding;
import com.shenyuan.superapp.common.databinding.PopPickAddressBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/2 16:13
 * desc 地址选择
 */
public class PickerAddressView extends PickerTextView implements RegionView {
    private BaseQuickAdapter<RegionBean, BaseDataBindingHolder> addressAdapter;
    private RegionPresenter regionPresenter;
    private List<RegionBean> provinceList;
    private List<RegionBean> cityList;
    private List<RegionBean> areaList;

    private int provincePosition;
    private int cityPosition;
    private int areaPosition;

    private boolean showIcon;

    private PopPickAddressBinding popBinding;
    private PopupWindow popupWindow;
    /**
     * 0--省
     * 1--市
     * 2--区
     */
    private int currentType;

    private OnAddressCallBack callBack;

    public PickerAddressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PickerAddressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initPopWindow() {
        regionPresenter = new RegionPresenter(this);
        regionPresenter.getProvinceList(1);

        popBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.pop_pick_address, null, false);

        addressAdapter = new BaseQuickAdapter<RegionBean, BaseDataBindingHolder>(R.layout.item_picker_address) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder baseDataBindingHolder, RegionBean regionBean) {
                ItemPickerAddressBinding itemBinding = (ItemPickerAddressBinding) baseDataBindingHolder.getDataBinding();
                if (itemBinding == null) {
                    return;
                }
                itemBinding.tvName.setText(regionBean.getName());

                itemBinding.tvFirstLetter.setText(regionBean.getFirstLetter());

                if (baseDataBindingHolder.getAdapterPosition() != 0) {
                    String m = addressAdapter.getData().get(baseDataBindingHolder.getAdapterPosition() - 1).getFirstLetter();

                    if (m.equals(regionBean.getFirstLetter())) {
                        itemBinding.tvFirstLetter.setVisibility(INVISIBLE);
                    } else {
                        itemBinding.tvFirstLetter.setVisibility(VISIBLE);
                    }
                }

                if (showIcon) {
                    if (currentType == 0 && baseDataBindingHolder.getAdapterPosition() == provincePosition) {
                        itemBinding.ivState.setVisibility(VISIBLE);
                        itemBinding.tvName.setTextColor(getResources().getColor(R.color.color_0077ff));
                    } else if (currentType == 1 && baseDataBindingHolder.getAdapterPosition() == cityPosition) {
                        itemBinding.ivState.setVisibility(VISIBLE);
                        itemBinding.tvName.setTextColor(getResources().getColor(R.color.color_0077ff));
                    } else if (currentType == 2 && baseDataBindingHolder.getAdapterPosition() == areaPosition) {
                        itemBinding.ivState.setVisibility(VISIBLE);
                        itemBinding.tvName.setTextColor(getResources().getColor(R.color.color_0077ff));
                    } else {
                        itemBinding.ivState.setVisibility(GONE);
                        itemBinding.tvName.setTextColor(getResources().getColor(R.color.color_333333));
                    }
                } else {
                    itemBinding.ivState.setVisibility(GONE);
                    itemBinding.tvName.setTextColor(getResources().getColor(R.color.color_333333));
                }
            }
        };
        popBinding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        popBinding.rvList.setAdapter(addressAdapter);

        popBinding.llClose.setOnClickListener(v -> popupWindow.dismiss());

        addressAdapter.setOnItemClickListener((adapter, view, position) -> {
            RegionBean regionBean = addressAdapter.getItem(position);
            if (currentType == 0) {
                provincePosition = position;
                cityPosition = -1;
                regionPresenter.getCityList(regionBean.getId());

                popBinding.tvTip.setText("请选择城市");
                popBinding.tvCity.setText("请选择城市");
                popBinding.llText.setVisibility(VISIBLE);

                popBinding.tvProvince.setText(regionBean.getName());
                popBinding.tvProvince.setTextColor(getResources().getColor(R.color.color_0077ff));
                popBinding.ivAreaLine.setVisibility(GONE);
                popBinding.ivCircularCity.setBackgroundResource(R.mipmap.ic_pick_address_stroke);


                popBinding.tvArea.setVisibility(GONE);

                popBinding.ivCircularArea.setVisibility(GONE);

            } else if (currentType == 1) {
                cityPosition = position;
                areaPosition = -1;
                regionPresenter.getAreaList(regionBean.getId());
                popBinding.tvTip.setText("请选择区/县");
                popBinding.tvArea.setText("请选择区/县");

                popBinding.tvCity.setText(regionBean.getName());
                popBinding.tvCity.setTextColor(getResources().getColor(R.color.color_0077ff));
                popBinding.tvProvince.setTextColor(getResources().getColor(R.color.color_999999));

                popBinding.tvArea.setVisibility(VISIBLE);

                popBinding.ivAreaLine.setVisibility(VISIBLE);
                popBinding.ivCircularCity.setBackgroundResource(R.mipmap.ic_pick_address_fill);
                popBinding.ivCircularArea.setVisibility(VISIBLE);
            } else {
                areaPosition = position;

                popBinding.tvArea.setText(regionBean.getName());
                popBinding.ivCircularArea.setBackgroundResource(R.mipmap.ic_pick_address_fill);

                popBinding.tvCity.setTextColor(getResources().getColor(R.color.color_999999));
                popBinding.tvProvince.setTextColor(getResources().getColor(R.color.color_999999));
                popBinding.tvArea.setTextColor(getResources().getColor(R.color.color_0077ff));

                showIcon = true;
                addressAdapter.notifyDataSetChanged();

                String provinceName;
                String cityName;
                String areaName;

                if (provinceList != null && cityList != null && areaList != null) {
                    provinceName = provinceList.get(provincePosition).getName();
                    cityName = cityList.get(cityPosition).getName();
                    areaName = areaList.get(areaPosition).getName();

                    String address = provinceName + " " + cityName + " " + areaName;
                    binding.etText.setText(address);
                    if (callBack != null) {
                        callBack.onSelectAddress(provinceName, cityName, areaName);
                    }
                }

                //延迟关闭 防止刷新未完成
                postDelayed(() -> {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                }, 200);
            }
        });

        popBinding.tvProvince.setOnClickListener(v -> {
            showIcon = true;
            addressAdapter.setNewInstance(provinceList);
            currentType = 0;
            popBinding.tvProvince.setTextColor(getResources().getColor(R.color.color_0077ff));
            popBinding.tvCity.setTextColor(getResources().getColor(R.color.color_999999));
            popBinding.tvArea.setTextColor(getResources().getColor(R.color.color_999999));
        });
        popBinding.tvCity.setOnClickListener(v -> {
            showIcon = true;
            addressAdapter.setNewInstance(cityList);
            currentType = 1;
            popBinding.tvProvince.setTextColor(getResources().getColor(R.color.color_999999));
            popBinding.tvCity.setTextColor(getResources().getColor(R.color.color_0077ff));
            popBinding.tvArea.setTextColor(getResources().getColor(R.color.color_999999));
        });
        popBinding.tvArea.setOnClickListener(v -> {
            showIcon = true;
            addressAdapter.setNewInstance(areaList);
            currentType = 2;
            popBinding.tvProvince.setTextColor(getResources().getColor(R.color.color_999999));
            popBinding.tvCity.setTextColor(getResources().getColor(R.color.color_999999));
            popBinding.tvArea.setTextColor(getResources().getColor(R.color.color_0077ff));
        });

        popBinding.llText.setVisibility(GONE);

    }

    @Override
    protected void showPickDialog() {
        if (popupWindow == null) {
            popupWindow = PopUtils.getPopupWindow(getContext(), popBinding.getRoot(), true, true);
        }
        popupWindow.showAtLocation(binding.etText, Gravity.BOTTOM, 0, 0);
    }


    public void setCallBack(OnAddressCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onProvinceList(List<RegionBean> beans) {
        provinceList = beans;
        addressAdapter.setNewInstance(beans);
    }

    @Override
    public void onCityList(List<RegionBean> beans) {
        currentType = 1;
        cityList = beans;
        addressAdapter.setNewInstance(beans);
    }

    @Override
    public void onAreaList(List<RegionBean> beans) {
        currentType = 2;
        areaList = beans;
        addressAdapter.setNewInstance(beans);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onErrorCode(String code, String msg) {

    }

    public interface OnAddressCallBack {
        void onSelectAddress(String provinceName, String cityName, String areaName);
    }

}
