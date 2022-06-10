package com.shenyuan.superapp.common.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author ch
 * @date 2021/3/12 15:00
 * desc
 */
public class NewsBean implements Serializable {
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "newsTitle")
    private String newsTitle;
    @JSONField(name = "newsTypeId")
    private int newsTypeId;
    @JSONField(name = "isSubscribed")
    private int isSubscribed;
    @JSONField(name = "isSubscribedInfo")
    private String isSubscribedInfo;
    @JSONField(name = "pictureUrl")
    private String pictureUrl;
    @JSONField(name = "signature")
    private String signature;
    @JSONField(name = "content")
    private String content;
    @JSONField(name = "status")
    private int status;
    @JSONField(name = "statusInfo")
    private String statusInfo;
    @JSONField(name = "publishTime")
    private String publishTime;
    @JSONField(name = "publisherId")
    private String publisherId;
    @JSONField(name = "publisherName")
    private String publisherName;
    @JSONField(name = "deleted")
    private Boolean deleted;
    @JSONField(name = "viewCount")
    private int viewCount;
    @JSONField(name = "creatorId")
    private String creatorId;
    @JSONField(name = "creatorName")
    private String creatorName;
    @JSONField(name = "createTime")
    private String createTime;
    @JSONField(name = "updaterId")
    private String updaterId;
    @JSONField(name = "updaterName")
    private String updaterName;
    @JSONField(name = "updateTime")
    private String updateTime;
    @JSONField(name = "newsTypeName")
    private String newsTypeName;
    @JSONField(name = "userId")
    private String userId;
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "summary")
    private String summary;
    @JSONField(name = "readStatus")
    private int readStatus;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public int getNewsTypeId() {
        return newsTypeId;
    }

    public void setNewsTypeId(int newsTypeId) {
        this.newsTypeId = newsTypeId;
    }

    public int getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(int isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public String getIsSubscribedInfo() {
        return isSubscribedInfo;
    }

    public void setIsSubscribedInfo(String isSubscribedInfo) {
        this.isSubscribedInfo = isSubscribedInfo;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getNewsTypeName() {
        return newsTypeName;
    }

    public void setNewsTypeName(String newsTypeName) {
        this.newsTypeName = newsTypeName;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
