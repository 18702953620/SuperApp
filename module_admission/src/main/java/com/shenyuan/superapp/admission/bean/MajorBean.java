package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.shenyuan.superapp.common.bean.BaseChooseBean;

/**
 * @author ch
 * @date 2021/2/24 15:10
 * desc
 */
public class MajorBean extends BaseChooseBean {

    /**
     * majorId : 1
     * majorName : 电子竞技
     */

    @JSONField(name = "majorId")
    private Integer majorId;
    @JSONField(name = "majorName")
    private String majorName;

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
}
