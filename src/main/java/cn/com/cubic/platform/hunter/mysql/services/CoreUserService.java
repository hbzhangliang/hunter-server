package cn.com.cubic.platform.hunter.mysql.services;

import cn.com.cubic.platform.hunter.mysql.entity.CoreUser;
import cn.com.cubic.platform.hunter.mysql.entity.CoreUserExample;
import cn.com.cubic.platform.hunter.mysql.vo.PageParams;
import cn.com.flaginfo.platform.api.common.base.BaseResponse;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/6/22.
 **/

public interface CoreUserService extends BaseService<CoreUser,CoreUserExample>{

    BaseResponse<PageParams<CoreUser>> list(PageParams pageParams);

    BaseResponse<CoreUser> findById(Long id);

    BaseResponse<Boolean> del(List<Long> ids);

    BaseResponse<Boolean> saveOrUpdate(CoreUser coreUser);

}
