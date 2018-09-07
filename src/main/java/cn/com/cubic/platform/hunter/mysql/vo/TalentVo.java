package cn.com.cubic.platform.hunter.mysql.vo;

import cn.com.cubic.platform.hunter.mysql.entity.*;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/6.
 **/

public class TalentVo extends TBizTalent {

    private List<TBizShareTalent> shareTalentList;

    private List<TBizRecordWork> recordWorkList;

    private List<TBizRecordProject> recordProjectList;

    private List<TBizRecordEducation> recordEducationList;

    private List<TBizRecordLanguage> recordLanguageList;


    public List<TBizShareTalent> getShareTalentList() {
        return shareTalentList;
    }

    public void setShareTalentList(List<TBizShareTalent> shareTalentList) {
        this.shareTalentList = shareTalentList;
    }

    public List<TBizRecordWork> getRecordWorkList() {
        return recordWorkList;
    }

    public void setRecordWorkList(List<TBizRecordWork> recordWorkList) {
        this.recordWorkList = recordWorkList;
    }

    public List<TBizRecordProject> getRecordProjectList() {
        return recordProjectList;
    }

    public void setRecordProjectList(List<TBizRecordProject> recordProjectList) {
        this.recordProjectList = recordProjectList;
    }

    public List<TBizRecordEducation> getRecordEducationList() {
        return recordEducationList;
    }

    public void setRecordEducationList(List<TBizRecordEducation> recordEducationList) {
        this.recordEducationList = recordEducationList;
    }

    public List<TBizRecordLanguage> getRecordLanguageList() {
        return recordLanguageList;
    }

    public void setRecordLanguageList(List<TBizRecordLanguage> recordLanguageList) {
        this.recordLanguageList = recordLanguageList;
    }
}
