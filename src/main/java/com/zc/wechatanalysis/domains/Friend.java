package com.zc.wechatanalysis.domains;

public class Friend {

    private String dbContactProfile;
    private String dbContactRemark;
    private byte type;
    private String userName;
    private String userNameMd5;

    public String getDbContactProfile() {
        return this.dbContactProfile;
    }

    public String getUserNameMd5() {
        return userNameMd5;
    }

    public void setUserNameMd5(String userNameMd5) {
        this.userNameMd5 = userNameMd5;
    }

    public void setDbContactProfile(String dbContactProfile) {
        this.dbContactProfile = dbContactProfile;
    }

    public String getDbContactRemark() {
        return this.dbContactRemark;
    }

    public void setDbContactRemark(String dbContactRemark) {
        this.dbContactRemark = dbContactRemark;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}