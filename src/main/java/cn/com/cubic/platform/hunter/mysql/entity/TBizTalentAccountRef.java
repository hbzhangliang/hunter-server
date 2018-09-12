package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizTalentAccountRef extends BaseEntity{

    private Long accountId;

    private Long talentId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getTalentId() {
        return talentId;
    }

    public void setTalentId(Long talentId) {
        this.talentId = talentId;
    }
}