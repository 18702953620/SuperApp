package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/23 15:47
 * desc
 */
public class ClaimInfoBean {
    /**
     * createTime : 2021-03-23 09:33:09
     * updateTime : 2021-03-23 09:33:09
     * creatorId : 1
     * creatorName : admin
     * updaterId : 1
     * updaterName : admin
     * id : 1
     * claimName : 报账1
     * travelPersonNum : 2
     * startTime : 2021-03-23
     * endTime : 2021-03-25
     * trainFee : 1
     * roundTripFee : 2
     * intervalTripFee : 3
     * carRentFee : 4
     * carRentNum : 5
     * carRentDays : 6
     * tollFee : 7
     * parkFee : 8
     * fuelFee : 9
     * roomFee : 10
     * entertainmentFee : 11
     * subsidyFee : 12
     * privateCarFee : 13
     * privateCarNum : 14
     * privateCarDays : 15
     * mailFee : 16
     * otherFee : 17
     * otherFeePurpose : 18
     * totalFeeSubsidy : 2946
     * totalFeeNosubsidy : 2934
     * propgtPlanId : null
     * status : 1
     * statusValue : null
     * planIdList : [1]
     * planTotalCostMoney : 0
     * totalTrafficMoney : 150
     * totalHotelMoney : 10
     * totalEntertainmentMoney : 11
     * totalSubsidyMoney : null
     * totalOtherMoney : 33
     */

    @JSONField(name = "createTime")
    private String createTime;
    @JSONField(name = "updateTime")
    private String updateTime;
    @JSONField(name = "creatorId")
    private Integer creatorId;
    @JSONField(name = "creatorName")
    private String creatorName;
    @JSONField(name = "updaterId")
    private Integer updaterId;
    @JSONField(name = "updaterName")
    private String updaterName;
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "claimName")
    private String claimName;
    @JSONField(name = "travelPersonNum")
    private String travelPersonNum;
    @JSONField(name = "startTime")
    private String startTime;
    @JSONField(name = "endTime")
    private String endTime;
    @JSONField(name = "trainFee")
    private double trainFee;
    @JSONField(name = "roundTripFee")
    private double roundTripFee;
    @JSONField(name = "intervalTripFee")
    private double intervalTripFee;
    @JSONField(name = "carRentFee")
    private double carRentFee;
    @JSONField(name = "carRentNum")
    private int carRentNum;
    @JSONField(name = "carRentDays")
    private int carRentDays;
    @JSONField(name = "tollFee")
    private double tollFee;
    @JSONField(name = "parkFee")
    private double parkFee;
    @JSONField(name = "fuelFee")
    private double fuelFee;
    @JSONField(name = "roomFee")
    private double roomFee;
    @JSONField(name = "entertainmentFee")
    private double entertainmentFee;
    @JSONField(name = "subsidyFee")
    private double subsidyFee;
    @JSONField(name = "privateCarFee")
    private double privateCarFee;
    @JSONField(name = "privateCarNum")
    private int privateCarNum;
    @JSONField(name = "privateCarDays")
    private int privateCarDays;
    @JSONField(name = "mailFee")
    private double mailFee;
    @JSONField(name = "otherFee")
    private double otherFee;
    @JSONField(name = "otherFeePurpose")
    private double otherFeePurpose;
    @JSONField(name = "totalFeeSubsidy")
    private double totalFeeSubsidy;
    @JSONField(name = "totalFeeNosubsidy")
    private double totalFeeNosubsidy;
    @JSONField(name = "propgtPlanId")
    private String propgtPlanId;
    @JSONField(name = "status")
    private int status;
    @JSONField(name = "isShow")
    private int isShow;
    @JSONField(name = "statusValue")
    private String statusValue;
    @JSONField(name = "planIdList")
    private List<Integer> planIdList;
    @JSONField(name = "planTotalCostMoney")
    private double planTotalCostMoney;
    @JSONField(name = "totalTrafficMoney")
    private double totalTrafficMoney;
    @JSONField(name = "totalHotelMoney")
    private double totalHotelMoney;
    @JSONField(name = "totalEntertainmentMoney")
    private double totalEntertainmentMoney;
    @JSONField(name = "totalSubsidyMoney")
    private double totalSubsidyMoney;
    @JSONField(name = "totalOtherMoney")
    private double totalOtherMoney;
    @JSONField(name = "totalCarRentFee")
    private double totalCarRentFee;
    @JSONField(name = "totalPrivateCarFee")
    private double totalPrivateCarFee;

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public double getTotalCarRentFee() {
        return totalCarRentFee;
    }

    public void setTotalCarRentFee(double totalCarRentFee) {
        this.totalCarRentFee = totalCarRentFee;
    }

    public double getTotalPrivateCarFee() {
        return totalPrivateCarFee;
    }

    public void setTotalPrivateCarFee(double totalPrivateCarFee) {
        this.totalPrivateCarFee = totalPrivateCarFee;
    }

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

    public double getCreatorId() {
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

    public double getUpdaterId() {
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

    public double getId() {
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

    public String getTravelPersonNum() {
        return travelPersonNum;
    }

    public void setTravelPersonNum(String travelPersonNum) {
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

    public double getTrainFee() {
        return trainFee;
    }

    public void setTrainFee(double trainFee) {
        this.trainFee = trainFee;
    }

    public double getRoundTripFee() {
        return roundTripFee;
    }

    public void setRoundTripFee(double roundTripFee) {
        this.roundTripFee = roundTripFee;
    }

    public double getIntervalTripFee() {
        return intervalTripFee;
    }

    public void setIntervalTripFee(double intervalTripFee) {
        this.intervalTripFee = intervalTripFee;
    }

    public double getCarRentFee() {
        return carRentFee;
    }

    public void setCarRentFee(double carRentFee) {
        this.carRentFee = carRentFee;
    }

    public int getCarRentNum() {
        return carRentNum;
    }

    public void setCarRentNum(int carRentNum) {
        this.carRentNum = carRentNum;
    }

    public int getCarRentDays() {
        return carRentDays;
    }

    public void setCarRentDays(int carRentDays) {
        this.carRentDays = carRentDays;
    }

    public double getTollFee() {
        return tollFee;
    }

    public void setTollFee(double tollFee) {
        this.tollFee = tollFee;
    }

    public double getParkFee() {
        return parkFee;
    }

    public void setParkFee(double parkFee) {
        this.parkFee = parkFee;
    }

    public double getFuelFee() {
        return fuelFee;
    }

    public void setFuelFee(double fuelFee) {
        this.fuelFee = fuelFee;
    }

    public double getRoomFee() {
        return roomFee;
    }

    public void setRoomFee(double roomFee) {
        this.roomFee = roomFee;
    }

    public double getEntertainmentFee() {
        return entertainmentFee;
    }

    public void setEntertainmentFee(double entertainmentFee) {
        this.entertainmentFee = entertainmentFee;
    }

    public double getSubsidyFee() {
        return subsidyFee;
    }

    public void setSubsidyFee(double subsidyFee) {
        this.subsidyFee = subsidyFee;
    }

    public double getPrivateCarFee() {
        return privateCarFee;
    }

    public void setPrivateCarFee(double privateCarFee) {
        this.privateCarFee = privateCarFee;
    }

    public int getPrivateCarNum() {
        return privateCarNum;
    }

    public void setPrivateCarNum(int privateCarNum) {
        this.privateCarNum = privateCarNum;
    }

    public int getPrivateCarDays() {
        return privateCarDays;
    }

    public void setPrivateCarDays(int privateCarDays) {
        this.privateCarDays = privateCarDays;
    }

    public double getMailFee() {
        return mailFee;
    }

    public void setMailFee(double mailFee) {
        this.mailFee = mailFee;
    }

    public double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(double otherFee) {
        this.otherFee = otherFee;
    }

    public double getOtherFeePurpose() {
        return otherFeePurpose;
    }

    public void setOtherFeePurpose(double otherFeePurpose) {
        this.otherFeePurpose = otherFeePurpose;
    }

    public double getTotalFeeSubsidy() {
        return totalFeeSubsidy;
    }

    public void setTotalFeeSubsidy(double totalFeeSubsidy) {
        this.totalFeeSubsidy = totalFeeSubsidy;
    }

    public double getTotalFeeNosubsidy() {
        return totalFeeNosubsidy;
    }

    public void setTotalFeeNosubsidy(double totalFeeNosubsidy) {
        this.totalFeeNosubsidy = totalFeeNosubsidy;
    }

    public String getPropgtPlanId() {
        return propgtPlanId;
    }

    public void setPropgtPlanId(String propgtPlanId) {
        this.propgtPlanId = propgtPlanId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public List<Integer> getPlanIdList() {
        return planIdList;
    }

    public void setPlanIdList(List<Integer> planIdList) {
        this.planIdList = planIdList;
    }

    public double getPlanTotalCostMoney() {
        return planTotalCostMoney;
    }

    public void setPlanTotalCostMoney(double planTotalCostMoney) {
        this.planTotalCostMoney = planTotalCostMoney;
    }

    public double getTotalTrafficMoney() {
        return totalTrafficMoney;
    }

    public void setTotalTrafficMoney(double totalTrafficMoney) {
        this.totalTrafficMoney = totalTrafficMoney;
    }

    public double getTotalHotelMoney() {
        return totalHotelMoney;
    }

    public void setTotalHotelMoney(double totalHotelMoney) {
        this.totalHotelMoney = totalHotelMoney;
    }

    public double getTotalEntertainmentMoney() {
        return totalEntertainmentMoney;
    }

    public void setTotalEntertainmentMoney(double totalEntertainmentMoney) {
        this.totalEntertainmentMoney = totalEntertainmentMoney;
    }

    public double getTotalSubsidyMoney() {
        return totalSubsidyMoney;
    }

    public void setTotalSubsidyMoney(double totalSubsidyMoney) {
        this.totalSubsidyMoney = totalSubsidyMoney;
    }

    public double getTotalOtherMoney() {
        return totalOtherMoney;
    }

    public void setTotalOtherMoney(double totalOtherMoney) {
        this.totalOtherMoney = totalOtherMoney;
    }
}
