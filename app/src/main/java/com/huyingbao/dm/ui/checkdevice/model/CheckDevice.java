package com.huyingbao.dm.ui.checkdevice.model;

import android.os.Parcel;

import com.huyingbao.dm.ui.devicelist.model.Device;

/**
 * Created by liujunfeng on 2017/8/5.
 */
public class CheckDevice extends Device {
    public static final Creator<CheckDevice> CREATOR = new Creator<CheckDevice>() {
        @Override
        public CheckDevice createFromParcel(Parcel source) {
            return new CheckDevice(source);
        }

        @Override
        public CheckDevice[] newArray(int size) {
            return new CheckDevice[size];
        }
    };
    /**
     * finishTime : 2017-07-18 14:01:22
     * isRepair : true
     * machineId : 7
     * result : 2
     */
    private String finishTime;
    private boolean isRepair;
    private int machineId;
    private int result;

    public CheckDevice() {
    }

    protected CheckDevice(Parcel in) {
        super(in);
        this.finishTime = in.readString();
        this.isRepair = in.readByte() != 0;
        this.machineId = in.readInt();
        this.result = in.readInt();
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public boolean isIsRepair() {
        return isRepair;
    }

    public void setIsRepair(boolean isRepair) {
        this.isRepair = isRepair;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.finishTime);
        dest.writeByte(this.isRepair ? (byte) 1 : (byte) 0);
        dest.writeInt(this.machineId);
        dest.writeInt(this.result);
    }
}
