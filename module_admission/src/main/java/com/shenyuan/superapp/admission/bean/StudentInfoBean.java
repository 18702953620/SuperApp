package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/26 13:36
 * desc
 */
public class StudentInfoBean {
    /**
     * id : 10
     * studentTarget : 1
     * studentGoal : 0
     * studentName : 王玉
     * gender : 0
     * mobile : 187029536221
     * schoolId : 17
     * subject : 0
     * graduateYear : 2021
     * identityNum : 610427199006063933
     * socialNum : 6156111
     * parentMobile1 : 18702953623
     * parentMobile2 : 18702953623
     * parentMobile3 : 18702953623
     * majorId : 1
     * staffIds : null
     * predicateScore : 680
     * remark : 9666
     * source : 0
     * studentTargetName : 高分生源
     * studentGoalName : 本科招生
     * genderName : 未知
     * schoolName : 高新四中
     * subjectName : 文史
     * majorName : 电子竞技
     * propagandistVoList : [{"id":22,"studentId":10,"deptName":null,"staffName":"测试员2号"},{"id":1,"studentId":10,"deptName":null,"staffName":"admin"},{"id":2,"studentId":10,"deptName":null,"staffName":"测试员1号"}]
     */

    @JSONField(name = "id")
    private int id;
    @JSONField(name = "studentTarget")
    private int studentTarget;
    @JSONField(name = "studentGoal")
    private int studentGoal;
    @JSONField(name = "studentName")
    private String studentName;
    @JSONField(name = "gender")
    private int gender;
    @JSONField(name = "mobile")
    private String mobile;
    @JSONField(name = "schoolId")
    private int schoolId;
    @JSONField(name = "subject")
    private int subject;
    @JSONField(name = "graduateYear")
    private int graduateYear;
    @JSONField(name = "identityNum")
    private String identityNum;
    @JSONField(name = "socialNum")
    private String socialNum;
    @JSONField(name = "parentMobile1")
    private String parentMobile1;
    @JSONField(name = "parentMobile2")
    private String parentMobile2;
    @JSONField(name = "parentMobile3")
    private String parentMobile3;
    @JSONField(name = "majorId")
    private int majorId;
    @JSONField(name = "staffIds")
    private String staffIds;
    @JSONField(name = "predicateScore")
    private int predicateScore;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "source")
    private int source;
    @JSONField(name = "studentTargetName")
    private String studentTargetName;
    @JSONField(name = "studentGoalName")
    private String studentGoalName;
    @JSONField(name = "genderName")
    private String genderName;
    @JSONField(name = "schoolName")
    private String schoolName;
    @JSONField(name = "subjectName")
    private String subjectName;
    @JSONField(name = "majorName")
    private String majorName;
    @JSONField(name = "gradeName")
    private String gradeName;
    @JSONField(name = "areaName")
    private String areaName;
    @JSONField(name = "identitySub")
    private String identitySub;
    @JSONField(name = "areaStaffName")
    private String areaStaffName;
    @JSONField(name = "propagandistVoList")
    private List<StaffBean> propagandistVoList;
    @JSONField(name = "commuList")
    private List<CommuListBean> commuList;

    @JSONField(name = "province")
    private String province;
    @JSONField(name = "city")
    private String city;
    @JSONField(name = "region")
    private String region;

    public String getIdentitySub() {
        return identitySub;
    }

    public void setIdentitySub(String identitySub) {
        this.identitySub = identitySub;
    }

    public List<CommuListBean> getCommuList() {
        return commuList;
    }

    public void setCommuList(List<CommuListBean> commuList) {
        this.commuList = commuList;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaStaffName() {
        return areaStaffName;
    }

    public void setAreaStaffName(String areaStaffName) {
        this.areaStaffName = areaStaffName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentTarget() {
        return studentTarget;
    }

    public void setStudentTarget(int studentTarget) {
        this.studentTarget = studentTarget;
    }

    public int getStudentGoal() {
        return studentGoal;
    }

    public void setStudentGoal(int studentGoal) {
        this.studentGoal = studentGoal;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public int getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(int graduateYear) {
        this.graduateYear = graduateYear;
    }

    public String getIdentityNum() {
        return identityNum;
    }

    public void setIdentityNum(String identityNum) {
        this.identityNum = identityNum;
    }

    public String getSocialNum() {
        return socialNum;
    }

    public void setSocialNum(String socialNum) {
        this.socialNum = socialNum;
    }

    public String getParentMobile1() {
        return parentMobile1;
    }

    public void setParentMobile1(String parentMobile1) {
        this.parentMobile1 = parentMobile1;
    }

    public String getParentMobile2() {
        return parentMobile2;
    }

    public void setParentMobile2(String parentMobile2) {
        this.parentMobile2 = parentMobile2;
    }

    public String getParentMobile3() {
        return parentMobile3;
    }

    public void setParentMobile3(String parentMobile3) {
        this.parentMobile3 = parentMobile3;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(String staffIds) {
        this.staffIds = staffIds;
    }

    public int getPredicateScore() {
        return predicateScore;
    }

    public void setPredicateScore(int predicateScore) {
        this.predicateScore = predicateScore;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getStudentTargetName() {
        return studentTargetName;
    }

    public void setStudentTargetName(String studentTargetName) {
        this.studentTargetName = studentTargetName;
    }

    public String getStudentGoalName() {
        return studentGoalName;
    }

    public void setStudentGoalName(String studentGoalName) {
        this.studentGoalName = studentGoalName;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public List<StaffBean> getPropagandistVoList() {
        return propagandistVoList;
    }

    public void setPropagandistVoList(List<StaffBean> propagandistVoList) {
        this.propagandistVoList = propagandistVoList;
    }

    public static class CommuListBean {

        /**
         * id : 1
         * studentId : 1
         * content : HelloWorld！
         * picture : http://192.168.30.139:9000/jysyfile/timg.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T093135Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=fcc776309b243a09693910139d52e4a4c25b90da351014e2bdf11e1f204ea24a,http://192.168.30.139:9000/jysyfile/timg.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=jysyminio%2F20210303%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20210303T085631Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=f6cc8d5f1aa9fa4cb215234c44e9959078fa7d140e37afda47762c257298f39b
         * creatorId : 1
         * creatorName : admin
         * createTime : 2021-03-04 14:11:00
         */

        @JSONField(name = "id")
        private Integer id;
        @JSONField(name = "studentId")
        private Integer studentId;
        @JSONField(name = "content")
        private String content;
        @JSONField(name = "picture")
        private String picture;
        @JSONField(name = "creatorId")
        private Integer creatorId;
        @JSONField(name = "creatorName")
        private String creatorName;
        @JSONField(name = "createTime")
        private String createTime;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getStudentId() {
            return studentId;
        }

        public void setStudentId(Integer studentId) {
            this.studentId = studentId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public Integer getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(Integer creatorId) {
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
    }
}
