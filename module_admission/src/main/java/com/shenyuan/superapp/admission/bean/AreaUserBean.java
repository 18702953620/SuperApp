package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.shenyuan.superapp.common.bean.BaseChooseBean;

/**
 * @author ch
 * @date 2021/2/20 10:43
 * desc
 */
public class AreaUserBean extends BaseChooseBean {
    /**
     * id : null
     * areaName : null
     * areaId : null
     * staffId : 1
     * staffName : admin
     */

    @JSONField(name = "id")
    private int id;
    @JSONField(name = "areaName")
    private String areaName;
    @JSONField(name = "areaId")
    private int areaId;
    @JSONField(name = "staffId")
    private int staffId;
    @JSONField(name = "staffName")
    private String staffName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
