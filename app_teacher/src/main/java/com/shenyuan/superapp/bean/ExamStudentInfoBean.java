package com.shenyuan.superapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author ch
 * @date 2021/5/19 15:12
 * @
 */
public class ExamStudentInfoBean implements Serializable {
    /**
     * tableSecondTitle : 2021年高职扩招准考证
     * stuNum : 0101180008
     * stuName : 薛佳驹
     * gender : 男
     * phone : 17629037827
     * identity : 610*********2834
     * subject : 文史
     * major : null
     * isPassOrRefuse : 0
     * absentResion : null
     * examArrangeList : [{"examName":"123","examTime":"123","examLocation":"123"}]
     * attentionList : null
     */

    private String tableSecondTitle;
    private String stuNum;
    private String stuName;
    private String gender;
    private String phone;
    private String identity;
    private String subject;
    private String major;
    private String studyMode;
    private int isPassOrRefuse;
    private String absentResion;
    private Object attentionList;
    private List<ExamArrangeListBean> examArrangeList;

    public String getStudyMode() {
        return studyMode;
    }

    public void setStudyMode(String studyMode) {
        this.studyMode = studyMode;
    }

    public static class ExamArrangeListBean implements Serializable {
        /**
         * examName : 123
         * examTime : 123
         * examLocation : 123
         */

        private String examName;
        private String examTime;
        private String examLocation;

        public String getExamName() {
            return examName;
        }

        public void setExamName(String examName) {
            this.examName = examName;
        }

        public String getExamTime() {
            return examTime;
        }

        public void setExamTime(String examTime) {
            this.examTime = examTime;
        }

        public String getExamLocation() {
            return examLocation;
        }

        public void setExamLocation(String examLocation) {
            this.examLocation = examLocation;
        }
    }

    public String getTableSecondTitle() {
        return tableSecondTitle;
    }

    public void setTableSecondTitle(String tableSecondTitle) {
        this.tableSecondTitle = tableSecondTitle;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getIsPassOrRefuse() {
        return isPassOrRefuse;
    }

    public void setIsPassOrRefuse(int isPassOrRefuse) {
        this.isPassOrRefuse = isPassOrRefuse;
    }

    public String getAbsentResion() {
        return absentResion;
    }

    public void setAbsentResion(String absentResion) {
        this.absentResion = absentResion;
    }

    public Object getAttentionList() {
        return attentionList;
    }

    public void setAttentionList(Object attentionList) {
        this.attentionList = attentionList;
    }

    public List<ExamArrangeListBean> getExamArrangeList() {
        return examArrangeList;
    }

    public void setExamArrangeList(List<ExamArrangeListBean> examArrangeList) {
        this.examArrangeList = examArrangeList;
    }
}
