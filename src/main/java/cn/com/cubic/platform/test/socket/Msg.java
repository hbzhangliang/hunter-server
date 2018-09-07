package cn.com.cubic.platform.test.socket;

import java.util.Date;

/**
 * Created by Liang.Zhang on 2018/9/7.
 **/

public class Msg {

    private String fromId;
    private String fromName;

    private String toId;
    private String toName;

    private String messge;

    private Date sendTime;

    private Boolean status;

    private String type;

    private String remark;

    public Msg(String fromId,String toId,String messge){
        this.fromId=fromId;
        this.toId=toId;
        this.messge=messge;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
