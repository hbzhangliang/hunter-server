package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.VShareCompany;
import cn.com.cubic.platform.hunter.mysql.entity.VShareCompanyExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

/**
 * Created by Liang.Zhang on 2018/9/26.
 **/

public interface VShareCompanyService  extends BaseService<VShareCompany,VShareCompanyExample>{

    PageParams<VShareCompany> list(PageParams<VShareCompany> pageParams);

    VShareCompany findById(@NotNull Long id);

}
