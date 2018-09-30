package cn.com.cubic.platform.hunter.mysql.vo;

import cn.com.cubic.platform.hunter.mysql.entity.TBizProject;
import cn.com.cubic.platform.hunter.mysql.entity.TBizShareProject;
import cn.com.cubic.platform.hunter.mysql.entity.TBizShareTalent;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/28.
 **/

public class ProjectVo extends TBizProject {

    //城市
    private List<Long> tmpCityId;
    private String tmpCityName;

    //职能
    private List<Long> tmpCareerId;
    private String tmpCareerName;


    //联系人
    private List<Long> tmpTalentIds;
    private String tmpTalentNames;
    private List<TalentVo> talentVoList;


    private List<TBizShareProject> shareProjectList;


    public List<Long> getTmpCityId() {
        return tmpCityId;
    }

    public void setTmpCityId(List<Long> tmpCityId) {
        this.tmpCityId = tmpCityId;
    }

    public String getTmpCityName() {
        return tmpCityName;
    }

    public void setTmpCityName(String tmpCityName) {
        this.tmpCityName = tmpCityName;
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

    public List<TBizShareProject> getShareProjectList() {
        return shareProjectList;
    }

    public void setShareProjectList(List<TBizShareProject> shareProjectList) {
        this.shareProjectList = shareProjectList;
    }

    public List<TalentVo> getTalentVoList() {
        return talentVoList;
    }

    public void setTalentVoList(List<TalentVo> talentVoList) {
        this.talentVoList = talentVoList;
    }

    public List<Long> getTmpTalentIds() {
        return tmpTalentIds;
    }

    public void setTmpTalentIds(List<Long> tmpTalentIds) {
        this.tmpTalentIds = tmpTalentIds;
    }

    public String getTmpTalentNames() {
        return tmpTalentNames;
    }

    public void setTmpTalentNames(String tmpTalentNames) {
        this.tmpTalentNames = tmpTalentNames;
    }
}
