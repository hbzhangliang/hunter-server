package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizCity extends BaseEntity{

    private String name;

    private Long parentId;

    private Integer seq;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

}