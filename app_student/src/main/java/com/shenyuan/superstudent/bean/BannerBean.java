package com.shenyuan.superstudent.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ch
 * @date 2021/3/11 14:09
 * desc
 */
public class BannerBean {
    /**
     * createTime : 2021-03-09 13:29:46
     * updateTime : null
     * creatorId : null
     * creatorName : null
     * updaterId : null
     * updaterName : null
     * id : 10
     * bannerTitle : 测试新增banner
     * pictureUrl : http://192.168.30.139:9000/jysyfile/ceshi.xlsx?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210309%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210309T052945Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=665cf84286b4600b5f3de3f95bdddf9c65806415ad52ac7ed10132bf2db2e6ff
     * sort : 1
     * jumpUrl : http://localhost:8804/doc.html
     * status : 0
     * deleted : null
     * statusInfo : 启用
     */

    @JSONField(name = "createTime")
    private String createTime;
    @JSONField(name = "updateTime")
    private String updateTime;
    @JSONField(name = "creatorId")
    private String creatorId;
    @JSONField(name = "creatorName")
    private String creatorName;
    @JSONField(name = "updaterId")
    private String updaterId;
    @JSONField(name = "updaterName")
    private String updaterName;
    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "bannerTitle")
    private String bannerTitle;
    @JSONField(name = "pictureUrl")
    private String pictureUrl;
    @JSONField(name = "sort")
    private Integer sort;
    @JSONField(name = "jumpUrl")
    private String jumpUrl;
    @JSONField(name = "status")
    private Integer status;
    @JSONField(name = "deleted")
    private String deleted;
    @JSONField(name = "statusInfo")
    private String statusInfo;

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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

}
