package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizShareDoc extends BaseEntity{

    private String shareType;

    private Long docId;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}