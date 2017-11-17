package com.huyingbao.rxflux2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 巡检等父类
 * Created by liujunfeng on 2017/6/1.
 */
public abstract class BaseDevice implements Parcelable {
    private String machineName;
    private String machineCode;
    private String machineHeadImg;
    private int machineId;
    private int machineType;
    private int degree;
    private int statusMain;
    private int[] statusMinor;

    public BaseDevice() {
    }

    protected BaseDevice(Parcel in) {
        this.machineName = in.readString();
        this.machineCode = in.readString();
        this.machineHeadImg = in.readString();
        this.machineId = in.readInt();
        this.machineType = in.readInt();
        this.degree = in.readInt();
        this.statusMain = in.readInt();
        this.statusMinor = in.createIntArray();
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMachineHeadImg() {
        return machineHeadImg;
    }

    public void setMachineHeadImg(String machineHeadImg) {
        this.machineHeadImg = machineHeadImg;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getMachineType() {
        return machineType;
    }

    public void setMachineType(int machineType) {
        this.machineType = machineType;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getStatusMain() {
        return statusMain;
    }

    public void setStatusMain(int statusMain) {
        this.statusMain = statusMain;
    }

    public int[] getStatusMinor() {
        return statusMinor;
    }

    public void setStatusMinor(int[] statusMinor) {
        this.statusMinor = statusMinor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.machineName);
        dest.writeString(this.machineCode);
        dest.writeString(this.machineHeadImg);
        dest.writeInt(this.machineId);
        dest.writeInt(this.machineType);
        dest.writeInt(this.degree);
        dest.writeInt(this.statusMain);
        dest.writeIntArray(this.statusMinor);
    }
}
