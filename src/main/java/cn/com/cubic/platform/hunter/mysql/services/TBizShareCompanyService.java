package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizShareCompany;
import cn.com.cubic.platform.hunter.mysql.entity.TBizShareCompanyExample;
import cn.com.cubic.platform.hunter.mysql.entity.TBizShareTalent;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/26.
 **/

public interface TBizShareCompanyService extends BaseService<TBizShareCompany,TBizShareCompanyExample>{

    PageParams<TBizShareCompany> list(PageParams<TBizShareCompany> pageParams);

    List<TBizShareCompany> listAll();

    TBizShareCompany findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(TBizShareCompany bean);


    List<Long> getAccountsBytalentid(Long talentId,List<TBizShareCompany> list);

}
