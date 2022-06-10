package com.shenyuan.superapp.common.scan;

/**
 * @author ch
 * @date 2021/1/4-11:47
 * desc
 */
public class ScanDataBean {
    private String clientId;
    private String action;
    private String data;

    public ScanDataBean(String clientId, String action, String data) {
        this.clientId = clientId;
        this.action = action;
        this.data = data;
    }

    public ScanDataBean(String data) {
        this.data = data;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
