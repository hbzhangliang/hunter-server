package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TSysConfig;
import cn.com.cubic.platform.hunter.mysql.entity.TSysConfigExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/13.
 **/

public interface TSysConfigService extends BaseService<TSysConfig,TSysConfigExample>{

    PageParams<TSysConfig> list(PageParams<TSysConfig> pageParams);

    List<TSysConfig> listAll();

    TSysConfig findById(@NotNull Long id);

    TSysConfig findByCode(@NotNull String code);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TSysConfig bean);

}
