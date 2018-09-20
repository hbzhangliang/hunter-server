package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.VTalentDoc;
import cn.com.cubic.platform.hunter.mysql.entity.VTalentDocExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

/**
 * Created by Liang.Zhang on 2018/9/20.
 **/

public interface VTalentDocService extends BaseService<VTalentDoc,VTalentDocExample>{

    PageParams<VTalentDoc> list(PageParams<VTalentDoc> pageParams);

    VTalentDoc findById(@NotNull Long id);

}
