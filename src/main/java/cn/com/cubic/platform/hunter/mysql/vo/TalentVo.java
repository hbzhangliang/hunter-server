package cn.com.cubic.platform.hunter.mysql.vo;

import cn.com.cubic.platform.hunter.mysql.entity.*;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/6.
 **/

public class TalentVo extends TBizTalent {

    //籍贯
    private String tmpNativePlace;

    //所在城市
    private String tmpCity;

    //行业
    private List<Long> tmpBusinessId;
    private String tmpBusinessName;

    //职能
    private List<Long> tmpCareerId;
    private String tmpCareerName;

    //意向城市
    private List<Long> tmpIntentCityId;
    private String tmpIntentCityName;

    //标签
    private List<Long> tmpTagsId;
    private String tmpTagsName;


    private List<TBizShareTalent> shareTalentList;

    private List<TBizRecordWork> recordWorkList;

    private List<TBizRecordProject> recordProjectList;

    private List<TBizRecordEducation> recordEducationList;

    private List<TBizRecordLanguage> recordLanguageList;


    public String getTmpNativePlace() {
        return tmpNativePlace;
    }

    public void setTmpNativePlace(String tmpNativePlace) {
        this.tmpNativePlace = tmpNativePlace;
    }

    public String getTmpCity() {
        return tmpCity;
    }

    public void setTmpCity(String tmpCity) {
        this.tmpCity = tmpCity;
    }

    public List<Long> getTmpBusinessId() {
        return tmpBusinessId;
    }

    public void setTmpBusinessId(List<Long> tmpBusinessId) {
        this.tmpBusinessId = tmpBusinessId;
    }

    public String getTmpBusinessName() {
        return tmpBusinessName;
    }

    public void setTmpBusinessName(String tmpBusinessName) {
        this.tmpBusinessName = tmpBusinessName;
    }

    public List<Long> getTmpCareerId() {
        return tmpCareerId;
    }

    public void setTmpCareerId(List<Long> tmpCareerId) {
        this.tmpCareerId = tmpCareerId;
    }

    public String getTmpCareerName() {
        return tmpCareerName;
    }

    public void setTmpCareerName(String tmpCareerName) {
        this.tmpCareerName = tmpCareerName;
    }

    public List<Long> getTmpIntentCityId() {
        return tmpIntentCityId;
    }

    public void setTmpIntentCityId(List<Long> tmpIntentCityId) {
        this.tmpIntentCityId = tmpIntentCityId;
    }

    public String getTmpIntentCityName() {
        return tmpIntentCityName;
    }

    public void setTmpIntentCityName(String tmpIntentCityName) {
        this.tmpIntentCityName = tmpIntentCityName;
    }

    public List<Long> getTmpTagsId() {
        return tmpTagsId;
    }

    public void setTmpTagsId(List<Long> tmpTagsId) {
        this.tmpTagsId = tmpTagsId;
    }

    public String getTmpTagsName() {
        return tmpTagsName;
    }

    public void setTmpTagsName(String tmpTagsName) {
        this.tmpTagsName = tmpTagsName;
    }

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
