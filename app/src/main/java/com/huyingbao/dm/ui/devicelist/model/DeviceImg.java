package com.huyingbao.dm.ui.devicelist.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liujunfeng on 2017/3/29.
 */
public class DeviceImg implements Parcelable {
    public static final Parcelable.Creator<DeviceImg> CREATOR = new Parcelable.Creator<DeviceImg>() {
        @Override
        public DeviceImg createFromParcel(Parcel source) {
            return new DeviceImg(source);
        }

        @Override
        public DeviceImg[] newArray(int size) {
            return new DeviceImg[size];
        }
    };
    private int id;
    private int machineId;
    private int cls;//附件类型：1图片；2设备合同；3技术文档；4安装调试记录
    private String name;//附件名称
    private String filePath;//文件地址

    public DeviceImg() {
    }

    protected DeviceImg(Parcel in) {
        this.id = in.readInt();
        this.machineId = in.readInt();
        this.cls = in.readInt();
        this.name = in.readString();
        this.filePath = in.readString();
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

    public int getCls() {
        return cls;
    }

    public void setCls(int cls) {
        this.cls = cls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.machineId);
        dest.writeInt(this.cls);
        dest.writeString(this.name);
        dest.writeString(this.filePath);
    }
}
