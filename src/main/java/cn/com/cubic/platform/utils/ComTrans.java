package cn.com.cubic.platform.utils;

import cn.com.cubic.platform.hunter.mysql.entity.TBizDoc;
import cn.com.cubic.platform.hunter.mysql.vo.DocVo;

import java.util.List;
import java.util.Map;

/**
 * Created by Liang.Zhang on 2018/9/3.
 **/

public class ComTrans {


    public static DocVo transDocVo(TBizDoc doc, List<Map<String,String>> list){
        if(null==doc) return null;
        DocVo vo=new DocVo();
        vo.setId(doc.getId());
        vo.setName(doc.getName());
        vo.setType(doc.getType());
        vo.setRemark(doc.getRemark());
        vo.setOwner(doc.getOwner());
        vo.setShare(list);
        return vo;
    }

}
