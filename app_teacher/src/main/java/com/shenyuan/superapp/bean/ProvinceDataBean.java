package com.shenyuan.superapp.bean;

import java.io.Serializable;

/**
 * @author ch
 * @date 2021/5/27 14:42
 * @
 */
public class ProvinceDataBean implements Serializable {
    /**
     * province : 陕西省
     * sum : 37
     */

    private String province;
    private String sum;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
