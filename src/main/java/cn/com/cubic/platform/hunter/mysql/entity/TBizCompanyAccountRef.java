package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizCompanyAccountRef extends BaseEntity {

    private Long accountId;

    private Long companyId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

}