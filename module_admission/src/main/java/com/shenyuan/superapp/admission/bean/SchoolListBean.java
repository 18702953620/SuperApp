package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.shenyuan.superapp.common.bean.BaseChooseBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/20 11:16
 * desc
 */
public class SchoolListBean extends BaseChooseBean implements Serializable {


    /**
     * id : 2
     * schoolName : 西安铁二中
     * provinceName : 陕西省
     * cityName : 西安市
     * regionName : 雁塔区
     * total : 1850
     * areaId : null
     * areaName : null
     * areaStaffName : null
     * areaStaffId : null
     * isListed : 1
     * staffPropagandist : [{"id":1,"schoolId":0,"deptName":null,"areaId":null,"areaName":null,"staffName":"admin"}]
     * bachelorType : 一类重点
     * vocationType : 一类重点
     * diplomaType : null
     * state : 待维护
     * returnedName : null
     * returnedTime : null
     * creatorName : 张三
     * createTime : 2021-02-05 17:01:35
     * bachelorEnrollList : [{"schoolId":2,"year":2020,"enrollNumber":15},{"schoolId":2,"year":2019,"enrollNumber":160},{"schoolId":2,"year":2018,"enrollNumber":180},{"schoolId":2,"year":2017,"enrollNumber":220}]
     * vocationEnrollList : [{"schoolId":2,"year":2020,"enrollNumber":160},{"schoolId":2,"year":2019,"enrollNumber":228},{"schoolId":2,"year":2018,"enrollNumber":235},{"schoolId":2,"year":2017,"enrollNumber":652}]
     * diplomaEnrollList : []
     * distributionName : null
     * distributionTime : null
     */

    @JSONField(name = "id")
    private int id;
    @JSONField(name = "schoolName")
    private String schoolName;
    @JSONField(name = "provinceName")
    private String provinceName;
    @JSONField(name = "cityName")
    private String cityName;
    @JSONField(name = "regionName")
    private String regionName;
    @JSONField(name = "total")
    private int total;
    @JSONField(name = "areaId")
    private String areaId;
    @JSONField(name = "areaName")
    private String areaName;
    @JSONField(name = "areaStaffName")
    private String areaStaffName;
    @JSONField(name = "areaStaffId")
    private String areaStaffId;
    @JSONField(name = "isListed")
    private String isListed;
    @JSONField(name = "staffPropagandist")
    private List<StaffPropagandistDTO> staffPropagandist;
    @JSONField(name = "bachelorType")
    private String bachelorType;
    @JSONField(name = "vocationType")
    private String vocationType;
    @JSONField(name = "diplomaType")
    private String diplomaType;
    @JSONField(name = "state")
    private String state;
    @JSONField(name = "returnedName")
    private String returnedName;
    @JSONField(name = "returnedTime")
    private String returnedTime;
    @JSONField(name = "creatorName")
    private String creatorName;
    @JSONField(name = "createTime")
    private String createTime;
    @JSONField(name = "bachelorEnrollList")
    private List<BachelorEnrollListDTO> bachelorEnrollList;
    @JSONField(name = "enrollList")
    private List<ErollListBean> enrollList;
    @JSONField(name = "vocationEnrollList")
    private List<BachelorEnrollListDTO> vocationEnrollList;
    @JSONField(name = "diplomaEnrollList")
    private List<BachelorEnrollListDTO> diplomaEnrollList;
    @JSONField(name = "distributionName")
    private String distributionName;
    @JSONField(name = "distributionTime")
    private String distributionTime;
    @JSONField(name = "schoolType")
    private int schoolType;

    public int getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(int schoolType) {
        this.schoolType = schoolType;
    }

    public List<ErollListBean> getEnrollList() {
        return enrollList;
    }

    public void setEnrollList(List<ErollListBean> enrollList) {
        this.enrollList = enrollList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
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

    public String getAreaStaffId() {
        return areaStaffId;
    }

    public void setAreaStaffId(String areaStaffId) {
        this.areaStaffId = areaStaffId;
    }

    public String getIsListed() {
        return isListed;
    }

    public void setIsListed(String isListed) {
        this.isListed = isListed;
    }

    public List<StaffPropagandistDTO> getStaffPropagandist() {
        return staffPropagandist;
    }

    public void setStaffPropagandist(List<StaffPropagandistDTO> staffPropagandist) {
        this.staffPropagandist = staffPropagandist;
    }

    public String getBachelorType() {
        return bachelorType;
    }

    public void setBachelorType(String bachelorType) {
        this.bachelorType = bachelorType;
    }

    public String getVocationType() {
        return vocationType;
    }

    public void setVocationType(String vocationType) {
        this.vocationType = vocationType;
    }

    public String getDiplomaType() {
        return diplomaType;
    }

    public void setDiplomaType(String diplomaType) {
        this.diplomaType = diplomaType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReturnedName() {
        return returnedName;
    }

    public void setReturnedName(String returnedName) {
        this.returnedName = returnedName;
    }

    public String getReturnedTime() {
        return returnedTime;
    }

    public void setReturnedTime(String returnedTime) {
        this.returnedTime = returnedTime;
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

    public List<BachelorEnrollListDTO> getBachelorEnrollList() {
        return bachelorEnrollList;
    }

    public void setBachelorEnrollList(List<BachelorEnrollListDTO> bachelorEnrollList) {
        this.bachelorEnrollList = bachelorEnrollList;
    }

    public List<BachelorEnrollListDTO> getVocationEnrollList() {
        return vocationEnrollList;
    }

    public void setVocationEnrollList(List<BachelorEnrollListDTO> vocationEnrollList) {
        this.vocationEnrollList = vocationEnrollList;
    }

    public List<BachelorEnrollListDTO> getDiplomaEnrollList() {
        return diplomaEnrollList;
    }

    public void setDiplomaEnrollList(List<BachelorEnrollListDTO> diplomaEnrollList) {
        this.diplomaEnrollList = diplomaEnrollList;
    }

    public String getDistributionName() {
        return distributionName;
    }

    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }

    public String getDistributionTime() {
        return distributionTime;
    }

    public void setDistributionTime(String distributionTime) {
        this.distributionTime = distributionTime;
    }

    public static class ErollListBean implements Serializable {

        /**
         * type : null
         * schoolId : 4
         * year : 2020
         * quantity : 520
         */

        @JSONField(name = "type")
        private String type;
        @JSONField(name = "schoolId")
        private int schoolId;
        @JSONField(name = "year")
        private int year;
        @JSONField(name = "quantity")
        private int quantity;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public static class StaffPropagandistDTO implements Serializable {
        /**
         * id : 1
         * schoolId : 0
         * deptName : null
         * areaId : null
         * areaName : null
         * staffName : admin
         */

        @JSONField(name = "id")
        private int id;
        @JSONField(name = "schoolId")
        private int schoolId;
        @JSONField(name = "deptName")
        private String deptName;
        @JSONField(name = "areaId")
        private String areaId;
        @JSONField(name = "areaName")
        private String areaName;
        @JSONField(name = "staffName")
        private String staffName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }
    }

    public static class BachelorEnrollListDTO implements Serializable {
        /**
         * schoolId : 2
         * year : 2020
         * enrollNumber : 15
         */

        @JSONField(name = "schoolId")
        private int schoolId;
        @JSONField(name = "year")
        private int year;
        @JSONField(name = "enrollNumber")
        private int enrollNumber;

        public int getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getEnrollNumber() {
            return enrollNumber;
        }

        public void setEnrollNumber(int enrollNumber) {
            this.enrollNumber = enrollNumber;
        }
    }

}
