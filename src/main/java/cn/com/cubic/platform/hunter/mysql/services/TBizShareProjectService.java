package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizShareProject;
import cn.com.cubic.platform.hunter.mysql.entity.TBizShareProjectExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/28.
 **/

public interface TBizShareProjectService extends BaseService<TBizShareProject,TBizShareProjectExample>{

    PageParams<TBizShareProject> list(PageParams<TBizShareProject> pageParams);

    List<TBizShareProject> listAll();

    TBizShareProject findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizShareProject bean);


    List<Long> getAccountsByProjectId(Long projectId,List<TBizShareProject> list);

}
