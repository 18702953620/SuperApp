package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.shenyuan.superapp.common.bean.BaseChooseBean;

/**
 * @author ch
 * @date 2021/2/18 11:55
 * desc
 */
public class SimpleStringBean extends BaseChooseBean {

    /**
     * value : 待维护
     * key : 0
     */

    @JSONField(name = "value")
    private String value;
    @JSONField(name = "key")
    private String key;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
