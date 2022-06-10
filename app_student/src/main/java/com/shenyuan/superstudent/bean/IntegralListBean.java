package com.shenyuan.superstudent.bean;

import java.io.Serializable;

/**
 * @author ch
 * @date 2021/5/24 15:22
 * @
 */
public class IntegralListBean implements Serializable {
    /**
     * id : 43
     * typeCode : login
     * userId : 7
     * userType : student
     * pointsChange : 1
     * inOutFlag : 0
     * reason : 登录
     * availableIds : 42
     * creatorId : 0
     * creatorName : system_user
     * createTime : 2021-05-24 14:52:29
     * inOutFlagName : 增加
     * changeValue : +1
     */

    private int id;
    private String typeCode;
    private int userId;
    private String userType;
    private int pointsChange;
    private int inOutFlag;
    private String reason;
    private String availableIds;
    private int creatorId;
    private String creatorName;
    private String createTime;
    private String inOutFlagName;
    private String changeValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getPointsChange() {
        return pointsChange;
    }

    public void setPointsChange(int pointsChange) {
        this.pointsChange = pointsChange;
    }

    public int getInOutFlag() {
        return inOutFlag;
    }

    public void setInOutFlag(int inOutFlag) {
        this.inOutFlag = inOutFlag;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAvailableIds() {
        return availableIds;
    }

    public void setAvailableIds(String availableIds) {
        this.availableIds = availableIds;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInOutFlagName() {
        return inOutFlagName;
    }

    public void setInOutFlagName(String inOutFlagName) {
        this.inOutFlagName = inOutFlagName;
    }

    public String getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(String changeValue) {
        this.changeValue = changeValue;
    }
}
