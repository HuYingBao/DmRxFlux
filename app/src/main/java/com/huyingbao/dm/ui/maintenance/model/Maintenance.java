package com.huyingbao.dm.ui.maintenance.model;

import android.os.Parcel;

import com.huyingbao.rxflux2.model.BaseDevice;

/**
 * 保养记录
 * Created by liujunfeng on 2017/3/22.
 */
public class Maintenance extends BaseDevice {
    public static final Creator<Maintenance> CREATOR = new Creator<Maintenance>() {
        @Override
        public Maintenance createFromParcel(Parcel source) {
            return new Maintenance(source);
        }

        @Override
        public Maintenance[] newArray(int size) {
            return new Maintenance[size];
        }
    };
    /**
     * createTime : 2017-05-22 16:32:03
     * duration : 7954
     * id : 10000
     * machineId : 7
     * maintainerId : 1
     * maintainerName : 管理员
     * pauseExplain : 油泵漏油
     * planId : 1
     * preExplain : test
     * rank : 3
     * result : 0
     * startTime : 2017-05-17 16:31:31
     */
    private String createTime;
    private long duration;
    private int id;
    private int maintainerId;
    private String maintainerName;
    private String pauseExplain;//暂停原因
    private int planId;
    private String preExplain;//保养前情况说明
    private int rank;
    private int result;
    private String planDate;
    private String remark;
    private String startTime;
    private String continueTime;
    private String finishTime;

    public Maintenance() {
    }

    protected Maintenance(Parcel in) {
        super(in);
        this.createTime = in.readString();
        this.duration = in.readLong();
        this.id = in.readInt();
        this.maintainerId = in.readInt();
        this.maintainerName = in.readString();
        this.pauseExplain = in.readString();
        this.planId = in.readInt();
        this.preExplain = in.readString();
        this.rank = in.readInt();
        this.result = in.readInt();
        this.planDate = in.readString();
        this.remark = in.readString();
        this.startTime = in.readString();
        this.continueTime = in.readString();
        this.finishTime = in.readString();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaintainerId() {
        return maintainerId;
    }

    public void setMaintainerId(int maintainerId) {
        this.maintainerId = maintainerId;
    }

    public String getMaintainerName() {
        return maintainerName;
    }

    public void setMaintainerName(String maintainerName) {
        this.maintainerName = maintainerName;
    }

    public String getPauseExplain() {
        return pauseExplain;
    }

    public void setPauseExplain(String pauseExplain) {
        this.pauseExplain = pauseExplain;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getPreExplain() {
        return preExplain;
    }

    public void setPreExplain(String preExplain) {
        this.preExplain = preExplain;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getContinueTime() {
        return continueTime;
    }

    public void setContinueTime(String continueTime) {
        this.continueTime = continueTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.createTime);
        dest.writeLong(this.duration);
        dest.writeInt(this.id);
        dest.writeInt(this.maintainerId);
        dest.writeString(this.maintainerName);
        dest.writeString(this.pauseExplain);
        dest.writeInt(this.planId);
        dest.writeString(this.preExplain);
        dest.writeInt(this.rank);
        dest.writeInt(this.result);
        dest.writeString(this.planDate);
        dest.writeString(this.remark);
        dest.writeString(this.startTime);
        dest.writeString(this.continueTime);
        dest.writeString(this.finishTime);
    }
}
