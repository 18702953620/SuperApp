package com.shenyuan.superapp.admission.bean;

import com.shenyuan.superapp.common.bean.BaseChooseBean;

/**
 * @author ch
 * @date 2021/1/28 15:09
 * desc
 */
public class SearchBean extends BaseChooseBean {
    public SearchBean(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
