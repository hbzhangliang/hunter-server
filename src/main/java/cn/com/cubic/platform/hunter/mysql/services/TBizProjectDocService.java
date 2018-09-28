package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCompanyDoc;
import cn.com.cubic.platform.hunter.mysql.entity.TBizCompanyDocExample;
import cn.com.cubic.platform.hunter.mysql.entity.TBizProjectDoc;
import cn.com.cubic.platform.hunter.mysql.entity.TBizProjectDocExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/28.
 **/

public interface TBizProjectDocService extends BaseService<TBizProjectDoc,TBizProjectDocExample>{

    PageParams<TBizProjectDoc> list(PageParams<TBizProjectDoc> pageParams);

    List<TBizProjectDoc> listAll();

    TBizProjectDoc findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizProjectDoc bean);


    void saveOrUpdate(Long projectId,List<Long> docIds);


    List<Long> getDocIdsByProjectId(Long projectId);

}
