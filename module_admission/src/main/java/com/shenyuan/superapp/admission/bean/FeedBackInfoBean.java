package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/17 16:51
 * desc
 */
public class FeedBackInfoBean {
    /**
     * createTime : 2021-03-17 15:31:20
     * updateTime : 2021-03-17 15:31:20
     * creatorId : 1
     * creatorName : admin
     * updaterId : 1
     * updaterName : admin
     * id : 1
     * fbName : 12
     * startTime : 2021-03-17
     * endTime : 2021-03-17
     * deleted : false
     * zsxtPropgtFeedbackSchoolViewVo : [{"id":1,"fbId":1,"targetSchoolId":21,"startTime":"2021-03-17 00:00:00","endTime":"2021-03-17 00:00:00","staffIds":"1,2","collectStudentNum":21,"classNum":13,"studentNum":14,"onlineStudentNum":15,"activitiesIntention":1,"activitiesIntentionValue":"有意向","distributeRegulationNum":18,"isGleeDelivered":1,"isGleeDeliveredValue":"已送达","distributeGleeNum":null,"listedIntention":0,"listedIntentionValue":"无意愿","distributeActivityNum":19,"visitIntention":0,"visitIntentionValue":"无意向","distributeGiftNum":20,"specificMeasure":"22","csEnrollmentNum":16,"hqEnrollmentNum":17,"creatorId":null,"creatorName":null,"createTime":null,"schoolName":"高新八中","contact":"HelloWorld","contactDuty":"主任","contactPhone":"17888888888","staffNames":"admin测试系统管理员"}]
     */

    @JSONField(name = "createTime")
    private String createTime;
    @JSONField(name = "updateTime")
    private String updateTime;
    @JSONField(name = "creatorId")
    private int creatorId;
    @JSONField(name = "creatorName")
    private String creatorName;
    @JSONField(name = "updaterId")
    private int updaterId;
    @JSONField(name = "updaterName")
    private String updaterName;
    @JSONField(name = "id")
    private int id;
    @JSONField(name = "fbName")
    private String fbName;
    @JSONField(name = "startTime")
    private String startTime;
    @JSONField(name = "endTime")
    private String endTime;
    @JSONField(name = "zsxtPropgtFeedbackSchoolViewVo")
    private List<SchoolBean> zsxtPropgtFeedbackSchoolViewVo;

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

    public String getFbName() {
        return fbName;
    }

    public void setFbName(String fbName) {
        this.fbName = fbName;
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


    public List<SchoolBean> getZsxtPropgtFeedbackSchoolViewVo() {
        return zsxtPropgtFeedbackSchoolViewVo;
    }

    public void setZsxtPropgtFeedbackSchoolViewVo(List<SchoolBean> zsxtPropgtFeedbackSchoolViewVo) {
        this.zsxtPropgtFeedbackSchoolViewVo = zsxtPropgtFeedbackSchoolViewVo;
    }

    public static class SchoolBean {
        /**
         * id : 1
         * fbId : 1
         * targetSchoolId : 21
         * startTime : 2021-03-17 00:00:00
         * endTime : 2021-03-17 00:00:00
         * staffIds : 1,2
         * collectStudentNum : 21
         * classNum : 13
         * studentNum : 14
         * onlineStudentNum : 15
         * activitiesIntention : 1
         * activitiesIntentionValue : 有意向
         * distributeRegulationNum : 18
         * isGleeDelivered : 1
         * isGleeDeliveredValue : 已送达
         * distributeGleeNum : null
         * listedIntention : 0
         * listedIntentionValue : 无意愿
         * distributeActivityNum : 19
         * visitIntention : 0
         * visitIntentionValue : 无意向
         * distributeGiftNum : 20
         * specificMeasure : 22
         * csEnrollmentNum : 16
         * hqEnrollmentNum : 17
         * creatorId : null
         * creatorName : null
         * createTime : null
         * schoolName : 高新八中
         * contact : HelloWorld
         * contactDuty : 主任
         * contactPhone : 17888888888
         * staffNames : admin测试系统管理员
         */

        @JSONField(name = "id")
        private int id;
        @JSONField(name = "fbId")
        private int fbId;
        @JSONField(name = "targetSchoolId")
        private int targetSchoolId;
        @JSONField(name = "startTime")
        private String startTime;
        @JSONField(name = "endTime")
        private String endTime;
        @JSONField(name = "staffIds")
        private String staffIds;
        @JSONField(name = "collectStudentNum")
        private int collectStudentNum;
        @JSONField(name = "classNum")
        private int classNum;
        @JSONField(name = "studentNum")
        private int studentNum;
        @JSONField(name = "onlineStudentNum")
        private int onlineStudentNum;
        @JSONField(name = "activitiesIntention")
        private int activitiesIntention;
        @JSONField(name = "activitiesIntentionValue")
        private String activitiesIntentionValue;
        @JSONField(name = "distributeRegulationNum")
        private int distributeRegulationNum;
        @JSONField(name = "isGleeDelivered")
        private int isGleeDelivered;
        @JSONField(name = "isGleeDeliveredValue")
        private String isGleeDeliveredValue;
        @JSONField(name = "distributeGleeNum")
        private int distributeGleeNum;
        @JSONField(name = "listedIntention")
        private int listedIntention;
        @JSONField(name = "listedIntentionValue")
        private String listedIntentionValue;
        @JSONField(name = "distributeActivityNum")
        private int distributeActivityNum;
        @JSONField(name = "visitIntention")
        private int visitIntention;
        @JSONField(name = "visitIntentionValue")
        private String visitIntentionValue;
        @JSONField(name = "distributeGiftNum")
        private int distributeGiftNum;
        @JSONField(name = "specificMeasure")
        private String specificMeasure;
        @JSONField(name = "csEnrollmentNum")
        private int csEnrollmentNum;
        @JSONField(name = "hqEnrollmentNum")
        private int hqEnrollmentNum;
        @JSONField(name = "creatorId")
        private String creatorId;
        @JSONField(name = "creatorName")
        private String creatorName;
        @JSONField(name = "createTime")
        private String createTime;
        @JSONField(name = "schoolName")
        private String schoolName;
        @JSONField(name = "contact")
        private String contact;
        @JSONField(name = "contactDuty")
        private String contactDuty;
        @JSONField(name = "contactPhone")
        private String contactPhone;
        @JSONField(name = "staffNames")
        private String staffNames;

        @JSONField(name = "propagandistVoList")
        private List<StaffBean> propagandistVoList;

        public List<StaffBean> getPropagandistVoList() {
            return propagandistVoList;
        }

        public void setPropagandistVoList(List<StaffBean> propagandistVoList) {
            this.propagandistVoList = propagandistVoList;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFbId() {
            return fbId;
        }

        public void setFbId(int fbId) {
            this.fbId = fbId;
        }

        public int getTargetSchoolId() {
            return targetSchoolId;
        }

        public void setTargetSchoolId(int targetSchoolId) {
            this.targetSchoolId = targetSchoolId;
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

        public String getStaffIds() {
            return staffIds;
        }

        public void setStaffIds(String staffIds) {
            this.staffIds = staffIds;
        }

        public int getCollectStudentNum() {
            return collectStudentNum;
        }

        public void setCollectStudentNum(int collectStudentNum) {
            this.collectStudentNum = collectStudentNum;
        }

        public int getClassNum() {
            return classNum;
        }

        public void setClassNum(int classNum) {
            this.classNum = classNum;
        }

        public int getStudentNum() {
            return studentNum;
        }

        public void setStudentNum(int studentNum) {
            this.studentNum = studentNum;
        }

        public int getOnlineStudentNum() {
            return onlineStudentNum;
        }

        public void setOnlineStudentNum(int onlineStudentNum) {
            this.onlineStudentNum = onlineStudentNum;
        }

        public int getActivitiesIntention() {
            return activitiesIntention;
        }

        public void setActivitiesIntention(int activitiesIntention) {
            this.activitiesIntention = activitiesIntention;
        }

        public String getActivitiesIntentionValue() {
            return activitiesIntentionValue;
        }

        public void setActivitiesIntentionValue(String activitiesIntentionValue) {
            this.activitiesIntentionValue = activitiesIntentionValue;
        }

        public int getDistributeRegulationNum() {
            return distributeRegulationNum;
        }

        public void setDistributeRegulationNum(int distributeRegulationNum) {
            this.distributeRegulationNum = distributeRegulationNum;
        }

        public int getIsGleeDelivered() {
            return isGleeDelivered;
        }

        public void setIsGleeDelivered(int isGleeDelivered) {
            this.isGleeDelivered = isGleeDelivered;
        }

        public String getIsGleeDeliveredValue() {
            return isGleeDeliveredValue;
        }

        public void setIsGleeDeliveredValue(String isGleeDeliveredValue) {
            this.isGleeDeliveredValue = isGleeDeliveredValue;
        }

        public int getDistributeGleeNum() {
            return distributeGleeNum;
        }

        public void setDistributeGleeNum(int distributeGleeNum) {
            this.distributeGleeNum = distributeGleeNum;
        }

        public int getListedIntention() {
            return listedIntention;
        }

        public void setListedIntention(int listedIntention) {
            this.listedIntention = listedIntention;
        }

        public String getListedIntentionValue() {
            return listedIntentionValue;
        }

        public void setListedIntentionValue(String listedIntentionValue) {
            this.listedIntentionValue = listedIntentionValue;
        }

        public int getDistributeActivityNum() {
            return distributeActivityNum;
        }

        public void setDistributeActivityNum(int distributeActivityNum) {
            this.distributeActivityNum = distributeActivityNum;
        }

        public int getVisitIntention() {
            return visitIntention;
        }

        public void setVisitIntention(int visitIntention) {
            this.visitIntention = visitIntention;
        }

        public String getVisitIntentionValue() {
            return visitIntentionValue;
        }

        public void setVisitIntentionValue(String visitIntentionValue) {
            this.visitIntentionValue = visitIntentionValue;
        }

        public int getDistributeGiftNum() {
            return distributeGiftNum;
        }

        public void setDistributeGiftNum(int distributeGiftNum) {
            this.distributeGiftNum = distributeGiftNum;
        }

        public String getSpecificMeasure() {
            return specificMeasure;
        }

        public void setSpecificMeasure(String specificMeasure) {
            this.specificMeasure = specificMeasure;
        }

        public int getCsEnrollmentNum() {
            return csEnrollmentNum;
        }

        public void setCsEnrollmentNum(int csEnrollmentNum) {
            this.csEnrollmentNum = csEnrollmentNum;
        }

        public int getHqEnrollmentNum() {
            return hqEnrollmentNum;
        }

        public void setHqEnrollmentNum(int hqEnrollmentNum) {
            this.hqEnrollmentNum = hqEnrollmentNum;
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

        public String getStaffNames() {
            return staffNames;
        }

        public void setStaffNames(String staffNames) {
            this.staffNames = staffNames;
        }
    }
}
