package com.huyingbao.dm.ui.message.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 新消息记录表
 */
//@Table(database = AppDatabase.class)extends BaseModel
public class Message implements Parcelable {
    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
    //    @PrimaryKey
    private String sendTime;
    //    @Column
    private int type;
    //    @Column
    private int targetId;
    //    @Column
    private String msg;
    //    @Column
    private int userId;

    public Message() {
    }

    protected Message(Parcel in) {
        this.sendTime = in.readString();
        this.type = in.readInt();
        this.targetId = in.readInt();
        this.msg = in.readString();
        this.userId = in.readInt();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sendTime);
        dest.writeInt(this.type);
        dest.writeInt(this.targetId);
        dest.writeString(this.msg);
        dest.writeInt(this.userId);
    }
}
