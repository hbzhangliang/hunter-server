package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.TBizCompany;
import cn.com.cubic.platform.hunter.mysql.entity.TBizCompanyExample;
import cn.com.cubic.platform.hunter.mysql.vo.CompanyVo;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/9/25.
 **/

public interface TBizCompanyService  extends BaseService<TBizCompany,TBizCompanyExample>{
    PageParams<TBizCompany> list(PageParams<TBizCompany> pageParams,Boolean ownerFlag);

    List<TBizCompany> listAll();

    TBizCompany findById(@NotNull Long id);

    Boolean fakeDel(List<Long> ids);

    Boolean adminDel(List<Long> ids);

    Boolean saveOrUpdate(CompanyVo bean);

    CompanyVo findVoById(Long id);

}
