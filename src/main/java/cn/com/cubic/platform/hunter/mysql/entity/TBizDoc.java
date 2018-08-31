package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizDoc extends BaseEntity{
    private Long type;

    private String name;

    private String remark;

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
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

}