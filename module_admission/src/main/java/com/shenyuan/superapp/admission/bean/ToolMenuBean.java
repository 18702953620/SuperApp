package com.shenyuan.superapp.admission.bean;

/**
 * @author ch
 * @date 2021/3/9 17:02
 * desc
 */
public class ToolMenuBean {
    public ToolMenuBean(int res, String name) {
        this.res = res;
        this.name = name;
    }

    private int res;
    private String name;

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
