package com.shenyuan.superapp.common.bean;

import java.io.Serializable;

/**
 * @author ch
 * @date 2021/5/25 10:50
 * @
 */
public class AdvertBean implements Serializable {
    /**
     * createTime : 2021-05-19 17:21:11
     * updateTime : 2021-05-20 13:33:23
     * creatorId : 1
     * creatorName : 开发管理
     * updaterId : 1
     * updaterName : 开发管理
     * id : 37
     * adTitle : 测试
     * adType : 0
     * pictureUrl : http://123.138.78.55:9000/jysyfile/src=http___attach.bbs.miui.com_forum_201408_07_213601f2xz7usscm2z1mjh.jpg&refer=http___attach.bbs.miui-1621416070254.jpg
     * jumpUrl : 2131413
     * status : 0
     * endpoints : zhxyAppTch,zhxyAppStu
     * deleted : 0
     */

    private String createTime;
    private String updateTime;
    private int creatorId;
    private String creatorName;
    private int updaterId;
    private String updaterName;
    private int id;
    private String adTitle;
    private String adType;
    private String pictureUrl;
    private String jumpUrl;
    private int status;
    private String endpoints;
    private int deleted;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(int updaterId) {
        this.updaterId = updaterId;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(String endpoints) {
        this.endpoints = endpoints;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
