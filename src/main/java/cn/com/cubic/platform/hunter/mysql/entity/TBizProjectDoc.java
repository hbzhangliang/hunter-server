package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizProjectDoc extends BaseEntity{

    private Long projectId;

    private Long docId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }
}