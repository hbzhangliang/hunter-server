package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.Date;

public class TBizTalentDoc extends BaseEntity{

    private Long talentId;

    private Long docId;


    public Long getTalentId() {
        return talentId;
    }

    public void setTalentId(Long talentId) {
        this.talentId = talentId;
    }

    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

}