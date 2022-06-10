package com.shenyuan.superapp.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @author ch
 * @date 2020/12/21-11:10
 * desc
 */
public class UserInfoBean implements Serializable {

    /**
     * id : 1
     * username : admin
     * gender : 男
     * userCode : 0000
     * mobile : 18710812229
     * identity : 00000000
     * userType : null
     * email : admin@admin.hk
     * roleNames : ["超级管理员"]
     * avatar : null
     * roles : [1]
     */

    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "username")
    private String username;
    @JSONField(name = "gender")
    private String gender;
    @JSONField(name = "userCode")
    private String userCode;
    @JSONField(name = "mobile")
    private String mobile;
    @JSONField(name = "identity")
    private String identity;
    @JSONField(name = "userType")
    private String userType;
    @JSONField(name = "email")
    private String email;
    @JSONField(name = "roleNames")
    private List<String> roleNames;
    @JSONField(name = "avatar")
    private String avatar;
    @JSONField(name = "roles")
    private List<Integer> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }
}
