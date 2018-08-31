package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizShareDoc;
import cn.com.cubic.platform.hunter.mysql.entity.TBizShareDocExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/8/31.
 **/

public interface TBizShareDocService extends BaseService<TBizShareDoc,TBizShareDocExample>{

    PageParams<TBizShareDoc> list(PageParams<TBizShareDoc> pageParams);

    List<TBizShareDoc> listAll();

    TBizShareDoc findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizShareDoc bean);


    List<TBizShareDoc> listByDoc(@NotNull Long docId,String shareType);

}
