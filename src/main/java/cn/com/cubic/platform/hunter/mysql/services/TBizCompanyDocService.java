package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.*;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/28.
 **/

public interface TBizCompanyDocService  extends BaseService<TBizCompanyDoc,TBizCompanyDocExample>{


    PageParams<TBizCompanyDoc> list(PageParams<TBizCompanyDoc> pageParams);

    List<TBizCompanyDoc> listAll();

    TBizCompanyDoc findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizCompanyDoc bean);


    void saveOrUpdate(Long companyId,List<Long> docIds);


    List<Long> getDocIdsByCompanyId(Long companyId);

}
