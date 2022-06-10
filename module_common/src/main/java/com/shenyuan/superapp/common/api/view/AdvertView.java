package com.shenyuan.superapp.common.api.view;

import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superapp.common.bean.AdvertBean;

import java.util.List;

/**
 * @author ch
 * @date 2021/5/25 10:47
 * @
 */
public interface AdvertView extends BaseView {
    void onLoadingAdvert(List<AdvertBean> o);
}
