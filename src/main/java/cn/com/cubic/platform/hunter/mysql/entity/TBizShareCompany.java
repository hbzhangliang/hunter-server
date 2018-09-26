package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizShareCompany extends BaseEntity {
    private String shareType;

    private Long companyId;

    private String shareValue;

    private String shareLabel;

    private String detail;

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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