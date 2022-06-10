package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.shenyuan.superapp.common.bean.BaseChooseBean;

/**
 * @author ch
 * @date 2021/2/18 11:38
 * desc
 */
public class CreaterBean extends BaseChooseBean {
    /**
     * creatorId : 2
     * creatorName : 李四
     */

    @JSONField(name = "id")
    private int id;
    @JSONField(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
