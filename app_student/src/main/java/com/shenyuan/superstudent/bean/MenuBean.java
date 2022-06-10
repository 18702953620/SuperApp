package com.shenyuan.superstudent.bean;

import java.io.Serializable;

/**
 * @author ch
 * @date 2021/3/30 15:34
 * desc
 */
public class MenuBean implements Serializable {

    /**
     * id : 25
     * sort : 1
     * serviceName : stu一卡通服务
     * iconUrl : http://192.168.0.110:9000/jysyfile/一卡通-1620895124504.png
     * stuIconUrl : http://192.168.0.110:9000/jysyfile/一卡通-1620895181417.png
     * routerUrl : 111htto
     * introduction : 简介
     * isMyService : 0
     * guide : <p>111</p>
     * typeName : 一卡通
     * deptName : 信息中心
     * serviceTarget : 在读
     * systemDomain :
     */

    private int id;
    private int sort;
    private String serviceName;
    private String iconUrl;
    private String stuIconUrl;
    private String routerUrl;
    private String introduction;
    private int isMyService;
    private String guide;
    private String typeName;
    private String deptName;
    private String serviceTarget;
    private String systemDomain;

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

    public String getStuIconUrl() {
        return stuIconUrl;
    }

    public void setStuIconUrl(String stuIconUrl) {
        this.stuIconUrl = stuIconUrl;
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

    public int getIsMyService() {
        return isMyService;
    }

    public void setIsMyService(int isMyService) {
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

    public String getSystemDomain() {
        return systemDomain;
    }

    public void setSystemDomain(String systemDomain) {
        this.systemDomain = systemDomain;
    }
}
