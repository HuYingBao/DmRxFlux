package com.huyingbao.dm.ui.repair.model;

import android.os.Parcel;

import com.huyingbao.rxflux2.model.BaseDevice;

/**
 * 维修记录
 * Created by liujunfeng on 2017/3/22.
 */
public class Repair extends BaseDevice {
    public static final Creator<Repair> CREATOR = new Creator<Repair>() {
        @Override
        public Repair createFromParcel(Parcel source) {
            return new Repair(source);
        }

        @Override
        public Repair[] newArray(int size) {
            return new Repair[size];
        }
    };
    /**
     * createTime : 2017-05-19 22:28:09
     * duration : 2545
     * faultCause : 故障问题1
     * id : 10000
     * informant : 1
     * informantName : 管理员
     * machineId : 7
     * pauseExplain : test
     * remark : test
     * repairMan : 1
     * repairManName : 管理员
     * repairType : 1
     * result : 0
     * startTime : 2017-05-17 22:27:37
     * continueTime : 2017-07-10 16:04:48
     * finishTime : 2017-07-10 16:07:57
     * scheme : 更换网带2
     */
    private String checkOpinion;
    private boolean checkResult;
    private String createTime;
    private long duration;
    private String faultCause;//问题故障
    private int id;
    private int informant;
    private String informantName;//报修人
    private String pauseExplain;
    private String remark;
    private int repairMan;
    private String repairManName;//维修员
    private int repairType;
    private int result;//'维修结果：-1未开始；0暂停；1进行中；2待验收；3完成'
    private String startTime;
    private String continueTime;
    private String finishTime;
    private String scheme;

    public Repair() {
    }

    protected Repair(Parcel in) {
        super(in);
        this.checkOpinion = in.readString();
        this.checkResult = in.readByte() != 0;
        this.createTime = in.readString();
        this.duration = in.readLong();
        this.faultCause = in.readString();
        this.id = in.readInt();
        this.informant = in.readInt();
        this.informantName = in.readString();
        this.pauseExplain = in.readString();
        this.remark = in.readString();
        this.repairMan = in.readInt();
        this.repairManName = in.readString();
        this.repairType = in.readInt();
        this.result = in.readInt();
        this.startTime = in.readString();
        this.continueTime = in.readString();
        this.finishTime = in.readString();
        this.scheme = in.readString();
    }

    public String getCheckOpinion() {
        return checkOpinion;
    }

    public void setCheckOpinion(String checkOpinion) {
        this.checkOpinion = checkOpinion;
    }

    public boolean isCheckResult() {
        return checkResult;
    }

    public void setCheckResult(boolean checkResult) {
        this.checkResult = checkResult;
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

    public String getFaultCause() {
        return faultCause;
    }

    public void setFaultCause(String faultCause) {
        this.faultCause = faultCause;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInformant() {
        return informant;
    }

    public void setInformant(int informant) {
        this.informant = informant;
    }

    public String getInformantName() {
        return informantName;
    }

    public void setInformantName(String informantName) {
        this.informantName = informantName;
    }

    public String getPauseExplain() {
        return pauseExplain;
    }

    public void setPauseExplain(String pauseExplain) {
        this.pauseExplain = pauseExplain;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRepairMan() {
        return repairMan;
    }

    public void setRepairMan(int repairMan) {
        this.repairMan = repairMan;
    }

    public String getRepairManName() {
        return repairManName;
    }

    public void setRepairManName(String repairManName) {
        this.repairManName = repairManName;
    }

    public int getRepairType() {
        return repairType;
    }

    public void setRepairType(int repairType) {
        this.repairType = repairType;
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

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.checkOpinion);
        dest.writeByte(this.checkResult ? (byte) 1 : (byte) 0);
        dest.writeString(this.createTime);
        dest.writeLong(this.duration);
        dest.writeString(this.faultCause);
        dest.writeInt(this.id);
        dest.writeInt(this.informant);
        dest.writeString(this.informantName);
        dest.writeString(this.pauseExplain);
        dest.writeString(this.remark);
        dest.writeInt(this.repairMan);
        dest.writeString(this.repairManName);
        dest.writeInt(this.repairType);
        dest.writeInt(this.result);
        dest.writeString(this.startTime);
        dest.writeString(this.continueTime);
        dest.writeString(this.finishTime);
        dest.writeString(this.scheme);
    }
}
