package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizDoc extends BaseEntity{

    private String type;

    private String name;

    private String remark;

    private Boolean shareType;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getShareType() {
        return shareType;
    }

    public void setShareType(Boolean shareType) {
        this.shareType = shareType;
    }
}