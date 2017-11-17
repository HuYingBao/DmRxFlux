package com.huyingbao.dm.ui.main.model;

/**
 * 用户
 * Created by liujunfeng on 2017/1/1.
 */
public class User {

    private int id;
    /**
     * 角色：0：管理员；1：设备管理员；2：设备维修员；3：设备巡检员。
     */
    private int role;
    private String name;
    private String alias;
    private String pwd;
    private String tel;
    private boolean admin;
    private boolean delFlag;
    /**
     * baiduChannelId : 12354563547456867
     */
    private String baiduChannelId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public String getBaiduChannelId() {
        return baiduChannelId;
    }

    public void setBaiduChannelId(String baiduChannelId) {
        this.baiduChannelId = baiduChannelId;
    }
}
