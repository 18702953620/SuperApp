package com.shenyuan.superapp.common.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ch
 * @date 2021/3/25 11:13
 * desc
 */
public class MsgInfoBean {
    /**
     * createTime : 2021-03-25 10:33:26
     * updateTime : 2021-03-25 10:33:26
     * creatorId : null
     * creatorName : null
     * updaterId : null
     * updaterName : null
     * id : 2
     * title : 测试公告111
     * content : <p>测试</p>
     * pictureUrl : http://192.168.30.139:9000/jysyfile/user_default_avatar-1616496339434.jpg
     * scope : all
     * scopeTarget : null
     * status : 1
     * publisherType : 产品部
     * publisherId : null
     * publisherName : null
     * publishTime : 2021-03-25 10:33:26
     * deleted : 0
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
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "content")
    private String content;
    @JSONField(name = "pictureUrl")
    private String pictureUrl;
    @JSONField(name = "scope")
    private String scope;
    @JSONField(name = "scopeTarget")
    private String scopeTarget;
    @JSONField(name = "status")
    private Integer status;
    @JSONField(name = "publisherType")
    private String publisherType;
    @JSONField(name = "publisherId")
    private String publisherId;
    @JSONField(name = "publisherName")
    private String publisherName;
    @JSONField(name = "publishTime")
    private String publishTime;
    @JSONField(name = "deleted")
    private Integer deleted;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScopeTarget() {
        return scopeTarget;
    }

    public void setScopeTarget(String scopeTarget) {
        this.scopeTarget = scopeTarget;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPublisherType() {
        return publisherType;
    }

    public void setPublisherType(String publisherType) {
        this.publisherType = publisherType;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
