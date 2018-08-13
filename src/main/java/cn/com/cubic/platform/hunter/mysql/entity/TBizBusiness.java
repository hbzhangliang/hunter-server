package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizBusiness extends BaseEntity{

    private String name;

    private Integer seq;

    private Long parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}