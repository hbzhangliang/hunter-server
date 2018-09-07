package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/7.
 **/

public interface TBizRecordProjectService extends BaseService<TBizRecordProject,TBizRecordProjectExample>{

    PageParams<TBizRecordProject> list(PageParams<TBizRecordProject> pageParams);

    List<TBizRecordProject> listAll();

    TBizRecordProject findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizRecordProject bean);

}
