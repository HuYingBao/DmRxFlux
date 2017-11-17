package com.huyingbao.dm.ui.inspection.model;

import android.os.Parcel;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.rxflux2.model.BaseDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * 巡检记录
 * Created by liujunfeng on 2017/3/22.
 */
public class Inspection extends BaseDevice implements MultiItemEntity {
    public static final Creator<Inspection> CREATOR = new Creator<Inspection>() {
        @Override
        public Inspection createFromParcel(Parcel source) {
            return new Inspection(source);
        }

        @Override
        public Inspection[] newArray(int size) {
            return new Inspection[size];
        }
    };
    /**
     * id : 1
     * informRepairer : false
     * result : 1
     * inspectTime : 2017-05-23 17:00:12
     * inspectorId : 2
     * inspectorName : 杨前锋
     * machineId : 7
     * createTime : 2017-07-22 04:14:08
     * planDate : 2017-07-22
     */
    private int id;
    private boolean informRepairer;//通知维修员：0不通知；1通知
    private int result;//巡检结果：-1:未开始,0:故障,1:正常,2:不通过
    private String inspectTime;
    private int inspectorId;
    private String inspectorName;
    private List<InspectItem> items;
    private String createTime;
    private String planDate;

    public Inspection() {
    }

    protected Inspection(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.informRepairer = in.readByte() != 0;
        this.result = in.readInt();
        this.inspectTime = in.readString();
        this.inspectorId = in.readInt();
        this.inspectorName = in.readString();
        this.items = new ArrayList<InspectItem>();
        in.readList(this.items, InspectItem.class.getClassLoader());
        this.createTime = in.readString();
        this.planDate = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInformRepairer() {
        return informRepairer;
    }

    public void setInformRepairer(boolean informRepairer) {
        this.informRepairer = informRepairer;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getInspectTime() {
        return inspectTime;
    }

    public void setInspectTime(String inspectTime) {
        this.inspectTime = inspectTime;
    }

    public int getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(int inspectorId) {
        this.inspectorId = inspectorId;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public List<InspectItem> getItems() {
        return items;
    }

    public void setItems(List<InspectItem> items) {
        this.items = items;
    }

    @Override
    public int getItemType() {
        return Constants.BASE_DEVICE_TYPE_INSPECTION;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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
        dest.writeInt(this.id);
        dest.writeByte(this.informRepairer ? (byte) 1 : (byte) 0);
        dest.writeInt(this.result);
        dest.writeString(this.inspectTime);
        dest.writeInt(this.inspectorId);
        dest.writeString(this.inspectorName);
        dest.writeList(this.items);
        dest.writeString(this.createTime);
        dest.writeString(this.planDate);
    }
}
