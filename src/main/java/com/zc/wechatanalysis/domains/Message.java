package com.zc.wechatanalysis.domains;

public class Message {
    private long createTime;
    private byte des;
    private String message;
    private byte status;
    private byte type;

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public byte getDes() {
        return this.des;
    }

    public void setDes(byte des) {
        this.des = des;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte getStatus() {
        return this.status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

}