package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.shenyuan.superapp.common.bean.BaseChooseBean;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/16 11:26
 * desc
 */
public class PlanInfoBean {
    /**
     * id : 3
     * planName : 20210316003
     * beginTime : 2021-03-16 00:00:00
     * endTime : 2021-03-20 00:00:00
     * taskType : false
     * feeType : false
     * roundTripFee : 58
     * carRentFee : 58
     * intervalTripFee : 69
     * tollFee : 56
     * fuelFee : 56
     * roomFee : 555
     * roomNum : 2
     * roomDays : 3
     * totalRoomFee : null
     * subsidyFee : 55
     * subsidyDays : 556
     * subsidyPersons : 666
     * totalSubsidyFee : null
     * otherFee : 555
     * otherFeePurpose :
     * totalCostMoney : null
     * status : 1
     * zsxtPropgtPlanLineList : [{"id":3,"planId":3,"lineStart":"西安","lineEnd":"西安","beginTime":"2021-03-16 00:00:00","endTime":"2021-03-16 00:00:00","staffIds":"2","taskType":0,"creatorId":null,"creatorName":null,"createTime":null,"zsxtPropgtPlanTargetList":[{"id":3,"lineId":3,"targetSchoolId":16,"isMade":0,"giftNum":null,"fileNum":null,"propagationWay":0,"personalNeeds":null,"expectedEffect":null,"problemsSolutions":null,"creatorId":null,"creatorName":null,"createTime":null,"schoolName":"高新二中","schoolCode":null,"address":"高新路369号","contact":"HelloWorld","contactDuty":"主任","contactPhone":"18711112222"}]}]
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
    @JSONField(name = "feeType")
    private int feeType;
    @JSONField(name = "feeTypeValue")
    private String feeTypeValue;
    @JSONField(name = "roundTripFee")
    private double roundTripFee;
    @JSONField(name = "carRentFee")
    private double carRentFee;
    @JSONField(name = "intervalTripFee")
    private double intervalTripFee;
    @JSONField(name = "tollFee")
    private double tollFee;
    @JSONField(name = "fuelFee")
    private double fuelFee;
    @JSONField(name = "roomFee")
    private double roomFee;
    @JSONField(name = "roomNum")
    private int roomNum;
    @JSONField(name = "roomDays")
    private int roomDays;
    @JSONField(name = "totalRoomFee")
    private double totalRoomFee;
    @JSONField(name = "subsidyFee")
    private double subsidyFee;
    @JSONField(name = "subsidyDays")
    private int subsidyDays;
    @JSONField(name = "subsidyPersons")
    private int subsidyPersons;
    @JSONField(name = "totalSubsidyFee")
    private double totalSubsidyFee;
    @JSONField(name = "otherFee")
    private double otherFee;
    @JSONField(name = "otherFeePurpose")
    private String otherFeePurpose;
    @JSONField(name = "totalCostMoney")
    private double totalCostMoney;
    @JSONField(name = "status")
    private String status;
    @JSONField(name = "isShow")
    private int isShow;
    @JSONField(name = "zsxtPropgtPlanLineList")
    private List<PlanLine> zsxtPropgtPlanLineList;

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getFeeTypeValue() {
        return feeTypeValue;
    }

    public void setFeeTypeValue(String feeTypeValue) {
        this.feeTypeValue = feeTypeValue;
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

    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    public double getRoundTripFee() {
        return roundTripFee;
    }

    public void setRoundTripFee(double roundTripFee) {
        this.roundTripFee = roundTripFee;
    }

    public double getCarRentFee() {
        return carRentFee;
    }

    public void setCarRentFee(double carRentFee) {
        this.carRentFee = carRentFee;
    }

    public double getIntervalTripFee() {
        return intervalTripFee;
    }

    public void setIntervalTripFee(double intervalTripFee) {
        this.intervalTripFee = intervalTripFee;
    }

    public double getTollFee() {
        return tollFee;
    }

    public void setTollFee(double tollFee) {
        this.tollFee = tollFee;
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

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getRoomDays() {
        return roomDays;
    }

    public void setRoomDays(int roomDays) {
        this.roomDays = roomDays;
    }

    public double getTotalRoomFee() {
        return totalRoomFee;
    }

    public void setTotalRoomFee(double totalRoomFee) {
        this.totalRoomFee = totalRoomFee;
    }

    public double getSubsidyFee() {
        return subsidyFee;
    }

    public void setSubsidyFee(double subsidyFee) {
        this.subsidyFee = subsidyFee;
    }

    public int getSubsidyDays() {
        return subsidyDays;
    }

    public void setSubsidyDays(int subsidyDays) {
        this.subsidyDays = subsidyDays;
    }

    public int getSubsidyPersons() {
        return subsidyPersons;
    }

    public void setSubsidyPersons(int subsidyPersons) {
        this.subsidyPersons = subsidyPersons;
    }

    public double getTotalSubsidyFee() {
        return totalSubsidyFee;
    }

    public void setTotalSubsidyFee(double totalSubsidyFee) {
        this.totalSubsidyFee = totalSubsidyFee;
    }

    public double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(double otherFee) {
        this.otherFee = otherFee;
    }

    public String getOtherFeePurpose() {
        return otherFeePurpose;
    }

    public void setOtherFeePurpose(String otherFeePurpose) {
        this.otherFeePurpose = otherFeePurpose;
    }

    public double getTotalCostMoney() {
        return totalCostMoney;
    }

    public void setTotalCostMoney(double totalCostMoney) {
        this.totalCostMoney = totalCostMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PlanLine> getZsxtPropgtPlanLineList() {
        return zsxtPropgtPlanLineList;
    }

    public void setZsxtPropgtPlanLineList(List<PlanLine> zsxtPropgtPlanLineList) {
        this.zsxtPropgtPlanLineList = zsxtPropgtPlanLineList;
    }

    public static class PlanLine extends BaseChooseBean {
        /**
         * id : 3
         * planId : 3
         * lineStart : 西安
         * lineEnd : 西安
         * beginTime : 2021-03-16 00:00:00
         * endTime : 2021-03-16 00:00:00
         * staffIds : 2
         * taskType : 0
         * creatorId : null
         * creatorName : null
         * createTime : null
         * zsxtPropgtPlanTargetList : [{"id":3,"lineId":3,"targetSchoolId":16,"isMade":0,"giftNum":null,"fileNum":null,"propagationWay":0,"personalNeeds":null,"expectedEffect":null,"problemsSolutions":null,"creatorId":null,"creatorName":null,"createTime":null,"schoolName":"高新二中","schoolCode":null,"address":"高新路369号","contact":"HelloWorld","contactDuty":"主任","contactPhone":"18711112222"}]
         */

        @JSONField(name = "id")
        private int id;
        @JSONField(name = "planId")
        private int planId;
        @JSONField(name = "lineStart")
        private String lineStart;
        @JSONField(name = "lineEnd")
        private String lineEnd;
        @JSONField(name = "beginTime")
        private String beginTime;
        @JSONField(name = "endTime")
        private String endTime;
        @JSONField(name = "staffIds")
        private String staffIds;
        @JSONField(name = "staffNames")
        private String staffNames;
        @JSONField(name = "taskType")
        private int taskType;
        @JSONField(name = "taskTypeValue")
        private String taskTypeValue;
        @JSONField(name = "creatorId")
        private String creatorId;
        @JSONField(name = "creatorName")
        private String creatorName;
        @JSONField(name = "createTime")
        private String createTime;
        @JSONField(name = "zsxtPropgtPlanTargetList")
        private List<TargetSchool> zsxtPropgtPlanTargetList;
        @JSONField(name = "propagandistVoList")
        private List<StaffBean> propagandistVoList;

        public List<StaffBean> getPropagandistVoList() {
            return propagandistVoList;
        }

        public void setPropagandistVoList(List<StaffBean> propagandistVoList) {
            this.propagandistVoList = propagandistVoList;
        }

        public String getStaffNames() {
            return staffNames;
        }

        public void setStaffNames(String staffNames) {
            this.staffNames = staffNames;
        }

        public String getTaskTypeValue() {
            return taskTypeValue;
        }

        public void setTaskTypeValue(String taskTypeValue) {
            this.taskTypeValue = taskTypeValue;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPlanId() {
            return planId;
        }

        public void setPlanId(int planId) {
            this.planId = planId;
        }

        public String getLineStart() {
            return lineStart;
        }

        public void setLineStart(String lineStart) {
            this.lineStart = lineStart;
        }

        public String getLineEnd() {
            return lineEnd;
        }

        public void setLineEnd(String lineEnd) {
            this.lineEnd = lineEnd;
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

        public String getStaffIds() {
            return staffIds;
        }

        public void setStaffIds(String staffIds) {
            this.staffIds = staffIds;
        }

        public int getTaskType() {
            return taskType;
        }

        public void setTaskType(int taskType) {
            this.taskType = taskType;
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

        public List<TargetSchool> getZsxtPropgtPlanTargetList() {
            return zsxtPropgtPlanTargetList;
        }

        public void setZsxtPropgtPlanTargetList(List<TargetSchool> zsxtPropgtPlanTargetList) {
            this.zsxtPropgtPlanTargetList = zsxtPropgtPlanTargetList;
        }

        public static class TargetSchool {
            /**
             * id : 3
             * lineId : 3
             * targetSchoolId : 16
             * isMade : 0
             * giftNum : null
             * fileNum : null
             * propagationWay : 0
             * personalNeeds : null
             * expectedEffect : null
             * problemsSolutions : null
             * creatorId : null
             * creatorName : null
             * createTime : null
             * schoolName : 高新二中
             * schoolCode : null
             * address : 高新路369号
             * contact : HelloWorld
             * contactDuty : 主任
             * contactPhone : 18711112222
             */

            @JSONField(name = "id")
            private int id;
            @JSONField(name = "lineId")
            private int lineId;
            @JSONField(name = "targetSchoolId")
            private int targetSchoolId;
            @JSONField(name = "isMade")
            private int isMade;
            @JSONField(name = "isMadeValue")
            private String isMadeValue;
            @JSONField(name = "giftNum")
            private int giftNum;
            @JSONField(name = "fileNum")
            private int fileNum;
            @JSONField(name = "propagationWay")
            private int propagationWay;
            @JSONField(name = "propagationWayValue")
            private String propagationWayValue;
            @JSONField(name = "personalNeeds")
            private String personalNeeds;
            @JSONField(name = "expectedEffect")
            private String expectedEffect;
            @JSONField(name = "problemsSolutions")
            private String problemsSolutions;
            @JSONField(name = "creatorId")
            private String creatorId;
            @JSONField(name = "creatorName")
            private String creatorName;
            @JSONField(name = "createTime")
            private String createTime;
            @JSONField(name = "schoolName")
            private String schoolName;
            @JSONField(name = "schoolCode")
            private String schoolCode;
            @JSONField(name = "address")
            private String address;
            @JSONField(name = "contact")
            private String contact;
            @JSONField(name = "contactDuty")
            private String contactDuty;
            @JSONField(name = "contactPhone")
            private String contactPhone;

            public String getIsMadeValue() {
                return isMadeValue;
            }

            public void setIsMadeValue(String isMadeValue) {
                this.isMadeValue = isMadeValue;
            }

            public String getPropagationWayValue() {
                return propagationWayValue;
            }

            public void setPropagationWayValue(String propagationWayValue) {
                this.propagationWayValue = propagationWayValue;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLineId() {
                return lineId;
            }

            public void setLineId(int lineId) {
                this.lineId = lineId;
            }

            public int getTargetSchoolId() {
                return targetSchoolId;
            }

            public void setTargetSchoolId(int targetSchoolId) {
                this.targetSchoolId = targetSchoolId;
            }

            public int getIsMade() {
                return isMade;
            }

            public void setIsMade(int isMade) {
                this.isMade = isMade;
            }

            public int getGiftNum() {
                return giftNum;
            }

            public void setGiftNum(int giftNum) {
                this.giftNum = giftNum;
            }

            public int getFileNum() {
                return fileNum;
            }

            public void setFileNum(int fileNum) {
                this.fileNum = fileNum;
            }

            public int getPropagationWay() {
                return propagationWay;
            }

            public void setPropagationWay(int propagationWay) {
                this.propagationWay = propagationWay;
            }

            public String getPersonalNeeds() {
                return personalNeeds;
            }

            public void setPersonalNeeds(String personalNeeds) {
                this.personalNeeds = personalNeeds;
            }

            public String getExpectedEffect() {
                return expectedEffect;
            }

            public void setExpectedEffect(String expectedEffect) {
                this.expectedEffect = expectedEffect;
            }

            public String getProblemsSolutions() {
                return problemsSolutions;
            }

            public void setProblemsSolutions(String problemsSolutions) {
                this.problemsSolutions = problemsSolutions;
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

            public String getSchoolName() {
                return schoolName;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }

            public String getSchoolCode() {
                return schoolCode;
            }

            public void setSchoolCode(String schoolCode) {
                this.schoolCode = schoolCode;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getContactDuty() {
                return contactDuty;
            }

            public void setContactDuty(String contactDuty) {
                this.contactDuty = contactDuty;
            }

            public String getContactPhone() {
                return contactPhone;
            }

            public void setContactPhone(String contactPhone) {
                this.contactPhone = contactPhone;
            }
        }
    }
}
