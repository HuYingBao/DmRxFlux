package com.huyingbao.dm.ui.inspection.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 巡检项
 * Created by liujunfeng on 2017/5/26.
 */
public class InspectItem implements Parcelable {
    public static final Parcelable.Creator<InspectItem> CREATOR = new Parcelable.Creator<InspectItem>() {
        @Override
        public InspectItem createFromParcel(Parcel source) {
            return new InspectItem(source);
        }

        @Override
        public InspectItem[] newArray(int size) {
            return new InspectItem[size];
        }
    };
    /**
     * content : 清洗槽、储液槽无渗漏，水泵运转正常无渗漏
     * id : 1
     * inspectId : 1
     * result : 1
     */
    private String content;
    private int id;
    private int inspectId;
    private int result;//巡检结果：0故障；1正常

    public InspectItem() {
    }

    protected InspectItem(Parcel in) {
        this.content = in.readString();
        this.id = in.readInt();
        this.inspectId = in.readInt();
        this.result = in.readInt();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInspectId() {
        return inspectId;
    }

    public void setInspectId(int inspectId) {
        this.inspectId = inspectId;
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
        dest.writeString(this.content);
        dest.writeInt(this.id);
        dest.writeInt(this.inspectId);
        dest.writeInt(this.result);
    }
}
