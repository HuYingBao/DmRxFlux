package com.huyingbao.dm.ui.devicelist.model;

/**
 * 设备类型
 * Created by liujunfeng on 2017/5/26.
 */
public class DeviceType {
    /**
     * code : pjj
     * createTime : 2017-05-16 21:22:09
     * id : 5
     * name : 喷胶机
     */
    private String code;
    private String createTime;
    private int id;
    private String name;
    private boolean isSelected = false;

    public DeviceType() {

    }

    public DeviceType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
