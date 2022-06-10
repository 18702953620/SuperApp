package com.shenyuan.superapp.admission.api.view;

import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.base.api.BaseView;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/5 9:58
 * desc
 */
public interface RecordView extends BaseView {
    /**
     * 意向程度
     *
     * @param o o
     */
    void onTargetDegree(List<SimpleBean> o);


    /**
     * 添加沟通记录
     *
     * @param o o
     */
    void onAddCommu(String o);
}
