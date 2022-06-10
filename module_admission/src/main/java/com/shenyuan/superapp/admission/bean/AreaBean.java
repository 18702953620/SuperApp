package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.shenyuan.superapp.common.bean.BaseChooseBean;

/**
 * @author ch
 * @date 2021/2/18 10:05
 * desc
 */
public class AreaBean extends BaseChooseBean {


    /**
     * id : 1
     * areaName : 西安一区
     */

    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "areaName")
    private String areaName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
