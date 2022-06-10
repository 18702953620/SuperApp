package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.shenyuan.superapp.common.bean.BaseChooseBean;

/**
 * @author ch
 * @date 2021/3/13 15:34
 * desc
 */
public class PlanListBean extends BaseChooseBean {
    /**
     * id : 1
     * planName : 测试招生宣传方案
     * beginTime : 2020-02-04 00:00:00
     * endTime : 2020-02-04 00:00:00
     * taskType : 0
     * status : 0
     * createTime : 2021-02-04 11:43:36
     * taskTypeValue : 招生宣传
     * statusValue : 草稿保存
     */

    @JSONField(name = "id")
    private int id;
    @JSONField(name = "planName")
    private String planName;
    @JSONField(name = "beginTime")
    private String beginTime;
    @JSONField(name = "endTime")
    private String endTime;
    @JSONField(name = "taskType")
    private int taskType;
    @JSONField(name = "status")
    private int status;
    @JSONField(name = "createTime")
    private String createTime;
    @JSONField(name = "taskTypeValue")
    private String taskTypeValue;
    @JSONField(name = "statusValue")
    private String statusValue;
    @JSONField(name = "creatorName")
    private String creatorName;
    @JSONField(name = "totalCostMoney")
    private double totalCostMoney;

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public double getTotalCostMoney() {
        return totalCostMoney;
    }

    public void setTotalCostMoney(double totalCostMoney) {
        this.totalCostMoney = totalCostMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTaskTypeValue() {
        return taskTypeValue;
    }

    public void setTaskTypeValue(String taskTypeValue) {
        this.taskTypeValue = taskTypeValue;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }
}
