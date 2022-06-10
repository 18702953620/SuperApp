package com.shenyuan.superstudent.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ch
 * @date 2021/3/25 11:01
 * desc
 */
public class MsgListBean {
    /**
     * id : 2
     * title : 测试公告111
     * content : <p>测试</p>
     * pictureUrl : http://192.168.30.139:9000/jysyfile/user_default_avatar-1616496339434.jpg
     * scope : null
     * scopeTarget : null
     * status : 1
     * publisherType : 产品部
     * publisherId : null
     * publisherName : null
     * publishTime : 2021-03-25 10:33:26
     * viewed : 1
     * noReadCount : null
     */

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
    @JSONField(name = "viewed")
    private int viewed;
    @JSONField(name = "noReadCount")
    private String noReadCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    public String getNoReadCount() {
        return noReadCount;
    }

    public void setNoReadCount(String noReadCount) {
        this.noReadCount = noReadCount;
    }
}
