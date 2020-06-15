package com.zc.wechatanalysis.domains;

public class ESMessage extends Message {
    private String dbContactRemark;
    private String userName;
    private byte friendType;

    public String getDbContactRemark() {
        return this.dbContactRemark;
    }

    public byte getFriendType() {
        return friendType;
    }

    public void setFriendType(byte friendType) {
        this.friendType = friendType;
    }

    public void setDbContactRemark(String dbContactRemark) {
        this.dbContactRemark = dbContactRemark;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "{" +
            " dbContactRemark='" + getDbContactRemark() + "'" +
                ", userName='" + getUserName() + "'" +
                ", message='" + getMessage() + "'" +
                ", createTime='" + getCreateTime() + "'" +
            "}";
    }

}