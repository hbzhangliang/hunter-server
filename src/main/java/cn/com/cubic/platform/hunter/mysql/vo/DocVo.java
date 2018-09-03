package cn.com.cubic.platform.hunter.mysql.vo;

import cn.com.cubic.platform.hunter.mysql.entity.TBizDoc;

import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/9/3.
 **/

public class DocVo extends TBizDoc {
    List<Map<String,String>> share;

    public List<Map<String, String>> getShare() {
        return share;
    }

    public void setShare(List<Map<String, String>> share) {
        this.share = share;
    }
}
