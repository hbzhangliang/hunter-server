package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizShareDoc extends BaseEntity{

    private String shareType;

    private Long docId;

    private Long code;

    private String shareValue;

    private String shareLabel;

    private String detail;


    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
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