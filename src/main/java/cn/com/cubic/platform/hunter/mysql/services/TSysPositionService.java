package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TSysPosition;
import cn.com.cubic.platform.hunter.mysql.entity.TSysPositionExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/27.
 **/

public interface TSysPositionService extends BaseService<TSysPosition,TSysPositionExample>{

    PageParams<TSysPosition> list(PageParams<TSysPosition> pageParams);

    List<TSysPosition> listAll();

    TSysPosition findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TSysPosition bean);

}
