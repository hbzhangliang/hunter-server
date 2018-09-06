package cn.com.cubic.platform.hunter.mysql.vo;

import cn.com.cubic.platform.hunter.mysql.entity.TBizTalent;

/**
 * Created by Liang.Zhang on 2018/9/6.
 **/

public class TalentVo extends TBizTalent {

    private String shareType;

    private String shareValue;

    private String shareLabel;


    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getShareValue() {
        return shareValue;
    }

    public void setShareValue(String shareValue) {
        this.shareValue = shareValue;
    }

    public String getShareLabel() {
        return shareLabel;
    }

    public void setShareLabel(String shareLabel) {
        this.shareLabel = shareLabel;
    }
}
