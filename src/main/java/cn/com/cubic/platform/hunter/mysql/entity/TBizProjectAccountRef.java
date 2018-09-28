package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizProjectAccountRef extends BaseEntity{

    private Long accountId;

    private Long projectId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

}