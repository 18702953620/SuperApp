package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.shenyuan.superapp.common.bean.BaseChooseBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/26 9:58
 * desc
 */
public class StudentListBean extends BaseChooseBean implements Serializable {
    /**
     * id : 10
     * studentName : 王玉
     * gender : 男
     * mobile : 187029536221
     * schoolName : 高新四中
     * areaName : 西安一区
     * areaStaffName : admin
     * subject : null
     * graduateYear : 2021
     * identitySub : null
     * identityNum : 610427199006063933
     * socialNum : 5566
     * predicateScore : 680
     * majorName : 电子竞技
     * studentTargetName : 高分生源
     * staffName : [null,null,null]
     * sourceName : null
     * parentMobile : null
     */

    @JSONField(name = "id")
    private int id;
    @JSONField(name = "studentTarget")
    private int studentTarget;
    @JSONField(name = "studentName")
    private String studentName;
    @JSONField(name = "gender")
    private String gender;
    @JSONField(name = "mobile")
    private String mobile;
    @JSONField(name = "schoolName")
    private String schoolName;
    @JSONField(name = "schoolName")
    private String name;
    @JSONField(name = "areaName")
    private String areaName;
    @JSONField(name = "areaStaffName")
    private String areaStaffName;
    @JSONField(name = "subject")
    private String subject;
    @JSONField(name = "graduateYear")
    private int graduateYear;
    @JSONField(name = "identitySub")
    private String identitySub;
    @JSONField(name = "identityNum")
    private String identityNum;
    @JSONField(name = "socialNum")
    private String socialNum;
    @JSONField(name = "predicateScore")
    private int predicateScore;
    @JSONField(name = "majorName")
    private String majorName;
    @JSONField(name = "studentTargetName")
    private String studentTargetName;
    @JSONField(name = "staffName")
    private List<String> staffName;
    @JSONField(name = "sourceName")
    private String sourceName;
    @JSONField(name = "parentMobile")
    private String parentMobile;
    @JSONField(name = "studentGoal")
    private String studentGoal;
    @JSONField(name = "province")
    private String province;
    @JSONField(name = "city")
    private String city;
    @JSONField(name = "region")
    private String region;
    @JSONField(name = "targetDegreeName")
    private String targetDegreeName;
    @JSONField(name = "returnedName")
    private String returnedName;
    @JSONField(name = "staffPropagandist")
    private List<StaffBean> staffPropagandist;

    public int getStudentTarget() {
        return studentTarget;
    }

    public void setStudentTarget(int studentTarget) {
        this.studentTarget = studentTarget;
    }

    public List<StaffBean> getStaffPropagandist() {
        return staffPropagandist;
    }

    public void setStaffPropagandist(List<StaffBean> staffPropagandist) {
        this.staffPropagandist = staffPropagandist;
    }

    public String getReturnedName() {
        return returnedName;
    }

    public void setReturnedName(String returnedName) {
        this.returnedName = returnedName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetDegreeName() {
        return targetDegreeName;
    }

    public void setTargetDegreeName(String targetDegreeName) {
        this.targetDegreeName = targetDegreeName;
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

    public String getStudentGoal() {
        return studentGoal;
    }

    public void setStudentGoal(String studentGoal) {
        this.studentGoal = studentGoal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(int graduateYear) {
        this.graduateYear = graduateYear;
    }

    public String getIdentitySub() {
        return identitySub;
    }

    public void setIdentitySub(String identitySub) {
        this.identitySub = identitySub;
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

    public int getPredicateScore() {
        return predicateScore;
    }

    public void setPredicateScore(int predicateScore) {
        this.predicateScore = predicateScore;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getStudentTargetName() {
        return studentTargetName;
    }

    public void setStudentTargetName(String studentTargetName) {
        this.studentTargetName = studentTargetName;
    }

    public List<String> getStaffName() {
        return staffName;
    }

    public void setStaffName(List<String> staffName) {
        this.staffName = staffName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getParentMobile() {
        return parentMobile;
    }

    public void setParentMobile(String parentMobile) {
        this.parentMobile = parentMobile;
    }
}
