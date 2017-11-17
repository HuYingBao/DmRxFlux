package com.huyingbao.dm.ui.devicepart.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * 备件使用记录
 * Created by liujunfeng on 2017/5/26.
 */
public class DevicePart implements Parcelable {
    public static final Parcelable.Creator<DevicePart> CREATOR = new Parcelable.Creator<DevicePart>() {
        @Override
        public DevicePart createFromParcel(Parcel source) {
            return new DevicePart(source);
        }

        @Override
        public DevicePart[] newArray(int size) {
            return new DevicePart[size];
        }
    };
    private String specification;//规格
    private String quantity;//数量
    private String unit;//单位
    private String createTime;
    private boolean isRepair;
    private int lowerLimit;
    private String name;
    private int partRecordId;
    private int targetId;//对应维修或者保养id
    private int upperLimit;
    private String userName;
    private int id;//对应的备件具体什么型号
    private int typeId;
    private int unitCost;
    private int buyPeriod;

    public DevicePart() {
    }

    protected DevicePart(Parcel in) {
        this.specification = in.readString();
        this.quantity = in.readString();
        this.unit = in.readString();
        this.createTime = in.readString();
        this.isRepair = in.readByte() != 0;
        this.lowerLimit = in.readInt();
        this.name = in.readString();
        this.partRecordId = in.readInt();
        this.targetId = in.readInt();
        this.upperLimit = in.readInt();
        this.userName = in.readString();
        this.id = in.readInt();
        this.typeId = in.readInt();
        this.unitCost = in.readInt();
        this.buyPeriod = in.readInt();
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getQuantity() {
        return TextUtils.equals("0", quantity) ? "" : quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isRepair() {
        return isRepair;
    }

    public void setRepair(boolean isRepair) {
        this.isRepair = isRepair;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartRecordId() {
        return partRecordId;
    }

    public void setPartRecordId(int partRecordId) {
        this.partRecordId = partRecordId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(int unitCost) {
        this.unitCost = unitCost;
    }

    public int getBuyPeriod() {
        return buyPeriod;
    }

    public void setBuyPeriod(int buyPeriod) {
        this.buyPeriod = buyPeriod;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.specification);
        dest.writeString(this.quantity);
        dest.writeString(this.unit);
        dest.writeString(this.createTime);
        dest.writeByte(this.isRepair ? (byte) 1 : (byte) 0);
        dest.writeInt(this.lowerLimit);
        dest.writeString(this.name);
        dest.writeInt(this.partRecordId);
        dest.writeInt(this.targetId);
        dest.writeInt(this.upperLimit);
        dest.writeString(this.userName);
        dest.writeInt(this.id);
        dest.writeInt(this.typeId);
        dest.writeInt(this.unitCost);
        dest.writeInt(this.buyPeriod);
    }
}
