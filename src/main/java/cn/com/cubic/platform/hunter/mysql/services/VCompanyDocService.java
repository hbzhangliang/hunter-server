package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.VCompanyDoc;
import cn.com.cubic.platform.hunter.mysql.entity.VCompanyDocExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import com.sun.istack.internal.NotNull;

/**
 * Created by Liang.Zhang on 2018/9/28.
 **/


public interface VCompanyDocService  extends BaseService<VCompanyDoc,VCompanyDocExample>{

    PageParams<VCompanyDoc> list(PageParams<VCompanyDoc> pageParams);

    VCompanyDoc findById(@NotNull Long id);

}
