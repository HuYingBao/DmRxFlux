package com.huyingbao.dm.ui.message.model;

import java.util.List;

/**
 * Created by liujunfeng on 2017/8/18.
 */
public class MessageData {
    /**
     * content : {"msg":"[杨前锋]于[2017-08-16 11:58:20]完成设备[A-HL-00-01]的维修，请尽快安排验收。","targetId":10028,"type":4}
     * createTime : 2017-08-17 03:58:20
     * id : 100
     * machineId : 8
     * sendTime : 2017-08-17 03:58:20
     * sender : 杨前锋
     * senderId : 2
     * targetUserIdArr : [2]
     * targetUserIds : 2
     * type : 4
     */
    private String content;
    private String createTime;
    private int id;
    private int machineId;
    private String sendTime;
    private String sender;
    private int senderId;
    private String targetUserIds;
    private int type;
    private List<Integer> targetUserIdArr;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getTargetUserIds() {
        return targetUserIds;
    }

    public void setTargetUserIds(String targetUserIds) {
        this.targetUserIds = targetUserIds;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Integer> getTargetUserIdArr() {
        return targetUserIdArr;
    }

    public void setTargetUserIdArr(List<Integer> targetUserIdArr) {
        this.targetUserIdArr = targetUserIdArr;
    }
}
