package cn.com.cubic.platform.a_book.mysql.services;

import cn.com.cubic.platform.a_book.mysql.entity.CoreUser;
import cn.com.cubic.platform.a_book.mysql.entity.CoreUserExample;
import cn.com.cubic.platform.a_book.mysql.vo.PageForm;
import cn.com.cubic.platform.a_book.mysql.vo.PageParams;
import cn.com.flaginfo.platform.api.common.base.BaseResponse;

import java.util.List;

/**
 * Created by Liang.Zhang on 2018/6/22.
 **/

public interface CoreUserService extends BaseService<CoreUser,CoreUserExample>{

    BaseResponse<PageParams<CoreUser>> list(PageForm pageForm);

    BaseResponse<CoreUser> findById(Long id);

    BaseResponse<Boolean> del(List<Long> ids);

    BaseResponse<Boolean> saveOrUpdate(CoreUser coreUser);

}
