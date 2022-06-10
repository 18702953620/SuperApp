package com.shenyuan.superapp.api.view;

import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superapp.bean.ProvinceDataBean;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/5/27 14:10
 * @
 */
public interface BigDataView extends BaseView {
    void onSchoolTypePer(HashMap<String, String> o);

    void onProvinceList(List<ProvinceDataBean> o);
}
