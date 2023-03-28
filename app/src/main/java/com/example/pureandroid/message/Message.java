package com.example.pureandroid.message;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;


public class Message implements Parcelable {


    public long messageId;
    public String sender;
    /**
     * 消息在会话中定向发送给指定用户
     */
    public String[] toUsers;
    public long messageUid;
    public long serverTime;

    public Message() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.messageId);
        dest.writeString(this.sender);
        dest.writeStringArray(this.toUsers);
        dest.writeLong(this.messageUid);
        dest.writeLong(this.serverTime);
    }

    protected Message(Parcel in) {
        this.messageId = in.readLong();
        this.sender = in.readString();
        this.toUsers = in.createStringArray();
        int tmpDirection = in.readInt();
        int tmpStatus = in.readInt();
        this.messageUid = in.readLong();
        this.serverTime = in.readLong();
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (messageId != message.messageId) return false;
        if (messageUid != message.messageUid) return false;
        if (serverTime != message.serverTime) return false;
        if (!sender.equals(message.sender)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(toUsers, message.toUsers)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (messageId ^ (messageId >>> 32));
        result = 31 * result + sender.hashCode();
        result = 31 * result + Arrays.hashCode(toUsers);
        result = 31 * result + (int) (messageUid ^ (messageUid >>> 32));
        result = 31 * result + (int) (serverTime ^ (serverTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", sender='" + sender + '\'' +
                ", toUsers=" + Arrays.toString(toUsers) +
                ", messageUid=" + messageUid +
                ", serverTime=" + serverTime +
                '}';
    }
}
