package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ch
 * @date 2021/3/8 11:47
 * desc
 */
public class HistoryBean {
    /**
     * id : 3
     * schoolName : 高新
     */

    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "schoolName")
    private String schoolName;

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
}
