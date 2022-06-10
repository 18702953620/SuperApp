package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ch
 * @date 2021/3/17 16:19
 * desc
 */
public class FeedBackListBean {
    /**
     * id : 1
     * fbName : 12
     * startTime : 2021-03-17 00:00:00
     * endTime : 2021-03-17 00:00:00
     * deleted : null
     * creatorName : admin
     * createTime : 2021-03-17 15:31:20
     * creatorId : 1
     * areaName : 西安一区
     */

    @JSONField(name = "id")
    private int id;
    @JSONField(name = "fbName")
    private String fbName;
    @JSONField(name = "startTime")
    private String startTime;
    @JSONField(name = "endTime")
    private String endTime;
    @JSONField(name = "creatorName")
    private String creatorName;
    @JSONField(name = "createTime")
    private String createTime;
    @JSONField(name = "creatorId")
    private Integer creatorId;
    @JSONField(name = "areaName")
    private String areaName;
    @JSONField(name = "classNum")
    private int classNum;
    @JSONField(name = "studentNum")
    private int studentNum;

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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
