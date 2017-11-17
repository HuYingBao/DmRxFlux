package com.huyingbao.dm.ui.devicelist.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 设备
 * Created by liujunfeng on 2017/3/22.
 */
public class Device implements Parcelable {

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel source) {
            return new Device(source);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
    /**
     * code : B-QX-00-03
     * complexDf : 12
     * complexJf : 10
     * createTime : 2017-05-17 22:15:43
     * degree : 3
     * dprcPeriod : 10
     * id : 5
     * manufacturer : 张家港海纳超声电器有限公司
     * name : 喷胶机03
     * org : 清洗二部
     * power : 3
     * price : 6.4
     * remark : test
     * serviceDate : 2017-05-17
     * statusMain : 1
     * type : HNP
     * typeId : 5
     */
    private String code;
    private int complexDf;
    private int complexJf;
    private String createTime;
    private int degree;
    private int dprcPeriod;
    private int id;
    private String manufacturer;
    private String name;
    private String org;
    private int power;
    private double price;
    private String remark;
    private String serviceDate;
    private String headImg;
    private int statusMain;
    private int[] statusMinor;
    private String type;
    private int typeId;

    public Device() {
    }

    protected Device(Parcel in) {
        this.code = in.readString();
        this.complexDf = in.readInt();
        this.complexJf = in.readInt();
        this.createTime = in.readString();
        this.degree = in.readInt();
        this.dprcPeriod = in.readInt();
        this.id = in.readInt();
        this.manufacturer = in.readString();
        this.name = in.readString();
        this.org = in.readString();
        this.power = in.readInt();
        this.price = in.readDouble();
        this.remark = in.readString();
        this.serviceDate = in.readString();
        this.headImg = in.readString();
        this.statusMain = in.readInt();
        this.statusMinor = in.createIntArray();
        this.type = in.readString();
        this.typeId = in.readInt();
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getComplexDf() {
        return complexDf;
    }

    public void setComplexDf(int complexDf) {
        this.complexDf = complexDf;
    }

    public int getComplexJf() {
        return complexJf;
    }

    public void setComplexJf(int complexJf) {
        this.complexJf = complexJf;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getDprcPeriod() {
        return dprcPeriod;
    }

    public void setDprcPeriod(int dprcPeriod) {
        this.dprcPeriod = dprcPeriod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeInt(this.complexDf);
        dest.writeInt(this.complexJf);
        dest.writeString(this.createTime);
        dest.writeInt(this.degree);
        dest.writeInt(this.dprcPeriod);
        dest.writeInt(this.id);
        dest.writeString(this.manufacturer);
        dest.writeString(this.name);
        dest.writeString(this.org);
        dest.writeInt(this.power);
        dest.writeDouble(this.price);
        dest.writeString(this.remark);
        dest.writeString(this.serviceDate);
        dest.writeString(this.headImg);
        dest.writeInt(this.statusMain);
        dest.writeIntArray(this.statusMinor);
        dest.writeString(this.type);
        dest.writeInt(this.typeId);
    }
}
