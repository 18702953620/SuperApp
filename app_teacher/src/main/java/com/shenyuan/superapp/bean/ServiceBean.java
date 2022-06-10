package com.shenyuan.superapp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/12 9:37
 * desc
 */
public class ServiceBean {
    /**
     * id : 5
     * typeName : 教务
     * sort : 1
     * sysList : [{"id":6,"systemName":"教学服务系统","sort":0,"typeId":5,"infoList":[{"id":9,"sort":1,"serviceName":"选课管理","iconUrl":null,"routerUrl":"teach/lesson/choose","introduction":null,"isMyService":null,"guide":null,"typeName":null,"deptName":null,"serviceTarget":null}]}]
     */

    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "typeName")
    private String typeName;
    @JSONField(name = "sort")
    private Integer sort;
    @JSONField(name = "sysList")
    private List<SysListDTO> sysList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<SysListDTO> getSysList() {
        return sysList;
    }

    public void setSysList(List<SysListDTO> sysList) {
        this.sysList = sysList;
    }

    public static class SysListDTO {
        /**
         * id : 6
         * systemName : 教学服务系统
         * sort : 0
         * typeId : 5
         * infoList : [{"id":9,"sort":1,"serviceName":"选课管理","iconUrl":null,"routerUrl":"teach/lesson/choose","introduction":null,"isMyService":null,"guide":null,"typeName":null,"deptName":null,"serviceTarget":null}]
         */

        @JSONField(name = "id")
        private Integer id;
        @JSONField(name = "systemName")
        private String systemName;
        @JSONField(name = "sort")
        private Integer sort;
        @JSONField(name = "typeId")
        private Integer typeId;
        @JSONField(name = "infoList")
        private List<MenuBean> infoList;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSystemName() {
            return systemName;
        }

        public void setSystemName(String systemName) {
            this.systemName = systemName;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public Integer getTypeId() {
            return typeId;
        }

        public void setTypeId(Integer typeId) {
            this.typeId = typeId;
        }

        public List<MenuBean> getInfoList() {
            return infoList;
        }

        public void setInfoList(List<MenuBean> infoList) {
            this.infoList = infoList;
        }
    }
}
