package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.CoreUser;
import cn.com.cubic.platform.hunter.mysql.entity.CoreUserExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.flaginfo.platform.api.common.base.BaseResponse;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/6/22.
 **/

public interface CoreUserService extends BaseService<CoreUser,CoreUserExample>{

    CoreUserExample construct(CoreUser coreUser);

    PageParams<CoreUser> list(PageParams<CoreUser> pageParams);

    CoreUser findById(@NotNull Long id);

    Boolean del(List<Long> ids);

    Boolean saveOrUpdate(CoreUser coreUser);


    CoreUser selectById(Long id);


    List<CoreUser> callProc();

}
