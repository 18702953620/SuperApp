package com.shenyuan.superapp.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ch
 * @date 2021/3/30 15:34
 * desc
 */
public class MenuBean {

    /**
     * id : 9
     * sort : 1
     * serviceName : 选课管理
     * iconUrl : null
     * routerUrl : teach/lesson/choose
     * introduction : null
     * isMyService : null
     * guide : null
     * typeName : null
     * deptName : null
     * serviceTarget : null
     */

    @JSONField(name = "id")
    private int id;
    @JSONField(name = "sort")
    private int sort;
    @JSONField(name = "serviceName")
    private String serviceName;
    @JSONField(name = "iconUrl")
    private String iconUrl;
    @JSONField(name = "routerUrl")
    private String routerUrl;
    @JSONField(name = "introduction")
    private String introduction;
    @JSONField(name = "isMyService")
    private String isMyService;
    @JSONField(name = "guide")
    private String guide;
    @JSONField(name = "typeName")
    private String typeName;
    @JSONField(name = "deptName")
    private String deptName;
    @JSONField(name = "serviceTarget")
    private String serviceTarget;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getRouterUrl() {
        return routerUrl;
    }

    public void setRouterUrl(String routerUrl) {
        this.routerUrl = routerUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIsMyService() {
        return isMyService;
    }

    public void setIsMyService(String isMyService) {
        this.isMyService = isMyService;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getServiceTarget() {
        return serviceTarget;
    }

    public void setServiceTarget(String serviceTarget) {
        this.serviceTarget = serviceTarget;
    }
}
