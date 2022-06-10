package com.shenyuan.superapp.common.api.view;

import com.shenyuan.superapp.common.bean.RegionBean;
import com.shenyuan.superapp.base.api.BaseView;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/18 10:55
 * desc
 */
public interface RegionView extends BaseView {
    /**
     * 省列表
     *
     * @param beans beans
     */
    void onProvinceList(List<RegionBean> beans);

    /**
     * 市列表
     *
     * @param beans beans
     */
    void onCityList(List<RegionBean> beans);

    /**
     * 区列表
     *
     * @param beans beans
     */
    void onAreaList(List<RegionBean> beans);
}
