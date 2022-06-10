package com.shenyuan.superapp.common.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author ch
 * @date 2021/2/18 11:03
 * desc
 */
public class RegionBean {
    /**
     * id : 110000
     * name : 北京
     * shortName : 北京
     * level : 1
     */

    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "shortName")
    private String shortName;
    @JSONField(name = "firstLetter")
    private String firstLetter;
    @JSONField(name = "level")
    private Integer level;

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
