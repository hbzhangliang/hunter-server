package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizRecordEducation extends BaseEntity{

    private Long talentId;

    private Date startDate;

    private Date endDate;

    private Boolean onCollege;

    private String college;

    private String major;

    private String education;

    private Integer seq;

    public Long getTalentId() {
        return talentId;
    }

    public void setTalentId(Long talentId) {
        this.talentId = talentId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getOnCollege() {
        return onCollege;
    }

    public void setOnCollege(Boolean onCollege) {
        this.onCollege = onCollege;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

}