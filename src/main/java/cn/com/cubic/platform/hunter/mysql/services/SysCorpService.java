package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TSysCorp;
import cn.com.cubic.platform.hunter.mysql.entity.TSysCorpExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/7/10.
 **/

public interface SysCorpService extends BaseService<TSysCorp,TSysCorpExample>{

    TSysCorpExample construct(TSysCorp corp);

    PageParams<TSysCorp> list(PageParams<TSysCorp> pageParams);

    TSysCorp findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TSysCorp corp);

}
