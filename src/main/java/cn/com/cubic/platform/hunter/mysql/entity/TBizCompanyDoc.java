package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizCompanyDoc extends BaseEntity{

    private Long companyId;

    private Long docId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }
}