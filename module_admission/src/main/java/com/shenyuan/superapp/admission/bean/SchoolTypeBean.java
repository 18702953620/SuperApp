package com.shenyuan.superapp.admission.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/18 13:17
 * desc
 */
public class SchoolTypeBean {


    /**
     * bachelorType : [{"schoolLevel":1,"levelName":"一类重点"},{"schoolLevel":2,"levelName":"二类机会"},{"schoolLevel":3,"levelName":"三类开发"}]
     * bachelorName : 本科
     * vocationType : null
     * vocationName : null
     * diplomaType : [{"schoolLevel":1,"levelName":"一类重点"},{"schoolLevel":2,"levelName":"二类机会"},{"schoolLevel":3,"levelName":"三类开发"}]
     * diplomaName : 高职
     */

    @JSONField(name = "bachelorType")
    private List<DiplomaTypeDTO> bachelorType;
    @JSONField(name = "bachelorName")
    private String bachelorName;
    @JSONField(name = "vocationType")
    private List<DiplomaTypeDTO> vocationType;
    @JSONField(name = "vocationName")
    private String vocationName;
    @JSONField(name = "diplomaType")
    private List<DiplomaTypeDTO> diplomaType;
    @JSONField(name = "diplomaName")
    private String diplomaName;

    public List<DiplomaTypeDTO> getBachelorType() {
        return bachelorType;
    }

    public void setBachelorType(List<DiplomaTypeDTO> bachelorType) {
        this.bachelorType = bachelorType;
    }

    public String getBachelorName() {
        return bachelorName;
    }

    public void setBachelorName(String bachelorName) {
        this.bachelorName = bachelorName;
    }

    public List<DiplomaTypeDTO> getVocationType() {
        return vocationType;
    }

    public void setVocationType(List<DiplomaTypeDTO> vocationType) {
        this.vocationType = vocationType;
    }

    public String getVocationName() {
        return vocationName;
    }

    public void setVocationName(String vocationName) {
        this.vocationName = vocationName;
    }

    public List<DiplomaTypeDTO> getDiplomaType() {
        return diplomaType;
    }

    public void setDiplomaType(List<DiplomaTypeDTO> diplomaType) {
        this.diplomaType = diplomaType;
    }

    public String getDiplomaName() {
        return diplomaName;
    }

    public void setDiplomaName(String diplomaName) {
        this.diplomaName = diplomaName;
    }


    public static class DiplomaTypeDTO {
        /**
         * schoolLevel : 1
         * levelName : 一类重点
         */

        @JSONField(name = "schoolLevel")
        private Integer schoolLevel;
        @JSONField(name = "levelName")
        private String levelName;

        public Integer getSchoolLevel() {
            return schoolLevel;
        }

        public void setSchoolLevel(Integer schoolLevel) {
            this.schoolLevel = schoolLevel;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }
    }
}
