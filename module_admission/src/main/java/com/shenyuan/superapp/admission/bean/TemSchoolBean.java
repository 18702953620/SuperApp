package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ch
 * @date 2021/3/4 18:23
 * desc
 */
public class TemSchoolBean {
    /**
     * id : 24
     * schoolName : 湖北一中
     * areaId : null
     * areaName : null
     * staffId : null
     * areaStaffName : null
     */

    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "schoolName")
    private String schoolName;
    @JSONField(name = "areaId")
    private String areaId;
    @JSONField(name = "areaName")
    private String areaName;
    @JSONField(name = "staffId")
    private String staffId;
    @JSONField(name = "areaStaffName")
    private String areaStaffName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getAreaStaffName() {
        return areaStaffName;
    }

    public void setAreaStaffName(String areaStaffName) {
        this.areaStaffName = areaStaffName;
    }
}
