package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/23 16:29
 * desc
 */
public class SchoolInfoBean implements Serializable {
    /**
     * id : 17
     * schoolName : 高新四中
     * province : 陕西省
     * city : 西安市
     * region : 雁塔区
     * address : 高新路158号
     * contact : 哦哦
     * contactDuty : 主任
     * contactPhone : 18702953620
     * socialNum : 18702953620
     * remark : null
     * bachelorType : 一类重点
     * vocationType : null
     * diplomaType : null
     * bachelorEnrollList : [{"schoolId":17,"year":2021,"enrollNumber":58}]
     * vocationEnrollList : [{"schoolId":17,"year":2021,"enrollNumber":966}]
     * diplomaEnrollList : []
     * isListedName : 是
     * isListed : 1
     * areaId : 0
     * areaStaffName : null
     * state : 正常
     * areaName : null
     * propagandistVoList : []
     * enrollList : [{"type":null,"schoolId":17,"year":2021,"quantity":1024}]
     */

    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "schoolName")
    private String schoolName;
    @JSONField(name = "province")
    private String province;
    @JSONField(name = "city")
    private String city;
    @JSONField(name = "region")
    private String region;
    @JSONField(name = "address")
    private String address;
    @JSONField(name = "contact")
    private String contact;
    @JSONField(name = "contactDuty")
    private String contactDuty;
    @JSONField(name = "contactPhone")
    private String contactPhone;
    @JSONField(name = "socialNum")
    private String socialNum;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "bachelorType")
    private int bachelorType;
    @JSONField(name = "bachelorTypeName")
    private String bachelorTypeName;
    @JSONField(name = "vocationType")
    private int vocationType;
    @JSONField(name = "vocationTypeName")
    private String vocationTypeName;
    @JSONField(name = "diplomaType")
    private int diplomaType;
    @JSONField(name = "diplomaTypeName")
    private String diplomaTypeName;
    @JSONField(name = "bachelorEnrollList")
    private List<BachelorEnrollListDTO> bachelorEnrollList;
    @JSONField(name = "vocationEnrollList")
    private List<BachelorEnrollListDTO> vocationEnrollList;
    @JSONField(name = "diplomaEnrollList")
    private List<BachelorEnrollListDTO> diplomaEnrollList;
    @JSONField(name = "isListedName")
    private String isListedName;
    @JSONField(name = "isListed")
    private int isListed;
    @JSONField(name = "areaId")
    private int areaId;
    @JSONField(name = "areaStaffName")
    private String areaStaffName;
    @JSONField(name = "state")
    private String state;
    @JSONField(name = "areaName")
    private String areaName;
    @JSONField(name = "schoolType")
    private int schoolType;
    @JSONField(name = "schoolTypeName")
    private String schoolTypeName;
    @JSONField(name = "propagandistVoList")
    private List<StaffBean> propagandistVoList;
    @JSONField(name = "enrollList")
    private List<EnrollListDTO> enrollList;

    public String getBachelorTypeName() {
        return bachelorTypeName;
    }

    public void setBachelorTypeName(String bachelorTypeName) {
        this.bachelorTypeName = bachelorTypeName;
    }

    public String getVocationTypeName() {
        return vocationTypeName;
    }

    public void setVocationTypeName(String vocationTypeName) {
        this.vocationTypeName = vocationTypeName;
    }

    public String getDiplomaTypeName() {
        return diplomaTypeName;
    }

    public void setDiplomaTypeName(String diplomaTypeName) {
        this.diplomaTypeName = diplomaTypeName;
    }

    public String getSchoolTypeName() {
        return schoolTypeName;
    }

    public void setSchoolTypeName(String schoolTypeName) {
        this.schoolTypeName = schoolTypeName;
    }

    public int getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(int schoolType) {
        this.schoolType = schoolType;
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

    public String getSocialNum() {
        return socialNum;
    }

    public void setSocialNum(String socialNum) {
        this.socialNum = socialNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getBachelorType() {
        return bachelorType;
    }

    public void setBachelorType(int bachelorType) {
        this.bachelorType = bachelorType;
    }

    public int getVocationType() {
        return vocationType;
    }

    public void setVocationType(int vocationType) {
        this.vocationType = vocationType;
    }

    public int getDiplomaType() {
        return diplomaType;
    }

    public void setDiplomaType(int diplomaType) {
        this.diplomaType = diplomaType;
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

    public String getIsListedName() {
        return isListedName;
    }

    public void setIsListedName(String isListedName) {
        this.isListedName = isListedName;
    }

    public int getIsListed() {
        return isListed;
    }

    public void setIsListed(int isListed) {
        this.isListed = isListed;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaStaffName() {
        return areaStaffName;
    }

    public void setAreaStaffName(String areaStaffName) {
        this.areaStaffName = areaStaffName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<StaffBean> getPropagandistVoList() {
        return propagandistVoList;
    }

    public void setPropagandistVoList(List<StaffBean> propagandistVoList) {
        this.propagandistVoList = propagandistVoList;
    }

    public List<EnrollListDTO> getEnrollList() {
        return enrollList;
    }

    public void setEnrollList(List<EnrollListDTO> enrollList) {
        this.enrollList = enrollList;
    }

    public static class PropagandistVoListBean implements Serializable {

        /**
         * id : 1
         * schoolId : null
         * deptName : null
         * areaId : null
         * areaName : null
         * staffName : admin
         */

        @JSONField(name = "id")
        private int id;
        @JSONField(name = "schoolId")
        private String schoolId;
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

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
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
         * schoolId : 17
         * year : 2021
         * enrollNumber : 58
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

    public static class VocationEnrollListDTO {
        /**
         * schoolId : 17
         * year : 2021
         * enrollNumber : 966
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

    public static class EnrollListDTO implements Serializable {
        /**
         * type : null
         * schoolId : 17
         * year : 2021
         * quantity : 1024
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
}
