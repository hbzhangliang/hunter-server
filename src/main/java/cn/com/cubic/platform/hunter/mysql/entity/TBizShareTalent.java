package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizShareTalent extends BaseEntity{

    private String shareType;

    private Long talentId;

    private String shareValue;

    private String shareLabel;

    private String detail;

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public Long getTalentId() {
        return talentId;
    }

    public void setTalentId(Long talentId) {
        this.talentId = talentId;
    }

    public String getShareValue() {
        return shareValue;
    }

    public void setShareValue(String shareValue) {
        this.shareValue = shareValue;
    }

    public String getShareLabel() {
        return shareLabel;
    }

    public void setShareLabel(String shareLabel) {
        this.shareLabel = shareLabel;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}