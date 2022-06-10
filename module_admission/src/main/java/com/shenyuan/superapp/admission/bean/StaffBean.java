package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.shenyuan.superapp.common.bean.BaseChooseBean;

import java.io.Serializable;

/**
 * @author ch
 * @date 2021/2/20 17:09
 * desc
 */
public class StaffBean extends BaseChooseBean implements Serializable {


    /**
     * id : 1
     * staffName : admin
     * deptName :
     */

    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "staffName")
    private String staffName;
    @JSONField(name = "deptName")
    private String deptName;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
