package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ch
 * @date 2021/3/23 15:16
 * desc
 */
public class ClaimListBean {
    /**
     * id : 1
     * claimName : 报账1
     * travelPersonNum : 2
     * startTime : 2021-03-23
     * endTime : 2021-03-25
     * status : 1
     * creatorId : null
     * creatorName : admin
     * createTime : 2021-03-23 09:33:09
     * statusValue : 审核中
     */

    @JSONField(name = "id")
    private int id;
    @JSONField(name = "claimName")
    private String claimName;
    @JSONField(name = "travelPersonNum")
    private int travelPersonNum;
    @JSONField(name = "startTime")
    private String startTime;
    @JSONField(name = "endTime")
    private String endTime;
    @JSONField(name = "status")
    private int status;
    @JSONField(name = "creatorId")
    private Object creatorId;
    @JSONField(name = "creatorName")
    private String creatorName;
    @JSONField(name = "createTime")
    private String createTime;
    @JSONField(name = "statusValue")
    private String statusValue;
    @JSONField(name = "areaName")
    private String areaName;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClaimName() {
        return claimName;
    }

    public void setClaimName(String claimName) {
        this.claimName = claimName;
    }

    public int getTravelPersonNum() {
        return travelPersonNum;
    }

    public void setTravelPersonNum(int travelPersonNum) {
        this.travelPersonNum = travelPersonNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Object creatorId) {
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

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }
}
