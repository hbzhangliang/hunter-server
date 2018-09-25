package cn.com.cubic.platform.hunter.mysql.vo;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCompany;
import cn.com.cubic.platform.hunter.mysql.entity.TBizShareCompany;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/25.
 **/

public class CompanyVo extends TBizCompany {
    //行业
    private List<Long> tmpBusinessId;
    private String tmpBusinessName;

    //意向城市
    private List<Long> tmpCityId;
    private String tmpCityName;


    private List<TBizShareCompany> shareCompanyList;

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

    public List<TBizShareCompany> getShareCompanyList() {
        return shareCompanyList;
    }

    public void setShareCompanyList(List<TBizShareCompany> shareCompanyList) {
        this.shareCompanyList = shareCompanyList;
    }
}
